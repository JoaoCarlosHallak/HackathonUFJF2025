// Espera o HTML carregar antes de tentar encontrar os elementos
document.addEventListener('DOMContentLoaded', function() {

    // 1. PEGA OS ELEMENTOS DO HTML
    //    (Esta é a parte que estava faltando e causando o erro)
    const chatForm = document.getElementById('chatForm');
    const perguntaInput = document.getElementById('pergunta');
    const resultadoDiv = document.getElementById('resultado');

    // Verifica se os elementos foram encontrados (bom para debug)
    if (!chatForm || !perguntaInput || !resultadoDiv) {
        console.error('Erro: Não foi possível encontrar um ou mais elementos do formulário (chatForm, pergunta, resultado). Verifique os IDs no seu HTML.');
        return;
    }

    // 2. ADICIONA O "OUVINTE" DE SUBMISSÃO
    //    (Este é o bloco de código que você enviou)
    chatForm.addEventListener('submit', function(event) {
        // Impede o envio padrão do formulário (que recarregaria a página)
        event.preventDefault();

        const pergunta = perguntaInput.value.trim();
        if (!pergunta) {
            alert('Por favor, digite uma pergunta.');
            return;
        }

        // Feedback visual de carregamento
        resultadoDiv.innerHTML = '<span class="loading">Processando sua pergunta... Por favor, aguarde.</span>';
        resultadoDiv.classList.add('loading');

        // --- CHAMADA PARA SUA API SPRING BOOT ---
        fetch('/api', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                dateTime: null,
                payload: pergunta
            })
        })
        .then(response => {
            if (!response.ok) {
                 // Tenta ler a mensagem de erro do corpo da resposta, se houver
                return response.text().then(errorMessage => {
                    throw new Error(`Erro ${response.status}: ${errorMessage || response.statusText}`);
                });
            }
            // Trata a resposta como JSON
            return response.json();
        })
        .then(data => {
            // Exibe a propriedade que contém a resposta
            // Assumindo que seu ResponseObject tenha um campo 'payload'
            // Se o nome for outro (ex: 'answer'), troque data.payload por data.answer

            // Verifique o console para ver o objeto 'data' completo
            console.log('Resposta da API:', data);

            resultadoDiv.textContent = data.payload;

            resultadoDiv.classList.remove('loading');
        })
        .catch(error => {
            // Exibe o erro
            console.error('Erro ao chamar a API:', error);
            resultadoDiv.textContent = `Erro ao processar a pergunta: ${error.message}`;
            resultadoDiv.classList.remove('loading');
        });
    });

}); // Fim do 'DOMContentLoaded'