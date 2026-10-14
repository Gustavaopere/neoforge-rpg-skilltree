# Critérios Obrigatórios para Aprovação de Perks — RPG Skill Tree

> **Regra de ouro:** escrever o dossiê, reservar uma vaga ou ter uma ideia implementável **não** basta para declarar uma perk aprovada. Toda perk deve passar pelos nove eixos sistêmicos e pelos dezoito critérios técnicos aplicáveis, com evidência da versão realmente instalada dos mods envolvidos.

**Projeto:** NeoForge 1.21.1 / Java 21.  
**Escopo:** novo catálogo `I/P/S/T` do Stage 03, incluindo especializações PURE/HYBRID e perfis de Transmutation por Ability.  
**Plano de trabalho:** [`plans/03-skill-tree-perks/MASTER-EXECUTION-PLAN.md`](../../../plans/03-skill-tree-perks/MASTER-EXECUTION-PLAN.md) e [`CODEX-FOUR-STAGES.md`](../../../plans/03-skill-tree-perks/CODEX-FOUR-STAGES.md).

Este arquivo é um **gate de qualidade e evidência**. Não impõe o antigo fluxo de três chats, lotes exatos de dez, IDs `A####` ou atualização obrigatória de registros no Notion. Esses procedimentos foram substituídos pelo plano de trabalho do Stage 03. O Notion pode continuar como referência histórica/complementar, mas **não substitui os dossiês e a evidência versionada do repositório**.

## 1. Fontes de verdade e versionamento

Consultar na seguinte ordem de relevância, considerando a função de cada fonte:

1. **Presença e versão física do pack:** `PROJECT-INSTRUCTIONS/modlist`, seu índice e dossiês individuais, sempre conferidos com a lista física mais recente quando disponível.
2. **Comportamento realmente carregado:** `PROJECT-INSTRUCTIONS/datapacks`, configs relevantes, addons e bridges presentes.
3. **Código/API/JAR da versão instalada:** source pinado na mesma release, registry, classe, método, evento, contrato de persistência e comportamento testado. Branch `main` ou documentação de outra versão não prova disponibilidade.
4. **Inventário funcional de magias e poderes:** `Gustavaopere/Black-Arcana/wiki/modpack-catalog/providers`. Uma ficha catalogada não prova, isoladamente, que seu hook esteja pronto ou que o mod esteja presente no pack atual.
5. **Minecraft vanilla e projetos próprios:** RPG Skill Tree, Black Arcana, Volcanoes, Enshrouded e outros sistemas efetivamente presentes.
6. **Apresentação visual:** `PROJECT-INSTRUCTIONS/resource-packs` e `PROJECT-INSTRUCTIONS/shaders` para modelos/VFX, não para afirmar gameplay.
7. **Design externo:** Diablo IV e outros jogos são inspiração, nunca autoridade sobre mecânica, números, APIs ou balanceamento do pack.

É proibido congelar na regra permanente números de JARs oriundos de snapshots históricos. Toda auditoria deve registrar a **data e o baseline (arquivo/head/hash) efetivamente conferidos**. Havendo divergência entre fonte antiga e versão física instalada, reabrir a análise e marcar `VERSION-UNVERIFIED` até reconciliar.

### Ficha de evidência obrigatória para cada integração

- Nome do mod, `mod id`, **JAR e versão exatos** presentes no pack.
- Caminho do dossiê da modlist + revisão/data de conferência.
- Identidade técnica do objeto/ação (registry/resource ID e owner).
- Comportamento original (por exemplo: vida da barreira, cooldown, custo, alcance, duração, seleção de alvo), com evidência.
- Delta pretendido pela perk e ponto de integração: hook, API, evento, capability, entity ou serviço.
- Autoridade de servidor/cliente, persistência, duplicação/stack, addons e política de ausência/falha.
- Source/release/commit ou classe/método e resultado de teste, quando houver.

Estados de evidência:
- `SOURCE-VERIFIED`: efeito e boundary conferidos na versão instalada.
- `DOC-VERIFIED`: comportamento documentado para a release, hook específico ainda não comprovado.
- `VERSION-UNVERIFIED`: versão instalada ou origem da informação sem confirmação.
- `TECH-BLOCKED`: ausência/insegurança do boundary exigido.
- `NOT-APPLICABLE`: efeito universal sem dependência de provider, com justificativa.

