package com.factory.embea.repository;

import com.factory.embea.entity.Policy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PolicyRepository extends MongoRepository<Policy, String> {
    Optional<Policy> findByPolicyIdAndStartDate(String policyId, String startDate);

    Optional<Policy> findByPolicyId(String policyId);
}
