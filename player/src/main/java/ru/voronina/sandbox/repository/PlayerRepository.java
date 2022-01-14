package ru.voronina.sandbox.repository;

import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.voronina.sandbox.model.Player;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Singleton
public class PlayerRepository {

    private final List<Player> players = List.of(
            new Player(1L, "first", 1),
            new Player(2L, "second", 5),
            new Player(3L, "third", 8));

    public CompletableFuture<Player> findById(int id) {
        log.info("[PlayerRepository] Find player by id={}", id);
        return CompletableFuture.supplyAsync(
                () -> players.stream()
                        .filter(p -> p.getId() == id)
                        .filter(Player::getActive)
                        .findFirst()
                        .orElse(null));
    }

    public CompletableFuture<List<Player>> findAll() {
        log.info("[PlayerRepository] Find all players");
        return CompletableFuture.supplyAsync(
                () -> players.stream()
                        .filter(Player::getActive)
                        .collect(Collectors.toList()));
    }
}
