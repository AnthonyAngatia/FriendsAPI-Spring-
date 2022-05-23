package com.wiredbrain.friends.controller;

import com.wiredbrain.friends.model.Friend;
import com.wiredbrain.friends.service.FriendService;
import com.wiredbrain.friends.util.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.Optional;

@RestController
public class FriendController {

    //    Dependency Friend service is injected into this class through the autowired annotation
    @Autowired
    FriendService friendService;

    @PostMapping("/friend")
    Friend create(@RequestBody Friend friend) throws ValidationException {
        if (friend.getId() == 0 && friend.getFirstName() != null && friend.getLastName() != null)
            return friendService.save(friend);
         else throw new ValidationException("Friends cannot be created");
//
    }

//    @ExceptionHandler(ValidationException.class)
//    ResponseEntity<String> exceptionHandler(ValidationException e) {
//        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    ErrorMessages exceptionHandler(ValidationException e) {
        return new ErrorMessages("400", e.getMessage());
    }

    @GetMapping("/friend")
    Iterable<Friend> read() {
        return friendService.findAll();
    }

//    @PutMapping("/friend")
//    Friend update(@RequestBody Friend friend) {
//        return friendService.save(friend);
//
//    }

    @PutMapping("/friend")
    ResponseEntity<Friend> update(@RequestBody Friend friend) {
        if (friendService.findById(friend.getId()).isPresent()) {
            return new ResponseEntity<>(friendService.save(friend), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(friend, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/friend/{id}")
    void delete(@PathVariable Integer id) {
        friendService.deleteById(id);
    }

    @GetMapping("/friend/{id}")
    Optional<Friend> findById(@PathVariable Integer id) {
        return friendService.findById(id);
    }

    @GetMapping("/friend/search")
    Iterable<Friend> findByQuery(
            @RequestParam(value = "first", required = false) String firstName,
            @RequestParam(value = "last", required = false) String lastName
    ) {
        if (firstName != null && lastName != null) {
            return friendService.findByFirstNameAndAndLastName(firstName, lastName);
        } else if (firstName != null) {
            return friendService.findByFirstName(firstName);
        } else if (lastName != null) {
            return friendService.findByLastName(lastName);
        } else {
            return friendService.findAll();
        }
    }
}
