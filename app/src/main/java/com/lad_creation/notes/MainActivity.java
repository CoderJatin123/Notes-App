package com.lad_creation.notes;
import RecyclerView.RecyclerViewAdapterThis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import Model.Note;
import ViewModel.NoteViewModel;

public class MainActivity extends AppCompatActivity {
    private Button add_btn;
    RecyclerView recyclerView;
    NoteViewModel noteViewModel;
    RecyclerViewAdapterThis adapterThis;
    View rootLayout;
    Snackbar snackbar;
    private Note tempNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteViewModel= new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.initRepository(this);
        init();

        ItemTouchHelper swipeHelpere=new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0,ItemTouchHelper.END);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Note TMP=noteViewModel.getAllNotes().getValue().get(viewHolder.getAdapterPosition());
                noteViewModel.deleteNote(TMP);
                tempNote=TMP;
                snackbar.show();
            }
        });


        snackbar=Snackbar.make(rootLayout,"Note deleted.",2000).setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             noteViewModel.inserNote(tempNote);

            }
        });



        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(MainActivity.this,NotePad.class);
                startActivity(intent);
            }
        });
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapterThis=new RecyclerViewAdapterThis(MainActivity.this,notes);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(adapterThis);
                swipeHelpere.attachToRecyclerView(recyclerView);
            }
        });
    }
    void init(){
        rootLayout=findViewById(R.id.root);
        add_btn=findViewById(R.id.add_btn);
        recyclerView=findViewById(R.id.recyclerView);
    }

}