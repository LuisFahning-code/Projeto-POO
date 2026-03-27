package br.com.vozdopovo.mapper;

import br.com.vozdopovo.dto.planoDeGoverno.PlanoDeGovernoRequestDTO;
import br.com.vozdopovo.dto.planoDeGoverno.PlanoDeGovernoResponseDTO;
import br.com.vozdopovo.entity.PlanoDeGoverno;

public class PlanoDeGovernoMapper {

    public static PlanoDeGoverno toEntity(PlanoDeGovernoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        PlanoDeGoverno plano = new PlanoDeGoverno();
        plano.setTitulo(dto.getTitulo());
        plano.setApresentacao(dto.getApresentacao());
        // MELHORIA #7: nomeArquivoTxt e caminhoArquivoTxt removidos —
        // esses campos são preenchidos exclusivamente pelo GeradorTxtService
        // e nunca devem vir do cliente.

        return plano;
    }

    public static PlanoDeGovernoResponseDTO toResponseDTO(PlanoDeGoverno plano) {
        if (plano == null) {
            return null;
        }

        PlanoDeGovernoResponseDTO dto = new PlanoDeGovernoResponseDTO();
        dto.setId(plano.getId());
        dto.setTitulo(plano.getTitulo());
        dto.setApresentacao(plano.getApresentacao());
        dto.setStatus(plano.getStatus());
        dto.setDataCriacao(plano.getDataCriacao());
        dto.setDataAtualizacao(plano.getDataAtualizacao());
        dto.setNomeArquivoTxt(plano.getNomeArquivoTxt());
        // MELHORIA #7: caminhoArquivoTxt não é exposto na resposta ao cliente —
        // é um detalhe de infraestrutura interno do servidor.
        dto.setUltimaAtualizacaoTxtEm(plano.getUltimaAtualizacaoTxtEm());

        if (plano.getCandidato() != null) {
            dto.setCandidatoId(plano.getCandidato().getId());
        }

        return dto;
    }
}
