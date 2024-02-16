package ru.sberbank.edu.ticketservice.ticket.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import ru.sberbank.edu.ticketservice.comment.Comment;
import ru.sberbank.edu.ticketservice.profile.User;
import ru.sberbank.edu.ticketservice.ticket.Estimation;
import ru.sberbank.edu.ticketservice.ticket.TicketStatus;

import java.time.LocalDateTime;
import java.util.List;



/**
 * DTO для передачи всей информации о тикете. Применяется для отображения страницы о тикете
 * @author SLaVeRS9
 * @version 1.0
 */
@Builder
@Getter
@Setter
@ToString
public class FullViewTicketDto {
    private Long id;
    private String code;
    @NotBlank(message = "Title can't be empty")
    @Size(max = 100, message = "Title size must be less then 100 symbols")
    private String title;
    @Column(name = "description")
    @Size(max = 600, message = "Description size must be less then 600 symbols")
    private String description;
    private User requester;
    private User manager;
    private TicketStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime statusUpdatedAt;
    private LocalDateTime controlPeriodAt;
    private List<Comment> comments;
    private Estimation estimation;
}
