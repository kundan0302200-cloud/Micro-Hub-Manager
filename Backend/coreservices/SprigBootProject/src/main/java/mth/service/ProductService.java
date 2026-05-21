package mth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mth.model.Product;
import mth.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepo;
	public Product register(Product product) {
		return productRepo.save(product);
	}
}
