package com.example.andriod.todolistactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.andriod.todolistactivity.database.Todo;

public class TodoListAdapter extends ListAdapter<Todo, TodoListAdapter.TodoListHolder> {

    //private List<Todo> todos = new ArrayList<>();
    private OnItemClickListener listener;

    public TodoListAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Todo> DIFF_CALLBACK = new DiffUtil.ItemCallback<Todo>() {
        @Override
        public boolean areItemsTheSame(@NonNull Todo todo, @NonNull Todo t1) {
            return todo.getId() == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Todo todo, @NonNull Todo t1) {
            return todo.getName().equals(t1.getName()) && todo.getDescription().equals(t1.getDescription())
                    && todo.getDate().equals(t1.getDate()) && todo.getTime().equals(t1.getTime()) &&
                    todo.getPriority() == t1.getPriority();
        }
    };

    @NonNull
    @Override
    public TodoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent, false);
        return new TodoListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListHolder todoListHolder, int i) {
        Todo currentTodo = getItem(i);
        todoListHolder.textViewTitle.setText(currentTodo.getName());
        todoListHolder.textViewDescription.setText(currentTodo.getDescription());
        todoListHolder.textViewDate.setText(currentTodo.getDate());
        todoListHolder.textViewTime.setText(currentTodo.getTime());
        todoListHolder.textViewPriority.setText(String.valueOf(currentTodo.getPriority()));
    }


    public Todo getTodoAt(int position) {
        return getItem(position);
    }

    class TodoListHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDate;
        private TextView textViewTime;
        private TextView textViewPriority;

        public TodoListHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewTime = itemView.findViewById(R.id.text_view_time);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Todo todo);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
