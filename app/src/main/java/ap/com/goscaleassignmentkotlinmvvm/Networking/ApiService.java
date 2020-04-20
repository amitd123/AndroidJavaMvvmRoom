package ap.com.goscaleassignmentkotlinmvvm.Networking;

import ap.com.goscaleassignmentkotlinmvvm.Models.SearchMovies.MoviesSearchResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {

    @GET("/?")
    Call<MoviesSearchResponseModel> moviesSearch(@Query("s") String searchString,@Query("apikey") String apiKey);
}
