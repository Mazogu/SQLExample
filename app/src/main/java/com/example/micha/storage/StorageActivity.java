package com.example.micha.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.micha.storage.data.DatabaseHelper;

import org.w3c.dom.Text;

import java.util.List;

public class StorageActivity extends AppCompatActivity {

    private static final String TAG = StorageActivity.class.getSimpleName();
    private EditText set;
    private TextView display;
    private EditText personName;
    private EditText personAge;
    private EditText
            viewById;
    private EditText personGender;
    private ListView lvPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        set = findViewById(R.id.setValue);
        display = findViewById(R.id.displayValue);

        lvPerson = findViewById(R.id.lvPerson);
        personName = findViewById(R.id.name);
        personAge = findViewById(R.id.age);
        personGender = findViewById(R.id.gender);
    }

    public void sharedPref(View view) {
        //Determines which button was pressed.
        String data = set.getText().toString();
        SharedPreferences shared = getSharedPreferences("mySharedPref", Context.MODE_PRIVATE);
        switch (view.getId()){
            //creates shared preference object
            case R.id.saveValue:
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("editText",data);
                boolean isSaved = editor.commit(); //you have to always commit when changing an editor.
                if(isSaved){
                    Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.getSavedValue:
                String dataRetrieved = shared.getString("editText","default");
                display.setText(dataRetrieved);
                break;
        }
    }

    public void handleDatabase(View view) {
        String name,age,gender;
        DatabaseHelper database = new DatabaseHelper(this);
        name = personName.getText().toString();
        age = personAge.getText().toString();
        gender = personGender.getText().toString();
        final Person person = new Person(name,age,gender);

        switch (view.getId()){
            case R.id.savePerson:
                long rowNumber = database.savePerson(person);
                Toast.makeText(this,"Person saved at " + rowNumber,Toast.LENGTH_LONG).show();
                break;
            case R.id.getPerson:
                final List<Person> personList = database.getPersonList();
                Log.d(TAG,personList.toString());

                ArrayAdapter<Person> arrayAdapter = new ArrayAdapter<Person>(this, android.R.layout.simple_expandable_list_item_1, personList);
                lvPerson.setAdapter(arrayAdapter);
                lvPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String display = personList.get(i).toString() + " was clicked";
                        Toast.makeText(StorageActivity.this,display,Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
