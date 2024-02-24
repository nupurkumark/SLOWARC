public class TerminalSubscriber implements ISubscriber {
    public void Notify(String message) {
        System.out.println(message);
    }
}
