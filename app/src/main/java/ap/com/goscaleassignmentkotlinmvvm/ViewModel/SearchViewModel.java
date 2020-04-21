package ap.com.goscaleassignmentkotlinmvvm.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ap.com.goscaleassignmentkotlinmvvm.Interfaces.GetDataInterface;
import ap.com.goscaleassignmentkotlinmvvm.Models.SearchMovies.MoviesSearchResponseModel;
import ap.com.goscaleassignmentkotlinmvvm.Networking.SearchRepository;
import ap.com.goscaleassignmentkotlinmvvm.Utils.Constants;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<MoviesSearchResponseModel> mutableLiveData;
    private SearchRepository searchRepository;
    private GetDataInterface getDataInterface;

    public void init(){
        if (mutableLiveData != null){
            return;
        }
        searchRepository = SearchRepository.getInstance();
    }

    public LiveData<MoviesSearchResponseModel> getSearchRepository(String inputString) {
        mutableLiveData = searchRepository.getSearchMovies(inputString);
        return mutableLiveData;
    }
}