package com.kodz.unjenkins.server.endpoints.http;

import com.kodz.unjenkins.server.dto.Metric;
import com.kodz.unjenkins.server.dto.gimpy.GimpyQuery;
import com.kodz.unjenkins.server.dto.gimpy.GimpyQueryResponse;
import com.kodz.unjenkins.server.dto.gimpy.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;

/**
 * Created by Kurt on 4/14/16.
 */
public class GimpyProvider {
    public static Logger logger = LoggerFactory.getLogger(GimpyProvider.class);


    public static GimpyQueryResponse postQuery(GimpyQuery report) {

    ReportPool.QueryQueue.offer(report);

        GimpyQueryResponse gimpyQueryResponse = new GimpyQueryResponse();
        gimpyQueryResponse.setRequestId(report.reportId);
        gimpyQueryResponse.setUrl("http://unjenkins.qpit.test:8080/api/tcdb/" + report.reportId);
        return gimpyQueryResponse;

    }

    public static Report getReport(String reportId){

        Report report = null;
        logger.info("Looking for report...");
        if (ReportPool.safeReports.containsKey(reportId)){
            report = ReportPool.safeReports.get(reportId);
        }
        else {
            logger.info("Report key {} not found...", reportId);
            throw new NotFoundException();
        }

        return report;
    }

}
