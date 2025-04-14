package com.ssau.todo.repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ssau.todo.entity.Project;

@Repository
public class ProjectRepository {  
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Project> getAllProjects(){
        String sql = "SELECT * FROM Project";
        return jdbcTemplate.query(sql, (rs,_) -> {
            Project project = new Project();
            project.setId(rs.getLong("id"));
            project.setName(rs.getString("name"));
            project.setDescription(rs.getString("description"));
            project.setStartDate(rs.getDate("start_date").toLocalDate());
            project.setEndDate(rs.getDate("end_date").toLocalDate());
            return project;
        });
    }

    public Project selectById(Long id) {
        String sql = "SELECT * FROM Project WHERE id = ?";
        List<Project> params = jdbcTemplate.query(sql, (rs, _) -> {
            Project project = new Project();
            project.setId(rs.getLong("id"));
            project.setName(rs.getString("name"));
            project.setDescription(rs.getString("description"));
            project.setStartDate(rs.getDate("start_date").toLocalDate());
            project.setEndDate(rs.getDate("end_date").toLocalDate());
            return project;
        }, id);
        return params.get(0);
    }

    public void saveProject (Project project){
        String sql = "INSERT INTO Project (name, description, start_date, end_date) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, project.getName(), project.getDescription(), project.getStartDate(), project.getEndDate());
    }

    public void deleteProject (Long id){
        String sql = "DELETE FROM Project WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updateProject (Long id, Project project){
        String sql = "UPDATE Project SET name = :name, description = :description, start_date = :startDate, end_date = :endDate WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("name", project.getName());
        params.put("description", project.getDescription());
        params.put("startDate", project.getStartDate());
        params.put("endDate", project.getEndDate());

        namedParameterJdbcTemplate.update(sql, params);
    }

    public List<Project> findByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM Project WHERE start_date >= ? AND end_date <= ?";
        return jdbcTemplate.query(sql, (rs, _) -> {
            Project project = new Project();
            project.setId(rs.getLong("id"));
            project.setName(rs.getString("name"));
            project.setDescription(rs.getString("description"));
            project.setStartDate(rs.getDate("start_date").toLocalDate());
            project.setEndDate(rs.getDate("end_date").toLocalDate());
            return project;
        }, startDate, endDate);
    }  
}
