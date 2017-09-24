package com.bjxc.school.service;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bjxc.school.AppRecruitSetting;
import com.bjxc.school.CoachAppSetting;
import com.bjxc.school.Solution;
import com.bjxc.school.SolutionAuthGroup;
import com.bjxc.school.mapper.AppRecruitSettingMapper;
import com.bjxc.school.mapper.CoachAppSettingMapper;
import com.bjxc.school.mapper.SolutionAuthGroupMapper;
import com.bjxc.school.mapper.SolutionMapper;

@Service
public class SolutionService {
	
	@Resource
	private SolutionMapper solutionMapper;
	
	@Resource 
	private SolutionAuthGroupMapper solutionAuthGroupMapper;
	
	@Resource
	private CoachAppSettingMapper coachAppSettingMapper;
	
	@Resource
	private AppRecruitSettingMapper appRecruitSettingMapper;
	
	public Integer getSolutionListCount()
	{
		return solutionMapper.getSolutionListCount();
	}
	
	public List<Solution> getSolutionList(Integer startIndex, Integer length)
	{
		List<Solution> solutionList = solutionMapper.getSolutionList(startIndex,length);
		
		List<SolutionAuthGroup> solutionAuthGroupList = solutionAuthGroupMapper.getSolutionAuthGroup();
		
		
		for(SolutionAuthGroup solutionAuthGroupItem: solutionAuthGroupList)
		{
			for(Solution solutionItem: solutionList)
			{
				if (solutionItem.getId() == solutionAuthGroupItem.getSolutionId()) {
					
					String auth = "";
					if (solutionItem.getAuths().length() != 0) {
						auth = "， ";
					}
					auth = auth + solutionAuthGroupItem.getAuthGroupName();
					
					solutionItem.setAuths(solutionItem.getAuths() + auth);
					
					if (solutionAuthGroupItem.getAuthGroupId().intValue() == 2) {
						solutionItem.setInitCoachAppSetting(true);
					}
					
					if (solutionAuthGroupItem.getAuthGroupId().intValue() == 3) {
						solutionItem.setInitAppRecruitSetting(true);
					}			

					
				}
			}
		}
		

		
		
		return solutionList;
	}
	
	@Transactional
	public Integer addSolution(String solutionName, String[] authGroupArray)
	{
		try
		{
			Solution solution = new Solution();
			solution.setSolutionName(solutionName);
			solutionMapper.addSolution(solution);
			
			for(String authGroupItem:authGroupArray)
			{
				solutionAuthGroupMapper.addSolutionAuthGroup(solution.getId(), Integer.parseInt(authGroupItem));
			}
			return 1;
		
		}
		catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}
	
	public List<SolutionAuthGroup> getSolutionAuthGroupList()
	{
		List<SolutionAuthGroup> list = solutionAuthGroupMapper.getSolutionAuthGroupList();
		
		return list;
	}
	public List<SolutionAuthGroup> getFunctionConfigSolutionAuthGroupList(Integer insId) {
		return solutionAuthGroupMapper.getFunctionConfigSolutionAuthGroupList(insId);
	}
	public List<SolutionAuthGroup> getSchoolSolutionAuthGroupList(Integer insId) {
		return solutionAuthGroupMapper.getSchoolSolutionAuthGroupList(insId);
	}
	public CoachAppSetting getInsCoachAppSetting(Integer insId)
	{
		return coachAppSettingMapper.getInsCoachAppSetting(insId);
	}
	public Integer initCoachAppSetting(Integer insId)
	{
		return coachAppSettingMapper.initSetting(insId);
	}
	
	public Integer updateCoachAppSetting(CoachAppSetting coachAppSetting) {
		
		return coachAppSettingMapper.updateCoachAppSetting(coachAppSetting);
	}
	
	public AppRecruitSetting getAppRecruitSetting(Integer insId) {
		return appRecruitSettingMapper.getAppRecruitSetting(insId);
	}
	public Integer initAppRecruitSetting(Integer insId) {
		return appRecruitSettingMapper.initAppRecruitSetting(insId);
	}
	
	public Integer updateAppRecruitSetting(AppRecruitSetting appRecruitSetting) {
		return appRecruitSettingMapper.updateAppRecruitSetting(appRecruitSetting);
	}

}
