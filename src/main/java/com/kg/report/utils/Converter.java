package com.kg.report.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * 基于 Spring BeanUtils的 扩展
 */
public class Converter {

  private static <T> void doTransConsumer(Object source, T targetObj) {
    if (source instanceof HasTransConsumer) {
      ((HasTransConsumer) source).reTransAction(source, targetObj);
    }
    if (targetObj instanceof HasTransConsumer) {
      ((HasTransConsumer) targetObj).transAction(source, targetObj);
    }
  }

  public static <T> T copyProperties(Object source, Class<T> targetClass) {
    try {
      T targetObj = targetClass.newInstance();
      BeanUtils.copyProperties(source, targetObj);
      doTransConsumer(source, targetObj);
      return targetObj;
    } catch (Exception ex) {
      return null;
    }
  }
  public static <T> T copyProperties(Object source, Class<T> targetClass, String... ignoreProperties) {
    try {
      T targetObj = targetClass.newInstance();
      BeanUtils.copyProperties(source, targetObj, ignoreProperties);
      doTransConsumer(source, targetObj);
      return targetObj;
    } catch (Exception ex) {
      return null;
    }
  }

  public static <T, E> T copyProperties(E source, Class<T> targetClass, BiConsumer<E,T> action, String... ignoreProperties) {
    try {
      T targetObj = copyProperties(source, targetClass, ignoreProperties);
      action.accept(source, targetObj);
      return targetObj;
    } catch (Exception ex) {
      return null;
    }
  }

  public static <T> List<T> copyList(List sources, Class<T> targetClass) {
    if (sources == null) {
      return null;
    }
    List<T> targets = new ArrayList<>();
    sources.forEach(item -> {
      targets.add(copyProperties(item,targetClass));
    });
    return targets;
  }

  public static <T, E> List<T> copyList(List<E> sources, Class<T> targetClass, String... ignoreProperties) {
    if (sources == null) {
      return null;
    }
    List<T> targets = new ArrayList<>();
    sources.forEach(item -> {
      targets.add(copyProperties(item, targetClass, ignoreProperties));
    });
    return targets;
  }
  public static <T, E> List<T> copyList(List<E> sources, Class<T> targetClass, BiConsumer<E,T> action, String... ignoreProperties) {
    if (sources == null) {
      return null;
    }
    List<T> targets = new ArrayList<>();
    sources.forEach(item -> {
      T target = copyProperties(item, targetClass, ignoreProperties);
      action.accept(item, target);
      targets.add(target);
    });
    return targets;
  }
}
