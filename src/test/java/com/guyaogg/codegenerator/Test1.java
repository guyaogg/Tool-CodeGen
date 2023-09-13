package com.guyaogg.codegenerator;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author guyao
 * @version 1.0
 * @description: TODO
 * @date 2023/9/11 15:48
 */
public class Test1 {

    @Test
    public void t1(){
        System.out.println(LocalDateTime.now().plusHours(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
    }
}
