package me.kingof0.jda.listener.user;

import me.kingof0.jda.main.KingOfJda;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserSentMessageListener extends ListenerAdapter {

    private KingOfJda main;

    public UserSentMessageListener(KingOfJda main) {
        this.main = main;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        boolean cancelled = false;
        if (main.getConfiguration().isThisBot(event.getAuthor().getIdLong()))
            return;
        if (event.getAuthor().isBot())
            cancelled = true;

        if (main.callUserSentMessage(event.getAuthor(), event.getGuild(), event.getMessage(), cancelled).isCancelled())
            event.getMessage().delete().queue();
    }

}
