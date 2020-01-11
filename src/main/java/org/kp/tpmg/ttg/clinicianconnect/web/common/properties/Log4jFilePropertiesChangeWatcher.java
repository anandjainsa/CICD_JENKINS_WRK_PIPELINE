package org.kp.tpmg.ttg.clinicianconnect.web.common.properties;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;




public class Log4jFilePropertiesChangeWatcher {
	
	private final class WatchEventForLog4jFile implements Runnable {
		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			WatchKey key ;
			key = Log4jFilePropertiesChangeWatcher.this.watchService.poll();
			while (key != null) {
				for (WatchEvent<?> event : key.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();

					WatchEvent<Path> watchEvent = (WatchEvent<Path>) event;
					Path filename =  watchEvent.context();
					if(kind.name() != null && "ENTRY_MODIFY".equalsIgnoreCase(kind.name())){
						reloadFile(filename);
					}
				}
				key.reset();
				key = Log4jFilePropertiesChangeWatcher.this.watchService.poll();
			}
		}

		private void reloadFile(Path filename) {
			if (filename.toString().endsWith(".properties")) {
				try {
					ReloadLog4jFileProperties.INSTANCE.init(log4jFilePath);
				} catch (Exception e) {
					logger.error("Error while refreshing Log4J properties"+ e.getMessage(), e);
				}
			}
		}
	}

	private static Logger logger = LogManager.getLogger(Log4jFilePropertiesChangeWatcher.class); 
	
	private final ScheduledExecutorService scheduledExecutorService;
	private final WatchService watchService = FileSystems.getDefault().newWatchService();
	private final String log4jFilePath ;
	
	public Log4jFilePropertiesChangeWatcher(String filePath) throws IOException {
		this.log4jFilePath = filePath;
		Path targetFilePath = Paths.get(log4jFilePath, new String[0]);
		if (targetFilePath.getParent() != null) {
			targetFilePath.getParent().register(this.watchService,	new WatchEvent.Kind[] { StandardWatchEventKinds.ENTRY_MODIFY });
		} else {
			logger.error("No parent found for path. The propertfy file will not be loaded. "+ targetFilePath);
		}

		this.scheduledExecutorService = Executors.newScheduledThreadPool(2,
				new ThreadFactory() {
					
					public Thread newThread(Runnable r) {
						Thread t = Executors.defaultThreadFactory()
								.newThread(r);
						t.setDaemon(true);
						return t;
					}
				});
		this.scheduledExecutorService.scheduleAtFixedRate(new WatchEventForLog4jFile(), 10L, 30L, TimeUnit.SECONDS);
	  }
	
	void close() throws IOException {
		logger.debug("Shutting down log4j monitor service for the log4j properties file");
		this.scheduledExecutorService.shutdownNow();
		this.watchService.close();
	}
}
