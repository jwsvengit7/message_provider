package com.example.authmodule.web.config.annotation.repo;

import com.example.authmodule.web.config.annotation.InvokeDomain;

import java.util.*;

public class Repository<T> {
    public void returnIvokeTime(T t){
        var classTime = t.getClass();
        var methods = classTime.getDeclaredMethods();
        for(var method : methods){
            var annotation  = method.getAnnotationsByType(InvokeDomain.class);
            List<Integer> invokeDomain= Arrays.stream(annotation).map((value)->{
                System.out.println(value);
                return annotation.length;
            }).toList();

         }

    }
}
