 package com.proj.security;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.proj.model.User;
import com.proj.repository.Userrepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

@Autowired
private Userrepository repo;

@Override
public UserDetails loadUserByUsername(String emailid)
throws UsernameNotFoundException {

User user = repo.findByEmailid(emailid);

if(user==null)
throw new UsernameNotFoundException("User not found");

return new CustomUserDetails(user);
}
}