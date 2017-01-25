package com.kodz.unjenkins.server.responsefilters;

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
        return toReturn;
    }
}