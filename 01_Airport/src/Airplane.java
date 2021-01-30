public class Airplane {
    private static int staticID = 0;
    private final int incomingTime, id;
    public Airplane(int incomingTime) {
        this.incomingTime = incomingTime;
        id = ++staticID;
    }
    public int waitingTime(int time) { return time - incomingTime; }

    public int id() {return id;}
}
