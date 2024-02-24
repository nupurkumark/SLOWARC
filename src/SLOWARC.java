public class SLOWARC extends IPublisher {
    
    private CSVReader reader;
    
    public SLOWARC() {}

    public SLOWARC(String filename) {
        this.reader = new CSVReader();
        this.reader.readFile(filename);
        // System.out.println(String.format("%s loaded.",filename));
    }

    public void run() {
        // Pitch determination stuff here
        StrikeZoneDetector ump = new StrikeZoneDetector(this.reader);
        boolean strike = ump.judgePitch();
        String message;
        if(strike){
            message = "Strike";
        }
        else {
            message = "Ball";
        }
        this.Notify(message);
        return;
    }
}