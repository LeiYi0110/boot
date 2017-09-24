package com.bjxc.school.service;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;


@Service
public class ExportResponseService {
	
	public  HttpServletResponse setExportResponse(HttpServletResponse response, String fileName) {
		
		response.setCharacterEncoding("UTF-8");
//		String fileName = "用户账户信息";
		try {
			fileName = new String(fileName.getBytes("GBK"), "ISO8859_1");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");

		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		
		return response;
		
	}
	@SuppressWarnings("deprecation")
	public void exportExcel(HttpServletResponse response, String fileName, String[] columnName, String[][] data)
	{
		try
		{
			
			response.setCharacterEncoding("UTF-8");
			fileName = new String(fileName.getBytes("GBK"), "ISO8859_1");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");

			response.setDateHeader("Expires", 0);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");

			
			
			HSSFSheet sheet = null;
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFFont f = wb.createFont();
			// for (ServiceStation string : tataion) { // 循环添加sheet
			int exportRow = 1;
			//List<Student> exportlist = studentService.exceporStudent(chargemode,paymode,stationName,sex,insId, stationId,traintype,classtype,searchText, time1, time2, subjectid);
			sheet = wb.createSheet("用户账户信息");
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = null;
			HSSFCellStyle style = wb.createCellStyle();
			style.setWrapText(true);// 设置自动换行
			// 第一个参数代表列id(从0开始),第2个参数代表宽度值
			
			
			
			
			f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			sheet.autoSizeColumn(1, true);
			// row.setHeightInPoints(30); //设置行高
			style.setFont(f);
			sheet.autoSizeColumn(1);
			// 获取第一行的每一个单元格
			for(int i = 0; i < columnName.length; i++)
			{
				sheet.setColumnWidth(i, 4500);
				
				cell = row.createCell(i);
				cell.setCellValue(columnName[i]); 
				cell.setCellStyle(style);
			}
			
			for(int i = 0; i < data.length; i++)
			{
				row = sheet.createRow(i + 1);
				for(int j = 0; j < data[i].length; j++)
				{
					row.createCell((short) j).setCellValue(data[i][j]);
				}
			}
			
			
			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
			
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
