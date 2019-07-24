package com.song.demo.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel写入工具类
 *
 * @Version 1.0
 */
public class ExcelExport {

	/**
	 * 写入Excel文件名
	 */
	private String fileName;

	/**
	 * 设定写入行数 默认为10万行
	 */
	private static final int ROW_COUNT = 100000;

	/**
	 * 设定写入Excel格式 默认为.xls格式
	 */
	private String excelType = ".xls";

	/**
	 * 工作簿对象
	 */
	private Workbook wb;

	/**
	 * 无参构造器, 需要setFileName文件名
	 */
	public ExcelExport() {
		this.wb = new HSSFWorkbook();
	}

	/**
	 * ExcelExport构造器
	 *
	 * @param fileName
	 *            Excel文件名
	 */
	public ExcelExport(String fileName) {
		this.wb = new HSSFWorkbook();
		this.fileName = fileName;
	}

	/**
	 * 通用Excel写入方法
	 *
	 * @param list
	 *            实体bean的集合
	 * @param header
	 *            装载实体属性名与表头的map< entity_name,table_name >集合
	 * @param response
	 *            response输出流
	 */
	public <T> void writeExcel(List<T> list, Map<String, String> header, HttpServletResponse response) throws Exception {
		writeExcel_xls(list, header,0, response);
	}

	public <T> void writeExcel(List<T> list, Map<String, String> header, Integer MergeLine,HttpServletResponse response) throws Exception {
		writeExcel_xls(list, header,MergeLine, response);
	}

	public void writeExcel(HttpServletResponse response) throws Exception {
		this.writeDates2Response(wb, fileName + excelType, response);
	}

	public <T> void putExcelShett(String sheetName, List<T> list, Map<String, String> header){
		putExcelShett(sheetName,list,header,0);
	}

	public <T> void putExcelShett(String sheetName, List<T> list, Map<String, String> header,Integer MergeLine) {
		List<Map<String, Object>> datas = this.genDatas4WB(list);
		wb = this.preparedDatas2WB_xls(datas,header,sheetName,MergeLine);
	}



	/**
	 * 写入Excel(97-2003版, xls格式)
	 *
	 * @param list
	 *            实体bean的集合
	 * @param header
	 *            装载实体属性名与表头的map< entity_name,table_name >集合
	 * @param response
	 *            response输出流
	 */
	private <T> void writeExcel_xls(List<T> list, Map<String, String> header,Integer MergeLine, HttpServletResponse response) throws Exception {
		List<Map<String, Object>> datas = this.genDatas4WB(list);
		this.wb = new HSSFWorkbook();
		wb = this.preparedDatas2WB_xls(datas, header,null,MergeLine);
		this.writeDates2Response(wb, fileName + excelType, response);
	}

