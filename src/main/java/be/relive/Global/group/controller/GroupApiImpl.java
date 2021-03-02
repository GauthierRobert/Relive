package be.relive.Global.group.controller;

import be.relive.Global.dto.GroupDto;
import be.relive.Global.group.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroupApiImpl implements GroupApi {

    private final GroupService groupService;

    @Autowired
    public GroupApiImpl(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public String save(GroupDto group) {
        return groupService.save(group).getKey();
    }

    @Override
    public List<GroupDto> findByKey(List<String> keys) {
        return groupService.findAllbyKeys(keys);
    }
}
