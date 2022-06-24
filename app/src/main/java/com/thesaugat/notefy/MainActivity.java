package com.thesaugat.notefy;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thesaugat.notefy.db.DBHelper;
import com.thesaugat.notefy.db.NoteDataClass;
import com.thesaugat.notefy.db.NoteEntry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NoteItemClickLister {
    FloatingActionButton fab;
    RecyclerView notesRv;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setClickListners();
        dbHelper = new DBHelper(this);
        notesRv = findViewById(R.id.notesRv);
        setRv();
    }

    void setRv() {
        notesRv.setHasFixedSize(true);
        NotesAdapter notesAdapter = new NotesAdapter(this, dbHelper.getdata(), this);
        notesRv.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesRv.setAdapter(notesAdapter);
    }

    private void setClickListners() {
        fab = findViewById(R.id.floatingAction);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStartForResult.launch(new Intent(MainActivity.this, AddNoteActivity.class));
            }
        });
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if (dbHelper.insertNote(intent.getStringExtra(NoteEntry.COLUMN_NAME_TITLE), intent.getStringExtra(NoteEntry.COLUMN_NAME_DESC))) {
                            setRv();
                        }
                        // Handle the Intent
                    }
                }
            });


    @Override
    public void onNoteClick(NoteDataClass noteDataClass, int position) {


    }

    @Override
    public void onNoteLongClick(NoteDataClass noteDataClass, int position) {
        dbHelper.deleteData(noteDataClass.getId());
        setRv();
    }
}