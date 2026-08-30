# Critérios Obrigatórios para Aprovação de Perks — RPG Skill Tree

> **REGRA DE OURO:** uma perk só pode ser declarada **APROVADA/FECHADA** quando passar por todos os critérios aplicáveis deste protocolo. Efeito tecnicamente possível, texto preenchido ou correção isolada não bastam.

Esta é a cópia operacional versionada do protocolo canônico do Notion para o **RPG Skill Tree — NeoForge 1.21.1 / Java 21**.

Fonte canônica no Notion: https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567

## 1. Fontes de verdade obrigatórias

Toda auditoria deve cruzar, no mínimo:

- Documento Mestre de Design e Implementação;
- Catálogo Mestre — Atributos e Passivos no Notion;
- Guia Completo — Gameplay e Sistemas;
- Guia Completo — Mods de Magia;
- Guia Completo — Mods de Tecnologia;
- **Guia Completo — Projetos Próprios do Modpack**, incluindo os quatro dossiês, matriz cruzada e `12-capability-delta-coverage.md`;
- modlist atual quando posterior ao snapshot dos guias;
- API, código, documentação ou comportamento verificado da versão exata do provider em NeoForge 1.21.1;
- repositório/implementação real quando a perk já possui código.

Se houver conflito, prevalece a evidência técnica mais específica e atual. README genérico, nome de método, similaridade temática ou plano futuro não substituem runtime/API/código real.

## 2. Os 9 eixos obrigatórios de aprovação

Cada perk deve ser examinada explicitamente contra os nove eixos abaixo.

### 2.1 Dependências, bloqueios e gates

Verificar:

- `Dependências Obrigatórias` e rank mínimo;
- Mastery/pontos/gates externos aplicáveis;
- ausência de atalhos e ciclos;
- coerência semântica da dependência;
- capacidade real do runtime de impedir aquisição/ativação quando o requisito falhar.

### 2.2 Integração global de corpo, sobrevivência, magia, tecnologia e sistemas compartilhados

A perk não pode ser analisada como se seu provider existisse isoladamente. Considerar, quando pertinente:

- temperatura corporal, frio/calor e ambiente;
- sede/hidratação e nutrição;
- vida, sangue, cura, ferimentos e recursos corporais;
- mana, Soul Energy, spirits e demais recursos mágicos reais;
- stamina/Epic Fight;
- radiação, contaminação, atmosfera, O₂, gases e pressão;
- energia elétrica/cinética, combustível e fluidos;
- summons/familiars/servants e ownership;
- agricultura, animais, genética e sobrevivência;
- progressões nativas autoritativas como Vampirism/Bloodlines/Werewolves e equivalentes.

**Afinidade não é resistência.** Não fundir conceitos apenas por compartilharem fogo, frio, magia, corrupção etc.

Sempre usar o pipeline e recurso canônico existente. Não criar segunda temperatura, sangue, mana, cura, atmosfera, pressão ou outro estado paralelo sem decisão explícita.

### 2.3 Qualidade e identidade

Reprovar/redesenhar perks que sejam apenas percentual genérico sem decisão de build, cópia de atributo já existente, duplicação artificial de nodes ou Notable/Keystone/Capstone incompatível com sua importância.

Preferir decisões, tradeoffs, telemetria útil, planejamento, interação entre mecânicas reais, especialização operacional e alteração de regra por hook estável.

### 2.4 Ramificação, distância e topologia

Conferir domínio, ramo, camada, vizinhança, travessia entre regiões, corredores/Bridge Nodes, posição de Gateway/Notable/Keystone/Capstone e custo total do caminho.

Travessias longas devem usar corredores compráveis quando apropriado; não aplicar teleportes ou taxas invisíveis para contornar a topologia.

### 2.5 Especializações

Verificar se a perk pertence à especialização correta, se deveria ser ramo universal/bridge/gateway, se invade outra especialização e se progressões I→II→III realmente crescem em identidade.

**Mods não viram classes automaticamente.** O provider fornece mecânicas; a árvore decide domínios/especializações.

### 2.6 Tradução PT-BR

Todo texto destinado ao jogador deve estar em português do Brasil, preservando apenas nomes próprios/termos técnicos que precisem permanecer oficiais. Classes Java, métodos, IDs, tags, registries e hooks podem permanecer em inglês.

### 2.7 Preenchimento completo do Notion

Conferir, conforme o schema aplicável:

