package ru.sberbank.edu.ticketservice.ticket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.sberbank.edu.ticketservice.ticket.dto.ShortViewTicketDto;

//https://www.baeldung.com/mapstruct
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-test.yaml")
public class ShortViewTicketMapperIntegrationTest {

    /*@Autowired
    ShortViewTicketDto shortViewTicketDto;*/

    @Test
    public void ticketToShortViewTicketDtoTest_success() {

    }


}
