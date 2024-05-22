package com.HMB.NotesProject.notes;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ModelAndView getAllNotes(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
//        log.info("User ID after authentication: " + userId);
        if (userId == null) {
//            log.info("userId null");
            return new ModelAndView("redirect:/login");
        }

        List<Note> notes = noteService.getAllNotesByUserId(userId);
        List<NoteDTO> noteDTOs = notes.stream()
                .map(NoteDTO::new)
                .collect(Collectors.toList());
        ModelAndView mav = new ModelAndView("notes");
        mav.addObject("notes", noteDTOs);
        return mav;
    }

    @GetMapping("/new")
    public ModelAndView showCreateNoteForm() {
        ModelAndView mav = new ModelAndView("notes/create-note");
        mav.addObject("note", new NoteDTO());
        return mav;
    }

    @PostMapping
    public ModelAndView createNote(@ModelAttribute NoteDTO noteDTO, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        log.info(userId.toString());
        if (userId == null) {
            return new ModelAndView("redirect:/login");
        }

        noteService.createNote(noteDTO.toEntity(), userId);
        return new ModelAndView("redirect:/notes");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditNoteForm(@PathVariable Integer id, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView("redirect:/login");
        }

        Note note = noteService.getNoteById(id, userId);
        ModelAndView mav = new ModelAndView("notes/update-note");
        mav.addObject("note", new NoteDTO(note));
        return mav;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView updateNote(@PathVariable Integer id, @ModelAttribute NoteDTO noteDTO, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView("redirect:/login");
        }

        noteService.updateNote(id, noteDTO.toEntity(), userId);
        return new ModelAndView("redirect:/notes");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteNote(@PathVariable Integer id, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView("redirect:/login");
        }

        noteService.deleteNoteById(id, userId);
        return new ModelAndView("redirect:/notes");
    }
}

