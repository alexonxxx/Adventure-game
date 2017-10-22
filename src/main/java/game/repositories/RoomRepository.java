package game.repositories;


import java.util.Collection;
import java.util.Optional;

import game.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Collection<Room> findById(Long Id);

    Optional<Room> findByPosition(String position);

}

