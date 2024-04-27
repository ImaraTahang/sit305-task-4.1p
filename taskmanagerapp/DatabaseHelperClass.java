package com.example.taskmanagerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperClass extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "task_database";
    private static final String TABLE_NAME = "TASK";
    public static final String ID = "id";
    public static final String TASK = "task";
    public static final String DESC = "description";
    public static final String DATE = "date";
    private SQLiteDatabase sqLiteDatabase;

    ////creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME +"("+ID+
            " INTEGER PRIMARY KEY AUTOINCREMENT," + TASK + " TEXT NOT NULL,"+DESC+" TEXT NOT NULL,"+ DATE + " TEXT NOT NULL);";
    //Constructor
    public DatabaseHelperClass (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void addTask(TaskModelClass taskModelClass){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperClass.TASK, taskModelClass.getTask());
        contentValues.put(DatabaseHelperClass.DESC, taskModelClass.getDescription());
        contentValues.put(DatabaseHelperClass.DATE, taskModelClass.getDate());
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert(DatabaseHelperClass.TABLE_NAME, null,contentValues);
    }

    public List<TaskModelClass> getTaskList(){
        String sql = "select * from " + TABLE_NAME;
        sqLiteDatabase = this.getReadableDatabase();
        List<TaskModelClass> storeTask = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String task = cursor.getString(1);
                String desc = cursor.getString(2);
                String date = cursor.getString(3);
                storeTask.add(new TaskModelClass(id,task,desc,date));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeTask;
    }

    public void updateTask(TaskModelClass taskModelClass){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelperClass.TASK,taskModelClass.getTask());
        contentValues.put(DatabaseHelperClass.DESC,taskModelClass.getDescription());
        contentValues.put(DatabaseHelperClass.DATE,taskModelClass.getDate());
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.update(TABLE_NAME,contentValues, ID + " = ?", new String[]
                {String.valueOf(taskModelClass.getId())});
    }
    public void deleteTask(int id){
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, ID + " = ? ", new String[]
                {String.valueOf(id)});
    }
}
