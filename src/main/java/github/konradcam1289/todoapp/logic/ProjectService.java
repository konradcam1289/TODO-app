package github.konradcam1289.todoapp.logic;

import github.konradcam1289.todoapp.TaskConfigurationProperties;
import github.konradcam1289.todoapp.model.*;
import github.konradcam1289.todoapp.model.projection.GroupReadModel;
import github.konradcam1289.todoapp.model.projection.GroupTaskWriteModel;
import github.konradcam1289.todoapp.model.projection.GroupWriteModel;
import github.konradcam1289.todoapp.model.projection.ProjectWriteModel;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class ProjectService {
    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskGroupService taskGroupService;
    private TaskConfigurationProperties config;


    public ProjectService(final ProjectRepository repository,
                          final TaskGroupRepository taskGroupRepository,
                          final TaskGroupService taskGroupService,
                          final TaskConfigurationProperties config) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.taskGroupService = taskGroupService;
        this.config = config;
    }

    public List<Project> readAll() {
        List<Project> projects = repository.findAll();
        projects.forEach(project -> {
            project.getSteps().stream().sorted(Comparator.comparingInt(ProjectStep::getDaysToDeadline));
        });
        return projects;
    }


    public Project save(final ProjectWriteModel toSave) {

        return repository.save(toSave.toProject());
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId) {
        if (!config.getTemplate().isAllowMultipleTasks() &&
                taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId))
            throw new IllegalStateException("Only one undone group from project is allowed");
        return repository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                    .map(projectStep -> {
                                                var task = new GroupTaskWriteModel();
                                                task.setDescription(projectStep.getDescription());
                                                task.setDeadline(deadline.plusDays(projectStep.getDaysToDeadline()));
                                                return task;
                                            }
                                    ).collect(Collectors.toList())
                    );
                    return taskGroupService.createGroup(targetGroup, project);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
    }

}
