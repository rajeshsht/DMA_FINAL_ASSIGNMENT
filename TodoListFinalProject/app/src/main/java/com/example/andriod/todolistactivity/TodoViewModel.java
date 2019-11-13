package com.example.andriod.todolistactivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.andriod.todolistactivity.database.Todo;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private TodoRepository repository;
    private LiveData<List<Todo>> allTodos;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        repository = new TodoRepository(application);
        allTodos = repository.getAllTodos();
    }

    public void insert(Todo todo){
        repository.insert(todo);
    }

    public void update(Todo todo){
        repository.update(todo);
    }

    public void delete(Todo todo){
        repository.delete(todo);
    }

    public void deleteAllTodos(){
        repository.deleteAllTodos();
    }

    public LiveData<List<Todo>> getAllTodos(){
        return allTodos;
    }

}