package com.example.kimschuiten.to_do_list_20;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.kimschuiten.to_do_list.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
In this class the user can look at the details of the list
 */

public class showList extends Activity{

    ListView showListView;
    Button goBackBtn;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.show_list_layout);

        showListView = (ListView) findViewById(R.id.ListView3);
        goBackBtn = (Button) findViewById(R.id.goBackButton);

    }

    /*
    readFromFile to read the list data from the internal storage.
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
    When GoBackBtn is clicked, go back to MainActivity
     */
    public void clickGobackBtn(View view) {
        // Intent back to MainScreen
        Intent getNewListIntent = new Intent(this, MainActivity.class);

        final int result = 1;

        startActivity(getNewListIntent);
    }
}

