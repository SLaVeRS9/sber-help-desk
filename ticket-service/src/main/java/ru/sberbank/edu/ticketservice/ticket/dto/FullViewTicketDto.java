package ru.sberbank.edu.ticketservice.ticket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.sberbank.edu.ticketservice.comment.Comment;
import ru.sberbank.edu.ticketservice.entity.User;
import ru.sberbank.edu.ticketservice.enums.Estimation;
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
    private String title;
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
