package ru.sberbank.edu.ticketservice.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.sberbank.edu.ticketservice.ticket.enums.Estimation;
import ru.sberbank.edu.ticketservice.ticket.enums.TicketStatus;

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
    @NotBlank(message = "Id can't be empty")
    private Long id;

    @NotBlank(message = "Code can't be empty")
    @Size(min = 3, max = 10, message = "Code size must be between 3 and 10")
    private String code;

    @NotBlank(message = "Title can't be empty")
    @Size(max = 100, message = "Title size must be less then 100 symbols")
    private String title;

    @Size(max = 1024, message = "Description size must be less then 1024 symbols")
    private String description;

    @NotNull(message = "Requester id can't be empty")
    private String requesterId;

    @NotBlank(message = "Requester can't be empty")
    private String requesterFullName;

    private String managerId;

    private String managerFullName;

    @NotNull(message = "Status can't be empty")
    private TicketStatus status;

    @NotNull(message = "Creation data can't be empty")
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime statusUpdatedAt;

    private LocalDateTime controlPeriodAt;

    private Estimation estimation;
}
