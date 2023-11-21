package bg.jug.academy.crawler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import bg.jug.academy.crawler.db.IPersistenceManager;
import bg.jug.academy.crawler.db.PersistenceManager;
import bg.jug.academy.crawler.entities.Website;

@Component
public class Application {
	
	private static final String DELIMITER_WEBSITE = ",";

	private static final String COMMAND_FILE = "file";

	private static final String COMMAND_SITES = "sites";

	private static Logger LOGGER = Logger.getLogger(Application.class.getName());

	private Map<String, Command> commands = new HashMap<>();
	
	private static AnnotationConfigApplicationContext context;

	@Qualifier("dbpersistencemanager")
	@Autowired
	private IPersistenceManager manager;
	
	public static void main(String[] args) {

		initContext();
		
		Application app = context.getBean(Application.class);
		
		// validating arguments
		final int numArguments = 2;
		app.validateArguments(args, numArguments);

		Runtime.getRuntime().addShutdownHook(new Thread(
				() -> { app.closeContext(); })  );
		
		app.initCommands();

		String command = args[0]; //sites https://abv.bg
		String params = args[1];

		app.executeCommand(command, params);
	}

	private void closeContext() {
		context.close();
	}

	private void executeCommand(String command, String params) {
		Command commandMethod = commands.get(command);
		if (commandMethod == null) {
			LOGGER.severe("Invalid command: " + command);
		} else {
			commandMethod.execute(params);
		}
	}

	private static void initContext() {
		context = new AnnotationConfigApplicationContext(
				Application.class.getPackage().getName());
		context.start();
	}
	
	private void initCommands() {
		LOGGER.info("Initializing commands ...");
		commands.put(COMMAND_SITES, this::handleCommandSites);
		commands.put(COMMAND_FILE, this::handleCommandFile);
	}

	private void validateArguments(String[] args, final int numArguments) {
		if (args.length != numArguments) {
			LOGGER.severe(String.format("Invalid number of arguments: %d, expected %d", args.length, numArguments));
			System.exit(1);
		}
	}

	public void handleCommandSites(String arguments) {

		for (String website : arguments.split(DELIMITER_WEBSITE)) {

			ThreadPool.getInstance().execute(() -> {
				Website websiteEntity = readWebsite(website);
				if (website != null) {
					parseLinks(websiteEntity);
					storeWebsite(websiteEntity);
				}
			});

		}
		
		try {
			ThreadPool.getInstance().shutdown();
		} catch (InterruptedException e) {
		}

	}

	private void storeWebsite(Website websiteEntity) {

		try {
			String insertQuery = PersistenceManager.generateInsertQuery(websiteEntity);
			manager.executeUpdate(insertQuery);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}

	}

	private void parseLinks(Website websiteEntity) {

		String html = websiteEntity.getHtml();

		Pattern pattern = Pattern.compile("(?:\")(http(s)?[^\"]+)(?:\")");
		Matcher matcher = pattern.matcher(html);
		while (matcher.find()) {
			String link = matcher.group(1);
			LOGGER.info(String.format("[%s] %s", websiteEntity.getUrl(), link));
		}

	}

	private Website readWebsite(String website) {
		BufferedReader reader = null;
		try {
			URL url = new URL(website);
			URLConnection connection = url.openConnection();
			reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(connection.getInputStream())));

			char[] buffer = new char[256];
			StringBuilder html = new StringBuilder();
			while (reader.read(buffer) != -1) {
				html.append(buffer);
			}

			Website websiteEntity = new Website();
			websiteEntity.setUrl(website);
			websiteEntity.setHtml(html.toString());
			return websiteEntity;
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					LOGGER.log(Level.WARNING, e.getMessage(), e);
				}
			}
		}
		return null;
	}

	public void handleCommandFile(String arguments) {
		// ...
	}

}
