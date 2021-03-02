package be.relive.Global.group.controller;

import be.relive.Global.dto.GroupDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping(path = "/group", produces = "application/json")
public interface GroupApi {


    @PostMapping(path = "/")
    String save(@RequestBody GroupDto group);

    @GetMapping(path = "/byKey")
    List<GroupDto> findByKey(@RequestParam("key") List<String> keys);

}
