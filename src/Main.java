
public class Main {

    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Provide the name of the pitch data file");
            System.out.println("$ java -jar SlowArc.jar <dir>/datafile.csv");
            return;
        }
        SLOWARC app = new SLOWARC(args[0]);
        app.Add(new TerminalSubscriber());
        app.run();
        return;
    }
}