package github.konradcam1289.todoapp.controller;

import github.konradcam1289.todoapp.model.Task;
import github.konradcam1289.todoapp.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerE2E {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    TaskRepository repo;

    @Test
    void httpGet_returnAllTasks() {
        var initial = repo.findAll().size();
        repo.save(new Task("foo", LocalDateTime.now()));
        repo.save(new Task("bar", LocalDateTime.now()));
        Task[] result = restTemplate.getForObject("http://localhost:" + port + "/tasks", Task[].class);
        assertThat(result).hasSize(initial + 2);
    }

    @Test
    void httpGet_returnsTaskById() {
        Task task = repo.save(new Task("test",
                LocalDateTime.now()));
        Task result = restTemplate.getForObject(
                "http://localhost:" + port + "/tasks/"
                        + task.getId(), Task.class);
        assertThat(result.getId()).isEqualTo(task.getId());
    }
}
