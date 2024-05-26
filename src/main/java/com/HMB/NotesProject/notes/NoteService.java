package com.HMB.NotesProject.notes;

import com.HMB.NotesProject.users.User;
import com.HMB.NotesProject.users.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    @Autowired
    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public Note getNoteById(Integer noteId, Integer userId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("Note not found with id: " + noteId));
        if (!note.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Note with id " + noteId + " does not belong to user with id " + userId);
        }
        return note;
    }

    public Note createNote(Note note, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        note.setUser(user);
        return noteRepository.save(note);
    }

    public Note updateNote(Integer noteId, Note updatedNote, Integer userId) {
        Note existingNote = getNoteById(noteId, userId);

        existingNote.setTitle(updatedNote.getTitle());
        existingNote.setContent(updatedNote.getContent());

        return noteRepository.save(existingNote);
    }

    public void deleteNoteById(Integer noteId, Integer userId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("Note not found with id: " + noteId));
        if (!note.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Note with id " + noteId + " does not belong to user with id " + userId);
        }
        noteRepository.delete(note);
    }

    public List<Note> getAllNotesByUserId(Integer userId) {
        return noteRepository.findAllByUserId(userId);
    }
}


