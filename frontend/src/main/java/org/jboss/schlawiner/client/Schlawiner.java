package org.jboss.schlawiner.client;

import com.github.nalukit.nalu.plugin.elemental2.client.NaluPluginElemental2;
import com.google.gwt.core.client.EntryPoint;

public class Schlawiner implements EntryPoint {

    @Override
    public void onModuleLoad() {
        SchlawinerApplication application = new SchlawinerApplicationImpl();
        application.run(new NaluPluginElemental2());
    }
}
