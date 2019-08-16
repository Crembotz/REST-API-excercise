package entities;

import org.postgresql.geometric.PGpolygon;
import types.PowerCategory;

public class Hero {
    private int id;
    private String fName, lName;
    private float luck, power_level;
    private PGpolygon area;
    private String sPower;
    private PowerCategory.Categories power_category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public float getLuck() {
        return luck;
    }

    public void setLuck(float luck) {
        this.luck = luck;
    }

    public float getPower_level() {
        return power_level;
    }

    public void setPower_level(float power_level) {
        this.power_level = power_level;
    }

    public PGpolygon getArea() {
        return area;
    }

    public void setArea(PGpolygon area) {
        this.area = area;
    }

    public String getsPower() {
        return sPower;
    }

    public void setsPower(String sPower) {
        this.sPower = sPower;
    }

    public PowerCategory.Categories getPower_category() {
        return power_category;
    }

    public void setPower_category(PowerCategory.Categories power_category) {
        this.power_category = power_category;
    }


    public Hero(int id, String fName, String lName, float luck, float power_level, PGpolygon area, String sPower, PowerCategory.Categories power_category) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.luck = luck;
        this.power_level = power_level;
        this.area = area;
        this.sPower = sPower;
        this.power_category = power_category;
    }

    public Hero(String fName, String lName, float luck, float power_level, PGpolygon area, String sPower, PowerCategory.Categories power_category) {
        this.fName = fName;
        this.lName = lName;
        this.luck = luck;
        this.power_level = power_level;
        this.area = area;
        this.sPower = sPower;
        this.power_category = power_category;
    }

    public Hero() {

    }

    @Override
    public boolean equals(Object object) {
        Hero other = (Hero) object;
        return id == other.id && fName.equals(other.fName) && lName.equals(other.lName)
                && luck == other.luck && power_level == other.power_level
                && area.equals(other.area) && sPower.equals(other.sPower)
                && power_category.equals(other.power_category);
    }


    @Override
    public String toString() {
        return "hid: " + id
                + "\nfname: " + fName
                + "\nlname: " + lName
                + "\nluck: " + luck
                + "\npower level: " + power_level
                + "\narea: " + area.toString()
                + "\nsuper power: " + sPower
                + "\npower category: " + power_category;
    }

}
