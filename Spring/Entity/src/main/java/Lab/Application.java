package Lab;

import Lab.Model.Product;
import Lab.Service.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class);
        ProductService productService = applicationContext.getBean(ProductService.class);

        StringBuilder output = new StringBuilder("\nIf you're seeing this message, the database & entities are set up correctly.\n");

        // Insert sample products
        Product p1 = productService.insertProduct(new Product(1, "Headphones", "Nice headphones"));
        Product p2 = productService.insertProduct(new Product(2, "Phone", "A smartphone"));
        Product p3 = productService.insertProduct(new Product(3, "Swag", "Some other swag"));

        output.append("\nCurrent Product Table:\n").append(productService.getAllProducts());

        // Delete a product and show updated table
        productService.deleteProduct(p2);
        output.append("\nProduct Table After Deletion:\n").append(productService.getAllProducts());

        System.out.println(output);
    }
}
