package com.example.eastcyclingclub;

public class EventListHelperClass {
    String name, difficultyOnOff, minimumAgeOnOff, paceOnOff, routeDetailsOnOff;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getDifficulty() {return difficultyOnOff;}
    public void setDifficulty(String difficultyOnOff) {this.difficultyOnOff = difficultyOnOff;}

    public String getMinimumAge() {return minimumAgeOnOff;}
    public void setMinimumAge(String minimumAgeOnOff) {this.minimumAgeOnOff = minimumAgeOnOff;}

    public String getPace() {return paceOnOff;}
    public void setPace(String paceOnOff) {this.paceOnOff = paceOnOff;}

    public String getRouteDetails() {return routeDetailsOnOff;}
    public void setRouteDetails(String routeDetailsOnOff) {this.routeDetailsOnOff = routeDetailsOnOff;}

    public EventListHelperClass(String name, String difficultyOnOff, String minimumAgeOnOff, String paceOnOff, String routeDetailsOnOff) {
        this.name = name;
        this.difficultyOnOff = difficultyOnOff;
        this.minimumAgeOnOff = minimumAgeOnOff;
        this.paceOnOff = paceOnOff;
        this.routeDetailsOnOff = routeDetailsOnOff;
    }
    public EventListHelperClass() {}
}
