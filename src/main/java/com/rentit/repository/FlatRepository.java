package com.rentit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rentit.entity.Flat;

public interface FlatRepository extends JpaRepository<Flat, Long> {
    @Query(value = "select fl from Flat fl JOIN FETCH fl.address.street as st where st.id = :idStreet",
    countQuery = "select count(fl) from Flat fl where fl.address.street.id = :idStreet")
    Page<Flat> findByIdStreet(@Param("idStreet") Long id, Pageable pageable);
}
