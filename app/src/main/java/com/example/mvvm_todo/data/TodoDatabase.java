package com.example.mvvm_todo.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Todo.class}, version = 2)
public abstract class TodoDatabase extends RoomDatabase {

    //initialization of instance used within the entire application
    private static TodoDatabase instance;

    //used to access Dao
    public abstract TodoDao todoDao();

    //Create single database instance
    public static synchronized TodoDatabase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TodoDatabase.class, "todo_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)//execute onCreate method and populate data table
                    .build();
        }
        return instance; //not null return database instance
    }

    ///////////////////////////////////////
    //access static onCreate method
    //This method executes the pre set data to populate the table
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();//passing instance variable
        }
    };

    ///////////////////////////////////////
    //Class to populate the data table
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TodoDao todoDao;

        private PopulateDbAsyncTask(TodoDatabase db){
            todoDao = db.todoDao();
        }

        @Override //Method for Pre set data to populate table
        protected Void doInBackground(Void... voids) {
            todoDao.insert(new Todo("Title 1", "Description 1", 1, false));
            todoDao.insert(new Todo("Title 2", "Description 2", 2, false));
            todoDao.insert(new Todo("Title 3", "Description 3", 3, true));
            return null;
        }
    }
}