`Código`, `Nome`, `Domínio`, `Árvore`, `Ramo`, `Camada`, `Função na Árvore`, `Tier`, `Faixa de Poder`, `Ranks Máx.`, `Custo por Rank`, `Custo Extra`, `Dependências Obrigatórias`, `Pré-requisitos`, `Provider/Mods`, `Efeito`, `Escalonamento`, `Gate`, `Hook`, `Fallback`, `Regra` e demais campos pertinentes.

O registro deve ser específico o suficiente para o Chat 2 implementar sem redesenhar.

### 2.8 Remoção total do NeoVitae

NeoVitae é legado. Nenhuma perk aprovada pode exigir NeoVitae, listá-lo como provider ativo ou fingir que recursos exclusivos dele ainda existem. Fantasias reaproveitáveis só podem ser redirecionadas a providers reais quando semanticamente correto.

### 2.9 Cobertura completa da modlist e integração entre mods

Os guias são também matriz de cobertura. Em cada lote:

- revisar providers pertinentes nos guias e modlist;
- perguntar que capacidades deveriam alimentar/ser representadas pela árvore;
- verificar bridges legítimas;
- não ignorar mods menores/periféricos;
- registrar quando um mod não deve ter perk própria;
- procurar lacunas antes de fechar o lote.

A auditoria mantém a pergunta paralela: **“há alguma mecânica do modpack ainda não representada ou integrada?”**

Classificações permitidas de cobertura:

- **COBERTA POR PERK EXISTENTE**;
- **PERK PRÓPRIA**;
- **ESPECIALIZAÇÃO**;
- **BRIDGE**;
- **COBERTO POR SISTEMA UNIVERSAL**;
- **PROGRESSÃO NATIVA AUTORITATIVA**;
- **SEM HOOK SEGURO**;
- **NÃO DEVE SER INTEGRADO**.

### 2.9.1 Delta obrigatório de capacidades dos projetos próprios

`neoforge-rpg-skilltree`, `Volcanoes`, `Enshrouded` e `Black-Arcana` estão em desenvolvimento contínuo. A cobertura não pode partir somente das perks existentes.

Antes de fechar **cada lote exato de 10**, o Chat 1 deve:

1. fazer fetch fresco de `main` e `plans/STATUS.md` dos quatro projetos;
2. comparar os SHAs atuais com os últimos baselines reconciliados;
3. quando houver avanço, identificar os subsistemas alterados;
4. extrair toda capacidade nova ou semanticamente alterada pertinente a progressão — recurso, resistência, estado, hazard, ação, equipamento, query, serviço, progressão, diagnóstico, milestone ou boundary público — mesmo que nenhuma perk atual a mencione;
5. registrar cada capacidade na matriz `Projeto → Capacidade → Estado real → SHA/evidência → Cobertura atual → Decisão → Perk(s)/ação → Hook/boundary → Fail-closed`;
6. atribuir uma das classificações de cobertura acima;
7. registrar lacunas parciais/ausentes antes de fechar o lote.

**O baseline de um projeto não pode avançar enquanto qualquer capacidade detectada naquele delta estiver sem disposição explícita.** O novo SHA só vira checkpoint depois que todas as linhas tiverem decisão, ação e comportamento fail-closed quando aplicável.

A auditoria é obrigatoriamente bidirecional:

- `perk → provider`: authority, hook/boundary, causalidade, deduplicação, fallback e fail-closed;
- `provider → árvore`: nenhuma capacidade nova/alterada pertinente fica sem avaliação.

Detectar uma capacidade não autoriza inventar mecânica. Exemplo: O₂ no Volcanoes exige avaliar cobertura de respiração/hipóxia/proteção, mas não autoriza inventar `+X% oxigênio` sem extension point real. Arcane Resistance do Black Arcana continua distinta de resistência mágica genérica, Shroud, temperatura, pressão e gases.

A descoberta de lacuna **não transforma lote de 10 em 11**. Se a solução exigir node fora do lote ativo, registrar a necessidade/posicionamento e deixá-la para o ciclo correto posterior. Não iniciar automaticamente o próximo lote.

### 2.9.2 Delta externo de modlist

Mods adicionados depois do snapshot dos guias devem ser incorporados incrementalmente antes do próximo fechamento de lote.

**Mobstein 5.4.4** (`mobstein-5.4.4-neoforge-1.21.1.jar`) foi adicionado em 2026-08-30:

- Gameplay/Magia: provider próprio de ressurreição corporal, experimentos, allies/bodyguards ressuscitados, estruturas e boss;
- as `Attack/Health/Speed/Template perks` internas pertencem ao Mobstein e **não** são nodes do RPG Skill Tree;
- necromancia temática não cria bridge automática com Goety, Black Arcana, Malum, Eidolon ou Enshrouded;
- Tecnologia: **NÃO APLICÁVEL** por padrão; Clinical/Surgery Stretch e Subject Assembly Machine não provam FE, SU, Create, AE2, Oritech ou outro contrato tecnológico.

