package bg.jug.academy.crawler.db;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Qualifier("filepersistencemanager")
@Component
public class FilePersistenceManager implements IPersistenceManager {

	@Override
	public void init() {
		System.out.println("file manager init ...");
	}

	@Override
	public void executeUpdate(String query) {
		System.out.println("file manager execute query ...");
	}

}
