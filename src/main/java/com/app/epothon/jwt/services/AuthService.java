package com.app.epothon.jwt.services;

import com.app.epothon.jwt.model.Role;
import com.app.epothon.jwt.model.RoleName;
import com.app.epothon.jwt.model.User;
import com.app.epothon.jwt.repository.RoleRepository;
import com.app.epothon.jwt.repository.UserRepository;
import com.app.epothon.jwt.security.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    JwtProvider jwtProvider;

    public boolean isThisUser(String roleName,String token){
        String  username = jwtProvider.getUserNameFromJwt(token);
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            if (getRolesStringFromRole(user.getRoles()).contains(roleName)){
                return true;
            }
            else return false;
        }
        else return false;
    }

    public Set<Role> getRolesFromStringToRole(Set<String> roles2) {
        Set<Role> roles = new HashSet<>();
        for (String role : roles2) {
            System.out.println(role);
            Optional<Role> roleOptional = roleRepository.findByName(RoleName.valueOf(role));
//            System.out.println(roleOptional.get());

            if (!roleOptional.isPresent()) {
                throw new ValidationException("Role '" + role + "' does not exist.");
            }
            roles.add(roleOptional.get());
        }
        return roles;
    }

    public Set<String> getRolesStringFromRole(Set<Role> roles2) {
        Set<String> roles = new HashSet<>();
        for (Role role : roles2) {

            roles.add(role.getName().toString());
        }
        return roles;
    }
}
