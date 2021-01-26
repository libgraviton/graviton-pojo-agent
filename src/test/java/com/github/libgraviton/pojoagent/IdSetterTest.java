package com.github.libgraviton.pojoagent;

import com.github.libgraviton.pojoagent.annotations.BsonIdImpl;
import com.github.libgraviton.pojoagent.bytebuddy.PojoTransformer;
import com.github.libgraviton.pojoagent.pojo.TestPojo;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import javax.management.openmbean.SimpleType;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.loading.ByteArrayClassLoader;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.matcher.ElementMatchers;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IdSetterTest {

  private ClassLoader simpleTypeLoader;

  @Before
  public void setUp() throws Exception {
    simpleTypeLoader = new ByteArrayClassLoader(ClassLoadingStrategy.BOOTSTRAP_LOADER,
        ClassFileLocator.ForClassLoader.readToNames(TestPojo.class),
        ByteArrayClassLoader.PersistenceHandler.MANIFEST);
  }

  @Test
  public void testSetter() throws Exception{
    MatcherAssert.assertThat(ByteBuddyAgent.install(), CoreMatchers.instanceOf(Instrumentation.class));

    new AgentBuilder.Default()
        .type(ElementMatchers.named("com.github.libgraviton.pojoagent.pojo.TestPojo"))
        .transform(new PojoTransformer())
        .installOnByteBuddyAgent();

    Class<?> type = simpleTypeLoader.loadClass(TestPojo.class.getName());
    Field[] fields = type.getDeclaredFields();

    Assert.assertEquals(2, fields.length);

    Object instance = type.getDeclaredConstructor().newInstance();
    type.getDeclaredMethod("setId", String.class).invoke(instance, "HANZ");
    type.getDeclaredMethod("setRealId", String.class).invoke(instance, "HANS");

    Assert.assertEquals(
        "HANZ",
        type.getDeclaredMethod("getRealId").invoke(instance)
    );
    Assert.assertEquals(
        "HANZ",
        type.getDeclaredMethod("getId").invoke(instance)
    );
  }

}
