package br.com.vozdopovo.mapper;

import br.com.vozdopovo.dto.proposta.PropostaRequestDTO;
import br.com.vozdopovo.dto.proposta.PropostaResponseDTO;
import br.com.vozdopovo.entity.Proposta;

public class PropostaMapper {

    private PropostaMapper() {
    }

    public static Proposta toEntity(PropostaRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Proposta proposta = new Proposta();
        proposta.setTitulo(dto.getTitulo());
        proposta.setResumo(dto.getResumo());
        proposta.setDetalhamento(dto.getDetalhamento());

        return proposta;
    }

    public static PropostaResponseDTO toResponseDTO(Proposta proposta) {
        if (proposta == null) {
            return null;
        }

        PropostaResponseDTO dto = new PropostaResponseDTO();
        dto.setId(proposta.getId());
        dto.setTitulo(proposta.getTitulo());
        dto.setResumo(proposta.getResumo());
        dto.setDetalhamento(proposta.getDetalhamento());
        dto.setStatus(proposta.getStatus());
        dto.setDataCriacao(proposta.getDataCriacao());
        dto.setDataAtualizacao(proposta.getDataAtualizacao());

        if (proposta.getPlanoDeGoverno() != null) {
            dto.setPlanoDeGovernoId(proposta.getPlanoDeGoverno().getId());
        }

        if (proposta.getTema() != null) {
            dto.setTemaId(proposta.getTema().getId());
        }

        return dto;
    }
}
