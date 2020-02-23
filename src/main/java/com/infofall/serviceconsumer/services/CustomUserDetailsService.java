package com.infofall.serviceconsumer.services;

import com.infofall.serviceconsumer.models.Address;
import com.infofall.serviceconsumer.models.Role;
import com.infofall.serviceconsumer.models.RoleType;
import com.infofall.serviceconsumer.models.User;
import com.infofall.serviceconsumer.repositories.AddressRepository;
import com.infofall.serviceconsumer.repositories.RoleRepository;
import com.infofall.serviceconsumer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public User registerUser(User user) {
        String systemUserId = registerUserInOtherSystem(user);
        user.setSystemUserId(systemUserId);
        return saveUser(user);
    }

    private String registerUserInOtherSystem(User user) {
        //tmp registry
        return UUID.randomUUID().toString();
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        Role userRole = roleRepository.findByRole(RoleType.CONSUMER.name());
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));

        Address address = addressRepository.findByStreetNameAndStreetNumberAndCity(user.getAddress().getStreetName(), user.getAddress().getStreetNumber(), user.getAddress().getCity());
        if (address == null) {
            address = addressRepository.save(user.getAddress());
        }

        user.setAddress(address);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        });

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}