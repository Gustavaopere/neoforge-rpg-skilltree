# Grimoire ↔ GitHub — Reconciliação Editorial

## Objetivo

Evitar duas versões independentes da campanha entre o Grimoire/TTRPG.bot e `historia/`.

Este fluxo não exige API paga nem sincronização automática. MCP/API pode acelerar o processo quando disponível, mas a campanha deve continuar administrável por Git + revisão explícita.

## Authorities por domínio

- Grimoire/TTRPG.bot: lore estruturada, Campaign Bible/Foundations, personagens, organizações, locais, relações, conhecimento, segredos e estado narrativo estruturado quando registrados ali.
- `historia/`: fonte editorial versionada da campanha no repositório, com IDs estáveis, documentos de autoria, diálogos, evidências, lifecycle e material revisável em PR.
- código/modlist/contracts: authority mecânica/runtime; lore nunca cria capability de provider.

Nenhuma dessas authorities deve ser convertida em cópia cega da outra.

## Regra principal

**Não usar sincronização bidirecional automática sem política de conflito explícita.**

O objetivo é reconciliação rastreável, não replicação indiscriminada.

## Antes de criar uma entidade narrativa

1. pesquisar `historia/` por nome, aliases e ID relacionado;
2. pesquisar Grimoire quando a conexão estiver disponível;
3. verificar se a entidade já existe sob outro nome/alias;
4. escolher ou preservar o ID estável do repositório (`NPC-####`, `FAC-####`, `SET-####`, `LOC-####`, etc.);
5. só criar uma nova entidade quando não houver duplicata ou quando a duplicação for decisão editorial deliberada.

Se Grimoire for necessário para resolver a identidade e estiver indisponível, manter o item como `RASCUNHO`/`BLOQUEADO PARA RECONCILIAÇÃO`, não como novo cânone.

## IDs cruzados

Quando a ferramenta externa permitir tags, aliases, custom fields ou notas técnicas, registrar o ID estável do repositório como referência cruzada.

Exemplo conceitual:

- display name: Severin;
- stable editorial id: `NPC-0001`.

Não depender apenas do nome de exibição para reconciliar entidades.

## Fluxo recomendado de mudança

### 1. Descoberta
Pesquisar GitHub + Grimoire + PRs/branches concorrentes.

### 2. Autoria
Criar/alterar o conteúdo em branch. O conteúdo continua proposta até revisão/merge e reconciliação das authorities relevantes.

### 3. Validação
Executar os validadores locais aplicáveis e revisar knowledge, causalidade, providers e spoilers.

### 4. PR
O PR deve informar IDs afetados e estado editorial sem precisar expor spoilers no resumo.

### 5. Reconciliação Grimoire
Após a decisão editorial correspondente, atualizar a entidade estruturada no Grimoire quando necessário, preservando o mesmo ID/referência cruzada.

### 6. Conflito posterior
Se Grimoire e `historia/` divergirem, identificar primeiro o domínio:

- fato estruturado de lore/campanha → revisar Campaign Bible/Foundations e histórico editorial;
- texto/versionamento/asset de autoria → revisar Git/PR;
- capability mecânica → runtime/modlist/provider vence;
- proposta não aprovada → não sobrescreve fato canônico apenas por ser mais recente.

Registrar retcon/substituição deliberadamente quando for mudança real de cânone.

## Conteúdo que NÃO deve ser copiado cegamente

- metadados genéricos de sistema da plataforma;
- scaffolding D&D/Fantasy que não pertença ao canon deste projeto;
- campos automáticos que não tenham decisão editorial;
- texto de concept/calibração marcado como não canônico;
- hipóteses técnicas sobre mods/providers;
- segredos inferidos por IA sem fonte narrativa.

## Diálogos e voiceprints

O Grimoire pode manter perfil/personality de um NPC, enquanto diálogos extensos podem permanecer versionados em `historia/12-dialogos/`.

A referência cruzada deve apontar para o mesmo `NPC-####`. Falas de calibração não se tornam eventos canônicos só porque foram copiadas para uma ferramenta externa.

## Assets visuais

O Grimoire pode exibir portrait/imagem de referência. O GitHub deve registrar o asset brief e, quando adequado, os assets finais versionados.

Portrait, concept e skin são artefatos distintos. Substituir uma imagem no Grimoire não altera automaticamente a skin técnica do jogo.

## Quando MCP/API estiver indisponível

- continuar autoria/validação no GitHub quando não houver risco de duplicar canon central;
- marcar reconciliação pendente;
- não afirmar que o Grimoire foi atualizado;
- não inventar dados que dependeriam da Campaign Bible;
- quando a conexão voltar, reconciliar por ID e provenance, não por merge textual cego.

## Custo

A sincronização automática é opcional. Nenhum serviço pago, API key paga ou assinatura externa é requisito para preservar consistência entre as duas authorities.
