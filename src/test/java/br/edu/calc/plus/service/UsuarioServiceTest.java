package br.edu.calc.plus.service;

import br.edu.calc.plus.domain.Usuario;
import br.edu.calc.plus.domain.dto.UserDTO;
import br.edu.calc.plus.repo.UsuarioRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepo uDao;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCountUsers() {
        // Arrange
        long expectedCount = 5;
        Mockito.when(uDao.count()).thenReturn(expectedCount);

        // Act
        long actualCount = usuarioService.getCountUsers();

        // Assert
        Assertions.assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testSave() {
        // Arrange
        UserDTO userDTO = new UserDTO(); // Create a UserDTO instance with required data

        // Act
        usuarioService.save(userDTO);

        // Assert
        Mockito.verify(uDao, Mockito.times(1)).save(Mockito.any(Usuario.class));
        // Verify that the save method of uDao is called once with a Usuario object
        // You might need to adjust the argument matcher based on your specific implementation
    }
}