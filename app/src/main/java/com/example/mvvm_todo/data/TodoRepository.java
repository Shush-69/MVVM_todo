package com.example.mvvm_todo.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoRepository {
    private TodoDao todoDao;
    private LiveData<List<Todo>> allTodos;//all the todo_objects

    //Create database instance
    public TodoRepository(Application application) {
        TodoDatabase database = TodoDatabase.getInstance(application);
        todoDao = database.todoDao();
        allTodos = todoDao.getAllTodo();
    }

    ////////////////////////////////////////////
    //Apis exectuing methods to do Async tasks
    public void insert(Todo todo){
        new InsertTodoAsyncTask(todoDao).execute(todo);
    }

    public void update(Todo todo){
        new UpdateTodoAsyncTask(todoDao).execute(todo);
    }

    public void delete(Todo todo){
        new DeleteTodoAsyncTask(todoDao).execute(todo);
    }

    public void deleteAllTodos(){
        new DeleteAllTodosAsyncTask(todoDao).execute();
    }

    public LiveData<List<Todo>> getAllTodos(){
        return allTodos;
    }



    /////////////////////////////////////////////
    //creating repository methods for the application to do async tasks within database
    private static class InsertTodoAsyncTask extends android.os.AsyncTask<Todo, Void, Void> {
        private TodoDao todoDao;//member variable

        private InsertTodoAsyncTask(TodoDao todoDao){
            this.todoDao = todoDao;
        }//constructor passing TodoDao

        @Override
        protected Void doInBackground(Todo... todos){
            todoDao.insert(todos[0]);
            return null;
        }
    }

    private static class UpdateTodoAsyncTask extends android.os.AsyncTask<Todo, Void, Void> {
        private TodoDao todoDao;

        //constructor passing TodoDao
        private UpdateTodoAsyncTask(TodoDao todoDao){
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos){
            todoDao.update(todos[0]);
            return null;
        }
    }

    private static class DeleteTodoAsyncTask extends android.os.AsyncTask<Todo, Void, Void> {
        private TodoDao todoDao;

        //constructor passing TodoDao
        private DeleteTodoAsyncTask(TodoDao todoDao){
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos){
            todoDao.delete(todos[0]);
            return null;
        }
    }

    private static class DeleteAllTodosAsyncTask extends AsyncTask<Void, Void, Void>{
        private TodoDao todoDao;

        //constructor passing TodoDao
        private DeleteAllTodosAsyncTask(TodoDao todoDao){
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Void... voids){
            todoDao.deleteAllTodos();
            return null;
        }
    }


}
