package edu.mdc.capstone.amplify.models;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class UserModelTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void properUser_ValidationSucceeds() {
        User user = new User();
        user.setUserName("Apple"); 
        user.setEmail("gmail@yahoo.com");
        user.setPassword("perfectpassword123");
        user.setConfirm("perfectpassword123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).isEmpty();
    }

    @Test
    void blankUserName_ValidationFails() {
        User user = new User();
        user.setUserName("");  // Blank username - Invalid
        user.setEmail("gmail@yahoo.com");
        user.setPassword("perfectpassword123");
        user.setConfirm("perfectpassword123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("Username is required!"));
    }
    
    @Test
    void userNameTooLong_ValidationFails() {
        User user = new User();
        user.setUserName("invalidUserNameNameTooLooooooooooooooooooooooooooong");  // Invalid 
        user.setEmail("gmail@yahoo.com");
        user.setPassword("perfectpassword123");
        user.setConfirm("perfectpassword123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("Username must be between 3 and 30 characters"));
    }
    
    @Test
    void shortUserName_ValidationFails() {
        User user = new User();
        user.setUserName("kg");   
        user.setEmail("gmail@yahoo.com");
        user.setPassword("perfectpassword123");
        user.setConfirm("perfectpassword123");

      	Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("Username must be between 3 and 30 characters"));
    }
    
    @Test
    void userNameContainsNumbers_ValidationFails() {
    	User user = new User();
        user.setUserName("N4meHasNumb3rs");  // Has numbers - Invalid
        user.setEmail("gmail@yahoo.com");
        user.setPassword("perfectpassword123");
        user.setConfirm("perfectpassword123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("No numbers, only letters are allowed"));
    }
    
    @Test
    void validEmail_ValidationSucceeds() {
        User user = new User();
        user.setUserName("Apple"); 
        user.setEmail("gmail@yahoo.com");  // Valid email
        user.setPassword("perfectpassword123");
        user.setConfirm("perfectpassword123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).isEmpty();
    }

    @Test
    void blankEmail_ValidationFails() {
        User user = new User();
        user.setUserName("Apple"); 
        user.setEmail("");  
        user.setPassword("perfectpassword123");
        user.setConfirm("perfectpassword123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("Email is required!"));
    }
    
    @Test
    void invalidEmail_ValidationFails() {
        User user = new User();
        user.setUserName("Apple"); 
        user.setEmail("Invalid_Email");  
        user.setPassword("perfectpassword123");
        user.setConfirm("perfectpassword123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("Please enter a valid email!"));
    }
    
    @Test
    void shortPassword_ValidationFails() {
        User user = new User();
        user.setUserName("Apple");
        user.setEmail("gmail@yahoo.com");
        user.setPassword("notLong");  
        user.setConfirm("notLong");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("Password must be between 8 and 128 characters"));
    }

    @Test
    void blankPassword_ValidationFails() {
        User user = new User();
        user.setUserName("Apple");
        user.setEmail("gmail@yahoo.com");
        user.setPassword("");  
        user.setConfirm("");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("Password is required!"));
    }

    @Test
    void longPassword_ValidationFails() {
        User user = new User();
        user.setUserName("Apple");
        user.setEmail("gmail@yahoo.com");
        user.setPassword("loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooongpassword");  
        user.setConfirm("loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooongpassword"); 

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("Password must be between 8 and 128 characters"));
    }

    @Test
    void matchingPasswords_ValidationSucceeds() {
        User user = new User();
        user.setUserName("Apple");
        user.setEmail("gmail@yahoo.com");
        user.setPassword("perfectpassword123");
        user.setConfirm("perfectpassword123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).isEmpty();
    }

    @Test
    void blankConfrimPassword_ValidationFails() {
        User user = new User();
        user.setUserName("Apple"); 
        user.setEmail("gmail@yahoo.com");
        user.setPassword("perfectpassword123");
        user.setConfirm("");  

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("Confirm Password is required!"));
    }
    
}
