package com.song.demo.Service.impl;

import com.song.demo.Service.PoliceService;
import com.song.demo.entity.Police;
import com.song.demo.repository.PoliceRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName: PoliceServiceImpl
 * @Description: //TODO()
 * @Author: 宋正健
 * @Date: 2019/11/12 10:13
 * @Version: 1.0
 **/
@Service
public class PoliceServiceImpl implements PoliceService {


    @Resource
    private PoliceRepository policeRepository;


    @Override
    public Police persistence(Police police) {
        Police entity = policeRepository.save(police);
        return entity;
    }

    @Override
    public void delete(String id) {
        policeRepository.deleteById(id);
    }

    @Override
    public Police update(Police police) {
        Police entity = policeRepository.save(police);
        return entity;
    }

    @Override
    public Police query(String number) {
        return policeRepository.findByNumber(number);
    }
}
