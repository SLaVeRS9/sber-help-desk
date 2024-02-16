package ru.sberbank.edu.ticketservice.ticket;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.id = :id")
    Ticket getTicketById(Long id);

    List<Ticket> getTicketsByRequesterId(String requester_id, Pageable pageable);


}
