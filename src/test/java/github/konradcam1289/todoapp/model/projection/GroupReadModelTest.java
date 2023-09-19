package github.konradcam1289.todoapp.model.projection;

import github.konradcam1289.todoapp.model.Task;
import github.konradcam1289.todoapp.model.TaskGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GroupReadModelTest {
    @Test
    @DisplayName("should create null deadline for group when no task deadlines")
    void constructor_noDeadlines_createsNullDeadlinde(){
        //given
        var source = new TaskGroup();
        source.setDescription("foo");
        source.setTasks(Set.of(
                new Task("bar",null)));
        //when
        var result = new GroupReadModel(source);
        //then
        assertThat(result).hasFieldOrPropertyWithValue(
                "deadline", null);
    }

}