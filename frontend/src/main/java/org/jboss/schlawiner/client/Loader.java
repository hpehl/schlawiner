package org.jboss.schlawiner.client;

import com.github.nalukit.nalu.client.application.AbstractApplicationLoader;
import org.jboss.schlawiner.client.settings.LocalStorage;

public class Loader extends AbstractApplicationLoader<Context> {

    @Override
    public void load(FinishLoadCommand finishLoadCommand) {
        context.setSettings(LocalStorage.load());
        finishLoadCommand.finishLoading();
    }
}
