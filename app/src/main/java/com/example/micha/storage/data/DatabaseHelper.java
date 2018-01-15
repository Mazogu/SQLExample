package com.example.micha.storage.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.micha.storage.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by micha on 1/15/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Person";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.CREATE_PERSON_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long savePerson(Person person){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.Person.NAME, person.getName());
        contentValues.put(DatabaseContract.Person.AGE,person.getAge());
        contentValues.put(DatabaseContract.Person.GENDER,person.getGender());

        long rowNumber = database.insert(DatabaseContract.Person.TABLE_NAME,null,contentValues);
        database.close();
        return rowNumber;
    }

    public List<Person> getPersonList(){
        SQLiteDatabase database = getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseContract.Person.TABLE_NAME, null);
        List<Person> peronList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Person person = new Person(cursor.getString(0),cursor.getString(1),cursor.getString(2));
                peronList.add(person);
            }while(cursor.moveToNext());
        }
        database.close();
        return peronList;
    }
}
