package ru.sberbank.edu.ticketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sberbank.edu.ticketservice.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
