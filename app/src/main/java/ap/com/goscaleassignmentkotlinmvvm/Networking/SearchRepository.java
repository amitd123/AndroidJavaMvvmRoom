package ap.com.goscaleassignmentkotlinmvvm.Networking;

import androidx.lifecycle.MutableLiveData;

import ap.com.goscaleassignmentkotlinmvvm.Models.SearchMovies.MoviesSearchResponseModel;
import ap.com.goscaleassignmentkotlinmvvm.Utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {

    private static SearchRepository searchRepository;

    public static SearchRepository getInstance(){
        if (searchRepository == null){
            searchRepository = new SearchRepository();
        }
        return searchRepository;
    }

    private ApiService apiService;

    public SearchRepository(){
        apiService = RetrofitService.cteateService(ApiService.class);
    }

    public MutableLiveData<MoviesSearchResponseModel> getSearchMovies(String source){
        final MutableLiveData<MoviesSearchResponseModel> searchData = new MutableLiveData<>();
        apiService.moviesSearch(source, Constants.API_KEY).enqueue(new Callback<MoviesSearchResponseModel>() {
            @Override
            public void onResponse(Call<MoviesSearchResponseModel> call,
                                   Response<MoviesSearchResponseModel> response) {
                if (response.isSuccessful()){
                    searchData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MoviesSearchResponseModel> call, Throwable t) {
                searchData.setValue(null);
            }
        });
        return searchData;
    }
}
