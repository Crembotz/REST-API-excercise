package entities;

import org.postgresql.geometric.PGpoint;
import org.springframework.web.bind.annotation.RequestBody;

public class Event {
    private String vName;
    private float vPowerLevel;
    private String pointString;
    private int[] cid;

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public float getvPowerLevel() {
        return vPowerLevel;
    }

    public void setvPowerLevel(float vPowerLevel) {
        this.vPowerLevel = vPowerLevel;
    }

    public String getPointString() {
        return pointString;
    }

    public void setPointString(String pointString) {
        this.pointString = pointString;
    }

    public int[] getCid() {
        return cid;
    }

    public void setCid(int[] cid) {
        this.cid = cid;
    }


    public Event(String vName, float vPowerLevel, String pointString, int[] cid) {
        this.vName = vName;
        this.vPowerLevel = vPowerLevel;
        this.pointString = pointString;
        this.cid = cid;
    }

    @Override
    public String toString() {
        String res = "vName: " + vName +
                ",\nvPowerLevel: " + vPowerLevel +
                ",\npointString: " + pointString +
                ",\ncid: ";
        for (int i = 0; i < cid.length; i++)
            res += cid[i] + ",";
        res = res.substring(0, res.length() - 1);
        return res;

    }


}
