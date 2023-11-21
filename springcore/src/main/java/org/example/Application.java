package org.example;


import org.example.service.DatabasePersistenceManager;
import org.example.service.FilePersistenceManager;
import org.example.service.PersistenceManager;
import org.example.service.WebServicePersistenceManager;
import org.example.util.DatabaseUtils;
import org.example.util.FileUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
    public static void main(String[] args) {
//        createApplication(args);
//        createApplicationUsingXmlContext(args);
        createApplicationUsingAnnotationContext(args);
    }

    private static void createApplicationUsingAnnotationContext(String[] args) {
        String location = args[0]; // db, file
        String message = args[1];

//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.example");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class.getPackage().getName());
        PersistenceManager manager = context.getBean(location, PersistenceManager.class);
        PersistenceManager manager2 = context.getBean(location, PersistenceManager.class);
        manager.save(message);

        context.getBean(WebServicePersistenceManager.class);

        BeanDefinition definition = context.getBeanFactory().getBeanDefinition(location);
        System.out.println(definition);
        System.out.println(System.identityHashCode(manager)); //връща id-то на обекта от Java HEAP паметта
        System.out.println(System.identityHashCode(manager2));

        manager.save(message);

//        Generic bean: class [org.example.service.DatabasePersistenceManager]; scope=singleton; abstract=false; lazyInit=null; autowireMode=0; dependencyCheck=0;
//        autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodNames=null; destroyMethodNames=null;
//        defined in file [/media/svilen/404677E04677D55E/SVILEN/bgjug/Spring Core framework/springcore/target/classes/org/example/service/DatabasePersistenceManager.class]

        context.close();
    }

    private static void createApplicationUsingXmlContext(String[] args) {
        String location = args[0]; // db, file
        String message = args[1];

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        PersistenceManager manager = context.getBean(location, PersistenceManager.class);
        manager.save(message);
        context.close();
    }

    private static void createApplication(String[] args) {
        String location = args[0]; // db, file
        String message = args[1];

        PersistenceManager persistenceManager = null;
        switch (location) {
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
