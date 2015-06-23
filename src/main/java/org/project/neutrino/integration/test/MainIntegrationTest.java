/* Tiziano Cecamore - 2015*/

package org.project.neutrino.integration.test;

import org.project.neutrino.nfvo.main.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainIntegrationTest {
	
	private static Logger log = LoggerFactory
			.getLogger(MainIntegrationTest.class);

	static ConfigurableApplicationContext context;

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException, FileNotFoundException, IOException,
			URISyntaxException, InterruptedException, ExecutionException {
		testConfiguration();
	}

	public static boolean available(String host, int port) {
		try {
			Socket s = new Socket(host, port);
			log.warn("Server is listening on port " + port + " of "
					+ host);
			s.close();
			return true;
		} catch (IOException ex) {
			// The remote host is not listening on this port
			log.error("Server is not listening on port " + port
					+ " of " + host);
			return false;
		}
	}

	public static void testConfiguration() throws ClassNotFoundException,
			SQLException, FileNotFoundException, IOException,
			URISyntaxException, InterruptedException, ExecutionException {

		Runnable r = new Runnable() {
			public void run() {
				log.info("################################# TEST CONFIGURATION #############################################");
				context = SpringApplication.run(Application.class);
			}
		};

		new Thread(r).start();

		while (!available("127.0.0.1", 8080)) {
			log.debug("waiting the server to start");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		log.info("STARTED!!!");
		//log.info("Working Directory = " +
		//log.info("user.dir"));
		
        
		ExecutorService threadpool = Executors.newFixedThreadPool(1);

		//Configuration task = new Configuration();
		NetworkServiceRecord task = new NetworkServiceRecord();
		
		log.debug("Submitting Task ...");

		@SuppressWarnings({ "unchecked", "rawtypes" })
		Future future = threadpool.submit(task);
		log.debug("Task is submitted");

		while (!future.isDone()) {
			log.debug("Task is not completed yet....");
			Thread.sleep(1); // sleep for 1 millisecond before checking again
		}

		log.info("Task is completed, let's check result");
		Boolean result = (Boolean) future.get();
		if (result.TRUE) {
			log.info("TRUE");
		} else {
			log.info("FALSE");
		}

		threadpool.shutdown();
         
		

	}

}
