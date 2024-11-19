package coderhack_springboot_mongodb.exception;

public class UserNotFoundException extends RuntimeException{
  
    public UserNotFoundException() {}

    public UserNotFoundException(String message) {
        super(message);
    }
}
