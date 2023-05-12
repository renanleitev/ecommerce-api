package com.amenity_reservation_system.repos;

import com.amenity_reservation_system.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product, Long> {
}

