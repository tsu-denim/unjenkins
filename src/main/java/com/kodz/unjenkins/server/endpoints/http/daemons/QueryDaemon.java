package com.kodz.unjenkins.server.endpoints.http.daemons;

import com.kodz.unjenkins.logging.Loggable;
import com.kodz.unjenkins.server.database.QueryHelper;
import com.kodz.unjenkins.server.dto.gimpy.GimpyQuery;
import com.kodz.unjenkins.server.dto.gimpy.Report;
import com.kodz.unjenkins.server.endpoints.http.ReportPool;
import com.kodz.unjenkins.server.endpoints.websocket.providers.SubscriptionProvider;

import java.util.TimerTask;

/**
 * Created by Kurt on 4/14/16.
 */
public class QueryDaemon extends TimerTask implements Loggable {

    public void run(){
        while (!(ReportPool.QueryQueue.isEmpty())){
            logger().info("Calling db...");
            GimpyQuery gimpyQuery = ReportPool.QueryQueue.poll();
            Report report = QueryHelper.getReport(gimpyQuery);
            ReportPool.safeReports.put(gimpyQuery.reportId, report);
            logger().info("Report added to pool...");
        }

    }



}