# Arquitetura do Stack de Autoria Narrativa

## Estado
ATIVO COMO CONTRATO EDITORIAL / nenhuma dependência nova de runtime.

## Objetivo
Tornar a criação de história, NPCs, diálogos e identidade visual mais rápida e consistente sem transferir autoridade canônica a serviços auxiliares e sem criar custo incremental obrigatório.

## Autoridade por domínio

Não existe uma ordem global única para todos os tipos de informação. Primeiro identificar o domínio da afirmação.

### Lore estruturada e estado da campanha

- **Grimoire/TTRPG.bot** — authority principal para lore estruturada, Campaign Bible/Foundations, causalidade narrativa, fatos do mundo, personagens, organizações, lugares, relações, conhecimento, segredos e estado narrativo estruturado quando registrado ali.
- **`historia/` aceita em `main`** — fonte editorial versionada da campanha: IDs estáveis, documentos revisáveis, diálogos, evidências, lifecycle, decisões editoriais e material aceito no repositório.
- **branch/PR** — proposta editorial até revisão/merge; novidade em branch não substitui fatos aceitos apenas por ser mais recente.

`historia/` não é licença para sobrescrever silenciosamente a Campaign Bible, e Grimoire não é licença para reescrever Git sem revisão. Quando as duas fontes parecerem divergir, preservar a discrepância, identificar domínio/proveniência e determinar qual lado está desatualizado, incompleto ou representa proposta. Mudança central de lore precisa ser reconciliada conscientemente nas duas superfícies relevantes.

### Runtime e capacidade mecânica

- **Narrative & Society Core / código do RPG** — autoridade sobre estado narrativo executável, persistência e efeitos reais.
- **Modlist física, JARs e providers mecânicos** — autoridade sobre presença, versão e mecânicas reais de cada provider.
- Lore nunca prova que um mod possui uma capability, API, entidade, atributo, biome, estrutura ou evento.

### Ferramentas auxiliares

ChatGPT, MCPs, geradores visuais e outras ferramentas de autoria produzem propostas, análise e assets. Nunca viram authority canônica apenas por terem produzido conteúdo.

Nenhum MCP, plugin, IA ou framework externo pode sobrescrever fatos canônicos, conhecimento de NPC, eventos, relações, recompensas, morte/retorno ou estado do save sem passar pelas authorities e contratos correspondentes.

## Política de custo zero

- O fluxo recomendado não pode depender de assinatura adicional, créditos comprados ou API paga.
- Recurso já incluído no ambiente atual pode ser usado enquanto não exigir pagamento incremental.
- Se uma ferramenta gratuita passar a exigir pagamento para a função usada, ela é substituída ou removida.
- Trial temporário não conta como solução gratuita permanente.
- Integrações opcionais pagas podem ser citadas apenas como referência, nunca como requisito.

## Stack adotado

### Camada A — ativa agora

- **Grimoire/TTRPG.bot**: authority principal para Campaign Bible/lore estruturada quando a conexão estiver disponível.
- **GitHub / `historia/` em `main`**: fonte editorial versionada, IDs, diff, branch/PR, auditoria, revisão e rollback.
- **ChatGPT**: autor/revisor assistido, respeitando authorities, política sem spoilers e contratos do projeto.
- **Geração de imagem disponível no ChatGPT**: concept art e portrait quando não houver custo incremental.
- **Minecraft Mod Factory + Blockbench**: pipeline visual compartilhada e validação de assets.

### Camada B — ferramentas narrativas opcionais

- **AIStoryHub MCP**: apenas experimento opcional para análise/voz/continuidade enquanto existir caminho realmente gratuito; não deve funcionar como segunda Campaign Bible autoritativa.
- **Spindle MCP**: alternativa open-source/local-first para pesquisa/revisão em clientes MCP compatíveis.
- **story-architect-mcp**: alternativa MIT baseada em arquivos para worldbuilding/auditoria de continuidade.

