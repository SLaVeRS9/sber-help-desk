package ru.sberbank.edu.ticketservice.ticket.mapper;

import org.mapstruct.*;
import ru.sberbank.edu.ticketservice.profile.mapper.UserProfileMapper;
import ru.sberbank.edu.ticketservice.ticket.entity.Ticket;
import ru.sberbank.edu.ticketservice.ticket.dto.CreateTicketDto;

/**
 * Mapper для сущности CreateTicketDto
 * @author SLaVeRS9
 * @version 1.0
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserProfileMapper.class})
public interface CreateTicketMapper {
    /**
     * Метод конвертации entity ticket в CreateTicketDto
     * @param ticket - entity ticket
     * @return возвращает объект dto
     */
    @Mapping(source = "requester.id", target = "requesterId")
    @Mapping(source = "manager.id", target = "managerId")
    CreateTicketDto ticketToCreateTicketDto(Ticket ticket);

    /**
     * Метод конвертации CreateTicketDto в entity ticket
     * @param createTicketDto - dto CreateTicketDto
     * @return возвращает объект entity
     */
    @Mapping(source = "requesterId", target = "requester.id")
    @Mapping(source = "managerId", target = "manager.id")
    Ticket createTicketDtoToTicket(CreateTicketDto createTicketDto);
}
