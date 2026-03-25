package br.com.vozdopovo.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.vozdopovo.entity.Candidato;
import br.com.vozdopovo.entity.Eleitor;
import br.com.vozdopovo.entity.Interacao;
import br.com.vozdopovo.entity.PlanoDeGoverno;
import br.com.vozdopovo.entity.Proposta;
import br.com.vozdopovo.entity.Tema;
import br.com.vozdopovo.enums.StatusConta;
import br.com.vozdopovo.enums.StatusInteracao;
import br.com.vozdopovo.enums.StatusPublicacao;
import br.com.vozdopovo.enums.TipoInteracao;
import br.com.vozdopovo.repository.CandidatoRepository;
import br.com.vozdopovo.repository.EleitorRepository;
import br.com.vozdopovo.repository.InteracaoRepository;
import br.com.vozdopovo.repository.PlanoDeGovernoRepository;
import br.com.vozdopovo.repository.PropostaRepository;
import br.com.vozdopovo.repository.TemaRepository;

/**
 * Popula o banco H2 com dados de teste no startup.
 * Substitui o data.sql — as senhas são hasheadas pelo BCrypt em tempo de execução.
 *
 * Credenciais de teste:
 *   Candidatos : ana.souza@email.com     / senha123
 *                carlos.mendes@email.com / senha123
 *   Eleitores  : fernanda.lima@email.com / senha123
 *                joao.silva@email.com    / senha123
 *                mariana.costa@email.com / senha123
 *
 * Coloque em: src/main/java/br/com/vozdopovo/config/DataLoader.java
 */