Essas ferramentas só fazem sentido quando oferecem uma capacidade concreta que Grimoire + GitHub + tooling local não atendem. Não duplicar permanentemente todo o cânone em outra story bible.

### Camada C — diálogos ramificados

- **Ink/Inky**: opção preferencial quando o Markdown deixar de ser suficiente. É gratuito e MIT.
- **Yarn Spinner**: alternativa para narrativa ramificada; avaliar licença vigente e integração antes de adoção.

Até existir necessidade concreta, diálogos permanecem no formato editorial versionado do repositório.

## Ferramentas não adotadas

- **AINPC Engine**: não recomendado para o fluxo padrão porque depende de serviço/trial externo. Só reconsiderar se existir opção permanente sem custo e uma necessidade não coberta localmente.
- Qualquer gerador visual baseado exclusivamente em créditos pagos: não é dependência do projeto.

## Regra contra split-brain

A reconciliação detalhada entre Grimoire e GitHub está em `12-grimoire-github-sync.md`.

Princípios:

1. pesquisar `main`, branches/PRs relevantes e Grimoire antes de criar elemento central quando a conexão estiver disponível;
2. usar IDs estáveis como referência cruzada;
3. tratar branch/PR como proposta até decisão editorial;
4. preservar Grimoire como authority principal de lore estruturada e `historia/` como registro editorial versionado;
5. não executar sincronização bidirecional cega;
6. se houver divergência, abrir reconciliação explícita por domínio/proveniência em vez de escolher automaticamente a fonte mais nova;
7. se Grimoire for necessário e estiver indisponível, preservar a lacuna como pendente/fail-closed em vez de inventar canon.

## Segurança

- Nunca versionar credenciais ou chaves de acesso.
- Nunca conceder escrita direta em `main` a ferramenta externa.
- Começar integrações com escopo mínimo.
- Toda integração nova precisa de teste com conteúdo não sensível antes de receber lore completa.
- MCPs de terceiros são fornecedores externos e precisam ser avaliados antes de receber conteúdo privado.

## Política de adoção

Uma ferramenta externa só vira parte recomendada do fluxo se cumprir todos os critérios:

1. custo incremental obrigatório igual a zero;
2. resolve uma necessidade que o stack atual não cobre bem;
3. reduz trabalho manual de forma mensurável;
4. exporta ou permite recuperar o trabalho sem lock-in crítico;
5. preserva IDs/proveniência do projeto;
6. não exige autoridade sobre runtime ou canon;
7. não força duplicação manual permanente da Campaign Bible;
8. melhora consistência, revisão ou produção visual de forma verificável.

## Decisões atuais

- Grimoire/TTRPG.bot: **AUTHORITY PRINCIPAL DE LORE E CAMPAIGN BIBLE ESTRUTURADA**.
- GitHub / `historia/` em `main`: **FONTE EDITORIAL VERSIONADA / REVISÃO, IDs E HISTÓRICO DE MUDANÇAS**.
- ChatGPT: **AUTORIA/REVISÃO ASSISTIDA, NÃO AUTHORITY**.
- Minecraft Mod Factory + Blockbench: **AUTHORITY/PIPELINE VISUAL COMPARTILHADA**.
- Geração visual no ChatGPT: **ADOTADA para concept/look-dev quando incluída sem custo incremental**.
- AIStoryHub: **EXPERIMENTO OPCIONAL, NÃO STORY BIBLE AUTORITATIVA**.
- Spindle/story-architect-mcp: **ALTERNATIVAS OPCIONAIS OPEN-SOURCE/LOCAL-FIRST**.
- Ink/Inky: **CANDIDATO GRATUITO quando branching complexo justificar**.
- AINPC Engine: **FORA DO FLUXO RECOMENDADO**.
- IA in-game: continua **OPCIONAL / NÃO É AUTORIDADE**.
