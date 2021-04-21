package me.kingof0.jda.listener.user;

import me.kingof0.jda.main.KingOfJda;
import me.kingof0.jda.validate.Validate;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserReactionAddListener extends ListenerAdapter {

    private KingOfJda main;

    public UserReactionAddListener(KingOfJda main) {
        this.main = main;
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        boolean cancelled = false;
        if (main.getConfiguration().isThisBot(event.getUserIdLong()))
            return;
        Validate.notNull(event.getUser(), "User cant be null");
        if (event.getUser().isBot())
            cancelled = true;

        if (main.callMentionAddEvent(event.getUser(), event.getGuild(), event.getReaction(), event.getReactionEmote(), cancelled).isCancelled())
            event.getReaction().removeReaction().queue();
    }
}
