package br.com.vozdopovo.mapper;

import br.com.vozdopovo.entity.Candidato;
import br.com.vozdopovo.dto.candidato.CandidatoRequestDTO;
import br.com.vozdopovo.dto.candidato.CandidatoResponseDTO;

public class CandidatoMapper {

    public static Candidato toEntity(CandidatoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Candidato candidato = new Candidato();
        candidato.setNome(dto.getNome());
        candidato.setEmail(dto.getEmail());
        candidato.setSenha(dto.getSenha());
        candidato.setPartido(dto.getPartido());
        candidato.setCargo(dto.getCargo());
        candidato.setBiografia(dto.getBiografia());

        return candidato;
    }

    public static CandidatoResponseDTO toResponseDTO(Candidato candidato) {
        if (candidato == null) {
            return null;
        }

        return new CandidatoResponseDTO(
                candidato.getId(),
                candidato.getNome(),
                candidato.getEmail(),
                candidato.getPartido(),
                candidato.getCargo(),
                candidato.getBiografia(),
                candidato.getStatus()
        );
    }
}
