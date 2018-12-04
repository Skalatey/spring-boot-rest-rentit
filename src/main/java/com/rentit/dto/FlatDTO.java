package com.rentit.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Builder
@Data
public class FlatDTO {
    private Long id;
    private String description;
    private String price;
    private String createdOn;
    private String metro;
    private String houseNumber;
    private String nameStreet;
    private String typeNameStreet;
    private int totalPage;
}
