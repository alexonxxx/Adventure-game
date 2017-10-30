package game.useCases;

import game.PlayerAlreadyExistException;
import game.domain.Monster;
import game.domain.Player;
import game.domain.Room;
import game.repositories.MonsterRepository;
import game.repositories.PlayerRepository;
import game.repositories.RoomRepository;
import org.springframework.stereotype.Service;

@Service
public final class PlayerUseCase {

    private final RoomRepository roomRepository;
    private final PlayerRepository playerRepository;
    private final MonsterRepository monsterRepository;

    public PlayerUseCase(PlayerRepository playerRepository, RoomRepository roomRepository,MonsterRepository monsterRepository) {

        this.playerRepository = playerRepository;
        this.roomRepository = roomRepository;
        this.monsterRepository= monsterRepository;
        addMonsters();

    }
    private void addMonsters() {
        this.monsterRepository.save(new Monster("monster2",5,5,3));
        this.monsterRepository.save(new Monster("monster1",5,5,4));
        // List<Monster> monsters=this.monsterRepository.findAll();
        // System.out.println(monsters.get(0).getName()); funciona
    }

    public void movePlayerToRoom(Player player, Room room) {

        player.x = room.getX();
        player.y = room.getY();
        playerRepository.save(player);
    }


    public void move(Player player, Room room, int direccio) {

        // Comprovem el tipus de sortida de l'habitació en la direcció del moviment
        // -1 cerrada, 0 abierta, > 0 llave

        int v = room.salidas[direccio];

        if (v < 0) {
            throw  new game.ExitRoomNotFoundException();

        } else if (v == 0) {

            int dx = 0 , dy = 0;
            switch (direccio) {
                case Room.UP: dx=0; dy=1; break; //NORD
                case Room.DOWN: dx=0; dy=-1; break; //SUD
                case Room.RIGHT: dx=1; dy=0; break; //EST
                case Room.LEFT: dx=-1; dy=0; break; //OEST
            }

            player.x += dx;
            player.y += dy;

            playerRepository.save(player);

        } else if (v > 0) {

            // la sortida te una porta

            if (player.getKey() != v) {
                throw  new game.RoomLockedException(v);
            }

            // Deixa la porta oberta i gasta la clau
            room.salidas[direccio] = 0;
            player.setKey(-1);

            roomRepository.save(room);
            playerRepository.save(player);

            move(player,room,direccio);
        }


    }

    public Player getFirst() {
        return playerRepository.findAll().get(0);
    }


    public Player save(Player player) throws PlayerAlreadyExistException {

        if (playerRepository.findByUsername(player.getUsername()).isPresent() )
            throw new PlayerAlreadyExistException(player.getUsername());

        return playerRepository.save(player);
    }
    public boolean resultAttackMonster(Player player, Room room) {
        int x = player.getCoordinateX();
        int y = player.getCoordinateY();
        //Room room = this.map.getRoom(x, y);
        //aqui agafariem el objecte de la llista a través del codi objecte
        int playerAttack = player.getWeapon(); //obtenir item jugador
        int playerDefense = player.getShield(); //obtenir item jugador
        Monster monster=this.monsterRepository.findOne(Long.valueOf(room.getMonsterCode()));
        int monsterAttack= monster.getAttack();
        int monsterLife=monster.getLife();
        int playerLife=player.getLife();
        //int monsterAttack = this.map.getlMonsters().getMonster(room.getMonsterCode()).getAttack();
        //int monsterLife = this.map.getlMonsters().getMonster(room.getMonsterCode()).getLife();
        if (playerAttack == -1) {
            System.out.println("No tens arma");
            return false;
        } else {
            if (playerAttack >= monsterLife) {
                System.out.println("El jugador ha guanyat");
                room.setMonsterCode(-1);
                this.roomRepository.save(room);
                //update room
                return true;
            } else {
                if (playerDefense != -1) {
                    if (playerDefense - monsterAttack >= 0) {
                        System.out.println("El jugador no ha derrotat el monstre");

                    } else {
                        playerLife = playerLife - monsterAttack + playerDefense;
                        if (playerLife > 0) {
                            player.setLife(playerLife);
                            System.out.println("El jugador ha perdut vida");

                        } else {
                            System.out.println("El jugador ha perdut");

                        }
                    }
                } else {
                    playerLife = playerLife - monsterAttack;
                    if (playerLife > 0) {

                        player.setLife(playerLife);
                        System.out.println("El jugador ha perdut vida");

                    } else {
                        System.out.println("El jugador ha perdut");
                       player.setPosition(0,0);


                    }
                }
                this.playerRepository.save(player);
                System.out.println(getFirst().getLife());
                //update player
                //començar metode on es veura si guanya o perd el jugador
                return false;
            }

        }
    }

}
