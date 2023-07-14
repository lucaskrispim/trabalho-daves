package br.edu.calc.plus.repo;

import br.edu.calc.plus.domain.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UsuarioRepoIntegrationTest {

    @Autowired
    private UsuarioRepo usuarioRepo;

    private Usuario usuario1;
    private Usuario usuario2;

    @BeforeEach
    void setUp() {

        // Set up test data
        usuario1 = new Usuario(1,"John Doe", "johndoe","johndoe@gmail.com","1234","Rio de Janeiro", LocalDate.now());
        usuario2 = new Usuario(2,"Jane Smith", "janesmith","janesmith@gmail.com","1234","Rio de Janeiro", LocalDate.now());

        usuarioRepo.save(usuario1);
        usuarioRepo.save(usuario2);
    }

    @AfterEach
    void tearDown() {
        // Clean up!
        usuarioRepo.deleteAll();
    }

    @Test
    void testFindByNome() {

        Optional<Usuario> foundUsuario = usuarioRepo.findByNome(usuario1.getNome());
        assertTrue(foundUsuario.isPresent());
        assertEquals(usuario1, foundUsuario.get());
    }

    @Test
    void testFindByLogin() {

        Optional<Usuario> foundUsuario = usuarioRepo.findByLogin(usuario1.getLogin());
        assertTrue(foundUsuario.isPresent());
        assertEquals(usuario1, foundUsuario.get());
    }
}