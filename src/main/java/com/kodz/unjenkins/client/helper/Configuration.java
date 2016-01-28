package com.kodz.unjenkins.client.helper;

import com.kodz.unjenkins.client.dto.ServerSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Kurt on 1/22/16.
 */
public class Configuration {
    public static Logger logger = LoggerFactory.getLogger(Configuration.class);
    static {

        URL resource = Configuration.class.getClassLoader().getResource("settings.yaml");
        Constructor configurationConstructor = new Constructor(Configuration.class);
        TypeDescription serverSettingDescription = new TypeDescription(ServerSetting.class);
        serverSettingDescription.putMapPropertyType("serverSettings", ServerSetting.class, Object.class);
        configurationConstructor.addTypeDescription(serverSettingDescription);


        final Yaml yaml = new Yaml(configurationConstructor);
        try {
            final Configuration configuration = (Configuration) yaml.load(resource.openStream());
            Configuration.Setting = configuration.getServerSettings();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ServerSetting Setting;

    private ServerSetting serverSettings;

    public  ServerSetting getServerSettings() {
        return serverSettings;
    }

    public void setServerSettings(ServerSetting serverSettings) {
        this.serverSettings = serverSettings;
    }

    public static void logSettings(){
        logger.info("Heartbeat Interval: " + Configuration.Setting.getHeartbeatInterval());
        logger.info("Server Port: " + Configuration.Setting.getServicePort());

    }

}
