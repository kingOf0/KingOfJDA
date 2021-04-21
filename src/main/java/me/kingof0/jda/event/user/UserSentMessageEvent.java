package me.kingof0.jda.event.user;


import me.kingof0.jda.Cancellable;
import me.kingof0.jda.HandlerList;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class UserSentMessageEvent extends UserEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancel;
    private final Message message;

    public UserSentMessageEvent(User user, Guild server, Message message) {
        super(user, server);
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled() {
        return this.cancel;
    }

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

}
