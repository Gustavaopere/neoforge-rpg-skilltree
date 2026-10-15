# 01 — Internal Attributes — Ponto 0

O sistema tem seis atributos canônicos **compartilhados entre todas as árvores**, independentes de P/S/T. Ranks são ilimitados no RPG Core; seus efeitos não podem crescer linearmente para sempre.

**Contrato de investimento e balanceamento:** [`INVESTMENT-BALANCE-V0.1.md`](./INVESTMENT-BALANCE-V0.1.md).

| Código | Atributo | Papel principal |
|---|---|---|
| I0001 | [Força](./I0001-strength.md) | impacto físico, esforço muscular e tarefas manuais |
| I0002 | [Constituição](./I0002-constitution.md) | saúde corporal e resistência a desgaste |
| I0003 | [Agilidade](./I0003-agility.md) | movimento, cadência corporal e entrega de projéteis |
| I0004 | [Inteligência](./I0004-intelligence.md) | reserva de magia, regeneração e potência de habilidades mágicas |
| I0005 | [Determinação](./I0005-determination.md) | disciplina, constância, recuperação e resistência a interrupções |
| I0006 | [Carisma](./I0006-charisma.md) | negociação, liderança de aliados e cura prestada |

**Status:** cálculos puros das 18 trilhas implementados; **seis modificadores vanilla, uma cura nativa, uma velocidade de projétil no lançamento, três modificadores Iron's, um cooldown nativo Iron's e um modifier Epic Fight de stamina** registrados no código (CI do último incremento pendente). Os **outros cinco** benefícios ou partes multi-provider aguardam auditoria técnica. Não confundir CI com teste físico completo do modpack. Cada atributo concede três trilhas de bônus por marcos com retornos decrescentes, fonte de verdade em `AttributeRanks`. Gastos de rank seguem `AttributeRankCostPolicy`. O preço proposto e os marcos serão confrontados com o orçamento real de Core Points.

**Requisito de perk:** `INTELLIGENCE >= 20`, por exemplo, é mínimo **não consumível**, não gasto de atributos. O runtime já aplica esse gate em `NodeAccessRequirement` e no servidor antes do débito de Passive Points.

**Regra anti-duplicação:** não criar segunda barra de mana, stamina, HP ou stats específicos. Mods opcionais usam adapters versionados e fail-closed, sem efeito fictício se o provider não suportar o hook.

