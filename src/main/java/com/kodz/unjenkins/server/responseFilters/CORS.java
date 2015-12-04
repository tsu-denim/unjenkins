package com.kodz.unjenkins.server.responseFilters;


import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * Created by Kurt on 12/4/15.
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface CORS {}