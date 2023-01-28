package Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import Database.AppDatabase;
import Database.NoteDao;
import Interfaces.Notes_DBMS;
import Model.Note;

public class NoteRepository implements Notes_DBMS {
    AppDatabase appDatabase;
    NoteDao noteDaos;
    private Context context;

    public NoteRepository(Context context) {
        this.context = context;
        appDatabase=AppDatabase.getDB(context);
        noteDaos=appDatabase.noteDao();

    }

    @Override
    public LiveData<List<Note>> getAllNotes() {
        return noteDaos.getAllNotes();
    }

    @Override
    public void insertNote(Note note) {
        noteDaos.insertNote(note);
    }

    @Override
    public void UpdateNoteDate(int id,String date) {
        noteDaos.UpdateNoteDate(id, date);
    }

    @Override
    public void deleteNote(Note note) {
        noteDaos.delete(note);
    }

    @Override
    public void UpdateNoteTitle(int id, String title) {
        noteDaos.UpdateNoteTitle(id,title);
    }

    @Override
    public void UpdateNoteDescription(int id, String description) {
        noteDaos.UpdateNoteDescription(id,description);
    }

    @Override
    public void UpdateNoteColor(int id, String color) {
        noteDaos.UpdateNoteColor(id,color);
    }

    @Override
    public void UpdateBookmark(int id, Boolean bookmark) {
        noteDaos.UpdateBookmark(id,bookmark);
    }

    @Override
    public void UpdateNoteTitleAndDesc(int id, String title, String desc) {
        noteDaos.UpdateNoteTitleAndDesc(id,title,desc);
    }
}
