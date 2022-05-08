package com.example.mvvm_todo.data;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;

import java.util.List;

//Database SQLlite table initialization
@Entity(tableName = "todo_table")
public class Todo {

    //unique Id used as primary key
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "priority")
    private int priority;

   @ColumnInfo(name = "completed")
    private boolean completed = false;
    ///////////////////////////////////

    //constructor
    public Todo(String title, String description, int priority, boolean completed) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.completed = completed;
    }
    ///////////////////////////////////

    //setter method
    public void setId(int id) {
        this.id = id;
    }

    public void setCompleted(boolean completed) {this.completed = completed;}

    //getter methods
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public boolean getCompleted() {
        return completed;
    }


}