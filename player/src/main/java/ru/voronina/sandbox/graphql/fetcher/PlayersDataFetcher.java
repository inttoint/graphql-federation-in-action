package ru.voronina.sandbox.graphql.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import ru.voronina.sandbox.model.Player;
import ru.voronina.sandbox.service.PlayerService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Singleton
@Slf4j
public class PlayersDataFetcher implements DataFetcher<CompletableFuture<List<Player>>> {

    private final PlayerService playerService;

    @Inject
    public PlayersDataFetcher(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public CompletableFuture<List<Player>> get(DataFetchingEnvironment environment) throws Exception {
        log.info("[GraphQL] Get all players");

        return playerService.findAll();
    }
}
