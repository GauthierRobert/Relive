package be.relive.Global.group.service;

import be.relive.Global.group.domain.Subscriber;
import be.relive.Global.group.dto.UserDto;
import be.relive.Global.group.repository.GroupRepository;
import be.relive.Global.group.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriberService {


    private final SubscriberRepository subscriberRepository;

    private final GroupRepository groupRepository;


    @Autowired
    public SubscriberService(SubscriberRepository subscriberRepository, GroupRepository groupRepository) {
        this.subscriberRepository = subscriberRepository;
        this.groupRepository = groupRepository;
    }

    /**
     * Subscribe all group present in userDto
     *
     * @param userDto
     */
    public void addSubscriber(UserDto userDto) {
        List<Subscriber> subscribers = userDto.getGroupsKeys().stream()
                .map(key -> (Subscriber.newSubscriber(groupRepository.findByKey(key), userDto)))
                .filter(subscriber -> subscriber.getGroup() != null)
                .collect(Collectors.toList());

        subscriberRepository.saveAll(subscribers);
    }

    /**
     * Unsubscribe all group present in userDto
     *
     * @param userDto
     */
    public void removeSubscriber(UserDto userDto) {

        userDto.getGroupsKeys().forEach(key -> subscriberRepository.deleteByKeyAndUserId(key, userDto.getId()));
    }

}
