package org.example.service;

import org.example.util.DatabaseUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

public class WebServicePersistenceManager implements PersistenceManager{
    private static final Logger LOGGER = Logger.getLogger(WebServicePersistenceManager.class.getName());

    @Override
    public void save(String message) {
        LOGGER.info(String.format("Sending message: %s to web service", message));
    }
}
