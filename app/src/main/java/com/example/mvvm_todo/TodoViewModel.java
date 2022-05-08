package com.example.mvvm_todo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mvvm_todo.data.Todo;
import com.example.mvvm_todo.data.TodoRepository;

import java.util.List;


public class TodoViewModel extends AndroidViewModel {
    private TodoRepository repository; //access todo repository class
    private LiveData<List<Todo>> allTodos;//access database table of list of todo within class

    //extends entry model and pass the application to the database
    public TodoViewModel(Application application){
        super(application);//constructor
        repository = new TodoRepository(application);//assign repository to new Repository
        allTodos = repository.getAllTodos();
    }
    /////////////////////////////////////
    //rebel methods for database operation from repository
    public void insert(Todo todo){
        repository.insert(todo);
    }

    public void update(Todo todo){
        repository.update(todo);
    }

    public void delete(Todo todo){
        repository.delete(todo);
    }

    public void deleteAllTodos() {
        repository.deleteAllTodos();
    }

    //returns live data off list of todos
    public LiveData<List<Todo>> getAllTodos() {
        return allTodos;
    }
}
