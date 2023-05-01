//package managers;
//
//import com.example.pas22.managers.ProductManager;
//import com.example.pas22.model.Product;
//import com.example.pas22.repositories.ProductRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import javax.inject.Inject;
//import java.util.function.Predicate;
//
//public class ProductManagerTest {
//    @Inject
//    ProductManager pm;
//    String n1;
//    float p1;
//
//    @BeforeEach
//    public void beforeEach() {
//        n1 = "n1";
//        p1 = 999;
//
//    }
//
//    @Test
//    public void addProductTest() {
//        Assertions.assertEquals(pm.getAll().size(), 0);
//        pm.add(n1, p1);
//        Assertions.assertEquals(pm.getAll().size(), 1);
//    }
//
//    @Test
//    public void updateTest() {
//        String n2 = "n2";
//        float p2 = 888;
//        String n3 = "n3";
//        float p3 = 777;
//
//        pm.add(n1, p1);
//        Product testProduct = pm.getAll().get(0);
//
//        pm.updateName(testProduct.getID(),n2);
//        Assertions.assertEquals(testProduct.getName(), n2);
//
//        pm.updatePrice(testProduct.getID(),p2);
//        Assertions.assertEquals(testProduct.getPrice(), p2);
//
//        pm.update(testProduct.getID(),p3, n3);
//        Assertions.assertEquals(testProduct.getPrice(), p3);
//        Assertions.assertEquals(testProduct.getName(), n3);
//    }
//}
