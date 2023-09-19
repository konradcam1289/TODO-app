package github.konradcam1289.todoapp.adapter;

import github.konradcam1289.todoapp.model.Task;
import github.konradcam1289.todoapp.model.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {

    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=:id")
    boolean existsById(@Param("id") Integer id);
    @Query(value = "SELECT * FROM tasks WHERE task_group_id = :groupId ORDER BY deadline ASC", nativeQuery = true)
    List<Task> findAllByGroup_IdSortedByDeadline(@Param("groupId") int groupId);
    @Override
    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);

    @Override
    List<Task> findAllByGroup_Id(Integer groupId);

    @Override
    List<Task> findAllByDeadlineIsNullAndDoneIsFalseOrDeadlineIsBefore(LocalDateTime data);
    List<Task> findAllByGroup_IdOrderByDeadlineAsc(int groupId);
}
