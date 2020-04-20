package ap.com.goscaleassignmentkotlinmvvm.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ap.com.goscaleassignmentkotlinmvvm.Database.BookMarkedMovies;
import ap.com.goscaleassignmentkotlinmvvm.Database.MoviesRoomRespository;
import ap.com.goscaleassignmentkotlinmvvm.Models.SearchMovies.MoviesSearchResponseModel;
import ap.com.goscaleassignmentkotlinmvvm.Networking.SearchRepository;
import ap.com.goscaleassignmentkotlinmvvm.Utils.Constants;

public class DatabaseViewModel extends AndroidViewModel {

    private MoviesRoomRespository mRepository;

    private LiveData<List<BookMarkedMovies>> mAllBookMarkMovies = new MutableLiveData<>();

    public DatabaseViewModel (Application application) {
        super(application);

//        mRepository = new (application);
        mRepository = MoviesRoomRespository.getInstance(application);
        mAllBookMarkMovies = mRepository.getAllBookmarkMovies();
    }

    public LiveData<List<BookMarkedMovies>> getAllBookmarkMovies() {

            return mAllBookMarkMovies;
    }

    public void insert(BookMarkedMovies bookMarkedMovies) { mRepository.insert(bookMarkedMovies); }
}