Uma perk descrita por documentação sem hook seguro não é `TECH-AUDITED`.

## 2. Os nove eixos sistêmicos de aprovação

### 2.1 Dependências, bloqueios e gates

Conferir classe/árvore/owner, nível mínimo, mastery, pontos de domínio, vizinhança, ranks de outras perks, especialização e requisitos externos. O requisito precisa ter **sentido mecânico**, não ser somente um ID existente. Proibir circularidade, bypass de progressão e compra sem gate efetivo.

**Atributos:** `STRENGTH`, `CONSTITUTION`, `AGILITY`, `INTELLIGENCE`, `DETERMINATION`, `CHARISMA`. Um mínimo, como `INTELLIGENCE >= 20`, deve ser lido do Core e **não consome** vinte pontos ao comprar a perk. Custo em pontos de perk é outro ledger. O `NodeAccessRequirement` legado ainda não contém mínimos de atributos; exigir implementação e testes antes de declarar o requisito ativo.

**Respec:** registrar como perks já adquiridas se comportam quando um atributo/requisito deixar de ser atendido; não presumir uma regra que ainda não foi decidida.

### 2.2 Integração global e recursos canônicos

Cruzar os sistemas reais quando pertinentes: temperatura corporal e estação/clima (não equivalentes automaticamente), sede, fome/nutrição, vida/sangue, cura, sobrecura, ferimentos, stamina, mana, spirits/Soul Energy, recursos ocultos, peso do jogador **apenas se existir provider de encumbrance**, energia elétrica/cinética, combustíveis/fluidos, summons e ownership, agricultura/genética, magia e progressões nativas (Vampirism/Bloodlines/afins).

Não criar uma segunda mana, temperatura, sangue, cura, stamina ou economia sem decisão arquitetural explícita. Resistência ambiental e afinidade elemental são conceitos distintos.

Para qualquer efeito cross-mod, documentar quem é **authority** do recurso, evento, caster, dano e cooldown e qual bridge só observa ou transforma informações.

### 2.3 Qualidade, identidade e escolha

Toda perk precisa justificar o espaço ocupado. Evitar linhas artificiais de `+5%`, duplicar um atributo ou converter três nós redundantes em perks distintas quando caberia um Ranked Passive.

São válidos pequenos bônus em Small/Ranked quando úteis para progressão. Notable deve alterar decisão; Keystone muda regra; Capstone conclui a fantasia; Gateway desbloqueia; Bridge justifica passagem. Preferir tradeoffs, novas interações reais e efeitos que afetam a estratégia, sem copiar literalmente inspirações externas.

### 2.4 Função, tier e topologia

Durante a autoria: documentar **owner, função estrutural, tier, faixa de poder, dependências, custo de ranks e custo estimado do caminho**. Não aceitar uma perk muito poderosa na camada inicial ou gateways que desviem de progressões necessárias.

**A posição geométrica, distância real, vizinhança visual e validação de corredores de Bridge Nodes são gates finais**, feitos após fechar catálogo/runtime. A antiga referência de travessias longas custarem aproximadamente 8–12 Passive Points é inspiração de balanceamento, **não valor obrigatório** nem taxa invisível.

A aprovação conceitual pode anteceder a topologia final; a aprovação de **release** não.

### 2.5 Especializações e limites P × S × T

- **P**: perk normal sobre atributos/capacidades/sistemas e categorias gerais, sem virar perfil específico de um único feitiço.
- **S**: especialização com **Mecânica Assinatura** e perks generalistas, acionadas por categorias semânticas verificadas, não por um spell único. Exemplos de signature mechanics: espíritos/afinidades, summon roles/sacrifício, caça/selamento, posturas e doutrinas. São inspiração, não exigência literal de Diablo IV.
- **T**: vaga de árvore e perfil específico por Ability, a ser auditado individualmente. Não clonar uma Fireball dentro de S.

