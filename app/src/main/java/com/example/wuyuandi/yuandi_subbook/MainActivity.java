package com.example.wuyuandi.yuandi_subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "subb.sav";
    private ListView subList;

    private ArrayList<subscription> subers = new ArrayList();
    private ArrayAdapter<subscription> adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * if i add (Button) after =, it will cause Casting to Button is redundant - Why?
         */
        Button add_new_sub = findViewById(R.id.add_new_sub);

        /**
         * it will show out all the subscription that already made
         */
        subList = findViewById(R.id.sub_list);

        subList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,CheckInfoActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
                subscription item = (subscription) parent.getItemAtPosition(position);
            }
        });
        /**
         * when usr press save
         */
        add_new_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddSubscription.class);
                startActivity(intent);
            }
        });

    }
    /**
     *
     */
    @Override
    protected void onStart(){
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<subscription>(this,android.R.layout.simple_list_item_1,subers);
        subList.setAdapter(adapter);

        TextView generalInFo = (TextView) findViewById(R.id.generalInfo);
        String totalX;
        totalX = totalPrice(subers);
        generalInFo.setText("Total:"+totalX);

    }
    public String totalPrice (ArrayList<subscription> all){
        float totalP = 0;
        for (int i=0; i<all.size();i++){
            totalP += all.get(i).getCharge();
        }
        return Float.toString(totalP);
    }

    /**
     * load From file function
     * base from the lonely twitter
     */
    private void loadFromFile(){
        try {
            FileInputStream files = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(files));

            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<subscription>>() {}.getType();
            subers = gson.fromJson(in, listType);
        }catch (FileNotFoundException e){
            subers = new ArrayList<subscription>();

        }
    }

}
