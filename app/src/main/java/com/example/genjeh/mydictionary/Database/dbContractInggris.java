package com.example.genjeh.mydictionary.Database;

import android.provider.BaseColumns;

public class dbContractInggris {
    public static final String TABLE_NAME = "dict_inggris";
    static String CREATE_TABLE = String.format("CREATE TABLE %s" +
            " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL);", TABLE_NAME, dictColumns._ID, dictColumns.WORD, dictColumns.DESCRIPTION);

    public static final class dictColumns implements BaseColumns {
        public static String WORD = "word";
        public static String DESCRIPTION = "description";
    }
}
