//package repositories;
//
//import com.example.pas22.model.Product;
//import com.example.pas22.repositories.ProductRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//public class RepositoryTest {
//    ProductRepository pr;
//    Product p1;
//
//    @BeforeEach
//    public void beforeEach() {
//        pr = new ProductRepository();
//        p1 = new Product("p1", 10, "");
//    }
//
//    @Test
//    public void addTest() {
//        Product p2 = new Product("p", 10, "");
//
//        Assertions.assertEquals(0, pr.size());
//        pr.add(p1);
//        Assertions.assertEquals(1, pr.size());
//        pr.add(p1);
//        Assertions.assertEquals(1, pr.size());
//        pr.add(p2);
//        Assertions.assertEquals(2, pr.size());
//    }
//
//    @Test
//    public void getTest() {
//        pr.add(p1);
//        Product p2 = pr.get(p1.getId());
//
//        Assertions.assertEquals(p2.getId(), p1.getId());
//    }
//
//    @Test
//    public void deleteTest() {
//        pr.add(p1);
//        Assertions.assertEquals(1, pr.size());
//        pr.delete(p1.getId());
//        Assertions.assertEquals(0, pr.size());
//    }
//}
