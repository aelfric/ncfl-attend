package org.ncfl;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/schools")
public class SchoolResource {
    private final SchoolRepository schools;

    @Inject
    public SchoolResource(SchoolRepository schools){
        this.schools = schools;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<School>> all(){
       return schools.listAll();
    }

    @POST
    @WithTransaction
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<School> register(School school){
       return schools.persist(school);
    }
}
