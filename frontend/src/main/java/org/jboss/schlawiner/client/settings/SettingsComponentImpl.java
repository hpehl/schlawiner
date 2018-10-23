package org.jboss.schlawiner.client.settings;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.Event;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.HTMLOutputElement;
import elemental2.dom.HTMLSelectElement;
import org.gwtproject.event.shared.HandlerRegistration;
import org.gwtproject.event.shared.HandlerRegistrations;
import org.jboss.gwt.elemento.core.EventType;
import org.jboss.gwt.elemento.template.DataElement;
import org.jboss.gwt.elemento.template.Templated;
import org.jboss.schlawiner.engine.game.Level;
import org.jboss.schlawiner.engine.game.Settings;

import static java.lang.Integer.parseInt;
import static org.jboss.gwt.elemento.core.EventType.input;

@Templated("settings.html")
public abstract class SettingsComponentImpl extends AbstractComponent<SettingsController, HTMLElement> implements
    SettingsComponent {

    static SettingsComponent create() {
        return new Templated_SettingsComponentImpl();
    }

    @DataElement HTMLInputElement numbers;
    @DataElement HTMLOutputElement numbersOutput;
    @DataElement HTMLInputElement timeout;
    @DataElement HTMLOutputElement timeoutOutput;
    @DataElement HTMLInputElement penalty;
    @DataElement HTMLOutputElement penaltyOutput;
    @DataElement HTMLInputElement retries;
    @DataElement HTMLOutputElement retriesOutput;
    @DataElement HTMLInputElement autoDice;
    @DataElement HTMLSelectElement level;
    private HandlerRegistration handler;

    @Override
    public void render() {
        initElement(asElement());
    }

    @Override
    public void onAttach() {
        super.onAttach();
        this.handler = HandlerRegistrations.compose(
            EventType.bind(numbers, input, event -> {
                    numbersOutput.value = value(event);
                    getController().setNumbers(parseInt(value(event)));
                }
            ),
            EventType.bind(timeout, input, event -> {
                    timeoutOutput.value = value(event);
                    getController().setTimeout(parseInt(value(event)));
                }
            ),
            EventType.bind(penalty, input, event -> {
                penaltyOutput.value = value(event);
                getController().setPenalty(parseInt(value(event)));
            }),
            EventType.bind(retries, input, event -> {
                retriesOutput.value = value(event);
                getController().setRetries(parseInt(value(event)));
            }),
            EventType.bind(autoDice, input,
                event -> getController().setAutoDice(((HTMLInputElement) event.target).checked)),
            EventType.bind(level, input,
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
        numbers.value = String.valueOf(settings.getNumbers());
        numbersOutput.value = String.valueOf(settings.getNumbers());
        timeout.value = String.valueOf(settings.getTimeout());
        timeoutOutput.value = String.valueOf(settings.getTimeout());
        penalty.value = String.valueOf(settings.getPenalty());
        penaltyOutput.value = String.valueOf(settings.getPenalty());
        retries.value = String.valueOf(settings.getRetries());
        retriesOutput.value = String.valueOf(settings.getRetries());
        autoDice.checked = settings.isAutoDice();
        level.value = settings.getLevel().name().toLowerCase();
    }
}