Especializações `PURE` ou `HYBRID` precisam ter owner único para contagem; requisitos de duas árvores não somam duas quotas. Mods não viram classes/especializações automaticamente. Proibir invasão de responsabilidade e formas equivalentes sem diferença de build.

**Tags:** `fire`, `projectile`, `single_target` etc. são **facts normalizadas propostas pelo RPG Skill Tree**, não tags nativas presumidas de todos os mods. Cada provider/versão exige mapping auditável. Uma propagação de projétil de fogo só deve acontecer quando facts, hook, origem causal e alvos elegíveis estiverem confirmados. Procs não geram recursão.

### 2.6 PT-BR para o jogador

Nome, descrição, tooltip, custo, mensagem de bloqueio, requisito, modo/metamorfose e interface devem estar em português do Brasil; nomes oficiais e identificadores técnicos podem permanecer na grafia original. Evitar mudança de terminologia entre dossiês, runtime e wiki.

### 2.7 Dossiê versionado completo

O dossiê `.md` do repositório é o registro editorial obrigatório (ver `plans/03-skill-tree-perks/DOSSIER-CONTRACT.md`). Cada perk P/S ou perfil de Ability deve ter identidade, owner, função, tier, requisitos, atributos mínimos, ranks, **custo por rank em pontos de perk**, recursos extras reais, efeito por rank, escala/caps, providers/versões/JARs, evidência de hook, sinergias, fallback, riscos e status.

**Reserva `Txxxx-reservado` é exceção estrutural deliberada**: seus campos podem permanecer vazios e o status é `RESERVED-UNBOUND`; não conta como perfil desenvolvido nem perk funcional. Um perfil concluído vive separado da vaga e representa uma única Ability.

Não exigir Notion como condição de aprovação. Se algum fluxo quiser sincronização adicional, registrar o vínculo e verificar persistência, mas a fonte do Stage 03 permanece a documentação GitHub.

### 2.8 Remoção do NeoVitae e de providers legados

NeoVitae é legado removido; não pode aparecer como provider ativo, hook, resource ou requisito. Mecânicas antigas só sobrevivem quando existe um provider real equivalente e diferença semântica comprovada.

Aplicar a mesma regra a **qualquer mod removido**, incluindo exemplos históricos citados em planos antigos. Nenhuma documentação velha ressuscita AE2, Oritech ou outro provider não confirmado na modlist física vigente.

### 2.9 Cobertura da modlist e delta dos projetos próprios

Para cada provider de gameplay pertinente, decidir explicitamente:
- **PERK PRÓPRIA**;
- **ESPECIALIZAÇÃO**;
- **BRIDGE**;
- **COBERTO POR SISTEMA UNIVERSAL**;
- **PROGRESSÃO NATIVA AUTORITATIVA**;
- **SEM HOOK SEGURO**;
- **NÃO DEVE SER INTEGRADO**.

Não esquecer providers menores apenas porque Iron's, Ars, Epic Fight, Create ou Goety têm muitas funcionalidades. A cobertura é por **capacidade**, não uma perk por mod.

Nos projetos próprios (RPG Skill Tree, Volcanoes, Black Arcana, Enshrouded), comparar o head/versão realmente disponível com o último baseline reconciliado. Identificar ações, recursos, states, hazards, serviços, equipamentos, resistências e boundaries novos/alterados, mesmo que nenhuma perk anterior os cite. O baseline **não avança enquanto cada delta não receber disposição justificada**; não impor lotes de dez nem exigir um chat particular.

## 3. Dezoito critérios técnicos cumulativos

Os **18 critérios abaixo** preservam os invariantes técnicos do protocolo anterior, mas com o sistema `I/P/S/T` e a versão instalada como autoridade.

