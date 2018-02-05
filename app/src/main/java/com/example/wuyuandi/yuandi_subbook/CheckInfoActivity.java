package com.example.wuyuandi.yuandi_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class CheckInfoActivity extends AppCompatActivity {
    public static final String FILENAME = "subb.sav";
    private ArrayList<subscription> subers = new ArrayList<subscription>();
    private subscription sub;
    private int position;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_info);
        loadFromFile();
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

        sub = subers.get(position);

        String name = sub.getName();
        String date = sub.getDate();
        double charge = sub.getCharge();
        String comment = sub.getComment();


        TextView name_info = findViewById(R.id.name_info);
        TextView date_info = findViewById(R.id.date_info);
        TextView charge_info = findViewById(R.id.charge_info);
        TextView comment_info = findViewById(R.id.comment_info);

        Button edit = findViewById(R.id.edit_button);
        Button del = findViewById(R.id.del_button);

        name_info.setText(name);
        date_info.setText(date);
        charge_info.setText(Double.toString(charge));
        comment_info.setText(comment);


        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(CheckInfoActivity.this, EditActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
                finish();
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                subers.remove(position);
                saveInFile();
                finish();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        loadFromFile();
    }



    private void loadFromFile(){
        try{
            FileInputStream files = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(files));
            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<subscription>>(){}.getType();
            subers = gson.fromJson(in,listType);
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

