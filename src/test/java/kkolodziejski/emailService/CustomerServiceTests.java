package kkolodziejski.emailService;

import kkolodziejski.emailService.model.Customer;
import kkolodziejski.emailService.service.CustomerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class CustomerServiceTests {

    @SpyBean
    private CustomerService customerService;

    @Test
    public void createCustomerTest() {

        Customer customer = Customer.builder()
                .email("TestEmail@gmail.com").build();

        customerService.createCustomer(customer);

        Assertions.assertThat(customer.getId()).isGreaterThan(0);

    }

    @Test
    public void findCustomerByIdTest() {

        Customer customer = Customer.builder()
                .email("TestEmailFindById@gmail.com").build();

        customerService.createCustomer(customer);
        Optional<Customer> existingCustomer = customerService.findById(customer.getId());

        assert existingCustomer.orElse(null) != null;
        Assertions.assertThat(existingCustomer.orElse(null).getEmail()).isEqualTo("TestEmailFindById@gmail.com");

    }

    @Test
    public void findAllCustomers() {

        Customer customer = Customer.builder()
                .email("TestEmailFindAll1@gmail.com").build();
        Customer customer2 = Customer.builder()
                .email("TestEmailFindAll2@gmail.com").build();

        customerService.createCustomer(customer);
        customerService.createCustomer(customer2);
        List<Customer> customers = customerService.findAll();

        Assertions.assertThat(customers.size()).isEqualTo(2);

    }

    @Test
    public void editCustomerTest() {

        Customer customer = Customer.builder()
                .email("EmailToUpdate@gmail.com").build();

        customerService.createCustomer(customer);
        Optional<Customer> existingCustomer = customerService.findById(customer.getId());

        assert existingCustomer.orElse(null) != null;
        customerService.updateCustomer(existingCustomer.orElse(null).getId(), "UpdatedEmail@gmail.com");

        Assertions.assertThat(existingCustomer.orElse(null).getEmail()).isEqualTo("UpdatedEmail@gmail.com");

    }

    @Test
    public void deleteCustomerTest() {

        Customer customer = Customer.builder()
                .email("EmailToDelete@gmail.com").build();
        Customer customer2 = Customer.builder()
                .email("EmailToPercieve@gmail.com").build();

        customerService.createCustomer(customer);
        customerService.createCustomer(customer2);
        Optional<Customer> existingCustomer = customerService.findById(customer.getId());

        assert existingCustomer.orElse(null) != null;
        customerService.deleteCustomer(existingCustomer.orElse(null).getId());

        Assertions.assertThat(customerService.findAll().get(0).getEmail()).isEqualTo("EmailToPercieve@gmail.com");
        Assertions.assertThat(customerService.findAll().size()).isEqualTo(1);

    }

}
