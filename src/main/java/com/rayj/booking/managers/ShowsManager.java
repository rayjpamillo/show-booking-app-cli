package com.rayj.booking.managers;

import com.rayj.booking.models.Show;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ShowsManager {
    private static ShowsManager INSTANCE;
    private Map<Integer, Show> showsMap;

    private ShowsManager(){
        this.showsMap = new HashMap<>();
    }

    public static ShowsManager getINSTANCE() {
        if(INSTANCE==null){
            INSTANCE = new ShowsManager();
        }
        return INSTANCE;
    }

    public void addShow(Integer key, Show show){
        INSTANCE.showsMap.put(key, show);
    }

    public Show getShow(Integer key){
        return INSTANCE.showsMap.get(key);
    }

    public void removeShow(Integer key){
        INSTANCE.showsMap.remove(key);
    }

    public Set<Integer> getShows(){
        return INSTANCE.showsMap.keySet();
    }

    public void addRow(int showNumber) {
        INSTANCE.showsMap.get(showNumber).addRow();
    }
}
