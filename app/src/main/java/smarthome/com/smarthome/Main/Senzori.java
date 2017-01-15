package smarthome.com.smarthome.Main;

public class Senzori {

@Expose
private Integer id;
@Expose
private String type;
@Expose
private List<Measurement> measurements = null;

public Integer getId() {
    return id;
}

public void setId(Integer id) {
    this.id = id;
}

public String getType() {
    return type;
}

public void setType(String type) {
    this.type = type;
}

public List<Measurement> getMeasurements() {
    return measurements;
}

public void setMeasurements(List<Measurement> measurements) {
    this.measurements = measurements;
}
}
