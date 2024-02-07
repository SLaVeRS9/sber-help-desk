package ru.sberbank.edu.ticketservice.ticket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.sberbank.edu.ticketservice.ticket.Ticket;
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
    ShortViewTicketDto ticketToShortViewTicketDto(Ticket ticket);

    /**
     * Метод конвертации FullViewTicketDto в entity ticket
     * @param shortViewTicketDto - dto ShortViewTicketDto
     * @return возвращает объект entity
     */
    Ticket shortViewTicketDtoToTicket(ShortViewTicketDto shortViewTicketDto);
}
