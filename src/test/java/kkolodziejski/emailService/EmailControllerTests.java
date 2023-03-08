package kkolodziejski.emailService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import kkolodziejski.emailService.controller.EmailController;
import kkolodziejski.emailService.model.Customer;
import kkolodziejski.emailService.model.EmailMessage;
import kkolodziejski.emailService.service.CustomerService;
import kkolodziejski.emailService.service.EmailSenderService;
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

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.when;

@WebMvcTest(EmailController.class)
public class EmailControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private EmailSenderService emailSenderService;

    @MockBean
    private CustomerService customerService;

    Customer customer1 = Customer.builder().id(1L)
            .email("TestCustomer1@gmail.com").build();

    Customer customer2 = Customer.builder().id(2L)
            .email("TestCustomer2@gmail.com").build();

    EmailMessage emailMessage = new EmailMessage("EmailControllerTests", "Email Controller Test Run");

    @Test
    public void createdMockMvcTest() {
        assertNotNull(mockMvc);
    }

    @Test
    public void sendEmailTest() throws Exception {

        when(customerService.findAll()).thenReturn(List.of(customer1, customer2));

        mockMvc.perform(MockMvcRequestBuilders.post("/emails/toSubscribers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(emailMessage)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String convertObjectToJsonString(EmailMessage email) throws JsonProcessingException {
        Gson gson = new Gson();
        return gson.toJson(email);
    }

}
