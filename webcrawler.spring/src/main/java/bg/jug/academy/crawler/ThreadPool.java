package bg.jug.academy.crawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

	private static ThreadPool instance = new ThreadPool();

	private ExecutorService pool;
	
	private ThreadPool() {
		pool = Executors.newFixedThreadPool(100);
	}
	
	public static ThreadPool getInstance() {
		return instance;
	}
	
	public void execute(Runnable task) {
		pool.execute(task);
	}
	
	public void shutdown() throws InterruptedException {
		pool.awaitTermination(20, TimeUnit.SECONDS);
		pool.shutdown();
	}
	
}
