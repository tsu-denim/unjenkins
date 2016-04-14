package com.kodz.unjenkins.server.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodz.unjenkins.server.dto.gimpy.GimpyQuery;
import com.kodz.unjenkins.server.dto.gimpy.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;

/**
 * Created by Kurt on 4/14/16.
 */
public class QueryHelper {
    public static Logger logger = LoggerFactory.getLogger(QueryHelper.class);

    //init connection and call sql server stored procedure via jdbc

    // Declare the JDBC objects.
    static Connection con = null;

public static void init() {
    try {
        // Establish the connection.
        String url = "jdbc:jtds:sqlserver://unjenkins.qpit.test:1999/ReportsOptimized;instance=VIRTUALGIMPY";

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con = DriverManager.getConnection(url, "reader", "Test1234!");
            logger.info("DB Connection successful");
        } catch (Exception e) {
            logger.error("Cannot connect to database server");
            e.printStackTrace();
        }

    }

    // Handle any errors that may have occurred.
    catch (Exception e) {
        e.printStackTrace();
    }
}

    public static Report getReport(GimpyQuery gimpyQuery){
        CallableStatement cstmt = null;
        Report report = null;
        try {
            String json = new ObjectMapper().writeValueAsString(gimpyQuery);
            cstmt = con.prepareCall("{call Readiness.TestSummary_json(?, ?)}");
            cstmt.setString("TestCaseQueryVarChar", json);
            cstmt.registerOutParameter("QueryResult", Types.VARCHAR);
            cstmt.execute();
            report = new ObjectMapper().readValue(cstmt.getString("QueryResult"), Report.class);
            logger.info("DB Retrieval Successful!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return report;
    }

}

