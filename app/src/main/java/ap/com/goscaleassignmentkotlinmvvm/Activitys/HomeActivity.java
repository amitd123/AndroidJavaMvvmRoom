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
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ap.com.goscaleassignmentkotlinmvvm.Adapters.AdapterBookMarkMovies;
import ap.com.goscaleassignmentkotlinmvvm.Adapters.SearchListAdapter;
import ap.com.goscaleassignmentkotlinmvvm.Database.BookMarkedMovies;
import ap.com.goscaleassignmentkotlinmvvm.Models.SearchMovies.MoviesSearchResponseModel;
import ap.com.goscaleassignmentkotlinmvvm.Models.SearchMovies.Search;
import ap.com.goscaleassignmentkotlinmvvm.R;
import ap.com.goscaleassignmentkotlinmvvm.Utils.Constants;
import ap.com.goscaleassignmentkotlinmvvm.ViewModel.DatabaseViewModel;
import ap.com.goscaleassignmentkotlinmvvm.ViewModel.SearchViewModel;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView, recyclerViewHorizontalBookmarkMovies;
    ArrayList<Search> searchArrayList = new ArrayList<>();
    private List<Search> searchLoadingArrayList = new ArrayList<>();
    ArrayList<BookMarkedMovies> bookmarkMoviesArrayList = new ArrayList<>();
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

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchview);
        txtNoBookMarkMovies = findViewById(R.id.txtNoBookMarkMovies);
        recyclerViewHorizontalBookmarkMovies = findViewById(R.id.recyclerViewHorizontalBookmarkList);

        setupRecyclerView();

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchViewModel.init();

        observerSearchMovies = new Observer<MoviesSearchResponseModel>() {
            @Override
            public void onChanged(MoviesSearchResponseModel moviesSearchResponseModel) {
                List<Search> searchMovies = moviesSearchResponseModel.getSearch();
                searchArrayList.clear();
                if (searchMovies != null){
                    searchArrayList.addAll(searchMovies);
                    loadMore();
                }
            }
        };
        searchViewModel.getSearchRepository(Constants.DEFAULT_SEARCH_MOVIES).observe((AppCompatActivity)HomeActivity.this, observerSearchMovies);

        databaseViewModel = ViewModelProviders.of(HomeActivity.this).get(DatabaseViewModel.class);
        databaseViewModel.getAllBookmarkMovies().observe(this, new Observer<List<BookMarkedMovies>>() {
            @Override
            public void onChanged(@Nullable final List<BookMarkedMovies> bookMarkedMovies) {
                // Update the cached copy of the words in the adapter.
                    bookmarkMoviesArrayList.addAll(bookMarkedMovies);

                    bookMarkMoviesAdapter.notifyDataSetChanged();

                if (bookmarkMoviesArrayList.size() > 0){
                    txtNoBookMarkMovies.setVisibility(View.GONE);
                }else {
                    txtNoBookMarkMovies.setVisibility(View.VISIBLE);
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewModel.getSearchRepository(query).observe((AppCompatActivity)HomeActivity.this, observerSearchMovies);                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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


    private void loadMore() {
        int count5Items = 0;
        for (int i = searchLoadingArrayList.size(); i < searchArrayList.size(); i++) {
            if (count5Items == 3) {
                break;
            } else {
                searchLoadingArrayList.add(searchArrayList.get(i));
            }
            count5Items = count5Items + 1;
        }
        searchListAdapter.notifyDataSetChanged();
        Log.d("count searchLoadingList", String.valueOf(searchLoadingArrayList.size()));
        if (searchLoadingArrayList.size() == searchArrayList.size()) {
            isLoading = true;
        }
    }


    private void setupRecyclerView() {
        if (searchListAdapter == null) {
            searchListAdapter = new SearchListAdapter(searchArrayList, HomeActivity.this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(searchListAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
        } else {
            searchListAdapter.notifyDataSetChanged();
        }

        if (bookMarkMoviesAdapter == null) {
            bookMarkMoviesAdapter = new AdapterBookMarkMovies(bookmarkMoviesArrayList, HomeActivity.this);
            LinearLayoutManager linearLayoutManagerHorizontal = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerViewHorizontalBookmarkMovies.setLayoutManager(linearLayoutManagerHorizontal);
            recyclerViewHorizontalBookmarkMovies.setAdapter(bookMarkMoviesAdapter);
            recyclerViewHorizontalBookmarkMovies.setItemAnimator(new DefaultItemAnimator());
            recyclerViewHorizontalBookmarkMovies.setNestedScrollingEnabled(true);
        } else {
            bookMarkMoviesAdapter.notifyDataSetChanged();
        }

        txtNoBookMarkMovies.setVisibility(View.VISIBLE);
    }
}