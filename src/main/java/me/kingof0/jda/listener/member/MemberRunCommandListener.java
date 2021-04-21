package me.kingof0.jda.listener.member;

import me.kingof0.jda.main.KingOfJda;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemberRunCommandListener extends ListenerAdapter {

    private KingOfJda main;

    public MemberRunCommandListener(KingOfJda main) {
        this.main = main;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        boolean cancelled = false;
        if (main.getConfiguration().isThisBot(event.getAuthor().getIdLong()))
            return;
        if (event.getAuthor().isBot())
            cancelled = true;

        long userId = event.getAuthor().getIdLong();
        if (main.getConfiguration().isAdmin(userId))
            return;
        String message = event.getMessage().getContentRaw();
        if (!message.startsWith(main.prefix))
            return;
        String[] args = message.substring(1).split(" ");


        if (main.callMemberRunCommandEvent(event.getAuthor(), event.getGuild(), event.getMessage(), args, cancelled)
                .isCancelled())
            event.getMessage().delete().queue();
    }
}
