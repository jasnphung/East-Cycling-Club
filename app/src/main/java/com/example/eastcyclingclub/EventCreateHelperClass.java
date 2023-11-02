package com.example.eastcyclingclub;

public class EventCreateHelperClass {

    private String  age, pace, eventType, difficultyLevel;

    public EventCreateHelperClass(String eventName, String age, String pace, String eventType, String difficultyLevel) {
        this.age = age;
        this.pace = pace;
        this.eventType = eventType;
        this.difficultyLevel = difficultyLevel;
    }
    public String getAge() {
        return age;
    }
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
