package game.repositories;

import game.domain.Monster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Àlex on 23/10/2017.
 */
@Repository
public interface MonsterRepository extends JpaRepository<Monster, Long> {
    Monster findByName(String name);


}

