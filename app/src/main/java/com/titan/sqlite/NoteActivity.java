package com.titan.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.titan.sqlite.models.Note;

public class NoteActivity extends AppCompatActivity {

    private LinedEditText linedEditText;
    private EditText editTitle;
    private TextView txtTitle;

    private boolean isNewNote;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        linedEditText = findViewById(R.id.note_text);
        editTitle = findViewById(R.id.note_edit_title);
        txtTitle = findViewById(R.id.note_text_title);

        if(getIncomingIntent()) {
            //EDIT MODE
            setNewNoteProperties();
        }
        else{
            //VIEW MODE
            setNoteProperties();
        }
    }

    private boolean getIncomingIntent(){
        if(getIntent().hasExtra("selected_note")) {
            note = getIntent().getParcelableExtra("selected_note");
            isNewNote = false;
            return false;
        }

        isNewNote = true;
        return true;
    }

    private void setNoteProperties(){

        txtTitle.setText(note.getTitle());
        editTitle.setText(note.getTitle());
        linedEditText.setText(note.getContent());
    }

    private void setNewNoteProperties(){

        txtTitle.setText("Note title");
        editTitle.setText("Note title");
    }
}
