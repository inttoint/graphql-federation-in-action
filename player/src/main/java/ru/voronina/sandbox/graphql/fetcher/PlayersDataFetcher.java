package ru.voronina.sandbox.graphql.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import ru.voronina.sandbox.model.Player;
import ru.voronina.sandbox.repository.PlayerRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Singleton
@Slf4j
public class PlayersDataFetcher implements DataFetcher<CompletableFuture<List<Player>>> {

    private final PlayerRepository playerRepository;

    @Inject
    public PlayersDataFetcher(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public CompletableFuture<List<Player>> get(DataFetchingEnvironment environment) throws Exception {
        log.info("[GraphQL] Get all players");

        return playerRepository.findAll();
    }
}
