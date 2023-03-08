package kkolodziejski.emailService.service;

import kkolodziejski.emailService.model.Customer;
import kkolodziejski.emailService.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CustomerService {

    @Autowired

    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void updateCustomer(Long id, String updateData) {
        customerRepository.findById(id).ifPresent(customer -> {
                    customer.setEmail(updateData);
                    customerRepository.save(customer);
        });
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
