package com.ibicn.hr.util;

import com.ibicnCloud.util.*;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Number;
import jxl.write.*;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhonghang on 2016/8/10.
 */
public class ExcelUtil {
    private static Logger logger = Logger.getLogger(com.ibicnCloud.util.ExcelUtil.class);

    public static ArrayList<String[]> readExcelByPath(File file) {
        Sheet rs = null;
        ArrayList<String[]> list = new ArrayList<String[]>();
        try {
            rs = com.ibicnCloud.util.ExcelUtil.readExcel(file, rs);
        } catch (FileNotFoundException e) {
            System.out.println("没有找到该文件");
            return null;
        } catch (IOException e) {
            System.out.println("文件读写错误，请重试");
            return null;
        } catch (BiffException e) {
            System.out.println("文件属性有误，请重试");
            return null;
        }
        if (rs == null) {
            System.out.println("文件有误");
            return null;
        }
        int rsRows = rs.getRows();
        int rsColumns = rs.getColumns();
        for (int i = 0; i < rsRows; i++) {
            String[] conList = new String[rsColumns];
            for (int j = 0; j < rsColumns; j++) {
                Cell cell = rs.getCell(j, i);
                conList[j] = StringUtil.format(cell.getContents());
            }
            list.add(conList);
        }
        return list;
    }


    public static ArrayList<String[]> readExcelByPath(String path) {
        File file = new File(path);
        return readExcelByPath(file);
    }

    public static int createdExcel(String PATH, List list, String title, String[] rowsName) {
        return createdExcel(PATH, list, title, rowsName, "");
    }

    /**
     * @param PATH
     * @param list
     * @param title
     * @param rowsName
     * @param merged
     * @return
     */
    public static int createdExcel(String PATH, List list, String title, String[] rowsName, String merged) {
        try {
            File ex = new File(PATH);
            if (!ex.exists()) {
                if (!ex.createNewFile()) {
                    logger.error("创建路径失败！");
                    return 0;
                }
            }

            WritableWorkbook wbook = Workbook.createWorkbook(ex);
            WritableSheet wsheet = wbook.createSheet(title, 0);
            WritableFont wfont = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD);
            WritableCellFormat wcfFC = new WritableCellFormat(wfont);
            wcfFC.setBorder(Border.ALL, BorderLineStyle.THIN);
            wcfFC.setAlignment(Alignment.CENTRE);
            wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);

            int mergeds;
            for (mergeds = 0; mergeds < StringUtil.size(rowsName); ++mergeds) {
                wsheet.addCell(new Label(mergeds, 0, rowsName[mergeds], wcfFC));
                wsheet.setColumnView(mergeds, 12);
            }

            wfont = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD);
            wcfFC = new WritableCellFormat(wfont);
            wcfFC.setBorder(Border.ALL, BorderLineStyle.THIN);
            wcfFC.setWrap(true);

            for (mergeds = 0; mergeds < CollectionUtil.size(list); ++mergeds) {
                String[] i = (String[]) ((String[]) list.get(mergeds));

                for (int mergedsTwo = 0; mergedsTwo < StringUtil.size(i); ++mergedsTwo) {
                    if (StringUtil.size(i[mergedsTwo]) < 15 && StringUtil.isFloat(i[mergedsTwo])) {
                        wsheet.addCell(new Number(mergedsTwo, mergeds + 1, Double.parseDouble(i[mergedsTwo]), wcfFC));
                    } else {
                        wsheet.addCell(new Label(mergedsTwo, mergeds + 1, i[mergedsTwo], wcfFC));
                    }
                }

                if (mergeds % 20 == 0) {
                    ;
                }
            }
            if (!StringUtil.format(merged).equals("")) {
                String[] var14 = merged.split(";");

                for (int var15 = 0; var15 < StringUtil.size(var14); ++var15) {
                    String[] var16 = var14[var15].split(",");
                    wsheet.mergeCells(StringUtil.parseInt(var16[0]), StringUtil.parseInt(var16[1]), StringUtil.parseInt(var16[2]), StringUtil.parseInt(var16[3]));
                }
            }

            wbook.write();
            wbook.close();
            return 1;
        } catch (Exception var13) {
            logger.error("创建excel文件错误！", var13);
            return 0;
        }
    }
}
