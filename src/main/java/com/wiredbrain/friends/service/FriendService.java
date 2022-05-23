package com.wiredbrain.friends.service;

import com.wiredbrain.friends.model.Friend;
import org.springframework.data.repository.CrudRepository;

public interface FriendService extends CrudRepository<Friend, Integer> {

//    Spring Data will help in creating the sql query needed to fulfill this function
    Iterable<Friend> findByFirstNameAndAndLastName(String firstName, String lastName);

    Iterable<Friend> findByFirstName(String firstName);

    Iterable<Friend> findByLastName(String lastName);
}
