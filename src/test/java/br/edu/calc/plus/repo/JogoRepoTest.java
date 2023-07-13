package br.edu.calc.plus.repo;

import br.edu.calc.plus.domain.EOperator;
import br.edu.calc.plus.domain.Jogo;
import br.edu.calc.plus.domain.Partida;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class JogoRepoIntegrationTest {

    @Autowired
    private JogoRepo jogoRepo;

    private Jogo jogo1;
    private Jogo jogo2;

    @Autowired
    private PartidaRepo partidaRepo;

    private Partida partida1;
    private Partida partida2;

    @Autowired
    private UsuarioRepo usuarioRepo;

    private Usuario usuario1;
    private Usuario usuario2;

    @BeforeEach
    void setUp() {



        // Set up test data

        usuario1 = new Usuario(1,"John Doe", "johndoe","johndoe@gmail.com","1234","Rio de Janeiro", LocalDate.now());

        usuarioRepo.save(usuario1);

        // Set up test data
        partida1 = new Partida(null,LocalDateTime.now(),100,1L);
        partida1.setUsuario(usuario1);

        partida2 = new Partida(null,LocalDateTime.now(),100,2L);
        partida2.setUsuario(usuario1);

        partidaRepo.save(partida1);
        partidaRepo.save(partida2);

        jogo1 = new Jogo(1,2.0,2.0, EOperator.soma,4.0,4.0,100.0);

        jogo1.setPartida(partidaRepo.getById(partida1.getId()));

        jogo2 = new Jogo(2,2.0,2.0, br.edu.calc.plus.domain.EOperator.soma,3.0,4.0,100.0);

        jogo2.setPartida(partidaRepo.getById(partida2.getId()));

        jogoRepo.save(jogo1);
        jogoRepo.save(jogo2);
    }

    @AfterEach
    void tearDown() {
        // Clean up the data after each test
        jogoRepo.deleteAll();
    }

    @Test
    void testGetAllErros() {

        long count = jogoRepo.getAllErros();
        assertEquals(1, count);
    }

    @Test
    void testGetAllAcertos() {

        long count = jogoRepo.getAllAcertos();
        assertEquals(1, count);
    }

    @Test
    void testGetAcertosDia() {

        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        long count = jogoRepo.getAcertosDia(startDate, endDate);
        assertEquals(1, count);
    }

    @Test
    void testGetErrosDia() {

        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        long count = jogoRepo.getErrosDia(startDate, endDate);
        assertEquals(1, count);
    }

    @Test
    void testGetAllAcertosUser() {

        long count = jogoRepo.getAllAcertosUser(jogo1.getPartida().getUsuario().getId());
        assertEquals(1, count);
    }
}