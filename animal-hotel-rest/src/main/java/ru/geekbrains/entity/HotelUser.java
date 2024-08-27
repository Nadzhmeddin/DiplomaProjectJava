package ru.geekbrains.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class HotelUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "hotel_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<HotelUserRole> hotelUserRole;

    public List<HotelUserRole> getHotelUserRoleList() {
        return hotelUserRole;
    }

    public void setUserRoleList(List<HotelUserRole> hotelUserRole) {
        this.hotelUserRole = hotelUserRole;
    }

}
