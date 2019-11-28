package com.titan.sqlite.async;

import android.os.AsyncTask;

import com.titan.sqlite.models.Note;
import com.titan.sqlite.persistence.NoteDao;

public class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDao dao;

    public DeleteAsyncTask(NoteDao dao) {

        this.dao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        dao.delete(notes);
        return null;
    }
}
