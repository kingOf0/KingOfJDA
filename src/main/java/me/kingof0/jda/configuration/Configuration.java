package me.kingof0.jda.configuration;

import java.util.HashSet;
import java.util.Set;

public abstract class Configuration {

    protected Set<Long> admins = new HashSet<>();
    protected String prefix;
    protected long ownerID;
    protected long botID;

    public boolean isAdmin(long id) {
        return admins.contains(id);
    }

    public boolean isThisBot(long id) {
        return botID == id;
    }

    public String getPrefix() {
        return prefix;
    }

    public long getOwner() {
        return ownerID;
    }
}
