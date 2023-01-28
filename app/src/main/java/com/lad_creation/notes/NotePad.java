package com.lad_creation.notes;

import static com.lad_creation.notes.R.layout.activity_note_pad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.Locale;

import Model.Note;
import Repository.NoteRepository;

public class NotePad extends AppCompatActivity {
  private TextView date;
  private EditText descripition, title;
  private String d_Description,d_Date,COLOR,d_title,c1,c2,defaultColor;
  private int NoteId;
  private FloatingActionButton color_btn,color1,color2;
  private NoteRepository noteRepository;
  private Dialog colorPalette;
  private Animation show_palette,hide_palette;
  private Boolean isColorPaletteOpen,isBookmarked,d_bookmark,isNewNote;
  private Bundle bundle;
  private ImageView bookmarkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_note_pad);
        init();
         bundle=getIntent().getExtras();
        if(bundle!=null){

            d_bookmark=bundle.getBoolean("bookmark");
            isBookmarked=d_bookmark;
            isNewNote=false;
            d_title=bundle.getString("title");
            d_Description=bundle.getString("des");
            defaultColor=bundle.getString("color");
            d_Date=bundle.getString("date");
           title.setText(d_title);
           descripition.setText(d_Description);
           date.setText(d_Date);
           NoteId= bundle.getInt("noteId",-1);
           color_btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(defaultColor)));
           COLOR=defaultColor;
        }

        else {
            d_bookmark=false;
            isBookmarked=false;
            isNewNote=true;

            date.setText(getDate());
            defaultColor = "#F4AB23";
            COLOR=defaultColor;
            d_Date=getDate();
        }

        if(d_bookmark==true)
            bookmarkView.setBackgroundResource(R.drawable.ic_baseline_bookmarked);
        else
            bookmarkView.setBackgroundResource(R.drawable.ic_baseline_bookmark_add);

    }

    private void setClickListener() {
      color1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              color_btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(c1)));
              COLOR=c1;
              changeColor(v);
          }
      });
      color2.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              color_btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(c2)));
              COLOR=c2;
              changeColor(v);
          }
      });
    }

    private void init(){
        date=findViewById(R.id.date);
        title=findViewById(R.id.note_title);
        descripition=findViewById(R.id.note_descripition);
        color_btn=findViewById(R.id.color_btn);
        color1=findViewById(R.id.color_blue);
        color2=findViewById(R.id.color_green);
        noteRepository = new NoteRepository(this);
        colorPalette=new Dialog(this);
        show_palette=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.show_palette);
        hide_palette=AnimationUtils.loadAnimation(this,R.anim.hide_palette);
        isColorPaletteOpen=false;
        bookmarkView=findViewById(R.id.bookmark);
    }

    public void changeColor(View view){


        if(isColorPaletteOpen){

            color1.startAnimation(hide_palette);
            color2.startAnimation(hide_palette);
            color1.setEnabled(false);
            color2.setEnabled(false);
            color1.setVisibility(View.GONE);
            color2.setVisibility(View.GONE);
            isColorPaletteOpen=false;

        }
        else {
            color1.setEnabled(true);
            color2.setEnabled(true);
            color1.setVisibility(View.VISIBLE);
            color2.setVisibility(View.VISIBLE);
            color1.startAnimation(show_palette);
            color2.startAnimation(show_palette);

            isColorPaletteOpen=true;

            if(COLOR.equals("#63C3FA")){
                //default blue
                c1="#F4AB23";
                c2="#2BD099";
                color1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F4AB23")));  //orange
                color2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2BD099")));  //green
            }
            else if(COLOR.equals("#2BD099")){
                //green
                color1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F4AB23")));  //orange
                color2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#63C3FA")));  //blue

                c1="#F4AB23";
                c2="#63C3FA";

            }
            else{
                //orange
                color1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#63C3FA")));  //blue
                color2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2BD099")));  //green

                c1="#63C3FA";
                c2="#2BD099";
            }

            setClickListener();
        }

    }

    private String getDate(){
        final String[] Days={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        String DateToStr = null;
        Date curDate = new Date();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            format = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
            DateToStr = Days[curDate.getDay()]+", "+format.format(curDate);
        }
        return DateToStr ;
    }

    public void onBookmarkClicked(View view){

        if(isBookmarked==true){
            isBookmarked=false;
            bookmarkView.setBackgroundResource(R.drawable.ic_baseline_bookmark_add);
        }
        else{
            isBookmarked=true;
            bookmarkView.setBackgroundResource(R.drawable.ic_baseline_bookmarked);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        String Title = title.getText().toString();
        String Description = new String();
        Description = descripition.getText().toString();


        if (isNewNote && (!Title.isEmpty() || !Description.isEmpty())) {
            Note note = new Note(Title, Description, getDate(), COLOR,isBookmarked);
            new CreateNote(noteRepository).execute(note);


        }
        else{

            if(!Title.equals(d_title) && Description.equals(d_Description) && NoteId!=-1){
                new UpdateTitle(noteRepository,NoteId,Title).execute();
            }

            if(Title.equals(d_title) && !Description.equals(d_Description) && NoteId!=-1){
                new UpdateDescription(noteRepository,NoteId,Description).execute();
            }
            if(!Title.equals(d_title) && !Description.equals(d_Description) && NoteId!=-1){
               new UpdateNoteTitleAndDesc(noteRepository,NoteId,Title,Description).execute();
            }

             if(!COLOR.equals(defaultColor) && NoteId!=-1){
                new UpdateColor(noteRepository,NoteId,COLOR).execute();
            }

             if(!d_Date.equals(getDate()) && NoteId!=-1){
                new UpdateNoteDate(noteRepository,NoteId,getDate()).execute();
            }
             if(!d_bookmark.equals(isBookmarked) && NoteId!=-1){
                new UpdateBookmark(noteRepository,NoteId,isBookmarked).execute();

            }

        }





    }
    public static class CreateNote extends AsyncTask<Note,Void,Void> {
        NoteRepository noteRepository;

        public CreateNote(NoteRepository noteRepository) {
            this.noteRepository = noteRepository;
        }

        @Override
        protected Void doInBackground(Note... note) {
            noteRepository.insertNote(note[0]);
            return null;
        }


    }
    public static class UpdateTitle extends AsyncTask<Note,Void,Void> {
        NoteRepository noteRepository;
        int id;
        String title;
        public UpdateTitle(NoteRepository noteRepository,int id,String title) {
            this.noteRepository = noteRepository;
            this.id=id;
            this.title=title;
        }

        @Override
        protected Void doInBackground(Note... note) {
            noteRepository.UpdateNoteTitle(id,title);
            return null;
        }


    }
    public static class UpdateDescription extends AsyncTask<Note,Void,Void> {
        NoteRepository noteRepository;
        int id;
        String desc;
        public UpdateDescription(NoteRepository noteRepository,int id,String desc) {
            this.noteRepository = noteRepository;
            this.id=id;
            this.desc=desc;
        }

        @Override
        protected Void doInBackground(Note... note) {
            noteRepository.UpdateNoteDescription(id,desc);
            return null;
        }


    }
    public static class UpdateColor extends AsyncTask<Note,Void,Void> {
        NoteRepository noteRepository;
        int id;
        String color;
        public UpdateColor(NoteRepository noteRepository,int id,String color) {
            this.noteRepository = noteRepository;
            this.id=id;
            this.color=color;
        }

        @Override
        protected Void doInBackground(Note... note) {
            noteRepository.UpdateNoteColor(id,color);
            return null;
        }


    }
    public static class UpdateNoteTitleAndDesc extends AsyncTask<Note,Void,Void> {

        NoteRepository noteRepository;
        int id;
        String title,desc,color,date;

        public UpdateNoteTitleAndDesc(NoteRepository noteRepository, int id, String title, String desc) {
            this.noteRepository = noteRepository;
            this.id = id;
            this.title = title;
            this.desc = desc;
        }

        @Override
        protected Void doInBackground(Note... note) {
            noteRepository.UpdateNoteTitleAndDesc(id,title,desc);
            return null;
        }


    }
    public static class UpdateNoteDate extends AsyncTask<Note,Void,Void> {

        NoteRepository noteRepository;
        int id;
        String date;

        public UpdateNoteDate(NoteRepository noteRepository,int id,String date) {
            this.noteRepository = noteRepository;
            this.date= date;
            this.id=id;

        }

        @Override
        protected Void doInBackground(Note... note) {
            noteRepository.UpdateNoteDate(id,date);
            return null;
        }


    }
    public static class UpdateBookmark extends AsyncTask<Note,Void,Void> {

        NoteRepository noteRepository;
        int id;
        Boolean bookmark;

        public UpdateBookmark(NoteRepository noteRepository,int id,Boolean bookmark) {
            this.noteRepository = noteRepository;
            this.id=id;
            this.bookmark= bookmark;

        }

        @Override
        protected Void doInBackground(Note... note) {
            noteRepository.UpdateBookmark(id,bookmark);
            return null;
        }


    }
}