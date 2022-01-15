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

@Singleton
@Slf4j
public class GamesDataFetcher implements DataFetcher<CompletableFuture<List<Game>>> {

    private final GameService gameService;

    @Inject
    public GamesDataFetcher(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public CompletableFuture<List<Game>> get(DataFetchingEnvironment environment) throws Exception {
        log.info("[GraphQL] Get all games");

        return gameService.findAll();
    }
}
