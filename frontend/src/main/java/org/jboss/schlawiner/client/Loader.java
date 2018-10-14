package org.jboss.schlawiner.client;

import com.github.nalukit.nalu.client.application.AbstractApplicationLoader;

public class Loader extends AbstractApplicationLoader<Context> {

    @Override
    public void load(FinishLoadCommand finishLoadCommand) {
        context.setSettings(LocalStorage.loadSettings());
        finishLoadCommand.finishLoading();
    }
}
