package ru.voronina.sandbox.graphql.fetcher;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import ru.voronina.sandbox.model.Game;
import ru.voronina.sandbox.service.GameService;

@Slf4j
@Singleton
public class GamesByPlayerDataFetcher implements DataFetcher<CompletableFuture<List<Game>>> {

    private final GameService gameService;

    @Inject
    public GamesByPlayerDataFetcher(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public CompletableFuture<List<Game>> get(DataFetchingEnvironment environment) {
        final String playerId = environment.getArgument("playerId");
        log.info("[GraphQL] Get games for player {}", playerId);

        return gameService.findByPlayer(Long.parseLong(playerId));
    }
}
