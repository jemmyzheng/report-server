package com.kg.report;

import com.kg.report.model.vo.FieldWriteVO;
import com.kg.report.utils.ExcelUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class UtilsTest {
  @Test
  public void test() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.setFirstDayOfWeek(Calendar.MONDAY);
    int month = calendar.get(Calendar.MONTH) + 1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int mints = calendar.get(Calendar.MINUTE);
    System.out.println(month);
    System.out.println(day);
    System.out.println(week);
    System.out.println(hour);
    System.out.println(mints);
    System.out.println(Integer.valueOf("01"));
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    System.out.println(calendar.getTime());
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    System.out.println(calendar.getTime());
    calendar.add(Calendar.DATE, -(week - 1));
    System.out.println(calendar.getTime());
    calendar.add(Calendar.DATE, 6);
    System.out.println(calendar.getTime());

    calendar.add(Calendar.MONTH, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
    System.out.println(calendar.getTime());

    //获取当前月最后一天
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    System.out.println(calendar.getTime());

    System.out.println("s".split("-").length);
    System.out.println("s-s".split("-").length);
  }

  @Test
  public void test2() {
    System.out.println(Double.valueOf("53123.25") + 234234.23);
  }

  private List<FieldWriteVO> getFieldTestData(int length) {
    List<FieldWriteVO> result = new ArrayList<>();
    for (int i = 1; i < length; i++) {
      FieldWriteVO writeVO = new FieldWriteVO(i + 1, "字段名称 (" + i + ")", i % 2 == 0 ? i * 10 : i * 10.10);
      result.add(writeVO);
    }
    return result;
  }

  private Map<String, List<FieldWriteVO>> getFullTestData() {
    Map<String, List<FieldWriteVO>> result = new HashMap<>();
    result.put("白云机场", getFieldTestData(41));
    result.put("虹桥机场", getFieldTestData(25));
    result.put("萧山机场", getFieldTestData(30));
    result.put("长沙机场", getFieldTestData(20));
    result.put("成都机场", getFieldTestData(20));
    return result;
  }

  @Test
  public void excel() throws IOException {
    Date[] dates = {new Date()};
    byte[] res = ExcelUtils.generateExcelFile("湖南空港实业股份有限公司", getFullTestData(), dates);
    FileOutputStream fos = new FileOutputStream("/Users/zhengwei/Desktop/text.xlsx");
    fos.write(res);
    fos.close();
  }
}