1. **Efeito real:** todo efeito baseado em provider precisa de fato/estado/cálculo verificável; “melhora eficiência” sem operação real não basta.
2. **Provider-native first:** usar resources, casts, cooldowns, produção, entities e progressão nativos como authority.
3. **Não inventar mecânica:** sem supor prioridade de rede, perdas, filtro, durabilidade, tempo de efeito, ricochete ou movimento não provados na release.
4. **Fail-closed:** ausência de API/hook/metadata impede o efeito específico; não substituir automaticamente por bônus genérico.
5. **Fallback preserva identidade:** degradação segura pode retirar uma telemetria, não transformar a perk de planejamento em produção extra.
6. **Mastery discreta:** nunca por tick, AFK, throughput/energia por tempo, distância contínua, dano repetido ou equipamento vestido.
7. **Anti-farm/rebuild:** não conceder mastery indefinida por desmontar/recolocar/alternar o mesmo objeto/configuração.
8. **Autoria causal:** distinguir jogador, automação, fake player, máquina, turret, summon; conceder crédito somente se for atribuível.
9. **Pipeline único:** não duplicar crítico, dano, cura, stamina, spells, summoning, outputs, cooldown ou mastery; evitar proc recursivo e dupla liquidação.
10. **Custos reais:** identificar pontos de perk, custos de rank de atributo e recursos reais (mana, stamina, sangue, FE etc.) sem ledger paralelo.
11. **Sem geração gratuita acidental:** não duplicar item, output, fluidos, energia, recursos, drops, vida, mana, blood ou mastery; geração intencional exige custo/gate/hook explícitos.
12. **Read-only genuíno:** simulação/telemetria não pode consumir item, executar receita, alterar máquina/world ou produzir output.
13. **Versionamento EXATO:** registrar para **toda integração de mod** o JAR/versão instalada e evidência da mesma release; source mais novo/antigo só serve para hipótese, não aprovação.
14. **Coerência estrutural:** power band, função Small/Ranked/Notable/Gateway/Keystone/Capstone/Bridge, tier, custos, ranks e dependências compatíveis; topologia final auditada depois.
15. **Dependência semântica:** requisitos representam progressão real; gates de atributos, classe, mastery e node não podem ser burlados.
16. **Sem sobreposição indevida:** P, S e T têm papéis distintos; integração em duas árvores não duplica crédito ou efeitos; híbrida tem um owner.
17. **Implementabilidade documentada:** fato, delta, hook, scope, caps, requisitos, mapping de tags, fallback, save/respec e teste claros para um implementador atuar sem adivinhação.
18. **Persistência verificada:** após gravar arquivos e implementar mudanças, reler o conteúdo commitado; para release, executar testes e comprovar funcionamento, save/reload e sincronização. Não exigir fetch do Notion se o Notion não é o alvo de escrita.

## 4. Critérios específicos por família

### 4.1 Ponto 0 — atributos I

Seis atributos canônicos: `STRENGTH`, `CONSTITUTION`, `AGILITY`, `INTELLIGENCE`, `DETERMINATION`, `CHARISMA`. Não criar stats duplicados. Registrar custo de rank pela policy canônica e diferença entre rank de atributo, requisito mínimo de perk e gasto efetivo de Passive Points. Fórmulas não verificadas ficam bloqueadas como desenho técnico, não apresentadas como runtime pronto.

### 4.2 Trabalho 1 — Standard Perks P

Cada `Pxxxx` precisa de nome, owner, tier, efeito completo por rank, custo, requisitos de atributos, sinergias, providers/mod ID/JAR/versão, fato nativo, boundary e status. Não gerar só título, perk sem conteúdo ou filler para preencher quota. Perks podem ser escritas enquanto o inventário externo ainda avança, mas não aprovadas tecnicamente quando dependem de versão/hook ainda sem prova.

### 4.3 Trabalho 2 — Specialization Perks S

Cada especialização PURE/HYBRID define sua fantasia, owner, required trees e **Mecânica Assinatura**, que é distinta de uma mera coleção de passivos. Cada `Sxxxx` modifica **uma família de ações/abilities**, baseada em facts demonstradas pelos adapters da release instalada.

Exemplo conceitual: projétil de fogo direto de alvo único espalha parte do impacto. Para aprovação: confirmar facts `fire`, `projectile`, `single_target`, `direct_hit`; definir custo, raio, caps, PvP, friendly-fire, caster, evento e defesa contra reentrância. Se uma fonte não fornece facts confiáveis, a perk não deve disparar nela.

