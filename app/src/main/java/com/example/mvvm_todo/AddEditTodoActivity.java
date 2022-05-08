package com.example.mvvm_todo;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditTodoActivity extends AppCompatActivity {
    //intent EXTRA case
    //Used to send the data to other classes such as main activity
    public static final String EXTRA_ID =
            "com.example.mvvm_todo.EXTRA_ID";//primary key
    public static final String EXTRA_TITLE =
            "com.example.mvvm_todo.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.mvvm_todo.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.example.mvvm_todo.EXTRA_PRIORITY";
    public static final String EXTRA_COMPLETED =
            "com.example.mvvm_todo.EXTRA_COMPLETED";

    public static final String TAG = "AddEditTodoActivity";

    //variables for the views
    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;
    //private CheckBox checkBoxCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        //Log the start of the onCreate() method
        Log.d(TAG, "--------");
        Log.d(TAG, "onCreate");

        //assign views by id
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
        //checkBoxCompleted = findViewById(R.id.check_box);

        //set priority NumberPicker values 1-10
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        //set the default completed to false
        //checkBoxCompleted.setActivated(false);

        //initialize support action bar dot
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();// hold all the data

        //if intent contains ID which only happens if ID is included in intent when updating todo_object
        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Todo");
            //method to update todo_objects selected if ID is provided
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
            //checkBoxCompleted.setActivated(intent.getBooleanExtra(EXTRA_COMPLETED, false));
        }else {
            setTitle("Add ToDo");//else change title to add todo_object
        }
    }

    //saveTodo method
    private void saveTodo(){
        //create variables for Todo_items components
        //sends input to the main activity
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();
        //boolean completed = checkBoxCompleted.isActivated();

        //if fields empty do not accept Todo_input
        if (title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Please insert a title & description", Toast.LENGTH_SHORT).show();
            return;
        }

        //initialize new intent to send data when activity is started
        Intent data = new Intent();
        //always parse variables to the EXTRA cases
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);
        //data.putExtra(EXTRA_COMPLETED, completed);

        //method to check the todo_object to be updated if it has a valid ID
        int id = getIntent().getIntExtra(EXTRA_ID, -1);//-1 is invalid
        if(id != -1){//only puts into intent if its not -1
            data.putExtra(EXTRA_ID, id);
        }
        //if result ok pass data and finish activity
        //parse data intent
        setResult(RESULT_OK, data);
        finish();
    }


    ///////////////////////////////////////////////////////////
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

    //////////////////////////////////////////////////////////////


    @Override //method for assigning add_todo_menu.xml
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_todo_menu, menu);
        return true; // display menu
    }

    @Override //method for assigning functions to add_todo_menu.xml
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.save_todo://when clicked call save method
                saveTodo(); //save method
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}