package org.example.service;

import com.somelibrary.core.SomeLibraryClass;
import org.example.util.DatabaseUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.logging.Logger;

@Component("db")
@Lazy
public class DatabasePersistenceManager implements PersistenceManager, InitializingBean, DisposableBean {
    private static final Logger LOGGER = Logger.getLogger(DatabasePersistenceManager.class.getName());

    @Autowired
    private DatabaseUtils dbUtils;

    @Autowired(required = true)
    @Qualifier("createSomeLibraryClassBean2")
    private SomeLibraryClass instance;

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

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @PostConstruct
    public void postConstruct(){

    }

    @PreDestroy
    public void preDestroy(){

    }
}
