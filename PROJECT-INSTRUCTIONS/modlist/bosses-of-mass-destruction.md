# Bosses of Mass Destruction

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db812aa3bfc7479a286063
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Bosses of Mass Destruction
- **Arquivo JAR:** `BOMD-NeoForge-1.21-1.3.3.jar`
- **Versão 1.21.1:** 1.3.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Mobs, RPG, Exploração, Worldgen
- **Função:** Port NeoForge de Bosses of Mass Destruction com 4 bosses endgame — Night Lich, Obsidilith, Nether Gauntlet e Void Blossom — suas estruturas/arenas, summon/navigation items, projectiles, loot e configs.
- **Dependências:** CERBON's API 1.3.0, GeckoLib 4.9.2 e Cloth Config 15.0.140 estão fisicamente presentes no pack; o projeto declara CERBON's API, GeckoLib e Cloth Config API como dependências.
- **Sobreposição:** Boss/worldgen endgame próprio. Coexistência deve ser balanceada por spacing, loot e dificuldade; não remover por semelhança temática sem conflito concreto.
- **Compatibilidade/Riscos:** Sobreposição de bosses/estruturas com Cataclysm, Mowzie's Mobs e Bosses'Rise é temática, não incompatibilidade automática. Riscos: structure density, boss state/reload, projectiles, config balance, render/animation e regressão de registry. 1.3.3 corrige double registration de VOID_BLOSSOM_STRUCTURE_TYPE.
- **Observações:** Quatro bosses oficiais: Night Lich em torres raras de biomas frios (Soul Stars apontam o caminho), Obsidilith em estruturas raras do End, Nether Gauntlet em estruturas raras do Nether e Void Blossom em cavernas raras no fundo do mundo (Void Lilies orientam). Source confirma 10 entity types totais na superfície principal: 4 bosses + 6 auxiliares/projectiles.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial BOMD Forge/NeoForge 1.3.3 + source oficial CERBON-MODS/Bosses-of-Mass-Destruction-FORGE + dependências físicas atuais.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/bosses-of-mass-destruction-forge
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — BOMD 1.3.3 físico confirmado; 4 bosses/10 entity types, structures/summon/config, fix de double registration e dependências CERBON's API 1.3.0 + GeckoLib 4.9.2 + Cloth Config 15.0.140 preservados. Runtime QA não executado.
- **Histórico da decisão:** 2026-09-07 — novo mod incorporado à auditoria; sem decisão curatorial ainda.
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `BOMD-NeoForge-1.21-1.3.3.jar`, mod id `bosses_of_mass_destruction`, runtime `1.3.3`, NeoForge 1.21.1. O port oficial CERBON usa CERBON's API, GeckoLib e Cloth Config; no pack estão presentes CERBON's API `1.3.0`, GeckoLib `4.9.2` e Cloth Config `15.0.140`.

## 1. Papel e autoridade
Bosses of Mass Destruction é um provider de **quatro encounters endgame**, cada um ligado a ambiente/estrutura e moveset próprios. O mod é authority de spawn/summon, boss state, attacks, loot e estruturas BOMD. Outros combat mods podem alterar animação/controles do jogador, mas não devem reexecutar attacks, phase state ou loot do boss.

## 2. Boss roster oficial — 4
### Night Lich
Encontrado em torres raras de biomas frios. **Soul Stars** servem como ferramenta de localização da torre. O source confirma entidade `lich`, projectiles `blue_fireball`/Magic Missile e `comet`, além da entidade Soul Star.

### Obsidilith
Boss do End encontrado em estruturas raras nas ilhas do End. O source registra entidade `obsidilith`, Charged Ender Pearl e blocos/infraestrutura de summon/runes associados.

### Nether Gauntlet
Boss encontrado em estruturas raras no Nether. O source registra entidade `gauntlet`, componentes visuais/laser/energy renderer e blocos próprios da arena.

### Void Blossom
Boss encontrado em cavernas raras na parte inferior do mundo. **Void Lilies** apontam o caminho. O source registra `void_blossom`, `spore_ball` e `petal_blade` e infraestrutura de summon/arena.

## 3. Entity registry confirmado
O source atual do port registra 10 entity types na superfície principal:
1. `lich`;
2. `blue_fireball`;
3. `comet`;
4. `soul_star`;
5. `charged_ender_pearl`;
6. `obsidilith`;
7. `gauntlet`;
8. `void_blossom`;
9. `spore_ball`;
10. `petal_blade`.

A contagem inclui os quatro bosses e seis entidades/projectiles auxiliares. Não tratá-las como dez mobs de spawn natural.

