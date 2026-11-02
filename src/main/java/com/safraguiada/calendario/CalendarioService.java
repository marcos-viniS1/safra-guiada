package com.safraguiada.calendario;

import com.safraguiada.propriedade.Talhao;
import com.safraguiada.propriedade.TalhaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarioService {

        private final TarefaRepository tarefaRepository;
        private final TalhaoRepository talhaoRepository;

        public List<Tarefa> gerarManejoPadrao(Long talhaoId) {
                Talhao talhao = talhaoRepository.findById(talhaoId)
                                .orElseThrow(() -> new RuntimeException("Talhão não encontrado com ID: " + talhaoId));

                List<Tarefa> tarefasPadrao = new ArrayList<>();

                // Setembro (Mês 9) - Pós-Florada
                tarefasPadrao.add(criarTarefa(
                                "Adubação NPK - 1ª Parcela (Pós-Florada)",
                                "Aplicação via solo de adubo nitrogenado e potássico após a abertura das flores.",
                                9, talhao));

                // Novembro (Mês 11) - Fungicida (Ferrugem)
                tarefasPadrao.add(criarTarefa(
                                "Controle de Ferrugem do Cafeeiro",
                                "Aplicação preventiva/curativa de fungicida foliar para controle de Hemileia vastatrix.",
                                11, talhao));

                // Janeiro (Mês 1) - Enchimento de Grãos
                tarefasPadrao.add(criarTarefa(
                                "Adubação NPK - 2ª Parcela",
                                "Segunda aplicação de cobertura focada no enchimento dos grãos de café.",
                                1, talhao));

                // Março (Mês 3) - Manejo Sanitário
                tarefasPadrao.add(criarTarefa(
                                "Monitoramento da Broca-do-Café",
                                "Amostragem de frutos nos terços médio e superior da planta para controle de broca.",
                                3, talhao));

                // Maio (Mês 5) - Preparação da Colheita
                tarefasPadrao.add(criarTarefa(
                                "Preparação e Início da Colheita",
                                "Ajuste de maquinários/derriçadeiras e limpeza do terreiro/secadores.",
                                5, talhao));

                // Agosto (Mês 8) - Pós-Colheita
                tarefasPadrao.add(criarTarefa(
                                "Poda e Tratos Culturais Pós-Colheita",
                                "Execução de decote/esqueletamento e roçada das ruas para o próximo ciclo.",
                                8, talhao));

                return tarefaRepository.saveAll(tarefasPadrao);
        }

        private Tarefa criarTarefa(String titulo, String descricao, Integer mes, Talhao talhao) {
                Tarefa t = new Tarefa();
                t.setTitulo(titulo);
                t.setDescricao(descricao);
                t.setMesPrevisto(mes);
                t.setStatus(StatusTarefa.PREVISTA);
                t.setTalhao(talhao);
                return t;
        }
}