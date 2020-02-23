package com.djamware.springangularauth.repositories;

import com.infofall.serviceconsumer.ServiceConsumer;
import com.infofall.serviceconsumer.models.Products;
import com.infofall.serviceconsumer.repositories.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceConsumer.class)
//@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void testCreateProduct(){
        Products product = new Products();
        product.setProdDesc("testProduct");
        product.setProdName("testProductName");
        product.setProdPrice(5.5);
        productRepository.save(product);
    }


}