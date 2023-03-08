package kkolodziejski.emailService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import kkolodziejski.emailService.controller.CustomerController;
import kkolodziejski.emailService.model.Customer;
import kkolodziejski.emailService.service.CustomerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.when;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CustomerService customerService;

    Customer customer1 = Customer.builder().id(1L)
        .email("TestCustomer1@gmail.com").build();

    Customer customer2 = Customer.builder().id(2L)
            .email("TestCustomer2@gmail.com").build();

    Customer customer3 = Customer.builder().id(3L)
            .email("TestCustomer3@gmail.com").build();

    @Test
    public void createdMockMvcTest() {
        assertNotNull(mockMvc);
    }

    @Test
    public void createCustomerTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(customer1)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void findAllTest() throws Exception {

        when(customerService.findAll()).thenReturn(List.of(customer1, customer2, customer3));

        mockMvc.perform(MockMvcRequestBuilders.get("/customers"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)));

    }

    @Test
    public void findByIdTest() throws Exception {

        when(customerService.findById(1L)).thenReturn(Optional.ofNullable(customer1));

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/findById/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("TestCustomer1@gmail.com")));

    }

    @Test
    public void updateCustomerTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(customer2)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void deleteCustomerTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    private String convertObjectToJsonString(Customer customer) throws JsonProcessingException {
        Gson gson = new Gson();
        return gson.toJson(customer);
    }

}
