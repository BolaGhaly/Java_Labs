package Lab.Service;

import Lab.Model.Product;
import Lab.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductService {
    ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product insertProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public void deleteAll() {
        productRepository.deleteAll();
    }
}