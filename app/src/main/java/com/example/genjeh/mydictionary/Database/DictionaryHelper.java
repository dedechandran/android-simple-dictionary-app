package com.example.genjeh.mydictionary.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;


import com.example.genjeh.mydictionary.ModelData.Dictionary;

import java.util.ArrayList;

public class DictionaryHelper {
    private SQLiteDatabase database;
    private Context context;

    public DictionaryHelper(Context context) {
        this.context = context;
    }

    public DictionaryHelper open() throws SQLException {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        database.close();
    }

    public ArrayList<Dictionary> query(String word, String table_name, String column_id, String column_word, String column_desc) {
        ArrayList<Dictionary> dictionaries = new ArrayList<>();
        String[] projection = {column_id, column_word, column_desc};
        String selection = column_word + " LIKE ?";
        String[] selectionArgs = { word + "%"};
        Cursor cursor = database.query(table_name, projection, selection, selectionArgs, null, null, null);
        cursor.moveToFirst();
        Dictionary dictionary;
        if (cursor.getCount() > 0) {
            do {
                dictionary = new Dictionary();
                dictionary.setDictID(cursor.getInt(cursor.getColumnIndexOrThrow(column_id)));
                dictionary.setDictWord(cursor.getString(cursor.getColumnIndexOrThrow(column_word)));
                dictionary.setDictDesc(cursor.getString(cursor.getColumnIndexOrThrow(column_desc)));
                dictionaries.add(dictionary);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();



        return dictionaries;
    }

    public int delete(int id, String table_name, String column_id) {
        return database.delete(table_name, column_id + " = '" + id + "'", null);
    }


    public void beginTransaction() {
        database.beginTransaction();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public void setTransactionSuccessfull() {
        database.setTransactionSuccessful();
    }

    public void insertTransaction(Dictionary dictionary,String table_name,String column_word,String column_desc) {
        String sql = String.format("INSERT INTO %s" +
                " (%s, %s)"
                + " VALUES (?, ?);",table_name,column_word,column_desc);
        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1,dictionary.getDictWord());
        statement.bindString(2,dictionary.getDictDesc());
        statement.execute();
        statement.clearBindings();
    }


}
