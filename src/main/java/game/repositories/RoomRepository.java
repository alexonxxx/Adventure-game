package game.repositories;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

import game.domain.Player;
import game.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;



public interface RoomRepository extends JpaRepository<Room, Long> {

    Collection<Room> findById(String roomId);

    Optional<Room> findByPosition(String position);

}

