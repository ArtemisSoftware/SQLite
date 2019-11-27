package com.titan.sqlite.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.titan.sqlite.models.Note;

import java.util.List;

public class NoteRepository {

    private NoteDataBase noteDataBase;

    public NoteRepository(Context context) {
        noteDataBase = NoteDataBase.getInstace(context);
    }

    public void insertNoteTask(Note note){

    }

    public void updateNoteTask(Note note){

    }

    public LiveData<List<Note>> getNotesTask(){

        return null;
    }

    public void deleteNote(Note note){

    }
}
