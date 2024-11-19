package coderhack_springboot_mongodb.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coderhack_springboot_mongodb.dto.UserDto;
import coderhack_springboot_mongodb.entity.User;
import coderhack_springboot_mongodb.exception.InvalidScoreException;
import coderhack_springboot_mongodb.exception.UserNotFoundException;
import coderhack_springboot_mongodb.repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService{
    
    @Autowired
    private IUserRepository userRepository;

    class ScoreComparator implements Comparator<User>{
        @Override
        public int compare(User u1, User u2){
            return u1.getScore().compareTo(u2.getScore());
        }
    }

    @Override
    public List<User> findAll() {

      List<User> userList = userRepository.findAll();
      Collections.sort(userList, Collections.reverseOrder(new ScoreComparator()));
      return userList;
    }

    @Override
    public User findById(Long userId) {
       return userRepository.findById(userId)
                            .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    @Override
    public User save(UserDto userDto) {
      User user = new User();
      user.setUserId(userDto.getUserId());
      user.setUsername(userDto.getUsername());
      return userRepository.save(user);
    }

    @Override
    public User updateScore(Long userId, Integer score) {

      //User user = findById(userId);

      User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

      if(score < 0 && score > 100){
        throw new InvalidScoreException("Score should be between 1 to 100");
      } 
      
      user.setScore(score);
      User updatedUser = updateBadge(user);
      return userRepository.save(updatedUser);
       
    }

    
    @Override
    public User updateBadge(User user) {
     
      Integer score = user.getScore();

      if(score >= 1 && score < 30){
        user.getBadges().add("Code Ninja");
      }else if(score >= 30 && score < 60){
        user.getBadges().add("Code Champ");
      }else if(score >= 60 && score <= 100){
        user.getBadges().add("Code Master");
      }
      return user;
    }

    @Override
    public void delete(Long userId) {

      userRepository.deleteById(userId);
      
      // userRepository.findById(userId).ifPresent(user -> userRepository.deleteById(userId));
    }

}