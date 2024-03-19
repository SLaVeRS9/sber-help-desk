package ru.sberbank.edu.ticketservice.ticket;

import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import ru.sberbank.edu.common.error.ServiceError;
import ru.sberbank.edu.common.error.handler.RestErrorHandler;
import ru.sberbank.edu.common.internal.config.SpringSecurityConfig;
import ru.sberbank.edu.ticketservice.profile.entity.User;
import ru.sberbank.edu.ticketservice.profile.enums.UserRole;
import ru.sberbank.edu.ticketservice.profile.mapper.UserProfileMapper;
import ru.sberbank.edu.ticketservice.profile.service.UserService;
import ru.sberbank.edu.ticketservice.ticket.controller.TicketUIController;
import ru.sberbank.edu.ticketservice.ticket.entity.Ticket;
import ru.sberbank.edu.ticketservice.ticket.enums.TicketStatus;
import ru.sberbank.edu.ticketservice.ticket.mapper.CreateTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.mapper.EditTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.mapper.FullViewTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.service.TicketService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(value = {TicketUIController.class})
@AutoConfigureMockMvc
/*@SpringBootTest
@ActiveProfiles("test")*/
public class TicketUIControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    /*@Autowired
    private WebApplicationContext context;*/

    @MockBean
    private RestErrorHandler errorHandler;

    @MockBean
    private ServiceError serviceError;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserProfileMapper userProfileMapper;

    @MockBean
    private FullViewTicketMapper fullViewTicketMapper;

    @MockBean
    private CreateTicketMapper createTicketMapper;

    @MockBean
    private EditTicketMapper editTicketMapper;

    private static User requester1;
    private static Ticket ticket1;

    @BeforeClass
    public static void prepareTestData() {
        requester1 = new User();
        requester1.setId("User1");

        ticket1 = new Ticket();
        ticket1.setId(1L);
        ticket1.setTitle("Title 1");
        ticket1.setRequester(requester1);
        ticket1.setStatus(TicketStatus.IN_PROGRESS);
    }

    @BeforeEach
    void setUp() {
        /*mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();*/
    }

    @AfterEach
    void tearDown() {}

    @Test
    @DisplayName("GET /{id} success")
    //@WithUserDetails(value = "User1")
    public void showFullTicketInfoTest_Success() throws Exception {
        Long ticketId = 1L;
        
        when(ticketService.getTicketInfo(ticketId)).thenReturn(ticket1);


        mockMvc.perform(get("/tickets/{id}", ticketId).with(user("User1").roles("ADMIN")))
                .andExpect(content().contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.LOCATION,"/tickets/1"))
                .andExpect(view().name("ticket-info"))
                .andExpect(jsonPath("$").exists());
    }


}
