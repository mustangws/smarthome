package smarthome.com.smarthome.Main;

public class Measurement {

    @Expose
    private String value;
    @Expose
    private String time;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
