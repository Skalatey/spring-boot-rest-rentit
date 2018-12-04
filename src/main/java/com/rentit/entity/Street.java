package com.rentit.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fias_street")
public class Street {
    @Id
    @SequenceGenerator(name = "hibernate_sequence", sequenceName = "hibernate_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence")
    @Column(name = "fstr_id")
    private Long id;

    @Column(name = "fstr_name")
    private String name;

    @Column(name = "fstr_type_name")
    private String typeName;

    @Column(name = "fstr_guid")
    private String fstr_guid;

    @OneToMany(mappedBy = "address.street", cascade = CascadeType.PERSIST)
    private List<Flat> flats = new ArrayList<>();
}
