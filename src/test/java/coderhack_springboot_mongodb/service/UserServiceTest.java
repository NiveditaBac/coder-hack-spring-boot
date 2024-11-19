package coderhack_springboot_mongodb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import coderhack_springboot_mongodb.dto.UserDto;
import coderhack_springboot_mongodb.entity.User;
import coderhack_springboot_mongodb.repository.IUserRepository;

public class UserServiceTest {
    
    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private final User user = new User();

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        user.setUserId(1L);
        user.setUsername("Alice");
        user.setScore(0);
    }

    @Test
    @DisplayName("Get all users from repository")
    void getAllUserTest(){
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);
        List<User> actualOutput = userServiceImpl.findAll();
        Assertions.assertEquals(users, actualOutput);
        Assertions.assertEquals(1, actualOutput.size());
    }

    @Test
    @DisplayName("Get user by given ID")
    void getUserByIdTest(){
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userServiceImpl.findById(userId);
        Assertions.assertEquals(userId, result.getUserId());
        Assertions.assertEquals("Alice", result.getUsername());
    }

    @Test
    @DisplayName("Create a user and save in repository")
    void saveUserTest(){
        UserDto userDto = new UserDto((long) 1, "Alice");
        when(userRepository.save(any(User.class))).thenReturn(user);
        User result = userServiceImpl.save(userDto);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(user, result);
    }

    @Test
    @DisplayName("Delete user by given ID")
    void deleteUserByIdTest(){
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(true);
        doNothing().when(userRepository).deleteById(userId);
        userServiceImpl.delete(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }


    @Test
    @DisplayName("Update user score and badge")
    void updateScoreTest(){

        when(userRepository.findById((long) 1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        User result = userServiceImpl.updateScore(1L, 70);
        Assertions.assertEquals((Integer) 70, result.getScore());
        Assertions.assertTrue(result.getBadges().contains("Code Master"));
    }

}
