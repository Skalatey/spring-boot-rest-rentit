package com.rentit.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.rentit.entity.Flat;
import com.rentit.exception.ResourceNotFoundException;
import com.rentit.repository.FlatRepository;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class FlatService {

    @Resource
    private FlatRepository flatRepository;

    public Page<Flat> getFlatByStreetId(Long id, Pageable pageable) {
        Page<Flat> pageByIdStreet = flatRepository.findByIdStreet(id, pageable);
        if (pageByIdStreet.getContent().size() == 0) {
            throw new ResourceNotFoundException("For street with " + id + ", flats are not found");
        }
        return pageByIdStreet;
    }
}
