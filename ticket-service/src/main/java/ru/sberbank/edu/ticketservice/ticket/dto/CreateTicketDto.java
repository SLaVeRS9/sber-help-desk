package ru.sberbank.edu.ticketservice.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.sberbank.edu.ticketservice.profile.dto.ProfileDto;

/**
 * DTO для создания тикета
 * @author SLaVeRS9
 * @version 1.0
 */

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateTicketDto {
    @NotBlank(message = "Code can't be empty")
    @Size(min = 3, max = 10, message = "Code size must be between 3 and 10")
    private String code;

    @NotBlank(message = "Title can't be empty")
    @Size(max = 100, message = "Title size must be less then 100 symbols")
    private String title;

    @Size(max = 1024, message = "Description size must be less then 1024 symbols")
    private String description;

    private ProfileDto managerDto;
}
