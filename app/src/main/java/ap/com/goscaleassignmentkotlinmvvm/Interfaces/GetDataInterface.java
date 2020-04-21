package ap.com.goscaleassignmentkotlinmvvm.Interfaces;

import java.util.List;

import ap.com.goscaleassignmentkotlinmvvm.Database.BookMarkedMovies;

public interface GetDataInterface {

    public void getAllBookmarkMovies(List<BookMarkedMovies> getAllBookmarkMovies);

    public void updateBookMarkList();

}
