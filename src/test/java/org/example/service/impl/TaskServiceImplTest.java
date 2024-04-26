package org.example.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import org.example.dto.TaskDTO;
import org.example.model.Task;
import org.example.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

  @Mock private TaskRepository taskRepository;

  @Mock private ModelMapper mapper;

  @InjectMocks private TaskServiceImpl taskService;

  @ParameterizedTest
  @ValueSource(longs = {1L, 2L, 3L, -4L})
  void testFindById_When_Task_Exists(long id) {

    if (id < 0) {
      assertThrows(IllegalArgumentException.class, () -> taskService.findById(id));
      return;
    }

    Task task = new Task();
    TaskDTO taskDTO = new TaskDTO();

    when(taskRepository.findById(id)).thenReturn(Optional.of(task));
    when(taskService.mapToDTO(task)).thenReturn(taskDTO);

    Optional<TaskDTO> foundTask = taskService.findById(id);

    verify(taskRepository, times(1)).findById(id);

    assertTrue(foundTask.isPresent());
  }

  @ParameterizedTest
  @ValueSource(longs = {1L, 2L, 3L, -4L})
  void testFindById_When_Task_Exists_BDD(long id) {
    if (id < 0) {
      assertThrows(IllegalArgumentException.class, () -> taskService.findById(id));
      return;
    }

    Task task = new Task();
    TaskDTO taskDTO = new TaskDTO();

    given(taskRepository.findById(id)).willReturn(Optional.of(task));
    given(taskService.mapToDTO(task)).willReturn(taskDTO);

    Optional<TaskDTO> foundTask = taskService.findById(id);

    then(taskRepository).should().findById(id);

    assertTrue(foundTask.isPresent());
  }

  @Test
  void testFindById_When_Task_Does_Not_Exist() {}
}
