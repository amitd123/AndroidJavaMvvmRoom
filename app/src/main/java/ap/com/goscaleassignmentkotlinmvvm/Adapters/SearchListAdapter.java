package ap.com.goscaleassignmentkotlinmvvm.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ap.com.goscaleassignmentkotlinmvvm.Activitys.HomeActivity;
import ap.com.goscaleassignmentkotlinmvvm.Activitys.MovieDetailActivity;
import ap.com.goscaleassignmentkotlinmvvm.Database.BookMarkedMovies;
import ap.com.goscaleassignmentkotlinmvvm.Models.SearchMovies.Search;
import ap.com.goscaleassignmentkotlinmvvm.R;
import ap.com.goscaleassignmentkotlinmvvm.ViewModel.DatabaseViewModel;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.MyViewHolder>  {

    private List<Search> moviesSearchList;
    private Context context;
    private DatabaseViewModel databaseViewModel;
    private HomeActivity activity;


    public SearchListAdapter(List<Search> moviesSearchList, Context context) {
        this.moviesSearchList = moviesSearchList;
        this.context = context;
        this.activity = (HomeActivity) context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView movieNameSearchList, movieYearSearchList;
        public Button buttonAddBookmark;
        public ImageView imgMoviePosterSearchList;
        public RelativeLayout rlParentSearchList;

        public MyViewHolder(View view) {
            super(view);
            buttonAddBookmark = (Button) view.findViewById(R.id.buttonAddBookmark) ;
            imgMoviePosterSearchList = (ImageView) view.findViewById(R.id.imgMoviePosterSearchList);
            movieNameSearchList = (TextView) view.findViewById(R.id.movieNameSearchList);
            movieYearSearchList = (TextView) view.findViewById(R.id.movieYearSearchList);
            rlParentSearchList = (RelativeLayout) view.findViewById(R.id.rlParentSearchList);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_item, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Search movie = moviesSearchList.get(position);
        holder.movieNameSearchList.setText(movie.getTitle());
        holder.movieYearSearchList.setText(movie.getYear());

        Picasso.with(context).load(movie.getPoster()).placeholder(R.drawable.avatar).error(R.drawable.avatar).into(holder.imgMoviePosterSearchList);

        holder.buttonAddBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                databaseViewModel = ViewModelProviders.of().get(DatabaseViewModel.class);
                databaseViewModel = ViewModelProviders.of((HomeActivity) context).get(DatabaseViewModel.class);
                BookMarkedMovies bookMarkedMovies = new BookMarkedMovies(movie.getImdbID(),movie.getTitle(),movie.getYear(),movie.getType(),movie.getPoster(),true);
                databaseViewModel.insert(bookMarkedMovies);
//                databaseViewModel.getAllBookmarkMovies();
            }
        });
        holder.rlParentSearchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MovieDetailActivity.class);
                i.putExtra("Title",movie.getTitle());
                i.putExtra("Year",movie.getYear());
                i.putExtra("Poster",movie.getPoster());

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesSearchList.size();
    }

    public void removeItem(int position) {
        moviesSearchList.remove(position);
        notifyItemRemoved(position);
        Toast.makeText(context, "Item Removed", Toast.LENGTH_SHORT).show();
    }

}