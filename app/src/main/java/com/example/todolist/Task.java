package com.example.todolist;

public class Task {
    int ID;
    String name;

    public Task(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }
}
