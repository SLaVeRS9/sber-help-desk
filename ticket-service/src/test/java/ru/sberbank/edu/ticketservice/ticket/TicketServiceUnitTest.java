package ru.sberbank.edu.ticketservice.ticket;

import org.junit.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.sberbank.edu.common.AuthenticationFacade;
import ru.sberbank.edu.common.error.exception.ActionNotAllowException;
import ru.sberbank.edu.ticketservice.kafka.KafkaCreateTicketNoticeService;
import ru.sberbank.edu.ticketservice.profile.entity.User;
import ru.sberbank.edu.ticketservice.profile.service.UserService;
import ru.sberbank.edu.ticketservice.ticket.entity.Ticket;
import ru.sberbank.edu.ticketservice.ticket.enums.TicketStatus;
import ru.sberbank.edu.ticketservice.ticket.repository.TicketRepository;
import ru.sberbank.edu.ticketservice.ticket.service.TicketService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class TicketServiceUnitTest {

    private static User requester1;
    private static User requester2;
    private static Ticket ticket1;
    private static Ticket ticket2;
    private static Ticket ticket3;
    private static Ticket ticket4;
    private static List<Ticket> allTickets;

    @BeforeClass
    public static void prepareTestData() {
        requester1 = new User();
        requester1.setId("User1");

        requester2 = new User();
        requester2.setId("User2");

        ticket1 = new Ticket();
        ticket1.setId(1L);
        ticket1.setTitle("Title 1");
        ticket1.setRequester(requester1);
        ticket1.setStatus(TicketStatus.IN_PROGRESS);

        ticket2 = new Ticket();
        ticket2.setId(2L);
        ticket2.setTitle("Title 2");
        ticket2.setStatus(TicketStatus.CLOSED);
        ticket2.setRequester(requester2);

        ticket3 = new Ticket();
        ticket3.setId(3L);
        ticket3.setTitle("Title 3");
        ticket3.setStatus(TicketStatus.ARCHIVED);
        ticket3.setRequester(requester1);

        ticket4 = new Ticket();
        ticket4.setId(4L);
        ticket4.setTitle("Title 4");
        ticket4.setStatus(TicketStatus.NEW);
        ticket4.setRequester(requester2);

        allTickets = new ArrayList<>() {{
            add(ticket1);
            add(ticket2);
            add(ticket3);
            add(ticket4);
        }};
    }
     @Before
     public void init() {
    }

    @Mock
    TicketRepository ticketRepository;

    @Mock
    UserService userService;

    @Mock
    AuthenticationFacade authenticationFacade;

    @Mock
    Authentication authentication;

    @Mock
    KafkaCreateTicketNoticeService kafkaCreateTicketNoticeService;

    @InjectMocks
    TicketService ticketService;

    @Test
    @DisplayName("Test getting all tickets from db. Success case - all tickets got")
    public void getAllTicketsTest_Success() {

        when(ticketRepository.findAll()).thenReturn(allTickets);

        List<Ticket> tickets = ticketService.getAllTickets();

        assertEquals(allTickets, tickets);
    }

    @Test
    @DisplayName("Test getting not all tickets from db. False case - size not equals")
    public void getAllTicketsTest_ReturnNotFullList() {

        when(ticketRepository.findAll()).thenReturn(allTickets);

        List<Ticket> tickets = ticketService.getAllTickets();

        assertFalse(allTickets.size() != tickets.size());
    }

    @Test
    @DisplayName("Test tickets with pagination. Success case - all tickets with pagination got")
    public void getUserTicketsWithPaginationTest_Success() {

        when(ticketRepository.getTicketsByRequesterId("requester", PageRequest.of(2, 3))).thenReturn(allTickets.subList(2, 4));

        List<Ticket> tickets = ticketService.getUserTicketsWithPagination("requester", 2, 3);

        assertEquals(tickets.size(), allTickets.subList(2, 4).size());

        Ticket ticketsStartEl = tickets.get(0);
        Ticket allTicketsPagStartEl = allTickets.subList(2, 4).get(0);
        assertEquals(allTicketsPagStartEl, ticketsStartEl);

        Ticket ticketsEndEl = tickets.get(1);
        Ticket allTicketsPagEndEl = allTickets.subList(2, 4).get(1);
        assertEquals(allTicketsPagEndEl, ticketsEndEl);
    }

    @Test
    @DisplayName("Test that current user has ADMIN role. Success case")
    public void hasCurrentUserAdminRoleTest_Success() {
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(authenticationFacade.getAuthentication().getAuthorities()).thenReturn(getTwoAuthorities("ROLE_ADMIN", "ROLE_MANAGER"));

        assertTrue(ticketService.hasCurrentUserAdminRole());
    }

    @Test
    @DisplayName("Test that current user has not ADMIN role. Fail case")
    public void hasCurrentUserAdminRoleTest_HasNotAdminRole() {
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(authenticationFacade.getAuthentication().getAuthorities()).thenReturn(getTwoAuthorities("ROLE_SOME_ROLE", "ROLE_MANAGER"));

        assertFalse(ticketService.hasCurrentUserAdminRole());
    }

    @Test
    @DisplayName("Control that its current user ticket. Success case with no admin user")
    public void isCanEditTicketInfoNoAdminTest_Success() {
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(authenticationFacade.getAuthentication().getAuthorities()).thenReturn(getAuthority("ROLE_SOME_ROLE"));
        when(authenticationFacade.getAuthentication().getName()).thenReturn(requester1.getId());

        assertTrue(ticketService.isCanEditTicketInfo(ticket1));
        assertFalse(ticketService.isCanEditTicketInfo(ticket2));
        assertFalse(ticketService.isCanEditTicketInfo(ticket3));
        assertFalse(ticketService.isCanEditTicketInfo(ticket4));
    }

    @Test
    @DisplayName("Control that its current user ticket. Success case with admin rules")
    public void isCanEditTicketInfoAdminTest_Success() {
        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(authenticationFacade.getAuthentication().getAuthorities()).thenReturn(getAuthority("ROLE_ADMIN"));
        when(authenticationFacade.getAuthentication().getName()).thenReturn(requester1.getId());

        assertTrue(ticketService.isCanEditTicketInfo(ticket1));
        assertTrue(ticketService.isCanEditTicketInfo(ticket2));
        assertTrue(ticketService.isCanEditTicketInfo(ticket3));
        assertTrue(ticketService.isCanEditTicketInfo(ticket4));
    }

    @Test
    @DisplayName("Delete user ticket. Success case with user ticket")
    public void deleteTicketTest_UserTicketSuccess() {
        Long ticketId = 1L;
        User currentUser = requester1;

        when(ticketRepository.getTicketById(ticketId)).thenReturn(Optional.of(ticket1));

        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(authenticationFacade.getAuthentication().getAuthorities()).thenReturn(getAuthority("ROLE_USER"));
        lenient().when(authenticationFacade.getAuthentication().getName()).thenReturn(currentUser.getId());

        ticketService.deleteTicket(ticketId);

        verify(ticketRepository).deleteById(ticketId);
    }

    @Test
    @DisplayName("Delete user ticket. Fail case when not user ticket with exception")
    public void deleteTicketTest_NotUserTicketFail() {
        Long ticketId = 2L;
        User currentUser = requester1;

        when(ticketRepository.getTicketById(ticketId)).thenReturn(Optional.of(ticket2));

        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(authenticationFacade.getAuthentication().getAuthorities()).thenReturn(getAuthority("ROLE_USER"));
        when(authenticationFacade.getAuthentication().getName()).thenReturn(currentUser.getId());

        ActionNotAllowException exception = assertThrows(ActionNotAllowException.class, () -> ticketService.deleteTicket(ticketId));
        assertEquals("You can't delete another's tickets", exception.getMessage());
    }

    @Test
    @DisplayName("Delete user ticket. Success case when not user ticket but user has role ADMIN")
    public void deleteTicketTest_NotUserTicketSuccess() {
        Long ticketId = 2L;
        User currentUser = requester1;

        when(ticketRepository.getTicketById(ticketId)).thenReturn(Optional.of(ticket2));

        when(authenticationFacade.getAuthentication()).thenReturn(authentication);
        when(authenticationFacade.getAuthentication().getAuthorities()).thenReturn(getAuthority("ROLE_ADMIN"));
        lenient().when(authenticationFacade.getAuthentication().getName()).thenReturn(currentUser.getId());

        ticketService.deleteTicket(ticketId);

        verify(ticketRepository).deleteById(ticketId);
    }


    private static ArrayList getAuthority(String authority) {
        SimpleGrantedAuthority simpleGrantedAuthorityUser = new SimpleGrantedAuthority(authority);

        Collection authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthorityUser);

        return (ArrayList) authorities;
    }

    private static ArrayList getTwoAuthorities(String authority1, String authority2) {
        SimpleGrantedAuthority simpleGrantedAuthority1 = new SimpleGrantedAuthority(authority1);
        SimpleGrantedAuthority simpleGrantedAuthority2 = new SimpleGrantedAuthority(authority2);

        Collection authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority1);
        authorities.add(simpleGrantedAuthority2);

        return (ArrayList) authorities;
    }
}
