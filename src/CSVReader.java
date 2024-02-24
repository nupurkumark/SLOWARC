

import java.io.*;
import java.util.*;

public class CSVReader {

    private Vector2 frontCloseCorner = null;
    private Vector2 backCloseCorner = null;
    private Vector2 point = null;
    private Vector2 backFarCorner = null;
    private Vector2 frontFarCorner = null;
    private Vector2 shoulder1 = null;
    private Vector2 shoulder2 = null;
    private Vector2 knee1 = null;
    private Vector2 knee2 = null;
    private ArrayList<Object[]> pitchData = new ArrayList<>();

    public CSVReader() {}

    public void readFile(String filename) {
        File file = new File(filename);
        if (!file.isFile() || !file.getName().toLowerCase().endsWith(".csv")) {
            System.out.println(String.format("Invalid file: %s",filename));
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineCount = 1; // Keep track of line number
            while ((line = br.readLine()) != null) {
                // System.out.println(String.format("Read Line[%d]: %s",lineCount,line));
                // Read the line and parse it into its parts
                String[] parts = line.split(",");
                double[] d_parts = new double[parts.length];
                for(int n = 0; n < parts.length; n++) {
                    d_parts[n] = Double.parseDouble(parts[n]);
                }

                switch (lineCount) {
                    case 1: // Home plate front close corner
                        this.frontCloseCorner = new Vector2(d_parts[0], d_parts[1]);
                        break;
                    case 2: // Home plate back close corner
                        this.backCloseCorner = new Vector2(d_parts[0],d_parts[1]);
                        break;
                    case 3: // Home plate pointed part
                        this.point = new Vector2(d_parts[0],d_parts[1]);
                        break;
                    case 4: // Home plate back far corner
                        this.backFarCorner = new Vector2(d_parts[0],d_parts[1]);
                        break;
                    case 5: // Home plate front far corner
                        this.frontFarCorner = new Vector2(d_parts[0],d_parts[1]);
                        break;
                    case 6: // Shoulder 1
                        this.shoulder1 = new Vector2(d_parts[0],d_parts[1]);
                        break;
                    case 7: // Shoulder 2
                        this.shoulder2 = new Vector2(d_parts[0],d_parts[1]);
                        break;
                    case 8: // Knee 1
                        this.knee1 = new Vector2(d_parts[0],d_parts[1]);
                        break;
                    case 9: // Knee 2
                        this.knee2 = new Vector2(d_parts[0],d_parts[1]);
                        break;
                    default: // Ball Position
                        Object[] quad = new Object[4];
                        quad[0] = d_parts[0];
                        quad[1] = new Vector2(d_parts[1],d_parts[2]); // Middle
                        quad[2] = new Vector2(d_parts[3],d_parts[4]); // left
                        quad[3] = new Vector2(d_parts[5],d_parts[6]); // Right
                        this.pitchData.add(quad);
                        break;
                }
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Vector2 getFrontCloseCorner()        { return this.frontCloseCorner; }
    public Vector2 getBackCloseCorner()         { return this.backCloseCorner; }
    public Vector2 getPoint()                   { return this.point; }
    public Vector2 getBackFarCorner()           { return this.backFarCorner; }
    public Vector2 getFrontFarCorner()          { return this.frontFarCorner; }
    public Vector2 getShoulder1()               { return this.shoulder1; }
    public Vector2 getShoulder2()               { return this.shoulder2; }
    public Vector2 getKnee1()                   { return this.knee1; }
    public Vector2 getKnee2()                   { return this.knee2; }
    public ArrayList<Object[]> getPitchData()   { return this.pitchData; }
}