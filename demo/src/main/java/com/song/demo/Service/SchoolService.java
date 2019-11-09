package com.song.demo.Service;

import com.song.demo.entity.School;

public interface SchoolService {

    School save(School school);

    Boolean update(School school);

    Boolean delete(String id);

    School query(String id);

}
