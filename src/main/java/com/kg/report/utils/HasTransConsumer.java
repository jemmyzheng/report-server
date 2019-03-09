package com.kg.report.utils;

/**
 * 实现该接口的VO类有 特殊的action 用来辅助转换
 * @param <P> 目标类型
 * @param <V> 自身类型
 */
public interface HasTransConsumer<P, V> {
  /**
   * PO 2 VO
   * @param source 一般是TO类
   * @param self 一般是VO类
   * @return
   */
  void transAction(P source, V self);

  /**
   * VO 2 PO
   * @param self 一般是VO类
   * @param target 一般是PO类
   * @return
   */
  void reTransAction(V self, P target);
}
