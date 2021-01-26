package com.github.libgraviton.pojoagent.bytebuddy;

import com.github.libgraviton.pojoagent.annotations.BsonIdImpl;
import net.bytebuddy.agent.builder.AgentBuilder.Transformer;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.utility.JavaModule;

// https://github.com/FasterXML/jackson-modules-base/blob/f4e44c51913d11964e83f3db0a16d0fc8529d58b/mrbean/src/main/java/com/fasterxml/jackson/module/mrbean/BeanBuilder.java#L267

public class PojoTransformer implements Transformer {

  @Override
  public Builder<?> transform(Builder<?> builder, TypeDescription typeDescription,
      ClassLoader classLoader, JavaModule module) {

    System.out.println("***** GRAVITON POJO AGENT - instrumenting " + typeDescription.getCanonicalName());

    try {
      return builder
          .defineField("realId", String.class, Visibility.PUBLIC)
          .annotateField(new BsonIdImpl())
          .defineMethod("getRealId", String.class, Visibility.PUBLIC)
          .intercept(FieldAccessor.ofField("id"))
          .defineMethod("setRealId", Void.TYPE, Visibility.PUBLIC)
          .withParameters(String.class)
          .intercept(FieldAccessor.ofBeanProperty());

    } catch (Throwable t) {
      System.out.println("***** GRAVITON POJO AGENT - ERROR INSTRUMENTATION: " + t.getMessage());
    }

    return builder;
  }
}
