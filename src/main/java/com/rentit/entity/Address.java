package com.rentit.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Address {

    private String metro;

    private String houseNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private Street street;
}
