package org.ncfl;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestForm;

import java.util.List;
import java.util.UUID;

@Path("/attendees")
public class AttendeeResource {

    AttendeeRepository attendees;
    Template attendee;

    @Inject
    public AttendeeResource(AttendeeRepository attendees, Template attendee) {
        this.attendees = attendees;
        this.attendee = attendee;
    }

    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Attendee>> all() {
        return attendees.listAll();
    }

    @WithTransaction
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Uni<Attendee> register(@RestForm String name, @RestForm LunchType lunchType) {
        Attendee entity = new Attendee();
        entity.setName(name);
        entity.setLunchType(lunchType);
        entity.setStatus(OrderStatus.REQUESTED);
        return attendees.persist(entity);
    }

    @WithTransaction
    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Uni<TemplateInstance> markStatus(@PathParam("id") UUID id, @RestForm("status") OrderStatus status) {
        return attendees.findById(id)
                .flatMap(a -> {
                    a.setStatus(status);
                    return attendees.persist(a);
                }).map(attendee::data);
    }

    @GET
    @Path("/{id}")
    public Uni<Attendee> getOne(@PathParam("id") UUID id) {
        return attendees.findById(id);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_HTML)
    public Uni<TemplateInstance> displayOne(@PathParam("id") UUID id) {
        return attendees.findById(id).map(a -> attendee.data(a));
    }

}
