package com.ssau.todo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ssau.todo.entity.Task;
@Repository
public class TaskRepository {
    @Autowired  
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Task> getAllTasks (){
        String sql = "SELECT * FROM Task";
        return jdbcTemplate.query(sql, (rs,_) -> {
            Task task = new Task();
            task.setId(rs.getLong("id"));
            task.setName(rs.getString("name"));
            task.setDescription(rs.getString("description"));
            task.setPlannedEndDate(rs.getDate("planned_end_date").toLocalDate());
            task.setCompleted(rs.getBoolean("is_completed"));
            task.setProjectId(rs.getLong("project_id"));
            return task;
        });
    }

    public Task selectById(Long id) {
        String sql = "SELECT * FROM Task WHERE id = ?";
        List<Task> params = jdbcTemplate.query(sql, (rs, _) -> {
            Task task = new Task();
            task.setId(rs.getLong("id"));
            task.setName(rs.getString("name"));
            task.setDescription(rs.getString("description"));
            task.setPlannedEndDate(rs.getDate("planned_end_date").toLocalDate());
            task.setCompleted(rs.getBoolean("is_completed"));
            task.setProjectId(rs.getLong("project_id"));
            return task;
        }, id);
        return params.get(0);
    }

    public void saveTask(Task task){
        String sql = "INSERT INTO Task (name, description, planned_end_date, is_completed, project_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, task.getName(), task.getDescription(), task.getPlannedEndDate(), task.isCompleted(), task.getProjectId());
    }

    public void deleteTask(Long id){
        String sql = "DELETE FROM Task WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updateTask(Long id, Task task){
        String sql = "UPDATE Task SET name = :name, description = :description, planned_end_date = :plannedEndDate, is_completed = :isCompleted, project_id = :projectId WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("name", task.getName());
        params.put("description", task.getDescription());
        params.put("plannedEndDate", task.getPlannedEndDate());
        params.put("isCompleted", task.isCompleted());
        params.put("projectId", task.getProjectId());
        namedParameterJdbcTemplate.update(sql, params);
    }

    public List<Task> findByProjectId(Long projectId) {
        String sql = "SELECT * FROM Task WHERE project_id = ?";
        return jdbcTemplate.query(sql, (rs, _) -> {
            Task task = new Task();
            task.setId(rs.getLong("id"));
            task.setName(rs.getString("name"));
            task.setDescription(rs.getString("description"));
            task.setPlannedEndDate(rs.getDate("planned_end_date").toLocalDate());
            task.setCompleted(rs.getBoolean("is_completed"));
            task.setProjectId(rs.getLong("project_id"));
            return task;
        }, projectId);
    }
}