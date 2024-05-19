package com.HMB.NotesProject.notes;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
        if (userId == null) {
            return new ModelAndView("redirect:/login");
        }

        List<Note> notes = noteService.getAllNotesByUserId(userId);
        ModelAndView mav = new ModelAndView("notes");
        mav.addObject("notes", notes);
        return mav;
    }

    @GetMapping("/new")
    public ModelAndView showCreateNoteForm() {
        ModelAndView mav = new ModelAndView("create-note");
        mav.addObject("note", new Note());
        return mav;
    }

    @PostMapping
    public ModelAndView createNote(@ModelAttribute Note note, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView("redirect:/login");
        }

        noteService.createNote(note, userId);
        return new ModelAndView("redirect:/notes");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditNoteForm(@PathVariable Integer id, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView("redirect:/login");
        }

        Note note = noteService.getNoteById(id, userId);
        ModelAndView mav = new ModelAndView("update-note");
        mav.addObject("note", note);
        return mav;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView updateNote(@PathVariable Integer id, @ModelAttribute Note note, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView("redirect:/login");
        }

        noteService.updateNote(id, note, userId);
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

