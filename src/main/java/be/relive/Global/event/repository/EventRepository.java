package be.relive.Global.event.repository;

import be.relive.Global.event.domain.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    @Query("SELECT e FROM Events e WHERE e.groupKey = :groupKey")
    @EntityGraph(attributePaths = {"attendants"})
    Page<Event> findAllByGroupKey(@Param("groupKey") String groupKey, Pageable pageable);

    @Query("SELECT e FROM Events e WHERE e.groupKey = :groupKey")
    @EntityGraph(attributePaths = {"attendants"})
    List<Event> findAllByGroupKey(@Param("groupKey") String groupKey);

    @Query("SELECT e FROM Events e JOIN e.attendants a WHERE a.userId = :attendantId")
    @EntityGraph(attributePaths = {"attendants"})
    List<Event> findAllByAttendantId(@Param("attendantId") UUID attendantId);
}
