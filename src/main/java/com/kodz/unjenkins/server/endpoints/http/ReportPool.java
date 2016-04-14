package com.kodz.unjenkins.server.endpoints.http;


import com.kodz.unjenkins.server.dto.gimpy.GimpyQuery;
import com.kodz.unjenkins.server.dto.gimpy.Report;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Kurt on 4/14/16.
 */
public class ReportPool {
    public static Queue<GimpyQuery> QueryQueue = new ConcurrentLinkedQueue<>();
    public static Map<String, Report> safeReports = new ConcurrentHashMap<>();
}
