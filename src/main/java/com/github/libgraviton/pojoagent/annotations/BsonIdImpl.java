package com.github.libgraviton.pojoagent.annotations;

import java.lang.annotation.Annotation;
import org.bson.codecs.pojo.annotations.BsonId;

public class BsonIdImpl implements BsonId {

  @Override
  public Class<? extends Annotation> annotationType() {
    return BsonId.class;
  }
}
