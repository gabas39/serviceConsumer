package com.infofall.serviceconsumer.models;

import java.util.Set;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
public class User {

	@Id
	private String id;
	@Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)

	private String email;
	private String password;
	private String fullName;

	private String systemUserId;
	private boolean enabled;

	@DBRef
	private Address address;
	@DBRef
    private Set<Role> roles;
    

	
}