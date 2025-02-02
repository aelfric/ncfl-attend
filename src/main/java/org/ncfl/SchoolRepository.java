package org.ncfl;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SchoolRepository implements PanacheRepository<School> {
}
