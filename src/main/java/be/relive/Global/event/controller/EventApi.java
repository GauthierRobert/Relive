package be.relive.Global.event.controller;

import be.relive.Global.event.domain.dto.EventDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@RequestMapping( produces = "application/json")
public interface EventApi {

    @PutMapping(path = "/event/{id}")
    ResponseEntity update(@PathVariable("id") UUID id, @RequestBody EventDto eventDto);

    @DeleteMapping(path = "/event/{id}")
    void delete(@PathVariable("id") UUID id);

    @PostMapping(path = "/event/")
    ResponseEntity save(@RequestBody EventDto eventDto);

    @GetMapping(path = "/group/{groupKey}/event")
    ResponseEntity findByKey(@PathVariable(name = "groupKey") String groupKey,
                             @RequestParam(required = false, name = "page") Integer page,
                             @RequestParam(required = false, name = "size") Integer size);

    @GetMapping(path = "/event/byUser/{attendantId}")
    ResponseEntity findByAttendantId(@PathVariable(name = "attendantId") UUID attendantId);

}
