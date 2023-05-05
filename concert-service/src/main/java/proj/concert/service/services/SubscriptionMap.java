package proj.concert.service.services;

import java.util.Map;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.container.AsyncResponse;

import java.util.ArrayList;

/** A class that holds subscriptions for users of the concert booking service in a map */

public class SubscriptionMap {
    private static SubscriptionMap instance = null;

    private Map<Long, ConcurrentLinkedQueue> subs;

    protected SubscriptionMap() {
        subs = new ConcurrentHashMap<>();
    }

    public static SubscriptionMap instance() { // instantiating the class this way ensures subs don't get reset too early and therefore lost
        if (instance == null) {
            instance = new SubscriptionMap();
        }
        return instance;
    }

    /** Adds a subscription to the map */
    public void addSub(Long dateId, AsyncResponse sub, Integer percentageBooked) {
        // if the date in question isn't present as a key in the map yet, add it
        subs.putIfAbsent(dateId, new ConcurrentLinkedQueue<ArrayList>()); 
        // get the list of subs for the date
        ConcurrentLinkedQueue<ArrayList> subsForDate = subs.get(dateId);
        // add a pair (tuple) to the list which contains the asyncresponse and the percentage at which to resume it
        ArrayList<Object> currentSub = new ArrayList<Object>();
        currentSub.add(sub);
        currentSub.add(percentageBooked);
        subsForDate.add(currentSub);
    }

    public ConcurrentLinkedQueue<ArrayList> getSubsForDate(Long dateId) {
        return subs.get(dateId);
    }

    public void removeSub(Long dateId, ArrayList<Object> subPair) {
        if(subs.containsKey(dateId)) {
            subs.get(dateId).remove(subPair);
        }
    }

    // reset for testing - called in ConcertUtils as tests are not permitted to be modified
    public static void reset() {
        instance = null;
    }


}
