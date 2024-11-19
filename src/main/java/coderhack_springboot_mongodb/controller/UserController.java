package coderhack_springboot_mongodb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import coderhack_springboot_mongodb.dto.UserDto;
import coderhack_springboot_mongodb.entity.User;
import coderhack_springboot_mongodb.service.IUserService;

@RestController
@RequestMapping(UserController.USER_API_ENDPOINT)
public class UserController {

    @Autowired
    private IUserService userService;

    public static final String USER_API_ENDPOINT = "/users";
    public static final String USER_API = "/{userId}";


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok().body(userService.findAll());
    }


    @PostMapping
    public ResponseEntity<User> save(@RequestBody UserDto userDto){
        User newUser = userService.save(userDto);
        return ResponseEntity.ok().body(newUser);
    }

    @GetMapping(USER_API)
    public ResponseEntity<User> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok().body(userService.findById(userId));
    }

    @PutMapping(USER_API)
    public ResponseEntity<User> updateScore(@PathVariable Long id, @RequestParam Integer score){
        User user = userService.updateScore(id, score);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping(USER_API)
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        userService.delete(userId);
        return ResponseEntity.ok().body("Deleted Successfully!");
    }
}