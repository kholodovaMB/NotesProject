package com.HMB.NotesProject.notes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> findAllByUserId(Integer userId);
}
