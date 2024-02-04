package ru.sberbank.edu.ticketservice.ticket;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "title")
    @NotBlank(message = "Title can't be empty")
    private String title;

    @Column (name = "description")
    @Size(max = 600, message = "Description size must be less then 600 symbols")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    @NotNull(message = "Requester must exist")
    private User requester;

    //TODO переписать fetch через entityGraph
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

    //TODO доделать как отдельную сущность, в которую будет писаться переписка. С датами записи, кто, кому отвечал
    //TODO Чекнуть что List лучший вариант
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private List<Comment> comments = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    @Column (name = "estimation")
    private Estimation estimation;
}
