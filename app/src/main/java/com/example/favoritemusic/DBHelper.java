package com.example.favoritemusic;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.favoritemusic.Todo;
import java.util.ArrayList;
import java.util.List;



public class DBHelper extends SQLiteOpenHelper{


    private static final String LOGTAG = "Reminder";
    private static final String DATABASE_NAME = "ReminderDB.db";
    private static final int DATABASE_VERSION = 6;



    // Todolist table
    public static final String TABLE_REMINDER = "todolist";
    public static final String COLUMN_REMINDER_ID = "todolistId";
    public static final String COLUMN_TODOLIST_TITLE = "todolistTitle";
    public static final String COLUMN_TODOLIST_CONTENT = "todolistContent";
    public static final String COLUMN_TODOLIST_URGENT = "todolistPrio";
    public static final String COLUMN_TODOLIST_CATEGORYID = "todolistCategoryId";

    private static final String TABLE_REMINDER_CREATE =
            "CREATE TABLE " + TABLE_REMINDER + " (" +
                    COLUMN_REMINDER_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_TODOLIST_TITLE + " TEXT, " +
                    COLUMN_TODOLIST_CONTENT + " TEXT, " +
                    COLUMN_TODOLIST_URGENT + " INTEGER, " +
                    COLUMN_TODOLIST_CATEGORYID + " INTEGER" +
                    ")";

    // Category table
    public static final String TABLE_CATEGORY = "category";
    public static final String COLUMN_CATEGORY_ID = "categoryId";
    public static final String COLUMN_CATEGORY_NAME = "categoryName";
    private static final String TABLE_CATEGORY_CREATE =
            "CREATE TABLE " + TABLE_CATEGORY + " (" +
                    COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_CATEGORY_NAME + " TEXT" +
                    ")";


    // everytime this constructor calls the database will be created.
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // to create tables when the app runs
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(LOGTAG, "Oncreate");
        db.execSQL(TABLE_REMINDER_CREATE);
        db.execSQL(TABLE_CATEGORY_CREATE);

        db.execSQL("INSERT INTO category (categoryName) VALUES ('Urgent/School'), ('Others')");
        db.execSQL("INSERT INTO todolist (todolistTitle, todolistContent, todolistCategoryId) " +
                "VALUES ('Database Labb1', 'Make Some app in SqliteDB', 1), " +
                "('Movie Night', 'with XXX', 2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        Log.i(LOGTAG, "Database has been upgraded from " +
                oldVersion + " to " + newVersion);
        onCreate(db);
    }

    // Get all Todlolist items
    // Cursor is pointer . Sqlite presents data as a cursor in a tableformat.
    public List<Todo> getAllTodolist(){
        List<Todo> todolistItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(DBHelper.TABLE_REMINDER,null,null,null,null,null,null);
        Log.i(LOGTAG, "Count: Todolist items: " + c.getCount() + " rows");
        if(c.getCount() > 0){
            while(c.moveToNext()){
                Todo todo = new Todo();
                todo.setTodolistId(c.getInt(c.getColumnIndex(DBHelper.COLUMN_REMINDER_ID)));
                todo.setTodolistTitle(c.getString(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_TITLE)));
                todo.setTodolistContent(c.getString(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_CONTENT)));
                todo.setTodolistUrgent(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_URGENT)));

                todolistItems.add(todo);

                Log.i("Todolist items: ", todo.getTodolistId() + ", "
                        + todo.getTodolistTitle() + ", " + todo.getTodolistDate());
            }
        }
        return todolistItems;
    }




    public int getAmountOfTodos(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT COUNT(todolistId) AS total FROM todolist";
        Cursor c = db.rawQuery(query, null);
        c.moveToNext();
        int total = c.getInt(c.getColumnIndex("total"));


        return total;
    }

    public void deleteRow(Todo todo){
        SQLiteDatabase db = getWritableDatabase();

        db.delete("todolist", "todolistId" + " = " + todo.getTodolistId(), null);;
    }

    // Get all todolist items for a category with help of a innerjoin if urgent=true comes to a higher place in the list.

    public List<Todo> getAllTodolistCategory() {
        List<Todo> todolistItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " +
                "category INNER JOIN todolist ON " +
                "category.categoryId = todolist.todolistCategoryId " +
                "ORDER BY todolist.todolistPrio DESC;";

        Cursor c = db.rawQuery(query, null);

        Log.i(LOGTAG, "Count: Todolist items/Category " + c.getCount() + " rows");
        while(c.moveToNext()){
            Todo todo = new Todo();
            todo.setTodolistId(c.getInt(c.getColumnIndex(DBHelper.COLUMN_REMINDER_ID)));
            todo.setTodolistTitle(c.getString(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_TITLE)));
            todo.setTodolistContent(c.getString(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_CONTENT)));
            todo.setTodolistUrgent(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_URGENT)));

            todo.setCategoryId(c.getInt(c.getColumnIndex(DBHelper.COLUMN_CATEGORY_ID)));
            todo.setCategoryName(c.getString(c.getColumnIndex(DBHelper.COLUMN_CATEGORY_NAME)));
            todolistItems.add(todo);

            Log.i("items/Category:" , todo.getTodolistId() + ", "
                    + todo.getTodolistTitle() + ", " + todo.getCategoryName());
        }
        return todolistItems;
    }

// to insert a todolist in db
    public void addNoteToDB(String noteTitle,int urgent,int category){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("INSERT INTO todolist (todolistTitle,todolistPrio, todolistCategoryId) " +
                "VALUES ('"+noteTitle+ "'," + urgent +"," + category +");");
    }

    public void updatePrio(Todo todo){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("UPDATE todolist SET todolistPrio = "+todo.getTodolistUrgent()+" WHERE todolistId = "+todo.getTodolistId()+";");
    }

    public Todo getTodoById(int todoId){
        Todo todo = new Todo();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " +
                "category INNER JOIN todolist ON " +
                "category.categoryId = todolist.todolistCategoryId " +
                "WHERE todolist.todolistId = " + todoId + ";";
        Cursor c = db.rawQuery(query, null);

        c.moveToNext();
        todo.setTodolistId(c.getInt(c.getColumnIndex(DBHelper.COLUMN_REMINDER_ID)));
        todo.setTodolistTitle(c.getString(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_TITLE)));
        todo.setTodolistContent(c.getString(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_CONTENT)));
        todo.setTodolistUrgent(c.getInt(c.getColumnIndex(DBHelper.COLUMN_TODOLIST_URGENT)));

        todo.setCategoryId(c.getInt(c.getColumnIndex(DBHelper.COLUMN_CATEGORY_ID)));
        todo.setCategoryName(c.getString(c.getColumnIndex(DBHelper.COLUMN_CATEGORY_NAME)));


        Log.i("items/Category:" , todo.getTodolistId() + ", "
                + todo.getTodolistTitle() + ", " + todo.getCategoryName());
        return todo;
    }
}
