package com.ride.userservice.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ride.userservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDetailsImpl implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L; // Serializable version identifier

    // Return user ID
    @Getter
    private String id; // Unique identifier for the user
    private String username; // Username of the user
    // Return email
    @Getter
    private String email; // Email address of the user

    @JsonIgnore // Prevent serialization of the password field
    private String password; // Password of the user

    private Collection<? extends GrantedAuthority> authorities; // Collection of user's authorities (roles)

    public static UserDetailsImpl build(User user) {
        // Map the roles of the user to GrantedAuthority
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getUserRole().name())) // Convert each role to SimpleGrantedAuthority
                .collect(Collectors.toList()); // Collect into a list

        // Return a new UserDetailsImpl object
        return new UserDetailsImpl(
                user.getId(), // User ID
                user.getUsername(), // Username
                user.getEmail(), // Email
                user.getPassword(), // Password
                authorities); // User authorities
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) // Check if the same object
            return true;
        if (o == null || getClass() != o.getClass()) // Check if the object is null or not of the same class
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o; // Cast to UserDetailsImpl
        return Objects.equals(id, user.id); // Check if IDs are equal
    }
}
