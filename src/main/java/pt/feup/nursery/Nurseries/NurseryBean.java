package pt.feup.nursery.Nurseries;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class NurseryBean implements Serializable {

    private int id;
    @NotNull
    private String name;
    @NotNull
    private String specialty;
    private int beds;
    @NotNull
    private String location;
    private int responsible;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getResponsible() {
        return responsible;
    }

    public void setResponsible(int responsible) {
        this.responsible = responsible;
    }
}
