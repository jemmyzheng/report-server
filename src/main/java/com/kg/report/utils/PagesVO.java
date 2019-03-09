package com.kg.report.utils;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PagesVO<E> implements Serializable {
  private static final long serialVersionUID = 3274281598095105909L;

  // 当前页
  private int current = 1;
  // 每页条数
  private int pageSize = 20;
  // 数据总条数
  private long total;
  // 数据总页数
  private int pages = 1;
  // 数据集合
  private List<E> dataList;

  public PagesVO() {
    this.dataList = new ArrayList<>();
  }

  public PagesVO(int current, int pageSize, long total, int pages, List<E> dataList) {
    this.current = current;
    this.pageSize = pageSize;
    this.total = total;
    this.pages = pages;
    this.dataList = dataList;
  }

  public PagesVO(Page pageList, List<E> dataList) {
    this.pageSize = pageList.getPageSize();
    this.total = pageList.getTotal();
    this.current = pageList.getPageNum();
    this.pages = pageList.getPages();
    this.dataList = dataList;
  }

  public int getCurrent() {
    return current;
  }

  public void setCurrent(int current) {
    this.current = current;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

  public List<E> getDataList() {
    return dataList;
  }

  public void setDataList(List<E> dataList) {
    this.dataList = dataList;
  }
}
