package proj.concert.service.services;

import proj.concert.common.dto.*;
import proj.concert.common.types.BookingStatus;

import proj.concert.service.domain.*;
import proj.concert.service.mapper.*;
import proj.concert.service.jaxrs.*;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.EntityTransaction;
import javax.persistence.PessimisticLockException;
import javax.persistence.LockModeType;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;

import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

import javax.ws.rs.WebApplicationException;

import java.net.URI;
import java.time.LocalDateTime;
import java.lang.Math;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.javatuples.Pair;

@Path("/concert-service")
@Produces({
    javax.ws.rs.core.MediaType.APPLICATION_JSON //ALL EXTRA PRODUCES/CONSUMES ANNOTATIONS NEED TO BE DONE PER METHOD, NOT DONE
})
public class ConcertResource {

    private EntityManager em = PersistenceManager.instance().createEntityManager();

    /** Retrieves all concerts. */
    @GET
    @Path("concerts")
    public Response retrieveConcerts() {
        try {
            em.getTransaction().begin();

            TypedQuery<Concert> query = em.createQuery("select e from Concert e", Concert.class);
            List<Concert> result = query.getResultList();


            ArrayList<ConcertDTO> concerts = new ArrayList<ConcertDTO>();

            for (Concert concert : result) {
                if (concert != null) {
                    ConcertDTO concertDTO = ConcertMapper.convert(concert);
                    concerts.add(concertDTO);
                }
            }

            em.getTransaction().commit();
            return Response.status(200).entity(concerts).build();
        } finally {
            em.close();
        }

    }

    /** Retrieves a specific concert via its ID. */
    @GET
    @Path("concerts/{id}")
    public Response retrieveConcert(@PathParam("id") long id) {
        try {
            em.getTransaction().begin();

            // get concert from path param id
            Concert concert = em.find(Concert.class, id);
            if (concert == null) {
                em.getTransaction().commit();
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }

            em.getTransaction().commit();

            ConcertDTO concertDTO = ConcertMapper.convert(concert);

            return Response.status(200).entity(concertDTO).build();
        } finally {
            em.close();
        }
    }

    /** Retrieves summaries of all concerts. */
    @GET
    @Path("concerts/summaries")
    public Response retrieveSummaries() {
        try {
            em.getTransaction().begin();

            // get concerts and create summary for each
            TypedQuery<Concert> query = em.createQuery("select e from Concert e", Concert.class);
            List<Concert> result = query.getResultList();

            ArrayList<ConcertSummaryDTO> summaries = new ArrayList<ConcertSummaryDTO>();

            for (Concert concert : result) {
                if (concert != null) {
                    ConcertSummaryDTO concertSummaryDTO = ConcertSummaryMapper.convert(concert);
                    summaries.add(concertSummaryDTO);
                }
            }

            em.getTransaction().commit();
            return Response.status(200).entity(summaries).build();
        } finally {
            em.close();
        }
    }

    /** Retrieves all performers. */
    @GET
    @Path("performers")
    public Response retrievePerformers() {
        try {
            em.getTransaction().begin();

            TypedQuery<Performer> query = em.createQuery("select p from Performer p", Performer.class);
            List<Performer> result = query.getResultList();

            ArrayList<PerformerDTO> performers = new ArrayList<PerformerDTO>();
            
            for (Performer performer : result) {
                if (performer != null) {
                    PerformerDTO performerDTO = PerformerMapper.convert(performer);
                    performers.add(performerDTO);
                }
            }

            em.getTransaction().commit();
            return Response.status(200).entity(performers).build();
        } finally {
            em.close();
        }
    }

    /** Retrieves a specific performer via their ID. */
    @GET
    @Path("performers/{id}")
    public Response retrievePerformer(@PathParam("id") long id) {
        try {
            em.getTransaction().begin();

            Performer performer = em.find(Performer.class, id);

            if (performer == null) {
                em.getTransaction().commit();
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }

            em.getTransaction().commit();

            PerformerDTO performerDTO = PerformerMapper.convert(performer);

            return Response.status(200).entity(performerDTO).build();
        } finally {
            em.close();
        }
    }