## 4. Estruturas e navegação
Os encounters são vinculados a estruturas/locais raros. Soul Stars e Void Lilies funcionam como mecanismos provider-native de descoberta. Integrações de quests/mapa podem referenciar esses recursos, mas não devem criar uma segunda lógica de geração ou marcar estrutura como encontrada antes do settlement real.

## 5. Summon e persistência
O source contém blocos de summon/arena como `ObsidilithSummonBlock` e `VoidBlossomSummonBlockEntity`, além de altar/monolith/levitation/mob-ward infrastructure. State de summon precisa ser server-authoritative e persistir corretamente por chunk unload/restart.

Não reexecutar summon ao carregar a block entity se o encounter já foi consumido/concluído.

## 6. Boss health e render
O port possui mixin para `BossHealthOverlay` e renderers/Geo models específicos. Essa camada é apresentação client-side. HP real, immunity, target e death settlement permanecem servidor.

GeckoLib/render callbacks não devem ser usados como gatilho autoritativo de dano.

## 7. Configuração
O source usa `BMDConfig`/AutoConfig para parâmetros de mobs/encounters. Balanceamento do pack deve preferir config/provider antes de patchar atributos por evento global.

Ao alterar health/damage/cooldown, testar boss phase/attack scripts e multiplayer; um valor externo não pode ser aplicado duas vezes sobre config já ajustada.

## 8. Release 1.3.3
A 1.3.3 corrige **double registration de `VOID_BLOSSOM_STRUCTURE_TYPE`**, problema que podia causar crash em NeoForge mais recente. Isso torna registry bootstrap um gate explícito de regressão desta versão.

Não adicionar registry bridge própria para “garantir” a structure type: exatamente uma registration é o comportamento correto.

## 9. Client/server
- boss AI, hit/damage, summon, structure state e loot: servidor/common;
- model, animation, particles, boss overlay e render: cliente;
- projectiles têm entidade/state sincronizado; partícula não é prova de hit;
- dedicated server precisa carregar sem depender de classes visuais.

## 10. Multiplayer
Testar cada encontro com múltiplos players:
- target switching;
- player death/re-entry;
- boss death exactly-once;
- loot/advancement sem duplicação;
- projectile ownership;
- chunk unload quando players se afastam;
- summon block não reiniciar encounter indevidamente.

## 11. Relação com o stack de bosses do pack
O pack possui outros providers como L_Ender's Cataclysm, Mowzie's Mobs e Bosses'Rise. Isso cria **densidade e progressão paralelas**, não conflito técnico por definição.

Curadoria deve observar:
- frequência/spacing de arenas;
- loot power;
- dificuldade relativa;
- mods de scaling;
- compat com Epic Fight/hitboxes;
- worldgen em dimensões já muito modificadas.

## 12. Riscos
1. Registro duplicado de structure/entity após update.
2. Boss state reinicializado em chunk reload.
3. Projectile/damage callback executado em ambos os lados.
4. Soul Star/Void Lily apontar para estrutura inválida após worldgen changes.
5. Boss overlay/render conflitar com HUDs de target/boss.
6. Config externa aplicar health/damage duas vezes.
7. Structure density excessiva com outros boss mods.
8. Animation event ser confundido com hit settlement.

## 13. Matriz de testes
1. Dedicated server boot com CERBON API 1.3.0, GeckoLib 4.9.2 e Cloth Config 15.0.140.
2. Localizar Night Lich via Soul Star e concluir encontro.
3. Gerar/combater Obsidilith no End.
4. Gerar/combater Nether Gauntlet no Nether.
5. Localizar Void Blossom via Void Lilies e concluir encounter.
6. Save/restart no meio de cada boss fight.
7. Dois jogadores: target, death/rejoin e loot.
8. Projectiles em alta latência sem double-hit.
9. Epic Fight ativo: hitboxes/iframes/damage exactly-once.
10. Gerar chunks novos com outros structure providers e medir densidade.

## 14. Evidência
- modlist física atual: BOMD NeoForge 1.3.3 + dependências presentes;
- CurseForge oficial do port: quatro bosses e ambientes de encontro;
- source oficial CERBON-MODS, registry `BMDEntities`: 10 entity types confirmados;
- changelog 1.3.3: correção de double registration de Void Blossom structure type.

> 👹 Roster canônico desta build: **Night Lich, Obsidilith, Nether Gauntlet e Void Blossom**. Entidades auxiliares/projectiles são catalogadas separadamente e não inflacionam a contagem de bosses.
