package github.konradcam1289.todoapp.logic;

import github.konradcam1289.todoapp.TaskConfigurationProperties;
import github.konradcam1289.todoapp.model.Project;
import github.konradcam1289.todoapp.model.TaskGroup;
import github.konradcam1289.todoapp.model.TaskGroupRepository;
import github.konradcam1289.todoapp.model.TaskRepository;
import github.konradcam1289.todoapp.model.projection.GroupReadModel;
import github.konradcam1289.todoapp.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.stream.Collectors;


public class TaskGroupService {
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    public TaskGroupService(final TaskGroupRepository repository,
                            final TaskRepository taskRepository)
    {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(final GroupWriteModel source) {
        return createGroup(source, null);
    }
    GroupReadModel createGroup(final GroupWriteModel source,
                               final Project project) {
        TaskGroup result = repository.save(source.toGroup(project));
        return new GroupReadModel(result);
    }
    public List<GroupReadModel> readAll() {
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId) {
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)){
            throw new IllegalStateException("Group has undone tasks. Done all the tasks first");
        }
        TaskGroup result = repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException
                        ("TaskGroup with given id not found"));
        result.setDone(!result.isDone());
        repository.save(result);
    }


}
