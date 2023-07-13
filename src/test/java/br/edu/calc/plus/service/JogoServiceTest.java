package br.edu.calc.plus.service;

import br.edu.calc.plus.domain.EOperator;
import br.edu.calc.plus.domain.Jogo;
import br.edu.calc.plus.repo.JogoRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class JogoServiceTest {

    @Mock
    private JogoRepo jDao;

    @InjectMocks
    private JogoService jogoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllErros() {

        List<Jogo> mockJogos = new ArrayList<>();

        mockJogos.add(new Jogo(null, 1, 2, EOperator.soma, 3, -1111, 0));
        mockJogos.add(new Jogo(null, 3, 4, EOperator.divisao, 0.75, -1111, 0));


        Mockito.when(jDao.getAllErros()).thenReturn(mockJogos.stream()
                .filter(jogo -> jogo.getResposta() != jogo.getResultado())
                .count());


        String result = jogoService.getAllErros();

        Assertions.assertEquals("2", result);
    }

    @Test
    public void testGetAllAcertos() {

        List<Jogo> mockJogos = new ArrayList<>();

        mockJogos.add(new Jogo(null, 1, 2, EOperator.soma, 3, 3, 0));
        mockJogos.add(new Jogo(null, 3, 4, EOperator.divisao, 0.75, 0.75, 0));


        Mockito.when(jDao.getAllAcertos()).thenReturn(mockJogos.stream()
                .filter(jogo -> jogo.getResposta() == jogo.getResultado())
                .count());


        long actualAcertos = jogoService.getAllAcertos();


        Assertions.assertEquals(2, actualAcertos);
    }

    @Test
    public void testGetAcertosUltimos10Dias() {

        long[] expectedAcertos = {5, 3, 2, 1, 4, 6, 2, 0, 3, 7};
        Mockito.when(jDao.getAcertosDia(Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(5L, 3L, 2L, 1L, 4L, 6L, 2L, 0L, 3L, 7L);


        long[] actualAcertos = jogoService.getAcertosUltimos10Dias();

        Assertions.assertArrayEquals(expectedAcertos, actualAcertos);
    }

    @Test
    public void testGetErrosUltimos10Dias() {

        long[] expectedErros = {2, 0, 1, 3, 2, 1, 0, 1, 4, 2};
        Mockito.when(jDao.getErrosDia(Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(2L, 0L, 1L, 3L, 2L, 1L, 0L, 1L, 4L, 2L);

        long[] actualErros = jogoService.getErrosUltimos10Dias();

        Assertions.assertArrayEquals(expectedErros, actualErros);
    }

    @Test
    public void testBonusOperacao() {

        double somaBonus = jogoService.bonusOperacao(EOperator.soma);
        double divisaoBonus = jogoService.bonusOperacao(EOperator.divisao);
        double multiplicacaoBonus = jogoService.bonusOperacao(EOperator.multiplicacao);
        double subtracaoBonus = jogoService.bonusOperacao(EOperator.subtracao);


        Assertions.assertEquals(0.1, somaBonus);
        Assertions.assertEquals(0.9, divisaoBonus);
        Assertions.assertEquals(0.5, multiplicacaoBonus);
        Assertions.assertEquals(0.2, subtracaoBonus);
    }

    @Test
    public void testBonusUsuario() {

        int id = 123;
        long totalAcertos = 150;
        Mockito.when(jDao.getAllAcertosUser(id)).thenReturn(totalAcertos);


        double bonus = jogoService.bonusUsuario(id);

        Assertions.assertEquals(0.3, bonus);
    }

    @Test
    public void testCriarJogosAleatorio() {

        int idx = 5;
        int idUser = 123;


        List<Jogo> actualJogos = jogoService.criarJogosAleatorio(idx, idUser);

        Assertions.assertEquals(idx, actualJogos.size());
    }
}
