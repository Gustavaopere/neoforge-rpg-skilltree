# Arquitetura do Stack de Autoria Narrativa

## Estado
ATIVO COMO CONTRATO EDITORIAL / nenhuma dependência nova de runtime.

## Objetivo
Tornar a criação de história, NPCs, diálogos e identidade visual mais rápida e consistente sem transferir autoridade canônica a serviços externos.

## Autoridade
A ordem de autoridade é:

1. `historia/` em `main` — fonte editorial versionada aceita.
2. Narrative & Society Core — autoridade de runtime sobre estado narrativo persistente.
3. Providers mecânicos — autoridade apenas sobre suas próprias mecânicas.
4. Ferramentas externas de autoria — rascunho, análise, revisão e geração de assets; nunca verdade canônica por si mesmas.

Nenhum MCP, plugin, IA ou framework externo pode sobrescrever fatos canônicos, conhecimento de NPC, eventos, relações, recompensas, morte/retorno ou estado do save sem passar pelos contratos existentes.

## Stack adotado

### Camada A — ativa agora

- **GitHub**: fonte versionada, diff, branch/PR, auditoria e rollback.
- **ChatGPT**: autor/revisor assistido, respeitando política sem spoilers e contratos de `historia/`.
- **OpenArt**: concept art, portrait e referência visual. Saída é asset candidato até revisão humana/editorial.

### Camada B — opcional, somente para autoria

- **AIStoryHub MCP**: candidato preferencial para story bible derivada, capítulos/cenas, consistência de voz e revisão de personagens.
- **StoryCanon MCP**: alternativa de story bible. Não usar simultaneamente com AIStoryHub como segunda base paralela.
- **AINPC Engine MCP**: laboratório para testar coerência de personalidade, diálogo, memória e voz de NPCs; não é authority e não entra no runtime por padrão.

### Camada C — framework candidato

- **Ink/Inky**: candidato para diálogos muito ramificados. Só entra após um PoC demonstrar exportação determinística e adaptação compatível com o Narrative Core. Até lá, diálogos permanecem no formato editorial versionado do repositório.

## Regra contra duplicação de cânone

Ferramentas externas recebem um recorte exportado do GitHub e devolvem propostas. Alterações aceitas retornam ao repositório via branch/PR. Não existe sincronização bidirecional automática inicialmente.

Fluxo:

`GitHub/main -> recorte de contexto -> ferramenta externa -> proposta -> revisão -> branch/PR -> GitHub/main`

## Segurança

- Nunca versionar API keys, bearer tokens, cookies ou credenciais.
- Nunca conceder write em `main` diretamente a ferramenta externa.
- Preferir OAuth quando o provider suportar.
- Começar MCPs em modo read/fetch quando possível.
- Toda integração nova precisa de teste com conteúdo não sensível antes de receber lore completa.
- MCPs de terceiros são tratados como fornecedores externos e precisam ser confiáveis antes de receber conteúdo privado.

## Política de adoção

Uma ferramenta externa só vira parte recomendada do fluxo se cumprir todos os critérios:

1. reduz trabalho manual de forma mensurável;
2. exporta ou permite recuperar o trabalho sem lock-in crítico;
3. preserva IDs estáveis do projeto;
4. não exige autoridade sobre o runtime;
5. não força duplicação manual permanente do cânone;
6. melhora consistência, revisão ou produção visual de forma verificável.

## Decisões atuais

- OpenArt: **ADOTADO para autoria visual**.
- AIStoryHub: **CANDIDATO PARA PoC**.
- StoryCanon: **ALTERNATIVA, não usar em paralelo no PoC inicial**.
- AINPC Engine: **CANDIDATO PARA LABORATÓRIO FUTURO**.
- Ink/Inky: **CANDIDATO, aguardando necessidade concreta de branching complexo**.
- IA in-game: continua **OPCIONAL / NÃO É AUTORIDADE**.
