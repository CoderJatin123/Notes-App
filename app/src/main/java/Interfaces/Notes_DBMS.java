package Interfaces;

import androidx.lifecycle.LiveData;

import java.util.List;

import Model.Note;

public interface Notes_DBMS {
    LiveData<List<Note>> getAllNotes();
    void insertNote(Note note);
    void UpdateNoteDate(int id, String date);

    void deleteNote(Note note);
    void UpdateNoteTitle(int id,String title);
    void UpdateNoteDescription(int id, String description);
    void UpdateNoteTitleAndDesc(int id, String title,String desc);
    void UpdateNoteColor(int id, String color);
    void UpdateBookmark(int id,Boolean bookmark);
}
