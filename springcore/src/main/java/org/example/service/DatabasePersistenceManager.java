package org.example.service;

import org.example.util.DatabaseUtils;

import java.util.logging.Logger;

public class DatabasePersistenceManager implements PersistenceManager {
    private static final Logger LOGGER = Logger.getLogger(DatabasePersistenceManager.class.getName());
    private DatabaseUtils dbUtils;

    public void save(String message) {
        dbUtils.persist(message);
        LOGGER.info(String.format("Saving message: %s to database", message));
    }

    public void setDbUtils(DatabaseUtils dbUtils) {
        this.dbUtils = dbUtils;
    }
}
