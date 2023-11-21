package bg.jug.academy.crawler.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

public class PersistenceManager implements IPersistenceManager
	/* ,InitializingBean, DisposableBean */ {
	
	private static final Logger LOGGER = Logger.getLogger(PersistenceManager.class.getName());
	
	@Value("${db.url}")
	private String dbUrl;
	
	@Value("${db.user}")
	private String dbUser;
	
	@Value("${db.pass}")
	private String dbPass;
		
	@Autowired
	private Environment environment;
	
	@PostConstruct
	public void init() {
		LOGGER.info("Initializing database ...");
		System.out.println(environment.getProperty("db.url"));
	}
	
	@PreDestroy
	public void destroy() {
		LOGGER.info("Closing connection to database ...");
	}
	
	public void executeUpdate(String query) {
		try(Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass)) {
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		} catch(SQLException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}
	
	public static String generateInsertQuery(Object entity) throws IllegalArgumentException, IllegalAccessException {
		
		String table = entity.getClass().getSimpleName().toLowerCase();
		
		StringBuilder query = new StringBuilder("insert into ");
		query.append(table).append("(");

		Field[] fields = entity.getClass().getDeclaredFields();
		for(int index = 0; index < fields.length; index++) {
			Field field = fields[index];
			field.setAccessible(true);
			if(!"id".equals(field.getName())) {
				query.append(field.getName());
				if(index != fields.length - 1) {
					query.append(",");
				}
			}
		}
		query.append(") values (");
		
		for(int index = 0; index < fields.length; index++) {
			Field field = fields[index];
			if(!"id".equals(field.getName())) {
				if(field.get(entity) == null) {
					query.append("NULL");
				} else {
					if("html".equals(field.getName())) {
						
						String value = Base64.getEncoder().encodeToString(field.get(entity).toString().getBytes());
						query.append("'"). append(value).append("'");
					} else {
						query.append("'"). append(field.get(entity).toString()).append("'");
					}
				}
				if(index != fields.length - 1) {
					query.append(",");
				}
			}
		}
		query.append(")");
		return query.toString();
	}
	
}
