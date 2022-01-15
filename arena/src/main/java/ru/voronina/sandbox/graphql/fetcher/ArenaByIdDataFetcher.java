package ru.voronina.sandbox.graphql.fetcher;

import java.util.concurrent.CompletableFuture;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import ru.voronina.sandbox.model.Arena;
import ru.voronina.sandbox.service.ArenaService;

@Slf4j
@Singleton
public class ArenaByIdDataFetcher implements DataFetcher<CompletableFuture<Arena>> {

    private final ArenaService arenaService;

    @Inject
    public ArenaByIdDataFetcher(ArenaService arenaService) {
        this.arenaService = arenaService;
    }

    @Override
    public CompletableFuture<Arena> get(DataFetchingEnvironment environment) throws Exception {
        String id = environment.getArgument("id");
        log.info("[GraphQL] Get arena by id={}", id);

        return arenaService.findById(Long.parseLong(id));
    }
}
