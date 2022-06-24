package com.thesaugat.notefy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.thesaugat.notefy.db.DBHelper;
import com.thesaugat.notefy.db.NoteEntry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity {
    EditText title;
    EditText desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_note);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void finish() {
        title = findViewById(R.id.titleET);
        desc = findViewById(R.id.descET);
        if (!title.getText().toString().isEmpty() || !desc.getText().toString().isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra(NoteEntry.COLUMN_NAME_TITLE, title.getText().toString());
            intent.putExtra(NoteEntry.COLUMN_NAME_DESC , desc.getText().toString());
            setResult(Activity.RESULT_OK, intent);
        }
        super.finish();
    }

    @Override
    protected void onDestroy() {


        super.onDestroy();

    }
}