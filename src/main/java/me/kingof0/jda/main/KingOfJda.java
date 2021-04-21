package me.kingof0.jda.main;

import me.kingof0.jda.*;
import me.kingof0.jda.configuration.Configuration;
import me.kingof0.jda.event.admin.AdminRunCommandEvent;
import me.kingof0.jda.event.bot.BotSentMessageEvent;
import me.kingof0.jda.event.bot.JdaBotSentMessageEvent;
import me.kingof0.jda.event.member.MemberRunCommandEvent;
import me.kingof0.jda.event.user.UserReactionAddEvent;
import me.kingof0.jda.event.user.UserRunCommandEvent;
import me.kingof0.jda.event.user.UserSentMessageEvent;
import me.kingof0.jda.listener.admin.AdminRunCommandListener;
import me.kingof0.jda.listener.bot.BotSentMessageListener;
import me.kingof0.jda.listener.bot.JdaBotSentMessageListener;
import me.kingof0.jda.listener.member.MemberRunCommandListener;
import me.kingof0.jda.listener.user.UserReactionAddListener;
import me.kingof0.jda.listener.user.UserRunCommandListener;
import me.kingof0.jda.listener.user.UserSentMessageListener;
import me.kingof0.jda.validate.Validate;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class KingOfJda {

    public String prefix;
    private String name = "KingOfJDA";
    private boolean enabled;

    private static KingOfJda instance;
    private JDA jda;
    public Configuration configuration;

    public KingOfJda(JDA api, Configuration configuration) {
        this.configuration = configuration;
        this.jda = api;

        if (instance != null) {
            enabled = false;
            System.out.println("Already created instance! use KingOfJda.getInstance()");
            return;
        }

        instance = this;
        enabled = true;
        this.prefix = configuration.getPrefix();

        api.addEventListener(new AdminRunCommandListener(this));

        api.addEventListener(new BotSentMessageListener(this));
        api.addEventListener(new JdaBotSentMessageListener(this));

        api.addEventListener(new MemberRunCommandListener(this));

        api.addEventListener(new UserRunCommandListener(this));
        api.addEventListener(new UserReactionAddListener(this));
        api.addEventListener(new UserSentMessageListener(this));


    }

    public static KingOfJda getInstance() {
        return instance;
    }

    public void disable() {
        enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public AdminRunCommandEvent callAdminRunCommandEvent(User user, Guild server, Message message, String[] command, boolean cancelled) {
        AdminRunCommandEvent event = new AdminRunCommandEvent(user, server, message, command);
        event.setCancelled(cancelled);
        fireEvent(event);
        return event;
    }

    public UserRunCommandEvent callUserRunCommandEvent(User user, Guild server, Message message, String[] command, boolean cancelled) {
        UserRunCommandEvent event = new UserRunCommandEvent(user, server, message, command);
        event.setCancelled(cancelled);
        fireEvent(event);
        return event;
    }

    public MemberRunCommandEvent callMemberRunCommandEvent(User user, Guild server, Message message, String[] command, boolean cancelled) {
        MemberRunCommandEvent event = new MemberRunCommandEvent(user, server, message, command);
        event.setCancelled(cancelled);
        fireEvent(event);
        return event;
    }

    public BotSentMessageEvent callBotSentMessageEvent(User user, Guild server, Message message, boolean cancelled) {
        BotSentMessageEvent event = new BotSentMessageEvent(user, server, message);
        event.setCancelled(cancelled);
        fireEvent(event);
        return event;
    }

    public JdaBotSentMessageEvent callJavacordBotSentMessageEvent(User user, Guild server, Message message, boolean cancelled) {
        JdaBotSentMessageEvent event = new JdaBotSentMessageEvent(user, server, message);
        event.setCancelled(cancelled);
        fireEvent(event);
        return event;
    }

    public UserReactionAddEvent callMentionAddEvent(User user, Guild server, MessageReaction reaction, MessageReaction.ReactionEmote emote, boolean cancelled) {
        UserReactionAddEvent event = new UserReactionAddEvent(user, server, reaction, emote);
        event.setCancelled(cancelled);
        fireEvent(event);
        return event;
    }

    public UserSentMessageEvent callUserSentMessage(User user, Guild server, Message message, boolean cancelled) {
        UserSentMessageEvent event = new UserSentMessageEvent(user, server, message);
        event.setCancelled(cancelled);
        fireEvent(event);
        return event;
    }

    private void fireEvent(Event event) {
        HandlerList handlers = event.getHandlers();
        RegisteredListener[] listeners = handlers.getRegisteredListeners();

        Arrays.stream(listeners).filter(registration -> isEnabled()).forEach(registration -> {
            try {
                registration.callEvent(event);
            } catch (Throwable e) {
                logError("Could not pass event " + event.getEventName() + " to " + name + " d: " + registration.getListener(), e);
            }
        });
    }

    public static void logError(String message, Throwable e) {
        System.out.println(message);
        System.out.println(" e: message " + e.getMessage());
        System.out.println(" e: st " + Arrays.toString(e.getStackTrace()));

    }

    //todo: check deobfuscated code
    public void registerEvents(Listener listener) {
        if (!isEnabled()) {
            System.out.println("Plugin attempted to register " + listener + " while not enabled");
        } else {

            for (Map.Entry<Class<? extends Event>, Set<RegisteredListener>> classSetEntry : createRegisteredListeners(listener, jda).entrySet()) {
                this.getEventListeners(this.getRegistrationClass((Class) classSetEntry.getKey())).registerAll(classSetEntry.getValue());
            }

        }
    }

    private HandlerList getEventListeners(Class<? extends Event> type) {
        try {
            Method method = this.getRegistrationClass(type).getDeclaredMethod("getHandlerList");
            method.setAccessible(true);
            return (HandlerList)method.invoke(null);
        } catch (Exception var3) {
            throw new IllegalStateException(var3.getMessage());
        }
    }

    private Class<? extends Event> getRegistrationClass(Class<? extends Event> clazz) {
        try {
            clazz.getDeclaredMethod("getHandlerList");
            return clazz;
        } catch (NoSuchMethodException var2) {
            if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Event.class) && Event.class.isAssignableFrom(clazz.getSuperclass())) {
                return this.getRegistrationClass(clazz.getSuperclass().asSubclass(Event.class));
            } else {
                throw new IllegalStateException("Unable to find handler list for event " + clazz.getName() + ". Static getHandlerList method required!");
            }
        }
    }

    //todo: check deobfuscated code
    private Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(Listener listener, final JDA api) {
        Validate.notNull(api, "Discordapi can not be null");
        Validate.notNull(listener, "Listener can not be null");

        Map<Class<? extends Event>, Set<RegisteredListener>> ret = new HashMap<Class<? extends Event>, Set<RegisteredListener>>();
        Set<Method> methods;
        try {
            Method[] publicMethods = listener.getClass().getMethods();
            methods = new HashSet<>(publicMethods.length, Float.MAX_VALUE);
            Collections.addAll(methods, publicMethods);
            Collections.addAll(methods, listener.getClass().getDeclaredMethods());
        } catch (NoClassDefFoundError e) {
            System.out.println("Plugin " + name + " has failed to register events for " + listener.getClass() + " because " + e.getMessage() + " does not exist.");
            return ret;
        }

        for (final Method method : methods) {
            final EventHandler eh = method.getAnnotation(EventHandler.class);
            if (eh == null) continue;
            final Class<?> checkClass;
            if (method.getParameterTypes().length != 1 || !Event.class.isAssignableFrom(checkClass = method.getParameterTypes()[0])) {
                System.out.println(name + " attempted to register an invalid EventHandler method signature \"" + method.toGenericString() + "\" in " + listener.getClass());
                continue;
            }
            final Class<? extends Event> eventClass = checkClass.asSubclass(Event.class);
            method.setAccessible(true);
            Set<RegisteredListener> eventSet = ret.computeIfAbsent(eventClass, k -> new HashSet<>());

            for (Class<?> clazz = eventClass; Event.class.isAssignableFrom(clazz); clazz = clazz.getSuperclass()) {
                // This loop checks for extending deprecated events
                if (clazz.getAnnotation(Deprecated.class) != null) {
                    System.out.println(
                            String.format(
                                    "%s has registered a listener for %s on method %s, but the event is Deprecated." +
                                            " %s; please notify the authors.",
                                    name,
                                    clazz.getName(),
                                    method.toGenericString(),
                                    "Server performance will be affected"));
                    break;
                }
            }

            EventExecutor executor = (listener1, event) -> {
                try {
                    if (!eventClass.isAssignableFrom(event.getClass())) {
                        return;
                    }
                    method.invoke(listener1, event);
                } catch (InvocationTargetException ex) {
                    throw new EventException(ex.getCause());
                } catch (Throwable t) {
                    throw new EventException(t);
                }
            };

            eventSet.add(new RegisteredListener(listener, executor, eh.priority(), api, eh.ignoreCancelled()));
        }
        return ret;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
