package ap.com.goscaleassignmentkotlinmvvm.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ap.com.goscaleassignmentkotlinmvvm.Activitys.HomeActivity;
import ap.com.goscaleassignmentkotlinmvvm.Database.BookMarkedMovies;
import ap.com.goscaleassignmentkotlinmvvm.Interfaces.GetDataInterface;
import ap.com.goscaleassignmentkotlinmvvm.R;
import ap.com.goscaleassignmentkotlinmvvm.ViewModel.DatabaseViewModel;

public class AdapterBookMarkMovies extends RecyclerView.Adapter<AdapterBookMarkMovies.MyViewHolder>  {

    private List<BookMarkedMovies> moviesSearchList;
    private Context context;
    private DatabaseViewModel databaseViewModel;
    int currentPosition = 0;
    private GetDataInterface getDataInterface;



    public AdapterBookMarkMovies(List<BookMarkedMovies> moviesSearchList, Context context,GetDataInterface getDataInterface) {
        this.moviesSearchList = moviesSearchList;
        this.context = context;
        this.getDataInterface = getDataInterface;
        databaseViewModel = ViewModelProviders.of((HomeActivity) context).get(DatabaseViewModel.class);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView movieNameSearchList, movieYearSearchList;
        public ImageView imgMoviePosterSearchList;
        public RelativeLayout rlParentSearchList;
        public Button buttonRemoveBookMark;

        public MyViewHolder(View view) {
            super(view);
            imgMoviePosterSearchList = (ImageView) view.findViewById(R.id.imgMoviePosterSearchList);
            movieNameSearchList = (TextView) view.findViewById(R.id.movieNameSearchList);
            movieYearSearchList = (TextView) view.findViewById(R.id.movieYearSearchList);
            rlParentSearchList = (RelativeLayout) view.findViewById(R.id.rlParentSearchList);
            buttonRemoveBookMark = (Button) view.findViewById(R.id.buttonAddBookmark);
        }
    }

    @Override
    public AdapterBookMarkMovies.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_item, parent, false);
        return new AdapterBookMarkMovies.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( AdapterBookMarkMovies.MyViewHolder holder, int position) {
        currentPosition = position;
        final BookMarkedMovies movie = moviesSearchList.get(position);
        holder.movieNameSearchList.setText(movie.getTitle());
        holder.movieYearSearchList.setText(movie.getYear());
        holder.buttonRemoveBookMark.setText("Remove BookMark");
        holder.buttonRemoveBookMark.setTag(position);

        try{
            Picasso.with(context).load(movie.getPoster()).placeholder(R.drawable.avatar).error(R.drawable.avatar).into(holder.imgMoviePosterSearchList);
        }catch (Exception e){

        }
        holder.buttonRemoveBookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseViewModel.deleteById(moviesSearchList.get(currentPosition).getImdbID());
                getDataInterface.updateBookMarkList();
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesSearchList.size();
    }

    public void removeItem(int position) {
        moviesSearchList.remove(position);
        if (moviesSearchList != null){
            if (moviesSearchList.size() > 0){
                notifyItemRemoved(position);
            }else {
                getDataInterface.updateBookMarkList();
            }
        }

        Toast.makeText(context, "Item Removed", Toast.LENGTH_SHORT).show();
    }

}