package com.distkv.dst.server;

import com.google.common.base.Strings;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

public class DstServerConfig {
  private static final Logger LOGGER = LoggerFactory.getLogger(DstServerConfig.class);

  public static final String CUSTOM_CONFIG_FILE = "dst.conf";
  public static final String DEFAULT_CONFIG_FILE = "dst.default.conf";

  private int listeningPort;
  private boolean isMaster;
  private int shardNum;

  public boolean isMaster() {
    return isMaster;
  }

  public int getPort() {
    return listeningPort;
  }

  public void setPort(int port) {
    listeningPort = port;
  }

  public int getShardNum() {
    return shardNum;
  }

  public DstServerConfig(Config config) {
    listeningPort = config.getInt("store.listeningPort");
    isMaster = config.getBoolean("store.isMaster");
    shardNum = config.getInt("store.shardNum");
  }

  @Override
  public String toString() {
    return "listeningPort: " + listeningPort + ";\n"
        + "isMaster: " + isMaster + ";\n"
        + "shardNum: " + shardNum + ";\n";
  }

  public static DstServerConfig create() {
    ConfigFactory.invalidateCaches();
    Config config = ConfigFactory.systemProperties();
    String configPath = System.getProperty("dst.config");
    if (Strings.isNullOrEmpty(configPath)) {
      LOGGER.info("Loading config from \"dst.conf\" file in classpath.");
      config = config.withFallback(ConfigFactory.load(CUSTOM_CONFIG_FILE));
    } else {
      LOGGER.info("Loading config from " + configPath + ".");
      config = config.withFallback(ConfigFactory.parseFile(new File(configPath)));
    }
    config = config.withFallback(ConfigFactory.load(DEFAULT_CONFIG_FILE));
    return new DstServerConfig(config);
  }
}


