package ru.sberbank.edu.ticketservice.ticket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.sberbank.edu.ticketservice.ticket.entity.Ticket;
import ru.sberbank.edu.ticketservice.ticket.dto.FullViewTicketDto;

/**
 * Mapper для сущности FullViewTicketDto
 * @author SLaVeRS9
 * @version 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FullViewTicketMapper {
    /**
     * Метод конвертации entity ticket в FullViewTicketDto
     * @param ticket - entity ticket
     * @return возвращает объект dto
     */
    @Mapping(source = "requester.id", target = "requesterId")
    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "requester.name", target = "requesterFullName")
    @Mapping(source = "manager.name", target = "managerFullName")
    FullViewTicketDto ticketToFullViewTicketDto(Ticket ticket);

    /**
     * Метод конвертации FullViewTicketDto в entity ticket
     * @param fullViewTicketDto - dto FullViewTicketDto
     * @return возвращает объект entity
     */
    @Mapping(source = "requesterId", target = "requester.id")
    @Mapping(source = "managerId", target = "manager.id")
    Ticket fullViewTicketDtoToTicket(FullViewTicketDto fullViewTicketDto);
}
