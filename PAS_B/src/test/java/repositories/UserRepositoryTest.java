//package repositories;
//
//import com.example.pas22.model.User;
//import com.example.pas22.model.UserType;
//import com.example.pas22.repositories.UserRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//public class UserRepositoryTest {
//    UserRepository cr;
//    User c1;
//
//    @BeforeEach
//    public void beforeEach() {
//        cr = new UserRepository();
//        c1 = new User("l1", "p1", UserType.CUSTOMER);
//    }
//
//    @Test
//    public void addTest() {
//        User c2 = new User(c1.getLogin(), "p2", UserType.CUSTOMER);
//        User c3 = new User("c3", "p3", UserType.CUSTOMER);
//
//        cr.add(c1);
//        Assertions.assertEquals(1, cr.size());
//        cr.add(c2);
//        Assertions.assertEquals(1, cr.size());
//        cr.add(c3);
//        Assertions.assertEquals(2, cr.size());
//    }
//}
