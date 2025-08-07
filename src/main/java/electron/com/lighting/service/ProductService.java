package electron.com.lighting.service;

import electron.com.lighting.dto.Product;
import electron.com.lighting.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // todo: error handling for all
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).isPresent()? productRepository.findById(id).get(): null;
    }
}