    /** Retrieves all bookings. */
    @GET
    @Path("bookings")
    public Response retrieveBookings(@CookieParam("auth") Cookie clientId) {

        if (clientId == null) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        try {
            em.getTransaction().begin();

            TypedQuery<Booking> query = em.createQuery("select b from Booking b where b.user.id = :id", Booking.class).setParameter("id", Long.parseLong(clientId.getValue()));
            List<Booking> result = query.getResultList();

            ArrayList<BookingDTO> collection = new ArrayList<BookingDTO>();

            em.getTransaction().commit();

            for (Booking b: result) {
                collection.add(BookingMapper.convert(b));
            }

            return Response.status(200).entity(collection).build();

        } catch(org.hibernate.PessimisticLockException e){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }finally {
            em.close();
        }     
    }

    /** Retrieves a specific booking via its ID. */
    @GET
    @Path("bookings/{id}")
    public Response retrieveBooking(@CookieParam("auth") Cookie clientId, @PathParam("id") long id) {

        if (clientId == null) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        try {
            em.getTransaction().begin();

            Booking booking = em.find(Booking.class, id);

            if (booking == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }


            if (booking.getUser().getId() != Long.parseLong(clientId.getValue())) {
                throw new WebApplicationException(Response.Status.FORBIDDEN);
            }

            em.getTransaction().commit();

            BookingDTO bookingDTO = BookingMapper.convert(booking);

            return Response.status(200).entity(bookingDTO).build();

        } catch(PessimisticLockException e){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }finally{
            em.close();
        }
    }


