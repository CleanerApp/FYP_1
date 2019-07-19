package sg.edu.rp.c346.fyp_1;

import java.io.Serializable;

public class Service implements Serializable {
    String name;
    String detail;
    String cost;
    String cleaner;

    public Service(String name, String detail, String cost, String cleaner) {
        this.name = name;
        this.detail = detail;
        this.cost = cost;
        this.cleaner = cleaner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCleaner() {
        return cleaner;
    }

    public void setCleaner(String cleaner) {
        this.cleaner = cleaner;
    }
}
