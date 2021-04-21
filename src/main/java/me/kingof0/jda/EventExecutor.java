package me.kingof0.jda;

public interface EventExecutor {
    void execute(Listener var1, Event var2) throws EventException;
}