package com.aryanbamba.vaultx.repository;

import com.aryanbamba.vaultx.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

}
