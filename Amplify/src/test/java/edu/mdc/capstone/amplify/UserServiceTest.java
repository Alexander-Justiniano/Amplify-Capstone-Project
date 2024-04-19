package edu.mdc.capstone.amplify;

import edu.mdc.capstone.amplify.models.LoginUser;
import edu.mdc.capstone.amplify.models.User;
import edu.mdc.capstone.amplify.repositories.UserRepository;
import edu.mdc.capstone.amplify.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private LoginUser loginUser;
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setConfirm("password");

        loginUser = new LoginUser();
        loginUser.setEmail("test@example.com");
        loginUser.setPassword("password");

        bindingResult = new BeanPropertyBindingResult(user, "user");
    }

    @Test
    void testRegister_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.register(user, bindingResult);

        assertNotNull(registeredUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegister_Fail_PasswordMismatch() {
        user.setConfirm("wrongpassword");
        userService.register(user, bindingResult);

        assertTrue(bindingResult.hasErrors());
        assertNotNull(bindingResult.getFieldError("confirm"));
    }

    @Test
    void testRegister_Fail_EmailExists() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        userService.register(user, bindingResult);

        assertTrue(bindingResult.hasErrors());
        assertNotNull(bindingResult.getFieldError("email"));
    }

    @Test
    void testLogin_Success() {
        user.setPassword("$2a$10$...hashed.password...");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        User loggedInUser = userService.login(loginUser, bindingResult);

        assertNotNull(loggedInUser);
        assertEquals(user.getEmail(), loggedInUser.getEmail());
    }

    @Test
    void testLogin_Fail_WrongPassword() {
        user.setPassword("$2a$10$...hashed.password...");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        loginUser.setPassword("wrongpassword");

        User loggedInUser = userService.login(loginUser, bindingResult);

        assertNull(loggedInUser);
        assertTrue(bindingResult.hasErrors());
        assertNotNull(bindingResult.getFieldError("password"));
    }

    @Test
    void testLogin_Fail_EmailNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        User loggedInUser = userService.login(loginUser, bindingResult);

        assertNull(loggedInUser);
        assertTrue(bindingResult.hasErrors());
        assertNotNull(bindingResult.getFieldError("email"));
    }

    @Test
    void testFindUser_Found() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        User foundUser = userService.findUser(1L);

        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    void testFindUser_NotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        User foundUser = userService.findUser(1L);

        assertNull(foundUser);
    }
}
