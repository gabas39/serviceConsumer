package com.infofall.serviceconsumer.repositories;

import com.infofall.serviceconsumer.models.Address;
import com.infofall.serviceconsumer.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Address, String> {

	Address findByStreetNameAndStreetNumberAndCity(String streetName, String streetNumber, String city);
}