
/*
 *  Copyright (c) $2018.CMPUT301 Wi18,university of Alberta -All Rights Reserved.
 *  You may use,distribute or modify this code under terms and condition of code of student Behavior
 *  at University of Alberta.
 *  You can find a copy of the license in this project. Otherwise, please contace yuandi@ualberta.ca
 * /
 */

package com.example.wuyuandi.yuandi_subbook;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;


public class AddSubscription extends AppCompatActivity {
    public static final String FILENAME = "subb.sav";

    private ArrayList<subscription> subers = new ArrayList<subscription>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subscription);

        Intent intent = getIntent();
        loadFromFile();

        Button sub_add = (Button) findViewById(R.id.save_button);



        sub_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInput();
                saveInFile();
                finish();

            }
        });

    }
    /**
    the function will check the input by get those from the xml file
     **/

    private void checkInput(){
        final EditText subName = (EditText) findViewById(R.id.sub_name);
        final EditText subDate = (EditText) findViewById(R.id.sub_date);
        final EditText subCharge = (EditText) findViewById(R.id.sub_charge);
        final EditText subComment = (EditText) findViewById(R.id.sub_comment);

        final String name = subName.getText().toString();
        final String date = subDate.getText().toString();
        final float charge = Float.parseFloat(subCharge.getText().toString());
        final String comment = subComment.getText().toString();

        /*
        check the input if is ok
         */
        if(!name.isEmpty() && charge>0 && !comment.isEmpty()){
            subers.add(new subscription(name,date,charge,comment));
        }
        else if(!name.isEmpty() && charge>0 && comment.isEmpty()){
            subers.add(new subscription(name,date,charge,""));
        }
        else {
            finish();
        }
    }

    /*
    copy from lonely twitter
    it can load the information from the file by using gson
     */
    private void loadFromFile(){
        try {
            FileInputStream files = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(files));

            Gson gson = new Gson();
            //citation : https://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt

            Type listType = new TypeToken<ArrayList<subscription>>() {}.getType();
            subers = gson.fromJson(in, listType);
        }catch (FileNotFoundException e){
            subers = new ArrayList<subscription>();

        }
    }
    /*
    copy from lonely twitter
    it can save the information from the file by using gson
    */
    private void saveInFile(){
        try{
            FileOutputStream fos = openFileOutput(FILENAME,0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);

            Gson gson = new Gson();
            gson.toJson(subers,writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e){
            throw new RuntimeException(e);

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }



}
