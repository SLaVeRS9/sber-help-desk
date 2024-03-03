package ru.sberbank.edu.ticketservice.ticket.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sberbank.edu.ticketservice.ticket.entity.Ticket;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.id = :id")
    Optional<Ticket> getTicketById(Long id);

    List<Ticket> getTicketsByRequesterId(String requester_id, Pageable pageable);
    List<Ticket> getTicketsByRequesterId(String requester_id);


}
