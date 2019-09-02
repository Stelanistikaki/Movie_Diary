package com.example.admin.movie_diary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchDataActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    ListView matchingListView;

    private static final String TAG = SearchDataActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_movie_list);
        getSupportActionBar().hide();

        mDatabaseHelper = new DatabaseHelper(this);
        matchingListView = findViewById(R.id.resultListView);

        Intent receivedIntent = getIntent();

        String value = receivedIntent.getStringExtra("value");
        String column = receivedIntent.getStringExtra("col");

        Log.d(TAG, "updateData: " + value + " " + column);
        Cursor result = mDatabaseHelper.searchData(value, column);
        if(result.getCount() == 0){
            toastMessage("No movies found!");
            finish();
        }
        ArrayList<Movie> resultListData = new ArrayList<>();
        while(result.moveToNext()){
            String name = result.getString(1);
            Movie aMovie = new Movie(name, null, null);
            resultListData.add(aMovie);
        }

        CustomListAdapter adapter = new CustomListAdapter(this, R.layout.adapter_list, resultListData);
        matchingListView.setAdapter(adapter);
    }

    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
