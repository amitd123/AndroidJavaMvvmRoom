package ap.com.goscaleassignmentkotlinmvvm.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "bookmarked_movies_table")
public class BookMarkedMovies {

    public BookMarkedMovies(@NonNull String imdbID, @NonNull String Title, @NonNull String Year, @NonNull String Type, @NonNull String Poster, @NonNull boolean IsBookmark) {
        this.imdbID = imdbID;
        this.Title = Title;
        this.Year = Year;
        this.Type = Type;
        this.Poster = Poster;
        this.IsBookmark = IsBookmark;
    }













    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "imdbID")
//    @SerializedName("imdbID")
//    @Expose
    private String imdbID;

    @NonNull
    public String getImdbID() {
        return imdbID;
    }



    public String getimdbID() {
        return this.imdbID;
    }
    public void setImdbID(@NonNull String imdbID) {
        this.imdbID = imdbID;
    }

    @NonNull
    @ColumnInfo(name = "Title")
//    @SerializedName("Title")
//    @Expose
    private String Title;

    public String getTitle() {
        return this.Title;
    }
    public void setTitle(@NonNull String title) {
        Title = title;
    }
    @NonNull
    @ColumnInfo(name = "Year")
//    @SerializedName("Year")
//    @Expose
    private String Year;

    public String getYear() {
        return this.Year;
    }
    public void setYear(@NonNull String year) {
        Year = year;
    }
    @NonNull
    @ColumnInfo(name = "Type")
//    @SerializedName("Type")
//    @Expose
    private String Type;

    public String getType() {
        return this.Type;
    }
    public void setType(@NonNull String type) {
        Type = type;
    }
    @NonNull
    @ColumnInfo(name = "Poster")
//    @SerializedName("Poster")
//    @Expose
    private String Poster;

    public String getPoster() {
        return this.Poster;
    }
    public void setPoster(@NonNull String poster) {
        Poster = poster;
    }
    @NonNull
    @ColumnInfo(name = "IsBookmark")
//    @SerializedName("IsBookmark")
//    @Expose
    private boolean IsBookmark;

    public boolean getIsBookmark() {
        return this.IsBookmark;
    }
    public boolean isBookmark() {
        return IsBookmark;
    }
    public void setBookmark(boolean bookmark) {
        IsBookmark = bookmark;
    }
}
