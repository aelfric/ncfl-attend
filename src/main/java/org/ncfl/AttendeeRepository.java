package org.ncfl;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class AttendeeRepository implements PanacheRepositoryBase<Attendee, UUID> {

}
