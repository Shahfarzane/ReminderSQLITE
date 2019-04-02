package com.example.favoritemusic;

public class Todo {

    private int todolistId;
    private String todolistTitle;
    private String todolistContent;
    private String todolistDate;
    private int todolistCategoryId;
    private int categoryId;
    private String categoryName;
    private int todolistUrgent;


    public Todo() {
    }

    public Todo(int todolistId,int todolistUrgent, String todolistTitle, String todolistContent, String todolistDate, int todolistCategoryId, int todoCategoryId, int categoryId, String categoryName) {

        this.todolistId = todolistId;
        this.todolistTitle = todolistTitle;
        this.todolistContent = todolistContent;
        this.todolistDate = todolistDate;
        this.todolistCategoryId = todolistCategoryId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.todolistUrgent = todolistUrgent;
    }

    public int getTodolistUrgent() {
        return todolistUrgent;
    }
    public void setTodolistUrgent(int todolistUrgent) {
        this.todolistUrgent = todolistUrgent;
    }
    public int getTodolistId() {
        return todolistId;
    }
    public void setTodolistId(int todolistId) {
        this.todolistId = todolistId;
    }
    public String getTodolistTitle() {
        return todolistTitle;
    }
    public void setTodolistTitle(String todolistTitle) {
        this.todolistTitle = todolistTitle;
    }
    public String getTodolistContent() {
        return todolistContent;
    }
    public void setTodolistContent(String todolistContent) { this.todolistContent = todolistContent; }
    public String getTodolistDate() {
        return todolistDate;
    }
    public void setTodolistDate(String todolistDate) {
        this.todolistDate = todolistDate;
    }
    public int getTodolistCategoryId() {
        return todolistCategoryId;
    }
    public void setTodolistCategoryId(int todolistCategoryId) { this.todolistCategoryId = todolistCategoryId; }
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
