package be.relive.Global.user.service;

import be.relive.Global.user.domain.Subscription;
import be.relive.Global.user.domain.User;
import be.relive.Global.user.repository.SubscriptionRepository;
import be.relive.Global.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final UserRepository userRepository;


    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }


    public void subscribe(User user, String key) {
        subscriptionRepository.save(new Subscription(key, user));
    }

    public void unSubscribe(User user, String key) {
        subscriptionRepository.deleteByKeyAndUserId(key, user.getId());
    }


}
