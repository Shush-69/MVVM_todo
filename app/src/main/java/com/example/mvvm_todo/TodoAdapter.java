package com.example.mvvm_todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm_todo.data.Todo;

import java.util.ArrayList;
import java.util.List;

//pass TodoHolder to Recyclerview to use the specific view holder
//pass list to super class to the list adapter
public class TodoAdapter extends ListAdapter<Todo, TodoAdapter.TodoHolder> {
    private OnItemClickListener listener; //declare click listener

    //constructor
    public TodoAdapter() {
        super(DIFF_CALLBACK);
    }//define directly in todo_adapter class


    private static final DiffUtil.ItemCallback<Todo> DIFF_CALLBACK = new DiffUtil.ItemCallback<Todo>() {
        @Override
        public boolean areItemsTheSame(Todo oldItem, Todo newItem) { //comparison logic
            return oldItem.getId() == newItem.getId();//if old items are same as new items we know they are same entry
        }

        @Override //if all three comparisons are true then this method returns true
        //if variables are changed returns false
        public boolean areContentsTheSame(Todo oldItem, Todo newItem) {//comparison logic
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority() &&
                    oldItem.getCompleted() == newItem.getCompleted();//compare all values separately
            // if all three comparisons are true, method returns true
            //else if changes made, method returns false and adapter updates todo_item
        }
    };

    ///////////////////////////////////////////////
    @NonNull
    @Override //method to override RecyclerView Adapter
    // create and return a TodoHolder with layout for the singular items within Recycler view
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())//get context within main activity
                .inflate(R.layout.todo_item, parent, false);
        return new TodoHolder(itemView);//Return new TodoHolder and item View
    }

    @Override //method to override RecyclerView Adapter
    //references Todo_object and the position
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        //parse data from single Todo_object into views of TodoHolder
        Todo currentTodo = getItem(position);
        holder.textViewTitle.setText(currentTodo.getTitle());
        holder.textViewDescription.setText(currentTodo.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentTodo.getPriority()));
        holder.checkBoxCompleted.setActivated(currentTodo.getCompleted());

    }

    /////////////////////////////////////////////
    //swipe to delete method
    public Todo getTodoAt(int position){
        return getItem(position);
    }//return Todo_object at this position


    /////////////////////////////////////////////
    //holds the views in single RecyclerView items
    class TodoHolder extends RecyclerView.ViewHolder{
        //variables for different views
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;
        private CheckBox checkBoxCompleted;


        //TodoHolder constructor
        public TodoHolder(@NonNull View itemView) {
            super(itemView);
            //Assign text views
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
            checkBoxCompleted = itemView.findViewById(R.id.check_box);

            ///////////////////////////////////////////////
            itemView.setOnClickListener(new View.OnClickListener() {//new click listener declared on TodoHolder
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();//gets position of click
                    if (listener != null && position != RecyclerView.NO_POSITION) {// check if item in valid adapter position
                        listener.onItemClick(getItem(position));//call item click on listener & passes position
                    }
                    }
            });

        }
    }

    //////////////////////////////////////
    //Create an interface
    public interface OnItemClickListener {
        void onItemClick(Todo todo); //declare method onItemClick
    }

    //Declare listener method to listen for clicks on item
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;//catches the click


    }
}
