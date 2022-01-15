package ru.voronina.sandbox.graphql.fetcher;

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
public class GameByIdDataFetcher implements DataFetcher<CompletableFuture<Game>> {

    private final GameService gameService;

    @Inject
    public GameByIdDataFetcher(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public CompletableFuture<Game> get(DataFetchingEnvironment environment) throws Exception {
        String id = environment.getArgument("id");
        log.info("[GraphQL] Get game by id={}", id);

        return gameService.findById(Long.parseLong(id));
    }
}
