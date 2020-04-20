package ap.com.goscaleassignmentkotlinmvvm.Models.DataSources;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import ap.com.goscaleassignmentkotlinmvvm.Models.SearchMovies.Search;

public class SearchDataSourceFactory extends DataSource.Factory<Long, Search> {

    public MutableLiveData<SearchDataSource> userLiveDataSource=new MutableLiveData<>();

    @Override public DataSource<Long, Search> create() {
        SearchDataSource userDataSource = new SearchDataSource();
        userLiveDataSource.postValue(userDataSource);
        return userDataSource;
    }
}