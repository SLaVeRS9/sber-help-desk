package ru.sberbank.edu.ticketservice.ticket;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Formula;
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

    //TODO проверить
    @Formula(value = "'SBHD-'+id")
    @Column(name = "ticket_code")
    private String code;

    @Column (name = "title")
    @NotBlank(message = "Title can't be empty")
    @Size(max = 100, message = "Title size must be less then 100 symbols")
    private String title;

    @Column (name = "description")
    @Size(max = 600, message = "Description size must be less then 600 symbols")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
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

    //TODO Добавить маску отображения
    @Column (name = "created_at")
    private LocalDateTime createdAt;

    //TODO Добавить маску отображения
    //TODO сделать листенером на смену статуса
    @Column (name = "status_updated_at")
    private LocalDateTime statusUpdatedAt;

    //TODO Добавить маску отображения
    //TODO сделать вычисляемым полем
    @Column (name = "control_period_at")
    private LocalDateTime controlPeriodAt;

    //TODO доделать как отдельную сущность, в которую будет писаться переписка. С датами записи, кто, кому отвечал
    //TODO Чекнуть что List лучший вариант
    //TODO Работу с комментариями сделать как доп задачу
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private List<Comment> comments = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    @Column (name = "estimation")
    private Estimation estimation;
}
