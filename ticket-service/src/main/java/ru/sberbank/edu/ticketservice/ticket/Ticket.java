package ru.sberbank.edu.ticketservice.ticket;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.sberbank.edu.common.model.TimestampedEntity;
import ru.sberbank.edu.ticketservice.comment.Comment;
import ru.sberbank.edu.ticketservice.profile.User;
import ru.sberbank.edu.ticketservice.ticket.enums.Estimation;
import ru.sberbank.edu.ticketservice.ticket.enums.TicketStatus;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ticket extends TimestampedEntity implements Serializable {
    static final long serialVersionUID = -100500L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    @NotBlank(message = "Code can't be empty")
    @Size(min = 3, max = 10, message = "Code size must be between 3 and 10")
    private String code;

    @Column (name = "title")
    @NotBlank(message = "Title can't be empty")
    @Size(max = 100, message = "Title size must be less then 100 symbols")
    private String title;

    @Column (name = "description")
    @Size(max = 1024, message = "Description size must be less then 1024 symbols")
    private String description;

    //TODO CascadeType проверить
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "requester_id")
    @NotNull(message = "Requester must exist")
    private User requester;

    //TODO переписать fetch через entityGraph
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "manager_id")
    private User manager;

    @Enumerated(EnumType.STRING)
    @Column (name = "status")
    @NotNull(message = "Status can't be empty")
    private TicketStatus status;

    //TODO Добавить маску отображения
    //TODO сделать листенером на смену статуса
    @Column (name = "status_updated_at")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime statusUpdatedAt;

    //TODO Добавить маску отображения
    //TODO сделать вычисляемым полем
    @Column (name = "control_period_at")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime controlPeriodAt;

    //TODO доделать как отдельную сущность, в которую будет писаться переписка. С датами записи, кто, кому отвечал
    //TODO Чекнуть что List лучший вариант
    //TODO Работу с комментариями сделать как доп задачу
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ticket_id")
    private List<Comment> comments = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    @Column (name = "estimation")
    private Estimation estimation;
}
