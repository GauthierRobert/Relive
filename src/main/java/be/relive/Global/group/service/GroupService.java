package be.relive.Global.group.service;

import be.relive.Global.dto.GroupDto;
import be.relive.Global.dto.UserDto;
import be.relive.Global.group.domain.Group;
import be.relive.Global.group.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }


    public List<GroupDto> findAllbyKeys(List<String> keys) {
        return groupRepository.findAllByKeys(keys).stream().map(this::convert).collect(toList());
    }

    public Group save(GroupDto group) {
        return groupRepository.save(convert(group));
    }

    private GroupDto convert(Group group) {
        return new GroupDto(group.getKey(), group.getDescription(), group.getTitle(),
                getSubscribers(group));
    }

    private List<UserDto> getSubscribers(Group group) {
        return group.getSubscribers().stream()
                .map(subscriber -> new UserDto(subscriber.getUserId(), subscriber.getCompleteName(), subscriber.getImageUrl()))
                .collect(toList());
    }

    private Group convert(GroupDto group) {
        if (group.getKey() != null && !group.getKey().isBlank()) {
            return Group.group(group.getKey(), group.getDescription(), group.getTitle());
        }
        return Group.newGroup(group.getDescription(), group.getTitle());
    }

}
