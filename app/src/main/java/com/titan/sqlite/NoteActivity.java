package com.titan.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.titan.sqlite.models.Note;

public class NoteActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private LinedEditText linedEditText;
    private EditText editTitle;
    private TextView txtTitle;

    private boolean isNewNote;
    private Note note;

    private GestureDetector gestureDetector;

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

        setListeners();
    }


    private void setListeners(){
        linedEditText.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this,this);
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
