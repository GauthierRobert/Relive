package be.relive.Global.group.repository;

import be.relive.Global.group.domain.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, UUID> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Subscribers sub WHERE sub.group.key = :key AND sub.userId = :userId")
    void deleteByKeyAndUserId(@Param("key") String key, @Param("userId") UUID userId);

}
