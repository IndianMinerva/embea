package com.factory.embea.repository;

import com.factory.embea.entity.Policy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends MongoRepository<Policy, String> {
}
