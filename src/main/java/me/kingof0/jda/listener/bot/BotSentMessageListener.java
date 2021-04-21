package me.kingof0.jda.listener.bot;

import me.kingof0.jda.main.KingOfJda;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotSentMessageListener extends ListenerAdapter {

    private KingOfJda main;

    public BotSentMessageListener(KingOfJda main) {
        this.main = main;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        boolean cancelled = false;
        if (!event.getAuthor().isBot())
            return;


        if (main.callBotSentMessageEvent(event.getAuthor(), event.getGuild(), event.getMessage(), cancelled)
                .isCancelled())
            event.getMessage().delete().queue();
    }
}
