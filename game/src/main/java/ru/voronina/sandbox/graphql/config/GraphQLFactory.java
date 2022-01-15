package ru.voronina.sandbox.graphql.config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.apollographql.federation.graphqljava.tracing.FederatedTracingInstrumentation;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.io.ResourceResolver;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import ru.voronina.sandbox.graphql.fetcher.GameByIdDataFetcher;
import ru.voronina.sandbox.graphql.fetcher.GamesDataFetcher;

@Slf4j
@Factory
public class GraphQLFactory {

    public static final String PATH_SCHEMA = "classpath:schema.graphqls";

    private final GamesDataFetcher gamesDataFetcher;
    private final GameByIdDataFetcher gameByIdDataFetcher;

    @Inject
    public GraphQLFactory(GamesDataFetcher gamesDataFetcher, GameByIdDataFetcher gameByIdDataFetcher) {
        this.gamesDataFetcher = gamesDataFetcher;
        this.gameByIdDataFetcher = gameByIdDataFetcher;
    }

    @Bean
    @Singleton
    public GraphQL graphQL(ResourceResolver resourceResolver) {
        InputStream schemaInputStream = resourceResolver.getResourceAsStream(PATH_SCHEMA).orElseThrow();
        TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry().merge(
                new SchemaParser().parse(new BufferedReader(new InputStreamReader(schemaInputStream))));

        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeRegistry, buildRuntimeWiring());

        return GraphQL.newGraphQL(graphQLSchema)
                .instrumentation(new FederatedTracingInstrumentation())
                .build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", builder -> builder
                        .dataFetcher("games", gamesDataFetcher)
                        .dataFetcher("gameById", gameByIdDataFetcher))
                .build();
    }
}
