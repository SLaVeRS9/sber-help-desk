package ru.sberbank.edu.ticketservice.ticket;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.sberbank.edu.ticketservice.comment.Comment;
import ru.sberbank.edu.ticketservice.entity.User;
import ru.sberbank.edu.ticketservice.enums.Estimation;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "title")
    @NotBlank(message = "Title mustn't empty")
    private String title;

    @Column (name = "description")
    private String description;

    //TODO Сделать через entity graph
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    @NotNull(message = "Requester mustn't empty")
    private User requester;

    //TODO Сделать через entity graph
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;

    @Enumerated(EnumType.STRING)
    @Column (name = "status")
    private TicketStatus status;

    @Column (name = "created_at")
    private LocalDateTime createdAt;

    //TODO сделать листенером на смену статуса
    @Column (name = "status_updated_at")
    private LocalDateTime statusUpdatedAt;

    //TODO сделать вычисляемым полем
    @Column (name = "control_period_at")
    private LocalDateTime controlPeriodAt;

    //TODO Проверить что ArrayList лучшее решение
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    private List<Comment> comments = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    @Column (name = "estimation")
    private Estimation estimation;
}
