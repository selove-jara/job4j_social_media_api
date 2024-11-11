package ru.job4j.social.media.api.security.userdetails;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.social.media.api.model.User;
import ru.job4j.social.media.api.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String fullName) throws UsernameNotFoundException {
        User user = userRepository.findByFullName(fullName)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + fullName));
        return UserDetailsImpl.build(user);
    }

}