    /** Creates a new booking. */
    @POST
    @Path("bookings")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBooking(@CookieParam("auth") Cookie clientId, BookingRequestDTO bookingRequest) {

        if (clientId == null) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        LocalDateTime date = bookingRequest.getDate();
        Long concertId = bookingRequest.getConcertId();
        List<String> seatLabels = bookingRequest.getSeatLabels();

        if (seatLabels.size() == 0) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        try {
            em.getTransaction().begin();

            // fetching concert date
            TypedQuery<ConcertDate> concertDateQuery = em.createQuery("select d from ConcertDate d where d.date = :date and d.concert.id = :concertId", ConcertDate.class).setParameter("date", date).setParameter("concertId", concertId);
            List<ConcertDate> concertDates = concertDateQuery.getResultList();

            if (concertDates.size() == 0) {
                // cannot make a booking for a date in which no concert is found
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            ConcertDate concertDate = concertDates.get(0);

            // fetching seats with pessimistic read lock, so that they cannot be booked while we are already booking them
            TypedQuery<Seat> seatQuery = em.createQuery("select s from Seat s where s.label in :seatLabels and s.concertDate.id = :concertDateId", Seat.class).setLockMode(LockModeType.PESSIMISTIC_READ).setHint("javax.persistence.lock.timeout", 5000 ).setParameter("seatLabels", seatLabels).setParameter("concertDateId", concertDate.getId());
            List<Seat> seats = seatQuery.getResultList();

            // fetching user
            User user = em.find(User.class, Long.parseLong(clientId.getValue()));

            // checking if seats are available
            for (Seat s : seats) {
                if (s.isBooked()) {
                    // cannot make a booking for a seat that is already booked
                    em.getTransaction().commit(); //Need to commit transaction if error is thrown, as this will release the lock on the db
                    throw new WebApplicationException(Response.Status.FORBIDDEN);
                }
            }

            for (Seat s : seats) {
                s.setBooked(true);
                em.merge(s);
            }

            // creating booking
            Booking booking = new Booking();
            booking.setUser(user);
            booking.setDate(concertDate);

            booking.setSeats(seats);

            em.persist(booking);

            em.getTransaction().commit();

            postConcertInfo(concertDate); // for subscription methods

            return Response.created(URI.create("concert-service/bookings/" + booking.getId())).build();
        } finally {
            em.close();
        }
    }

    /** 
     * Gets a list of seats for a given concert date, optionally filtered by
     * those that are or are not yet booked.
     */
    @GET
    @Path("seats/{date}")
    public Response getSeatStatus(@PathParam("date") LocalDateTimeParam date, @QueryParam("status") BookingStatus status){

        LocalDateTime dateTime = date.getLocalDateTime();

        String queryString = "";
        switch (status.ordinal()) {
            case 0:
                queryString = "select s from Seat s where s.concertDate.id = :dateId and s.isBooked = TRUE";
                break;
            case 1:
                queryString = "select s from Seat s where s.concertDate.id = :dateId and s.isBooked = FALSE";
                break;
            case 2:
                queryString = "select s from Seat s where s.concertDate.id = :dateId";
                break;

        }

        try {
            em.getTransaction().begin();

            TypedQuery<ConcertDate> concertDateQuery = em.createQuery("select d from ConcertDate d where d.date = :date", ConcertDate.class).setParameter("date", dateTime);
            List<ConcertDate> dateResult = concertDateQuery.getResultList();

            if (dateResult.size() == 0) {
                // cannot get seats for a date that doesn't have a corresponding concert date
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            long dateId = dateResult.get(0).getId();

            TypedQuery<Seat> query = em.createQuery(queryString, Seat.class).setParameter("dateId", dateId);
            List<Seat> result = query.getResultList();

            ArrayList<SeatDTO> collection = new ArrayList<SeatDTO>();

            em.getTransaction().commit();

            for (Seat s: result) {
                collection.add(SeatMapper.convert(s));
            }

            return Response.status(200).entity(collection).build();

        } finally {
            em.close();
        } 
    }

    @POST
    @Path("subscribe/concertInfo")
    @Consumes(MediaType.APPLICATION_JSON)
    public void subscribeToConcertInfo(@Suspended AsyncResponse sub, @CookieParam("auth") Cookie clientId, ConcertInfoSubscriptionDTO subscriptionInfo) { 

        try {

            if (clientId == null) {
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }
            
            em.getTransaction().begin();

            Concert concert = em.find(Concert.class, subscriptionInfo.getConcertId());

            if(concert == null) {
                // concert not found
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            List<ConcertDate> dates = new ArrayList<ConcertDate>(concert.getDates());

            em.getTransaction().commit();

            Long dateId = -1L;
            LocalDateTime subDate = subscriptionInfo.getDate();

            for (ConcertDate date: dates) {
                //find date
                if (date.getDate().equals(subDate)) {
                    dateId = new Long(date.getId());
                }
            }

            if (dateId == -1L) {
                //invalid date
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }

            SubscriptionMap.instance().addSub(dateId, sub, Integer.valueOf(subscriptionInfo.getPercentageBooked()));


        }
        finally {
            em.close();
        }
    }


    private void postConcertInfo(ConcertDate date) {
        //seats MUST be eagerly loaded for this object

        ConcertInfoNotificationDTO notification = ConcertInfoMapper.convert(date);

        int percentageBooked = (int) Math.round(100 * (1 - ((double) notification.getNumSeatsRemaining() / date.getSeats().size())));

        Long dateId = date.getId();

        ConcurrentLinkedQueue<Pair> subsForDate = SubscriptionMap.instance().getSubsForDate(dateId);   


        if (!(subsForDate == null)) {
            for (Pair<AsyncResponse, Integer> subInfo : subsForDate) {
                if(subInfo.getValue1().intValue() <= percentageBooked) {
                    subInfo.getValue0().resume(notification);
                    SubscriptionMap.instance().removeSub(dateId, subInfo);
                }
            }
        }
    
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(UserDTO creds) {

        try {

            em.getTransaction().begin();

            TypedQuery<User> query = em.createQuery("select u from User u where u.username = :username", User.class).setParameter("username", creds.getUsername());
            List<User> result = query.getResultList();

            em.getTransaction().commit();

            // create example user to use to compare provided credentials to ones retrieved from db
            User credCompareUser = new User(creds.getUsername(), creds.getPassword());

            // if there is no user in db with that username, or password is incorrect, throw exception
            if(result.size() == 0 || !result.get(0).equals(credCompareUser)){
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }

            // issue a token for the user
            NewCookie cookie = new NewCookie("auth", result.get(0).getId().toString());

            // return the token on the response
            return Response.status(200).cookie(cookie).build();

        } 
        finally {
            em.close();
        }      
    }

}
