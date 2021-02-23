package be.relive.Global.group.repository;

import be.relive.Global.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

    @Query("SELECT g FROM Groups g WHERE g.key IN :keys")
    List<Group> findAllByKeys(@Param("keys") List<String> keys);

    @Query("SELECT g FROM Groups g WHERE g.key = :key")
    Group findByKey(@Param("key") String key);
}
