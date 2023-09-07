package com.lmluat.league.utils;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.ejb.Singleton;

@Singleton
public class ApplicationLogger {
    private  final Logger logger;

    public ApplicationLogger() {
        this.logger = LogManager.getLogger(ApplicationLogger.class);
    }

    public void logInfo(String message){
        logger.info(message);
    }

    public void logError(String message){
        logger.error(message);
    }
}
