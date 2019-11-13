package com.example.andriod.todolistactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.andriod.todolistactivity.database.Todo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_TODO_REQUEST = 1;
    public static final int EDIT_TODO_REQUEST = 2;

    private TodoViewModel todoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddTodo = findViewById(R.id.fab_addbtn);
        buttonAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
                startActivityForResult(intent, ADD_TODO_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TodoListAdapter adapter = new TodoListAdapter();
        recyclerView.setAdapter(adapter);

        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        todoViewModel.getAllTodos().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable List<Todo> todos) {
                //update our RecyclerView
//                Toast.makeText(MainActivity.this,
//////                        "OnChanged", Toast.LENGTH_LONG).show();
                adapter.submitList(todos);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                todoViewModel.delete(adapter.getTodoAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Todo deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TodoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Todo todo) {
                Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
                intent.putExtra(AddTodoActivity.EXTRA_ID, todo.getId());
                intent.putExtra(AddTodoActivity.EXTRA_TITLE, todo.getName());
                intent.putExtra(AddTodoActivity.EXTRA_DESCRIPTION, todo.getDescription());
                intent.putExtra(AddTodoActivity.EXTRA_DATE, todo.getDate());
                intent.putExtra(AddTodoActivity.EXTRA_TIME, todo.getTime());
                intent.putExtra(AddTodoActivity.EXTRA_PRIORITY, todo.getPriority());
                startActivityForResult(intent, EDIT_TODO_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TODO_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddTodoActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddTodoActivity.EXTRA_DESCRIPTION);
            String date = data.getStringExtra(AddTodoActivity.EXTRA_DATE);
            String time = data.getStringExtra(AddTodoActivity.EXTRA_TIME);
            int priority = data.getIntExtra(AddTodoActivity.EXTRA_PRIORITY, 1);

            Todo todo = new Todo(title, description, date, time, priority);

            todoViewModel.insert(todo);

            Toast.makeText(this, "Todo activity saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_TODO_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddTodoActivity.EXTRA_ID, -1);

            if (id == -1){
                Toast.makeText(this, "Todo can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddTodoActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddTodoActivity.EXTRA_DESCRIPTION);
            String date = data.getStringExtra(AddTodoActivity.EXTRA_DATE);
            String time = data.getStringExtra(AddTodoActivity.EXTRA_TIME);
            int priority = data.getIntExtra(AddTodoActivity.EXTRA_PRIORITY, 1);

            Todo todo = new Todo(title, description, date, time, priority);
            todo.setId(id);

            todoViewModel.update(todo);

            Toast.makeText(this, "Todo updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Todo not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_todos:
                todoViewModel.deleteAllTodos();
                Toast.makeText(this, "Clearing all todos from the list...", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
