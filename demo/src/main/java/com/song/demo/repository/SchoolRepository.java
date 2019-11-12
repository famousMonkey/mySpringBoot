package com.song.demo.repository;

import com.song.demo.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SchoolRepository extends PagingAndSortingRepository<School,String>, JpaRepository<School,
        String>, JpaSpecificationExecutor<School> {

    @Transactional
    @Modifying
    @Query(value = "update School set address=?2 where id=?1")
    int updateResource(String id,String address);


    @Transactional
    @Modifying
    @Query(value = "delete from School where id=?1")
    int deleteResourceById(String id);




}
