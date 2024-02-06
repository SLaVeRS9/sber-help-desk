package ru.sberbank.edu.ticketservice.ticket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.sberbank.edu.ticketservice.entity.User;
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
public class ShortViewTicketDto implements Serializable {
    private Long id;
    private String title;
    private User requester;
    private User manager;
    private TicketStatus status;
    private LocalDateTime controlPeriodAt;
}
