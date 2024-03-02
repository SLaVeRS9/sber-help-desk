package ru.sberbank.edu.ticketservice.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.sberbank.edu.ticketservice.comment.Comment;
import ru.sberbank.edu.ticketservice.ticket.enums.Estimation;
import ru.sberbank.edu.ticketservice.ticket.enums.TicketStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
public class EditTicketDto {

    @NotNull(message = "Id can't be empty")
    private Long id;

    @Size(min = 3, max = 10, message = "Code size must be between 3 and 10")
    private String code;

    @NotBlank(message = "Title can't be empty")
    @Size(max = 100, message = "Title size must be less then 100 symbols")
    private String title;

    @Size(max = 1024, message = "Description size must be less then 1024 symbols")
    private String description;

    @NotBlank(message = "Requester id can't be empty")
    private String requesterId;
    @NotBlank(message = "Requester name can't be empty")
    private String requesterFullName;

    private String managerId;
    private String managerFullName;

    private TicketStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime statusUpdatedAt;

    private LocalDateTime controlPeriodAt;

    private List<Comment> comments;

    private Estimation estimation;
}
