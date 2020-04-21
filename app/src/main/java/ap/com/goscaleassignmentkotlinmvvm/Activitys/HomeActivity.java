package ap.com.goscaleassignmentkotlinmvvm.Activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ap.com.goscaleassignmentkotlinmvvm.Adapters.AdapterBookMarkMovies;
import ap.com.goscaleassignmentkotlinmvvm.Adapters.SearchListAdapter;
import ap.com.goscaleassignmentkotlinmvvm.Database.BookMarkedMovies;
import ap.com.goscaleassignmentkotlinmvvm.Interfaces.GetDataInterface;
import ap.com.goscaleassignmentkotlinmvvm.Models.SearchMovies.MoviesSearchResponseModel;
import ap.com.goscaleassignmentkotlinmvvm.Models.SearchMovies.Search;
import ap.com.goscaleassignmentkotlinmvvm.R;
import ap.com.goscaleassignmentkotlinmvvm.Utils.Constants;
import ap.com.goscaleassignmentkotlinmvvm.ViewModel.DatabaseViewModel;
import ap.com.goscaleassignmentkotlinmvvm.ViewModel.SearchViewModel;

public class HomeActivity extends AppCompatActivity implements GetDataInterface {

    RecyclerView recyclerView, recyclerViewHorizontalBookmarkMovies;
    ArrayList<Search> searchArrayList = new ArrayList<>();
    private List<Search> searchLoadingArrayList = new ArrayList<>();
    List<BookMarkedMovies> bookmarkMoviesArrayList = new ArrayList<>();
    AdapterBookMarkMovies bookMarkMoviesAdapter;
    SearchListAdapter searchListAdapter;
    SearchViewModel searchViewModel;
    DatabaseViewModel databaseViewModel;
    TextView txtNoBookMarkMovies;
    SearchView searchView;
    Observer observerSearchMovies;
    int firstVisibleItem;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeScreenComponents();

    }

    // initializeScreenComponents
    private void initializeScreenComponents() {
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchview);
        txtNoBookMarkMovies = findViewById(R.id.txtNoBookMarkMovies);
        recyclerViewHorizontalBookmarkMovies = findViewById(R.id.recyclerViewHorizontalBookmarkList);

        setupRecyclerView();

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchViewModel.init();

        //set observer for search list
        observerSearchMovies = new Observer<MoviesSearchResponseModel>() {
            @Override
            public void onChanged(MoviesSearchResponseModel moviesSearchResponseModel) {
                List<Search> searchMovies = moviesSearchResponseModel.getSearch();
                searchArrayList.clear();
                if (searchMovies != null) {
                    searchArrayList.addAll(searchMovies);
                    loadMore();
                }
            }
        };

        // Call Api to get searchMovies
        searchViewModel.getSearchRepository(Constants.DEFAULT_SEARCH_MOVIES).observe((AppCompatActivity) HomeActivity.this, observerSearchMovies);

        // get bookmarkList from database
        databaseViewModel = ViewModelProviders.of(HomeActivity.this).get(DatabaseViewModel.class);
        databaseViewModel.getAllBookmarkMovies(HomeActivity.this);

        // set Query text change listener to searchview
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewModel.getSearchRepository(query).observe((AppCompatActivity) HomeActivity.this, observerSearchMovies);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //Set scroll listener to recyclerview for pagination
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == searchLoadingArrayList.size() - 1) {
                        isLoading = false;
                        loadMore();
                    }
                }
            }
        });
    }

    //Pagination for loading more movies
    private void loadMore() {
        int count3Items = 0;
        for (int i = searchLoadingArrayList.size(); i < searchArrayList.size(); i++) {
            if (count3Items == 3) {
                break;
            } else {
                searchLoadingArrayList.add(searchArrayList.get(i));
            }
            count3Items = count3Items + 1;
        }
        searchListAdapter.notifyDataSetChanged();
        Log.d("count searchLoadingList", String.valueOf(searchLoadingArrayList.size()));
        if (searchLoadingArrayList.size() == searchArrayList.size()) {
            isLoading = true;
        }
    }


    //setup adapters and recyclerview for both list
    private void setupRecyclerView() {
        if (searchListAdapter == null) {
            searchListAdapter = new SearchListAdapter(searchArrayList, HomeActivity.this, HomeActivity.this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(searchListAdapter);
            recyclerView.setNestedScrollingEnabled(true);
        } else {
            searchListAdapter.notifyDataSetChanged();
        }
        if (bookMarkMoviesAdapter == null) {
            bookMarkMoviesAdapter = new AdapterBookMarkMovies(bookmarkMoviesArrayList, HomeActivity.this, HomeActivity.this);
            LinearLayoutManager linearLayoutManagerHorizontal = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewHorizontalBookmarkMovies.setLayoutManager(linearLayoutManagerHorizontal);
            recyclerViewHorizontalBookmarkMovies.setAdapter(bookMarkMoviesAdapter);
            recyclerViewHorizontalBookmarkMovies.setNestedScrollingEnabled(true);
        } else {
            bookMarkMoviesAdapter.notifyDataSetChanged();
        }
        txtNoBookMarkMovies.setVisibility(View.VISIBLE);
    }

    //get List of all bookmarkmovies from database
    @Override
    public void getAllBookmarkMovies(final List<BookMarkedMovies> getAllBookmarkMovies) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bookmarkMoviesArrayList.clear();
                bookmarkMoviesArrayList.addAll(getAllBookmarkMovies);
                bookMarkMoviesAdapter = new AdapterBookMarkMovies(bookmarkMoviesArrayList, HomeActivity.this, HomeActivity.this);
                LinearLayoutManager linearLayoutManagerHorizontal = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerViewHorizontalBookmarkMovies.setLayoutManager(linearLayoutManagerHorizontal);
                recyclerViewHorizontalBookmarkMovies.setAdapter(bookMarkMoviesAdapter);
                recyclerViewHorizontalBookmarkMovies.setNestedScrollingEnabled(true);
            }
        });
    }

    //get latest List of all bookmarked movies from database
    @Override
    public void updateBookMarkList() {
        databaseViewModel.getAllBookmarkMovies(HomeActivity.this);
    }


}