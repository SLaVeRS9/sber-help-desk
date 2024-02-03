package ru.sberbank.edu.ticketservice.ticket;

import org.springframework.data.repository.CrudRepository;
import ru.sberbank.edu.ticketservice.ticket.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

}
