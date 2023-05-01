//package managers;
//
//import com.example.pas22.managers.CustomerManager;
//import com.example.pas22.model.Customer;
//import com.example.pas22.model.CustomerType;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//public class CustomerManagerTest {
//    CustomerManager cm;
//
//    @BeforeEach
//    public void beforeEach() {
//        cm = new CustomerManager();
//    }
//
//    @Test
//    public void findTest() {
//        String login = "login";
//        String pass = "pass";
//        cm.add(login, pass, CustomerType.CUSTOMER);
//        cm.add("login2", "pass2", CustomerType.CUSTOMER);
//        cm.add("qwe", "ewq", CustomerType.RENTER);
//        Customer duplicateLoginAdded = cm.add(login, "aa", CustomerType.ADMIN);
//
//        Assertions.assertNull(duplicateLoginAdded);
//
//        Customer c1 = cm.findByExactLogin(login);
//        Assertions.assertNotNull(c1);
//
//        List<Customer> customers = cm.findByLogin("l");
//        Assertions.assertEquals(2, customers.size());
//    }
//
//    @Test
//    public void crudTest() {
//        String login = "login";
//        String pass = "pass";
//        cm.add(login, pass, CustomerType.CUSTOMER);
//
//        Assertions.assertEquals(1, cm.getAll().size());
//
//        Customer c1 = cm.findByExactLogin(login);
//        Assertions.assertNotNull(c1);
//
//        cm.updatePassword(c1.getID(), "new pass");
//        Assertions.assertNotEquals(c1.getPassword(), pass);
//
//        cm.unblockCustomer(c1.getID());
//        Assertions.assertFalse(c1.isBlocked());
//    }
//}
