package ru.voronina.sandbox.graphql.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import ru.voronina.sandbox.model.Player;
import ru.voronina.sandbox.service.PlayerService;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Singleton
public class PlayerByIdDataFetcher implements DataFetcher<CompletableFuture<Player>> {

    private final PlayerService playerService;

    @Inject
    public PlayerByIdDataFetcher(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public CompletableFuture<Player> get(DataFetchingEnvironment environment) throws Exception {
        String id = environment.getArgument("id");
        log.info("[GraphQL] Get player by id={}", id);

        return playerService.findById(Long.parseLong(id));
    }
}
