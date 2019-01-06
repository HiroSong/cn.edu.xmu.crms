package cn.edu.xmu.crms.util.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

/**
 * @author SongLingbing
 * @date 2018/12/25 20:41
 */
@Component
public class ExcelUtil {

    private static final String XLS_TYPE = "xls";
    private static final String XLSX_TYPE = "xlsx";
    /**
     * 默认的开始读取的行位置为第一行（索引值为0）
     */
    private final static int READ_START_POS = 0;

    /**
     * 默认结束读取的行位置为最后一行（索引值=0，用负数来表示倒数第n行）
     */
    private final static int READ_END_POS = 0;

    /**
     * 默认Excel内容的开始比较列位置为第一列（索引值为0）
     */
    private final static int COMPARE_POS = 0;

    /**
     * 默认多文件合并的时需要做内容比较（相同的内容不重复出现）
     */
    private final static boolean NEED_COMPARE = true;

    /**
     * 默认多文件合并的新文件遇到名称重复时，进行覆盖
     */
    private final static boolean NEED_OVERWRITE = true;

    /**
     * 默认只操作一个sheet
     */
    private final static boolean ONLY_ONE_SHEET = true;

    /**
     * 默认读取第一个sheet中（只有当ONLY_ONE_SHEET = true时有效）
     */
    private final static int SELECTED_SHEET = 0;

    /**
     * 默认从第一个sheet开始读取（索引值为0）
     */
    private final static int READ_START_SHEET = 0;

    /**
     * 默认在最后一个sheet结束读取（索引值=0，用负数来表示倒数第n行）
     */
    private final static int READ_END_SHEET = 0;

    /**
     * 默认打印各种信息
     */
    private final static boolean PRINT_MSG = true;


    /**
     * Excel文件路径
     */
    private String excelPath;

    /**
     * 设定开始读取的位置，默认为0
     */
    private int startReadPos = READ_START_POS;

    /**
     * 设定结束读取的位置，默认为0，用负数来表示倒数第n行
     */
    private int endReadPos = READ_END_POS;

    /**
     * 设定开始比较的列位置，默认为0
     */
    private int comparePos = COMPARE_POS;

    /**
     * 设定汇总的文件是否需要替换，默认为true
     */
    private boolean isOverWrite = NEED_OVERWRITE;

    /**
     * 设定是否需要比较，默认为true(仅当不覆写目标内容是有效，即isOverWrite=false时有效)
     */
    private boolean isNeedCompare = NEED_COMPARE;

    /**
     * 设定是否只操作第一个sheet
     */
    private boolean onlyReadOneSheet = ONLY_ONE_SHEET;

    /**
     * 设定操作的sheet在索引值
     */
    private int selectedSheetIdx = SELECTED_SHEET;

    /**
     * 设定操作的sheet的名称
     */
    private String selectedSheetName = "";

    /**
     * 设定开始读取的sheet，默认为0
     */
    private int startSheetIdx = READ_START_SHEET;

    /**
     * 设定结束读取的sheet，默认为0，用负数来表示倒数第n行
     */
    private int endSheetIdx = READ_END_SHEET;

    /**
     * 设定是否打印消息
     */
    private boolean printMsg = PRINT_MSG;

    public ExcelUtil() {

    }

    public ExcelUtil(String excelPath) {
        this.excelPath = excelPath;
    }

    /**
     * 还原设定（其实是重新new一个新的对象并返回）
     */
    public ExcelUtil restoreSettings() {
        ExcelUtil instance = new ExcelUtil(this.excelPath);
        return instance;
    }

    /**
     * 自动根据文件扩展名，调用对应的读取方法
     *
     * @throws IOException
     * @Title: writeExcel
     */
    public List<Row> readExcel() throws IOException {
        return readExcel(this.excelPath);
    }

