package ru.voronina.sandbox.repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import ru.voronina.sandbox.model.Arena;
import ru.voronina.sandbox.model.Game;
import ru.voronina.sandbox.model.Player;

@Slf4j
@Singleton
public class GameRepository {

    private final List<Game> games = List.of(
            new Game(1L,
                    2,
                    "created",
                    new Arena(3L),
                    List.of(new Player(2L), new Player(3L))),
            new Game(2L,
                    3,
                    "started",
                    new Arena(1L),
                    List.of(new Player(3L), new Player(1L), new Player(2L))),
            new Game(3L,
                    1,
                    "finished",
                    new Arena(1L),
                    List.of(new Player(3L))));

    public CompletableFuture<List<Game>> findAll() {
        return findBy(v -> true);
    }

    public CompletableFuture<Game> findById(Long id) {
        return findBy(a -> a.getId().equals(id))
                .thenApplyAsync(docs -> {
                    if (docs.size() > 1) {
                        throw new IllegalStateException("[Repository] Found more than 1 game {id=" + id + "}");
                    }
                    return docs.stream().findFirst().orElse(null);
                });
    }

    public CompletableFuture<List<Game>> findBy(Predicate<Game> predicate) {
        return CompletableFuture.supplyAsync(
                () -> games.stream()
                        .filter(predicate)
                        .collect(Collectors.toList()));
    }
}
