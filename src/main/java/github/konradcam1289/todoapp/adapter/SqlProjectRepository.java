package github.konradcam1289.todoapp.adapter;

import github.konradcam1289.todoapp.model.Project;
import github.konradcam1289.todoapp.model.ProjectRepository;
import github.konradcam1289.todoapp.model.TaskGroup;
import github.konradcam1289.todoapp.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {
    @Override
    @Query("from Project p join fetch p.steps")
    List<Project> findAll();
}
