package com.HMB.NotesProject.notes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
    private Integer id;
    private Integer userId;
    private String title;
    private String content;
    private LocalDate createdAt;

    public NoteDTO(Note note) {
        this.id = note.getId();
        this.userId = note.getUser().getId();
        this.title = note.getTitle();
        this.content = note.getContent();
        this.createdAt = LocalDate.from(note.getCreatedAt());
    }

    public static NoteDTO fromNote(Note note) {
        NoteDTO dto = new NoteDTO();
        dto.setId(note.getId());
        dto.setUserId(note.getUser().getId());
        dto.setTitle(note.getTitle());
        dto.setContent(note.getContent());
        dto.setCreatedAt(note.getCreatedAt().toLocalDate());
        return dto;
    }
    public Note toEntityId(Integer userId) {
        Note note = new Note();
        note.setId(this.id);
        note.setTitle(this.title);
        note.setContent(this.content);
        return note;
    }
    public Note toEntity() {
        Note note = new Note();
        note.setId(this.id);
        note.setTitle(this.title);
        note.setContent(this.content);
        return note;
    }
}