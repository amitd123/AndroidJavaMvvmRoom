package ap.com.goscaleassignmentkotlinmvvm.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import ap.com.goscaleassignmentkotlinmvvm.Adapters.AdapterBookMarkMovies;
import ap.com.goscaleassignmentkotlinmvvm.Database.BookMarkedMovies;
import ap.com.goscaleassignmentkotlinmvvm.Database.MoviesRoomRespository;
import ap.com.goscaleassignmentkotlinmvvm.Interfaces.AdapterInterfaces;
import ap.com.goscaleassignmentkotlinmvvm.Interfaces.GetDataInterface;
import ap.com.goscaleassignmentkotlinmvvm.Models.SearchMovies.MoviesSearchResponseModel;
import ap.com.goscaleassignmentkotlinmvvm.Networking.SearchRepository;
import ap.com.goscaleassignmentkotlinmvvm.Utils.Constants;

public class DatabaseViewModel extends AndroidViewModel {

    private MoviesRoomRespository mRepository;
    private GetDataInterface getDataInterface;
    private AdapterInterfaces adapterInterfaces;

    private List<BookMarkedMovies> mAllBookMarkMovies;

    public DatabaseViewModel(Application application) {
        super(application);

//        mRepository = new (application);
        mRepository = MoviesRoomRespository.getInstance(application);
//        mAllBookMarkMovies = mRepository.getAllBookmarkMovies();

    }

    public List<BookMarkedMovies> getAllBookmarkMovies(GetDataInterface getDataInterface) {

        this.getDataInterface = getDataInterface;
        mAllBookMarkMovies = mRepository.getAllBookmarkMovies(getDataInterface);
        return mAllBookMarkMovies;
    }

    public void checkIsItemAvailabe(String id, AdapterInterfaces adapterInterfaces) {

        this.adapterInterfaces = adapterInterfaces;
        mRepository.checkIfItemIsAvailableInDatabase(id, adapterInterfaces);
    }

    public boolean checkIsItemAvailabe(String id) {
      boolean isAvailabe =   mRepository.checkIfItemIsAvailableInDatabase(id);
        return isAvailabe;
    }

    public void deleteById(String id) {
        mRepository.delete(id);
    }
    public void insert(BookMarkedMovies bookMarkedMovies) {
        mRepository.insert(bookMarkedMovies);
    }
}

