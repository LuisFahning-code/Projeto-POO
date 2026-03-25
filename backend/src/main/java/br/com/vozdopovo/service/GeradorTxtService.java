package br.com.vozdopovo.service;

import br.com.vozdopovo.entity.PlanoDeGoverno;

public interface GeradorTxtService {

    /**
     * Gera o arquivo TXT do plano de governo com todo o conteúdo PUBLICADO
     * (plano + temas + propostas) e salva o caminho no banco.
     */
    void gerarTxt(PlanoDeGoverno plano);
}
