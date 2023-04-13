package proj.concert.service.services;

import java.util.ArrayList;
import java.util.Map;

import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.container.AsyncResponse;

import org.javatuples.Pair;


public class SubscriptionMap {
    private static SubscriptionMap instance = null;

    private Map<Long, ArrayList> subs;

    protected SubscriptionMap() {
        subs = new ConcurrentHashMap<>();
    }

    public static SubscriptionMap instance() {
        if (instance == null) {
            instance = new SubscriptionMap();
        }
        return instance;
    }

    public void addSub(Long dateId, AsyncResponse sub, Integer percentageBooked){
        subs.putIfAbsent(dateId, new ArrayList<Pair>());
        ArrayList<Pair> subsForDate = subs.get(dateId);
        Pair<AsyncResponse, Integer> currentSub = new Pair<AsyncResponse, Integer>(sub, percentageBooked);
        subsForDate.add(currentSub);


    }

    public ArrayList<Pair> getSubsForDate(Long dateId){
        return subs.get(dateId);
    }

    public void removeSub(Long dateId, Pair<AsyncResponse, Integer> subPair){
        if(subs.containsKey(dateId)){
            subs.get(dateId).remove(subPair);
        }
    }

    //Reset for testing - called in ConcertUtils as tests are not permitted to be modified
    public static void reset() {
        instance = null;
    }


}
