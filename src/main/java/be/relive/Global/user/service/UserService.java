package be.relive.Global.user.service;

import be.relive.Global.event.repository.AttendantRepository;
import be.relive.Global.group.repository.SubscriberRepository;
import be.relive.Global.user.domain.User;
import be.relive.Global.user.exception.UserNotFoundException;
import be.relive.Global.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AttendantRepository attendantRepository;
    private final SubscriberRepository subscriberRepository;

    public UserService(UserRepository userRepository, AttendantRepository attendantRepository, SubscriberRepository subscriberRepository) {
        this.userRepository = userRepository;
        this.attendantRepository = attendantRepository;
        this.subscriberRepository = subscriberRepository;
    }

    public User findByFacebookId(String facebookId) {
        return userRepository.findBy(facebookId);
    }

    public void updateNotificationToken(UUID id, String token) {
        userRepository.updateNotificationToken(id.toString(), token);
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }


    public User save(User user) {
        attendantRepository.updateAttendant(user.getId(), user.getProfile().getPicture().getData().getUrl(), user.getProfile().getName());
        subscriberRepository.updateSubscriber(user.getId(), user.getProfile().getPicture().getData().getUrl(), user.getProfile().getName());
        return userRepository.save(user);

    }
}
