package com.petition.platform.ooprequirements;

/**
 * Listener implementation for handling the creation of petitions.
 */
public class CreatePetitionActionListener implements EventListener{
    /**
     * Default constructor for CreatePetitionActionListener.
     */
    public CreatePetitionActionListener() {}

    /**
     * Handles the update event triggered upon petition creation.
     *
     * @param args The arguments passed to the listener.
     * @throws InvalidArgumentListException if the arguments are invalid or insufficient.
     */
    @Override
    public void update(String... args) throws InvalidArgumentListException {
        if(args == null || args.length < 2) {
            throw new InvalidArgumentListException("too few arguments provided");
        }
        System.out.println(System.currentTimeMillis() + ": user with email `" + args[0] + "` created petition: `" + args[1] + "`");
    }
}