### 4.4 Trabalho 3 — Transmutation Slots e Ability Profiles

`Txxxx` é **vaga de progressão na árvore**, não ID fixo de uma spell. A vaga inicialmente contém filhos estruturais `.11/.12/.21/.22/.31/.32/.33` com campos vazios e status `RESERVED-UNBOUND`. Todas as 11 árvores terão quota igual de **vagas**.

Cada Ability tecnicamente elegível recebe **um perfil único por registry ID/provider** (inclusive não mágicas). Um perfil aprovado contém ao menos 2 LEFT, 2 RIGHT e 3 METAMORPHOSIS, até 9 por grupo, sempre **uma opção ativa de cada grupo**. Perfis podem ser usados em vagas compatíveis sem serem copiados para cada número `Txxxx`.

A Ability precisa estar realmente conhecida/desbloqueada pela autoridade pertinente; possuir temporariamente item equipado não é prova universal. Avaliar tier, raridade, rank e requisitos de forma **normalizada**, pois max level 1 pode ser poder de fim de jogo.

**Cobertura:** avaliar todas as Abilities pertinentes, mas classificar `ELIGIBLE`, `DESIGN-CANDIDATE`, `BLOCKED` ou `UNSUPPORTED` com motivo. “Cobrir todas” não é prometer alterar mecanismos sem hooks. Escudo Iron's que parece poder mover/refletir projéteis não passa antes de source audit de entidade, duração, colisão e authority.

A conversão de affinity/school precisa alterar semântica real de dano, scaling, statuses, resistência, tags, VFX e sinergias onde tecnicamente suportado; mudar apenas cor não é metamorfose funcional. Aplicações de S depois da transmutação devem observar a classificação **efetiva** do efeito, sem duplicar escola original e nova.

**Decisão de produto ainda aberta:** política de ocupação/reassociação das vagas, se uma Ability pode ocupar múltiplas vagas do mesmo jogador e os efeitos de respec. Não inventar esses detalhes durante o dossiê.

## 5. Custos, quotas e poderes

- Mesma quantidade **final** de P por árvore; mesma quantidade total de S por árvore owner; mesma quantidade de **vagas T** por árvore. Um perfil por Ability não conta como vaga, e o total de perfis não precisa ser igual entre árvores/providers.
- O número final de perks, slots e especializações deve ser derivado do conteúdo útil das 11 árvores, **sem filler**. Propostas editoriais provisórias devem ser rotuladas.
- Preço em Passive Points por rank, `maxRank`, level, mastery, thresholds de atributos e custo do caminho devem ser ponderados contra power band, impacto, frequência de uso e sinergias.
- **Threshold de atributo não é débito de atributo.** Gasto dos ranks de atributo já ocorreu na Core Point policy. Custos alternativos consumíveis exigem ledger/policy próprios.
- Requisitos híbridos não devem obrigar gasto impossível: avaliar orçamento real de pontos, respec e viabilidade de progressão.

## 6. Checklist de aprovação do dossiê

Cada resposta deve ser SIM ou N/A **justificado e documentado**:

- [ ] A fonte física da modlist e a versão exata foram conferidas?
- [ ] Mod ID, JAR, registry, comportamento nativo, source/API e boundary correspondem à versão instalada?
- [ ] O owner, função estrutural, tier, ranks, faixa de poder e orçamento estão coerentes?
- [ ] O efeito é específico e altera a build sem duplicação artificial?
- [ ] Custo por rank em Passive Points e thresholds de atributos estão explícitos e não são confundidos?
- [ ] Classe, mastery, gateways, ranks e dependências têm sentido e bloqueiam bypass/circularidade?
- [ ] As integrações globais de recursos e as progressões nativas foram respeitadas?
- [ ] A perk tem hook seguro, fail-closed e fallback sem mudar a identidade?
- [ ] O pipeline não gera proc recursivo, dupla aplicação ou recursos/mastery grátis?
- [ ] O provider tem server authority, attribution, vida útil/persistência, multiplayer e outros limites descritos?
- [ ] O item está na família certa: I, P, S generalista ou T específico?
- [ ] Se S: tags/categorias vêm de fatos comprovados; Mecânica Assinatura e owner estão corretos?
- [ ] Se perfil T: a Ability real, seu provider e o tier são verificáveis; há 2/2/3 a 9 opções e grupo 1+1+1?
- [ ] Mod removido, inclusive NeoVitae, não é tratado como provider ativo?
- [ ] O texto para o jogador é PT-BR e os campos obrigatórios do dossiê estão preenchidos?
- [ ] A cobertura dos providers e deltas dos projetos próprios foi revisada?
- [ ] O arquivo alterado foi relido no GitHub e o commit foi verificado?
- [ ] Antes da release: testes, integração real, regra de respec, topologia e documentação final foram validados?

