package com.bjxc.school.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bjxc.school.Solution;


@Mapper
public interface SolutionMapper {
	
	@Select("select t_institution_solution.id as id, t_institution_solution.solution_name as solutionName, count(t_institution.solution_id) as insCount from t_institution_solution left join t_institution on t_institution_solution.id = t_institution.solution_id group by t_institution_solution.id, t_institution_solution.solution_name  order by t_institution_solution.id limit #{startIndex},#{length};")
	List<Solution> getSolutionList(@Param("startIndex")Integer startIndex, @Param("length")Integer length);
	
	@Select("select count(*) from t_institution_solution")
	Integer getSolutionListCount();
	
	Integer addSolution(Solution solution);

}
