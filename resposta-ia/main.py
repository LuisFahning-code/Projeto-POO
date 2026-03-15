from genai import Client
from fastapi import FastAPI
from pydantic import BaseModel

# Conectar com a API do Gemini
client = Client(api_key="SUA_CHAVE_AQUI")
app = FastAPI()

# Definir as regras de negócio (falta combinar melhor com sousa)
class PedidoEleitor(BaseModel):
    texto: str
    id_candidato: int

# Receber o chamado da função processamento do Java
@app.post("/processar")

async def responder_eleitor(pedido: PedidoEleitor):

    # Conferir se o arquivo a ser lido existe e fazer a leitura caso exista (Tratamento de erros)
    try:
        with open(f"propostas_{pedido.id_candidato}.txt", "r", encoding="utf-8") as f:
            propostas = f.read()

        # Prompt que vai ser enviado
        prompt = f"""
        Você é o assistente oficial do candidato de ID {pedido.id_candidato}.
        Use estas propostas para responder: {propostas}
        Pergunta do eleitor: {pedido.texto}
        Responda de forma curta, gentil e humana.
        """

        # Gerando resposta com o Gemini
        response = client.models.generate_content(
            model="gemini-2.0-flash", # Você já pode usar a versão mais nova do modelo aqui!
            contents=prompt
        )
        
        # Retornando a resposta para o Backend(Java)
        return {
            "resposta": response.text,
            "validacao_simulada": "Sucesso (GPS Ok)",
            "status": 200
        }
    except Exception as e:
        return {"erro": str(e), "status": 500}

