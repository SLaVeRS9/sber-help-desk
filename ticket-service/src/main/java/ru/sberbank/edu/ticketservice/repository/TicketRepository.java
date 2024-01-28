package ru.sberbank.edu.ticketservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sberbank.edu.ticketservice.entity.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

}
