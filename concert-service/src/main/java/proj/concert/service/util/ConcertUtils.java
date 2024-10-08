package proj.concert.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import proj.concert.service.domain.Concert;
import proj.concert.service.domain.ConcertDate;
import proj.concert.service.domain.Seat;
import proj.concert.service.services.ConcertApplication;
import proj.concert.service.services.PersistenceManager;
import proj.concert.service.services.SubscriptionMap;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConcertUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(ConcertUtils.class);

    /**
     * This method will clear all seat and booking data from the database. Then, it will create all Seat objects for
     * all concerts and dates.
     */
    public static void initConcerts() {

        //clear subscription data as booking and seats are being reset
        SubscriptionMap.reset();

        LOGGER.debug("initConcerts(): Creating the Application");

        EntityManager em = PersistenceManager.instance().createEntityManager();
        try {

            // Get all concerts
            em.getTransaction().begin();
            TypedQuery<Concert> query = em.createQuery("select c from Concert c", Concert.class);
            List<Concert> concerts = query.getResultList();

            // Get all dates for all concerts
            Set<ConcertDate> allDates = new HashSet<>();
            for (Concert c : concerts) {
                Set<ConcertDate> dates = c.getDates();
                allDates.addAll(dates);
            }
            em.getTransaction().commit();

            LOGGER.debug("initConcerts(): There are " + allDates.size() + " concert dates");

            // For each concert date, create the seats for that date and persist them.
            int seatCount = 0;
            for (ConcertDate date : allDates) {

                em.getTransaction().begin();
                Set<Seat> seatsForDate = TheatreLayout.createSeatsFor(date);
                for (Seat s : seatsForDate) {
                    em.persist(s);
                    seatCount++;
                }

                date.setSeats(seatsForDate);
                em.merge(date);

                em.getTransaction().commit();

                // Ensures we aren't braking the EM with thousands of seat entities.
                em.clear();
            }

            LOGGER.debug("initConcerts(): Created " + seatCount + " seats!");
        } finally {
            em.close();
        }
    }
}
