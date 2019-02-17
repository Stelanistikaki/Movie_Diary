package com.example.admin.movie_diary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchDataActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    ListView matchingListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_movie_list);

        mDatabaseHelper = new DatabaseHelper(this);
        matchingListView = findViewById(R.id.resultListView);

        Intent receivedIntent = getIntent();

        String value = receivedIntent.getStringExtra("value");
        String column = receivedIntent.getStringExtra("col");

        Cursor result = mDatabaseHelper.searchData(value, column);
        if(result.getCount() == 0){
            toastMessage("No movies found!");
            finish();
        }
        ArrayList<String> resultListData = new ArrayList<>();
        while(result.moveToNext()){
            resultListData.add(result.getString(1));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, resultListData);
        matchingListView.setAdapter(adapter);
    }

    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
