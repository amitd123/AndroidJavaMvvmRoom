package ap.com.goscaleassignmentkotlinmvvm.Database;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface BookMarkMoviesDao {

//    @Insert
    @Insert(onConflict = IGNORE)
    void insert(BookMarkedMovies bookMarkedMovies);

    @Query("DELETE FROM bookmarked_movies_table")
    void deleteAll();

    @Query("SELECT * FROM bookmarked_movies_table")
    List<BookMarkedMovies> getAllBookMarkMovies();

    @Query("SELECT Title FROM bookmarked_movies_table")
    LiveData<String> getTitleBookMarkMovies();

    //Delete one item by id
    @Query("DELETE FROM bookmarked_movies_table WHERE imdbID = :itemId")
    void deleteByItemId(String  itemId);

    @Query("SELECT COUNT(imdbID) FROM bookmarked_movies_table")
    int countRecords();

    @Query("SELECT imdbID FROM bookmarked_movies_table WHERE imdbID = :itemId")
    boolean checkIfItemIsAvailabeInDatabase(String itemId);

}
