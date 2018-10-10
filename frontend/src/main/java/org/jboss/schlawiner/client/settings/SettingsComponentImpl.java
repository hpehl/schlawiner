package org.jboss.schlawiner.client.settings;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.Event;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.HTMLSelectElement;
import org.gwtproject.event.shared.HandlerRegistration;
import org.gwtproject.event.shared.HandlerRegistrations;
import org.jboss.gwt.elemento.core.EventType;
import org.jboss.schlawiner.engine.game.Level;
import org.jboss.schlawiner.engine.game.Settings;

import static java.lang.Integer.parseInt;
import static org.jboss.gwt.elemento.core.EventType.input;

public class SettingsComponentImpl extends AbstractComponent<SettingsController, HTMLElement> implements
    SettingsComponent {

    private SettingsView view;
    private HandlerRegistration handler;

    @Override
    public void render() {
        view = SettingsView.create();
        initElement(view.asElement());
    }

    @Override
    public void onAttach() {
        super.onAttach();
        this.handler = HandlerRegistrations.compose(
            EventType.bind(view.name, input, event -> getController().setName(value(event))),
            EventType.bind(view.numbers, input, event -> {
                    view.numbersOutput.value = value(event);
                    getController().setNumbers(parseInt(value(event)));
                }
            ),
            EventType.bind(view.timeout, input, event -> {
                    view.timeoutOutput.value = value(event);
                    getController().setTimeout(parseInt(value(event)));
                }
            ),
            EventType.bind(view.penalty, input, event -> {
                view.penaltyOutput.value = value(event);
                getController().setPenalty(parseInt(value(event)));
            }),
            EventType.bind(view.retries, input, event -> {
                view.retriesOutput.value = value(event);
                getController().setRetries(parseInt(value(event)));
            }),
            EventType.bind(view.autoDice, input,
                event -> getController().setAutoDice(((HTMLInputElement) event.target).checked)),
            EventType.bind(view.level, input,
                event -> {
                    String value = ((HTMLSelectElement) event.target).value;
                    Level level = Level.valueOf(value.toUpperCase());
                    getController().setLevel(level);
                }));
    }

    private String value(Event event) {
        return ((HTMLInputElement) event.target).value;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (handler != null) {
            handler.removeHandler();
        }
    }

    @Override
    public void showSettings(Settings settings) {
        view.name.value = settings.getName();
        view.numbers.value = String.valueOf(settings.getNumbers());
        view.numbersOutput.value = String.valueOf(settings.getNumbers());
        view.timeout.value = String.valueOf(settings.getTimeout());
        view.timeoutOutput.value = String.valueOf(settings.getTimeout());
        view.penalty.value = String.valueOf(settings.getPenalty());
        view.penaltyOutput.value = String.valueOf(settings.getPenalty());
        view.retries.value = String.valueOf(settings.getRetries());
        view.retriesOutput.value = String.valueOf(settings.getRetries());
        view.autoDice.checked = settings.isAutoDice();
        view.level.value = settings.getLevel().name().toLowerCase();
    }
}
