package ru.voronina.sandbox.repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import ru.voronina.sandbox.model.Arena;

@Slf4j
@Singleton
public class ArenaRepository {

    private final List<Arena> arenas = List.of(
            new Arena(1L, "Ocean", null, 1),
            new Arena(2L, "Forest", "The most wonderful place", 5),
            new Arena(3L, "Town", " Large densely populated urban area", 8));

    public CompletableFuture<List<Arena>> findAll() {
        return findBy(v -> true);
    }

    public CompletableFuture<Arena> findById(Long id) {
        return findBy(a -> a.getId().equals(id))
                .thenApplyAsync(docs -> {
                    if (docs.size() > 1) {
                        throw new IllegalStateException("[Repository] Found more than 1 arena {id=" + id + "}");
                    }
                    return docs.stream().findFirst().orElse(null);
                });
    }

    public CompletableFuture<List<Arena>> findBy(Predicate<Arena> predicate) {
        return CompletableFuture.supplyAsync(
                () -> arenas.stream()
                        .filter(predicate)
                        .collect(Collectors.toList()));
    }
}
