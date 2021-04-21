package me.kingof0.jda.event.admin;


import me.kingof0.jda.Cancellable;
import me.kingof0.jda.HandlerList;
import me.kingof0.jda.event.user.UserEvent;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class AdminRunCommandEvent extends UserEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancel;
    private final Message message;
    private final String[] command;

    public AdminRunCommandEvent(User user, Guild server, Message message, String[] command) {
        super(user, server);
        this.message = message;
        this.command = command;
    }



    public Message getMessage() {
        return message;
    }

    public String[] getCommand() {
        return command;
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
