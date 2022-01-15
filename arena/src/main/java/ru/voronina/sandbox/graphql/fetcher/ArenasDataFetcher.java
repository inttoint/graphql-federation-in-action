package ru.voronina.sandbox.graphql.fetcher;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import ru.voronina.sandbox.model.Arena;
import ru.voronina.sandbox.service.ArenaService;

@Singleton
@Slf4j
public class ArenasDataFetcher implements DataFetcher<CompletableFuture<List<Arena>>> {

    private final ArenaService arenaService;

    @Inject
    public ArenasDataFetcher(ArenaService arenaService) {
        this.arenaService = arenaService;
    }

    @Override
    public CompletableFuture<List<Arena>> get(DataFetchingEnvironment environment) throws Exception {
        log.info("[GraphQL] Get all arenas");

        return arenaService.findAll();
    }
}
