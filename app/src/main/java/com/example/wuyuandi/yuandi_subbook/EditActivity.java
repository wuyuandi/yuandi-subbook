
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


public class EditActivity extends AppCompatActivity {
    public static final String FILENAME = "subb.sav";
    private ArrayList<subscription> subers;
    private subscription sub;
    private int position;




    private void checkEdit(){
        final EditText nameText = findViewById(R.id.nameEdit);
        final EditText dateText = findViewById(R.id.dateEdit);
        final EditText chargeText = findViewById(R.id.chargeEdit);
        final EditText commentText = findViewById(R.id.commentEdit);



        final String name = nameText.getText().toString();
        final String date = dateText.getText().toString();
        final float charge = Float.parseFloat(chargeText.getText().toString());
        final String comment = commentText.getText().toString();

        if(!name.isEmpty() && charge >0) {
            sub.setName(name);
            sub.setDate(date);
            sub.setCharge(charge);
            sub.setComment(comment);
            //sub.changeSub(name,date,charge,comment);
        }else{
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent =getIntent();
        loadFromFile();

        position = intent.getIntExtra("position",0);

        sub = subers.get(position);
        /*EditText name = findViewById(R.id.nameEdit);
        name.setText(sub.getName());

        EditText date = findViewById(R.id.dateEdit);
        date.setText(sub.getDate());

        EditText charge = findViewById(R.id.chargeEdit);
        charge.setText(String.valueOf(sub.getCharge());

        EditText comment = findViewById(R.id.commentEdit);
        comment.setText(sub.getComment()); */


        Button saveButton = findViewById(R.id.confirmButton);

        saveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                checkEdit();
                saveInFile();
                finish();
            }
        });
    }


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
