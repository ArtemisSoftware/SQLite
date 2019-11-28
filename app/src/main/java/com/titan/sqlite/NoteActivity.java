package com.titan.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.titan.sqlite.models.Note;
import com.titan.sqlite.persistence.NoteRepository;

public class NoteActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener, View.OnClickListener, TextWatcher {

    private LinedEditText linedEditText;
    private EditText editTitle;
    private TextView txtTitle;
    private RelativeLayout checkContainer, backArrowContainer;
    private ImageButton check, backArrow;

    private boolean isNewNote;
    private Note note;
    private Note finalNote;

    private GestureDetector gestureDetector;

    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;
    private int mode;
    private NoteRepository repository;


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

        repository = new NoteRepository(this);

        if(getIncomingIntent()) {
            //EDIT MODE
            setNewNoteProperties();
            enabledEditMode();
        }
        else{
            //VIEW MODE
            setNoteProperties();
            disableContentInteraction();
        }

        setListeners();
    }

    private void saveChanges(){
        if(isNewNote){
            saveNewNote();
        }
        else{

        }
    }


    private void saveNewNote(){
        repository.insertNoteTask(finalNote);
    }

    private void enabledEditMode(){
        backArrowContainer.setVisibility(View.GONE);
        checkContainer.setVisibility(View.VISIBLE);

        txtTitle.setVisibility(View.GONE);
        editTitle.setVisibility(View.VISIBLE);

        mode = EDIT_MODE_ENABLED;

        enableContentInteraction();
    }

    private void disableEditMode(){
        backArrowContainer.setVisibility(View.VISIBLE);
        checkContainer.setVisibility(View.GONE);

        txtTitle.setVisibility(View.VISIBLE);
        editTitle.setVisibility(View.GONE);

        mode = EDIT_MODE_DISABLED;

        disableContentInteraction();

        hideSoftKeyboard();

        String temp = linedEditText.getText().toString();
        temp = temp.replace("\n", "");
        temp = temp.replace(" ", "");

        if(temp.length() > 0){
            finalNote.setTitle(editTitle.getText().toString());
            finalNote.setContent(linedEditText.getText().toString());
            String timeStamp = "Jan 2019";

            finalNote.setTimestamp(timeStamp);

            if(!finalNote.getContent().equals(note.getContent()) || !finalNote.getTitle().equals(note.getTitle())){
                saveChanges();
            }
        }
    }

    private void hideSoftKeyboard(){

        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();

        if(view == null){
            view = new View(this);
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setListeners(){
        linedEditText.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this,this);
        txtTitle.setOnClickListener(this);
        check.setOnClickListener(this);
        backArrow.setOnClickListener(this);
        editTitle.addTextChangedListener(this);

    }

    private boolean getIncomingIntent(){
        if(getIntent().hasExtra("selected_note")) {
            note = getIntent().getParcelableExtra("selected_note");
            finalNote = getIntent().getParcelableExtra("selected_note");
            mode = EDIT_MODE_DISABLED;
            isNewNote = false;
            return false;
        }

        mode = EDIT_MODE_ENABLED;
        isNewNote = true;
        return true;
    }


    private void disableContentInteraction(){
        linedEditText.setKeyListener(null);
        linedEditText.setFocusable(false);
        linedEditText.setFocusableInTouchMode(false);
        linedEditText.setCursorVisible(false);
        linedEditText.clearFocus();
    }

    private void enableContentInteraction(){
        linedEditText.setKeyListener(new EditText(this).getKeyListener());
        linedEditText.setFocusable(true);
        linedEditText.setFocusableInTouchMode(true);
        linedEditText.setCursorVisible(true);
        linedEditText.requestFocus();
    }
    private void setNoteProperties(){

        txtTitle.setText(note.getTitle());
        editTitle.setText(note.getTitle());
        linedEditText.setText(note.getContent());
    }

    private void setNewNoteProperties(){

        txtTitle.setText("Note title");
        editTitle.setText("Note title");

        note = new Note();
        finalNote = new Note();
        note.setTitle("Note title");
        finalNote.setTitle("Note title");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("mode", mode);
    }


    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState){
        super.onRestoreInstanceState(saveInstanceState);

        mode = saveInstanceState.getInt("mode");

        if(mode == EDIT_MODE_ENABLED){
            enabledEditMode();
        }
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
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.toolbar_check:{
                hideSoftKeyboard();
                disableEditMode();
                break;
            }

            case R.id.note_text_title:{
                enabledEditMode();
                editTitle.requestFocus();
                editTitle.setSelection(editTitle.length());
                break;
            }

            case R.id.toolbar_back_arrow:{
                finish();
                break;
            }

        }
    }


    @Override
    public void onBackPressed() {

        if(mode == EDIT_MODE_ENABLED){
            onClick(check);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        txtTitle.setText((s.toString()));
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


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }



    @Override
    public void afterTextChanged(Editable s) {

    }
}
