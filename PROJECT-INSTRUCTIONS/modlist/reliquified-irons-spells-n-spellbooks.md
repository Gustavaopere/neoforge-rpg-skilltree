# Reliquified Iron's Spells 'n Spellbooks

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81b69fdcd5b76eeea0b6
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `reliquified_irons_spells_and_spellbooks-1.21.1-0.2.7.jar`, mod id `reliquified_irons_spells_and_spellbooks`, runtime `0.2.7`, mixin `reliquified_irons_spells_and_spellbooks.mixins.json`; Iron's Spells 'n Spellbooks físico atual `1.21.1-3.16.3` e Relics 0.12.8 presentes
- **Data da exportação:** 2026-09-11

## Divergências documentais detectadas na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**.
- O dossiê-fonte cita **Iron's Spells 3.15.1** como provider físico. A modlist acessível confirma **`irons_spellbooks-1.21.1-3.16.3.jar` / runtime `1.21.1-3.16.3`**. Portanto, a combinação física atualmente demonstrada para regressão é Reliquified Iron's 0.2.7 + Iron's 3.16.3 + Relics 0.12.8. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Reliquified Iron's Spells 'n Spellbooks
- **Arquivo JAR:** `reliquified_irons_spells_and_spellbooks-1.21.1-0.2.7.jar`
- **Versão 1.21.1:** 0.2.7
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Magia, RPG
- **Função:** Bridge/content addon entre Relics e Iron's Spells 'n Spellbooks que adiciona relics temáticas e integra sua progressão/loot ao conteúdo mágico do Iron's.
- **Dependências:** Required: Iron's Spells 'n Spellbooks 3.15.1 + Relics 0.12.8, ambos presentes no pack físico.
- **Sobreposição:** Não substitui Iron's nem Relics. Iron's mantém spells/mana/cooldown/structures; Relics progression/abilities; o addon conecta os domínios.
- **Compatibilidade/Riscos:** Beta-only para 1.21.1. Riscos: Relics/Iron's API drift, double-cast/double-charge, research UI/state mismatch, loot duplication, client/server desync e dedicated-server regression. 0.2.7 adiciona ability icons e research entries para end relics.
- **Observações:** 0.2.7 é a build instalada atual da linha NeoForge 1.21.1; não existe fundamento para downgrade arbitrário para outra Beta apenas por canal. Linha histórica já recebeu fixes de dedicated server/Relics compatibility.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + publicação/dependências oficiais Reliquified Iron's + changelog exato 0.2.7.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/reliquified-irons-spells-n-spellbooks
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Reliquified Iron's 0.2.7 reconstruído: ownership Iron's/Relics, loot em estruturas, ability icons/research delta, mana/cooldown causalidade, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `reliquified_irons_spells_and_spellbooks-1.21.1-0.2.7.jar`, mod id `reliquified_irons_spells_and_spellbooks`, versão `0.2.7`, NeoForge 1.21.1. O projeto integra **Relics** a **Iron's Spells 'n Spellbooks** e toda a linha pública aplicável a 1.21.1 permanece Beta; a 0.2.7 é a build atual instalada, não um downgrade acidental.

## 1. Identidade e papel
- **Mod:** Reliquified Iron's Spells 'n Spellbooks.
- **JAR:** `reliquified_irons_spells_and_spellbooks-1.21.1-0.2.7.jar`.
- **Mod id:** `reliquified_irons_spells_and_spellbooks`.
- **Versão instalada:** `0.2.7`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Beta.
- **Ambiente publicado:** Client & Server.

## 2. Dependências e stack físico
A publicação oficial exige:
- **Iron's Spells 'n Spellbooks**;
- **Relics**.

O pack atual contém Iron's Spells 3.15.1 e Relics 0.12.8. A integração deve ser testada exatamente contra esse par físico.

## 3. Papel no modpack
O addon adiciona relics próprias que usam mecânicas/conteúdo do Iron's Spells e as coloca no framework de progression de Relics. A documentação oficial também informa que relics do addon podem aparecer em estruturas adicionadas por Iron's Spells ou seus addons.

Ele não é um novo sistema de spells nem um segundo framework de relics.

## 4. Autoridade / ownership
- **Iron's Spells:** spells, schools, mana, cooldowns, cast semantics, estruturas e conteúdo mágico do provider.
- **Relics:** relic XP/progression, rank/level, ability framework e state da relic.
- **Reliquified Iron's:** conteúdo cruzado e regras de integração entre ambos.
- **Curios/slot provider do stack Relics:** equip lifecycle quando aplicável.

