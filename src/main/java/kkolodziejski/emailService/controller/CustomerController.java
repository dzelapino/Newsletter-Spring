package kkolodziejski.emailService.controller;

import kkolodziejski.emailService.service.CustomerService;
import kkolodziejski.emailService.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<Customer>> findAll() {
        return  ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/findById/{id}")
    ResponseEntity<Customer> findById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findById(id).orElse(null));
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> updateCustomer(@PathVariable Long id, @RequestBody Customer updateData) {
        customerService.updateCustomer(id, updateData.getEmail());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

}
