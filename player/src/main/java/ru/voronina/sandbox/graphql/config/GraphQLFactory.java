package ru.voronina.sandbox.graphql.config;

import com.apollographql.federation.graphqljava.Federation;
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
import ru.voronina.sandbox.graphql.config.fetcher.FederationFetcher;
import ru.voronina.sandbox.graphql.config.fetcher.resolver.FederationTypeResolver;
import ru.voronina.sandbox.graphql.fetcher.PlayerByIdDataFetcher;
import ru.voronina.sandbox.graphql.fetcher.PlayersDataFetcher;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
@Factory
public class GraphQLFactory {

    public static final String PATH_SCHEMA = "classpath:schema.graphqls";

    private final PlayersDataFetcher playersDataFetcher;
    private final PlayerByIdDataFetcher playerByIdDataFetcher;

    @Inject
    public GraphQLFactory(PlayersDataFetcher playersDataFetcher, PlayerByIdDataFetcher playerByIdDataFetcher) {
        this.playersDataFetcher = playersDataFetcher;
        this.playerByIdDataFetcher = playerByIdDataFetcher;
    }

    @Bean
    @Singleton
    public GraphQL graphQL(ResourceResolver resourceResolver, FederationTypeResolver typeResolver, FederationFetcher fetcher) {
        InputStream schemaInputStream = resourceResolver.getResourceAsStream(PATH_SCHEMA).orElseThrow();
        TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry().merge(
                new SchemaParser().parse(new BufferedReader(new InputStreamReader(schemaInputStream))));

        GraphQLSchema graphQLSchema = Federation.transform(typeRegistry, buildRuntimeWiring())
                .resolveEntityType(typeResolver)
                .fetchEntities(fetcher)
                .build();

        return GraphQL.newGraphQL(graphQLSchema)
                .instrumentation(new FederatedTracingInstrumentation())
                .build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", builder -> builder
                        .dataFetcher("players", playersDataFetcher)
                        .dataFetcher("playerById", playerByIdDataFetcher))
                .build();
    }
}