@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner carregarDados(
            CandidatoRepository candidatoRepo,
            EleitorRepository eleitorRepo,
            PlanoDeGovernoRepository planoRepo,
            TemaRepository temaRepo,
            PropostaRepository propostaRepo,
            InteracaoRepository interacaoRepo,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // -------------------------------------------------------
            // Candidatos
            // -------------------------------------------------------
            Candidato ana = new Candidato();
            ana.setNome("Ana Beatriz Souza");
            ana.setEmail("ana.souza@email.com");
            ana.setSenha(passwordEncoder.encode("senha123"));
            ana.setPartido("Partido da Renovação Nacional");
            ana.setCargo("Vereadora");
            ana.setBiografia("Engenheira civil com 12 anos de experiência em obras públicas. Atua em projetos de mobilidade urbana e saneamento básico.");
            ana.setStatus(StatusConta.ATIVA);
            ana = candidatoRepo.save(ana);

            Candidato carlos = new Candidato();
            carlos.setNome("Carlos Mendes");
            carlos.setEmail("carlos.mendes@email.com");
            carlos.setSenha(passwordEncoder.encode("senha123"));
            carlos.setPartido("Movimento Democrático Popular");
            carlos.setCargo("Prefeito");
            carlos.setBiografia("Professor universitário e ex-secretário de educação. Defende gestão participativa e transparência no uso dos recursos públicos.");
            carlos.setStatus(StatusConta.ATIVA);
            carlos = candidatoRepo.save(carlos);

            // -------------------------------------------------------
            // Eleitores
            // -------------------------------------------------------
            Eleitor fernanda = new Eleitor();
            fernanda.setNome("Fernanda Lima");
            fernanda.setEmail("fernanda.lima@email.com");
            fernanda.setSenha(passwordEncoder.encode("senha123"));
            fernanda.setStatus(StatusConta.ATIVA);
            fernanda = eleitorRepo.save(fernanda);

            Eleitor joao = new Eleitor();
            joao.setNome("João Pedro Silva");
            joao.setEmail("joao.silva@email.com");
            joao.setSenha(passwordEncoder.encode("senha123"));
            joao.setStatus(StatusConta.ATIVA);
            joao = eleitorRepo.save(joao);

            Eleitor mariana = new Eleitor();
            mariana.setNome("Mariana Costa");
            mariana.setEmail("mariana.costa@email.com");
            mariana.setSenha(passwordEncoder.encode("senha123"));
            mariana.setStatus(StatusConta.ATIVA);
            mariana = eleitorRepo.save(mariana);

            // -------------------------------------------------------
            // Planos de governo
            // -------------------------------------------------------
            PlanoDeGoverno planoAna = new PlanoDeGoverno();
            planoAna.setTitulo("Plano de Governo 2025 — Ana Beatriz Souza");
            planoAna.setApresentacao("Uma cidade mais justa começa pela infraestrutura. Meu plano prioriza mobilidade, saneamento e habitação para todas as famílias.");
            planoAna.setStatus(StatusPublicacao.PUBLICADO);
            planoAna.setDataCriacao(LocalDateTime.of(2025, 1, 10, 9, 0));
            planoAna.setDataAtualizacao(LocalDateTime.of(2025, 2, 15, 14, 30));
            planoAna.setCandidato(ana);
            planoAna = planoRepo.save(planoAna);

            PlanoDeGoverno planoCarlos = new PlanoDeGoverno();
            planoCarlos.setTitulo("Plano de Governo 2025 — Carlos Mendes");
            planoCarlos.setApresentacao("Educação de qualidade é a base de tudo. Proponho escolas em tempo integral, capacitação de professores e tecnologia para todos os alunos.");
            planoCarlos.setStatus(StatusPublicacao.PUBLICADO);
            planoCarlos.setDataCriacao(LocalDateTime.of(2025, 1, 12, 10, 0));
            planoCarlos.setDataAtualizacao(LocalDateTime.of(2025, 2, 20, 11, 0));
            planoCarlos.setCandidato(carlos);
            planoCarlos = planoRepo.save(planoCarlos);

            // -------------------------------------------------------
            // Temas
            // -------------------------------------------------------
            Tema mobilidade = new Tema();
            mobilidade.setTitulo("Mobilidade Urbana");
            mobilidade.setDescricao("Propostas voltadas para transporte coletivo, ciclovias e calçadas acessíveis.");
            mobilidade.setStatus(StatusPublicacao.PUBLICADO);
            mobilidade.setDataCriacao(LocalDateTime.of(2025, 1, 10, 9, 10));
            mobilidade.setDataAtualizacao(LocalDateTime.of(2025, 2, 15, 14, 30));
            mobilidade.setPlanoDeGoverno(planoAna);
            mobilidade = temaRepo.save(mobilidade);

            Tema saneamento = new Tema();
            saneamento.setTitulo("Saneamento Básico");
            saneamento.setDescricao("Ampliação da cobertura de água tratada e esgotamento sanitário.");
            saneamento.setStatus(StatusPublicacao.PUBLICADO);
            saneamento.setDataCriacao(LocalDateTime.of(2025, 1, 10, 9, 20));
            saneamento.setDataAtualizacao(LocalDateTime.of(2025, 2, 15, 14, 30));
            saneamento.setPlanoDeGoverno(planoAna);
            saneamento = temaRepo.save(saneamento);

            Tema educacao = new Tema();
            educacao.setTitulo("Educação Integral");
            educacao.setDescricao("Escolas em tempo integral com reforço em ciências, artes e esportes.");
            educacao.setStatus(StatusPublicacao.PUBLICADO);
            educacao.setDataCriacao(LocalDateTime.of(2025, 1, 12, 10, 10));
            educacao.setDataAtualizacao(LocalDateTime.of(2025, 2, 20, 11, 0));
            educacao.setPlanoDeGoverno(planoCarlos);
            educacao = temaRepo.save(educacao);

            Tema tecnologia = new Tema();
            tecnologia.setTitulo("Tecnologia nas Escolas");
            tecnologia.setDescricao("Laboratórios de informática, internet de qualidade e formação digital.");
            tecnologia.setStatus(StatusPublicacao.PUBLICADO);
            tecnologia.setDataCriacao(LocalDateTime.of(2025, 1, 12, 10, 20));
            tecnologia.setDataAtualizacao(LocalDateTime.of(2025, 2, 20, 11, 0));
            tecnologia.setPlanoDeGoverno(planoCarlos);
            tecnologia = temaRepo.save(tecnologia);

            // -------------------------------------------------------
            // Propostas
            // -------------------------------------------------------
            Proposta corredor = new Proposta();
            corredor.setTitulo("Corredor expresso de ônibus no Centro");
            corredor.setResumo("Criar faixa exclusiva de ônibus na Avenida Principal para reduzir tempo de deslocamento em 30%.");
            corredor.setDetalhamento("O corredor terá 4,2 km de extensão com 8 paradas remodeladas, sistema de prioridade semafórica e integração com o terminal central.");
            corredor.setStatus(StatusPublicacao.PUBLICADO);
            corredor.setDataCriacao(LocalDateTime.of(2025, 1, 15, 10, 0));
            corredor.setDataAtualizacao(LocalDateTime.of(2025, 2, 10, 9, 0));
            corredor.setPlanoDeGoverno(planoAna);
            corredor.setTema(mobilidade);
            propostaRepo.save(corredor);

            Proposta calcada = new Proposta();
            calcada.setTitulo("Programa Calçada Acessível");
            calcada.setResumo("Reforma de 50 km de calçadas com piso tátil, rampas e arborização nos bairros periféricos.");
            calcada.setDetalhamento("Parceria com a Secretaria de Obras e associações de moradores para mapear os pontos críticos.");
            calcada.setStatus(StatusPublicacao.PUBLICADO);
            calcada.setDataCriacao(LocalDateTime.of(2025, 1, 16, 11, 0));
            calcada.setDataAtualizacao(LocalDateTime.of(2025, 2, 12, 8, 0));
            calcada.setPlanoDeGoverno(planoAna);
            calcada.setTema(mobilidade);
            propostaRepo.save(calcada);

            Proposta esgoto = new Proposta();
            esgoto.setTitulo("Ampliação da rede de esgoto no Bairro Alto");
            esgoto.setResumo("Levar coleta e tratamento de esgoto a 3.200 famílias que ainda utilizam fossas sépticas.");
            esgoto.setDetalhamento("Obra estimada em R$ 8 milhões com financiamento federal via PAC Saneamento. Prazo: 18 meses.");
            esgoto.setStatus(StatusPublicacao.PUBLICADO);
            esgoto.setDataCriacao(LocalDateTime.of(2025, 1, 17, 14, 0));
            esgoto.setDataAtualizacao(LocalDateTime.of(2025, 2, 13, 16, 0));
            esgoto.setPlanoDeGoverno(planoAna);
            esgoto.setTema(saneamento);
            propostaRepo.save(esgoto);

            Proposta escolaIntegral = new Proposta();
            escolaIntegral.setTitulo("Escola em tempo integral no Distrito Sul");
            escolaIntegral.setResumo("Converter duas escolas municipais para regime integral com alimentação e atividades extracurriculares.");
            escolaIntegral.setDetalhamento("As unidades EMEF João XXIII e EMEF Jardim das Flores passarão por reforma. Previsão: 800 alunos atendidos.");
            escolaIntegral.setStatus(StatusPublicacao.PUBLICADO);
            escolaIntegral.setDataCriacao(LocalDateTime.of(2025, 1, 18, 9, 0));
            escolaIntegral.setDataAtualizacao(LocalDateTime.of(2025, 2, 18, 10, 0));
            escolaIntegral.setPlanoDeGoverno(planoCarlos);
            escolaIntegral.setTema(educacao);
            propostaRepo.save(escolaIntegral);

            Proposta internet = new Proposta();
            internet.setTitulo("Internet nas escolas municipais");
            internet.setResumo("Instalar fibra óptica e wi-fi em todas as 32 escolas da rede municipal até dezembro de 2025.");
            internet.setDetalhamento("Licitação para link dedicado de 100 Mbps por unidade, com suporte técnico e treinamento para professores.");
            internet.setStatus(StatusPublicacao.PUBLICADO);
            internet.setDataCriacao(LocalDateTime.of(2025, 1, 19, 10, 0));
            internet.setDataAtualizacao(LocalDateTime.of(2025, 2, 19, 11, 0));
            internet.setPlanoDeGoverno(planoCarlos);
            internet.setTema(tecnologia);
            propostaRepo.save(internet);

            // -------------------------------------------------------
            // Interações
            // -------------------------------------------------------
            Interacao i1 = new Interacao();
            i1.setTitulo("Quando começa a obra do corredor de ônibus?");
            i1.setConteudo("Moro na Avenida Principal e fico curioso para saber se há previsão de início das obras. Vai haver interdição do trânsito?");
            i1.setTipo(TipoInteracao.DUVIDA);
            i1.setStatus(StatusInteracao.RESPONDIDA);
            i1.setResposta("A previsão é para o segundo semestre de 2025, após licitação. Haverá interdições parciais por trechos de 500m com desvios sinalizados.");
            i1.setEleitor(fernanda);
            i1.setCandidato(ana);
            i1.setDataInicio(LocalDateTime.of(2025, 3, 1, 8, 30));
            i1.setDataResposta(LocalDateTime.of(2025, 3, 3, 14, 0));
            interacaoRepo.save(i1);

            Interacao i2 = new Interacao();
            i2.setTitulo("Falta piso tátil na Rua das Flores");
            i2.setConteudo("Sou cadeirante e a Rua das Flores está completamente inacessível. As calçadas estão quebradas e sem rampas nas esquinas.");
            i2.setTipo(TipoInteracao.DEMANDA);
            i2.setStatus(StatusInteracao.EM_ANALISE);
            i2.setEleitor(joao);
            i2.setCandidato(ana);
            i2.setDataInicio(LocalDateTime.of(2025, 3, 5, 10, 0));
            interacaoRepo.save(i2);

            Interacao i3 = new Interacao();
            i3.setTitulo("Escola integral para o Bairro Norte também?");
            i3.setConteudo("Vi que as escolas do Distrito Sul serão contempladas, mas o Bairro Norte tem a mesma necessidade. Existe previsão para incluir nossa região?");
            i3.setTipo(TipoInteracao.DUVIDA);
            i3.setStatus(StatusInteracao.RECEBIDA);
            i3.setEleitor(mariana);
            i3.setCandidato(carlos);
            i3.setDataInicio(LocalDateTime.of(2025, 3, 8, 9, 15));
            interacaoRepo.save(i3);

            Interacao i4 = new Interacao();
            i4.setTitulo("Internet caindo toda semana na EMEF Vila Nova");
            i4.setConteudo("A internet da escola do meu filho cai toda semana. O programa de fibra óptica vai substituir o link atual ou só reforçar?");
            i4.setTipo(TipoInteracao.DUVIDA);
            i4.setStatus(StatusInteracao.RESPONDIDA);
            i4.setResposta("O programa vai substituir completamente a infraestrutura atual, instalando fibra óptica com link dedicado e equipamentos novos.");
            i4.setEleitor(fernanda);
            i4.setCandidato(carlos);
            i4.setDataInicio(LocalDateTime.of(2025, 3, 10, 11, 0));
            i4.setDataResposta(LocalDateTime.of(2025, 3, 11, 9, 0));
            interacaoRepo.save(i4);

            Interacao i5 = new Interacao();
            i5.setTitulo("Comprovação: Assinatura do contrato PAC Saneamento");
            i5.setConteudo("Compartilho o link da publicação no Diário Oficial com a assinatura do termo de compromisso para o saneamento do Bairro Alto.");
            i5.setTipo(TipoInteracao.DEMANDA);
            i5.setStatus(StatusInteracao.FINALIZADA);
            i5.setUrlComprovacao("https://www.in.gov.br/exemplo-publicacao-pac-saneamento");
            i5.setResposta("Documento recebido e registrado. O processo licitatório será aberto em 60 dias. Obrigada pela fiscalização!");
            i5.setEleitor(joao);
            i5.setCandidato(ana);
            i5.setDataInicio(LocalDateTime.of(2025, 3, 12, 8, 0));
            i5.setDataResposta(LocalDateTime.of(2025, 3, 13, 10, 30));
            interacaoRepo.save(i5);

            System.out.println("""
                    =====================================================
                     DataLoader — banco populado com sucesso!
                     Candidatos : ana.souza@email.com     / senha123
                                  carlos.mendes@email.com / senha123
                     Eleitores  : fernanda.lima@email.com / senha123
                                  joao.silva@email.com    / senha123
                                  mariana.costa@email.com / senha123
                    =====================================================
                    """);
        };
    }
}
