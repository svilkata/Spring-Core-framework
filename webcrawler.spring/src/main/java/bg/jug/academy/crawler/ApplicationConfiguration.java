package bg.jug.academy.crawler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import bg.jug.academy.crawler.db.PersistenceManager;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {
	
	@Qualifier("dbpersistencemanager")
	@Bean
//	@DependsOn("filePersistenceManager")
//	@Scope("prototype")
//	@Lazy
	public PersistenceManager createPersistenceManager() {
		return new PersistenceManager();
	}
	
}
