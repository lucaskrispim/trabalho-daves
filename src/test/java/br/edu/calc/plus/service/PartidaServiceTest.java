package br.edu.calc.plus.service;

import br.edu.calc.plus.domain.EOperator;
import br.edu.calc.plus.domain.Jogo;
import br.edu.calc.plus.domain.Partida;
import br.edu.calc.plus.domain.dto.JogoListDTO;
import br.edu.calc.plus.domain.dto.RankingDTO;
import br.edu.calc.plus.repo.JogoRepo;
import br.edu.calc.plus.repo.PartidaRepo;
import br.edu.calc.plus.repo.UsuarioRepo;
import br.edu.calc.plus.util.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PartidaServiceTest {

    @Mock
    private PartidaRepo pDao;

    @Mock
    private JogoRepo jDao;

    @Mock
    private UsuarioRepo uDao;

    @Mock
    private JogoService jogoService;

    @InjectMocks
    private PartidaService partidaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPremioUsers() {

        double expectedPremio = 1000.0; // Set the expected premio value
        Mockito.when(pDao.getAllBonus()).thenReturn(expectedPremio);


        String actualPremio = partidaService.getPremioUsers();


        Assertions.assertEquals(Util.formatarMoeda(expectedPremio), actualPremio);
    }

    @Test
    public void testGetMeusJogos() {

        int idUserLogado = 123;
        Partida p = new Partida(1, LocalDateTime.now(), 100, 50);

        List<Jogo> jogos = new ArrayList<>();

        jogos.add(new Jogo(null, 1, 2, EOperator.soma, 3, -1111, 0));
        jogos.add(new Jogo(null, 3, 4, EOperator.divisao, 0.75, -1111, 0));

        p.setJogoList( jogos );

        List<Partida> mockPartidas = new ArrayList<>();
        mockPartidas.add(p);

        Mockito.when(pDao.findByUsuarioId(idUserLogado)).thenReturn(mockPartidas);


        List<JogoListDTO> meusJogos = partidaService.getMeusJogos(idUserLogado);

        // Assert
        Assertions.assertEquals(meusJogos.get(0).getIdPartida(),1);
        Assertions.assertEquals(meusJogos.get(0).getBonificacao(),100);
        Assertions.assertEquals(meusJogos.get(0).getAcertos(),0);
        Assertions.assertEquals(meusJogos.get(0).getErros(),2);
    }

    @Test
    public void testUserJaCompetiuHoje() {

        Integer idUserLogado = 123;
        LocalDateTime dtIni = LocalDate.now().atStartOfDay();
        LocalDateTime dtFim = LocalDate.now().atTime(23, 59, 59);
        Mockito.when(pDao.getUsuarioCompetil(idUserLogado, dtIni, dtFim)).thenReturn(1L);


        boolean result = partidaService.userJaCompetiuHoje(idUserLogado);


        Assertions.assertTrue(result);
    }

    @Test
    public void testIniciarPartida() throws Exception {

        Integer idUser = 123;
        Partida mockPartida = new Partida(null, LocalDateTime.now(), 0, 0);
        Mockito.when(pDao.save(Mockito.any(Partida.class))).thenReturn(mockPartida);


        Partida result = partidaService.iniciarPartida(idUser);


        Assertions.assertEquals(mockPartida, result);

    }

    @Test
    public void testSavePartida() throws Exception {
        // Arrange
        int idPartida = 1;
        Integer idUser = 123;
        int posicao = 1;
        int idJogo = 1;
        double valor = 42.0;
        Partida mockPartida = new Partida(1, LocalDateTime.now(), 0, 0);
        Jogo mockJogo = new Jogo(null, 1, 2, EOperator.soma, 3, -1111, 0);
        mockPartida.setJogoList(Collections.singletonList(mockJogo));
        Mockito.when(pDao.findByIdAndUsuarioId(idPartida, idUser)).thenReturn(mockPartida);

        // Act
        Partida result = partidaService.savePartida(idPartida, idUser, posicao, idJogo, valor);

        // Assert
        Assertions.assertEquals(mockPartida, result);

    }

    @Test
    public void testFinalizaPartida() throws Exception {
        // Arrange
        int idPartida = 1;
        Integer idUser = 123;
        LocalDateTime inicio = LocalDateTime.now().minusSeconds(2);
        Partida mockPartida = new Partida(1, LocalDateTime.now(), 0, 0);
        mockPartida.setJogoList(new ArrayList<>());
        Mockito.when(pDao.findByIdAndUsuarioId(idPartida, idUser)).thenReturn(mockPartida);

        // Act
        Partida result = partidaService.FinalizaPartida(idPartida, idUser, inicio);

        // Assert
        Assertions.assertEquals(result.getTempo(),2);
    }

    @Test
    public void testGetPartida() throws Exception {
        // Arrange
        int idPartida = 1;
        Integer idUser = 123;
        Partida mockPartida = new Partida(1, LocalDateTime.now(), 0, 0);
        Mockito.when(pDao.findByIdAndUsuarioId(idPartida, idUser)).thenReturn(mockPartida);

        // Act
        Partida result = partidaService.getPartida(idPartida, idUser);

        // Assert
        Assertions.assertEquals(mockPartida, result);
    }

    @Test
    public void testGetRanking() {
        // Mock the behavior of the PlayerDao
        List<RankingDTO> expectedRanking = Arrays.asList(
                new RankingDTO(1,"Player1", 100,1L,1L),
                new RankingDTO(2,"Player2", 90,1L,1L),
                new RankingDTO(3,"Player3", 80,1L,1L)
        );
        Mockito.when(pDao.getRanking()).thenReturn(expectedRanking);

        // Call the method under test
        List<RankingDTO> actualRanking = partidaService.getRanking();

        // Verify the result
        Assertions.assertEquals(expectedRanking, actualRanking);
        Mockito.verify(pDao).getRanking();
    }
}
