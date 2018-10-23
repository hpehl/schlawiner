package org.jboss.schlawiner.client.resources;

import java.util.Date;

import com.google.gwt.i18n.shared.DateTimeFormat;

public final class Format {

    private static final DateTimeFormat TIME = DateTimeFormat.getFormat("HH:mm");

    public static String time(Date date) {
        return date != null ? TIME.format(date) : "n/a";
    }
}
