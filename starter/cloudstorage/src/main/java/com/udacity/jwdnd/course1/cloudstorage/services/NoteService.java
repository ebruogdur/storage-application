package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }


    public List<Note> getAllNotes() {
        return noteMapper.getAllNotes();
    }


    public Note getNote(int noteId) {
        return noteMapper.getNote(noteId);
    }

    public int createNote(Note note) {

        return noteMapper.insertNote(new Note(null,note.getNoteTitle(),note.getNoteDescription(),note.getUserId()));

    }

    public void updateNote(Note note) {
        noteMapper.updateNote(note);
    }


    public void deleteNote(Note note ) {


        noteMapper.deleteNote(note.getNoteId());
    }



}
