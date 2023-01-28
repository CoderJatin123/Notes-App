package Model;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "notesTable")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int noteId;

    public int getNoteId() {
        return noteId;
    }

    @NotNull
    public String getTitile() {
        return Titile;
    }

    public String getDescripition() {
        return Descripition;
    }

    @NotNull
    public String getUpdateddate() {
        return Updateddate;
    }

    @NotNull
    public String getColor() {
        return Color;
    }

    @NotNull
    @ColumnInfo(name = "Titile")
    public String Titile;

    @ColumnInfo(name = "Descripition")
    public String Descripition;
    
    @ColumnInfo(name = "Date")
    public String Updateddate;

    @ColumnInfo(name = "Color")
    public String Color;

    public Boolean getBookmarked() {
        return isBookmarked;
    }

    @ColumnInfo(name = "isBookmarked")
    public Boolean isBookmarked;


    public Note(int noteId, @NotNull String titile, String descripition, @NotNull String updateddate,String color, Boolean isbookmarked) {
        this.noteId = noteId;
        Color=color;
        Titile = titile;
        Descripition = descripition;
        Updateddate = updateddate;
        isBookmarked=isbookmarked;

    }
    @Ignore
    public Note( @NotNull String titile, String descripition, @NotNull String updateddate,@NotNull String color, Boolean bookmarked) {

        Titile = titile;
        Color=color;
        Descripition = descripition;
        Updateddate = updateddate;
        isBookmarked=bookmarked;
    }

    public Note(){}
}
