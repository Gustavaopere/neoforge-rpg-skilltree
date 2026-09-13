# Arquitetura do Stack de Autoria Narrativa

## Estado
ATIVO COMO CONTRATO EDITORIAL / nenhuma dependência nova de runtime.

## Objetivo
Tornar a criação de história, NPCs, diálogos e identidade visual mais rápida e consistente sem transferir autoridade canônica a serviços externos e sem criar custo incremental obrigatório.

## Autoridade
A ordem de autoridade é:

1. `historia/` em `main` — fonte editorial versionada aceita.
2. Narrative & Society Core — autoridade de runtime sobre estado narrativo persistente.
3. Providers mecânicos — autoridade apenas sobre suas próprias mecânicas.
4. Ferramentas externas de autoria — rascunho, análise, revisão e geração de assets; nunca verdade canônica por si mesmas.

Nenhum MCP, plugin, IA ou framework externo pode sobrescrever fatos canônicos, conhecimento de NPC, eventos, relações, recompensas, morte/retorno ou estado do save sem passar pelos contratos existentes.

## Política de custo zero

- O fluxo recomendado não pode depender de assinatura adicional, créditos comprados ou API paga.
- Recurso já incluído no ambiente atual pode ser usado enquanto não exigir pagamento incremental.
- Se uma ferramenta gratuita passar a exigir pagamento para a função usada, ela é substituída ou removida.
- Trial temporário não conta como solução gratuita permanente.
- Integrações opcionais pagas podem ser citadas apenas como referência, nunca como requisito.

## Stack adotado

### Camada A — ativa agora

- **GitHub**: fonte versionada, diff, branch/PR, auditoria e rollback.
- **ChatGPT**: autor/revisor assistido, respeitando política sem spoilers e contratos de `historia/`.
- **Geração de imagem disponível no ChatGPT**: concept art e portrait quando não houver custo incremental.
- **Blockbench**: edição e validação gratuita de skins e modelos Minecraft.

### Camada B — autoria gratuita opcional

- **AIStoryHub MCP**: candidato preferencial para PoC de story bible derivada e consistência de voz somente enquanto o modo gratuito atender ao fluxo sem cobrança.
- **Spindle MCP**: alternativa open-source/local-first para story bible, continuidade e pesquisa de cânone em clientes MCP compatíveis.
- **story-architect-mcp**: alternativa MIT baseada em arquivos Markdown/JSON para worldbuilding e auditoria de continuidade.

Não usar duas story bibles externas como bases paralelas. O GitHub continua sendo a única fonte aceita.

### Camada C — diálogos ramificados

- **Ink/Inky**: opção preferencial quando o Markdown deixar de ser suficiente. É gratuito e MIT.
- **Yarn Spinner**: alternativa gratuita para narrativa ramificada; avaliar licença vigente e integração antes de adoção.

Até existir necessidade concreta, diálogos permanecem no formato editorial versionado do repositório.

## Ferramentas não adotadas

- **AINPC Engine**: não recomendado para o fluxo padrão porque a solução depende de serviço/trial externo. Só reconsiderar se existir opção permanente sem custo.
- Qualquer gerador visual baseado exclusivamente em créditos pagos: não é dependência do projeto.

## Regra contra duplicação de cânone

Ferramentas externas recebem um recorte exportado do GitHub e devolvem propostas. Alterações aceitas retornam ao repositório via branch/PR. Não existe sincronização bidirecional automática inicialmente.

Fluxo:

`GitHub/main -> recorte de contexto -> ferramenta auxiliar -> proposta -> revisão -> branch/PR -> GitHub/main`

## Segurança

- Nunca versionar credenciais ou chaves de acesso.
- Nunca conceder escrita direta em `main` a ferramenta externa.
- Começar integrações com escopo mínimo.
- Toda integração nova precisa de teste com conteúdo não sensível antes de receber lore completa.
- MCPs de terceiros são fornecedores externos e precisam ser avaliados antes de receber conteúdo privado.

## Política de adoção

Uma ferramenta externa só vira parte recomendada do fluxo se cumprir todos os critérios:

1. custo incremental obrigatório igual a zero;
2. reduz trabalho manual de forma mensurável;
3. exporta ou permite recuperar o trabalho sem lock-in crítico;
4. preserva IDs estáveis do projeto;
5. não exige autoridade sobre o runtime;
6. não força duplicação manual permanente do cânone;
7. melhora consistência, revisão ou produção visual de forma verificável.

## Decisões atuais

- GitHub + ChatGPT: **BASE ATIVA**.
- Blockbench: **ADOTADO para validação visual/skin**.
- Geração visual no ChatGPT: **ADOTADA quando incluída sem custo incremental**.
- AIStoryHub: **CANDIDATO PARA PoC GRATUITO**.
- Spindle: **ALTERNATIVA OPEN-SOURCE**.
- story-architect-mcp: **ALTERNATIVA OPEN-SOURCE**.
- Ink/Inky: **CANDIDATO GRATUITO quando branching complexo justificar**.
- AINPC Engine: **FORA DO FLUXO RECOMENDADO**.
- IA in-game: continua **OPCIONAL / NÃO É AUTORIDADE**.
