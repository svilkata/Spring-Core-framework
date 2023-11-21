package org.example.util;

import com.somelibrary.core.MessageSavedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component("fileUtils")
public class FileUtils implements ApplicationListener<MessageSavedEvent> {
    public static final Logger LOGGER = Logger.getLogger(FileUtils.class.getName());

    public void save(String message) {

    }

    @Override
    public void onApplicationEvent(MessageSavedEvent event) {
        LOGGER.info("Message event received in file utils bean....");
    }
}
