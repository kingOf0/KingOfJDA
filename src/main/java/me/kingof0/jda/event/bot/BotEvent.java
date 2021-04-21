package me.kingof0.jda.event.bot;

import me.kingof0.jda.Event;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

public abstract class BotEvent extends Event {

    private Guild server;
    private User bot;

    BotEvent(User user, Guild server) {
        this.bot = user;
        this.server = server;
    }

    public Guild getServer() {
        return server;
    }

    public User getBot() {
        return this.bot;
    }

    public long getBotId() {
        return this.bot.getIdLong();
    }

}
