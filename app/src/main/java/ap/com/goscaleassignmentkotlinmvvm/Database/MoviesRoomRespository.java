package ap.com.goscaleassignmentkotlinmvvm.Database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import ap.com.goscaleassignmentkotlinmvvm.Interfaces.AdapterInterfaces;
import ap.com.goscaleassignmentkotlinmvvm.Interfaces.GetDataInterface;
import ap.com.goscaleassignmentkotlinmvvm.Models.SearchMovies.MoviesSearchResponseModel;
import ap.com.goscaleassignmentkotlinmvvm.Networking.ApiService;
import ap.com.goscaleassignmentkotlinmvvm.Networking.RetrofitService;
import ap.com.goscaleassignmentkotlinmvvm.Networking.SearchRepository;

public class MoviesRoomRespository {
    public BookMarkMoviesDao bookMarkMoviesDao;
    private List<BookMarkedMovies> mAllBookmarkMovies;
    private GetDataInterface getDataInterface;


    private static MoviesRoomRespository moviesRoomRespository;


    public static MoviesRoomRespository getInstance(Application application) {
        if (moviesRoomRespository == null) {
            moviesRoomRespository = new MoviesRoomRespository(application);
        }
        return moviesRoomRespository;
    }


    public MoviesRoomRespository(Application application) {
        MoviesRoomDatabase db = MoviesRoomDatabase.getDatabase(application);
        bookMarkMoviesDao = db.BookMarkDao();
    }


    public List<BookMarkedMovies> getAllBookmarkMovies(final GetDataInterface getDataInterface) {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (bookMarkMoviesDao.getAllBookMarkMovies() != null) {
                    int count = bookMarkMoviesDao.countRecords();
                    Log.d("count", String.valueOf(count));
                    mAllBookmarkMovies = bookMarkMoviesDao.getAllBookMarkMovies();
                    getDataInterface.getAllBookmarkMovies(mAllBookmarkMovies);
                }
            }
        });
        return mAllBookmarkMovies;
    }

    public void insert(final BookMarkedMovies bookMarkedMovies) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                bookMarkMoviesDao.insert(bookMarkedMovies);
            }
        });
    }

    public void delete(final String idItem) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                bookMarkMoviesDao.deleteByItemId(idItem);
            }
        });
    }

    public void checkIfItemIsAvailableInDatabase(final String idItem, final AdapterInterfaces adapterInterfaces) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
              boolean isAvailable =  bookMarkMoviesDao.checkIfItemIsAvailabeInDatabase(idItem);
                adapterInterfaces.checkIfMovieAvailableInDatabase(isAvailable);
            }
        });
    }

    public boolean checkIfItemIsAvailableInDatabase(final String idItem) {
        final boolean[] isAvailable = {false};
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                isAvailable[0] =  bookMarkMoviesDao.checkIfItemIsAvailabeInDatabase(idItem);
            }
        });
        return isAvailable[0];
    }

}
