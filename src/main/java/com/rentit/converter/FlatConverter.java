package com.rentit.converter;

import org.springframework.data.domain.Page;
import org.springframework.util.Assert;
import com.rentit.dto.FlatDTO;
import com.rentit.entity.Flat;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FlatConverter {
    public static List<FlatDTO> flatToFlatDTO(Page<Flat> flat) {
        Assert.isTrue(Objects.nonNull(flat), "List flats are null");
        return flat.getContent().stream().map(m -> {
            FlatDTO flatDTO = flatToFlatDTO(m);
            flatDTO.setTotalPage(flat.getTotalPages());
            return flatDTO;
        }).collect(Collectors.toList());
    }

    public static FlatDTO flatToFlatDTO(Flat flat) {
        Assert.notNull(flat, "Flat is not be null");
        return FlatDTO.builder()
                .id(flat.getId())
                .description(flat.getDescription())
                .price(flat.getPrice().toString())
                .createdOn(flat.getCreatedOn().toString())
                .metro(flat.getAddress().getMetro())
                .houseNumber(flat.getAddress().getHouseNumber())
                .nameStreet(flat.getAddress().getStreet().getName())
                .typeNameStreet(flat.getAddress().getStreet().getTypeName()).build();
    }
}
