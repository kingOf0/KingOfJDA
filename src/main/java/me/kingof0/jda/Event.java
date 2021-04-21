package me.kingof0.jda;

public abstract class Event {

    private String name;

    public Event() {}

    public String getEventName() {
        if (this.name == null) {
            this.name = this.getClass().getSimpleName();
        }

        return this.name;
    }

    public abstract HandlerList getHandlers();

    public static enum Result {
        DENY,
        DEFAULT,
        ALLOW;

        private Result() {
        }
    }
}
