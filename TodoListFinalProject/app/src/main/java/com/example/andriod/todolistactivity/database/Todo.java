package com.example.andriod.todolistactivity.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_table")
public class Todo {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String description;

    private String date;

    private String time;

    private int priority;


    public Todo(String name, String description, String date, String time, int priority) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
