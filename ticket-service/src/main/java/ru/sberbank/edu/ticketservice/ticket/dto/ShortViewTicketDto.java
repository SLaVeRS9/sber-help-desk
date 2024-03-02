package ru.sberbank.edu.ticketservice.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.sberbank.edu.ticketservice.ticket.enums.TicketStatus;

import java.time.LocalDateTime;

/**
 * DTO для передачи краткой информации о тикете. Применяется для отображения списка тикетов
 * @author SLaVeRS9
 * @version 1.0
 */
@Builder
@Getter
@Setter
public class ShortViewTicketDto {
    @NotNull(message = "ID can't be empty")
    private Long id;

    @NotBlank(message = "Code can't be empty")
    @Size(min = 3, max = 10, message = "Code size must be between 3 and 10")
    private String code;

    @NotBlank(message = "Title can't be empty")
    @Size(max = 100, message = "Title size must be less then 100 symbols")
    private String title;

    @NotBlank(message = "Requester can't be empty")
    private String requesterId;

    @NotBlank(message = "Requester can't be empty")
    private String requesterFullName;

    private String managerFullName;

    @NotNull(message = "Status can't be empty")
    private TicketStatus status;

    @NotNull(message = "Creation data can't be empty")
    private LocalDateTime creationAt;

    private LocalDateTime controlPeriodAt;
}
