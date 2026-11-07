package com.riotstak.back_xentrix.api.v1.local.api_xentrix.venues.adapters.bd1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {

}
