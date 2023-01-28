package ViewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import Model.Note;
import Repository.NoteRepository;

public  class NoteViewModel extends AndroidViewModel {


    public LiveData<List<Note>> liveNoteList;
    private Context context;
    private NoteRepository noteRepository;
    public MutableLiveData mutableLiveData;

    public NoteViewModel(Application application) {
        super(application);

    }
    public void inserNote(Note note){
        noteRepository.insertNote(note);
    }

    public void initRepository(Context context){
        this.context=context;
        noteRepository=new NoteRepository(context);
        liveNoteList=noteRepository.getAllNotes();
    }
    public LiveData<List<Note>> getAllNotes(){
       return liveNoteList;
    }

    public void deleteNote(Note note){
        noteRepository.deleteNote(note);
    }

}
