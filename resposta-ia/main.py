from genai import Client
from fastapi import FastAPI
from pydantic import BaseModel

# Conectar com a API do Gemini
client = Client(api_key="AIzaSyAT9MgQlLmfo4RhBG38G7d7AYcn9HpDbpI")
app = FastAPI()

# Definir as regras de negócio (falta combinar melhor com sousa)
class PedidoEleitor(BaseModel):
    id_candidato: int
    caminho_txt: str
    texto: str

# Receber o chamado da função processamento do Java
@app.post("/processar")

async def responder_eleitor(pedido: PedidoEleitor):

    # Abrir o arquivo com as propostas do candidato
    with open(f"plano_{pedido.id_candidato}.txt", "r", encoding="utf-8") as f:
        plano = f.read()

    # Prompt que vai ser enviado
    prompt = f"""
    Você é o assistente oficial do candidato de ID {pedido.id_candidato}.
    Use estas propostas para responder: {plano}
    Pergunta do eleitor: {pedido.texto}
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

