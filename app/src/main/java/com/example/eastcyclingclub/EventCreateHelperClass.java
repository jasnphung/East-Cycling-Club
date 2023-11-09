package com.example.eastcyclingclub;

public class EventCreateHelperClass {

    private String  eventName,age, pace, eventType, difficultyLevel;

    public EventCreateHelperClass(String eventName, String age, String pace, String eventType, String difficultyLevel) {
        this.eventName = eventName;
        this.age = age;
        this.pace = pace;
        this.eventType = eventType;
        this.difficultyLevel = difficultyLevel;
    }

    public String getAge() {
        return age;
    }
    public String getEventName(){return eventName;}
    public void setEventName(String eventName){this.eventName = eventName;}
    public void setAge(String age) {
        this.age = age;
    }
    public String getPace() {
        return pace;
    }
    public void setPace(String pace) {
        this.pace = pace;
    }
    public String getEventType() {
        return eventType;
    }
    public void setEventType(String type) {
        this.eventType = type;
    }
    public String getDifficultyLevel() {
        return difficultyLevel;
    }
    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }



}
