package com.example.mvvm_todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mvvm_todo.data.Todo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_TODO_REQUEST = 1;//code to add a todo_object
    public static final int EDIT_TODO_REQUEST = 2;//code to edit a todo_object

    private static final String TAG = "MainActivity";//initiate TAG variable for lifecycle

    private TodoViewModel todoViewModel;//create member variable for view model

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate");//OnCreate Activity lifecycle

        //Swipe-able items
        FloatingActionButton buttonAddTodo = findViewById(R.id.button_add_todo);
        buttonAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //create new intent to pass new content
                Intent intent = new Intent(MainActivity.this, AddEditTodoActivity.class);
                startActivityForResult(intent, ADD_TODO_REQUEST);//send back values
            }
        });

        //Reference Recycler view found via ID
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//displays single items below each other
        recyclerView.setHasFixedSize(true);

        //create new TodoAdapter
        TodoAdapter adapter = new TodoAdapter();
        recyclerView.setAdapter(adapter);

        //view model life cycle
        //Destroys view model when activity is finished
        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        //returns live data
        todoViewModel.getAllTodos().observe(this, new Observer<List<Todo>>() {
            @Override //OnChanged call back method
            public void onChanged(List<Todo> todos) {
                adapter.submitList(todos);
            }//submit new list of todos to adapter
        });

        //////////////////////////////////////////////////////////
        //Swipe to delete on RecyclerView
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {//supports left and right swipes
            @Override
            //drag and drop functionality (not used)
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; //if false do not delete
            }

            @Override //gets todo object & adapter position
            //on swiping
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                todoViewModel.delete(adapter.getTodoAt(viewHolder.getAdapterPosition())); //passes the todo_position to delete method
                Toast.makeText(MainActivity.this, "Todo Deleted", Toast.LENGTH_SHORT).show();//print if sucessful
            }
        }).attachToRecyclerView(recyclerView);

        //////////////////////////////////////////////////////////////

        adapter.setOnItemClickListener(new TodoAdapter.OnItemClickListener() {//calls on method setOnItemClickListener
            @Override
            public void onItemClick(Todo todo) {//handles the click activity
                Intent intent = new Intent(MainActivity.this, AddEditTodoActivity.class);
                intent.putExtra(AddEditTodoActivity.EXTRA_ID, todo.getId());//primary key to identify Todo_object to update
                intent.putExtra(AddEditTodoActivity.EXTRA_TITLE, todo.getTitle());
                intent.putExtra(AddEditTodoActivity.EXTRA_DESCRIPTION, todo.getDescription());
                intent.putExtra(AddEditTodoActivity.EXTRA_PRIORITY, todo.getPriority());
                intent.putExtra(AddEditTodoActivity.EXTRA_COMPLETED, todo.getCompleted());
                startActivityForResult(intent, EDIT_TODO_REQUEST);
            }
        });
    }

    ///////////////////////////////////////////////////////////

    //update recycler view with result
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if result ok retrieve EXTRA case
        if (requestCode == ADD_TODO_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditTodoActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTodoActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditTodoActivity.EXTRA_PRIORITY, 1);
            boolean completed = data.getBooleanExtra(AddEditTodoActivity.EXTRA_COMPLETED, false);


            //Create a new Todo & insert Todo_data into database
            Todo todo = new Todo(title, description, priority, false);
            todoViewModel.insert(todo);

            Toast.makeText(this, "Todo Saved", Toast.LENGTH_SHORT).show();
        }
        //////////////////////////////////////////////////////////////
        //else if edit_todo_request ok set ID on Todo_Object
        else if (requestCode == EDIT_TODO_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditTodoActivity.EXTRA_ID, -1);

            if (id == -1) { //if resultcode = RESULT_CANCELLED do not save
                Toast.makeText(this, "Note cant be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            //If sucessful extract the values below
            String title = data.getStringExtra(AddEditTodoActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTodoActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditTodoActivity.EXTRA_PRIORITY, 1);
            boolean completed = data.getBooleanExtra(AddEditTodoActivity.EXTRA_COMPLETED, false);

            Todo todo = new Todo(title, description, priority, false); //creates new object
            todo.setId(id);//set the id of the todo_object
            todoViewModel.update(todo);//method to update view model

            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show(); //sucessful
        } else {
            Toast.makeText(this, "Todo Not saved", Toast.LENGTH_SHORT).show();// unsucessful
        }
    }

    //////////////////////////////////////////////////////////////////
    //LifeCycle

    //onStart log
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart");
    }

    //onRestart log
    @Override
    public void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    //onResume log
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume");
    }

    //onPause log
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause");
    }

    //onStop log
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop");
    }

    //onDestory log
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestory");
    }



    ///////////////////////////////////////////////////////////////
    @Override //method for assigning main_menu.xml
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;//display menu
    }
    //developer notes
    //write left swipe to delete
    //right swipe to complete
    //add a boolean for database complete

    @Override //method for assigning delete all todo_function to main_menu.xml
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_todos://when clicked call delete_all_todos method
                todoViewModel.deleteAllTodos();//deleteAllTodos method
                Toast.makeText(this, "All Todos Deleted", Toast.LENGTH_SHORT).show();//sucessful
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

