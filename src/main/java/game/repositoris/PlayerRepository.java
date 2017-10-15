package game.repositoris;


import java.util.List;

import game.domini.Player;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/*
@RepositoryRestResource(collectionResourceRel = "players", path = "players")
public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {

    List<Player> findByLastName(@Param("name") String name);

}
*/
