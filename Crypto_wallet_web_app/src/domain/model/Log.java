package domain.model;

public class Log {
    private int id;
    private static int tId=0;
    private String logTime, activity;

    public Log(String logTime, String activity){
        setId();
        setLogTime(logTime);
        setActivity(activity);
    }

    public int getId() {
        return id;
    }

    /* Set id and auto increment */
    public void setId() {
        this.id = tId;
        tId++;
    }

    /* Warning incorrect - getter is used by jsp page */
    public String getLogTime() { return logTime; }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    /* Warning incorrect - getter is used by jsp page */
    public String getActivity() { return activity; }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
