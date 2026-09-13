# Grimoire ↔ GitHub — Reconciliação Editorial

## Objetivo

Evitar duas versões independentes da campanha entre o Grimoire/TTRPG.bot e `historia/`.

Este fluxo não exige API paga nem sincronização automática. MCP/API pode acelerar o processo quando disponível, mas a campanha deve continuar administrável por Git + revisão explícita.

## Authorities por domínio

- Grimoire/TTRPG.bot: **authority principal para lore estruturada**, Campaign Bible/Foundations, causalidade narrativa, fatos do mundo, personagens, organizações, locais, relações, conhecimento, segredos e estado narrativo estruturado quando registrados ali.
- `historia/` aceita em `main`: **fonte editorial versionada da campanha**, com IDs estáveis, documentos de autoria, diálogos, evidências, lifecycle, histórico de revisão e material aceito no repositório.
- branches/PRs: propostas editoriais até revisão/merge; novidade não substitui fatos aceitos apenas por ser mais recente.
- código/modlist/contracts: authority mecânica/runtime; lore nunca cria capability de provider.

Nenhuma dessas fontes deve ser convertida em cópia cega da outra. Grimoire governa o domínio semântico de lore estruturada; GitHub preserva a representação editorial versionada, revisão e proveniência de mudanças.

## Regra principal

**Não usar sincronização bidirecional automática sem política de conflito explícita.**

O objetivo é reconciliação rastreável, não replicação indiscriminada.

Quando `main` e Grimoire divergirem:

1. preservar os dois valores enquanto a discrepância não estiver resolvida;
2. identificar domínio, provenance, decisão editorial anterior e se algum lado representa proposta/estado desatualizado;
3. não escolher automaticamente o valor mais recente;
4. para lore estruturada, consultar Campaign Bible/Foundations e demais fontes de proveniência antes de decidir;
5. registrar em branch/PR toda correção necessária na representação versionada;
6. atualizar o Grimoire quando a decisão editorial legítima alterar a própria Campaign Bible;
7. nunca sincronizar cegamente em qualquer direção.

## Antes de criar uma entidade narrativa

1. pesquisar `historia/` em `main` por nome, aliases e ID relacionado;
2. pesquisar branches/PRs relevantes para evitar duplicação concorrente;
3. pesquisar Grimoire quando a conexão estiver disponível;
4. verificar se a entidade já existe sob outro nome/alias;
5. escolher ou preservar o ID estável do repositório (`NPC-####`, `FAC-####`, `SET-####`, `LOC-####`, etc.);
6. só criar uma nova entidade quando não houver duplicata ou quando a duplicação for decisão editorial deliberada.

Se Grimoire for necessário para resolver a identidade e estiver indisponível, manter o item como `RASCUNHO`/`BLOQUEADO PARA RECONCILIAÇÃO`, não como novo cânone.

## IDs cruzados

Quando a ferramenta externa permitir tags, aliases, custom fields ou notas técnicas, registrar o ID estável do repositório como referência cruzada.

Exemplo conceitual:

- display name: Severin;
- stable editorial id: `NPC-0001`.

Não depender apenas do nome de exibição para reconciliar entidades.

## Fluxo recomendado de mudança

### 1. Descoberta
Pesquisar `main` + branches/PRs relevantes + Grimoire.

### 2. Autoria
Criar/alterar o conteúdo em branch. O conteúdo continua proposta até revisão/merge e reconciliação das authorities relevantes.

### 3. Validação
Executar os validadores locais aplicáveis e revisar knowledge, causalidade, providers e spoilers.

### 4. PR
O PR deve informar IDs afetados e estado editorial sem precisar expor spoilers no resumo.

### 5. Reconciliação de lore
Quando a mudança tocar lore estruturada central, validar contra Grimoire/Campaign Bible. Se a conexão estiver indisponível e a decisão depender dela, permanecer fail-closed.

### 6. Aceitação versionada
Alterações editoriais entram em `main` somente após revisão. O merge registra a representação versionada da decisão; ele não transforma informação conflitante em verdade de lore sem a reconciliação necessária.

### 7. Sincronização estruturada
Quando a decisão legítima modificar a Campaign Bible, atualizar o Grimoire preservando ID/referência cruzada e provenance.

### 8. Conflito posterior
Se Grimoire e `historia/` divergirem, identificar primeiro o domínio:

- fato de lore/campanha divergente → Grimoire/Campaign Bible é a authority principal do domínio; revisar também `main` e histórico editorial para descobrir qual representação está desatualizada ou se há retcon pendente;
- texto/versionamento/asset de autoria → Git/PR governa a versão editorial do artefato;
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

- continuar autoria/validação no GitHub quando não houver risco de duplicar ou contradizer lore central;
- marcar reconciliação pendente;
- não afirmar que o Grimoire foi atualizado;
- não inventar dados que dependeriam da Campaign Bible;
- quando a conexão voltar, reconciliar por ID e provenance, não por merge textual cego.

## Custo

A sincronização automática é opcional. Nenhum serviço pago, API key paga ou assinatura externa é requisito para preservar consistência entre as duas fontes.
