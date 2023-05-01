//package model;
//
//import com.example.pas22.model.User;
//import com.example.pas22.model.UserType;
//import com.example.pas22.model.Product;
//import com.example.pas22.model.Rent;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//public class RentTest {
//    User c1, c2;
//    Product p1, p2;
//
//    @BeforeEach
//    public void beforeAll() {
//        c1 = new User("l1", "p1", UserType.CUSTOMER);
//        c2 = new User("l2", "p2", UserType.MANAGER);
//        p1 = new Product("p1", 10, "");
//        p2 = new Product("p2", 20, "");
//    }
//
//    @Test
//    public void ConstructorTest() {
//        Rent r1 = new Rent(c1, p1);
//        Rent r2 = new Rent(c2, p2);
//
//        Assertions.assertNotEquals(r1.getId(), r2.getId());
//    }
//}
