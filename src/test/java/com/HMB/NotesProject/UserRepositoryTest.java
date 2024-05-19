package com.HMB.NotesProject;

import com.HMB.NotesProject.notes.Note;
import com.HMB.NotesProject.notes.NoteRepository;
import com.HMB.NotesProject.users.User;
import com.HMB.NotesProject.users.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Test
    @Transactional
    public void testCreateUserWithNotes() {
        User user = User.builder()
                .username("john_doe")
                .email("john.doe@example.com")
                .password("password")
                .build();

        Note note1 = Note.builder()
                .title("Note 1")
                .content("Content 1")
                .user(user)
                .build();

        Note note2 = Note.builder()
                .title("Note 2")
                .content("Content 2")
                .user(user)
                .build();

        user.setNotes(List.of(note1, note2));

        userRepository.save(user);

        User savedUser = userRepository.findById(user.getId()).orElse(null);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getNotes()).hasSize(2);
    }
}