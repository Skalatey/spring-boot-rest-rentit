package com.rentit.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "flat")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Flat {
    @Id
    @SequenceGenerator(name = "hibernate_sequence", sequenceName = "hibernate_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence")
    @Column(name = "fl_id")
    private Long id;

    @Column(name = "fl_description")
    private String description;

    @Column(name = "fl_price")
    private BigDecimal price;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fl_creation_time", updatable = false)
    @CreationTimestamp
    private Date createdOn;

    @AssociationOverride(name = "street",
            joinColumns = @JoinColumn(name = "fl_fstr_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "metro", column = @Column(name = "fl_metro")),
            @AttributeOverride(name = "houseNumber", column = @Column(name = "fl_house_number"))
    })
    private Address address;
}
