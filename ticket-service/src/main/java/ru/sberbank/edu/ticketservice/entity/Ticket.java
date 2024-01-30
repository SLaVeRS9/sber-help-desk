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
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.sberbank.edu.ticketservice.enums.Rating;
import ru.sberbank.edu.ticketservice.enums.TicketStatus;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@ToString
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
}
