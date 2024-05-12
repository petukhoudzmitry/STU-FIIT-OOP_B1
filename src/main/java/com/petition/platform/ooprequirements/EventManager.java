package com.petition.platform.ooprequirements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

    private static EventManager instance = null;

    final Map<Actions, List<EventListener>> listeners = new HashMap<>();

    private EventManager(Actions... operations) {
        for (Actions operation : operations) {
            listeners.put(operation, new ArrayList<>());
        }
    }

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

    public void subscribe(Actions eventType, EventListener listener) {
        List<EventListener> list = listeners.get(eventType);
        if(list != null) list.add(listener);
    }

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