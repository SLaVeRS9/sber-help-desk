package ru.sberbank.edu.ticketservice.ticket;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.edu.ticketservice.profile.*;
import ru.sberbank.edu.ticketservice.ticket.dto.CreateTicketDto;
import ru.sberbank.edu.ticketservice.ticket.dto.EditTicketDto;
import ru.sberbank.edu.ticketservice.ticket.dto.FullViewTicketDto;
import ru.sberbank.edu.ticketservice.ticket.mapper.CreateTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.mapper.EditTicketMapper;
import ru.sberbank.edu.ticketservice.ticket.mapper.FullViewTicketMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller для обработки запросов на отображение ui
 * @author SLaVeRS9
 * @version 2.0
 */

@Controller
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketUIController {
    private final TicketService ticketService;
    private final UserService userService;
    private final UserProfileMapper userProfileMapper;
    private final FullViewTicketMapper fullViewTicketMapper;
    private final CreateTicketMapper createTicketMapper;
    private final EditTicketMapper editTicketMapper;

    @Value("${ticket.code}")
    private String ticketCode;

    @Value("${ticket.startedStatus}")
    private String startedStatus;

    /**
     * Контроллер отображения страницы со всей информацией о тикете
     * @param id id тикета
     * @param model объект для хранения атрибутов и предеачи их в представление
     * @param currentUser текущий пользователь
     * @return представление информации о тикете
     */
    @GetMapping("/{id}")
    //TODO Добавить обработку исключений
    public String showFullTicketInfo(@PathVariable Long id, Model model,
                                 @AuthenticationPrincipal UserDetails currentUser) {
        Ticket ticket = ticketService.getTicketInfo(id);
        var fullViewTicketDto = fullViewTicketMapper.ticketToFullViewTicketDto(ticket);

        User currentUserEntity = userService.getUserById(currentUser.getUsername());
        UserRole currentUserRole = currentUserEntity.getRole();

        model.addAttribute("fullViewTicketDto", fullViewTicketDto);
        model.addAttribute("userId", currentUser.getUsername());
        model.addAttribute("currentUserRole", currentUserRole.name());

        return "ticket-info";
    }

    /**
     * Контроллер показа страницы создания тикета
     * @param createTicketDto объект данных для создания
     * @param userDetails текущий пользователь
     * @param model объект для хранения атрибутов и предеачи их в представление
     * @return представление создания тикета
     */
    @GetMapping("/create")
    public String showCreatePage(@ModelAttribute("createTicketDto") CreateTicketDto createTicketDto,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 Model model) {
        String currentUserId = userDetails.getUsername();
        User currentUser = userService.getUserById(currentUserId);
        ProfileDto currentUserDto = userProfileMapper.UserToProfileDto(currentUser);

        List<User> managers = userService.findByRole(UserRole.MANAGER);
        Set<ProfileDto> managersDto = managers.stream()
                .map(userProfileMapper::UserToProfileDto)
                .collect(Collectors.toSet());
        createTicketDto.setRequesterId(currentUserId);

        createTicketDto.setCode(ticketCode);

        model.addAttribute("currentUserDto", currentUserDto);
        model.addAttribute("managers", managersDto);
        model.addAttribute("ticketCode", ticketCode);
        model.addAttribute("startedStatus", startedStatus);

        return "create-ticket";
    }

    /**
     * Контроллер post запроса на созданеи тикета
     * @param createTicketDto сущность с данными для создания
     * @param bindingResult сущность для ошибок валидации полей
     * @param userDetails текущий пользователь
     * @param model объект для хранения атрибутов и предеачи их в представление
     * @return страницу со списком всех тикетов
     */
    @PostMapping
    public String createTicket(@ModelAttribute("createTicketDto") @Valid CreateTicketDto createTicketDto,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal UserDetails userDetails,
                               Model model) {
        if(bindingResult.hasErrors()) {
            String currentUserId = userDetails.getUsername();
            User currentUser = userService.getUserById(currentUserId);
            ProfileDto currentUserDto = userProfileMapper.UserToProfileDto(currentUser);

            List<User> managers = userService.findByRole(UserRole.MANAGER);
            Set<ProfileDto> managersDto = managers.stream()
                    .map(userProfileMapper::UserToProfileDto)
                    .collect(Collectors.toSet());

            model.addAttribute("currentUserDto", currentUserDto);
            model.addAttribute("managers", managersDto);
            model.addAttribute("ticketCode", ticketCode);
            model.addAttribute("startedStatus", startedStatus);

            return "create-ticket";
        }

        ticketService.createTicket(createTicketDto);
        return "redirect:dashboard";
    }

    /**
     * Контроллер delete запроса на удаление тикета
     * @param id идентификатор тикета
     * @return страница со списком всех тикетов
     */
    @DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return "redirect:/dashboard";
    }

    @GetMapping("/{id}/edit")
    public String showEditPage(@PathVariable Long id,
                               @AuthenticationPrincipal UserDetails currentUser,
                               Model model) {
        //TODO Обработка exception
        Ticket ticket = ticketService.getTicketInfo(id);

        if( !ticketService.isCanEditTicketInfo(ticket)) {
            return "not-allow";
        }

        EditTicketDto editTicketDto = editTicketMapper.ticketToEditTicketDto(ticket);
        model.addAttribute("editTicketDto", editTicketDto);

        if (ticketService.hasCurrentUserAdminRole()) {
            model.addAttribute("editTicketDto", editTicketDto);
            model.addAttribute("isAdmin", true);
        }

        List<User> managers = userService.findByRole(UserRole.MANAGER);
        List<ProfileDto> managersDto = managers.stream()
                .map(userProfileMapper::UserToProfileDto)
                .collect(Collectors.toList());
        model.addAttribute("managers", managersDto);

        return "edit-ticket";
    }



    @PatchMapping("/{id}")
    public String editTicket(@ModelAttribute("ticket") @Valid EditTicketDto editTicketDto,
                             BindingResult bindingResult) {
        //TODO Обработка exception
        if (bindingResult.hasErrors()) {
            return "edit-ticket";
        }
        Ticket ticket = ticketService.getTicketInfo(editTicketDto.getId());

        User manager = userService.getUserById(editTicketDto.getManagerId());
        ticket.setManager(manager);

        ticket = editTicketMapper.editTicketDtoToTicket(editTicketDto, ticket);
        ticketService.editTicket(ticket);



        //fullViewTicketDtoModel = ticketService.editTicket(fullViewTicketDtoModel, userId);
        return "redirect:/tickets/"+editTicketDto.getId();
    }
}
