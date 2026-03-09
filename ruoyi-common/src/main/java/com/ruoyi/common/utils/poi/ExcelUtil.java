package com.ruoyi.common.utils.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.annotation.Excel.Type;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.UtilException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileTypeUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.file.ImageUtils;
import com.ruoyi.common.utils.reflect.ReflectUtils;

/**
 * Excel相关处理
 * 
 * @author ruoyi
 */
public class ExcelUtil<T>
{
    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    public static final String SEPARATOR = ",";

    public static final String FORMULA_REGEX_STR = "=|-|\\+|@";

    public static final String[] FORMULA_STR = { "=", "-", "+", "@" };

    /**
     * 用于dictType属性数据存储，避免重复查缓存
     */
    public Map<String, String> sysDictMap = new HashMap<String, String>();

    /**
     * 单元格样式缓存
     */
    private Map<String, CellStyle> cellStyleCache = new HashMap<String, CellStyle>();

    /**
     * Excel sheet最大行数，默认65536
     */
    public static final int sheetSize = 65536;

    /**
     * 工作表名称
     */
    private String sheetName;

    /**
     * 导出类型（EXPORT:导出数据；IMPORT：导入模板）
     */
    private Type type;

    /**
     * 工作薄对象
     */
    private Workbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 样式列表
     */
    private Map<String, CellStyle> styles;

    /**
     * 导入导出数据列表
     */
    private List<T> list;

    /**
     * 注解列表
     */
    private List<Object[]> fields;

    /**
     * 当前行号
     */
    private int rownum;

    /**
     * 标题
     */
    private String title;

    /**
     * 最大高度
     */
    private short maxHeight;

    /**
     * 合并后最后行数
     */
    private int subMergedLastRowNum = 0;

    /**
     * 合并后开始行数
     */
    private int subMergedFirstRowNum = 1;

    /**
     * 对象的子列表方法
     */
    private Map<String, Method> subMethods;

    /**
     * 对象的子列表属性
     */
    private Map<String, List<Field>> subFieldsMap;

    /**
     * 统计列表
     */
    private Map<Integer, Double> statistics = new HashMap<Integer, Double>();

    /**
     * 实体对象
     */
    public Class<T> clazz;

    /**
     * 需要显示列属性
     */
    public String[] includeFields;

    /**
     * 需要排除列属性
     */
    public String[] excludeFields;

