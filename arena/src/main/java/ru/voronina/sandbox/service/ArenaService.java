package ru.voronina.sandbox.service;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import ru.voronina.sandbox.model.Arena;
import ru.voronina.sandbox.repository.ArenaRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Singleton
public class ArenaService {

    private final ArenaRepository arenaRepository;

    @Inject
    public ArenaService(ArenaRepository arenaRepository) {
        this.arenaRepository = arenaRepository;
    }

    public CompletableFuture<Arena> findById(long id) {
        log.info("[Service] Find arena by id={}", id);
        return arenaRepository.findById(id);
    }

    public CompletableFuture<List<Arena>> findAll() {
        log.info("[Service] Find all arenas");
        return arenaRepository.findAll();
    }
}
