package com.song.demo.repository;

import com.song.demo.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SchoolRepository extends PagingAndSortingRepository<School,String>, JpaRepository<School,
        String>, JpaSpecificationExecutor<School> {
}
