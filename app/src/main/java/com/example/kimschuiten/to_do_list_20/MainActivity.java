package com.example.kimschuiten.to_do_list_20;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
Main Activity. Shows ListView containing all the titles from the several lists made by the user.
When clicked on one of the items, the user is taken to a new activity. Also a button to let the user
create a new to do list.
*/

public class MainActivity extends AppCompatActivity {

    ListView showSavedFilesListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize listview
        showSavedFilesListview = (ListView) findViewById(R.id.ListView1);

        showSavedFilesListview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent showListIntent = new Intent(MainActivity.this, showList.class);
                startActivity(showListIntent);
            }
        });

        // Get intent from lists class
        Intent listActivityCalled = this.getIntent();
    }

    /*
    readFromFile, read from internal storage to show the different lists.
    */
    private void readFromFile (String request) {
        // Reading text from file
        try {
            File file = new File("mytextfile.txt");
            InputStream inputStream = new FileInputStream(file);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                while ((receiveString = bufferedReader.readLine()) != null) {
                    String s = bufferedReader.readLine();
                    if (receiveString == request) {
                        break;
                    }
                }
                inputStream.close();
            }
        }
        catch(FileNotFoundException e){
            Log.e("login activity", "File not found: " + e.toString());

        }catch(IOException e){

        }
    }

    /*
    onClickListener for the Create List button
     */
    public void createNewList(View view) {
        // State intention to open another Activity by passing a Context and the Activity that we
        // want to open
        Intent createListIntent = new Intent(this, Lists.class);

        // Ask for the Activity to start and don't expect a result to be sent back
        final int result = 1;

        startActivity(createListIntent);
    }
}