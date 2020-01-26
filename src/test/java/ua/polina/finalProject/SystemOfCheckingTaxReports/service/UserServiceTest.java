package ua.polina.finalProject.SystemOfCheckingTaxReports.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.User;
import ua.polina.finalProject.SystemOfCheckingTaxReports.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;
    private List<User> users;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        users =Arrays.asList(
                        User.builder()
                                .id(1L)
                                .email("user1@gmail.com")
                                .password("user1")
                                .build(),
                        User.builder()
                                .id(2L)
                                .email("user2@gmail.com")
                                .password("user2")
                                .build()
                );
    }

    @Test
    void getAllUsers() {
        int page = 1;
        int size = 10;
        String sortParameter = "reason";
        String sortDir = "asc";
        PageRequest pageReq = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sortParameter);
        when(userRepository.findAll(pageReq)).thenReturn(new PageImpl<>(users));

        List<User> actualUsers = userService.getAllUsers(page, size, sortParameter, sortDir);

        Assert.assertEquals(users, actualUsers);
    }

    @Test
    void getById() {
        User expectedUser = users.get(0);
        Long userID = 1L;
        Optional<User> expectedOptionalUser = Optional.of(expectedUser);
        when(userRepository.findById(userID)).thenReturn(expectedOptionalUser);

        Optional<User> actualUser = userService.getById(userID);

        Assert.assertEquals(expectedOptionalUser, actualUser);
    }

    @Test
    void getByEmail() {
        User expectedUser = users.get(0);
        String userEmail = "user1@gmail.com";
        Optional<User> expectedOptionalUser = Optional.of(expectedUser);
        when(userRepository.findByEmail(userEmail)).thenReturn(expectedOptionalUser);

        Optional<User> actualUser = userService.getByEmail(userEmail);

        Assert.assertEquals(expectedOptionalUser, actualUser);
    }
}