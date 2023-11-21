package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class Client implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, mappedBy = "client")
    private List<Phone> phones = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    private Address address;

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones.addAll(phones);

        if (address != null) {
            address.setClient(this);
        }

        phones.forEach(p -> p.setClient(this));
    }

    @Override
    public Client clone() {

        Address addressFromProxy = this.address;
        if (this.address instanceof HibernateProxy) {
            addressFromProxy = Hibernate.unproxy(this.address, Address.class);
        }

        return new Client(this.id, this.name, addressFromProxy, this.phones);
    }
}
