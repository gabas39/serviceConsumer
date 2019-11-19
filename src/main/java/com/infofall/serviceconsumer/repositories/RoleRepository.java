package com.infofall.serviceconsumer.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.infofall.serviceconsumer.models.Role;

public interface RoleRepository extends MongoRepository<Role, String> {

	Role findByRole(String role);
}