package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.entity.Owner;
import ru.geekbrains.exception.EmailException;
import ru.geekbrains.exception.PhoneException;
import ru.geekbrains.repository.OwnerRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest
class OwnerServiceTest {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    OwnerService ownerService;

    @BeforeEach
    void clearData(){
        ownerRepository.deleteAll();
    }

    @Test
    @DisplayName("find all owners method test")
    void findAllOwnersTest() throws PhoneException, EmailException {
        Owner firstOwner = new Owner("Kate", "79202020676", "kate@mail.ru");
        Owner secondOwner = new Owner("Tony", "79040913580", "tony@mail.ru");
        ownerService.save(firstOwner);
        ownerService.save(secondOwner);

        List<Owner> ownerList = ownerService.findAll();

        assertThat(ownerList)
                .isNotNull()
                .hasSize(2)
                .contains(firstOwner, secondOwner);
    }

    @Test
    @DisplayName("find owner by id method test")
    void findOwnerByIdTest() throws PhoneException, EmailException {
        Owner owner = new Owner("Kate", "79202020676", "kate@mail.ru");
        owner = ownerService.save(owner);


        Owner foundOwner = ownerService.findById(owner.getId()).get();

        assertThat(foundOwner)
                .isEqualTo(owner)
                .isNotNull();
    }

    @Test
    @DisplayName("save owner method test")
    void saveOwnerTest() throws PhoneException, EmailException {
        Owner owner = new Owner("Kate", "79202020676", "kate@mail.ru");
        owner = ownerService.save(owner);

        Owner savedOwner = ownerService.findById(owner.getId()).get();

        assertThat(savedOwner)
                .isEqualTo(owner)
                .isNotNull();
    }

    @Test
    @DisplayName("delete owner by id method test")
    void deleteOwnerByIdTest() throws PhoneException, EmailException {
        Owner firstOwner = new Owner("Kate", "79202020676", "kate@mail.ru");
        Owner secondOwner = new Owner("Tony", "79040913580", "tony@mail.ru");
        ownerService.save(firstOwner);
        ownerService.save(secondOwner);

        ownerService.deleteById(secondOwner.getId());

        List<Owner> ownersList = ownerService.findAll();

        assertThat(ownersList)
                .hasSize(1)
                .contains(firstOwner)
                .isNotNull();

    }
}