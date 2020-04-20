package ap.com.goscaleassignmentkotlinmvvm.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ap.com.goscaleassignmentkotlinmvvm.Database.BookMarkedMovies;
import ap.com.goscaleassignmentkotlinmvvm.Models.SearchMovies.Search;
import ap.com.goscaleassignmentkotlinmvvm.R;

public class AdapterBookMarkMovies extends RecyclerView.Adapter<AdapterBookMarkMovies.MyViewHolder>  {

    private List<BookMarkedMovies> moviesSearchList;
    private Context context;


    public AdapterBookMarkMovies(List<BookMarkedMovies> moviesSearchList, Context context) {
        this.moviesSearchList = moviesSearchList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView movieNameSearchList, movieYearSearchList;
        public ImageView imgMoviePosterSearchList;
        public RelativeLayout rlParentSearchList;

        public MyViewHolder(View view) {
            super(view);
            imgMoviePosterSearchList = (ImageView) view.findViewById(R.id.imgMoviePosterSearchList);
            movieNameSearchList = (TextView) view.findViewById(R.id.movieNameSearchList);
            movieYearSearchList = (TextView) view.findViewById(R.id.movieYearSearchList);
            rlParentSearchList = (RelativeLayout) view.findViewById(R.id.rlParentSearchList);
        }
    }

    @Override
    public AdapterBookMarkMovies.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_item, parent, false);
        return new AdapterBookMarkMovies.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterBookMarkMovies.MyViewHolder holder, final int position) {

        BookMarkedMovies movie = moviesSearchList.get(position);
        holder.movieNameSearchList.setText(movie.getTitle());
        holder.movieYearSearchList.setText(movie.getYear());

        Picasso.with(context).load(movie.getPoster()).placeholder(R.drawable.avatar).error(R.drawable.avatar).into(holder.imgMoviePosterSearchList);

/*
        holder.lllayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WebViewActivity.class);
                i.putExtra("url",recipesList.get(position).getPublisherUrl());
                context.startActivity(i);
            }
        });
*/
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