    /**
     * 自动根据文件扩展名，调用对应的读取方法
     *
     * @param xlsPath
     * @throws IOException
     * @Title: writeExcel
     */
    public List<Row> readExcel(String xlsPath) throws IOException {

        if ("".equals(xlsPath)) {
            throw new IOException("文件路径不能为空！");
        } else {
            File file = new File(xlsPath);
            if (!file.exists()) {
                throw new IOException("文件不存在！");
            }
        }

        String ext = xlsPath.substring(xlsPath.lastIndexOf(".") + 1);

        try {
            if (XLS_TYPE.equals(ext)) {
                return readExcelXls(xlsPath);
            } else if (XLSX_TYPE.equals(ext)) {
                return readExcelXlsx(xlsPath);
            } else {
                out("您要操作的文件没有扩展名，正在尝试以xls方式读取...");
                try {
                    return readExcelXls(xlsPath);
                } catch (IOException e1) {
                    out("尝试以xls方式读取，结果失败！，正在尝试以xlsx方式读取...");
                    try {
                        return readExcelXlsx(xlsPath);
                    } catch (IOException e2) {
                        out("尝试以xls方式读取，结果失败！\n请您确保您的文件是Excel文件，并且无损，然后再试。");
                        throw e2;
                    }
                }
            }
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 自动根据文件扩展名，调用对应的写入方法
     *
     * @param rowList
     * @throws IOException
     * @Title: writeExcel
     * @Date : 2014-9-11 下午01:50:38
     */
    public void writeExcel(List<Row> rowList) throws IOException {
        writeExcel(rowList, excelPath);
    }

    /**
     * 自动根据文件扩展名，调用对应的写入方法
     *
     * @param rowList
     * @param xlsPath
     * @throws IOException
     * @Title: writeExcel
     */
    public void writeExcel(List<Row> rowList, String xlsPath) throws IOException {

        if ("".equals(xlsPath)) {
            throw new IOException("文件路径不能为空！");
        }

        String ext = xlsPath.substring(xlsPath.lastIndexOf(".") + 1);

        try {
            if (XLS_TYPE.equals(ext)) {
                writeExcelXls(rowList, xlsPath);
            } else if (XLSX_TYPE.equals(ext)) {
                writeExcelXlsx(rowList, xlsPath);
            } else {
                out("您要操作的文件没有扩展名，正在尝试以xls方式写入...");
                try {
                    writeExcelXls(rowList, xlsPath);
                } catch (IOException e1) {
                    out("尝试以xls方式写入，结果失败！，正在尝试以xlsx方式读取...");
                    try {
                        writeExcelXlsx(rowList, xlsPath);
                    } catch (IOException e2) {
                        out("尝试以xls方式写入，结果失败！\n请您确保您的文件是Excel文件，并且无损，然后再试。");
                        throw e2;
                    }
                }
            }
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 修改Excel（97-03版，xls格式）
     *
     * @param rowList
     * @param distXlsPath
     * @throws IOException
     * @Title: writeExcelXls
     */
    public void writeExcelXls(List<Row> rowList, String distXlsPath) throws IOException {
        writeExcelXls(rowList, excelPath, distXlsPath);
    }

    /**
     * 修改Excel（97-03版，xls格式）
     *
     * @param rowList
     * @param srcXlsPath
     * @param distXlsPath
     * @throws IOException
     * @Title: writeExcelXls
     */
    public void writeExcelXls(List<Row> rowList, String srcXlsPath, String distXlsPath) throws IOException {
        if (distXlsPath == null || "".equals(distXlsPath)) {
            out("文件路径不能为空");
            throw new IOException("文件路径不能为空");
        }
        if (srcXlsPath == null || "".equals(srcXlsPath)) {
            out("文件路径不能为空");
            throw new IOException("文件路径不能为空");
        }

        if (rowList == null || rowList.size() == 0) {
            out("文档为空");
            return;
        }

        try {
            HSSFWorkbook wb = null;

            File file = new File(distXlsPath);
            if (file.exists()) {
                if (isOverWrite) {
                    file.delete();
                    wb = new HSSFWorkbook(new FileInputStream(srcXlsPath));
                } else {
                    wb = new HSSFWorkbook(new FileInputStream(file));
                }
            } else {
                wb = new HSSFWorkbook(new FileInputStream(srcXlsPath));
            }

            writeExcel(wb, rowList, distXlsPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改Excel（97-03版，xls格式）
     *
     * @param rowList
     * @param distXlsPath
     * @throws IOException
     * @Title: writeExcelXls
     */
    public void writeExcelXlsx(List<Row> rowList, String distXlsPath) throws IOException {
        writeExcelXls(rowList, excelPath, distXlsPath);
    }

    /**
     * 修改Excel（2007版，xlsx格式）
     *
     * @param rowList
     * @param srcXlsPath
     * @param distXlsPath
     * @throws IOException
     * @Title: writeExcelXlsx
     */
    public void writeExcelXlsx(List<Row> rowList, String srcXlsPath, String distXlsPath) throws IOException {

        if (distXlsPath == null || "".equals(distXlsPath) || srcXlsPath == null || "".equals(srcXlsPath)) {
            out("文件路径不能为空");
            throw new IOException("文件路径不能为空");
        }

        if (rowList == null || rowList.size() == 0) {
            out("文档为空");
            return;
        }

        try {
            XSSFWorkbook wb = null;

            File file = new File(distXlsPath);
            if (file.exists()) {
                if (isOverWrite) {
                    file.delete();
                    wb = new XSSFWorkbook(new FileInputStream(srcXlsPath));
                } else {
                    wb = new XSSFWorkbook(new FileInputStream(file));
                }
            } else {
                wb = new XSSFWorkbook(new FileInputStream(srcXlsPath));
            }
            writeExcel(wb, rowList, distXlsPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * //读取Excel 2007版，xlsx格式
     *
     * @return
     * @throws IOException
     * @Title: readExcelXlsx
     */
    public List<Row> readExcelXlsx() throws IOException {
        return readExcelXlsx(excelPath);
    }

    /**
     * //读取Excel 2007版，xlsx格式
     *
     * @return
     * @throws Exception
     * @Title: readExcelXlsx
     */
    public List<Row> readExcelXlsx(String xlsPath) throws IOException {
        File file = new File(xlsPath);
        if (!file.exists()) {
            throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
        }

        XSSFWorkbook wb = null;
        List<Row> rowList = new ArrayList<Row>();
        try {
            FileInputStream fis = new FileInputStream(file);
            wb = new XSSFWorkbook(fis);
            rowList = readExcel(wb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rowList;
    }

    /***
     * 读取Excel(97-03版，xls格式)
     *
     * @throws IOException
     *
     * @Title: readExcel
     */
    public List<Row> readExcelXls() throws IOException {
        return readExcelXls(excelPath);
    }

    /***
     * 读取Excel(97-03版，xls格式)
     *
     * @throws Exception
     *
     * @Title: readExcel
     */
    public List<Row> readExcelXls(String xlsPath) throws IOException {

        File file = new File(xlsPath);
        if (!file.exists()) {
            throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
        }

        HSSFWorkbook wb = null;
        List<Row> rowList = new ArrayList<>();

        try {
            wb = new HSSFWorkbook(new FileInputStream(file));
            rowList = readExcel(wb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rowList;
    }

    /***
     * 读取单元格的值
     *
     * @Title: getCellValue
     * @param cell
     */
    public String getCellValue(Cell cell) {
        Object result = "";
        if (cell != null) {
            result = cell.getStringCellValue();
        }
        return result.toString().replace((char)160, ' ').trim();
    }

    /**
     * 通用读取Excel
     *
     * @param wb
     * @Title: readExcel
     */
    private List<Row> readExcel(Workbook wb) {
        List<Row> rowList = new ArrayList<>();

        int sheetCount = 1;

        Sheet sheet = null;
        if (onlyReadOneSheet) {
            sheet = "".equals(selectedSheetName) ? wb.getSheetAt(selectedSheetIdx) : wb.getSheet(selectedSheetName);
        } else {
            sheetCount = wb.getNumberOfSheets();
        }

        for (int t = startSheetIdx; t < sheetCount + endSheetIdx; t++) {
            if (!onlyReadOneSheet) {
                sheet = wb.getSheetAt(t);
            }

            int lastRowNum = sheet.getLastRowNum();

            Row row = null;
            for (int i = startReadPos; i <= lastRowNum + endReadPos; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    rowList.add(row);
                }
            }
        }
        return rowList;
    }

    /**
     * 修改Excel，并另存为
     *
     * @param wb
     * @param rowList
     * @param xlsPath
     * @Title: WriteExcel
     */
    private void writeExcel(Workbook wb, List<Row> rowList, String xlsPath) {

        if (wb == null) {
            out("操作文档不能为空！");
            return;
        }

        Sheet sheet = wb.getSheetAt(0);

        int lastRowNum = isOverWrite ? startReadPos : sheet.getLastRowNum() + 1;
        int t = 0;
        out("要添加的数据总条数为：" + rowList.size());
        for (Row row : rowList) {
            if (row == null) {
                continue;
            }
            int pos = findInExcel(sheet, row);
            Row r = null;
            if (pos >= 0) {
                sheet.removeRow(sheet.getRow(pos));
                r = sheet.createRow(pos);
            } else {
                r = sheet.createRow(lastRowNum + t++);
            }

            CellStyle newstyle = wb.createCellStyle();

            for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
                Cell cell = r.createCell(i);
                cell.setCellValue(getCellValue(row.getCell(i)));
                if (row.getCell(i) == null) {
                    continue;
                }
                copyCellStyle(row.getCell(i).getCellStyle(), newstyle);
                cell.setCellStyle(newstyle);
            }
        }
        out("其中检测到重复条数为:" + (rowList.size() - t) + " ，追加条数为：" + t);

        setMergedRegion(sheet);

        try {
            FileOutputStream outputStream = new FileOutputStream(xlsPath);
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            out("写入Excel时发生错误！ ");
            e.printStackTrace();
        }
    }

    /**
     * 查找某行数据是否在Excel表中存在，返回行数。
     *
     * @param sheet
     * @param row
     * @return
     * @Title: findInExcel
     */
    private int findInExcel(Sheet sheet, Row row) {
        int pos = -1;

        try {
            if (isOverWrite || !isNeedCompare) {
                return pos;
            }
            for (int i = startReadPos; i <= sheet.getLastRowNum() + endReadPos; i++) {
                Row r = sheet.getRow(i);
                if (r != null && row != null) {
                    String v1 = getCellValue(r.getCell(comparePos));
                    String v2 = getCellValue(row.getCell(comparePos));
                    if (v1.equals(v2)) {
                        pos = i;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pos;
    }

    /**
     * 复制一个单元格样式到目的单元格样式
     *
     * @param fromStyle
     * @param toStyle
     */
    public static void copyCellStyle(CellStyle fromStyle, CellStyle toStyle) {
        toStyle.setTopBorderColor(fromStyle.getTopBorderColor());
        toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());
        toStyle.setRightBorderColor(fromStyle.getRightBorderColor());
        toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());

        toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());
        toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());

        toStyle.setDataFormat(fromStyle.getDataFormat());
        toStyle.setHidden(fromStyle.getHidden());
        toStyle.setIndention(fromStyle.getIndention());
        toStyle.setLocked(fromStyle.getLocked());
        toStyle.setRotation(fromStyle.getRotation());
        toStyle.setWrapText(fromStyle.getWrapText());

    }

    /**
     * 获取合并单元格的值
     *
     * @param sheet
     * @return
     */
    public void setMergedRegion(Sheet sheet) {
        int sheetMergeCount = sheet.getNumMergedRegions();

        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstRow = ca.getFirstRow();
            if (startReadPos - 1 > firstRow) {
                continue;
            }
            int lastRow = ca.getLastRow();
            int mergeRows = lastRow - firstRow;
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            for (int j = lastRow + 1; j <= sheet.getLastRowNum(); j++) {
                sheet.addMergedRegion(new CellRangeAddress(j, j + mergeRows, firstColumn, lastColumn));
                j = j + mergeRows;
            }

        }
    }


    /**
     * 打印消息，
     *
     * @param msg 消息内容
     */
    private void out(String msg) {
        if (printMsg) {
            out(msg, true);
        }
    }

    /**
     * 打印消息，
     *
     * @param msg 消息内容
     * @param tr  换行
     */
    private void out(String msg, boolean tr) {
        if (printMsg) {
            System.out.print(msg + (tr ? "\n" : ""));
        }
    }

    public String getExcelPath() {
        return this.excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    public boolean isNeedCompare() {
        return isNeedCompare;
    }

    public void setNeedCompare(boolean isNeedCompare) {
        this.isNeedCompare = isNeedCompare;
    }

    public int getComparePos() {
        return comparePos;
    }

    public void setComparePos(int comparePos) {
        this.comparePos = comparePos;
    }

    public int getStartReadPos() {
        return startReadPos;
    }

    public void setStartReadPos(int startReadPos) {
        this.startReadPos = startReadPos;
    }

    public int getEndReadPos() {
        return endReadPos;
    }

    public void setEndReadPos(int endReadPos) {
        this.endReadPos = endReadPos;
    }

    public boolean isOverWrite() {
        return isOverWrite;
    }

    public void setOverWrite(boolean isOverWrite) {
        this.isOverWrite = isOverWrite;
    }

    public boolean isOnlyReadOneSheet() {
        return onlyReadOneSheet;
    }

    public void setOnlyReadOneSheet(boolean onlyReadOneSheet) {
        this.onlyReadOneSheet = onlyReadOneSheet;
    }

    public int getSelectedSheetIdx() {
        return selectedSheetIdx;
    }

    public void setSelectedSheetIdx(int selectedSheetIdx) {
        this.selectedSheetIdx = selectedSheetIdx;
    }

    public String getSelectedSheetName() {
        return selectedSheetName;
    }

    public void setSelectedSheetName(String selectedSheetName) {
        this.selectedSheetName = selectedSheetName;
    }

    public int getStartSheetIdx() {
        return startSheetIdx;
    }

    public void setStartSheetIdx(int startSheetIdx) {
        this.startSheetIdx = startSheetIdx;
    }

    public int getEndSheetIdx() {
        return endSheetIdx;
    }

    public void setEndSheetIdx(int endSheetIdx) {
        this.endSheetIdx = endSheetIdx;
    }

    public boolean isPrintMsg() {
        return printMsg;
    }

    public void setPrintMsg(boolean printMsg) {
        this.printMsg = printMsg;
    }
}