    public ExcelUtil(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    /**
     * 仅在Excel中显示列属性
     *
     * @param fields 列属性名 示例[单个"name"/多个"id","name"]
     */
    public void showColumn(String... fields)
    {
        this.includeFields = fields;
    }

    /**
     * 隐藏Excel中列属性
     *
     * @param fields 列属性名 示例[单个"name"/多个"id","name"]
     */
    public void hideColumn(String... fields)
    {
        this.excludeFields = fields;
    }

    public void init(List<T> list, String sheetName, String title, Type type)
    {
        if (list == null)
        {
            list = new ArrayList<T>();
        }
        this.list = list;
        this.sheetName = sheetName;
        this.type = type;
        this.title = title;
        createExcelField();
        createWorkbook();
        createTitle();
        createSubHead();
    }

    /**
     * 创建excel第一行标题
     */
    public void createTitle()
    {
        if (StringUtils.isNotEmpty(title))
        {
            int titleLastCol = this.fields.size() - 1;
            if (isSubList())
            {
                for (List<Field> currentSubFields : subFieldsMap.values())
                {
                    titleLastCol = titleLastCol + currentSubFields.size() - 1;
                }
            }
            Row titleRow = sheet.createRow(rownum == 0 ? rownum++ : 0);
            titleRow.setHeightInPoints(30);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(styles.get("title"));
            titleCell.setCellValue(title);
            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), 0, titleLastCol));
        }
    }

    /**
     * 创建对象的子列表名称
     */
    public void createSubHead()
    {
        if (isSubList())
        {
            Row subRow = sheet.createRow(rownum);
            int column = 0;
            for (Object[] objects : fields)
            {
                Field field = (Field) objects[0];
                Excel attr = (Excel) objects[1];
                CellStyle cellStyle = styles.get(StringUtils.format("header_{}_{}", attr.headerColor(), attr.headerBackgroundColor()));
                if (Collection.class.isAssignableFrom(field.getType()))
                {
                    Cell cell = subRow.createCell(column);
                    cell.setCellValue(attr.name());
                    cell.setCellStyle(cellStyle);
                    int subFieldSize = subFieldsMap != null ? subFieldsMap.get(field.getName()).size() : 0;
                    if (subFieldSize > 1)
                    {
                        CellRangeAddress cellAddress = new CellRangeAddress(rownum, rownum, column, column + subFieldSize - 1);
                        sheet.addMergedRegion(cellAddress);
                    }
                    column += subFieldSize;
                }
                else
                {
                    Cell cell = subRow.createCell(column++);
                    cell.setCellValue(attr.name());
                    cell.setCellStyle(cellStyle);
                }
            }
            rownum++;
        }
    }

    /**
     * 对excel表单默认第一个索引名转换成list
     * 
     * @param is 输入流
     * @return 转换后集合
     */
    public List<T> importExcel(InputStream is)
    {
        return importExcel(is, 0);
    }

    /**
     * 对excel表单默认第一个索引名转换成list
     * 
     * @param is 输入流
     * @param titleNum 标题占用行数
     * @return 转换后集合
     */
    public List<T> importExcel(InputStream is, int titleNum)
    {
        List<T> list = null;
        try
        {
            list = importExcel(StringUtils.EMPTY, is, titleNum);
        }
        catch (Exception e)
        {
            log.error("导入Excel异常{}", e.getMessage());
            throw new UtilException(e.getMessage());
        }
        finally
        {
            IOUtils.closeQuietly(is);
        }
        return list;
    }

    /**
     * 对excel表单指定表格索引名转换成list
     * 
     * @param sheetName 表格索引名
     * @param titleNum 标题占用行数
     * @param is 输入流
     * @return 转换后集合
     */
    public List<T> importExcel(String sheetName, InputStream is, int titleNum) throws Exception
    {
        this.type = Type.IMPORT;
        this.wb = WorkbookFactory.create(is);
        List<T> list = new ArrayList<T>();
        // 如果指定sheet名,则取指定sheet中的内容 否则默认指向第1个sheet
        Sheet sheet = StringUtils.isNotEmpty(sheetName) ? wb.getSheet(sheetName) : wb.getSheetAt(0);
        if (sheet == null)
        {
            throw new IOException("文件sheet不存在");
        }
        boolean isXSSFWorkbook = !(wb instanceof HSSFWorkbook);
        Map<String, List<PictureData>> pictures = null;
        if (isXSSFWorkbook)
        {
            pictures = getSheetPictures07((XSSFSheet) sheet, (XSSFWorkbook) wb);
        }
        else
        {
            pictures = getSheetPictures03((HSSFSheet) sheet, (HSSFWorkbook) wb);
        }
        // 获取最后一个非空行的行下标，比如总行数为n，则返回的为n-1
        int rows = sheet.getLastRowNum();
        if (rows > 0)
        {
            // 定义一个map用于存放excel列的序号和field.
            Map<String, Integer> cellMap = new HashMap<String, Integer>();
            // 获取表头
            Row heard = sheet.getRow(titleNum);
            if (heard == null)
            {
                throw new UtilException("文件标题行为空，请检查Excel文件格式");
            }
            for (int i = 0; i < heard.getLastCellNum(); i++)
            {
                Cell cell = heard.getCell(i);
                if (StringUtils.isNotNull(cell))
                {
                    String value = this.getCellValue(heard, i).toString();
                    cellMap.put(value, i);
                }
            }
            // 有数据时才处理 得到类的所有field.
            List<Object[]> fields = this.getFields();
            Map<Integer, Object[]> fieldsMap = new HashMap<Integer, Object[]>();
            for (Object[] objects : fields)
            {
                Excel attr = (Excel) objects[1];
                Integer column = cellMap.get(attr.name());
                if (column != null)
                {
                    fieldsMap.put(column, objects);
                }
            }
            for (int i = titleNum + 1; i <= rows; i++)
            {
                // 从第2行开始取数据,默认第一行是表头.
                Row row = sheet.getRow(i);
                // 判断当前行是否是空行
                if (isRowEmpty(row))
                {
                    continue;
                }
                T entity = null;
                for (Map.Entry<Integer, Object[]> entry : fieldsMap.entrySet())
                {
                    Object val = this.getCellValue(row, entry.getKey());

                    // 如果不存在实例则新建.
                    entity = (entity == null ? clazz.newInstance() : entity);
                    // 从map中得到对应列的field.
                    Field field = (Field) entry.getValue()[0];
                    Excel attr = (Excel) entry.getValue()[1];
                    // 取得类型,并根据对象类型设置值.
                    Class<?> fieldType = field.getType();
                    if (String.class == fieldType)
                    {
                        String s = Convert.toStr(val);
                        if (s.matches("^\\d+\\.0$"))
                        {
                            val = StringUtils.substringBefore(s, ".0");
                        }
                        else
                        {
                            String dateFormat = field.getAnnotation(Excel.class).dateFormat();
                            if (StringUtils.isNotEmpty(dateFormat))
                            {
                                val = parseDateToStr(dateFormat, val);
                            }
                            else
                            {
                                val = Convert.toStr(val);
                            }
                        }
                    }
                    else if ((Integer.TYPE == fieldType || Integer.class == fieldType) && StringUtils.isNumeric(Convert.toStr(val)))
                    {
                        val = Convert.toInt(val);
                    }
                    else if ((Long.TYPE == fieldType || Long.class == fieldType) && StringUtils.isNumeric(Convert.toStr(val)))
                    {
                        val = Convert.toLong(val);
                    }
                    else if (Double.TYPE == fieldType || Double.class == fieldType)
                    {
                        val = Convert.toDouble(val);
                    }
                    else if (Float.TYPE == fieldType || Float.class == fieldType)
                    {
                        val = Convert.toFloat(val);
                    }
                    else if (BigDecimal.class == fieldType)
                    {
                        val = Convert.toBigDecimal(val);
                    }
                    else if (Date.class == fieldType)
                    {
                        if (val instanceof String)
                        {
                            val = DateUtils.parseDate(val);
                        }
                        else if (val instanceof Double)
                        {
                            val = DateUtil.getJavaDate((Double) val);
                        }
                    }
                    else if (Boolean.TYPE == fieldType || Boolean.class == fieldType)
                    {
                        val = Convert.toBool(val, false);
                    }
                    if (StringUtils.isNotNull(fieldType))
                    {
                        String propertyName = field.getName();
                        if (StringUtils.isNotEmpty(attr.targetAttr()))
                        {
                            propertyName = field.getName() + "." + attr.targetAttr();
                        }
                        if (StringUtils.isNotEmpty(attr.readConverterExp()))
                        {
                            val = reverseByExp(Convert.toStr(val), attr.readConverterExp(), attr.separator());
                        }
                        else if (StringUtils.isNotEmpty(attr.dictType()))
                        {
                            if (!sysDictMap.containsKey(attr.dictType() + val))
                            {
                                String dictValue = reverseDictByExp(Convert.toStr(val), attr.dictType(), attr.separator());
                                sysDictMap.put(attr.dictType() + val, dictValue);
                            }
                            val = sysDictMap.get(attr.dictType() + val);
                        }
                        else if (!attr.handler().equals(ExcelHandlerAdapter.class))
                        {
                            val = dataFormatHandlerAdapter(val, attr, null);
                        }
                        else if (ColumnType.IMAGE == attr.cellType() && StringUtils.isNotEmpty(pictures))
                        {
                            StringBuilder propertyString = new StringBuilder();
                            List<PictureData> images = pictures.get(row.getRowNum() + "_" + entry.getKey());
                            for (PictureData picture : images)
                            {
                                byte[] data = picture.getData();
                                String fileName = FileUtils.writeImportBytes(data);
                                propertyString.append(fileName).append(SEPARATOR);
                            }
                            val = StringUtils.stripEnd(propertyString.toString(), SEPARATOR);
                        }
                        ReflectUtils.invokeSetter(entity, propertyName, val);
                    }
                }
                list.add(entity);
            }
        }
        return list;
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @param list 导出数据集合
     * @param sheetName 工作表的名称
     * @return 结果
     */
    public AjaxResult exportExcel(List<T> list, String sheetName)
    {
        return exportExcel(list, sheetName, StringUtils.EMPTY);
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @param list 导出数据集合
     * @param sheetName 工作表的名称
     * @param title 标题
     * @return 结果
     */
    public AjaxResult exportExcel(List<T> list, String sheetName, String title)
    {
        this.init(list, sheetName, title, Type.EXPORT);
        return exportExcel();
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @param response 返回数据
     * @param list 导出数据集合
     * @param sheetName 工作表的名称
     * @return 结果
     */
    public void exportExcel(HttpServletResponse response, List<T> list, String sheetName)
    {
        exportExcel(response, list, sheetName, StringUtils.EMPTY);
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @param response 返回数据
     * @param list 导出数据集合
     * @param sheetName 工作表的名称
     * @param title 标题
     * @return 结果
     */
    public void exportExcel(HttpServletResponse response, List<T> list, String sheetName, String title)
    {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        this.init(list, sheetName, title, Type.EXPORT);
        exportExcel(response);
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @param sheetName 工作表的名称
     * @return 结果
     */
    public AjaxResult importTemplateExcel(String sheetName)
    {
        return importTemplateExcel(sheetName, StringUtils.EMPTY);
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @param sheetName 工作表的名称
     * @param title 标题
     * @return 结果
     */
    public AjaxResult importTemplateExcel(String sheetName, String title)
    {
        this.init(null, sheetName, title, Type.IMPORT);
        return exportExcel();
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @param sheetName 工作表的名称
     * @return 结果
     */
    public void importTemplateExcel(HttpServletResponse response, String sheetName)
    {
        importTemplateExcel(response, sheetName, StringUtils.EMPTY);
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @param sheetName 工作表的名称
     * @param title 标题
     * @return 结果
     */
    public void importTemplateExcel(HttpServletResponse response, String sheetName, String title)
    {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        this.init(null, sheetName, title, Type.IMPORT);
        exportExcel(response);
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @return 结果
     */
    public void exportExcel(HttpServletResponse response)
    {
        try
        {
            writeSheet();
            wb.write(response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("导出Excel异常{}", e.getMessage());
        }
        finally
        {
            IOUtils.closeQuietly(wb);
        }
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     * 
     * @return 结果
     */
    public AjaxResult exportExcel()
    {
        OutputStream out = null;
        try
        {
            writeSheet();
            String filename = encodingFilename(sheetName);
            out = new FileOutputStream(getAbsoluteFile(filename));
            wb.write(out);
            return AjaxResult.success(filename);
        }
        catch (Exception e)
        {
            log.error("导出Excel异常{}", e.getMessage());
            throw new UtilException("导出Excel失败，请联系网站管理员！");
        }
        finally
        {
            IOUtils.closeQuietly(wb);
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 创建写入数据到Sheet
     */
    public void writeSheet()
    {
        // 取出一共有多少个sheet.
        int sheetNo = Math.max(1, (int) Math.ceil(list.size() * 1.0 / sheetSize));
        for (int index = 0; index < sheetNo; index++)
        {
            createSheet(sheetNo, index);

            // 产生一行
            Row row = sheet.createRow(rownum);
            int column = 0;
            // 写入各个字段的列头名称
            for (Object[] os : fields)
            {
                Field field = (Field) os[0];
                Excel excel = (Excel) os[1];
                if (Collection.class.isAssignableFrom(field.getType()))
                {
                    List<Field> currentSubFields = subFieldsMap.get(field.getName());
                    for (Field subField : currentSubFields)
                    {
                        Excel subExcel = subField.getAnnotation(Excel.class);
                        this.createHeadCell(subExcel, row, column++);
                    }
                }
                else
                {
                    this.createHeadCell(excel, row, column++);
                }
            }
            if (Type.EXPORT.equals(type))
            {
                fillExcelData(index);
                addStatisticsRow();
            }
        }
    }

    /**
     * 填充excel数据
     * 
     * @param index 序号
     */
    @SuppressWarnings("unchecked")
    public void fillExcelData(int index)
    {
        int startNo = index * sheetSize;
        int endNo = Math.min(startNo + sheetSize, list.size());
        int currentRowNum = rownum + 1; // 从标题行后开始

        for (int i = startNo; i < endNo; i++)
        {
            Row row = sheet.createRow(currentRowNum);
            T vo = (T) list.get(i);
            int column = 0;
            int maxSubListSize = getCurrentMaxSubListSize(vo);
            for (Object[] os : fields)
            {
                Field field = (Field) os[0];
                Excel excel = (Excel) os[1];
                if (Collection.class.isAssignableFrom(field.getType()))
                {
                    try
                    {
                        Collection<?> subList = (Collection<?>) getTargetValue(vo, field, excel);
                        List<Field> currentSubFields = subFieldsMap.get(field.getName());
                        if (subList != null && !subList.isEmpty())
                        {
                            int subIndex = 0;
                            for (Object subVo : subList)
                            {
                                Row subRow = sheet.getRow(currentRowNum + subIndex);
                                if (subRow == null)
                                {
                                    subRow = sheet.createRow(currentRowNum + subIndex);
                                }

                                int subColumn = column;
                                for (Field subField : currentSubFields)
                                {
                                    Excel subExcel = subField.getAnnotation(Excel.class);
                                    addCell(subExcel, subRow, (T) subVo, subField, subColumn++);
                                }
                                subIndex++;
                            }
                        }
                        column += currentSubFields.size();
                    }
                    catch (Exception e)
                    {
                        log.error("填充集合数据失败", e);
                    }
                }
                else
                {
                    // 创建单元格并设置值
                    addCell(excel, row, vo, field, column);
                    if (maxSubListSize > 1 && excel.needMerge())
                    {
                        sheet.addMergedRegion(new CellRangeAddress(currentRowNum, currentRowNum + maxSubListSize - 1, column, column));
                    }
                    column++;
                }
            }
            currentRowNum += maxSubListSize;
        }
    }

    /**
     * 获取子列表最大数
     */
    private int getCurrentMaxSubListSize(T vo)
    {
        int maxSubListSize = 1;
        for (Object[] os : fields)
        {
            Field field = (Field) os[0];
            if (Collection.class.isAssignableFrom(field.getType()))
            {
                try
                {
                    Collection<?> subList = (Collection<?>) getTargetValue(vo, field, (Excel) os[1]);
                    if (subList != null && !subList.isEmpty())
                    {
                        maxSubListSize = Math.max(maxSubListSize, subList.size());
                    }
                }
                catch (Exception e)
                {
                    log.error("获取集合大小失败", e);
                }
            }
        }
        return maxSubListSize;
    }

    /**
     * 创建表格样式
     * 
     * @param wb 工作薄对象
     * @return 样式列表
     */
    private Map<String, CellStyle> createStyles(Workbook wb)
    {
        // 写入各条记录,每条记录对应excel表中的一行
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font titleFont = wb.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        style.setFont(titleFont);
        DataFormat dataFormat = wb.createDataFormat();
        style.setDataFormat(dataFormat.getFormat("@"));
        styles.put("title", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        styles.put("data", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setDataFormat(dataFormat.getFormat("######0.00"));
        Font totalFont = wb.createFont();
        totalFont.setFontName("Arial");
        totalFont.setFontHeightInPoints((short) 10);
        style.setFont(totalFont);
        styles.put("total", style);

        styles.putAll(annotationHeaderStyles(wb, styles));

        styles.putAll(annotationDataStyles(wb));

        return styles;
    }

    /**
     * 根据Excel注解创建表格头样式
     * 
     * @param wb 工作薄对象
     * @return 自定义样式列表
     */
    private Map<String, CellStyle> annotationHeaderStyles(Workbook wb, Map<String, CellStyle> styles)
    {
        Map<String, CellStyle> headerStyles = new HashMap<String, CellStyle>();
        for (Object[] os : fields)
        {
            Excel excel = (Excel) os[1];
            String key = StringUtils.format("header_{}_{}", excel.headerColor(), excel.headerBackgroundColor());
            if (!headerStyles.containsKey(key))
            {
                CellStyle style = wb.createCellStyle();
                style.cloneStyleFrom(styles.get("data"));
                style.setAlignment(HorizontalAlignment.CENTER);
                style.setVerticalAlignment(VerticalAlignment.CENTER);
                style.setFillForegroundColor(excel.headerBackgroundColor().index);
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                Font headerFont = wb.createFont();
                headerFont.setFontName("Arial");
                headerFont.setFontHeightInPoints((short) 10);
                headerFont.setBold(true);
                headerFont.setColor(excel.headerColor().index);
                style.setFont(headerFont);
                // 设置表格头单元格文本形式
                DataFormat dataFormat = wb.createDataFormat();
                style.setDataFormat(dataFormat.getFormat("@"));
                headerStyles.put(key, style);
            }
        }
        return headerStyles;
    }

    /**
     * 根据Excel注解创建表格列样式
     * 
     * @param wb 工作薄对象
     * @return 自定义样式列表
     */
    private Map<String, CellStyle> annotationDataStyles(Workbook wb)
    {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        for (Object[] os : fields)
        {
            Field field = (Field) os[0];
            Excel excel = (Excel) os[1];
            if (Collection.class.isAssignableFrom(field.getType()))
            {
                ParameterizedType pt = (ParameterizedType) field.getGenericType();
                Class<?> subClass = (Class<?>) pt.getActualTypeArguments()[0];
                List<Field> subFields = FieldUtils.getFieldsListWithAnnotation(subClass, Excel.class);
                for (Field subField : subFields)
                {
                    Excel subExcel = subField.getAnnotation(Excel.class);
                    annotationDataStyles(styles, subField, subExcel);
                }
            }
            else
            {
                annotationDataStyles(styles, field, excel);
            }
        }
        return styles;
    }

    /**
     * 根据Excel注解创建表格列样式
     * 
     * @param styles 自定义样式列表
     * @param field  属性列信息
     * @param excel  注解信息
     */
    public void annotationDataStyles(Map<String, CellStyle> styles, Field field, Excel excel)
    {
        String key = StringUtils.format("data_{}_{}_{}_{}_{}", excel.align(), excel.color(), excel.backgroundColor(), excel.cellType(), excel.wrapText());
        if (!styles.containsKey(key))
        {
            CellStyle style = wb.createCellStyle();
            style.setAlignment(excel.align());
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setBorderRight(BorderStyle.THIN);
            style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            style.setBorderLeft(BorderStyle.THIN);
            style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            style.setBorderTop(BorderStyle.THIN);
            style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            style.setBorderBottom(BorderStyle.THIN);
            style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setFillForegroundColor(excel.backgroundColor().getIndex());
            style.setWrapText(excel.wrapText());
            Font dataFont = wb.createFont();
            dataFont.setFontName("Arial");
            dataFont.setFontHeightInPoints((short) 10);
            dataFont.setColor(excel.color().index);
            style.setFont(dataFont);
            if (ColumnType.TEXT == excel.cellType())
            {
                DataFormat dataFormat = wb.createDataFormat();
                style.setDataFormat(dataFormat.getFormat("@"));
            }
            styles.put(key, style);
        }
    }

    /**
     * 创建单元格
     */
    public Cell createHeadCell(Excel attr, Row row, int column)
    {
        // 创建列
        Cell cell = row.createCell(column);
        // 写入列信息
        cell.setCellValue(attr.name());
        setDataValidation(attr, row, column);
        cell.setCellStyle(styles.get(StringUtils.format("header_{}_{}", attr.headerColor(), attr.headerBackgroundColor())));
        if (isSubList())
        {
            // 填充默认样式，防止合并单元格样式失效
            sheet.setDefaultColumnStyle(column, styles.get(StringUtils.format("data_{}_{}_{}_{}_{}", attr.align(), attr.color(), attr.backgroundColor(), attr.cellType(), attr.wrapText())));
            if (attr.needMerge())
            {
                sheet.addMergedRegion(new CellRangeAddress(rownum - 1, rownum, column, column));
            }
        }
        return cell;
    }

    /**
     * 设置单元格信息
     * 
     * @param value 单元格值
     * @param attr 注解相关
     * @param cell 单元格信息
     */
    public void setCellVo(Object value, Excel attr, Cell cell)
    {
        if (ColumnType.STRING == attr.cellType() || ColumnType.TEXT == attr.cellType())
        {
            String cellValue = Convert.toStr(value);
            // 对于任何以表达式触发字符 =-+@开头的单元格，直接使用tab字符作为前缀，防止CSV注入。
            if (StringUtils.startsWithAny(cellValue, FORMULA_STR))
            {
                cellValue = RegExUtils.replaceFirst(cellValue, FORMULA_REGEX_STR, "\t$0");
            }
            if (value instanceof Collection && StringUtils.equals("[]", cellValue))
            {
                cellValue = StringUtils.EMPTY;
            }
            cell.setCellValue(StringUtils.isNull(cellValue) ? attr.defaultValue() : cellValue + attr.suffix());
        }
        else if (ColumnType.NUMERIC == attr.cellType())
        {
            if (StringUtils.isNotNull(value))
            {
                cell.setCellValue(StringUtils.contains(Convert.toStr(value), ".") ? Convert.toDouble(value) : Convert.toInt(value));
            }
        }
        else if (ColumnType.IMAGE == attr.cellType())
        {
            ClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) cell.getColumnIndex(), cell.getRow().getRowNum(), (short) (cell.getColumnIndex() + 1), cell.getRow().getRowNum() + 1);
            String propertyValue = Convert.toStr(value);
            if (StringUtils.isNotEmpty(propertyValue))
            {
                List<String> imagePaths = StringUtils.str2List(propertyValue, SEPARATOR);
                for (String imagePath : imagePaths)
                {
                    byte[] data = ImageUtils.getImage(imagePath);
                    getDrawingPatriarch(cell.getSheet()).createPicture(anchor, cell.getSheet().getWorkbook().addPicture(data, getImageType(data)));
                }
            }
        }
    }

    /**
     * 获取画布
     */
    public static Drawing<?> getDrawingPatriarch(Sheet sheet)
    {
        if (sheet.getDrawingPatriarch() == null)
        {
            sheet.createDrawingPatriarch();
        }
        return sheet.getDrawingPatriarch();
    }

    /**
     * 获取图片类型,设置图片插入类型
     */
    public int getImageType(byte[] value)
    {
        String type = FileTypeUtils.getFileExtendName(value);
        if ("JPG".equalsIgnoreCase(type))
        {
            return Workbook.PICTURE_TYPE_JPEG;
        }
        else if ("PNG".equalsIgnoreCase(type))
        {
            return Workbook.PICTURE_TYPE_PNG;
        }
        return Workbook.PICTURE_TYPE_JPEG;
    }

    /**
     * 创建表格样式
     */
    public void setDataValidation(Excel attr, Row row, int column)
    {
        if (attr.name().indexOf("注：") >= 0)
        {
            sheet.setColumnWidth(column, 6000);
        }
        else
        {
            // 设置列宽
            sheet.setColumnWidth(column, (int) ((attr.width() + 0.72) * 256));
        }
        if (StringUtils.isNotEmpty(attr.prompt()) || attr.combo().length > 0 || attr.comboReadDict())
        {
            String[] comboArray = attr.combo();
            if (attr.comboReadDict())
            {
                if (!sysDictMap.containsKey("combo_" + attr.dictType()))
                {
                    String labels = DictUtils.getDictLabels(attr.dictType());
                    sysDictMap.put("combo_" + attr.dictType(), labels);
                }
                String val = sysDictMap.get("combo_" + attr.dictType());
                comboArray = StringUtils.split(val, DictUtils.SEPARATOR);
            }
            if (comboArray.length > 15 || StringUtils.join(comboArray).length() > 255)
            {
                // 如果下拉数大于15或字符串长度大于255，则使用一个新sheet存储，避免生成的模板下拉值获取不到
                setXSSFValidationWithHidden(sheet, comboArray, attr.prompt(), 1, 100, column, column);
            }
            else
            {
                // 提示信息或只能选择不能输入的列内容.
                setPromptOrValidation(sheet, comboArray, attr.prompt(), 1, 100, column, column);
            }
        }
    }

    /**
     * 添加单元格
     */
    public Cell addCell(Excel attr, Row row, T vo, Field field, int column)
    {
        Cell cell = null;
        try
        {
            // 设置行高
            row.setHeight(maxHeight);
            // 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
            if (attr.isExport())
            {
                // 创建cell
                cell = row.createCell(column);
                if (isSubListValue(vo) && getListCellValue(vo) > 1 && attr.needMerge())
                {
                    if (subMergedLastRowNum >= subMergedFirstRowNum)
                    {
                        sheet.addMergedRegion(new CellRangeAddress(subMergedFirstRowNum, subMergedLastRowNum, column, column));
                    }
                }
                cell.setCellStyle(styles.get(StringUtils.format("data_{}_{}_{}_{}_{}", attr.align(), attr.color(), attr.backgroundColor(), attr.cellType(), attr.wrapText())));

                // 用于读取对象中的属性
                Object value = getTargetValue(vo, field, attr);
                String dateFormat = attr.dateFormat();
                String readConverterExp = attr.readConverterExp();
                String separator = attr.separator();
                String dictType = attr.dictType();
                if (StringUtils.isNotEmpty(dateFormat) && StringUtils.isNotNull(value))
                {
                    cell.setCellStyle(createCellStyle(cell.getCellStyle(), dateFormat));
                    cell.setCellValue(parseDateToStr(dateFormat, value));
                }
                else if (StringUtils.isNotEmpty(readConverterExp) && StringUtils.isNotNull(value))
                {
                    cell.setCellValue(convertByExp(Convert.toStr(value), readConverterExp, separator));
                }
                else if (StringUtils.isNotEmpty(dictType) && StringUtils.isNotNull(value))
                {
                    if (!sysDictMap.containsKey(dictType + value))
                    {
                        String lable = convertDictByExp(Convert.toStr(value), dictType, separator);
                        sysDictMap.put(dictType + value, lable);
                    }
                    cell.setCellValue(sysDictMap.get(dictType + value));
                }
                else if (value instanceof BigDecimal && -1 != attr.scale())
                {
                    cell.setCellValue((((BigDecimal) value).setScale(attr.scale(), attr.roundingMode())).doubleValue());
                }
                else if (!attr.handler().equals(ExcelHandlerAdapter.class))
                {
                    cell.setCellValue(dataFormatHandlerAdapter(value, attr, cell));
                }
                else
                {
                    // 设置列类型
                    setCellVo(value, attr, cell);
                }
                addStatisticsData(column, Convert.toStr(value), attr);
            }
        }
        catch (Exception e)
        {
            log.error("导出Excel失败{}", e);
        }
        return cell;
    }

    /**
     * 使用自定义格式，同时避免样式污染
     * 
     * @param cellStyle 从此样式复制
     * @param format 格式匹配的字符串
     * @return 格式化后CellStyle对象
     */
    private CellStyle createCellStyle(CellStyle cellStyle, String format)
    {
        String key = cellStyle.getIndex() + "|" + format;
        CellStyle cached = cellStyleCache.get(key);
        if (cached != null)
        {
            return cached;
        }
        CellStyle style = wb.createCellStyle();
        style.cloneStyleFrom(cellStyle);
        style.setDataFormat(wb.getCreationHelper().createDataFormat().getFormat(format));
        cellStyleCache.put(key, style);
        return style;
    }

    /**
     * 设置 POI XSSFSheet 单元格提示或选择框
     * 
     * @param sheet 表单
     * @param textlist 下拉框显示的内容
     * @param promptContent 提示内容
     * @param firstRow 开始行
     * @param endRow 结束行
     * @param firstCol 开始列
     * @param endCol 结束列
     */
    public void setPromptOrValidation(Sheet sheet, String[] textlist, String promptContent, int firstRow, int endRow,
            int firstCol, int endCol)
    {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = textlist.length > 0 ? helper.createExplicitListConstraint(textlist) : helper.createCustomConstraint("DD1");
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        if (StringUtils.isNotEmpty(promptContent))
        {
            // 如果设置了提示信息则鼠标放上去提示
            dataValidation.createPromptBox("", promptContent);
            dataValidation.setShowPromptBox(true);
        }
        // 处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation)
        {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        }
        else
        {
            dataValidation.setSuppressDropDownArrow(false);
        }
        sheet.addValidationData(dataValidation);
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框（兼容超出一定数量的下拉框）.
     * 
     * @param sheet 要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param promptContent 提示内容
     * @param firstRow 开始行
     * @param endRow 结束行
     * @param firstCol 开始列
     * @param endCol 结束列
     */
    public void setXSSFValidationWithHidden(Sheet sheet, String[] textlist, String promptContent, int firstRow, int endRow, int firstCol, int endCol)
    {
        String hideSheetName = "combo_" + firstCol + "_" + endCol;
        Sheet hideSheet = null;
        String hideSheetDataName = hideSheetName + "_data";
        Name name = wb.getName(hideSheetDataName);
        if (name != null)
        {
            // 名称已存在，尝试从名称的引用中找到sheet名称
            String refersToFormula = name.getRefersToFormula();
            if (StringUtils.isNotEmpty(refersToFormula) && refersToFormula.contains("!"))
            {
                String sheetNameFromFormula = refersToFormula.substring(0, refersToFormula.indexOf("!"));
                hideSheet = wb.getSheet(sheetNameFromFormula);
            }
        }

        if (hideSheet == null)
        {
            hideSheet = wb.createSheet(hideSheetName); // 用于存储 下拉菜单数据
            for (int i = 0; i < textlist.length; i++)
            {
                hideSheet.createRow(i).createCell(0).setCellValue(textlist[i]);
            }
            // 创建名称，可被其他单元格引用
            name = wb.createName();
            name.setNameName(hideSheetDataName);
            name.setRefersToFormula(hideSheetName + "!$A$1:$A$" + textlist.length);
        }

        DataValidationHelper helper = sheet.getDataValidationHelper();
        // 加载下拉列表内容
        DataValidationConstraint constraint = helper.createFormulaListConstraint(hideSheetDataName);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        if (StringUtils.isNotEmpty(promptContent))
        {
            // 如果设置了提示信息则鼠标放上去提示
            dataValidation.createPromptBox("", promptContent);
            dataValidation.setShowPromptBox(true);
        }
        // 处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation)
        {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        }
        else
        {
            dataValidation.setSuppressDropDownArrow(false);
        }

        sheet.addValidationData(dataValidation);
        // 设置hiddenSheet隐藏
        wb.setSheetHidden(wb.getSheetIndex(hideSheet), true);
    }

    /**
     * 解析导出值 0=男,1=女,2=未知
     * 
     * @param propertyValue 参数值
     * @param converterExp 翻译注解
     * @param separator 分隔符
     * @return 解析后值
     */
    public static String convertByExp(String propertyValue, String converterExp, String separator)
    {
        StringBuilder propertyString = new StringBuilder();
        String[] convertSource = converterExp.split(SEPARATOR);
        for (String item : convertSource)
        {
            String[] itemArray = item.split("=");
            if (StringUtils.containsAny(propertyValue, separator))
            {
                for (String value : propertyValue.split(separator))
                {
                    if (itemArray[0].equals(value))
                    {
                        propertyString.append(itemArray[1] + separator);
                        break;
                    }
                }
            }
            else
            {
                if (itemArray[0].equals(propertyValue))
                {
                    return itemArray[1];
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 反向解析值 男=0,女=1,未知=2
     * 
     * @param propertyValue 参数值
     * @param converterExp 翻译注解
     * @param separator 分隔符
     * @return 解析后值
     */
    public static String reverseByExp(String propertyValue, String converterExp, String separator)
    {
        StringBuilder propertyString = new StringBuilder();
        String[] convertSource = converterExp.split(SEPARATOR);
        for (String item : convertSource)
        {
            String[] itemArray = item.split("=");
            if (StringUtils.containsAny(propertyValue, separator))
            {
                for (String value : propertyValue.split(separator))
                {
                    if (itemArray[1].equals(value))
                    {
                        propertyString.append(itemArray[0] + separator);
                        break;
                    }
                }
            }
            else
            {
                if (itemArray[1].equals(propertyValue))
                {
                    return itemArray[0];
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 解析字典值
     * 
     * @param dictValue 字典值
     * @param dictType 字典类型
     * @param separator 分隔符
     * @return 字典标签
     */
    public static String convertDictByExp(String dictValue, String dictType, String separator)
    {
        return DictUtils.getDictLabel(dictType, dictValue, separator);
    }

    /**
     * 反向解析值字典值
     * 
     * @param dictLabel 字典标签
     * @param dictType 字典类型
     * @param separator 分隔符
     * @return 字典值
     */
    public static String reverseDictByExp(String dictLabel, String dictType, String separator)
    {
        return DictUtils.getDictValue(dictType, dictLabel, separator);
    }

    /**
     * 数据处理器
     * 
     * @param value 数据值
     * @param excel 数据注解
     * @return
     */
    public String dataFormatHandlerAdapter(Object value, Excel excel, Cell cell)
    {
        try
        {
            Object instance = excel.handler().newInstance();
            Method formatMethod = excel.handler().getMethod("format", new Class[] { Object.class, String[].class, Cell.class, Workbook.class });
            value = formatMethod.invoke(instance, value, excel.args(), cell, this.wb);
        }
        catch (Exception e)
        {
            log.error("不能格式化数据 " + excel.handler(), e.getMessage());
        }
        return Convert.toStr(value);
    }

    /**
     * 合计统计信息
     */
    private void addStatisticsData(Integer index, String text, Excel entity)
    {
        if (entity != null && entity.isStatistics())
        {
            Double temp = 0D;
            if (!statistics.containsKey(index))
            {
                statistics.put(index, temp);
            }
            try
            {
                temp = Double.valueOf(text);
            }
            catch (NumberFormatException e)
            {
            }
            statistics.put(index, statistics.get(index) + temp);
        }
    }

    /**
     * 创建统计行
     */
    public void addStatisticsRow()
    {
        if (statistics.size() > 0)
        {
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            Set<Integer> keys = statistics.keySet();
            Cell cell = row.createCell(0);
            cell.setCellStyle(styles.get("total"));
            cell.setCellValue("合计");

            for (Integer key : keys)
            {
                cell = row.createCell(key);
                cell.setCellStyle(styles.get("total"));
                cell.setCellValue(statistics.get(key));
            }
            statistics.clear();
        }
    }

    /**
     * 编码文件名
     */
    public String encodingFilename(String filename)
    {
        return UUID.randomUUID() + "_" + filename + ".xlsx";
    }

    /**
     * 获取下载路径
     * 
     * @param filename 文件名称
     */
    public String getAbsoluteFile(String filename)
    {
        String downloadPath = RuoYiConfig.getDownloadPath() + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        return downloadPath;
    }

    /**
     * 获取bean中的属性值
     * 
     * @param vo 实体对象
     * @param field 字段
     * @param excel 注解
     * @return 最终的属性值
     * @throws Exception
     */
    private Object getTargetValue(T vo, Field field, Excel excel) throws Exception
    {
        field.setAccessible(true);
        Object o = field.get(vo);
        if (StringUtils.isNotEmpty(excel.targetAttr()))
        {
            String target = excel.targetAttr();
            if (target.contains("."))
            {
                String[] targets = target.split("[.]");
                for (String name : targets)
                {
                    o = getValue(o, name);
                }
            }
            else
            {
                o = getValue(o, target);
            }
        }
        return o;
    }

    /**
     * 以类的属性的get方法方法形式获取值
     * 
     * @param o
     * @param name
     * @return value
     * @throws Exception
     */
    private Object getValue(Object o, String name) throws Exception
    {
        if (StringUtils.isNotNull(o) && StringUtils.isNotEmpty(name))
        {
            Class<?> clazz = o.getClass();
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            o = field.get(o);
        }
        return o;
    }

    /**
     * 得到所有定义字段
     */
    private void createExcelField()
    {
        this.fields = getFields();
        this.fields = this.fields.stream().sorted(Comparator.comparing(objects -> ((Excel) objects[1]).sort())).collect(Collectors.toList());
        this.maxHeight = getRowHeight();
    }

    /**
     * 获取字段注解信息
     */
    public List<Object[]> getFields()
    {
        List<Object[]> fields = new ArrayList<Object[]>();
        List<Field> tempFields = new ArrayList<>();
        subFieldsMap = new HashMap<>();
        subMethods = new HashMap<>();
        tempFields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        tempFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        if (StringUtils.isNotEmpty(includeFields))
        {
            for (Field field : tempFields)
            {
                if (ArrayUtils.contains(this.includeFields, field.getName()) || field.isAnnotationPresent(Excels.class))
                {
                    addField(fields, field);
                }
            }
        }
        else if (StringUtils.isNotEmpty(excludeFields))
        {
            for (Field field : tempFields)
            {
                if (!ArrayUtils.contains(this.excludeFields, field.getName()))
                {
                    addField(fields, field);
                }
            }
        }
        else
        {
            for (Field field : tempFields)
            {
                addField(fields, field);
            }
        }
        return fields;
    }

    /**
     * 添加字段信息
     */
    public void addField(List<Object[]> fields, Field field)
    {
        // 单注解
        if (field.isAnnotationPresent(Excel.class))
        {
            Excel attr = field.getAnnotation(Excel.class);
            if (attr != null && (attr.type() == Type.ALL || attr.type() == type))
            {
                fields.add(new Object[] { field, attr });
            }
            if (Collection.class.isAssignableFrom(field.getType()))
            {
                String fieldName = field.getName();
                subMethods.put(fieldName, getSubMethod(fieldName, clazz));
                ParameterizedType pt = (ParameterizedType) field.getGenericType();
                Class<?> subClass = (Class<?>) pt.getActualTypeArguments()[0];
                subFieldsMap.put(fieldName, FieldUtils.getFieldsListWithAnnotation(subClass, Excel.class));
            }
        }

        // 多注解
        if (field.isAnnotationPresent(Excels.class))
        {
            Excels attrs = field.getAnnotation(Excels.class);
            Excel[] excels = attrs.value();
            for (Excel attr : excels)
            {
                if (StringUtils.isNotEmpty(includeFields))
                {
                    if (ArrayUtils.contains(this.includeFields, field.getName() + "." + attr.targetAttr())
                            && (attr != null && (attr.type() == Type.ALL || attr.type() == type)))
                    {
                        fields.add(new Object[] { field, attr });
                    }
                }
                else
                {
                    if (!ArrayUtils.contains(this.excludeFields, field.getName() + "." + attr.targetAttr())
                            && (attr != null && (attr.type() == Type.ALL || attr.type() == type)))
                    {
                        fields.add(new Object[] { field, attr });
                    }
                }
            }
        }
    }

    /**
     * 根据注解获取最大行高
     */
    public short getRowHeight()
    {
        double maxHeight = 0;
        for (Object[] os : this.fields)
        {
            Excel excel = (Excel) os[1];
            maxHeight = Math.max(maxHeight, excel.height());
        }
        return (short) (maxHeight * 20);
    }

    /**
     * 创建一个工作簿
     */
    public void createWorkbook()
    {
        this.wb = new SXSSFWorkbook(500);
        this.sheet = wb.createSheet();
        wb.setSheetName(0, sheetName);
        this.styles = createStyles(wb);
    }

    /**
     * 创建工作表
     * 
     * @param sheetNo sheet数量
     * @param index 序号
     */
    public void createSheet(int sheetNo, int index)
    {
        // 设置工作表的名称.
        if (sheetNo > 1 && index > 0)
        {
            this.sheet = wb.createSheet();
            this.createTitle();
            int actualIndex = wb.getSheetIndex(this.sheet);
            wb.setSheetName(actualIndex, sheetName + index);
        }
    }

    /**
     * 获取单元格值
     * 
     * @param row 获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    public Object getCellValue(Row row, int column)
    {
        if (row == null)
        {
            return row;
        }
        Object val = "";
        try
        {
            Cell cell = row.getCell(column);
            if (StringUtils.isNotNull(cell))
            {
                if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA)
                {
                    val = cell.getNumericCellValue();
                    if (DateUtil.isCellDateFormatted(cell))
                    {
                        val = DateUtil.getJavaDate((Double) val); // POI Excel 日期格式转换
                    }
                    else
                    {
                        if ((Double) val % 1 != 0)
                        {
                            val = new BigDecimal(val.toString());
                        }
                        else
                        {
                            val = new DecimalFormat("0").format(val);
                        }
                    }
                }
                else if (cell.getCellType() == CellType.STRING)
                {
                    val = cell.getStringCellValue();
                }
                else if (cell.getCellType() == CellType.BOOLEAN)
                {
                    val = cell.getBooleanCellValue();
                }
                else if (cell.getCellType() == CellType.ERROR)
                {
                    val = cell.getErrorCellValue();
                }

            }
        }
        catch (Exception e)
        {
            return val;
        }
        return val;
    }

    /**
     * 判断是否是空行
     * 
     * @param row 判断的行
     * @return
     */
    private boolean isRowEmpty(Row row)
    {
        if (row == null)
        {
            return true;
        }
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++)
        {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取Excel2003图片
     *
     * @param sheet 当前sheet对象
     * @param workbook 工作簿对象
     * @return Map key:图片单元格索引（1_1）String，value:图片流PictureData
     */
    public static Map<String, List<PictureData>> getSheetPictures03(HSSFSheet sheet, HSSFWorkbook workbook)
    {
        Map<String, List<PictureData>> sheetIndexPicMap = new HashMap<>();
        List<HSSFPictureData> pictures = workbook.getAllPictures();
        if (!pictures.isEmpty() && sheet.getDrawingPatriarch() != null)
        {
            for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren())
            {
                if (shape instanceof HSSFPicture)
                {
                    HSSFPicture pic = (HSSFPicture) shape;
                    HSSFClientAnchor anchor = (HSSFClientAnchor) pic.getAnchor();
                    String picIndex = anchor.getRow1() + "_" + anchor.getCol1();
                    sheetIndexPicMap.computeIfAbsent(picIndex, k -> new ArrayList<>()).add(pic.getPictureData());
                }
            }
        }
        return sheetIndexPicMap;
    }

    /**
     * 获取Excel2007图片
     *
     * @param sheet 当前sheet对象
     * @param workbook 工作簿对象
     * @return Map key:图片单元格索引（1_1）String，value:图片流PictureData
     */
    public static Map<String, List<PictureData>> getSheetPictures07(XSSFSheet sheet, XSSFWorkbook workbook)
    {
        Map<String, List<PictureData>> sheetIndexPicMap = new HashMap<>();
        for (POIXMLDocumentPart dr : sheet.getRelations())
        {
            if (dr instanceof XSSFDrawing)
            {
                XSSFDrawing drawing = (XSSFDrawing) dr;
                for (XSSFShape shape : drawing.getShapes())
                {
                    if (shape instanceof XSSFPicture)
                    {
                        XSSFPicture pic = (XSSFPicture) shape;
                        XSSFClientAnchor anchor = pic.getPreferredSize();
                        CTMarker ctMarker = anchor.getFrom();
                        String picIndex = ctMarker.getRow() + "_" + ctMarker.getCol();
                        sheetIndexPicMap.computeIfAbsent(picIndex, k -> new ArrayList<>()).add(pic.getPictureData());
                    }
                }
            }
        }
        return sheetIndexPicMap;
    }

    /**
     * 格式化不同类型的日期对象
     * 
     * @param dateFormat 日期格式
     * @param val 被格式化的日期对象
     * @return 格式化后的日期字符
     */
    public String parseDateToStr(String dateFormat, Object val)
    {
        if (val == null)
        {
            return "";
        }
        String str;
        if (val instanceof Date)
        {
            str = DateUtils.parseDateToStr(dateFormat, (Date) val);
        }
        else if (val instanceof LocalDateTime)
        {
            str = DateUtils.parseDateToStr(dateFormat, DateUtils.toDate((LocalDateTime) val));
        }
        else if (val instanceof LocalDate)
        {
            str = DateUtils.parseDateToStr(dateFormat, DateUtils.toDate((LocalDate) val));
        }
        else
        {
            str = val.toString();
        }
        return str;
    }

    /**
     * 是否有对象的子列表
     */
    public boolean isSubList()
    {
        return !StringUtils.isEmpty(subFieldsMap);
    }

    /**
     * 是否有对象的子列表，集合不为空
     */
    public boolean isSubListValue(T vo)
    {
        return !StringUtils.isEmpty(subFieldsMap) && getListCellValue(vo) > 0;
    }

    /**
     * 获取集合的值
     */
    public int getListCellValue(Object obj)
    {
        Collection<?> value;
        int max = 0;
        try
        {
            for (String s : subMethods.keySet())
            {
                value = (Collection<?>) subMethods.get(s).invoke(obj);
                if (value.size() > max)
                {
                    max = value.size();
                }
            }
        }
        catch (Exception e)
        {
            return 0;
        }
        return max;
    }

    /**
     * 获取对象的子列表方法
     * 
     * @param name 名称
     * @param pojoClass 类对象
     * @return 子列表方法
     */
    public Method getSubMethod(String name, Class<?> pojoClass)
    {
        StringBuffer getMethodName = new StringBuffer("get");
        getMethodName.append(name.substring(0, 1).toUpperCase());
        getMethodName.append(name.substring(1));
        Method method = null;
        try
        {
            method = pojoClass.getMethod(getMethodName.toString(), new Class[] {});
        }
        catch (Exception e)
        {
            log.error("获取对象异常{}", e.getMessage());
        }
        return method;
    }
}
