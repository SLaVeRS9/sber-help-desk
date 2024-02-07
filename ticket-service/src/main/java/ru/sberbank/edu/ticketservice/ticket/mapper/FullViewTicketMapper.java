package ru.sberbank.edu.ticketservice.ticket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.sberbank.edu.ticketservice.ticket.Ticket;
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
    FullViewTicketDto ticketToFullViewTicketDto(Ticket ticket);

    /**
     * Метод конвертации FullViewTicketDto в entity ticket
     * @param fullViewTicketDto - dto FullViewTicketDto
     * @return возвращает объект entity
     */
    Ticket fullViewTicketDtoToTicket(FullViewTicketDto fullViewTicketDto);
}
