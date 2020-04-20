package ap.com.goscaleassignmentkotlinmvvm.Database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ap.com.goscaleassignmentkotlinmvvm.Models.SearchMovies.MoviesSearchResponseModel;
import ap.com.goscaleassignmentkotlinmvvm.Networking.ApiService;
import ap.com.goscaleassignmentkotlinmvvm.Networking.RetrofitService;
import ap.com.goscaleassignmentkotlinmvvm.Networking.SearchRepository;
import ap.com.goscaleassignmentkotlinmvvm.Utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRoomRespository {
    public static BookMarkMoviesDao bookMarkMoviesDao;
    private LiveData<List<BookMarkedMovies>> mAllBookmarkMovies = new MutableLiveData<>();


    private static MoviesRoomRespository moviesRoomRespository;

   /* public static SearchRepository getInstance(){
        if (searchRepository == null){
            searchRepository = new SearchRepository();
        }
        return searchRepository;
    }*/





    public static MoviesRoomRespository getInstance(Application application) {
        if (moviesRoomRespository == null){
            moviesRoomRespository = new MoviesRoomRespository();
        }
        MoviesRoomDatabase db = MoviesRoomDatabase.getDatabase(application);
        bookMarkMoviesDao = db.BookMarkDao();
      return moviesRoomRespository;
    }

    public LiveData<List<BookMarkedMovies>> getAllBookmarkMovies() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (bookMarkMoviesDao.getAllBookMarkMovies() != null){
//                    mAllBookmarkMovies =  bookMarkMoviesDao.getAllBookMarkMovies();
                    mAllBookmarkMovies =  bookMarkMoviesDao.getAllBookMarkMovies();
//                    Log.d("sizeTableDataCount",String.valueOf(mAllBookmarkMovies.getValue().size()) );
                }
            }
        });
        return mAllBookmarkMovies;
    }

    public void insert (final BookMarkedMovies bookMarkedMovies) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                bookMarkMoviesDao.insert(bookMarkedMovies);
               int count =  bookMarkMoviesDao.countRecords();
                Log.d("count",String.valueOf(count));
//                mAllBookmarkMovies =  bookMarkMoviesDao.getAllBookMarkMovies();
//                Log.d("getImdbID",mAllBookmarkMovies.getValue().get(0).getImdbID());
            }
        });
//        new insertAsyncTask((BookMarkMoviesDao) mAllBookmarkMovies).execute(bookMarkedMovies);
    }

    private static class insertAsyncTask extends AsyncTask<BookMarkedMovies, Void, Void> {

        private BookMarkMoviesDao mAsyncTaskDao;

        insertAsyncTask(BookMarkMoviesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final BookMarkedMovies... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void deleteItemById(String idItem) {
        new deleteByIdAsyncTask(bookMarkMoviesDao).execute(idItem);
    }

    private static class deleteByIdAsyncTask extends AsyncTask<String, Void, Void> {
        private BookMarkMoviesDao mAsyncTaskDao;
        deleteByIdAsyncTask(BookMarkMoviesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            mAsyncTaskDao.deleteByItemId(params[0]);
            return null;
        }
    }
}
