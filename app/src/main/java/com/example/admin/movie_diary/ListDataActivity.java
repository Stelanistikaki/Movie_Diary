package com.example.admin.movie_diary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListDataActivity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";
    DatabaseHelper mDatabaseHelper;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list);
        getSupportActionBar().hide();

        mListView = findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);

        populateListView();

    }

    @Override
    public void onBackPressed() {
        Intent intent  = new Intent(ListDataActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void populateListView(){
        Cursor data = mDatabaseHelper.getData();
        ArrayList<Movie> listData = new ArrayList<>();
        while(data.moveToNext()){
            String name = data.getString(1);
            String genre = data.getString(4);
            String rating = Float.toString(data.getFloat(5)) + "/5 ";
            Movie aMovie = new Movie(name, genre, rating);
            listData.add(aMovie);
        }

        CustomListAdapter adapter = new CustomListAdapter(this, R.layout.adapter_list, listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie aMovie =(Movie) parent.getItemAtPosition(position);
                String name = aMovie.getTitle();
                Log.d(TAG, "onItemClick: You clicked on: " + name);

                Cursor data = mDatabaseHelper.getItemData(name);
                int itemId = -1;
                String director ="", genre="", year="", recommend="", comments="";
                float rating=0;
                while(data.moveToNext()){
                    itemId = data.getInt(0);
                    year = data.getString(2);
                    director=data.getString(3);
                    genre=data.getString(4);
                    rating=data.getFloat(5);
                    recommend=data.getString(6);
                    comments=data.getString(7);
                }
                if(itemId > -1){
                    Log.d(TAG, "onItemClick: The id is: " + itemId);
                    Intent intent = new Intent(ListDataActivity.this, EditDataActivity.class);
                    intent.putExtra("ID", itemId);
                    intent.putExtra("TITLE", name);
                    intent.putExtra("YEAR", year);
                    intent.putExtra("DIRECTOR", director);
                    intent.putExtra("GENRE", genre);
                    intent.putExtra("RATING", rating);
                    intent.putExtra("RECOMMEND", recommend);
                    intent.putExtra("COMMENTS", comments);

                    startActivity(intent);
                    finish();
                }
                else
                    toastMessage("No ID found with that movie");

            }
        });
    }

    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
