package com.bjxc.school.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bjxc.Result;
import com.bjxc.school.AppRecruitSetting;
import com.bjxc.school.CoachAppSetting;
import com.bjxc.school.FunctionConfigResult;
import com.bjxc.school.Insititution;
import com.bjxc.school.ListResult;
import com.bjxc.school.Solution;
import com.bjxc.school.SolutionAuthGroup;
import com.bjxc.school.SolutionListResult;
import com.bjxc.school.service.InstitutionService;
import com.bjxc.school.service.SolutionService;
import com.bjxc.userdetails.PlatformUserDetail;
import com.bjxc.userdetails.TokenUserDetailsService;
import com.bjxc.userdetails.UserDetails;

@RestController
public class SolutionController {
	
	@Resource
	private SolutionService solutionService;
	
	@Resource
	private InstitutionService institutionService;
	
	@Resource
	private TokenUserDetailsService tokenService;
	
	
	
	@RequestMapping(value="/solutionList")
	public Result getSolutionList(String token, @RequestParam(value = "startIndex", defaultValue = "0", required = false)Integer startIndex, @RequestParam(value = "length", defaultValue = "10000", required = false)Integer length)
	{
		Result result = new Result();
		
		try
		{
			PlatformUserDetail userDetail = tokenService.loadPlatformUserByToken(token);
			if (userDetail == null) {
				
				result.error("用户未登录");
			}
			else
			{
				List<Solution> solutionList = solutionService.getSolutionList(startIndex,length);
				Integer count = solutionService.getSolutionListCount();
				
				ListResult listResult = new ListResult();
				
				listResult.setList(solutionList);
				listResult.setCount(count);
			
				
				result.success(listResult);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		
		
		return result;
	}
	
	@RequestMapping(value="/insList")
	public Result getInsList(String token, Integer solutionId)
	{
		Result result = new Result();
		try
		{
			PlatformUserDetail userDetail = tokenService.loadPlatformUserByToken(token);
			if (userDetail == null) {
				result.error("用户未登录");
			}
			else
			{
				List<Insititution> list = institutionService.getInsitutionList(solutionId);
				result.success(list);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		return result;
	}
	@RequestMapping(value="/addSolution")
	public Result addSolution(String token,String solutionName, String authGroupList)
	{
		Result result = new Result();
		
		try
		{
			PlatformUserDetail userDetail = tokenService.loadPlatformUserByToken(token);
			if (userDetail == null) {
				
				result.error("用户未登录");
			}
			else
			{
				String[] authGroupArray = authGroupList.split(",");
				
				Integer insertResult = solutionService.addSolution(solutionName, authGroupArray); 
				
				if (insertResult == 0) {
					
					result.error("添加失败");
				}
				else
				{
					result.success("添加成功");
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		return result;
	}
	
	@RequestMapping(value="/authGroupList")
	public Result getAuthGroupList(String token)
	{
		
		Result result = new Result();
		
		try
		{
			PlatformUserDetail userDetail = tokenService.loadPlatformUserByToken(token);
			if (userDetail == null) {
				
				result.error("用户未登录");
			}
			else
			{
				List<SolutionAuthGroup> list = solutionService.getSolutionAuthGroupList();
				
				result.success(list);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		
		return result;
	}
	@RequestMapping(value="/functionConfigSolutionAuthList")
	public Result getFunctionConfigSolutionAuthList(String token)
	{
		Result result = new Result();
		try
		{
			PlatformUserDetail platformUserDetail = tokenService.loadPlatformUserByToken(token);
			
			//result.success(solutionService.getFunctionConfigSolutionAuthGroupList(platformUserDetail.getInsId()));
			
			FunctionConfigResult functionConfigResult = new FunctionConfigResult();
			functionConfigResult.setMyFunctionList(solutionService.getSchoolSolutionAuthGroupList(platformUserDetail.getInsId()));
			functionConfigResult.setFunctionStore(solutionService.getFunctionConfigSolutionAuthGroupList(platformUserDetail.getInsId()));
			result.success(functionConfigResult);
			//result.success(solutionService.getFunctionConfigSolutionAuthGroupList(1));
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		return result;
	}
	@RequestMapping(value="/schoolSolutionAuthList")
	public Result getSchoolSolutionAuthList(String token)
	{
		Result result = new Result();
		try
		{
			PlatformUserDetail platformUserDetail = tokenService.loadPlatformUserByToken(token);
			
			result.success(solutionService.getSchoolSolutionAuthGroupList(platformUserDetail.getInsId()));
			
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="/coachAppSetting")
	public Result getCoachAppSetting(String token)
	{
		Result result = new Result();
		try
		{
			PlatformUserDetail platformUserDetail = tokenService.loadPlatformUserByToken(token);
			result.success(solutionService.getInsCoachAppSetting(platformUserDetail.getInsId()));
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		return result;
	}
	@RequestMapping(value="/app/coachAppSetting")
	public Result getCoachAppSettingForApp(String token)
	{
		Result result = new Result();
		try
		{
			UserDetails userDetails = tokenService.loadUserByToken(token);
			Insititution insititution = institutionService.getCoachIns(userDetails.getId());
			result.success(solutionService.getInsCoachAppSetting(insititution.getId()));
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		return result;
	}
	@RequestMapping(value="/setCoachAppSetting", method=RequestMethod.POST)
	public Result setCoachAppSetting(String token, CoachAppSetting coachAppSetting)
	{
		Result result = new Result();
		try {
			PlatformUserDetail platformUserDetail = tokenService.loadPlatformUserByToken(token);
			coachAppSetting.setInsId(platformUserDetail.getInsId());
			solutionService.updateCoachAppSetting(coachAppSetting);
			result.success("修改成功");
		} catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="/initCoachAppSetting",method=RequestMethod.POST)
	public Result initCoachAppSetting(String token, Integer insId) {
		Result result = new Result();
		try
		{
			PlatformUserDetail platformUserDetail = tokenService.loadPlatformUserByToken(token);
			if (platformUserDetail.getInsId().intValue() != 0) {
				result.error("需要平台管理员角色");
			}
			else
			{
				solutionService.initCoachAppSetting(insId);
				result.success("设置成功");
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		return result;
		
	}
	
	@RequestMapping(value="/appRecruitSetting")
	public Result AppRecruitSettingForWeb(String token)
	{
		Result result = new Result();
		
		try
		{
			PlatformUserDetail platformUserDetail = tokenService.loadPlatformUserByToken(token);
			AppRecruitSetting appRecruitSetting = solutionService.getAppRecruitSetting(platformUserDetail.getInsId());
			result.success(appRecruitSetting);
			
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		return result;
	}
	@RequestMapping(value="/app/AppRecruitSetting")
	public Result AppRecruitSettingForApp(String token)
	{
		Result result = new Result();
		
		try
		{
			UserDetails userDetails = tokenService.loadUserByToken(token);
			Insititution insititution = institutionService.getStudentIns(userDetails.getId());
			AppRecruitSetting appRecruitSetting = solutionService.getAppRecruitSetting(insititution.getId());
			result.success(appRecruitSetting);
			
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		
		return result;
	}
	@RequestMapping(value="/initAppRecruitSetting")
	public Result initAppRecruitSetting(String token, Integer insId)
	{
		Result result = new Result();
		try
		{
//			PlatformUserDetail platformUserDetail = tokenService.loadPlatformUserByToken(token);
//			
//			solutionService.initAppRecruitSetting(platformUserDetail.getInsId());
//			result.success("初始化成功");
			
			PlatformUserDetail platformUserDetail = tokenService.loadPlatformUserByToken(token);
			if (platformUserDetail.getInsId().intValue() != 0) {
				result.error("需要平台管理员角色");
			}
			else
			{
				solutionService.initAppRecruitSetting(insId);
				result.success("设置成功");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		return result;
	}
	@RequestMapping(value="/updateAppRecruitSetting")
	public Result updateAppRecruitSetting(String token, AppRecruitSetting appRecruitSetting)
	{
		Result result = new Result();
		try
		{
			PlatformUserDetail platformUserDetail = tokenService.loadPlatformUserByToken(token);
			solutionService.updateAppRecruitSetting(appRecruitSetting);
			result.success("更新成功");
		}
		catch (Exception e) {
			// TODO: handle exception
			result.error(e.getMessage());
		}
		return result;
	}
	
	

}
