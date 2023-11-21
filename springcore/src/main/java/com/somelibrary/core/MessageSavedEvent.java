package com.somelibrary.core;

import org.springframework.context.ApplicationEvent;

public class MessageSavedEvent extends ApplicationEvent {
    public MessageSavedEvent(Object source) {
        super(source);
    }
}
