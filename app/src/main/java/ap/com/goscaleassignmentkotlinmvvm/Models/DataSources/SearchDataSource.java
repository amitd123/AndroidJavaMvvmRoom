package ap.com.goscaleassignmentkotlinmvvm.Models.DataSources;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import ap.com.goscaleassignmentkotlinmvvm.Networking.ApiService;
import ap.com.goscaleassignmentkotlinmvvm.Models.SearchMovies.MoviesSearchResponseModel;
import ap.com.goscaleassignmentkotlinmvvm.Models.SearchMovies.Search;
//import ap.com.goscaleassignmentkotlinmvvm.Networking.APIClient;
import ap.com.goscaleassignmentkotlinmvvm.Networking.RetrofitService;
import ap.com.goscaleassignmentkotlinmvvm.Utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchDataSource extends PageKeyedDataSource<Long, Search> {
    public static int PAGE_SIZE = 5;
    public static long FIRST_PAGE = 1;

    @Override public void loadInitial(@NonNull final LoadInitialParams<Long> params,
                                      @NonNull final LoadInitialCallback<Long, Search> callback) {
        ApiService  apiService = RetrofitService.cteateService(ApiService.class);
        Call<MoviesSearchResponseModel> call = apiService.moviesSearch("friends", Constants.API_KEY);

        call.enqueue(new Callback<MoviesSearchResponseModel>() {
            @Override public void onResponse(Call<MoviesSearchResponseModel> call, Response<MoviesSearchResponseModel> response) {
                MoviesSearchResponseModel apiResponse = response.body();
                if (apiResponse != null) {
                    List<Search> responseItems = apiResponse.getSearch();
                    callback.onResult(responseItems, null, FIRST_PAGE + 1);
                }
            }

            @Override public void onFailure(Call<MoviesSearchResponseModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Long> params,
                           @NonNull final LoadCallback<Long, Search> callback) {

        ApiService  apiService = RetrofitService.cteateService(ApiService.class);
        Call<MoviesSearchResponseModel> call = apiService.moviesSearch("friends", Constants.API_KEY);

        call.enqueue(new Callback<MoviesSearchResponseModel>() {
            @Override public void onResponse(Call<MoviesSearchResponseModel> call, Response<MoviesSearchResponseModel> response) {
                MoviesSearchResponseModel apiResponse = response.body();
                if (apiResponse != null) {
                    List<Search> responseItems = apiResponse.getSearch();
                    long key;
                    if (params.key > 1) {
                        key = params.key - 1;
                    } else {
                        key = 0;
                    }
                    callback.onResult(responseItems, key);
                }
            }

            @Override public void onFailure(Call<MoviesSearchResponseModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params,
                          @NonNull final LoadCallback<Long, Search> callback) {

        ApiService  apiService = RetrofitService.cteateService(ApiService.class);
        Call<MoviesSearchResponseModel> call = apiService.moviesSearch("friends", Constants.API_KEY);

        call.enqueue(new Callback<MoviesSearchResponseModel>() {
            @Override public void onResponse(Call<MoviesSearchResponseModel> call, Response<MoviesSearchResponseModel> response) {
                MoviesSearchResponseModel apiResponse = response.body();
                if (apiResponse != null) {
                    List<Search> responseItems = apiResponse.getSearch();
                    callback.onResult(responseItems, params.key + 1);
                }
            }

            @Override public void onFailure(Call<MoviesSearchResponseModel> call, Throwable t) {

            }
        });
    }
}