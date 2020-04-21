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
import ap.com.goscaleassignmentkotlinmvvm.Interfaces.GetDataInterface;
import ap.com.goscaleassignmentkotlinmvvm.Models.SearchMovies.Search;
import ap.com.goscaleassignmentkotlinmvvm.R;
import ap.com.goscaleassignmentkotlinmvvm.ViewModel.DatabaseViewModel;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.MyViewHolder>  {

    private List<Search> moviesSearchList;
    private Context context;
    private Activity mActivity;
    private DatabaseViewModel databaseViewModel;
    private HomeActivity activity;
    private GetDataInterface getDataInterface;
    private int currentPosition = 0;


    public SearchListAdapter(List<Search> moviesSearchList, Context context,GetDataInterface getDataInterface) {
        this.moviesSearchList = moviesSearchList;
        this.context = context;
        this.mActivity = (Activity) context;
        this.activity = (HomeActivity) context;
        this.getDataInterface = getDataInterface;
        databaseViewModel = ViewModelProviders.of((HomeActivity) context).get(DatabaseViewModel.class);
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
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Search movie = moviesSearchList.get(position);
        holder.movieNameSearchList.setText(movie.getTitle());
        holder.movieYearSearchList.setText(movie.getYear());
        Picasso.with(context).load(movie.getPoster()).placeholder(R.drawable.avatar).error(R.drawable.avatar).into(holder.imgMoviePosterSearchList);

        if (databaseViewModel.checkIsItemAvailabe(movie.getImdbID())){
            holder.buttonAddBookmark.setVisibility(View.GONE);
        }else {
            holder.buttonAddBookmark.setVisibility(View.VISIBLE);
        }
        currentPosition = position;
        holder.buttonAddBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookMarkedMovies bookMarkedMovies = new BookMarkedMovies(moviesSearchList.get(currentPosition).getImdbID(),moviesSearchList.get(currentPosition).getTitle(),moviesSearchList.get(currentPosition).getYear(),moviesSearchList.get(currentPosition).getType(),moviesSearchList.get(currentPosition).getPoster(),true);
                databaseViewModel.insert(bookMarkedMovies);
                getDataInterface.updateBookMarkList();
            }
        });
        holder.rlParentSearchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MovieDetailActivity.class);
                i.putExtra("Title",moviesSearchList.get(currentPosition).getTitle());
                i.putExtra("Year",moviesSearchList.get(currentPosition).getYear());
                i.putExtra("Poster",moviesSearchList.get(currentPosition).getPoster());

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