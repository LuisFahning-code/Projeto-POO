package br.com.vozdopovo.mapper;

import br.com.vozdopovo.dto.interacao.InteracaoRequestDTO;
import br.com.vozdopovo.dto.interacao.InteracaoResponseDTO;
import br.com.vozdopovo.entity.Interacao;

public class InteracaoMapper {

    public static Interacao toEntity(InteracaoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Interacao interacao = new Interacao();
        interacao.setTitulo(dto.getTitulo());
        interacao.setConteudo(dto.getConteudo());
        interacao.setTipo(dto.getTipo());
        interacao.setUrlComprovacao(dto.getUrlComprovacao());

        return interacao;
    }

    public static InteracaoResponseDTO toResponseDTO(Interacao interacao) {
        if (interacao == null) {
            return null;
        }

        InteracaoResponseDTO dto = new InteracaoResponseDTO();
        dto.setId(interacao.getId());
        dto.setTitulo(interacao.getTitulo());
        dto.setConteudo(interacao.getConteudo());
        dto.setTipo(interacao.getTipo());
        dto.setStatus(interacao.getStatus());
        dto.setUrlComprovacao(interacao.getUrlComprovacao());
        dto.setResposta(interacao.getResposta());
        dto.setDataInicio(interacao.getDataInicio());
        dto.setDataResposta(interacao.getDataResposta());

        if (interacao.getEleitor() != null) {
            dto.setEleitorId(interacao.getEleitor().getId());
        }

        if (interacao.getCandidato() != null) {
            dto.setCandidatoId(interacao.getCandidato().getId());
        }

        return dto;
    }
}
