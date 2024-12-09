package b41.lab4.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "TICKET", schema = "LAB4_IPZWZ_SCHEMA")
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalTime time;
    @JoinColumn(name = "SEAT_NUMBER")
    private String seatNumber;
    @JoinColumn(name = "MOVIE_NAME")
    private String movieName;
}
