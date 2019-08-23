package ru.itis.springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.springboot.dto.UserDto;
import ru.itis.springboot.forms.SignInForm;
import ru.itis.springboot.forms.SignUpForm;
import ru.itis.springboot.models.CookieValue;
import ru.itis.springboot.models.User;
import ru.itis.springboot.models.UserRole;
import ru.itis.springboot.repositories.CookieValuesRepository;
import ru.itis.springboot.repositories.UsersRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.itis.springboot.dto.UserDto.from;

@Component
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CookieValuesRepository cookieValuesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void signUp(SignUpForm form) {
        User user = User.builder()
                .password(passwordEncoder.encode(form.getPassword()))
                .login(form.getLogin())
                .role(UserRole.USER)
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .build();
        usersRepository.save(user);
    }

    @Transactional
    @Override
    public Optional<String> signIn(SignInForm form) {
        Optional<User> userCandidate = usersRepository.findByLogin(form.getLogin());

        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            if (passwordEncoder.matches(form.getPassword(), user.getPassword())) {
                CookieValue cookieValue = CookieValue.builder()
                        .value(UUID.randomUUID().toString())
                        .user(user)
                        .build();
                cookieValuesRepository.save(cookieValue);
                return Optional.of(cookieValue.getValue());
            }
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<UserDto> getUserByCookie(String cookie) {
        Optional<CookieValue> cookieValueCandidate = cookieValuesRepository.findByValue(cookie);
        if (cookieValueCandidate.isPresent()) {
            CookieValue cookieValue = cookieValueCandidate.get();
            User user = cookieValue.getUser();
            return Optional.of(from(user));
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = usersRepository.findAll();
        return from(users);
    }
}