Uma vaga `RESERVED-UNBOUND` só passa pelo **gate estrutural de reserva**, nunca pelo checklist de perk implementada.

## 7. Status e gates

Estados editoriais/técnicos:
- `RESERVED-UNBOUND`: slot T vazio; não é perk funcional.
- `CONCEPT-DRAFT`: efeito proposto, sem aprovação técnica.
- `CONCEPT-REVIEWED`: design/requisitos revisados, mas integrações podem estar pendentes.
- `TECH-AUDITED`: API/hook/versão, authority e segurança comprovados.
- `TECH-BLOCKED`: integração inviável ou pendente de condição específica; fail-closed.
- `REQUIRES-DESIGN-CHANGE`: desenho deve ser corrigido, não substituído silenciosamente.
- `IMPLEMENTED`: código presente, sem declarar release validada.
- `VALIDATED`: testes aplicáveis executados com sucesso, persistência e integração conferidas.

**Gates distintos:**

1. **Design:** dossiê conceitual completo, custos/thresholds/owner e qualidade.
2. **Auditoria técnica:** evidence/version/hook reais; `TECH-AUDITED` não pode ser inferido do design.
3. **Implementação:** runtime, adapters, slots, efeitos e storage escritos.
4. **Validação/release:** JUnit, validators, integration/GameTests e dedicated server quando aplicáveis; topology/UI/wiki sincronizadas.

Evitar a palavra “APROVADA” sem especificar se é `CONCEPT-REVIEWED`, `TECH-AUDITED` ou `VALIDATED`.

## 8. Protocolo de execução para Codex/agentes

Seguir [`CODEX-FOUR-STAGES.md`](../../../plans/03-skill-tree-perks/CODEX-FOUR-STAGES.md):

**0 → atributos I; 1 → P; 2 → S/Mecânicas Assinatura; 3 → vagas T e perfis individuais de Ability.** Modlist/versionamento são verificados durante P/S e cada Ability é auditada na etapa T. O catálogo I/P/S/T pode ser trabalhado em commits/lotes do tamanho necessário à revisão, **sem lote obrigatório de dez**.

Antes de fechar qualquer batch:
- registrar fonte e baseline da modlist e dos projetos próprios;
- listar arquivos/IDs afetados e contagem por owner;
- validar campos, versionamento, custos, thresholds, hooks e status;
- reabrir itens cuja API/versão mudou ou cujo provider foi removido;
- registrar problemas bloqueados, sem alegar que passaram;
- reler os arquivos após o commit para confirmar a persistência.

Nenhum chat específico tem responsabilidade exclusiva de design, implementação ou merge. O plano mestre define entregáveis e gates.

---

## Conexão com os documentos canônicos

- [Plano mestre](../../../plans/03-skill-tree-perks/MASTER-EXECUTION-PLAN.md)
- [Quatro etapas do Codex](../../../plans/03-skill-tree-perks/CODEX-FOUR-STAGES.md)
- [Dossiês I/P/S/T](../../../plans/03-skill-tree-perks/DOSSIER-CONTRACT.md)
- [Provas de versão, requisitos e custos](../../../plans/03-skill-tree-perks/PROVIDER-EVIDENCE-AND-COSTS.md)
- [Slots T, Ability Profiles e facts normalizadas](../../../plans/03-skill-tree-perks/ABILITY-SLOTS-AND-TAGS.md)
- [Especializações](../../../plans/03-skill-tree-perks/SPECIALIZATION-CONTRACT.md)
