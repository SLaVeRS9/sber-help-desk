package ru.sberbank.edu.ticketservice.ticket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.sberbank.edu.ticketservice.comment.Comment;
import ru.sberbank.edu.ticketservice.profile.User;
import ru.sberbank.edu.ticketservice.ticket.enums.Estimation;
import ru.sberbank.edu.ticketservice.ticket.enums.TicketStatus;

import java.time.LocalDateTime;
import java.util.List;

//TODO Подумать как грамотно реализовать, в том числе и перевод EditViewTicketDto на полный entity. После убрать Deprecated
/**
 * DTO для передачи информации для редактирования тикета
 * @author SLaVeRS9
 * @version 1.0
 */
@Deprecated
@Builder
@Getter
@Setter
@ToString
public class EditViewTicketDto {
    private Long id;
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
