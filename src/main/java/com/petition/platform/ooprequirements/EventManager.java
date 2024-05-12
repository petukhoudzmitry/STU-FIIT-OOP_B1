package com.petition.platform.ooprequirements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages event subscriptions and notifications.
 */
public class EventManager {

    /** Singleton instance of the EventManager. */
    private static EventManager instance = null;

    /** Map to hold event listeners for different types of actions. */
    final Map<Actions, List<EventListener>> listeners = new HashMap<>();

    /**
     * Constructs an EventManager with the specified list of actions.
     *
     * @param operations The list of actions to be supported by the EventManager.
     */
    private EventManager(Actions... operations) {
        for (Actions operation : operations) {
            listeners.put(operation, new ArrayList<>());
        }
    }

    /**
     * Retrieves the singleton instance of the EventManager.
     *
     * @return The singleton instance of the EventManager.
     */
    public static EventManager getInstance() {
        if(instance == null){
            Runnable thread = () -> {
                synchronized (EventManager.class) {
                    instance = new EventManager(Actions.values());
                }
            };
            thread.run();
        }
        return instance;
    }

    /**
     * Subscribes an event listener to a specific type of action.
     *
     * @param eventType The type of action to subscribe to.
     * @param listener  The event listener to subscribe.
     */
    public void subscribe(Actions eventType, EventListener listener) {
        List<EventListener> list = listeners.get(eventType);
        if(list != null) list.add(listener);
    }

    /**
     * Notifies all subscribed listeners of a specific type of action.
     *
     * @param eventType The type of action to notify listeners about.
     * @param args      The arguments to pass to the event listeners.
     */
    public void notify(Actions eventType, String... args) {
        List<EventListener> list = listeners.get(eventType);
        if(list == null) return;
        for(EventListener eventListener : list) {
            try {
                eventListener.update(args);
            } catch (InvalidArgumentListException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}