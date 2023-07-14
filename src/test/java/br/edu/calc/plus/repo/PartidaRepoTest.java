package br.edu.calc.plus.repo;

import br.edu.calc.plus.domain.Partida;
import br.edu.calc.plus.domain.Usuario;
import br.edu.calc.plus.domain.dto.RankingDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PartidaRepoIntegrationTest {

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
        partidaRepo.deleteAll();
        usuarioRepo.deleteAll();
        usuario1 = new Usuario(1,"John Doe", "johndoe","johndoe@gmail.com","1234","Rio de Janeiro", LocalDate.now());

        usuarioRepo.save(usuario1);

        // Set up test data
        partida1 = new Partida(1,LocalDateTime.now(),100,1L);
        partida1.setUsuario(usuario1);

        partida2 = new Partida(2,LocalDateTime.now(),100,2L);
        partida2.setUsuario(usuario1);

        partidaRepo.save(partida1);
        partidaRepo.save(partida2);
    }

    @AfterEach
    void tearDown() {
        // Clean up the data after each test
        partidaRepo.deleteAll();
    }

    @Test
    void testGetAllBonus() {


        Double totalBonus = partidaRepo.getAllBonus();
        assertEquals(200.0, totalBonus);
    }

    @Test
    void testFindByUsuarioId() {

        List<Partida> partidas = partidaRepo.findByUsuarioId(partida1.getUsuario().getId());
        assertEquals(2, partidas.size());
    }

    @Test
    void testGetUsuarioCompetil() {

        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        long count = partidaRepo.getUsuarioCompetil(partida1.getUsuario().getId(), startDate, endDate);
        assertEquals(2, count);
    }

    @Test
    void testFindByIdAndUsuarioId() {

        Partida foundPartida = partidaRepo.findByIdAndUsuarioId(partida1.getId(), partida1.getUsuario().getId());
        assertEquals(partida1, foundPartida);
    }

    @Test
    void testGetRanking() {

        List<RankingDTO> ranking = partidaRepo.getRanking();
        assertEquals(1, ranking.size());
    }
}