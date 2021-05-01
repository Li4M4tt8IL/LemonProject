package me.pm.lemon.event;

import java.util.ArrayList;

public class Event {

    public Event call() {
        final ArrayList<EventData> dataList = EventManager.get(this.getClass());

        if(dataList != null) {
            for(EventData data : dataList) {
                try {
                    data.target.invoke(data.source, this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
    }

    private boolean cancelled = false;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
