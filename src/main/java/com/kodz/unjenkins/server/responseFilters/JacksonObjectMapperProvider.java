package com.kodz.unjenkins.server.responseFilters;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Created by Kurt on 12/7/15.
 */
@Provider
public class JacksonObjectMapperProvider implements ContextResolver<ObjectMapper> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectMapper getContext(final Class<?> type) {
        final ObjectMapper toReturn = new ObjectMapper();
        toReturn.enable(SerializationFeature.INDENT_OUTPUT); // This is the important setting
        toReturn.disable(MapperFeature.USE_ANNOTATIONS); // I have this one on but it's probably for other resources in the container testing it in, I don't know if you'd need it.
        return toReturn;
    }
}