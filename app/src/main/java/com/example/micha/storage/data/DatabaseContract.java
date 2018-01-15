package com.example.micha.storage.data;

import android.provider.BaseColumns;

/**
 * Created by micha on 1/15/2018.
 */

public class DatabaseContract{
    public static final String CREATE_PERSON_TABLE = "CREATE TABLE " + Person.TABLE_NAME + "(" +
            Person.NAME + " TEXT PRIMARY KEY," + Person.AGE + " TEXT," + Person.GENDER + " TEXT)";

    public static class Person implements BaseColumns {
        public static final String TABLE_NAME = "Person";
        public static final String NAME = "name";
        public static final String AGE = "age";
        public static final String GENDER = "gender";
    }
}
