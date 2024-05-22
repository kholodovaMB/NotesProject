package com.HMB.NotesProject.users;
import com.HMB.NotesProject.security.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            log.info("User not found with username: " + username);
            throw new UsernameNotFoundException("User not found");
        }
        log.info("User found with username: " + username);
        return new MyUserDetails(user.get());
    }

    public Optional<User> findByUsername(String username) {
       return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
    }
}

