

import java.util.*;

public class StrikeZoneDetector{
    static final double BallWidthInches = 3.82;
    static final double PlateWidthInches = 8.5; 
    private CSVReader reader;

    // Constructor
    public StrikeZoneDetector(CSVReader reader) {
        this.reader = reader;
    }

    private Vector2 backShoulderPosition() {
        Vector2 shoulder1 = this.reader.getShoulder1();
        Vector2 shoulder2 = this.reader.getShoulder2();
        if(shoulder1.x > shoulder2.x){
            return shoulder1;
        } else {
            return shoulder2;
        }
    }

    private Vector2 frontKneePosition() {
        Vector2 knee1 = this.reader.getKnee1();
        Vector2 knee2 = this.reader.getKnee2();
        if(knee1.x < knee2.x){
            return knee1;
        } else {
            return knee2;
        }
    }

    private ArrayList<Boolean> sideViewStrike() {

        ArrayList<Object[]> pitchData = this.reader.getPitchData();
        Vector2 frontCloseCorner = this.reader.getFrontCloseCorner();
        Vector2 backCloseCorner = this.reader.getBackCloseCorner();
        Vector2 backFarCorner = this.reader.getBackFarCorner();
        Vector2 frontFarCorner = this.reader.getFrontFarCorner();

        ArrayList<Boolean> output = new ArrayList<>();

        double topOfStrikeZone = this.backShoulderPosition().y;
        double bottomOfStrikeZone = this.frontKneePosition().y;
        double frontOfPlate = (frontFarCorner.x + frontCloseCorner.x)/2;
        double backOfPlate =  (backFarCorner.x + backCloseCorner.x)/2;

        Vector2 frontTop = new Vector2(frontOfPlate,topOfStrikeZone);
        Vector2 frontBottom = new Vector2(frontOfPlate,bottomOfStrikeZone);
        Vector2 backTop = new Vector2(backOfPlate,topOfStrikeZone);
        Vector2 backBottom = new Vector2(backOfPlate,bottomOfStrikeZone);

        for (int pi = 0; pi < pitchData.size(); pi++) {

            Vector2 centerPitch = (Vector2) pitchData.get(pi)[1];

            boolean goodHeight = centerPitch.y > topOfStrikeZone && centerPitch.y < bottomOfStrikeZone;
            boolean leftAndRight = centerPitch.x < backOfPlate && centerPitch.x > frontOfPlate;
            
            if(goodHeight && leftAndRight) {
                output.add(true);
                continue;
            }

            if (ballIntersectsLine(centerPitch,frontTop,frontBottom) ||
                ballIntersectsLine(centerPitch,frontBottom,backBottom) ||
                ballIntersectsLine(centerPitch,backBottom,backTop) ||
                ballIntersectsLine(centerPitch,backTop,frontTop))
            {
                output.add(true);
                continue;
            }
            
            // If we have a previous position, check if the ball passed through the strike zone between frames
            if(pi > 0) {

                Vector2 prevLeft = (Vector2) pitchData.get(pi-1)[2];
                Vector2 prevRight = (Vector2) pitchData.get(pi-1)[3];
                Vector2 currentLeft = (Vector2) pitchData.get(pi)[2];
                Vector2 currentRight = (Vector2) pitchData.get(pi)[3];
                
                // Checking all 4 sides with both left and right side of path of ball
                if (Vector2.lineLineIntersect(prevLeft,currentLeft,frontTop,frontBottom) ||
                    Vector2.lineLineIntersect(prevLeft,currentLeft,frontBottom,backBottom) ||
                    Vector2.lineLineIntersect(prevLeft,currentLeft,backBottom,backTop) ||
                    Vector2.lineLineIntersect(prevLeft,currentLeft,backTop,frontTop) ||
                    Vector2.lineLineIntersect(prevRight,currentRight,frontTop,frontBottom) ||
                    Vector2.lineLineIntersect(prevRight,currentRight,frontBottom,backBottom) ||
                    Vector2.lineLineIntersect(prevRight,currentRight,backBottom,backTop) ||
                    Vector2.lineLineIntersect(prevRight,currentRight,backTop,frontTop)
                )
                {
                    output.add(true);
                    continue;
                }

            }

            output.add(false);

        }
        return output;

    }

    private ArrayList<Boolean> ballOverPlate() {

        ArrayList<Object[]> pitchData = this.reader.getPitchData();
        ArrayList<Double> cleanPitchData = new ArrayList<>();
        ArrayList<Boolean> bop = new ArrayList<>();

        Vector2 frontCloseCorner = this.reader.getFrontCloseCorner();
        Vector2 backCloseCorner = this.reader.getBackCloseCorner();
        Vector2 backFarCorner = this.reader.getBackFarCorner();
        Vector2 frontFarCorner = this.reader.getFrontFarCorner();

        double ratioClose = frontCloseCorner.distanceTo(backCloseCorner) / PlateWidthInches;
        double ratioFar = frontFarCorner.distanceTo(backFarCorner) / PlateWidthInches;

        // Scrub and do depth calculation before
        for(Object[] pitch : pitchData) {
            Vector2 left = (Vector2) pitch[2];
            Vector2 right = (Vector2) pitch[3];
            double ballWidth = left.distanceTo(right);
            cleanPitchData.add(ballWidth);
        }

        for(int pi = 1; pi < cleanPitchData.size() - 1; pi++) {
            double avgWidth = (cleanPitchData.get(pi+1) + cleanPitchData.get(pi-1)) / 2;
            double change = Math.abs(cleanPitchData.get(pi) - avgWidth);
            // System.out.println(String.format("%d-%d: %.02f",pi-1,pi+1,change));
            if(change >= 1.0) {
                cleanPitchData.set(pi,avgWidth);
                // System.out.println(String.format("Changed %d to %.02f",pi,avgWidth));
            }
            
        }

        for(double ratio : cleanPitchData) {
            ratio = ratio/BallWidthInches;
            boolean overPlate = (ratio > ratioFar && ratio < ratioClose);
            bop.add( overPlate );
        }

        return bop;
    } 

    private Boolean ballIntersectsLine(Vector2 ballPos, Vector2 e1, Vector2 e2) {
        double dx = e2.x - e1.x;
        double dy = e2.y - e1.y;
        double dotProduct = (ballPos.x - e1.x) * dx + (ballPos.y - e1.y) * dy;
        double t = dotProduct / (dx * dx + dy * dy);

        if (t < 0) {
            t = 0;
        } else if (t > 1) {
            t = 1;
        }

        double xClosest = e1.x + t * dx;
        double yClosest = e1.y + t * dy;
        Vector2 closestPoint = new Vector2(xClosest,yClosest);
        return closestPoint.distanceTo(ballPos) <= BallWidthInches;
    }

    public Boolean judgePitch() {
        ArrayList<Boolean> bop = this.ballOverPlate();
        ArrayList<Boolean> sideX = this.sideViewStrike();
        assert bop.size() == sideX.size();
        for(int pi = 0; pi < bop.size(); pi++) {
            if(bop.get(pi) && sideX.get(pi)){
                return true;
            }
        }
        return false;
    }

}