## 3. Critérios técnicos obrigatórios

### 3.1 Provider-native first

Preservar a mecânica e a progressão nativas do provider. Não clonar combustível, energia, durabilidade, prioridade, progressão, cooldown, resource cost, unlock, ownership ou estado que o provider já controla adequadamente.

### 3.2 Não inventar mecânicas do provider

É proibido afirmar que uma perk controla algo inexistente na versão auditada: perda de transmissão inexistente, filtros inexistentes, prioridade inexistente, pressão não aplicada, durabilidade em item unbreakable etc.

### 3.3 Hook implementável e versão exata

`Hook` deve identificar a fonte real do evento/estado. Branch posterior ou documentação de outra versão não prova disponibilidade em NeoForge 1.21.1.

### 3.4 Fail-closed

Sem API/hook seguro, a funcionalidade dependente fica inativa. Não substituir silenciosamente por `+dano`, `+mastery`, `+durabilidade` ou bônus genérico.

### 3.5 Fallback preserva identidade

Fallback é degradação técnica segura, não uma segunda perk. Uma perk de telemetria/planejamento não pode virar bônus de produção porque um adapter faltou.

### 3.6 Uma ação, um pipeline canônico

Crítico, cura, stamina, dano, Mastery, summons, produção e recursos não podem ser processados duas vezes por bridges diferentes.

### 3.7 Sem geração/duplicação acidental

Não gerar gratuitamente energia, combustível, fluidos, minério, ingredientes, outputs, recursos mágicos, vida/sangue, drops ou pontos/Mastery. Geração intencional precisa de design, custo, gate e hook auditados.

### 3.8 Read-only realmente read-only

Diagnóstico/telemetria/previsão/planejamento podem ler/calcular/simular, mas não consumir recursos, executar receita, alterar máquina/rede/bloco ou produzir resultado durante a consulta.

## 4. Mastery e anti-abuso

Mastery representa experiência real e atribuível.

É proibido gerar Mastery por tick, AFK, equipamento vestido, distância contínua, RF/t, FE/t, SU/t, throughput, dano repetitivo sem milestone, botão contínuo, processo autônomo sem autoria ou rebuild/configuração repetitiva.

Preferir milestones discretos: first-use legítimo, nova família de ferramenta/máquina, nova receita/configuração real, primeiro comissionamento de arquitetura distinta, advancement e descoberta inédita com ledger limitado.

Automação, fake player, redstone, turret, minion ou máquina abandonada só podem conceder progressão quando houver atribuição causal inequívoca ao jogador.

## 5. Coerência de poder e função na árvore

- **Small / Ranked Passive:** bônus simples pequenos/canônicos; consolidar cadeias redundantes quando possível.
- **Notable:** muda decisão/interação/fantasia; não pode ser percentual banal.
- **Gateway:** desbloqueia sistema/especialização/camada; normalmente não carrega grande pacote de poder.
- **Keystone:** altera regra relevante; pode envolver tradeoff; exige hook seguro.
- **Capstone:** conclusão significativa do ramo/especialização.
- **Bridge:** conecta regiões/fantasias com custo/topologia intencionais; não é teleporte gratuito.

## 6. Checklist individual de aprovação

Uma perk recebe **APROVADA** somente se todos os itens forem `SIM` ou `N/A justificado`:

- [ ] código/identidade corretos;
- [ ] dependências/gates corretos;
- [ ] domínio/ramo/camada/posição corretos;
- [ ] topologia/distância coerentes;
- [ ] função estrutural correta;
- [ ] poder/ranks/custos coerentes;
- [ ] identidade própria e ausência de duplicação;
- [ ] especialização correta;
- [ ] integrações globais pertinentes consideradas;
- [ ] providers pertinentes dos guias/modlist consultados;
- [ ] comportamento existe na versão instalada;
- [ ] hook específico e implementável;
- [ ] provider-native first preservado;
- [ ] nenhuma mecânica/recurso/estado inventado;
- [ ] fallback seguro;
- [ ] nenhum pipeline duplicado;
- [ ] nenhum exploit de energia/recursos/outputs/cura/sangue/mana/Mastery;
- [ ] Mastery usa milestone discreto e atribuição causal;
- [ ] anti-farm/rebuild quando necessário;
- [ ] nenhuma dependência residual de NeoVitae;
- [ ] PT-BR consistente;
- [ ] campos pertinentes do Notion completos;
- [ ] delta de `main`/`plans/STATUS.md` dos quatro projetos próprios verificado;
- [ ] toda capacidade nova/alterada recebeu disposição de cobertura;
- [ ] nenhum baseline foi avançado com linha de delta pendente;
- [ ] pós-escrita re-fetch do Notion confirmou persistência.

