package com.kodz.unjenkins.server.endpoints.websocket.providers;

import com.kodz.unjenkins.server.dto.JobStatus;
import com.kodz.unjenkins.server.dto.Subscription;
import com.kodz.unjenkins.server.dto.UpdateNotification;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Kurt on 3/15/16.
 */
public class SubscriptionPools {

    static CopyOnWriteArrayList<JobStatus> safeJobPool = new CopyOnWriteArrayList<>();
    static CopyOnWriteArrayList<Subscription> safeSubscriptions = new CopyOnWriteArrayList<>();
    static Queue<UpdateNotification> messageQueue = new ConcurrentLinkedQueue<>();
    static Queue<JobStatus> jobUpdateQueue = new ConcurrentLinkedQueue<>();



}