	/**
	 * 实体参数名与值的map键值对
	 *
	 * @param list
	 *            实体bean的集合
	 * @return 实体map键值对的List集合
	 */
	private <T> List<Map<String, Object>> genDatas4WB(List<T> list) {
		List<Map<String, Object>> datas = new ArrayList<>();
		try {
			if (null != list && !list.isEmpty()) {
				for (T em : list) {
					Map<String, Object> emValues = this.getPropertyValues(em);
					datas.add(emValues);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}

	/**
	 * 写入Excel工作簿(97-2003版, xls格式)
	 *
	 * @param list
	 *            实体map键值对的List集合
	 * @param header
	 *            装载实体属性名与表头的map< entity_name,table_name >集合
	 */
	private Workbook preparedDatas2WB_xls(List<Map<String, Object>> datas, Map<String, String> header,String sheetName,Integer MergeLine){

		if ((datas == null || datas.isEmpty()) && header != null) {
			datas = new ArrayList<>();
			Map<String, Object> headerTmp = new HashMap<>();
			for (String key : header.keySet()) {
				headerTmp.put(key,null);
			}
			datas.add(headerTmp);
		}

		//存储最大列宽
		Map<Integer,Integer> maxWidth = new HashMap<>();

		if (null != header && !header.isEmpty()) {
			int dataRowCount = datas.size();
			int over = (dataRowCount % ROW_COUNT != 0) ? 1 : 0;
			int sheetCount = (dataRowCount / ROW_COUNT) + over;

			// 创建HSSFWorkbook对象
			if(this.wb == null){
				this.wb = new HSSFWorkbook();
			}

			CellStyle cellStyle = getHSSFCell();
			CellStyle headCellStyle = getHeadHSSFCell();
			// 创建HSSFSheet对象
			for (int i = 0; i < sheetCount; i++) {
				int fromIndex = i * ROW_COUNT;
				int toIndex = (i + 1) * ROW_COUNT;
				if (toIndex > dataRowCount) {
					toIndex = dataRowCount;
				}

				if(StringUtils.isBlank(sheetName)){
					sheetName = "sheet_" + i;
				}else {
					if(i != 0){
						sheetName += "_" + i;
					}
				}
				List<Map<String, Object>> subDatas = datas.subList(fromIndex, toIndex);

				HSSFSheet sheet = (HSSFSheet) wb.createSheet(sheetName);
				sheet.setColumnWidth(1,15000);

				List<HSSFRow> heads = new ArrayList<>();
				List<Map<String ,Integer>> cellRange = new ArrayList<>();
				List<String> temValues = new ArrayList<>();

				Set<Map.Entry<String, String>> headEntrySet = header.entrySet();

				int headIdx = 0;
				int headRow = 0;

				for (Iterator<Map.Entry<String, String>> headIte = headEntrySet.iterator(); headIte.hasNext();) {
					Map.Entry<String, String> entry = headIte.next();
					String[] values = (entry.getValue() == null ? "" : entry.getValue()).split(",");
					int row = 0;
					String onValue = null;
					for (String value : values){
						if(heads.size()<= row){
							HSSFRow hssfRow = sheet.createRow(row);
							setHeadHSSFRow(hssfRow);
							heads.add(hssfRow);
						}

						if(cellRange.size()<= row){
							cellRange.add(new HashMap<>());
						}

						if(temValues.size()<= row){
							temValues.add(null);
						}

						boolean endRow = false;
						boolean endIdx = false;
						List<Map<String ,Integer>> temCellRange = new ArrayList<>();
						if(value.equals(onValue)){
							for(int j = row-1; j >= 0; j--){
								if(cellRange.get(j).size()>0 && cellRange.get(j).containsKey("firstRow")){
									cellRange.get(j).put("lastRow",row);
									cellRange.get(j).put("lastCol",headIdx);
									temValues.set(j,null);
									break;
								}
							}
						}else {
							endRow = true;
						}

						if(headIdx > 0 && cellRange.size() > 0 &&value.equals(temValues.get(row))){
							cellRange.get(row).put("lastRow",row);
							cellRange.get(row).put("lastCol",headIdx);
						}else {
							endIdx = true;
						}

						if(endRow || row== values.length -1 ){
							for(int j = row-1; j >= 0; j--){
								if(cellRange.get(j).size()==4 && cellRange.get(j).get("firstCol") == headIdx){
									temCellRange .add(cellRange.get(j));
									break;
								}
							}
						}
						if(endIdx ||  headIdx == header.size()-1){
							if(cellRange.get(row).size()==4){
								temCellRange .add(cellRange.get(row));
							}
						}

						if(temCellRange.size()>0){
							for(Map<String,Integer> map : temCellRange){
								if(map.size()==4) {
									CellRangeAddress region = new CellRangeAddress(map.get("firstRow"), map.get("lastRow"),
											map.get("firstCol"), map.get("lastCol"));
									sheet.addMergedRegion(region);
									map.clear();
								}
							}
						}

						if(endRow && endIdx){
							cellRange.set(row,new HashMap<>());
							cellRange.get(row).put("firstRow",row);
							cellRange.get(row).put("firstCol",headIdx);
						}

						HSSFCell cell = heads.get(row).createCell(headIdx, Cell.CELL_TYPE_STRING);

						cell.setCellStyle(headCellStyle);
						cell.setCellValue(value);
						onValue = value;
						temValues.set(row,value);
						row ++;
						headRow = row>headRow?row:headRow;
					}
					headIdx++;
				}

				// 创建HSSFRow对象
				int subSize = subDatas.size();

				String[] history = new String[MergeLine];
				cellRange = new ArrayList<>();
				for (int j = 0; j < subSize; j++) {
					HSSFRow row = sheet.createRow(j + headRow);
					setHSSFRow(row);
					Map<String, Object> keyValues = subDatas.get(j);
					int colIndex = 0;
					Set<String> colSets = header.keySet();
					// 合并单元格处理
					for (Iterator<String> colIte = colSets.iterator(); colIte.hasNext();) {
						String key = colIte.next();
						String value = "";
						if ("role".equalsIgnoreCase(key)) {
							value = null == keyValues.get(key) ? "" : keyValues.get(key).toString();
						} else {
							value = null == keyValues.get(key) ? "" : keyValues.get(key).toString();
						}

						if(colIndex < MergeLine){
							if(cellRange.size() <= colIndex){
								cellRange.add(new HashMap<>());
							}
							if(!value.equals(history[colIndex])){
								if(cellRange.get(colIndex) != null && cellRange.get(colIndex).size()==4){
									CellRangeAddress region = new CellRangeAddress(cellRange.get(colIndex).get("firstRow"),
											cellRange.get(colIndex).get("lastRow"),
											cellRange.get(colIndex).get("firstCol"),
											cellRange.get(colIndex).get("lastCol"));
									sheet.addMergedRegion(region);
								}
								cellRange.set(colIndex,new HashMap<>());
								cellRange.get(colIndex).put("firstRow",j+headRow);
								cellRange.get(colIndex).put("firstCol",colIndex);

							}else {
								cellRange.get(colIndex).put("lastRow",j+headRow);
								cellRange.get(colIndex).put("lastCol",colIndex);
								if(j+1 >= subSize){
									CellRangeAddress region = new CellRangeAddress(cellRange.get(colIndex).get("firstRow"),
											cellRange.get(colIndex).get("lastRow"),
											cellRange.get(colIndex).get("firstCol"),
											cellRange.get(colIndex).get("lastCol"));
									sheet.addMergedRegion(region);
								}
							}
							history[colIndex] =value;
						}

						HSSFCell cell = row.createCell(colIndex, Cell.CELL_TYPE_STRING);
						cell.setCellValue(value);

						int length = value.getBytes().length  * 256 + 300;
						maxWidth.put(colIndex,Math.max(length,maxWidth.containsKey(colIndex)?maxWidth.get(colIndex):0));

						cell.setCellStyle(cellStyle);
						colIndex++;
					}
				}

				// 列宽自适应
				for (int j = 0; j < maxWidth.size(); j++) {
					if(!maxWidth.containsKey(j) || maxWidth.get(j) < 3500){
						sheet.setColumnWidth(j,3500);
					}else if(maxWidth.get(j) > 15000){
						sheet.setColumnWidth(j,15000);
					}else{
						sheet.setColumnWidth(j,maxWidth.get(j));
					}
				}
			}
		}

		return wb;
	}

	private void setHeadHSSFRow(HSSFRow hssfRow){
		setHSSFRow(hssfRow);
	}

	private CellStyle getHeadHSSFCell(){
		CellStyle borderStyle = wb.createCellStyle();
		borderStyle.setBorderBottom(CellStyle.BORDER_THIN);
		borderStyle.setBorderTop(CellStyle.BORDER_THIN);
		borderStyle.setBorderLeft(CellStyle.BORDER_THIN);
		borderStyle.setBorderRight(CellStyle.BORDER_THIN);

		borderStyle.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
		borderStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		borderStyle.setWrapText(true);//设置自动换行

		borderStyle.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);// 设置背景色
		borderStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		Font font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
		borderStyle.setFont(font);
		return borderStyle;
	}

	private void setHSSFRow(HSSFRow hssfRow){
		// 高度
		hssfRow.setHeightInPoints(24);
	}

	private CellStyle getHSSFCell() {
		CellStyle borderStyle = wb.createCellStyle();
		borderStyle.setBorderBottom(CellStyle.BORDER_THIN);
		borderStyle.setBorderTop(CellStyle.BORDER_THIN);
		borderStyle.setBorderLeft(CellStyle.BORDER_THIN);
		borderStyle.setBorderRight(CellStyle.BORDER_THIN);

		borderStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
		borderStyle.setWrapText(true);//设置自动换行
		return borderStyle;
	}



	/**
	 * 通过输出流 生成Excel文件
	 *
	 * @param wb
	 *            工作簿对象
	 * @param fileName
	 *            Excel文件名
	 * @param response
	 *            response输出流
	 */
	private void writeDates2Response(Workbook wb, String fileName, HttpServletResponse response) {
		OutputStream os = null;
		try {
			fileName = java.net.URLEncoder.encode(fileName,"UTF-8");
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			// response.setContentType("application/octet-stream;charset=UTF-8");
			response.setContentType("application/msexcel");
			os = response.getOutputStream();
			wb.write(os);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 获取实体参数名与值的map键值对
	 *
	 * @param obj
	 *            动态加载类
	 * @return 实体参数名与值的map键值对
	 * @throws Exception
	 */
	private Map<String, Object> getPropertyValues(Object obj) throws Exception {
		Map<String, Object> properties = new HashMap<>();

		if (null != obj) {

			if(obj instanceof Map){
				return (Map<String, Object>) obj;
			}

			Class<? extends Object> cls = obj.getClass();

			Method[] methods = cls.getMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				String methodReTypeName = method.getReturnType().getSimpleName();

				if (methodName.contains("get") && !methodReTypeName.contains("Class")) {

					String fieldName = methodName.replace("get", "");

					String value = "";
					if (methodReTypeName.toLowerCase().contains("date")) {

						Object vo = method.invoke(obj);
						if (null != vo) {
							Date datev = (Date) vo;
							value = formatDate(datev, "yyyy-MM-dd HH:mm:ss");
						}

					} else {
						value = null == method.invoke(obj) ? "" : method.invoke(obj).toString();
					}

					properties.put(fieldName, value);
				}
			}
		}

		return properties;
	}

	/**
	 * 日期格式转化
	 *
	 * @param date
	 *            日期Date
	 * @param type
	 *            日期格式
	 * @return 时间格式String
	 */
	private String formatDate(Date date, String type) {
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		String result = null;
		try {
			if (null != date) {
				result = sdf.format(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getFileName() {
		return fileName;
	}

	/**
	 * 设置Excel文件名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
