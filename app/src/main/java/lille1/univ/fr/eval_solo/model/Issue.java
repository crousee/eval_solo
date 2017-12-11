package lille1.univ.fr.eval_solo.model;

import com.orm.SugarRecord;

/**
 * Created by zhweng on 10/12/2017.
 */

public class Issue extends SugarRecord<Issue> {

    private String type;

    private String description;

    private String longitude;
    private String latitude;
    private String address;

    public Issue(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public Issue(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
