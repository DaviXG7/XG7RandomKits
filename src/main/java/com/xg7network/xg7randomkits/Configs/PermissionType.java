package com.xg7network.xg7randomkits.Configs;

public enum PermissionType {

    ADMIN("admin"),

    REGION("region.*"),
    REGION_SET("region.set"),
    REGION_SAVE("region.save"),
    REGION_CHECK("region.check"),
    REGION_DELETE("region.delete"),

    SET_SPAWN("setspawn");




    private String perm;

    PermissionType(String perm) {
        this.perm = perm;
    }

    public String getPerm() {
        return "xg7randomkits." + this.perm;
    }

}
