package com.example.favoritemusic;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<Todo> todoList;
    private LayoutInflater inflater;


    public OrderAdapter(Context context, List<Todo> todoList) {
        if (todoList == null)
            this.todoList = new ArrayList<>();
        else
            this.todoList = todoList;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return todoList.size();
    }

    @Override
    public Object getItem(int position) {
        return todoList.get(position);
    }

    public long getItemId(int position) {
        return todoList.get(position).getTodolistId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null)
            view = inflater.inflate(R.layout.items, parent, false);
        else
            view = convertView;

        int urgent = todoList.get(position).getTodolistUrgent();
        if(urgent != 1)
            view.setBackgroundColor(Color.GREEN);
        else
            view.setBackgroundColor(Color.RED);


        TextView textTodoTitle = view.findViewById(R.id.todoText);
        TextView textTodoKategori = view.findViewById(R.id.category);
        textTodoTitle.setText(todoList.get(position).getTodolistTitle() );
        textTodoKategori.setText(todoList.get(position).getCategoryName());

        return view;
    }

 //inserts  a new todolist
    public void setTodoList(List<Todo> todoList) {
        if (todoList == null)
            this.todoList = new ArrayList<>();
        else
            this.todoList = todoList;
        notifyDataSetChanged();
    }


    public void removeTodo(Todo todo) {
        todoList.remove(todo);
        notifyDataSetChanged();
    }
}
