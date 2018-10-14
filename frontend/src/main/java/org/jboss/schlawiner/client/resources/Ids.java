package org.jboss.schlawiner.client.resources;

import java.util.List;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import static com.google.common.base.CharMatcher.inRange;
import static com.google.common.base.Strings.emptyToNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.stream.Collectors.joining;
import static java.util.stream.StreamSupport.stream;

public interface Ids {

    // ------------------------------------------------------ ids (a-z)
    // Don't compose IDs with build(), otherwise they cannot be used in annotations.
    String SETTINGS_STORAGE = "org.jboss.schlawiner.settings";

    /**
     * Turns a label which can contain whitespace and upper/lower case characters into an all lowercase id separated
     * by "-".
     */
    static String asId(String text) {
        Iterable<String> parts = Splitter
            .on(CharMatcher.whitespace().or(CharMatcher.is('-')))
            .omitEmptyStrings()
            .trimResults()
            .split(text);
        return stream(parts.spliterator(), false)
            .map(String::toLowerCase)
            .map(inRange('a', 'z').or(inRange('0', '9'))::retainFrom)
            .collect(joining("-"));
    }

    static String build(String id, String... additionalIds) {
        return build(id, '-', additionalIds);
    }

    static String build(String id, char separator, String... additionalIds) {
        if (emptyToNull(id) == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        List<String> ids = Lists.newArrayList(id);
        if (additionalIds != null) {
            for (String additionalId : additionalIds) {
                if (!isNullOrEmpty(additionalId)) {
                    ids.add(additionalId);
                }
            }
        }
        return ids.stream().map(Ids::asId).collect(joining(String.valueOf(separator)));
    }
}
