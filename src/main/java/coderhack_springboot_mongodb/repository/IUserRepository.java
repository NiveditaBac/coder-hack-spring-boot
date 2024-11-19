package coderhack_springboot_mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import coderhack_springboot_mongodb.entity.User;

public interface IUserRepository extends MongoRepository<User, Long>{
    
}
