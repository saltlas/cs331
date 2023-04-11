package proj.concert.service.services;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import proj.concert.common.dto.*;
import proj.concert.common.types.*;

import proj.concert.service.domain.*;
import proj.concert.service.mapper.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.WebApplicationException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.EntityGraph;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


@Path("/concert-service")
@Produces({
    javax.ws.rs.core.MediaType.APPLICATION_JSON //ALL EXTRA PRODUCES/CONSUMES ANNOTATIONS NEED TO BE DONE PER METHOD, NOT DONE
})
public class ConcertResource {

    //private static Logger LOGGER = LoggerFactory.getLogger(ConcertResource.class);
    private EntityManager em = PersistenceManager.instance().createEntityManager();
    //private final List<AsyncResponse> subs = new Vector<>(); needed for async methods but potentially will need several (one for each concert/date)


    @GET
    @Path("concerts")
    public Response retrieveConcerts() {
        try {
            em.getTransaction().begin();

            //entitygraph used to reduce queries needed, might not work lol

            EntityGraph<Concert> entityGraph = em.createEntityGraph(Concert.class);
            entityGraph.addAttributeNodes("dates");
            entityGraph.addAttributeNodes("performers");

            TypedQuery<Concert> query = em.createQuery("select e from Concert e", Concert.class).setHint("javax.persistence.fetchgraph", entityGraph);
            List<Concert> result = query.getResultList();

            ArrayList<ConcertDTO> collection = new ArrayList<ConcertDTO>();
            ConcertMapper mapper = new ConcertMapper();


            for (Concert concert : result) {
                if (concert != null) {
                    ConcertDTO concertDTO = mapper.convert(concert);
                    collection.add(concertDTO);
                }
            }

            em.getTransaction().commit();
            return Response.status(200).entity(collection).build();
        } finally {
            em.close();
        }

    }

    @GET
    @Path("concerts/{id}")
    public Response retrieveConcert(@PathParam("id") long id) {
        try {
            em.getTransaction().begin();

            Concert concert = em.find(Concert.class, id);
            //Concert concert = em.createQuery("select e from Concert e left join fetch e.dates where s.id = :id", Concert.class).setParameter("id", id).getSingleResult();
            if (concert == null) {
                em.getTransaction().commit();
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }

            concert.getDates();
            concert.getPerformers();
            em.getTransaction().commit();

            ConcertMapper mapper = new ConcertMapper();
            ConcertDTO concertDTO = mapper.convert(concert);

            return Response.status(200).entity(concertDTO).build();
        } finally {
            em.close();
        }
    }

    @GET
    @Path("concerts/summaries")
    public Response retrieveSummaries() {
        try {
            em.getTransaction().begin();

            TypedQuery<Concert> query = em.createQuery("select e from Concert e", Concert.class);
            List<Concert> result = query.getResultList();

            ArrayList<ConcertSummaryDTO> collection = new ArrayList<ConcertSummaryDTO>();
            ConcertSummaryMapper mapper = new ConcertSummaryMapper();


            for (Concert concert : result) {
                if (concert != null) {
                    ConcertSummaryDTO concertSummaryDTO = mapper.convert(concert);
                    collection.add(concertSummaryDTO);
                }
            }

            em.getTransaction().commit();
            return Response.status(200).entity(collection).build();
        } finally {
            em.close();
        }
    }

    @GET
    @Path("performers")
    public Response retrievePerformers() {
        try {
            em.getTransaction().begin();

            TypedQuery<Performer> query = em.createQuery("select p from Performer p", Performer.class);
            List<Performer> result = query.getResultList();

            ArrayList<PerformerDTO> collection = new ArrayList<PerformerDTO>();
            PerformerMapper mapper = new PerformerMapper();


            for (Performer performer : result) {
                if (performer != null) {
                    PerformerDTO performerDTO = mapper.convert(performer);
                    collection.add(performerDTO);
                }
            }

            em.getTransaction().commit();
            return Response.status(200).entity(collection).build();
        } finally {
            em.close();
        }
    }

    @GET
    @Path("performers/{id}")
    public Response retrievePerformer(@PathParam("id") long id) {
        try {
            em.getTransaction().begin();

            Performer performer = em.find(Performer.class, id);
            //Concert concert = em.createQuery("select e from Concert e left join fetch e.dates where s.id = :id", Concert.class).setParameter("id", id).getSingleResult();
            if (performer == null) {
                em.getTransaction().commit();
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }

            em.getTransaction().commit();

            PerformerMapper mapper = new PerformerMapper();
            PerformerDTO performerDTO = mapper.convert(performer);

            return Response.status(200).entity(performerDTO).build();
        } finally {
            em.close();
        }
    }
}

/* NOT IMPLEMENTED BELOW - copy paste method into above class and then implement


    @GET
    @Path("concerts/summaries")
    public Response retrieveSummaries() {
        //TODO
    }

    @GET
    @Path("bookings")
    public Response retrieveBooking(){
        //TODO
    }


    @POST
    @Path("bookings")
    public Response createBooking(BookingRequestDTO bookingRequest){
        //TODO
    }

    @GET
    @Path("seats/{date}")
    public Response getSeatStatus(@QueryParam("status") BookingStatus status){
        //TODO
    }

    @POST
    @Path("login")
    public Response login(UserDTO creds){
        //TODO
    }

    //Taken from lecture examples lecture 10, will need modification for project purposes
    @GET
    @Path("subscribe/concertInfo")
    public void subscribeToConcertInfo(@Suspended AsyncResponse sub, ConcertInfoSubscriptionDTO subscription) { 
        //TODO     
        //subs.add(sub);        
    }


    //POSTs a notification, which will be pushed back to all subscribers, taken from lecture example code as above
    // notification param the notification to POST.
    @POST
    public Response postConcertInfo(ConcertInfoNotificationDTO notification) {
        //TODO

        //synchronized (subs) {
        //    for (AsyncResponse sub : subs) {
        //        sub.resume(notification);
        //    }
        //    subs.clear();
        //}

        //return Response.ok().build();

    }


*/