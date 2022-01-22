package ru.voronina.sandbox.graphql.config.fetcher;

import static graphql.introspection.Introspection.TypeNameMetaFieldDef;

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
import ru.voronina.sandbox.model.Player;
import ru.voronina.sandbox.service.PlayerService;

@Slf4j
@Singleton
public class FederationFetcher implements DataFetcher<CompletableFuture<List<Object>>> {

    private final PlayerService playerService;

    @Inject
    public FederationFetcher(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public CompletableFuture<List<Object>> get(DataFetchingEnvironment environment) {
        final List<Map<String, Object>> arguments = Collections.unmodifiableList(environment.getArgument(_Entity.argumentName));

        CompletableFuture<?>[] futureResults = arguments.stream()
                .map(argument -> {
                    log.info("[Federation] Fetch entity {}", argument.get(TypeNameMetaFieldDef.getName()));

                    if (Player.class.getSimpleName().equals(argument.get(TypeNameMetaFieldDef.getName()))) {
                        final String id = (String) argument.get("id");
                        return playerService.findById(Long.parseLong(id));
                    }

                    return CompletableFuture.completedFuture(null);
                })
                .toArray(CompletableFuture[]::new);

        return CompletableFuture.allOf(futureResults)
                .thenApply(v -> {
                    List<Object> docs = Arrays.stream(futureResults)
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList());
                    log.info("[Federation] Found {} documents", docs.size());

                    if (CollectionUtils.isEmpty(docs)) {
                        return Collections.emptyList();
                    }

                    return docs;
                });
    }
}
