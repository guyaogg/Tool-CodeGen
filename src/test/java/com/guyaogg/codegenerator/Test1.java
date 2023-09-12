package com.guyaogg.codegenerator;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author guyao
 * @version 1.0
 * @description: TODO
 * @date 2023/9/11 15:48
 */
public class Test1 {

    @Test
    public void t1(){
        System.out.println(Arrays.toString("5\\6\\7".split("\\\\")));
    }
}
