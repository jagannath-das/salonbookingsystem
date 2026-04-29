package com.proj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.proj.model.User;
import com.proj.repository.Userrepository;


@Service
public class Adminservice 
{
@Autowired
private Userrepository repo;

public List<User> getallusers() {
    return repo.findAll();
           
}
public ResponseEntity<String> deletebyid(Long id) {

    if(!repo.existsById(id)) {
        return ResponseEntity.status(404)
                .body("User not found");
    }

    repo.deleteById(id);
    return ResponseEntity.ok("User deleted successfully");
}
}
