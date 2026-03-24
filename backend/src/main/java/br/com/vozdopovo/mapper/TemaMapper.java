package br.com.vozdopovo.mapper;

import br.com.vozdopovo.dto.tema.TemaRequestDTO;
import br.com.vozdopovo.dto.tema.TemaResponseDTO;
import br.com.vozdopovo.entity.Tema;

public class TemaMapper {

    private TemaMapper() {
    }

    public static Tema toEntity(TemaRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Tema tema = new Tema();
        tema.setTitulo(dto.getTitulo());
        tema.setDescricao(dto.getDescricao());

        return tema;
    }

    public static TemaResponseDTO toResponseDTO(Tema tema) {
        if (tema == null) {
            return null;
        }

        TemaResponseDTO dto = new TemaResponseDTO();
        dto.setId(tema.getId());
        dto.setTitulo(tema.getTitulo());
        dto.setDescricao(tema.getDescricao());
        dto.setStatus(tema.getStatus());
        dto.setDataCriacao(tema.getDataCriacao());
        dto.setDataAtualizacao(tema.getDataAtualizacao());

        if (tema.getPlanoDeGoverno() != null) {
            dto.setPlanoDeGovernoId(tema.getPlanoDeGoverno().getId());
        }

        return dto;
    }
}