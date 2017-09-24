package com.bjxc.school.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bjxc.school.Insititution;
import com.bjxc.school.mapper.InstitutionMapper;

@Service
public class InstitutionService {
	
	@Resource
	private InstitutionMapper institutionMapper;
	public List<Insititution> getInsitutionList(Integer solutionId)
	{
		return institutionMapper.getInsList(solutionId);
	}
	
	public Insititution getCoachIns(Integer userId)
	{
		return institutionMapper.getCoachIns(userId); 
	}
	
	public Insititution getStudentIns(Integer userId)
	{
		return institutionMapper.getStudentIns(userId); 
	}

}
