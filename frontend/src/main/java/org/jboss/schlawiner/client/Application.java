package org.jboss.schlawiner.client;

import com.github.nalukit.nalu.client.application.IsApplication;

@com.github.nalukit.nalu.client.application.annotation.Application(
    loader = Loader.class, shell = Shell.class, startRoute = "/main", context = Context.class)
public interface Application extends IsApplication {
}
