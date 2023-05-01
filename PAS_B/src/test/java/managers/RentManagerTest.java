//package managers;
//
//import com.example.pas22.managers.RentManager;
//import com.example.pas22.model.Customer;
//import com.example.pas22.model.CustomerType;
//import com.example.pas22.model.Product;
//import com.example.pas22.model.Rent;
//import com.example.pas22.repositories.CustomerRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//public class RentManagerTest {
//    RentManager rentManager;
//    Customer c1, c2, c3;
//    Product p1, p2, p3;
//    public void RentManagerTest(){
//
//
//
//
//    }
//    @BeforeEach
//    public void beforeEach() {
//        rentManager = new RentManager();
//
//        p1 = new Product("Pryncypałki", 8);
//        p2 = new Product("Książka", 18);
//        p3 = new Product("Kalosze", 24);
//
//        c1 = new Customer("Jan", "Qwertyuiop1!", CustomerType.CUSTOMER);
//        c2 = new Customer("Adam", "Qwertyuiop1!", CustomerType.CUSTOMER);
//        c3 = new Customer("Maciek", "Qwertyuiop1!", CustomerType.CUSTOMER);
//
//        c1.setBlocked(false);
//        c2.setBlocked(false);
//        c3.setBlocked(false);
//    }
//
//    @Test
//    public void addTest() {
//        Assertions.assertEquals(rentManager.getAllRents().size(), 0);
//        rentManager.rentProduct(c1, p1);
//        Assertions.assertEquals(rentManager.getAllRents().size(), 1);
//        rentManager.rentProduct(c2, p2);
//        Assertions.assertEquals(rentManager.getAllRents().size(), 2);
//        rentManager.rentProduct(c3, p2);
//        Assertions.assertEquals(rentManager.getAllRents().size(), 2);
//        rentManager.rentProduct(c2, p3);
//        Assertions.assertEquals(rentManager.getAllRents().size(), 2);
//    }
//
//    @Test
//    public void customGettersTest() {
//        rentManager.rentProduct(c1, p1);
//        rentManager.rentProduct(c2, p2);
//        Assertions.assertEquals(rentManager.getRentByCustomer(c1.getID()).getProduct() , p1);
//        Assertions.assertEquals(rentManager.getRentByProduct(p1.getID()).getCustomer() , c1);
//    }
//    @Test
//    public void endRentTest() throws ParseException {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
//
//
//        Assertions.assertEquals(rentManager.getAllActive().size(), 0);
//        rentManager.rentProduct(c1, p1);
//        Assertions.assertEquals(rentManager.getAllActive().size(), 1);
//        rentManager.rentProduct(c2, p2);
//        Assertions.assertEquals(rentManager.getAllActive().size(), 2);
//        rentManager.endRent(rentManager.getRentByCustomer(c1.getID()).getID());
//        Assertions.assertEquals(rentManager.getAllActive().size(), 1);
//
//        Date dateFromPast = formatter.parse("10-Jun-2000");
//
//
//        rentManager.endRent(rentManager.getRentByCustomer(c2.getID()).getID(), dateFromPast);
//        Assertions.assertEquals(rentManager.getAllActive().size(), 1);
//        Date dateFromFuture = formatter.parse("10-Jun-2040");
//        rentManager.endRent(rentManager.getRentByCustomer(c2.getID()).getID(), dateFromFuture);
//        Assertions.assertEquals(rentManager.getAllActive().size(), 0);
//    }
//}
