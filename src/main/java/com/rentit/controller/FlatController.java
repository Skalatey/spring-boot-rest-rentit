package com.rentit.controller;

import com.rentit.entity.Flat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.rentit.converter.FlatConverter;
import com.rentit.dto.FlatDTO;
import com.rentit.service.FlatService;
import javax.annotation.Resource;
import java.util.List;

@RestController
public class FlatController {

    @Resource
    private FlatService flatService;

    @GetMapping(value = "/steets/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<FlatDTO>> getFlatsByStreetId(
            @PathVariable("id") Long id,
            Pageable pageable) {
        Page<Flat> flatByStreetId = flatService.getFlatByStreetId(id, pageable);
        List<FlatDTO> flatsDTO = FlatConverter.flatToFlatDTO(flatByStreetId);
        return new ResponseEntity<>(flatsDTO, HttpStatus.OK);
    }
}
