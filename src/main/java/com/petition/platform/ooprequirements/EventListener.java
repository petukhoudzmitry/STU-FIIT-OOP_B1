package com.petition.platform.ooprequirements;

public interface EventListener {
    default void update(String... args) throws InvalidArgumentListException {
        if(args == null || args.length < 2) {
            throw new InvalidArgumentListException("too few arguments provided");
        }
        System.out.println(System.currentTimeMillis() + ": user with email `" + args[0] + "` performed action:" + args[1]);
    }
}
