package com.song.demo.Service;

import org.springframework.scheduling.annotation.Async;

public interface MyService {

    void sayHi(String name);

    @Async
    void introduce(String param);

}
