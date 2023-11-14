package org.example;


import org.example.service.DatabasePersistenceManager;
import org.example.service.FilePersistenceManager;
import org.example.service.PersistenceManager;
import org.example.util.DatabaseUtils;
import org.example.util.FileUtils;

public class Application {
    public static void main(String[] args) {
//        createApplication(args);

        createApplicationUsingXmlContext(args);
    }

    private static void createApplicationUsingXmlContext(String[] args) {

    }

    private static void createApplication(String[] args) {
        // db, file
        String location = args[0];
        String message = args[1];

        PersistenceManager persistenceManager = null;
        switch (location){
            case "db": {
                persistenceManager = new DatabasePersistenceManager();
                ((DatabasePersistenceManager) persistenceManager).setDbUtils(new DatabaseUtils());
                break;
            }
            case "file": {
                persistenceManager = new FilePersistenceManager();
                ((FilePersistenceManager) persistenceManager).setFileUtils(new FileUtils());
                break;
            }
        }

        persistenceManager.save(message);
    }
}
