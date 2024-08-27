package ru.geekbrains.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.geekbrains.entity.HotelUser;
import ru.geekbrains.repository.HotelUserRepository;
import ru.geekbrains.repository.HotelUserRoleRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MyCustomUserDetailsService implements UserDetailsService{

    private final HotelUserRepository hotelUserRepository;
    private final HotelUserRoleRepository hotelUserRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        HotelUser hotelUser = hotelUserRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с такой ролью не найден: " + username));

        List<SimpleGrantedAuthority> usersRole = hotelUserRoleRepository.findHotelUserRoleById(hotelUser.getId()).stream()
                .map(it -> new SimpleGrantedAuthority(it.getName()))
                .toList();

        return new User(
                hotelUser.getLogin(),
                hotelUser.getPassword(),
                usersRole
        );
    }
}
