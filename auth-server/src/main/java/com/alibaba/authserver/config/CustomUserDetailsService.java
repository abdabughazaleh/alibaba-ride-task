package com.alibaba.authserver.config;

import com.alibaba.authserver.model.entity.User;
import com.alibaba.authserver.model.enums.Roles;
import com.alibaba.authserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final List<GrantedAuthority> grantedAuthorities;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByUsername(username);
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + Roles.ADMIN.name()));
                return grantedAuthorities;
            }

            @Override
            public String getPassword() {
                return user.get().getPassword();
            }

            @Override
            public String getUsername() {
                return user.get().getUsername();
            }

            @Override
            public boolean isEnabled() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }
        };
    }
}
