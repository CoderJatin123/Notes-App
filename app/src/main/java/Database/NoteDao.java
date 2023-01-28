package Database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Model.Note;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM notesTable ORDER BY noteId ASC")
    LiveData<List<Note>> getAllNotes();

    @Insert
    Void insertNote(Note note);

    @Delete
    void delete(Note note);

    @Query("UPDATE notesTable SET Titile=:title WHERE noteId=:id" )
    void UpdateNoteTitle(int id,String title);

    @Query("UPDATE notesTable SET Descripition=:desc WHERE noteId=:id" )
    void UpdateNoteDescription(int id, String desc);

    @Query("UPDATE notesTable SET Color=:color WHERE noteId=:id" )
    void UpdateNoteColor(int id, String color);

    @Query("UPDATE notesTable SET Titile=:title,Descripition=:desc WHERE noteId=:id")
    void UpdateNoteTitleAndDesc(int id, String title,String desc);

    @Query("UPDATE notesTable SET Date=:date WHERE noteId=:id")
    void UpdateNoteDate(int id,String date);

    @Update(onConflict = REPLACE)
    void UpdateNote(Note note);

    @Query("UPDATE notesTable SET isBookmarked=:bookmark WHERE noteId=:id")
    void UpdateBookmark(int id,Boolean bookmark);
}
