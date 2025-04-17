package com.ssau.lab2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssau.lab2.entity.Task;

import jakarta.transaction.Transactional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProjectId(Long projectId);
    
    @Query("SELECT t FROM Task t WHERE t.id = :taskId AND t.project.id = :projectId")
    Optional<Task> findByIdAndProjectId(
        @Param("taskId") Long taskId,
        @Param("projectId") Long projectId
    );
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Task t WHERE t.project.id = :projectId AND t.isCompleted = true")
    void deleteCompletedTasksByProjectId(@Param("projectId") Long projectId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Task t WHERE t.id = :taskId AND t.project.id = :projectId")
    void deleteByIdAndProjectId(@Param("taskId") Long taskId, @Param("projectId") Long projectId);

    @Query("SELECT t.project.id AS projectId, COUNT(t) AS count FROM Task t WHERE t.isCompleted = false GROUP BY t.project.id")
    List<Object[]> countUncompletedTasks();
}
