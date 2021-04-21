import me.kingof0.jda.configuration.Configuration;
import me.kingof0.jda.configuration.ConfigurationBuilder;
import me.kingof0.jda.main.KingOfJda;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) {
        JDA jda = null;
        try {
            jda = new JDABuilder().build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        new KingOfJda(jda, new ConfigurationBuilder()
                .setPrefix(".")
                .build());
    }

}
