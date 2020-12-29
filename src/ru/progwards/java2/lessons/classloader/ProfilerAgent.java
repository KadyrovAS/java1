package ru.progwards.java2.lessons.classloader;

import java.lang.instrument.Instrumentation;

public class ProfilerAgent {
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        instrumentation.addTransformer(new ProfilerTransformer());
    }
}
