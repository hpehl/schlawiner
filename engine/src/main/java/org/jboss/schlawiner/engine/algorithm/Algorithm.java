package org.jboss.schlawiner.engine.algorithm;

public interface Algorithm {

    Solutions compute(int a, int b, int c, int target);

    String getName();
}
