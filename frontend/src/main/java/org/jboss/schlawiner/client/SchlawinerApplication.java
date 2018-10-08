package org.jboss.schlawiner.client;

import com.github.nalukit.nalu.client.application.IsApplication;
import com.github.nalukit.nalu.client.application.annotation.Application;

@Application(shell = Shell.class, startRoute = "/why-cant-this-be-empty", context = Context.class)
public interface SchlawinerApplication extends IsApplication {
}
