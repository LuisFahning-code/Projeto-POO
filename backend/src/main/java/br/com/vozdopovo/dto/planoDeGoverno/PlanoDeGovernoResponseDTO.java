package br.com.vozdopovo.dto.planoDeGoverno;

import java.time.LocalDateTime;

import br.com.vozdopovo.enums.StatusPublicacao;

/**
 * MELHORIA #7: removido o campo caminhoArquivoTxt da resposta.
 *
 * O caminho absoluto do arquivo no sistema de arquivos do servidor
 * é um detalhe de infraestrutura interna — expô-lo revela a estrutura
 * de diretórios do servidor ao cliente, o que é desnecessário e
 * representa um risco de segurança (information disclosure).
 *
 * O nomeArquivoTxt é mantido pois pode ser útil para o frontend
 * exibir que o plano possui um TXT gerado (ex: "plano_1.txt").
 */
public class PlanoDeGovernoResponseDTO {

    private Long id;
    private String titulo;
    private String apresentacao;
    private StatusPublicacao status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Long candidatoId;
    private String nomeArquivoTxt;
    private LocalDateTime ultimaAtualizacaoTxtEm;

    public PlanoDeGovernoResponseDTO() {}

    public PlanoDeGovernoResponseDTO(Long id, String titulo, String apresentacao,
                                     StatusPublicacao status, LocalDateTime dataCriacao,
                                     LocalDateTime dataAtualizacao, Long candidatoId,
                                     String nomeArquivoTxt, LocalDateTime ultimaAtualizacaoTxtEm) {
        this.id = id;
        this.titulo = titulo;
        this.apresentacao = apresentacao;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.candidatoId = candidatoId;
        this.nomeArquivoTxt = nomeArquivoTxt;
        this.ultimaAtualizacaoTxtEm = ultimaAtualizacaoTxtEm;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getApresentacao() { return apresentacao; }
    public void setApresentacao(String apresentacao) { this.apresentacao = apresentacao; }

    public StatusPublicacao getStatus() { return status; }
    public void setStatus(StatusPublicacao status) { this.status = status; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    public Long getCandidatoId() { return candidatoId; }
    public void setCandidatoId(Long candidatoId) { this.candidatoId = candidatoId; }

    public String getNomeArquivoTxt() { return nomeArquivoTxt; }
    public void setNomeArquivoTxt(String nomeArquivoTxt) { this.nomeArquivoTxt = nomeArquivoTxt; }

    public LocalDateTime getUltimaAtualizacaoTxtEm() { return ultimaAtualizacaoTxtEm; }
    public void setUltimaAtualizacaoTxtEm(LocalDateTime ultimaAtualizacaoTxtEm) {
        this.ultimaAtualizacaoTxtEm = ultimaAtualizacaoTxtEm;
    }
}
