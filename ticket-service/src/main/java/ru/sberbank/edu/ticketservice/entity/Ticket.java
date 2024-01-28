package ru.sberbank.edu.ticketservice.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import ru.sberbank.edu.ticketservice.enums.Rating;
import ru.sberbank.edu.ticketservice.enums.TicketStatus;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private User requester;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;
    
    @Column (name = "description")
    private String description;

    @Column (name = "commentary")
    private String commentary;
    
    @Enumerated(EnumType.STRING)
    @Column (name = "status")
    private TicketStatus status;
    
    @Enumerated(EnumType.STRING)
    @Column (name = "rating")
    private Rating rating;
    
    @Column (name = "creation_time")
    private LocalDateTime creationTime;
    
    @Column (name = "last_status_change_time")
    private LocalDateTime lastStatusChangeTime;

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

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getLastStatusChangeTime() {
        return lastStatusChangeTime;
    }

    public void setLastStatusChangeTime(LocalDateTime lastStatusChangeTime) {
        this.lastStatusChangeTime = lastStatusChangeTime;
    }
}
