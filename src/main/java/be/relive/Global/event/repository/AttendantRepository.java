package be.relive.Global.event.repository;

import be.relive.Global.event.domain.entity.Attendant;
import be.relive.Global.event.domain.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttendantRepository extends JpaRepository<Attendant, UUID> {

    @Transactional
    @Modifying
    @Query("UPDATE Attendants att SET att.imageUrl = :imageUrl, att.completeName = :completeName  WHERE att.userId = :userId")
    void updateAttendant(@Param("userId") UUID userId, @Param("imageUrl") String imageUrl, @Param("completeName") String completeName );
}
