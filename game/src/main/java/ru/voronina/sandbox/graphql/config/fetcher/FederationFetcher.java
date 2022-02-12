package ru.voronina.sandbox.graphql.config.fetcher;

import static graphql.introspection.Introspection.TypeNameMetaFieldDef;
import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.apollographql.federation.graphqljava._Entity;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import io.micronaut.core.util.CollectionUtils;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import ru.voronina.sandbox.model.Arena;
import ru.voronina.sandbox.service.GameService;

@Slf4j
@Singleton
public class FederationFetcher implements DataFetcher<CompletableFuture<List<Object>>> {

    private final GameService gameService;

    @Inject
    public FederationFetcher(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public CompletableFuture<List<Object>> get(DataFetchingEnvironment environment) {
        final List<Map<String, Object>> arguments = Collections.unmodifiableList(environment.getArgument(_Entity.argumentName));
//        You can group arguments by '__typename', then make one query to DB
//        Map<Object, List<Object>> typenameToIdsMap = arguments.stream()
//                .collect(groupingBy(m -> m.get(TypeNameMetaFieldDef.getName()), mapping(m -> m.get("id"), toList())));

        CompletableFuture<?>[] futureResults = arguments.stream()
                .map(argument -> {
                    log.info("[Federation] Fetch entity {}", argument.get(TypeNameMetaFieldDef.getName()));

                    if (Arena.class.getSimpleName().equals(argument.get(TypeNameMetaFieldDef.getName()))) {
                        final String idAsString = (String) argument.get("id");
                        // Check the number of returned records and their order

                        long id = Long.parseLong(idAsString);
                        return gameService.findByArenaId(id).thenApply(games -> new Arena(id, games));
                    }

                    return CompletableFuture.completedFuture(null);
                })
                .toArray(CompletableFuture[]::new);

        return CompletableFuture.allOf(futureResults)
                .thenApply(v -> {
                    List<Object> docs = Arrays.stream(futureResults)
                            .map(CompletableFuture::join)
                            .collect(toList());
                    log.info("[Federation] Found {} documents", docs.size());

                    if (CollectionUtils.isEmpty(docs)) {
                        return Collections.emptyList();
                    }

                    return docs;
                });
    }
}
