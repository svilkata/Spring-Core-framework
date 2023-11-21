package bg.jug.academy.crawler.db;

import java.util.Properties;

import org.springframework.stereotype.Component;

public interface IPersistenceManager {

	void init();
	
	void executeUpdate(String query);
	
}
