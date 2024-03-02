package ru.sberbank.edu.ticketservice.ticket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.sberbank.edu.ticketservice.ticket.entity.Ticket;
import ru.sberbank.edu.ticketservice.ticket.dto.ShortViewTicketDto;

/**
 * Mapper для сущности ShortViewTicket
 * @author SLaVeRS9
 * @version 1.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShortViewTicketMapper {

    /**
     * Метод конвертации entity ticket в ShortViewTicketDto
     * @param ticket - entity ticket
     * @return возвращает объект dto
     */
    @Mapping(source = "requester.id", target = "requesterId")
    @Mapping(source = "requester.name", target = "requesterFullName")
    @Mapping(source = "manager.name", target = "managerFullName")
    ShortViewTicketDto ticketToShortViewTicketDto(Ticket ticket);

    /**
     * Метод конвертации FullViewTicketDto в entity ticket
     * @param shortViewTicketDto - dto ShortViewTicketDto
     * @return возвращает объект entity
     */
    Ticket shortViewTicketDtoToTicket(ShortViewTicketDto shortViewTicketDto);
}
