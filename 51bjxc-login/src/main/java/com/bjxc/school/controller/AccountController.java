package com.bjxc.school.controller;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bjxc.Result;
import com.bjxc.school.Account;
import com.bjxc.school.AccountTransaction;
import com.bjxc.school.Bank;
import com.bjxc.school.ListResultWithCount;
import com.bjxc.school.TopUp;
import com.bjxc.school.Withdraw;
import com.bjxc.school.service.AccountService;
import com.bjxc.school.service.AuthenticateService;
import com.bjxc.school.service.ExportResponseService;
import com.bjxc.userdetails.PlatformUserDetail;
import com.bjxc.userdetails.TokenUserDetailsService;
import com.bjxc.userdetails.UserDetails;

@RestController
public class AccountController {
	
	@Resource
	private AccountService accountService;
	
	@Resource
	private TokenUserDetailsService tokenService;
	
	@Resource
	private AuthenticateService authenticateService;
	
	@Resource
	private ExportResponseService exportResponseService;
	
	@RequestMapping(value = "/bankList")
	public Result getBankList()
	{
		Result result = new Result();
		
		List<Bank> list = accountService.getBankList();
		
		result.success(list);
		
		return result;
	}
	
	@RequestMapping(value = "/setAccountPassword", method=RequestMethod.POST)
	public Result setAccountPassword(String token, String password)
	{
		Result result = new Result();
		
		try
		{
			UserDetails userDetails = tokenService.loadUserByToken(token);
			if (userDetails == null) {
				result.error("请先登录");
			}
			else{
				
				Account account = accountService.createAccount(password, userDetails.getId());
				
				if (account != null) {
					result.success(account);
				}
				else
				{
					result.error("设置失败");
				}
				
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		
		
		return result;
	}
	
	@RequestMapping(value = "/changeAccountPassword", method=RequestMethod.POST)
	public Result setAccountPassword(String token, String oldPassword, String newPassword)
	{
		Result result = new Result();
		
		try
		{
			UserDetails userDetails = tokenService.loadUserByToken(token);
			if (userDetails == null) {
				result.error("请先登录");
			}
			else{
				
				Boolean changeResult = accountService.changePassword(userDetails.getId(), oldPassword, newPassword);
				
				if (changeResult) {
					result.success("修改成功");
				}
				else
				{
					result.error("修改失败");
				}
				
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		
		
		return result;
	}
	
	@RequestMapping(value = "/checkAccountPwd")
	public Result checkAccountPwd(String token, String password)
	{
		Result result = new Result();
		
		try
		{
			UserDetails userDetails = tokenService.loadUserByToken(token);
			if (userDetails == null) {
				result.error("请先登录");
			}
			else{
				
				Account account = accountService.checkPassword(password, userDetails.getId());
				
				if (account != null) {
					result.success(account);
				}
				else
				{
					result.setCode(701);
					result.error("密码不正确");
				}
			
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		
		
		return result;
	}
	
	@RequestMapping(value="/bankCardList")
	public Result getBankCardList(String token)
	{
		Result result = new Result();
		
		try
		{
			UserDetails userDetails = tokenService.loadUserByToken(token);
			if (userDetails == null) {
				result.error("请先登录");
			}
			else
			{
				result.success(accountService.getBankCardList(userDetails.getId()));
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		
		
		return result;
	}
	@RequestMapping(value="/addBankCard", method=RequestMethod.POST)
	public Result addBankCard(String token,String cardNo, String cardHolder, Integer bankId, String mobile, String checkCode)
	{
		Result result = new Result();
		
		try
		{
			UserDetails userDetails = tokenService.loadUserByToken(token);
			if (userDetails == null) {
				result.error("请先登录");
			}
			else
			{
				Account account = accountService.getAccount(userDetails.getId());
				if (account == null) {
					result.error("还没有账户");
					return result;
					
				}
				if (authenticateService.checkBindCardCode(checkCode, mobile))
				{
					accountService.addBankCard(userDetails.getId(), cardNo, cardHolder, bankId);
					result.success("添加成功");
				}
				else
				{
					result.error("验证码不正确");
				}
				
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		return result;
	}
	@RequestMapping(value="/deleteBankCard", method=RequestMethod.POST)
	public Result addBankCard(String token,Integer bankCardId)
	{
		Result result = new Result();
		
		try
		{
			UserDetails userDetails = tokenService.loadUserByToken(token);
			if (userDetails == null) {
				result.error("请先登录");
			}
			else
			{
				if (accountService.deleteBankCard(bankCardId) == 1)
				{
					result.success("删除成功");
					
				}
				else
				{
					result.error("删除失败");
				}
				
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value="/accountInfo", method=RequestMethod.GET)
	public Result getAccountInfo(String token)
	{
		Result result = new Result();
		try
		{
			UserDetails userDetails = tokenService.loadUserByToken(token);
			if (userDetails == null) {
				result.error("请先登录");
			}
			else
			{
				result.success(accountService.getAccount(userDetails.getId()));
				
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		
		return result;
	}
	@RequestMapping(value="/payRecord", method=RequestMethod.GET)
	public Result getPayRecord(String token, Integer startIndex, Integer length)
	{
		Result result = new Result();
		try
		{
			UserDetails userDetails = tokenService.loadUserByToken(token);
			if (userDetails == null) {
				result.error("请先登录");
			}
			else
			{
				result.success(accountService.getPayRecord(userDetails.getId(), startIndex, length));
				
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		
		return result;
	}
	
	@RequestMapping(value="/generateTopUpOrder", method=RequestMethod.POST)
	public Result generateTopUpOrder(String token, Integer mny)
	{
		Result result = new Result();
		try
		{
			UserDetails userDetails = tokenService.loadUserByToken(token);
			if (userDetails == null) {
				result.error("请先登录");
			}
			else
			{
				Account account = accountService.getAccount(userDetails.getId());
				if (account == null) {
					result.error("还没有开账户");
				}
				else
				{
					TopUp topUp = accountService.addTopUp(account.getId(), mny);
					
					result.success(topUp);
				}
				
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		
		return result;
	}
	
	@RequestMapping(value="/withdraw", method=RequestMethod.POST)
	public Result withdraw(String token, Integer bankCardId,Integer mny)
	{
		Result result = new Result();
		
		try
		{
			UserDetails userDetails = tokenService.loadUserByToken(token);
			if (userDetails == null) {
				result.error("请先登录");
			}
			else
			{
				Account account = accountService.getAccount(userDetails.getId());
				if (account == null) {
					result.error("还没有开账户");
				}
				else if (mny <= 0) {
					result.error("提现金额必须大于0");
				}
				else if (account.getMny() < mny) {
					result.error("余额不足");
				}
				else
				{
					Withdraw withdraw = accountService.addWithdraw(account, mny,bankCardId);

					result.success("已申请，请等待");
				}
				
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		return result;
	}
	
	
	@RequestMapping(value="/withdrawList", method=RequestMethod.GET)
	public Result withdrawList(String token, Integer status, Integer startIndex, Integer length, @RequestParam(value = "startTime", defaultValue = "0", required = false)Long startTime,@RequestParam(value = "endTime", defaultValue = "0", required = false)Long endTime)
	{
		Result result = new Result();
		
		try
		{
			//UserDetails userDetails = tokenService.loadUserByToken(token);
			PlatformUserDetail platformUserDetail = tokenService.loadPlatformUserByToken(token);
			Date startTimeDate = null;
			Date endTimeDate = null;
			if (startTime > 0 || endTime > 0) {
				
				try
				{
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String startDate = dateFormat.format(new Date(startTime));
					String endDate = dateFormat.format(new Date(endTime));
					SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					startTimeDate = dateTimeFormat.parse(startDate +" 00:00:00");
					endTimeDate = dateTimeFormat.parse(endDate +" 23:59:59");
				}
				catch (Exception e) {
					// TODO: handle exception
					result.setCode(700);
					result.setMessage(e.getMessage());
					//result.error(e.getMessage());
				}
				

			}
			
			List<Withdraw> list = accountService.getWithdrawList(status, startIndex, length, startTimeDate, endTimeDate);
			
			Integer count = accountService.getWithdrawListCount(status, startTimeDate, endTimeDate);
			
			ListResultWithCount listResultWithCount = new ListResultWithCount();
			listResultWithCount.setList(list);
			listResultWithCount.setCount(count);
			
			result.success(listResultWithCount);
			
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value="/accountList")
	public Result getAccountList(String token, String searchText, Integer startIndex, Integer length)
	{
		Result result = new Result();
		
		try
		{
			PlatformUserDetail platformUserDetail = tokenService.loadPlatformUserByToken(token);
			
			List<Account> list = accountService.getAccountList(searchText, startIndex, length);
			Integer count = accountService.getAccountListCount(searchText);
			
			ListResultWithCount listResultWithCount = new ListResultWithCount();
			listResultWithCount.setCount(count);
			listResultWithCount.setList(list);
			
			result.success(listResultWithCount);
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		return result;
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/exportAccount", method = RequestMethod.GET)
	public Result exportAccountList(String searchText, HttpServletResponse response)
	{
		Result result = new Result();
		
		try
		{
			/*
			response.setCharacterEncoding("UTF-8");
			String fileName = "用户账户信息";
			fileName = new String(fileName.getBytes("GBK"), "ISO8859_1");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");

			response.setDateHeader("Expires", 0);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			*/
			response = exportResponseService.setExportResponse(response, "用户账户信息");
			
			
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
			sheet.setColumnWidth(0, 4500);
			sheet.setColumnWidth(1, 4500);
			sheet.setColumnWidth(2, 4500);
			sheet.setColumnWidth(3, 4500);
			
			f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			sheet.autoSizeColumn(1, true);
			// row.setHeightInPoints(30); //设置行高
			style.setFont(f);
			sheet.autoSizeColumn(1);
			// 获取第一行的每一个单元格
			cell = row.createCell(0);
			cell.setCellValue("姓名"); // 头部
			cell.setCellStyle(style);
			
			cell = row.createCell(1);
			cell.setCellValue("身份证号"); // 头部
			cell.setCellStyle(style);
			
			cell = row.createCell(2);
			cell.setCellValue("电话号"); // 头部
			cell.setCellStyle(style);
			
			cell = row.createCell(3);
			cell.setCellValue("钱包余额"); // 头部
			cell.setCellStyle(style);
			
			
			List<Account> list = accountService.getAccountList(searchText, 0, Integer.MAX_VALUE);
			for(int i = 0; i < list.size(); i++)
			{
				Account account = list.get(i);
				row = sheet.createRow(i + 1);
				row.createCell((short) 0).setCellValue(account.getUserName());
				row.createCell((short) 1).setCellValue(account.getIdCard());
				row.createCell((short) 2).setCellValue(account.getMobile());
				row.createCell((short) 3).setCellValue(account.getMny());
			}
			
			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
			
			
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		
		return null;
	}
	
	@RequestMapping(value="/accountTransaction")
	public Result getTransactionByAccountId(String token, Integer accountId)
	{
		Result result = new Result();
		try
		{
			List<AccountTransaction> list = accountService.getTransactionByAccountId(accountId);
			
			result.success(list);
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		return result;
	}
	@RequestMapping(value="/userAccountTransaction")
	public Result getUserTransactionByAccountId(String token, Integer startIndex, Integer length)
	{
		Result result = new Result();
		try
		{
			UserDetails userDetails = tokenService.loadUserByToken(token);
			Account account = accountService.getAccount(userDetails.getId());
			if (account == null) {
				result.error("还没有账户");
			}
			else
			{
				List<AccountTransaction> list = accountService.getTransactionByAccountIdForApp(account.getId(),startIndex,length);//accountService.getTransactionByAccountId(account.getId());
				result.success(list);
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			result.setCode(701);
			result.error(e.getMessage());
		}
		
		return result;
	}
	//localhost:8091/exportUserTransaction?accountId=2
	@RequestMapping(value = "/exportUserTransaction")
	public Result exportUserTransaction(HttpServletResponse response, Integer accountId)
	{
		String[] columnName = new String[]{"交易事件","交易金额","交易日期","钱包余额"};
		List<AccountTransaction> list = accountService.getTransactionByAccountId(accountId);
		String[][] data = new String[list.size()][columnName.length];
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for(int i = 0; i < list.size(); i++)
		{
			AccountTransaction accountTransaction = list.get(i);
			data[i][0] = getTransactionTypeName(accountTransaction.getTransactionType());
			data[i][1] = accountTransaction.getTransactionMny().toString();
			data[i][2] = formatter.format(accountTransaction.getTransactionDate());
			data[i][3] = accountTransaction.getAccountMny().toString();
		}
		
		exportResponseService.exportExcel(response, "消费记录", columnName, data);
		return null;
		
	}
	
	//姓名	电话号	钱包余额	提现金额	申请时间	申请状态	
	@RequestMapping(value = "/exportWithdraw")
	public Result exportWithdraw(HttpServletResponse response, String token, Integer status, @RequestParam(value = "startTime", defaultValue = "0", required = false)Long startTime,@RequestParam(value = "endTime", defaultValue = "0", required = false)Long endTime)
	{
		Date startTimeDate = null;
		Date endTimeDate = null;
		if (startTime > 0 || endTime > 0) {
			
			try
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String startDate = dateFormat.format(new Date(startTime));
				String endDate = dateFormat.format(new Date(endTime));
				SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				startTimeDate = dateTimeFormat.parse(startDate +" 00:00:00");
				endTimeDate = dateTimeFormat.parse(endDate +" 23:59:59");
			}
			catch (Exception e) {

			}
			

		}
		String[] columnName = new String[]{"姓名","电话号","钱包余额","提现金额","申请时间","申请状态"};
		List<Withdraw> list = accountService.getWithdrawList(status, 0, Integer.MAX_VALUE, startTimeDate, endTimeDate);
		String[][] data = new String[list.size()][columnName.length];
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for(int i = 0; i < list.size(); i++)
		{
			Withdraw withdraw = list.get(i);
			data[i][0] = withdraw.getUserName();
			data[i][1] = withdraw.getMobile();
			data[i][2] = withdraw.getAccountMny().toString();
			data[i][3] = withdraw.getMny().toString();
			data[i][4] = formatter.format(withdraw.getApplyTime());
			data[i][5] = withdraw.getStatus() == 0 ? "待处理": "已处理";
		}
		
		exportResponseService.exportExcel(response, "提现记录", columnName, data);
		return null;
		
	}
	
	private String getTransactionTypeName(Integer type)
	{
		/*1-学员报名 2-网络教育付费账号 3-先付后学 4-先学后付 5-钱包充值 6-钱包提款 7-招生奖励*/
		String name = "";
		switch (type) {
		case 1:
			name = "学员报名";
			break;
		case 2:
			name = "网络教育付费账号";
			break;
		case 3:
			name = "先付后学";
			break;
		case 4:
			name = "先学后付";
			break;
		case 5:
			name = "钱包充值";
			break;
		case 6:
			name = "钱包提款";
			break;
		case 7:
			name = "招生奖励";
			break;


		default:
			name = "消费";
			break;
		}
		return name;
	}
	/*
	@RequestMapping(value="/transactionByAccount", method=RequestMethod.POST)
	public Result transactionByAccount(String token, Integer transactionType, Integer mny, Integer businessId)
	{
		Result result = new Result();
		
		try
		{
			UserDetails userDetails = tokenService.loadUserByToken(token);
			Account account = accountService.getAccount(userDetails.getId());
			if (account == null) {
				result.error("还没有开账户");
			}
			else if (mny < 0) {
				result.error("支付金额必须大于0");
			}
			else if (accountService.isConsume(transactionType) && account.getMny() < mny) {
				result.error("账户余额不足");
			}
			else if (transactionType != 1 && transactionType != 2 && transactionType != 3 && transactionType != 4 && transactionType != 5 && transactionType != 6 && transactionType != 7) {
				result.error("无此种交易类型");
			}
			else {
				accountService.addAccountTransaction(transactionType, mny, account, businessId);
				result.success("支付成功");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		return result;
	}
	*/
	
	@RequestMapping(value="/finishTransfer",method=RequestMethod.POST)
	public Result finishTransfer(String token, Integer withdrawId)
	{
		Result result = new Result();
		try
		{
			PlatformUserDetail platformUserDetail = tokenService.loadPlatformUserByToken(token);
			if (accountService.updateWithdrawStatus(withdrawId, 1) == 1) {
				result.success("转账完成");
			}
			else
			{
				result.error("转账失败");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		return result;
	}
	@RequestMapping(value="/transactionMonthSum")
	public Result getTransactionMonthSum(String token)
	{
		Result result = new Result();
		try
		{
			UserDetails userDetails = tokenService.loadUserByToken(token);
			Account account = accountService.getAccount(userDetails.getId());
			result.setData(accountService.getTransactionMonthSum(account.getId()));
			

		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		return result;
	}
	
	
	
	
	

}
