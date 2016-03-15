package com.kodz.unjenkins.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.kodz.unjenkins.server.endpoints.websocket.rooms.DebugRoom;
import com.kodz.unjenkins.server.endpoints.websocket.rooms.InfoRoom;

/**
 * Created by Kurt on 1/26/16.
 */
public class SocketAppender extends AppenderBase<ILoggingEvent> {

    public void append(ILoggingEvent event){
        if(event.getLevel().equals(Level.INFO)){
            InfoRoom.getInstance().writeAllMembers(event.getMessage());
        }
        else if (event.getLevel().equals(Level.DEBUG)){
            DebugRoom.getInstance().writeAllMembers(event.getMessage());
        }

    }
}
