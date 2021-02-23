package be.relive.Global.user.service;

import be.relive.Global.user.domain.User;
import be.relive.Global.user.exception.UserNotFoundException;
import be.relive.Global.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByFacebookId(String facebookId) {
        return userRepository.findBy(facebookId);
    }


    public User findById(UUID Id) {
        return userRepository.findById(Id)
                .orElseThrow(() -> new UserNotFoundException(Id));
    }


    public User save(User user) {
        return userRepository.save(user);
    }
}
