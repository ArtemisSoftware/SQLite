package com.titan.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.titan.sqlite.models.Note;

public class NoteActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private LinedEditText linedEditText;
    private EditText editTitle;
    private TextView txtTitle;
    private RelativeLayout checkContainer, backArrowContainer;
    private ImageButton check, backArrow;

    private boolean isNewNote;
    private Note note;

    private GestureDetector gestureDetector;

    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;
    private int mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        linedEditText = findViewById(R.id.note_text);
        editTitle = findViewById(R.id.note_edit_title);
        txtTitle = findViewById(R.id.note_text_title);
        checkContainer = findViewById(R.id.check_container);
        backArrowContainer = findViewById(R.id.back_arrow_container);
        check = findViewById(R.id.toolbar_check);
        backArrow = findViewById(R.id.toolbar_back_arrow);

        if(getIncomingIntent()) {
            //EDIT MODE
            setNewNoteProperties();
            enabledEditMode();
        }
        else{
            //VIEW MODE
            setNoteProperties();
        }

        setListeners();
    }

    private void enabledEditMode(){
        backArrowContainer.setVisibility(View.GONE);
        checkContainer.setVisibility(View.VISIBLE);

        txtTitle.setVisibility(View.GONE);
        editTitle.setVisibility(View.VISIBLE);

        mode = EDIT_MODE_ENABLED;
    }

    private void disableEditMode(){
        backArrowContainer.setVisibility(View.VISIBLE);
        checkContainer.setVisibility(View.GONE);

        txtTitle.setVisibility(View.VISIBLE);
        editTitle.setVisibility(View.GONE);

        mode = EDIT_MODE_DISABLED;
    }


    private void setListeners(){
        linedEditText.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this,this);
    }

    private boolean getIncomingIntent(){
        if(getIntent().hasExtra("selected_note")) {
            note = getIntent().getParcelableExtra("selected_note");
            mode = EDIT_MODE_DISABLED;
            isNewNote = false;
            return false;
        }

        mode = EDIT_MODE_ENABLED;
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
    public boolean onDoubleTap(MotionEvent e) {
        enabledEditMode();
        return false;
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
