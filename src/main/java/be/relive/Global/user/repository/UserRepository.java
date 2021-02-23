package be.relive.Global.user.repository;

import be.relive.Global.user.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM Users u WHERE u.profile.id = :facebook_id")
    @EntityGraph(attributePaths = {"subscriptions"})
    User findBy(@Param("facebook_id") String facebookId);

}
