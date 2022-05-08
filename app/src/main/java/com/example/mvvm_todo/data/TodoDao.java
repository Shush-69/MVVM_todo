package com.example.mvvm_todo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao //Data Access Objects
public interface TodoDao {

    @Insert
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("DELETE FROM todo_table")
    void deleteAllTodos();

    @Query("SELECT * FROM todo_table ORDER BY priority DESC")
    LiveData<List<Todo>> getAllTodo();
    //return a list of all todos ordered by priority
}
