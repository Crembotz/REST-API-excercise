package entities;

import org.postgresql.geometric.PGpoint;

public class Battle {
    private int id;
    private String vName;
    private float vPower;
    private boolean win;
    private PGpoint location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public float getvPower() {
        return vPower;
    }

    public void setvPower(float vPower) {
        this.vPower = vPower;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public PGpoint getLocation() {
        return location;
    }

    public void setLocation(PGpoint location) {
        this.location = location;
    }


    public Battle(int id, String vName, float vPower, boolean win, PGpoint location) {
        this.id = id;
        this.vName = vName;
        this.vPower = vPower;
        this.win = win;
        this.location = location;
    }

    public Battle(String vName, float vPower, boolean win, PGpoint location) {
        this.vName = vName;
        this.vPower = vPower;
        this.win = win;
        this.location = location;
    }

    @Override
    public String toString() {
        return "bid: " + id
                + "\nvName: " + vName
                + "\nvPower: " + vPower
                + "\nwin: " + win
                + "\nlocation: " + location;
    }

    @Override
    public boolean equals(Object other) {
        return id == ((Battle) other).id;
    }


}
