package com.wiredbrain.friends.controller;

import com.wiredbrain.friends.model.Friend;
import com.wiredbrain.friends.service.FriendService;
import com.wiredbrain.friends.util.ErrorMessages;
import com.wiredbrain.friends.util.FieldErrorMessageClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService){
        this.friendService = friendService;
    }

//    //    Without using the validation class
//    @PostMapping("/friend")
//    Friend create(@RequestBody Friend friend) throws ValidationException {
//        if (friend.getId() == 0 && friend.getFirstName() != null && friend.getLastName() != null)
//            return friendService.save(friend);
//        else throw new ValidationException("Friends cannot be created");
////
//    }
    @PostMapping("/friend")
    Friend create(@Valid @RequestBody Friend friend) {
        return friendService.save(friend);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    List<FieldErrorMessageClass> exceptionHandler(MethodArgumentNotValidException e){
        List<FieldError> fieldErrorList = e.getBindingResult().getFieldErrors();
        List<FieldErrorMessageClass> fieldErrorMessages = fieldErrorList.stream().map(
                fieldError -> new FieldErrorMessageClass(
                        fieldError.getField(),
                        fieldError.getDefaultMessage())).collect(Collectors.toList());

        return fieldErrorMessages;
    }

//    @ExceptionHandler(ValidationException.class)
//    ResponseEntity<String> exceptionHandler(ValidationException e) {
//        return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }

    /**
     * This method is to be shifted to a class(ControllerExceptionHandler) for reusability.
     **/
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
