package com.example.kimschuiten.to_do_list_20;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kimschuiten.to_do_list.R;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/*
In this class the user can create a new to do list. The list is saved to the internal storage so it
can be used in the MainActivity.
 */

public class Lists extends Activity {

    EditText addTitleEdText;
    Button addToArrayBtn;
    EditText addToDoEdText;
    ListView toDoListView;
    ArrayList<String> addArray = new ArrayList<String>();
    Button BtnSave;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.lists_layout);

        // Get the intent that called for this activity to open
        Intent activityThatCalled = getIntent();

        // Initialize EditText, Button and ListView
        addTitleEdText = (EditText) findViewById(R.id.titleList);
        addToDoEdText = (EditText) findViewById(R.id.listAddEditText);
        addToArrayBtn = (Button) findViewById(R.id.addButton);
        toDoListView = (ListView) findViewById(R.id.ListView2);
        BtnSave = (Button) findViewById(R.id.saveListButton);

        /*
        Set Button OnClickListener to add items to the list
         */
        addToArrayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get text from input field
                String getInput = addToDoEdText.getText().toString();

                // If getInput exists, don't add
                if (addArray.contains(getInput)) {
                    Toast.makeText(getBaseContext(), "You already have this on your TODO list", Toast.LENGTH_LONG).show();
                }
                // If getInput is empty, don't add but prompt for input
                else if (getInput == null || getInput.trim().equals("")) {
                    Toast.makeText(getBaseContext(), "You can't add an empty task", Toast.LENGTH_LONG).show();
                }
                // Add getInput to array
                else {
                    addArray.add(getInput);
                    // Create Array adapter
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Lists.this, android.R.layout.simple_list_item_1, addArray);
                    // Set Array adapter
                    toDoListView.setAdapter(adapter);
                    ((EditText) findViewById(R.id.listAddEditText)).setText(" ");

                    // We notify the data model is changed
                    adapter.notifyDataSetChanged();
                }
            }
        });

        /*
        Add a longclick to delete items from the list
         */
        toDoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Delete rows when on clicked
                addArray.remove(position);

                // Create Array adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Lists.this, android.R.layout.simple_list_item_1, addArray);

                // Set Array adapter
                toDoListView.setAdapter(adapter);

                // We notify the data model is changed
                adapter.notifyDataSetChanged();

                // Confirm deletion
                String taskCompleted = "You deleted this task";
                Toast.makeText(Lists.this, taskCompleted, Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    /*
    When button is clicked, save the list to internal storage. Show a toast when the user did not put
    in a title for the list.
     */
    public void saveListButtonClick(View view) {
        // Get text from input field
        String getTitleInput = addTitleEdText.getText().toString();

        // Show a toast when the title EditText is empty
        if (getTitleInput == null || getTitleInput.trim().equals("")) {
            Toast.makeText(getBaseContext(), "You can't add a list without a title", Toast.LENGTH_LONG).show();
        }

        else {
            // Save list to internal storage
            try {
                FileOutputStream fileout = openFileOutput("mytextfile.txt", MODE_PRIVATE);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                outputWriter.write(toDoListView.getAdapter().toString());
                outputWriter.close();

                //Display file saved message
                Toast.makeText(getBaseContext(), "List saved successfully!",
                        Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }

            // Intent back to MainScreen
            Intent getNewListIntent = new Intent(this, MainActivity.class);

            final int result = 1;

            startActivity(getNewListIntent);
        }
    }
}

