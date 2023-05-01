//package repositories;
//
//import com.example.pas22.model.User;
//import com.example.pas22.model.UserType;
//import com.example.pas22.model.Product;
//import com.example.pas22.model.Rent;
//import com.example.pas22.repositories.RentRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//public class RentRepositoryTest {
//    RentRepository rr;
//    User c1, c2, c3;
//    Product p1, p2, p3;
//    Rent r1;
//    @BeforeEach
//    public void beforeEach() {
//        rr = new RentRepository();
//
//        p1 = new Product("Pryncypałki", 8, "");
//        p2 = new Product("Książka", 18, "");
//        p3 = new Product("Kalosze", 24, "");
//
//        c1 = new User("Jan", "Qwertyuiop1!", UserType.CUSTOMER);
//        c2 = new User("Adam", "Qwertyuiop1!", UserType.CUSTOMER);
//        c3 = new User("Maciek", "Qwertyuiop1!", UserType.CUSTOMER);
//    }
//
//    @Test
//    public void addTest() {
//        Rent r1 = new Rent(c1, p1);
//        Rent r2 = new Rent(c2, p2);
//        Rent r3 = new Rent(c3, p3);
//
//        rr.add(r1);
//        Assertions.assertEquals(1, rr.size());
//        rr.add(r2);
//        Assertions.assertEquals(2, rr.size());
//        rr.add(r3);
//        Assertions.assertEquals(3, rr.size());
//    }
//
//}
