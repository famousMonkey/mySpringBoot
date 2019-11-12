package com.song.demo.Service;

import com.song.demo.entity.Police;

public interface PoliceService {

    Police persistence(Police police);
    void delete(String id);
    Police update(Police police);
    Police query(String number);


}
