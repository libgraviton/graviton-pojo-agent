package com.github.libgraviton.pojoagent;

import com.github.libgraviton.pojoagent.bytebuddy.PojoTransformer;
import java.lang.instrument.Instrumentation;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.matcher.*;
import static net.bytebuddy.matcher.ElementMatchers.*;

public class Agent {
  public static void premain(String arg, Instrumentation instrumentation) {
    System.out.println("***** GRAVITON POJO AGENT ACTIVE");

    new AgentBuilder.Default()
        .type(
            ElementMatchers
                .nameStartsWith("com.github.libgraviton.gdk.gravitondyn")
                .and(not(ElementMatchers.hasSuperType(ElementMatchers.nameContains("GravitonBase"))))
        )
        .transform(new PojoTransformer())
        .installOn(instrumentation);
  }
}
