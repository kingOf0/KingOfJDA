package me.kingof0.jda.event.user;


import me.kingof0.jda.Cancellable;
import me.kingof0.jda.HandlerList;
import net.dv8tion.jda.api.entities.*;

public class UserReactionAddEvent extends UserEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancel;
    private final MessageReaction reaction;
    private final MessageReaction.ReactionEmote emoji;

    public UserReactionAddEvent(User user, Guild server, MessageReaction reaction, MessageReaction.ReactionEmote emoji) {
        super(user, server);
        this.reaction = reaction;
        this.emoji = emoji;
    }

    public MessageReaction getReaction() {
        return reaction;
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

    public MessageReaction.ReactionEmote getEmoji() {
        return emoji;
    }

}
