package github.konradcam1289.todoapp.logic;

import github.konradcam1289.todoapp.TaskConfigurationProperties;
import github.konradcam1289.todoapp.model.ProjectRepository;
import github.konradcam1289.todoapp.model.TaskGroupRepository;
import github.konradcam1289.todoapp.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogicConfiguration {
    @Bean
    ProjectService projectService(
            final ProjectRepository repository,
            final TaskGroupRepository taskGroupRepository,
            final TaskConfigurationProperties config,
            final TaskGroupService taskGroupService
    ) {
        return new ProjectService(repository,
                taskGroupRepository,
                taskGroupService, config);
    }

    @Bean
    TaskGroupService taskGroupService(
            final TaskGroupRepository taskGroupRepository,
            final TaskRepository taskRepository
    ) {
        return new TaskGroupService(taskGroupRepository, taskRepository);
    }
}
