import Lab.Application;
import Lab.Model.Product;
import Lab.Service.ProductService;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ProductEntityTest {
    @Autowired
    private ProductService productService;

    @Before
    public void setUp() {
        productService.deleteAll();
    }

    @Test
    public void productEntityTest1() {
        Product p1 = productService.insertProduct(new Product(1, "Headphones", "Nice headphones"));
        assertTrue(productService.getAllProducts().contains(p1));
    }
}
