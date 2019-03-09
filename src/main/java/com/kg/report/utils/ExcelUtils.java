package com.kg.report.utils;

import com.kg.report.model.vo.FieldWriteVO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
  public static SXSSFWorkbook createOOXMLExcelWorkBook(String title, Map<String, List<FieldWriteVO>> data, Date[] dateRange) {
    boolean isOneDay = dateRange.length == 1 ? true : org.apache.commons.lang3.time.DateUtils.isSameDay(dateRange[0], dateRange[1]);
    String reportName = title + (isOneDay ? "日报表" : "业务数据报表");

    SXSSFWorkbook workbook = new SXSSFWorkbook();

    Font titleFont = workbook.createFont();
    titleFont.setFontHeightInPoints((short) 24); // 字体高度
    titleFont.setColor(Font.COLOR_NORMAL); // 字体颜色
    titleFont.setFontName("仿宋-GB2312"); // 字体
    titleFont.setBold(true); // 宽度

    Font subTitleFont = workbook.createFont();
    subTitleFont.setFontHeightInPoints((short) 14); // 字体高度
    subTitleFont.setColor(Font.COLOR_NORMAL); // 字体颜色
    subTitleFont.setFontName("仿宋-GB2312"); // 字体

    Font headerFont = workbook.createFont();
    headerFont.setFontHeightInPoints((short) 12); // 字体高度
    headerFont.setColor(Font.COLOR_NORMAL); // 字体颜色
    headerFont.setFontName("仿宋-GB2312"); // 字体

    Font contentFont = workbook.createFont();
    contentFont.setFontHeightInPoints((short) 11); // 字体高度
    contentFont.setColor(Font.COLOR_NORMAL); // 字体颜色
    contentFont.setFontName("仿宋-GB2312"); // 字体

    CellStyle titleCellStyle = workbook.createCellStyle();
    titleCellStyle.setFont(titleFont);
    titleCellStyle.setAlignment(HorizontalAlignment.CENTER); // 水平布局：居中
    titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

    CellStyle subTitleCellStyle = workbook.createCellStyle();
    subTitleCellStyle.setFont(subTitleFont);
    subTitleCellStyle.setAlignment(HorizontalAlignment.CENTER); // 水平布局：居中
    subTitleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

    CellStyle headerCellStyle = workbook.createCellStyle();
    headerCellStyle.setFont(headerFont);
    headerCellStyle.setAlignment(HorizontalAlignment.CENTER); // 水平布局：居中
    headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    headerCellStyle.setWrapText(true);

    CellStyle contentCellStyle = workbook.createCellStyle();
    contentCellStyle.setFont(contentFont);
    contentCellStyle.setAlignment(HorizontalAlignment.CENTER); // 水平布局：居中
    contentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    workbook.setCompressTempFiles(true);
    data.forEach((airportName, fieldWriteVOS) -> {
      SXSSFSheet sheet = workbook.createSheet(airportName);
      String subTitle = " " + airportName;
      if (isOneDay) {
        subTitle = com.kg.report.utils.DateUtils.formatDateHuman(dateRange[0]) + subTitle;
      } else {
        subTitle = com.kg.report.utils.DateUtils.formatDateHuman(dateRange[0]) + "-" + com.kg.report.utils.DateUtils.formatDateHuman(dateRange[1]) + subTitle;
      }
      Row rowTile = sheet.createRow(0);
      rowTile.setHeightInPoints((short) 64);
      Cell titleCell = rowTile.createCell(0);
      titleCell.setCellValue(reportName);
      titleCell.setCellStyle(titleCellStyle);

      Row rowSubTile = sheet.createRow(1);
      rowSubTile.setHeightInPoints((short) 20);
      Cell subTitleCell = rowSubTile.createCell(0);
      subTitleCell.setCellValue(subTitle);
      subTitleCell.setCellStyle(subTitleCellStyle);

      // title
      sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));
      // subtitle
      sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 13));

      // content
      Row headerRow = sheet.createRow(2);
      Row contentRow = sheet.createRow(3);

      for (int i = 0; i < fieldWriteVOS.size(); i++) {
        int step = i % 13;
        if (step == 0) {
          if (i != 0) {
            headerRow = sheet.createRow(headerRow.getRowNum() + 2);
            contentRow = sheet.createRow(contentRow.getRowNum() + 2);
          }
          headerRow.setHeightInPoints(74);
          Cell headerCell = headerRow.createCell(0);
          headerCell.setCellStyle(headerCellStyle);
          headerCell.setCellValue("项目");
          Cell contentCell = contentRow.createCell(0);
          contentCell.setCellStyle(contentCellStyle);
          contentCell.setCellValue("数值");
        }
        Cell headerCell = headerRow.createCell(step + 1);
        headerCell.setCellStyle(headerCellStyle);
        headerCell.setCellValue(fieldWriteVOS.get(i).getFieldName());
        Cell contentCell = contentRow.createCell(step + 1);
        contentCell.setCellStyle(contentCellStyle);
        Object value = fieldWriteVOS.get(i).getValue();
        if (value instanceof Integer) {
          contentCell.setCellValue((Integer) value);
          contentCell.setCellType(CellType.NUMERIC);
        } else if (value instanceof Double) {
          contentCell.setCellType(CellType.NUMERIC);
          contentCell.setCellValue((Double) value);
        } else {
          contentCell.setCellValue((String) value);
        }
      }
    });
    return workbook;
  }

  public static byte[] generateExcelFile(String title, Map<String, List<FieldWriteVO>> data, Date[] dateRange) throws IOException {
    SXSSFWorkbook workbook = createOOXMLExcelWorkBook(title, data, dateRange);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    workbook.write(byteArrayOutputStream);
    byte[] res = byteArrayOutputStream.toByteArray();
    byteArrayOutputStream.close();
    workbook.dispose();
    return res;
  }
}
