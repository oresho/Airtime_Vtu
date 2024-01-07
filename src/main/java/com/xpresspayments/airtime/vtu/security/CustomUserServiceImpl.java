package com.xpresspayments.airtime.vtu.security;

import com.xpresspayments.airtime.vtu.exceptionHandler.ApplicationException;
import com.xpresspayments.airtime.vtu.model.entity.AppUser;
import com.xpresspayments.airtime.vtu.model.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserServiceImpl implements UserDetailsService {
//    Dependency Injection of App User Repo Class
    private final AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(username)
                .orElseThrow(() -> new ApplicationException("This user does not exist"));
       return new User(appUser.getEmail(),appUser.getPasswordHash(), List.of(new SimpleGrantedAuthority("USER")));
    }
}
