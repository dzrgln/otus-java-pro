package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Phone(Long id, String phone) {
        this.id = id;
        this.phone = phone;
    }
}
