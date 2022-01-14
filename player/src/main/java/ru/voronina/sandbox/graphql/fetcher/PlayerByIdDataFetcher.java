package ru.voronina.sandbox.graphql.fetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import ru.voronina.sandbox.model.Player;
import ru.voronina.sandbox.repository.PlayerRepository;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Singleton
public class PlayerByIdDataFetcher implements DataFetcher<CompletableFuture<Player>> {

    private final PlayerRepository playerRepository;

    @Inject
    public PlayerByIdDataFetcher(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public CompletableFuture<Player> get(DataFetchingEnvironment environment) throws Exception {
        String id = environment.getArgument("id");
        log.info("[GraphQL] Get player by id={}", id);

        return playerRepository.findById(Integer.parseInt(id));
    }
}
