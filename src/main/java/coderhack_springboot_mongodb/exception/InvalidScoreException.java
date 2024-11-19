package coderhack_springboot_mongodb.exception;

public class InvalidScoreException extends RuntimeException{

    public InvalidScoreException() {
    }
    
    public InvalidScoreException(String message) {
        super(message);
    }
}
