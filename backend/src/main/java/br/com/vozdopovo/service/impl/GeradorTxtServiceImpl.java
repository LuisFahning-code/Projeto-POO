package br.com.vozdopovo.service.impl;

import br.com.vozdopovo.entity.PlanoDeGoverno;
import br.com.vozdopovo.entity.Proposta;
import br.com.vozdopovo.entity.Tema;
import br.com.vozdopovo.enums.StatusPublicacao;
import br.com.vozdopovo.repository.PlanoDeGovernoRepository;
import br.com.vozdopovo.repository.PropostaRepository;
import br.com.vozdopovo.repository.TemaRepository;
import br.com.vozdopovo.service.GeradorTxtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GeradorTxtServiceImpl implements GeradorTxtService {

    private final TemaRepository temaRepository;
    private final PropostaRepository propostaRepository;
    private final PlanoDeGovernoRepository planoDeGovernoRepository;

    @Value("${plano.txt.diretorio}")
    private String diretorioBase;

    public GeradorTxtServiceImpl(TemaRepository temaRepository,
                                  PropostaRepository propostaRepository,
                                  PlanoDeGovernoRepository planoDeGovernoRepository) {
        this.temaRepository = temaRepository;
        this.propostaRepository = propostaRepository;
        this.planoDeGovernoRepository = planoDeGovernoRepository;
    }

    @Transactional
    @Override
    public void gerarTxt(PlanoDeGoverno plano) {
        String conteudo = montarConteudo(plano);
        String nomeArquivo = "plano_" + plano.getCandidato().getId() + ".txt";
        String caminho = diretorioBase + "/" + nomeArquivo;

        salvarArquivo(caminho, conteudo);

        // Atualiza o caminho no banco
        plano.setNomeArquivoTxt(nomeArquivo);
        plano.setCaminhoArquivoTxt(caminho);
        plano.setUltimaAtualizacaoTxtEm(LocalDateTime.now());
        planoDeGovernoRepository.save(plano);
    }

    // =============================
    //  MÉTODOS AUXILIARES
    // =============================

    private String montarConteudo(PlanoDeGoverno plano) {
        StringBuilder sb = new StringBuilder();

        // Cabeçalho do plano
        sb.append("PLANO DE GOVERNO\n");
        sb.append("================\n\n");
        sb.append("Candidato: ").append(plano.getCandidato().getNome()).append("\n");
        sb.append("Título: ").append(plano.getTitulo()).append("\n\n");

        if (plano.getApresentacao() != null && !plano.getApresentacao().isBlank()) {
            sb.append("Apresentação:\n");
            sb.append(plano.getApresentacao()).append("\n\n");
        }

        // Temas publicados
        List<Tema> temas = temaRepository.findByPlanoDeGovernoId(plano.getId())
                .stream()
                .filter(t -> t.getStatus() == StatusPublicacao.PUBLICADO)
                .toList();

        if (temas.isEmpty()) {
            sb.append("Nenhum tema publicado.\n");
            return sb.toString();
        }

        sb.append("TEMAS E PROPOSTAS\n");
        sb.append("=================\n\n");

        for (Tema tema : temas) {
            sb.append("Tema: ").append(tema.getTitulo()).append("\n");

            if (tema.getDescricao() != null && !tema.getDescricao().isBlank()) {
                sb.append("Descrição: ").append(tema.getDescricao()).append("\n");
            }

            // Propostas publicadas daquele tema
            List<Proposta> propostas = propostaRepository
                    .findByTemaIdAndStatus(tema.getId(), StatusPublicacao.PUBLICADO);

            if (propostas.isEmpty()) {
                sb.append("  Nenhuma proposta publicada para este tema.\n\n");
                continue;
            }

            sb.append("Propostas:\n");
            for (Proposta proposta : propostas) {
                sb.append("  - ").append(proposta.getTitulo()).append("\n");
                sb.append("    Resumo: ").append(proposta.getResumo()).append("\n");

                if (proposta.getDetalhamento() != null && !proposta.getDetalhamento().isBlank()) {
                    sb.append("    Detalhamento: ").append(proposta.getDetalhamento()).append("\n");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private void salvarArquivo(String caminho, String conteudo) {
        try {
            Path path = Paths.get(caminho);
            // Cria o diretório se não existir
            Files.createDirectories(path.getParent());
            Files.writeString(path, conteudo, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar o arquivo TXT do plano de governo: " + e.getMessage());
        }
    }
}
