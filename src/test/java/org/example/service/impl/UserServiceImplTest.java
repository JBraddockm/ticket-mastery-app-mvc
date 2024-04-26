package org.example.service.impl;

import static org.mockito.Mockito.*;

import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private UserServiceImpl userService;

  @Test
  void deleteByUsernameTest() {
    userService.deleteByUsername("info@example.org");
    verify(userRepository, atMost(1)).deleteByUsername("info@example.org");
  }
}