## 7. Status permitidos

- **PENDENTE** — ainda não passou pela auditoria completa.
- **EM REVISÃO** — análise em andamento.
- **BLOQUEADA** — depende de informação/API/provider não confirmado.
- **REPROVADA / REDESENHAR** — falhou em critérios e exige mudança funcional/estrutural.
- **APROVADA** — passou individualmente pelos critérios aplicáveis e foi re-fetched no Notion.
- **LOTE FECHADO** — as 10 perks estão aprovadas **e** matrizes de cobertura/delta estão fechadas.

## 8. Protocolo obrigatório de auditoria por lotes

**Chat 1 e Chat 2 trabalham em LOTES EXATOS DE 10 perks consecutivas.**

Não existe intervalo fixo permanente: cada chat determina o próximo lote a partir do estado real de `STATUS.md`, auditorias e dossiês.

Ao iniciar, registrar `INÍCIO`, `FIM`, estado inicial e fontes/providers cruzados.

Matriz mínima do lote:

| Critério | Status |
|---|---|
| Dependências/bloqueios | ⬜ |
| Integração global | ⬜ |
| Qualidade/identidade | ⬜ |
| Topologia | ⬜ |
| Especializações | ⬜ |
| PT-BR | ⬜ |
| Notion completo | ⬜ |
| NeoVitae removido | ⬜ |
| Cobertura modlist/providers | ⬜ |
| Delta provider → árvore | ⬜ |

Antes de anunciar `Axxxx–Ayyyy — FECHADO`:

1. concluir correções das 10 perks;
2. re-fetch das páginas alteradas e confirmar persistência;
3. confirmar os nove eixos e critérios técnicos;
4. confirmar a matriz provider → árvore;
5. confirmar que nenhum delta ficou sem classificação antes de atualizar baselines;
6. registrar exceções/N/A;
7. concluir documentação/status pertinente;
8. quando houver trabalho GitHub neste ciclo, concluir PR, CI verde quando aplicável, merge na `main` e confirmar a `main` pós-merge;
9. **PARAR**. Não iniciar automaticamente o próximo lote.

Se a execução parar no meio, informar o último código realmente aprovado e o próximo código exato.

## 9. Política de novos chats/agentes

Antes de qualquer lote, ler este arquivo e as fontes obrigatórias atuais. Não aceitar um intervalo antigo como definitivamente fechado apenas porque outro chat escreveu `FECHADO` se ele não passou por esta versão do protocolo.

Ao retomar trabalho: preservar decisões tecnicamente confirmadas, reabrir somente diante de nova evidência, documentar a razão e continuar do ponto real verificado.

## 10. Checklist técnica consolidada — 18 critérios

1. efeito existe de verdade na versão instalada;
2. provider-native first;
3. nenhuma mecânica inventada;
4. fail-closed sem hook seguro;
5. fallback preserva identidade;
6. Mastery somente por feitos discretos/atribuíveis;
7. anti-farm/anti-rebuild;
8. atribuição causal ao jogador;
9. não duplicar pipelines canônicos;
10. custos/recursos reais;
11. sem geração gratuita/duplicação acidental;
12. read-only realmente read-only;
13. versionamento explícito quando API for sensível;
14. coerência estrutural da árvore;
15. dependências com semântica correta;
16. sem sobreposição indevida entre ramos/especializações;
17. perk especificada para implementação posterior sem redesenho;
18. verificação pós-escrita obrigatória.

Os 18 critérios são cumulativos aos 9 eixos; não substituem integração global, topologia, especializações, PT-BR, remoção de NeoVitae, cobertura da modlist ou Notion completo.

---

## Nota de sincronização

Sincronizado com o conteúdo canônico do Notion em **2026-08-30**, incluindo:

- lotes exatos de 10;
- Guia Completo — Projetos Próprios como fonte obrigatória;
- auditoria bidirecional `perk → provider` e `provider → árvore`;
- gate de delta de capacidades;
- proibição de avançar baseline com capacidade não classificada;
- Mobstein 5.4.4 como primeiro delta externo pós-snapshot.

**O Notion continua sendo a fonte canônica.** Se a página mudar, atualizar esta cópia antes do próximo lote.
