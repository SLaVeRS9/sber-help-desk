package ru.sberbank.edu.ticketservice.ticket.mapper;

import org.mapstruct.*;
import ru.sberbank.edu.ticketservice.ticket.entity.Ticket;
import ru.sberbank.edu.ticketservice.ticket.dto.EditTicketDto;

/**
 * Mapper для сущности EditTicketDto
 * @author SLaVeRS9
 * @version 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EditTicketMapper {
    /**
     * Метод конвертации entity ticket в EditTicketDto
     * @param ticket - entity ticket
     * @return возвращает объект dto
     */
    @Mapping(source = "requester.id", target = "requesterId")
    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "requester.name", target = "requesterFullName")
    @Mapping(source = "manager.name", target = "managerFullName")
    EditTicketDto ticketToEditTicketDto(Ticket ticket);

    /**
     * Метод конвертации EditTicketDto в entity ticket
     * @param editTicketDto - dto EditTicketDto
     * @return возвращает объект entity
     */
    @Mapping(source = "requesterId", target = "requester.id")
    @Mapping(source = "managerId", target = "manager.id")
    Ticket editTicketDtoToTicket(EditTicketDto editTicketDto);

    Ticket editTicketDtoToTicket(EditTicketDto editTicketDto, @MappingTarget Ticket ticket);
}
