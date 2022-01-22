package ru.voronina.sandbox.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import ru.voronina.sandbox.model.Game;
import ru.voronina.sandbox.model.Player;
import ru.voronina.sandbox.repository.GameRepository;

@Slf4j
@Singleton
public class GameService {

    private final GameRepository gameRepository;

    @Inject
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public CompletableFuture<Game> findById(long id) {
        log.info("[Service] Find game by id={}", id);
        return gameRepository.findById(id);
    }

    public CompletableFuture<List<Game>> findByPlayer(Long playerId) {
        log.info("[Service] Find games for player {}", playerId);
        return gameRepository.findBy(g -> g.getPlayers().stream().map(Player::getId).anyMatch(id -> id.equals(playerId)));
    }

    public CompletableFuture<List<Game>> findAll() {
        log.info("[Service] Find all games");
        return gameRepository.findAll();
    }
}
