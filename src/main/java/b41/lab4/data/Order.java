package b41.lab4.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ORDERS", schema = "LAB4_IPZWZ_SCHEMA")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "VIEWER_ID")
    private Viewer viewer;
    @ManyToOne
    @JoinColumn(name = "TICKET_ID")
    private Ticket ticket;
    @JoinColumn(name = "ORDER_DATE")
    private LocalDateTime orderDate;

    @PrePersist
    public void prePersist() {
        if (this.orderDate == null) {
            this.orderDate = LocalDateTime.now();
        }
    }
}
