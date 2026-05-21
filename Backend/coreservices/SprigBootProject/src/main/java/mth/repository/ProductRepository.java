package mth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mth.model.Product;

@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