**Referências:** [BG3 — Abilities](https://bg3.wiki/wiki/Abilities), [Path of Exile — Attributes](https://www.poewiki.net/wiki/Attribute), [Elden Ring — Vigor](https://eldenring.wiki.gg/wiki/Vigor). Esses jogos inspiram papéis e soft caps, **não fornecem coeficientes aprovados para Minecraft**.


## Progresso de implementação — 14/10/2026

- Cálculo matemático dos 18 efeitos: `AttributeInvestmentBonuses` (puro, `long` e limites finitos).
- Seis modificadores nativos conectados ao Minecraft 1.21.1: Força/dano corpo a corpo nativo, resistência a empurrão e mineração manual, Constituição/vida máxima, Agilidade/movimento e velocidade de ataque vanilla; adicionalmente Constituição/curas recebidas usa o evento NeoForge `LivingHealEvent`. Três efeitos de Inteligência e o cooldown de Determinação foram integrados ao Iron's 3.16.3; a regeneração de stamina de Determinação usa o atributo nativo do Epic Fight 21.17.3.1. Todos esses adapters são condicionados à versão exata do provider. A prévia pública de atributos não substitui a validação de gameplay; fontes e código precisam ser examinados na revisão. **Implementados no código, ainda dependentes de validação GameTest/servidor e cross-mod.**
- Cinco contribuições ainda sem ponte de gameplay completa (e componentes de provedores adicionais dentro de contribuições já cobertas): bloqueados/pendentes de source, ABI ou hook equivalente na versão instalada; jamais simular stats sem provider.
- Requisitos de `requiredAttributes` nos nós usam nomes canônicos em maiúsculas (por exemplo `{"INTELLIGENCE":20,"AGILITY":15}`), número inteiro positivo `long`; verificação e reconciliação no servidor via `NodeAccessResolver` e `ProgressionService` (sem consumo de ranks). Perda do mínimo no respec usa política existente de remover/refundir ranks dos nós passivos.
- **Economia preservada:** `UnitAttributeRankCostPolicy`, 1 Core Point por rank, sem instalar a curva proposta `1+floor((novoRank−1)/50)`; alterar a economia sem migração afetaria custo/reembolso de saves.
- Documentos/versões físicos da modlist são apenas evidência de instalação declarada; nenhuma API de mod externo foi considerada certificada sem examinar o JAR da mesma release.

**Prévia de requisitos no cliente (outubro/2026):** `TreeDisplayProjector` aceita o snapshot Core de `AttributeRanks` e calcula elegibilidade em modo fail-closed sem snapshot. `ClientTreeLayout` suporta `requiredAttributes` e tooltips expõem rank atual/mínimo em português. A paridade de regras e layout é auditada por `scripts/validate-client-tree.py`; a compra definitiva continua exclusiva do servidor. Datapacks externos que alterem requisitos sem sincronizar metadados cliente exigem um contrato específico de rede na etapa de interface dinâmica.

**CI anterior comprovado:** [RPG Skill Tree CI #38603733437](https://github.com/Gustavaopere/neoforge-rpg-skilltree/actions/runs/38603733437) e [Battle Mage Epic Compatibility #38603733379](https://github.com/Gustavaopere/neoforge-rpg-skilltree/actions/runs/38603733379) aprovados no commit `15dd074`. A integração Epic Fight/stamina foi acrescentada posteriormente e necessita de novo resultado verde em ambas as suítes antes de ser rotulada VALIDATED.

**Incremento atual:** Força/dano corpo a corpo passa a usar `minecraft:generic.attack_damage` e o mesmo refresh server-authoritative, com teste de aplicação real ao atributo, rank extremo e remoção. O novo incremento ainda não tem resultado de CI confirmado. Determinação/controle continua bloqueado após auditoria de `MobEffectEvent`/`LivingEntity`: não há uma ponte genérica segura para qualquer debuff. **Gate SonarQube do PR:** análise anterior reportou 70,6% de cobertura no código novo, abaixo dos 80% exigidos. Esse bloqueio é distinto de workflows de build/GameTests verdes.

**Agilidade/projétil (incremento mais recente):** `PhysicalProjectileMasteryEvents.onAgilityArrowJoin` usa a correlação de lançamento do arco vanilla para alterar uma vez a velocidade inicial da flecha em `EntityJoinLevelEvent`. GameTest novo incluído; revisão de Sonar classifica esse GameTest como código de teste. Benefício **IMPLEMENTADO NO CÓDIGO / CI NOVO PENDENTE**. Total provisório: 13/18 implementações de gameplay, cinco pendentes. Nenhuma alegação de compatibilidade com todas as armas/addons; PR permanece Draft.

**Carisma/companheiro — integração restrita:** `InvestedCharismaCompanionDamageEvents` conecta `CHARISMA_COMPANION_DAMAGE` **somente a dano melee de `Wolf` vanilla domesticado** contra hostis, com dono online/same-world e GameTest de evento real com fixtures isoladas. Outros pets, summons, dano indireto e PvP permanecem sem suporte; `CHARISMA_VILLAGER_DISCOUNT` e `CHARISMA_ALLY_HEALING` continuam bloqueados. Total provisório em código **14/18**, sendo Carisma/companheiros **parcial por domínio**; o CI desse incremento e o gate Sonar >=80% seguem pendentes. O HEAD anterior `d7d878d` teve 4 workflows centrais aprovados, mas Sonar reprovado por **72,4%** de cobertura New Code. PR Draft, sem merge.

**Constituição/DoT (incremento 15):** adapter `InvestedConstitutionDotEvents` para dano vanilla `on_fire` e `wither` sem causador explícito, com rank canônico, escala matemática pré-definida, fail-closed e GameTest nativo. **Não** protege de veneno, magia, lava, dano de contato inicial, addons e outros hazards não classificados. Contagem provisória **15/18 conexões de gameplay**, esta ainda parcial por fonte; três trilhas continuam bloqueadas integralmente (Determinação/controle, Carisma/desconto, Carisma/cura de aliados). No último HEAD comprovadamente verde `09e492b` o Sonar passou com 80,6%; nova implantação precisa de novo CI e QA. PR Draft, sem merge.

**Determinação/controle (incremento 16, escopo estrito):** `InvestedDeterminationControlEvents` encurta a primeira Levitação de Shulker vanilla atribuída corretamente pelo `MobEffectEvent.Added`, rank 8 = 0,4% e teto assintótico de 10% da duração; não atende efeitos reaplicados, `forceAddEffect`, outros efeitos nem providers externos. GameTest de evento com fixture vanilla adicionado; **CI novo pendente**. Contagem provisória **16/18 contribuições com alguma conexão de gameplay**, mas controle é **parcial por fonte**; duas contribuições de Carisma ainda sem hook válido: desconto por aldeão e cura realizada em aliados. PR Draft; nenhum merge ou QA físico do modpack completo.
