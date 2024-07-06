package View;

public abstract class Command {
    private String key;
    private String desc;

    public Command(String k, String d){
        key = k;
        desc = d;
    }

    public abstract void execute();

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
