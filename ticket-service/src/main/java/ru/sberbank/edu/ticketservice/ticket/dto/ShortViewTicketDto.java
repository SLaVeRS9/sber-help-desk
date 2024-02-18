package ru.sberbank.edu.ticketservice.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.sberbank.edu.ticketservice.profile.User;
import ru.sberbank.edu.ticketservice.ticket.TicketStatus;

import java.io.Serializable;
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
    private Long id;
    private String code;
    @NotBlank(message = "Title can't be empty")
    private String title;
    private User requester;
    private User manager;
    private TicketStatus status;
    private LocalDateTime creationAt;
    private LocalDateTime controlPeriodAt;
}
