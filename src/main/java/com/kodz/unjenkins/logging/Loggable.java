package com.kodz.unjenkins.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Kurt on 3/8/16.
 */
public interface Loggable {
    default Logger logger(){
        return LoggerFactory.getLogger(this.getClass());
    }
}
