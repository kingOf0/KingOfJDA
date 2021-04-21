package me.kingof0.jda.listener.user;

import me.kingof0.jda.main.KingOfJda;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserRunCommandListener extends ListenerAdapter {

    private KingOfJda main;

    public UserRunCommandListener(KingOfJda main) {
        this.main = main;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        boolean cancelled = false;
        if (main.getConfiguration().isThisBot(event.getAuthor().getIdLong()))
            return;
        if (event.getAuthor().isBot())
            cancelled = true;
        String message = event.getMessage().getContentRaw();
        if (!message.startsWith(main.prefix))
            return;
        String[] args = message.substring(1).split(" ");

        if (main.callUserRunCommandEvent(event.getAuthor(), event.getGuild(), event.getMessage(), args, cancelled).isCancelled())
            event.getMessage().delete().queue();
    }
}
