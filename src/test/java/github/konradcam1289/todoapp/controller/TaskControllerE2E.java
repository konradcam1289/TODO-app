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
        //given
        var initial = repo.findAll().size();
        repo.save(new Task("foo", LocalDateTime.now()));
        repo.save(new Task("bar", LocalDateTime.now()));

        //and
        //when
        Task[] result = restTemplate.getForObject("http://localhost:" + port + "/tasks", Task[].class);

        //then
        assertThat(result).hasSize(initial + 2);
    }

    @Test
    void httpGet_returnsTaskById() {
        //given
        Task task = repo.save(new Task("test",
                LocalDateTime.now()));
        //when
        Task result = restTemplate.getForObject(
                "http://localhost:" + port + "/tasks/"
                        + task.getId(), Task.class);
        //then
        assertThat(result.getId()).isEqualTo(task.getId());
    }

//    @Test
//    void httpPost_returnsCreatedResponse() {
//        Task task = new Task("test2", LocalDateTime.now());
//        ResponseEntity<?> result = restTemplate.postForEntity("http://localhost:"+port+"/tasks", task, ResponseEntity.class);
//        URI url = result.getHeaders().getLocation();
//        String path = url.getPath();
//        Task toCheck = restTemplate.getForObject("http://localhost:"+port+path, Task.class);
//
//        assertThat(result.getStatusCode())
//                .isEqualTo(HttpStatus.CREATED);
//        assertThat(toCheck.getDeadline())
//                .isCloseTo(task.getDeadline(),
//                        within(100, ChronoUnit.MILLIS));
//        assertThat(toCheck.getDescription())
//                .isEqualTo(task.getDescription());
//
//    }

}