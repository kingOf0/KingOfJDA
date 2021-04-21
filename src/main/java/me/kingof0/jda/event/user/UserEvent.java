package me.kingof0.jda.event.user;

import me.kingof0.jda.Event;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

public abstract class UserEvent extends Event {

    private Guild server;
    private User user;

    public UserEvent(User user, Guild server) {
        this.user = user;
        this.server = server;
    }

    public Guild getServer() {
        return server;
    }

    public User getUser() {
        return this.user;
    }

    public long getUserId() {
        return this.user.getIdLong();
    }

}
