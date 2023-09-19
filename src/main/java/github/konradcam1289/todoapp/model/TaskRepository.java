package github.konradcam1289.todoapp.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.net.ContentHandler;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface TaskRepository {
    List<Task> findAll();
    Page<Task> findAll(Pageable page);
    Optional<Task> findById(Integer id);
    boolean existsById(Integer id);
    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);
    Task save(Task entity);
    List<Task> findByDone(@Param("state") boolean done);
    List<Task> findAllByGroup_Id(Integer groupId);
    List<Task> findAllByDeadlineIsNullAndDoneIsFalseOrDeadlineIsBefore(LocalDateTime data);


}
