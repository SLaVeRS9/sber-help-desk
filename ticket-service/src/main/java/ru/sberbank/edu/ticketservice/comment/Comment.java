/*package ru.sberbank.edu.ticketservice.comment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sberbank.edu.ticketservice.ticket.entity.Ticket;

import java.io.Serializable;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {
    static final long SerialVersionUID = -100500L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "text")
    @Size(max = 2048, message = "Text size must be less then 2048 symbols")
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Ticket ticket;

}*/
