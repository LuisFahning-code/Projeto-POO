import os
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from google import genai
from dotenv import load_dotenv

load_dotenv()

# Conectar com a API do Gemini
client = genai.Client(api_key=os.getenv("GEMINI_API_KEY"))
app = FastAPI()

# Definir as regras de negócio (falta combinar melhor com sousa)
class PedidoEleitor(BaseModel):
    id_candidato: int
    caminho_txt: str
    pergunta: str

# Receber o chamado da função processamento do Java
@app.post("/processar")

async def responder_eleitor(pedido: PedidoEleitor):

    caminho = pedido.caminho_txt
    # Validação de segurança
    diretorio_permitido = os.getenv("PLANOS_DIR", "/home/user/projeto/arquivos/planos")
    if not os.path.abspath(caminho).startswith(os.path.abspath(diretorio_permitido)):
        raise HTTPException(status_code=403, detail="Acesso negado ao caminho informado.")

    if not os.path.exists(caminho):
        raise HTTPException(status_code=404, detail="Arquivo TXT do plano não encontrado.")
    
    # Abrir o arquivo com as propostas do candidato
    with open(caminho, "r", encoding="utf-8") as f:
        plano = f.read()

    # Prompt que vai ser enviado
    prompt = f"""
    Você é o assistente oficial do candidato de ID {pedido.id_candidato}.
    Use estas propostas para responder: {plano}
    Pergunta do eleitor: {pedido.pergunta}
    Responda de forma curta, gentil e humana.
    """

    # Gerando resposta com o Gemini
    response = client.models.generate_content(
        model="gemini-2.0-flash",
        contents=prompt
    )
    
    # Retornando a resposta para o Backend(Java)
    return {
        "resposta": response.text,
        "validacao_simulada": "Sucesso (GPS Ok)",
    }

