package com.HMB.NotesProject.notes;

import com.HMB.NotesProject.security.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RestController
@RequestMapping("/api/notes")
public class NoteControllerApi {

    private final NoteService noteService;

    public NoteControllerApi(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Integer userId = userDetails.getId();

        log.info("User ID after authentication: " + userId);
        List<Note> notes = noteService.getAllNotesByUserId(userId);
        List<NoteDTO> noteDTOs = notes.stream()
                .map(NoteDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(noteDTOs);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createNote(@RequestBody NoteDTO noteDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Integer userId = userDetails.getId();
        noteService.createNote(noteDTO.toEntity(), userId);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Integer userId = userDetails.getId();

        Note note = noteService.getNoteById(id, userId);
        return note != null ? ResponseEntity.status(HttpStatus.OK).body(new NoteDTO(note)) : ResponseEntity.notFound().build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable Integer id, @RequestBody NoteDTO noteDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Integer userId = userDetails.getId();

        Note updatedNote = noteService.updateNote(id, noteDTO.toEntity(), userId);
        return updatedNote != null ? ResponseEntity.status(HttpStatus.OK).body(new NoteDTO(updatedNote)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Integer userId = userDetails.getId();

        noteService.deleteNoteById(id, userId);
    }
}
