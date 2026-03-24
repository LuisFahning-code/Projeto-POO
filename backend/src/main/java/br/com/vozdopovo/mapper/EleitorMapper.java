package br.com.vozdopovo.mapper;

import br.com.vozdopovo.entity.Eleitor;
import br.com.vozdopovo.dto.eleitor.EleitorRequestDTO;
import br.com.vozdopovo.dto.eleitor.EleitorResponseDTO;

public class EleitorMapper {

    private EleitorMapper() {
    }

    public static Eleitor toEntity(EleitorRequestDTO dto) {
        Eleitor eleitor = new Eleitor();
        eleitor.setNome(dto.getNome());
        eleitor.setEmail(dto.getEmail());
        eleitor.setSenha(dto.getSenha());
        return eleitor;
    }

    public static EleitorResponseDTO toResponseDTO(Eleitor eleitor) {
        return new EleitorResponseDTO(
                eleitor.getId(),
                eleitor.getNome(),
                eleitor.getEmail(),
                eleitor.getStatus()
        );
    }
} 

