package me.kingof0.jda;

import net.dv8tion.jda.api.JDA;

public class RegisteredListener {
    private final Listener listener;
    private final EventPriority priority;
    private final JDA api;
    private final EventExecutor executor;
    private final boolean ignoreCancelled;

    public RegisteredListener(final Listener listener, final EventExecutor executor, final EventPriority priority, final JDA api, final boolean ignoreCancelled) {
        this.listener = listener;
        this.priority = priority;
        this.api = api;
        this.executor = executor;
        this.ignoreCancelled = ignoreCancelled;
    }

    /**
     * Gets the listener for this registration
     *
     * @return Registered Listener
     */
    public Listener getListener() {
        return listener;
    }

    /**
     * Gets the plugin for this registration
     *
     * @return Registered Plugin
     */
    public JDA getApi() {
        return api;
    }

    /**
     * Gets the priority for this registration
     *
     * @return Registered Priority
     */
    public EventPriority getPriority() {
        return priority;
    }

    /**
     * Calls the event executor
     *
     * @param event The event
     * @throws EventException If an event handler throws an exception.
     */
    public void callEvent(final Event event) throws EventException {
        if (event instanceof Cancellable){
            if (((Cancellable) event).isCancelled() && isIgnoringCancelled()){
                return;
            }
        }
        executor.execute(listener, event);
    }

    /**
     * Whether this listener accepts cancelled events
     *
     * @return True when ignoring cancelled events
     */
    public boolean isIgnoringCancelled() {
        return ignoreCancelled;
    }
}