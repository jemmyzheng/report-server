package com.kg.report.utils;

import java.io.Serializable;

public class Pagination implements Serializable {
  private static final long serialVersionUID = 3781856693922774647L;
  private int current; // 当前页
  private int pageSize = 20; // 每页的数量
  private boolean pagination;

  public boolean isPagination() {
    return pagination;
  }

  public void setPagination(boolean pagination) {
    this.pagination = pagination;
  }

  public Integer getCurrent() {
    return current;
  }

  public void setCurrent(Integer current) {
    this.current = current;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
}
