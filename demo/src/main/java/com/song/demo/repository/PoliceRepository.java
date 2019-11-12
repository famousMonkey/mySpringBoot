package com.song.demo.repository;

import com.song.demo.entity.Police;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PoliceRepository extends PagingAndSortingRepository<Police,String>, JpaRepository<Police,
        String>, JpaSpecificationExecutor<Police> {

    Police findByNumber(String number);


}
