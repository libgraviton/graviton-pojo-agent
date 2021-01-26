package com.github.libgraviton.pojoagent.delegates;

import java.util.concurrent.Callable;

import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

public class IdSetter {
  public static void setRealId(String name) {

  }

  public static String getRealId(@SuperCall Callable<String> zuper) {
    try {
      return zuper.call();
    } catch (Throwable t) {
      System.out.println("could not call zuper on getRealId");
    }
    return null;
  }

  /*
  public static void setRealId(String name, @SuperCall Callable<?> zuper) {
    try {
      zuper.call();
    } catch (Throwable t) {
      System.out.println("could not call zuper on setRealId");
    }
  }



   */

}
