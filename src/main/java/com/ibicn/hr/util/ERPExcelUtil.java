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
public class ERPExcelUtil {
    private static Logger logger = Logger.getLogger(ExcelUtil.class);

    public static ArrayList<String[]> readExcelByPath(File file) {
        Sheet rs = null;
        ArrayList<String[]> list = new ArrayList<String[]>();
        try {
            rs = ExcelUtil.readExcel(file, rs);
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

    public static void readKaipiao() throws ParseException {
        File file = new File("d:/a.xls");
        ArrayList<String[]> list = ERPExcelUtil.readExcelByPath(file);

        file = new File("d:/b.xls");

        ArrayList<String[]> list2 = ERPExcelUtil.readExcelByPath(file);

        List<String> result = new ArrayList<>();

        //开始从前往后匹配
        for (String[] content : list2) {
            chongZhang(content, list, result);
        }
        try {
            FileUtil.writeListToFile("d:/ab.txt", result);
        } catch (UtilException e) {
            logger.error(e);
        }
    }

    /**
     * 冲抵入库
     *
     * @param content 要冲抵的发货记录
     * @param list    被冲抵的入库记录
     * @param result  结果集
     */
    public static void chongZhang(String[] content, ArrayList<String[]> list, List<String> result) {
        for (int i = 0; i < list.size(); i++) {
            String[] strings = list.get(i);
            if (strings[6].equals(content[2])) {
                //开始冲抵
                BigDecimal nengchongdiShuliang = new BigDecimal(strings[9]);
                BigDecimal xuyaoChongdiShuliang = new BigDecimal(content[3]);
                if (xuyaoChongdiShuliang.compareTo(BigDecimal.ZERO) == 0) {
                    return;
                }
                BigDecimal chongdiShuliang = BigDecimal.ZERO;
                if (nengchongdiShuliang.compareTo(xuyaoChongdiShuliang) >= 0) {
                    chongdiShuliang = xuyaoChongdiShuliang;
                } else {
                    chongdiShuliang = nengchongdiShuliang;
                }
                result.add(strings[0] + "@"
                        + strings[1] + "@"
                        + strings[2] + "@"
                        + strings[3] + "@"
                        + strings[4] + "@"
                        + strings[5] + "@"
                        + strings[6] + "@"
                        + strings[7] + "@"
                        + strings[8] + "@"
                        + strings[9] + "@"
                        + strings[10] + "@"
                        + strings[11] + "@"
                        + strings[12] + "@"
                        + chongdiShuliang + "@"
                        + content[0] + "@"
                        + content[1] + "@"
                        + content[2] + "@"
                        + content[3] + "@"
                        + content[4] + "@"
                        + content[5] + "@"
                        + content[6] + "@"
                        + content[7] + "@"
                        + content[8]
                );

                //要将已经冲抵的数量减掉
                strings[9] = BigDecimalUtil.formatBig((new BigDecimal(strings[9])).subtract(chongdiShuliang), 4);
                content[3] = BigDecimalUtil.formatBig((new BigDecimal(content[3])).subtract(chongdiShuliang), 4);
                if ((new BigDecimal(strings[9])).compareTo(BigDecimal.ZERO) == 0) {
                    list.remove(i);
                    i--;
                } else {
                    list.set(i, strings);
                }
            } else {
                continue;
            }

        }
        //如果循环完所有的数据，都无法冲抵掉本次发货，则把剩余发货展示出来；
        if (!content[3].equals("0")) {
            result.add("@@@@@@@@@@@@@@" + content[0] + "@"
                    + content[1] + "@"
                    + content[2] + "@"
                    + content[3] + "@"
                    + content[4] + "@"
                    + content[5] + "@"
                    + content[6] + "@"
                    + content[7] + "@"
                    + content[8]
            );
        }
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

    public static void main(String[] args) throws ParseException {
        readKaipiao();
    }
}
