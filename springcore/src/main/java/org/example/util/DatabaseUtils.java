package org.example.util;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component("dbUtils")
//@DependsOn({"fileUtils", })
public class DatabaseUtils {

    public void persist(String message) {

    }
}
