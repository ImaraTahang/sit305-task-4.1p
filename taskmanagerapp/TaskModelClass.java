package com.example.taskmanagerapp;

public class TaskModelClass {
    Integer id;
    String task;
    String description;
    String date;

    public TaskModelClass(String task, String description, String date) {
        this.task = task;
        this.description = description;
        this.date = date;
    }

    public TaskModelClass(Integer id, String task, String description, String date) {
        this.id = id;
        this.task = task;
        this.description = description;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
