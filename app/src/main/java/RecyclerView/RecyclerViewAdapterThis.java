package RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lad_creation.notes.NotePad;
import com.lad_creation.notes.R;

import java.util.List;

import Model.Note;

public class RecyclerViewAdapterThis extends RecyclerView.Adapter<RecyclerViewAdapterThis.ViewHolder> {

    private final Context context;
    private  List<Note> noteList;

    public RecyclerViewAdapterThis(Context context,List<Note> list) {
        this.context = context;
        noteList=list;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterThis.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterThis.ViewHolder holder, int position) {

        String color=noteList.get(position).Color;
        Boolean bookmark=noteList.get(position).getBookmarked();
        if(color!=null)
            holder.cardView.setCardBackgroundColor(Color.parseColor(color));
        if(bookmark==true)
            holder.bookMarkIcon.setVisibility(View.VISIBLE);
    holder.title_view.setText(noteList.get(position).getTitile());
    holder.desc_view.setText(noteList.get(position).getDescripition());

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView title_view,desc_view;
        ImageView bookMarkIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.note_card);
            title_view=itemView.findViewById(R.id.title_item);
            desc_view=itemView.findViewById(R.id.desc_item);
            bookMarkIcon=itemView.findViewById(R.id.bookmark_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context, NotePad.class);
                    Bundle bundle=new Bundle();
                    bundle.putInt("noteId",noteList.get(getAdapterPosition()).getNoteId());
                    bundle.putString("title",noteList.get(getAdapterPosition()).getTitile());
                    bundle.putString("des",noteList.get(getAdapterPosition()).getDescripition());
                    bundle.putString("date",noteList.get(getAdapterPosition()).getUpdateddate());
                    bundle.putString("color",noteList.get(getAdapterPosition()).getColor());
                    bundle.putBoolean("bookmark",noteList.get(getAdapterPosition()).getBookmarked());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }

    }
    public void updateRecyclerView(List<Note> list){
        noteList.clear();
        notify();
    }
}