Qualquer ability que invoque spell deve respeitar o provider de mana/cooldown, sem criar cobrança ou cast paralelo não intencional.

## 5. Delta exato da 0.2.7
O changelog da versão instalada registra:
- adição de **ability icons**;
- adição de **research entries para as end relics**.

Esses são regression gates diretos da 0.2.7, especialmente no client/resource layer e na disponibilidade das entradas de pesquisa.

## 6. Pesquisa e apresentação
A existência de research entries implica uma superfície de dados/UI que precisa permanecer coerente com a progressão funcional. Ícone ou entrada visível não prova que a ability está habilitada no servidor; da mesma forma, ability funcional sem research entry esperada indica divergence de data/client resources.

Não foi inferido o schema interno das research entries sem source pin exato.

## 7. Loot e estruturas
A documentação do projeto informa que as relics podem spawnar em estruturas do Iron's Spells ou de seus addons. O addon portanto participa de integração de loot/structure context, mas **Iron's continua authority da estrutura**.

Testar que cada reward é adicionada uma única vez e que addon de estrutura não produz duplicação por múltiplos injectors.

## 8. Mana, cooldown e cast
Quando uma relic aciona comportamento dependente de spell, validar:
- owner/caster correto;
- consumo de mana exatamente uma vez quando aplicável;
- cooldown exatamente uma vez quando aplicável;
- ausência de double-cast por observers de Relics e Iron's;
- spell school/target preservados.

Não foi presumido que toda relic use mana ou spell cast.

## 9. Client / Server
O addon é Client & Server. Progressão, loot, mana/cooldown e efeitos funcionais devem ser server-authoritative. Ability icons, research UI e efeitos visuais são client-facing, mas devem refletir state real do servidor.

## 10. Lifecycle
Validar:
- acquire/loot;
- equip/unequip;
- login/relogin;
- death/respawn;
- dimension change;
- server restart;
- relic XP/level/rank;
- spell cast/cooldown interaction;
- research unlock/read state;
- resource reload para icons/UI;
- update de Iron's ou Relics.

## 11. Multiplayer
Em multiplayer, relic XP, research e spell source devem permanecer por jogador. Um cast acionado por relic não pode ser creditado ao jogador errado nem executar duas vezes por packet/client prediction.

Dois jogadores com a mesma relic devem poder manter cooldown/progressão independentes.

## 12. Integrações concretas no pack
- **Iron's Spells 3.15.1:** provider mágico obrigatório.
- **Relics 0.12.8:** framework obrigatório.
- O pack possui vários addons de Iron's; estruturas adicionadas por eles podem ser candidatas à distribuição documentada de relics, mas nenhuma estrutura específica foi declarada sem data/source correspondente.

## 13. Riscos técnicos
1. **Relics API drift:** progression/ability quebra.
2. **Iron's API drift:** spell/mana/cooldown/structure hooks mudam.
3. **Double-cast/double-charge:** mesma ability processada por dois sistemas.
4. **Research mismatch:** icon/entry diverge do state funcional.
5. **Loot duplication:** múltiplas structure integrations adicionam a mesma relic.
6. **Client/server desync:** research ou ability aparece apenas de um lado.
7. **Beta schema drift:** IDs/data mudam entre builds.
8. **Dedicated-server regression:** linha histórica já recebeu fixes de servidor e deve ser sempre revalidada.

## 14. Matriz de testes
- [ ] Dedicated server inicia com 0.2.7 + Iron's 3.15.1 + Relics 0.12.8.
- [ ] Ability icons da 0.2.7 renderizam sem missing texture.
- [ ] Research entries das end relics aparecem e correspondem ao state funcional.
- [ ] Relic é obtida por loot/estrutura sem duplicação não intencional.
- [ ] Equip/unequip não duplica modifiers/ability state.
- [ ] Relic que usa spell/mana consome exatamente uma vez.
- [ ] Cooldown não duplica nem é ignorado indevidamente.
- [ ] XP/progressão Relics persiste após relog/restart.
- [ ] Death/respawn e dimension change não deixam state stale.
- [ ] Dois jogadores mantêm progressão/cooldown separados.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 15. Evidências e limites
- Modlist física: JAR/mod id/runtime e providers instalados.
- Publicação oficial: required dependencies Iron's Spells + Relics, ambiente Client & Server e distribuição via estruturas.
- Changelog oficial 0.2.7: ability icons + research entries para end relics.
- **Limite:** não foi inventariado todo o conjunto de relics/abilities da 0.2.7; classes, research IDs e recipes não foram inventados sem source pin.
