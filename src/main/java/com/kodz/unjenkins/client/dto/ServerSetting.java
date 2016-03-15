package com.kodz.unjenkins.client.dto;

/**
 * Created by Kurt on 1/22/16.
 */
public class ServerSetting {
    private int servicePort;
    private int heartbeatInterval;
    private int daemonInterval;
    private String remoteJenkinsHostDomain;
    private String remoteJenkinsHostPort;
    private String remoteHostDomainForHealthCheck;
    private int remoteHostPortForHealthCheck;
    private String sslKeyRepositoryPath;
    private String sslKeyPassword;
    private String jenkinsUserName;
    private String jenkinsApiToken;

    public int getDaemonInterval() {
        return daemonInterval;
    }

    public void setDaemonInterval(int daemonInterval) {
        this.daemonInterval = daemonInterval;
    }

    public int getServicePort() {
        return servicePort;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    public int getHeartbeatInterval() {
        return heartbeatInterval;
    }

    public void setHeartbeatInterval(int heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    public int getRemoteHostPortForHealthCheck() {
        return remoteHostPortForHealthCheck;
    }

    public void setRemoteHostDomainForHealthCheck(String remoteHostDomainForHealthCheck) {
        this.remoteHostDomainForHealthCheck = remoteHostDomainForHealthCheck;
    }

    public String getRemoteHostDomainForHealthCheck() {
        return remoteHostDomainForHealthCheck;
    }

    public void setRemoteHostPortForHealthCheck(int remoteHostPortForHealthCheck) {
        this.remoteHostPortForHealthCheck = remoteHostPortForHealthCheck;
    }

    public String getSslKeyPassword() {
        return sslKeyPassword;
    }

    public void setSslKeyPassword(String sslKeyPassword) {
        this.sslKeyPassword = sslKeyPassword;
    }

    public String getSslKeyRepositoryPath() {
        return sslKeyRepositoryPath;
    }

    public void setSslKeyRepositoryPath(String sslKeyRepositoryPath) {
        this.sslKeyRepositoryPath = sslKeyRepositoryPath;
    }

    public String getRemoteJenkinsHostDomain() {
        return remoteJenkinsHostDomain;
    }

    public void setRemoteJenkinsHostDomain(String remoteJenkinsHostDomain) {
        this.remoteJenkinsHostDomain = remoteJenkinsHostDomain;
    }

    public String getRemoteJenkinsHostPort() {
        return remoteJenkinsHostPort;
    }

    public void setRemoteJenkinsHostPort(String remoteJenkinsHostPort) {
        this.remoteJenkinsHostPort = remoteJenkinsHostPort;
    }

    public String getJenkinsApiToken() {
        return jenkinsApiToken;
    }

    public void setJenkinsApiToken(String jenkinsApiToken) {
        this.jenkinsApiToken = jenkinsApiToken;
    }

    public String getJenkinsUserName() {
        return jenkinsUserName;
    }

    public void setJenkinsUserName(String jenkinsUserName) {
        this.jenkinsUserName = jenkinsUserName;
    }

}
