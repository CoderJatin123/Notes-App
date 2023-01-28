package ViewModel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.Closeable;
import java.util.List;

import Model.Note;
import Repository.NoteRepository;

class Viewmodel extends ViewModel {
    public LiveData<List<Note>> liveNotes;
    private final NoteRepository noteRepository;

    public Viewmodel(Context context) {
        noteRepository=new NoteRepository(context);

    }

    public LiveData<List<Note>> getAllNotes(){
        return liveNotes;
    }
    public void insertNote(Note note){
        noteRepository.insertNote(note);
       // live_mutable_Notes.setValue(noteRepository.getAllNotes());
    }

}
