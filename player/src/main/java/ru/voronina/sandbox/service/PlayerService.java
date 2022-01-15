package ru.voronina.sandbox.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import ru.voronina.sandbox.model.Player;
import ru.voronina.sandbox.repository.PlayerRepository;

@Slf4j
@Singleton
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Inject
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public CompletableFuture<Player> findById(long id) {
        log.info("[Service] Find player by id={}", id);
        return playerRepository.findById(id);
    }

    public CompletableFuture<List<Player>> findAll() {
        log.info("[Service] Find all players");
        return playerRepository.findAll();
    }
}
