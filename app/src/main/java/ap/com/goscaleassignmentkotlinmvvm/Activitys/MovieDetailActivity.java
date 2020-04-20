package ap.com.goscaleassignmentkotlinmvvm.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import ap.com.goscaleassignmentkotlinmvvm.R;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initializeScreenComponents();
    }

    private void initializeScreenComponents() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMovieDetail);
        toolbar.setTitle(getIntent().getStringExtra("Title"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ImageView imageView = (ImageView) findViewById(R.id.imgMoviePoster);
        TextView txtMovieName = (TextView) findViewById(R.id.txtMovieName);
        TextView txtMovieYear = (TextView) findViewById(R.id.txtMovieYear);
        Picasso.with(MovieDetailActivity.this).load(getIntent().getStringExtra("Poster")).placeholder(R.drawable.avatar).error(R.drawable.avatar).into(imageView);
        txtMovieName.setText(getIntent().getStringExtra("Title"));
        txtMovieYear.setText(getIntent().getStringExtra("Year"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
