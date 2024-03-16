package ru.sberbank.edu.ticketservice.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.sberbank.edu.ticketservice.ticket.enums.Estimation;
import ru.sberbank.edu.ticketservice.ticket.enums.TicketStatus;

import java.time.LocalDateTime;
import java.util.List;

public class EditUserByAdminDto {
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

    @NotBlank(message = "Requester can't be empty")
    private String requesterFullName;

    private String managerFullName;

    @NotBlank(message = "Status can't be empty")
    private TicketStatus status;

    @NotNull(message = "Creation data can't be empty")
    private LocalDateTime createdAt;

    private LocalDateTime statusUpdatedAt;

    private LocalDateTime controlPeriodAt;

    private Estimation estimation;
}
