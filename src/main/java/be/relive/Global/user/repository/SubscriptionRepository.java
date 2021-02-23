package be.relive.Global.user.repository;

import be.relive.Global.user.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Subscriptions sub WHERE sub.key = :key AND sub.user.id = :userId")
    void deleteByKeyAndUserId(@Param("key") String key, @Param("userId") UUID userId);

}
