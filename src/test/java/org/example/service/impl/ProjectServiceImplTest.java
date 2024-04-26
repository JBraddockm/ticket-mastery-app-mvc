package org.example.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import org.example.dto.ProjectDTO;
import org.example.enums.Status;
import org.example.exception.UserNotFoundException;
import org.example.model.Project;
import org.example.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

  @Mock private ProjectRepository projectRepository;

  @Mock private ModelMapper modelMapper;

  @InjectMocks private ProjectServiceImpl projectService;

  @Test
  void testFindByProjectCode_When_Project_Exists() {

    Project project = new Project();
    ProjectDTO projectDTO = new ProjectDTO();

    when(projectRepository.findByProjectCode(anyString())).thenReturn(Optional.of(project));
    when(projectService.mapToDTO(project)).thenReturn(projectDTO);

    Optional<ProjectDTO> foundProjectDTO = projectService.findByProjectCode(anyString());

    verify(projectRepository, times(1)).findByProjectCode(anyString());
    assertTrue(foundProjectDTO.isPresent());
  }

  @Test
  void testFindByProjectCode_When_It_Throws_Exception() {
    when(projectRepository.findByProjectCode(null))
        .thenThrow(new UserNotFoundException("User not found"));
    assertThrowsExactly(UserNotFoundException.class, () -> projectService.findByProjectCode(null));
  }

  @Test
  void testSave_When_Project_Status_Null() {

    // Given
    ProjectDTO projectDTO = new ProjectDTO();
    Project project = new Project();

    // Mocking behaviours
    when(projectService.mapToEntity(projectDTO)).thenReturn(project);
    when(projectService.save(projectDTO)).thenReturn(projectDTO);

    // When
    projectService.save(projectDTO);

    // Then
    verify(projectRepository, times(2)).save(project);

    // Assert outcomes
    assertNotNull(projectDTO.getProjectStatus());
    assertEquals(Status.OPEN, projectDTO.getProjectStatus());
  }
}
