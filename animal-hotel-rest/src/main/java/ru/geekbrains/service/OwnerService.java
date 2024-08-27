package ru.geekbrains.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.entity.Owner;
import ru.geekbrains.exception.EmailException;
import ru.geekbrains.exception.PhoneException;
import ru.geekbrains.repository.OwnerRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }

    public Optional<Owner> findById(Long id) {
        return ownerRepository.findById(id);
    }

    public Owner save(Owner owner) throws PhoneException, EmailException {
        String phone  = owner.getPhone();
        String email = owner.getEmail();

        if(phone.isEmpty() || phone.length() < 11) {
            throw new PhoneException("Ошибка ввода номера телефона. Размер %s не соответствует 11!\n", phone);
        }
        if(!email.contains("@")) {
            throw new EmailException("Email некорректен: %s! Введите Email с '@'\n",email);
        }
        return ownerRepository.save(owner);
    }

    public void deleteById(Long id) {
        ownerRepository.deleteById(id);
    }

}
