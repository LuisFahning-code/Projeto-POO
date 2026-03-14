// Capturamos os elementos do HTML
const botaoEnviar = document.getElementById('btn-enviar');
const campoTexto = document.getElementById('campo-texto');
const areaMensagens = document.getElementById('area-mensagens');
const campoArquivo = document.getElementById('campo-arquivo');

// Enviar texto 
botaoEnviar.addEventListener('click', function() {
    const textoDigitado = campoTexto.value;
    if (textoDigitado.trim() === "") return;

    // Cria o balão de texto
    const novaMensagem = document.createElement('div');
    novaMensagem.classList.add('mensagem', 'eleitor');
    novaMensagem.innerText = textoDigitado;

    // Joga na tela e limpa o campo
    areaMensagens.appendChild(novaMensagem);
    campoTexto.value = '';
    areaMensagens.scrollTop = areaMensagens.scrollHeight;
});

//Enviar Imagem
campoArquivo.addEventListener('change', function() {
    // Pega o primeiro arquivo que o usuário selecionou
    const arquivo = this.files[0]; 
    
    if (arquivo) {
        // O FileReader é uma ferramenta nativa do JS para ler arquivos do computador do usuário
        const leitor = new FileReader();

        // Dizemos ao leitor: "Quando você terminar de carregar a foto, faça isso:"
        leitor.onload = function(evento) {
            // Cria o balão azul do eleitor
            const novaMensagem = document.createElement('div');
            novaMensagem.classList.add('mensagem', 'eleitor');
            
            // Cria a tag de imagem (<img>) pelo JavaScript
            const imagem = document.createElement('img');
            // O resultado da leitura é um texto gigante (Base64) que o navegador entende como imagem
            imagem.src = evento.target.result; 
            
            // Coloca a imagem dentro do balão, e o balão dentro da área de chat
            novaMensagem.appendChild(imagem);
            areaMensagens.appendChild(novaMensagem);
            
            areaMensagens.scrollTop = areaMensagens.scrollHeight;
        };

        // Dá a ordem para o leitor começar a ler a foto como uma URL de dados
        leitor.readAsDataURL(arquivo);
    }
});