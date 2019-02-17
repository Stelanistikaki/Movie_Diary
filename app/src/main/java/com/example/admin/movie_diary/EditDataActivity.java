package com.example.admin.movie_diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class EditDataActivity extends AppCompatActivity {

    private EditText editTitleText, editYearText, editDirectorText, editCommentsText;
    private Spinner editGenreSpinner;
    private Switch editRecommendSwitch;
    private RatingBar editRatingStars;
    private Button updateButton, deleteButton;
    private DatabaseHelper mDatabaseHelper;
    private String[] movieGenres = {"Select a genre" , "Action", "Drama", "Comedy", "Thriller", "Horror", "Fantasy", "Sci-fi", "Anime"};

    private String title, year, comments, director, genre, recommendString;
    private boolean  recommend;
    private float rating;
    private int id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_movie_layout);

        mDatabaseHelper = new DatabaseHelper(this);
        editTitleText = findViewById(R.id.editTitleText);
        editYearText = findViewById(R.id.editYearText);
        editDirectorText = findViewById(R.id.editDirectorText);
        editCommentsText = findViewById(R.id.editCommentsText);
        editRecommendSwitch = findViewById(R.id.editRecommendSwitch);
        editRatingStars = findViewById(R.id.editMovieRatingBar);
        editGenreSpinner= findViewById(R.id.editGenreSpinner);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, movieGenres); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        editGenreSpinner.setAdapter(spinnerArrayAdapter);

        //get the values for the clicked movie
        Intent receivedIntent = getIntent();

        id = receivedIntent.getIntExtra("ID", -1);
        title = receivedIntent.getStringExtra("TITLE");
        year = receivedIntent.getStringExtra("YEAR");
        director = receivedIntent.getStringExtra("DIRECTOR");
        genre = receivedIntent.getStringExtra("GENRE");
        rating = receivedIntent.getFloatExtra("RATING", 0);
        recommendString = receivedIntent.getStringExtra("RECOMMEND");
        comments = receivedIntent.getStringExtra("COMMENTS");


        editTitleText.setText(title);
        editYearText.setText(year);
        editDirectorText.setText(director);
        editCommentsText.setText(comments);
        editRatingStars.setRating(rating);

        if(recommendString.equals("true")){
            editRecommendSwitch.setChecked(true);
        }
        else {
            editRecommendSwitch.setChecked(false);
        }

        for(int i=0; i<movieGenres.length; i++){
            if(genre.equals(movieGenres[i]))
                editGenreSpinner.setSelection(i);
        }

        UpdateDataHelp();
        DeleteDataHelp();
    }

    private void UpdateDataHelp(){

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = editTitleText.getText().toString();
                year = editYearText.getText().toString();
                director = editDirectorText.getText().toString();
                genre = editGenreSpinner.getSelectedItem().toString();
                rating = editRatingStars.getRating();
                comments = editCommentsText.getText().toString();
                recommend = editRecommendSwitch.isChecked();
                recommendString = String.valueOf(recommend);

                boolean updated = mDatabaseHelper.UpdateData(id, title, year, director, genre, rating, recommendString, comments);
                if(updated) {
                    toastMessage("Movie updated!");
                    finish();
                }
                else
                    toastMessage("Couldn't update this");

            }
        });
    }

    private void DeleteDataHelp(){
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int deleteRows = mDatabaseHelper.deleteData(id);
                if(deleteRows > 0) {
                    toastMessage("Movie removed!");
                    finish();
                }
                else
                    toastMessage("Something is wrong!");
            }
        });

    }

    //this method is for when the back button is clicked without changes so it goes to ListDataActivity and not MainActivity
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(EditDataActivity.this, ListDataActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }


    public void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
