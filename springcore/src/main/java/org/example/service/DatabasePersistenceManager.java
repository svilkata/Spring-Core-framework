package org.example.service;

import org.example.util.DatabaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component("db")
public class DatabasePersistenceManager implements PersistenceManager {
    private static final Logger LOGGER = Logger.getLogger(DatabasePersistenceManager.class.getName());

    @Autowired
    private DatabaseUtils dbUtils;

//    public DatabasePersistenceManager(@Autowired DatabaseUtils dbUtils){
//        this.dbUtils = dbUtils;
//    }

//    @Autowired
    public void setDbUtils(DatabaseUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    public void save(String message) {
        dbUtils.persist(message);
        LOGGER.info(String.format("Saving message: %s to database", message));
    }
}
