package com.proj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proj.model.User;
@Repository
public interface  Userrepository extends JpaRepository<User,Long>
{
   User findByEmailid(String emailid);
}
