package electron.com.lighting.repo;

import electron.com.lighting.dto.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
