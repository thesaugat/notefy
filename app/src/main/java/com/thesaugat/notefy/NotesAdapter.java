package com.thesaugat.notefy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thesaugat.notefy.db.NoteDataClass;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    LayoutInflater inflater;
    Context context;
    List<NoteDataClass> noteDataClassList;
    NoteItemClickLister noteItemClickLister;

    public NotesAdapter(Context context, List<NoteDataClass> noteDataClassList, NoteItemClickLister noteItemClickLister) {
        this.context = context;
        this.noteDataClassList = noteDataClassList;
        this.noteItemClickLister = noteItemClickLister;
        inflater = LayoutInflater.from(
                context
        );
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_note, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int p = holder.getAdapterPosition();
        holder.title.setText(noteDataClassList.get(p).getTitle());
        holder.desc.setText(noteDataClassList.get(p).getDesc());
        holder.date.setText(noteDataClassList.get(p).getDateTime());
        holder.mainLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteItemClickLister.onNoteClick(noteDataClassList.get(p),p);
            }
        });
        holder.mainLL.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                noteItemClickLister.onNoteLongClick(noteDataClassList.get(p), p);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return noteDataClassList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView desc;
        TextView date;
        LinearLayout mainLL;


        public ViewHolder(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.titleTV);
            desc = v.findViewById(R.id.descTV);
            date = v.findViewById(R.id.dateTV);
            mainLL = v.findViewById(R.id.mainLL);
        }
    }
}

interface NoteItemClickLister {
    void onNoteClick(NoteDataClass noteDataClass, int position);

    void onNoteLongClick(NoteDataClass noteDataClass, int position);

}