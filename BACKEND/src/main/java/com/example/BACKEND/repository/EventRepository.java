package com.example.BACKEND.repository;

import com.example.BACKEND.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    @Query(value="select name from event",nativeQuery = true)
    List<String> getNames();

    @Query(value="select id from event where name=:name",nativeQuery = true)
    Optional<Long> getIdFromName(String name);

    Optional<Event> findById(Long id);

    @Query(value="select * from event order by start_date",nativeQuery = true)
    List<Event> getOrderedEvents();
}
