package org.jboss.schlawiner.client.settings;

import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.HTMLOutputElement;
import elemental2.dom.HTMLSelectElement;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.gwt.elemento.template.DataElement;
import org.jboss.gwt.elemento.template.Templated;

@Templated
abstract class SettingsView implements IsElement<HTMLElement> {

    static SettingsView create() {
        return new Templated_SettingsView();
    }

    @DataElement HTMLInputElement name;
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
}
