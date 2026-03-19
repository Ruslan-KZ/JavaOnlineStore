package java_projects.online_store.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "delivery_addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 100)
    private String city;

    @Column(length = 255)
    private String street;

    @Column(length = 20)
    private String house;

    @Column(length = 20)
    private String apartment;

    @Column(length = 10)
    private String zipCode;

    @Column(length = 500)
    private String comment;
}