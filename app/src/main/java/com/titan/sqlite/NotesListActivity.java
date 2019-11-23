package com.titan.sqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.titan.sqlite.adapters.NotesRecyclerAdapter;
import com.titan.sqlite.models.Note;
import com.titan.sqlite.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;

public class NotesListActivity extends AppCompatActivity implements NotesRecyclerAdapter.OnNoteListener {


    private RecyclerView recyclerView;

    private ArrayList<Note> notes = new ArrayList<>();
    private NotesRecyclerAdapter notesRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        recyclerView = findViewById(R.id.recyclerView);

        initRecyclerView();
        insertFakeNotes();

        setSupportActionBar((Toolbar) findViewById(R.id.notes_toolbar));
        setTitle("Notes");
    }

    private void insertFakeNotes(){
        for(int i = 0; i < 1000; i++){
            Note note = new Note();
            note.setTitle("title #" + i);
            note.setContent("content #: " + i);
            note.setTimestamp("Jan 2019");
            notes.add(note);
        }
        notesRecyclerAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        recyclerView.addItemDecoration(itemDecorator);

        notesRecyclerAdapter = new NotesRecyclerAdapter(notes, this);
        recyclerView.setAdapter(notesRecyclerAdapter);
    }


    @Override
    public void onNoteClick(int position) {

        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("selected_note", notes.get(position));
        startActivity(intent);
    }
}
