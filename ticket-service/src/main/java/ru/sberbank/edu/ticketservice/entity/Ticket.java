package ru.sberbank.edu.ticketservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import ru.sberbank.edu.ticketservice.enums.Rating;
import ru.sberbank.edu.ticketservice.enums.TicketStatus;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private User requester;
    private User manager;
    private String description;
    private String commentary;
    
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    
    @Enumerated(EnumType.STRING)
    private Rating rating;
    
    public Ticket(Long id, User requester, User manager, String description, String commentary, TicketStatus status,
            Rating rating) {
        this.id = id;
        this.requester = requester;
        this.manager = manager;
        this.description = description;
        this.commentary = commentary;
        this.status = status;
        this.rating = rating;
    }
    
    public Ticket() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
    
}
