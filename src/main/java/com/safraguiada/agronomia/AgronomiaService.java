package com.safraguiada.agronomia;

import com.safraguiada.propriedade.Talhao;
import com.safraguiada.propriedade.TalhaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgronomiaService {

    private final AmostragemRepository amostragemRepository;
    private final TalhaoRepository talhaoRepository;

    public Amostragem registrarAmostragem(Long talhaoId, Amostragem amostragem) {
        Talhao talhao = talhaoRepository.findById(talhaoId)
                .orElseThrow(() -> new RuntimeException("Talhão não encontrado com ID: " + talhaoId));

        if (amostragem.getDataAmostragem() == null) {
            amostragem.setDataAmostragem(LocalDate.now());
        }

        NivelAlerta alerta = calcularNivelAlerta(amostragem.getTipoPragaDoenca(), amostragem.getPercentualInfestacao());
        amostragem.setNivelAlerta(alerta);
        amostragem.setTalhao(talhao);

        return amostragemRepository.save(amostragem);
    }

    public List<Amostragem> listarPorTalhao(Long talhaoId) {
        return amostragemRepository.findByTalhaoId(talhaoId);
    }

    public List<Amostragem> listarAlertasPorTalhao(Long talhaoId) {
        return amostragemRepository.findByTalhaoIdAndNivelAlertaNot(talhaoId, NivelAlerta.NORMAL);
    }

    private NivelAlerta calcularNivelAlerta(TipoPragaDoenca tipo, Double percentual) {
        if (percentual == null)
            return NivelAlerta.NORMAL;

        return switch (tipo) {
            case FERRUGEM -> {
                if (percentual >= 10.0)
                    yield NivelAlerta.CRITICO;
                if (percentual >= 5.0)
                    yield NivelAlerta.ATENCAO;
                yield NivelAlerta.NORMAL;
            }
            case BROCA_DO_CAFE -> {
                if (percentual >= 5.0)
                    yield NivelAlerta.CRITICO;
                if (percentual >= 3.0)
                    yield NivelAlerta.ATENCAO;
                yield NivelAlerta.NORMAL;
            }
            case BICHO_MINEIRO -> {
                if (percentual >= 10.0)
                    yield NivelAlerta.CRITICO;
                if (percentual >= 5.0)
                    yield NivelAlerta.ATENCAO;
                yield NivelAlerta.NORMAL;
            }
            case CERCOSPORIOSE -> {
                if (percentual >= 8.0)
                    yield NivelAlerta.CRITICO;
                if (percentual >= 4.0)
                    yield NivelAlerta.ATENCAO;
                yield NivelAlerta.NORMAL;
            }
        };
    }
}