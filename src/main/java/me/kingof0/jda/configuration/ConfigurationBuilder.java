package me.kingof0.jda.configuration;

import java.util.HashSet;
import java.util.Set;

public class ConfigurationBuilder {

    private String prefix;
    private long ownerID;
    private long botID;
    private Set<Long> admins = new HashSet<>();

    public Configuration build() {
        return new Configuration(admins, prefix, ownerID, botID) {};
    }

    public ConfigurationBuilder setAdmins(Set<Long> admins) {
        this.admins = admins;
        return this;
    }

    public ConfigurationBuilder setBotID(long botID) {
        this.botID = botID;
        return this;
    }

    public ConfigurationBuilder setOwnerID(long ownerID) {
        this.ownerID = ownerID;
        return this;
    }

    public ConfigurationBuilder setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public ConfigurationBuilder addAdmin(long id) {
        admins.add(id);
        return this;
    }
}