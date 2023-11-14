package org.example.service;

import org.example.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component("file")
public class FilePersistenceManager implements PersistenceManager {
    public static final Logger LOGGER = Logger.getLogger(FilePersistenceManager.class.getName());

    @Autowired
    private FileUtils fileUtils;


    public void save(String message) {
        fileUtils.save(message);
        LOGGER.info(String.format("Saving message: %s to database", message));
    }

    public void setFileUtils(FileUtils fileUtils) {
        this.fileUtils = fileUtils;
    }
}
