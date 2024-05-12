package com.petition.platform.ooprequirements;

/**
 * Interface representing an event listener.
 */
public interface EventListener {
    /**
     * Default implementation for handling updates triggered by events.
     *
     * @param args The arguments passed to the event listener.
     * @throws InvalidArgumentListException if the arguments are invalid or insufficient.
     */
    default void update(String... args) throws InvalidArgumentListException {
        if(args == null || args.length < 2) {
            throw new InvalidArgumentListException("too few arguments provided");
        }
        System.out.println(System.currentTimeMillis() + ": user with email `" + args[0] + "` performed action:" + args[1]);
    }
}