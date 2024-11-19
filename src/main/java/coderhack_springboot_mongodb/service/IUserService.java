package coderhack_springboot_mongodb.service;

import java.util.List;

import coderhack_springboot_mongodb.dto.UserDto;
import coderhack_springboot_mongodb.entity.User;

public interface IUserService {

    List<User> findAll();

    User findById(Long userId);

    User save(UserDto userDto);

    User updateScore(Long userId, Integer score);

    User updateBadge(User user);
    
    void delete(Long userId);

}
