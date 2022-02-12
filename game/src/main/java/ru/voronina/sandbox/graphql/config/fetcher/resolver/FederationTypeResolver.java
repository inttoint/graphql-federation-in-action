package ru.voronina.sandbox.graphql.config.fetcher.resolver;

import graphql.TypeResolutionEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.TypeResolver;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import ru.voronina.sandbox.model.Arena;

@Slf4j
@Singleton
public class FederationTypeResolver implements TypeResolver {

    @Override
    public GraphQLObjectType getType(TypeResolutionEnvironment env) {
        Object sourceObj = env.getObject();
        log.info("[Federation] Received type {}", sourceObj);

        if (sourceObj instanceof Arena) {
            return env.getSchema().getObjectType(Arena.class.getSimpleName());
        } else {
            log.warn("Unknown type {} can't be resolved", sourceObj);
            return null;
        }
    }
}
