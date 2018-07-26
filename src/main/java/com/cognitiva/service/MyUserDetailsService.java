/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.service;

import com.cognitiva.models.login.Usuario;
import com.cognitiva.repositories.CrudRespository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyUserDetailsService implements UserDetailsService {

    private static List<UserObject> users = new ArrayList();

    public MyUserDetailsService() {
        //in a real application, instead of using local data,
        // we will find user details by some other means e.g. from an external system
        //users.add(new UserObject("erin", "123", "ADMIN"));
        //users.add(new UserObject("mike", "234", "ADMIN"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CrudRespository cr = new CrudRespository();

        Usuario usuario = cr.findByName(username);

        users.add(new UserObject(usuario.getName(), usuario.getPassword(), usuario.getRole()));

        System.out.println(usuario.toString());

        Optional<UserObject> user = users.stream()
                .filter(u -> u.name.equals(username))
                .findAny();
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found by name: " + username);
        }
        return toUserDetails(user.get());
    }

    private UserDetails toUserDetails(UserObject userObject) {

        return User.withUsername(userObject.name)
                .password(userObject.password)
                .roles(userObject.role).build();
    }

    private static class UserObject {

        private String name;
        private String password;
        private String role;

        public UserObject(String name, String password, String role) {
            this.name = name;
            this.password = password;
            this.role = role;
        }
    }
}
