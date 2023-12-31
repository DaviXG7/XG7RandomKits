package com.xg7network.xg7randomkits.Configs;

public enum ConfigType {

    CONFIG("config"),
    MESSAGES("messages"),
    DATA("data/data"),
    DROPS("dropsandkits/drops"),
    KITS("dropsandkits/kits");

    private String config;

    ConfigType(String config) {
        this.config = config;
    }

    public String getConfig() {
        return this.config + ".yml";
    }


}
