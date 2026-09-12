# Reliquified Artifacts

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81009b3eceda8fbeac99
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `reliquified_artifacts-1.21.1-1.0.8.jar`, mod id `reliquified_artifacts`, runtime `1.0.8`, mixin `reliquified_artifacts.mixins.json`; Artifacts 13.2.3, Relics 0.12.8, Curios 9.5.1, Sophisticated Backpacks 3.26.2 e Reliquified Ars Nouveau 0.8.1 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Reliquified Artifacts 1.0.8, Artifacts 13.2.3 e os integradores citados estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Reliquified Artifacts
- **Arquivo JAR:** `reliquified_artifacts-1.21.1-1.0.8.jar`
- **Versão 1.21.1:** 1.0.8
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, RPG
- **Função:** Bridge/content addon Relics × Artifacts que reinterpreta artifacts como relics com progressão e mecânicas do framework Relics, mantendo ownership dos providers-base.
- **Dependências:** Relics 0.12.8 + Artifacts 13.2.3 são providers-base presentes. Curios 9.5.1 sustenta equip lifecycle. Sophisticated Backpacks 3.26.2 é integração concreta relevante; Reliquified Ars Nouveau 0.8.1 também corrige compatibilidade com este addon.
- **Sobreposição:** É bridge entre Artifacts e Relics, não duplicata. Artifacts mantém conteúdo-base; Relics progression/abilities; Curios slots; Sophisticated Backpacks storage/pickup.
- **Compatibilidade/Riscos:** 1.0.8 corrige explicitamente compatibilidade com Artifacts 13.2.3, exatamente a versão física atual. Riscos: version coupling, double-processing/XP, Curios stale modifiers, pickup dupe com Sophisticated Backpacks, loot overlap, cross-addon drift e power stacking.
- **Observações:** Correção de metadata: o pack físico usa Artifacts 13.2.3, não 13.2.5. A linha 1.0 documenta rework de 48 relics com 120+ mecânicas; esse número é lineage do redesign, não delta exclusivo da 1.0.8.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + publicação/changelog oficial Reliquified Artifacts 1.0.8 + changelogs históricos 1.0–1.0.4 usados apenas para lineage/regression surfaces.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/reliquified-artifacts
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Reliquified Artifacts 1.0.8 reconstruído: Artifacts 13.2.3 corrigido, 48-relic lineage, XP/Curios/pickup, Sophisticated Backpacks, lifecycle, riscos e testes.
- **Histórico da decisão:** 2026-09-06 — pesquisa fechada em Manter. 1.0.8 é a build atual NeoForge 1.21.1; Relics e Artifacts estão presentes. Risco Beta preservado, sem indicação de incompatibilidade estrutural que exija remoção.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `reliquified_artifacts-1.21.1-1.0.8.jar`, mod id `reliquified_artifacts`, versão `1.0.8`, NeoForge 1.21.1. É uma bridge/content addon entre **Relics** e **Artifacts**. A 1.0.8 é Beta e seu changelog corrige especificamente compatibilidade com **Artifacts 13.2.3**, exatamente a versão física instalada no pack.

## 1. Identidade e papel
- **Mod:** Reliquified Artifacts.
- **JAR:** `reliquified_artifacts-1.21.1-1.0.8.jar`.
- **Mod id:** `reliquified_artifacts`.
- **Versão instalada:** `1.0.8`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Beta.
- **Ambiente:** Client & Server.

## 2. Stack físico confirmado
- **Relics:** 0.12.8.
- **Artifacts:** 13.2.3.
- **Curios:** 9.5.1.
- **Sophisticated Backpacks:** 3.26.2.
- **Reliquified Ars Nouveau:** 0.8.1, que também contém fix explícito de compatibilidade com Reliquified Artifacts.

A referência antiga a Artifacts 13.2.5 estava incorreta para o snapshot físico atual e foi removida.

## 3. Papel no modpack
O addon reinterpreta/conecta o conteúdo de Artifacts ao framework de progressão de Relics, produzindo relics associadas a artefatos e adicionando mecânicas sobre esse conteúdo.

A linha 1.0 do projeto documenta um rework de **48 relics** com **120+ novas mecânicas**. Esse número é evidência de lineage do redesign 1.0; não é apresentado como delta exclusivo da 1.0.8.

## 4. Autoridade / ownership
- **Artifacts:** conteúdo-base, identidade e comportamento original dos artifacts quando não substituído/estendido pelo addon.
- **Relics:** progression/XP, relic ability framework, rank/level e state do ecossistema Relics.
- **Reliquified Artifacts:** adaptação e mecânicas cruzadas entre os dois providers.
- **Curios:** slot/equip lifecycle.
- **Sophisticated Backpacks:** armazenamento e pickup upgrades próprios.

A bridge não deve criar terceiro ownership para slots ou storage.

## 5. Delta exato da 1.0.8
O changelog oficial da build instalada registra uma correção de **compatibilidade com Artifacts 13.2.3**.

Isso transforma a combinação física atual — Reliquified Artifacts 1.0.8 + Artifacts 13.2.3 — em regression gate prioritário. O teste deve confirmar que o fix realmente funciona no runtime do pack; a auditoria documental não substitui execução.

## 6. Lineage funcional relevante
Fixes anteriores da linha 1.0 documentam superfícies concretas que continuam úteis como regressão:
- Eternal Steak / Everlasting Beef;
- Pocket Piston;
- Power Glove;
- Universal Attractor;
- Drinking Hat;
- Lucky Scarf;
- Fire Gauntlet;
- Angler's Hat;
- Invisibility Scarf;
- Kitty Slippers.

Esses nomes são usados para mapear áreas de risco confirmadas pela história do projeto, não para declarar bugs atuais da 1.0.8.

## 7. Integração com Sophisticated Backpacks
A lineage da 1.0.1 corrigiu XP do **Universal Attractor** ao interagir com pickup upgrade de Sophisticated Backpacks. Como Sophisticated Backpacks está fisicamente presente, essa é uma integração concreta que merece regression test.

Ownership continua separado: o backpack/provider decide pickup/storage; a relic não deve duplicar item pickup nem XP ao observar o mesmo evento.

## 8. XP, progressão e idempotência
Relic XP/progression devem ocorrer uma vez por evento lógico. Bridges entre Artifacts e Relics são particularmente sensíveis a:
- artifact event + relic event observando a mesma ação;
- equip/unequip reaplicando modifiers;
- pickup hooks gerando XP em cascata;
- death/relog reconstruindo state duas vezes.

Todo ganho precisa permanecer vinculado ao owner correto.

## 9. Loot e spawning
A lineage da 1.0.3 incluiu opção/correção para desabilitar spawning de artifacts em entidades. Portanto spawning/loot é uma superfície configurável que deve ser validada após updates.

Não foi presumida a configuração efetiva do pack sem abrir os arquivos locais de config/data.

## 10. Client / Server
O addon é Client & Server. Relic progression, modifiers e efeitos funcionais devem ser server-authoritative. Tooltip/model/feedback podem ser client-facing.

Qualquer mismatch entre tooltip e state funcional deve ser tratado como desync, não como evidência de que a ability está correta.

## 11. Lifecycle
Validar:
- acquire/spawn/loot;
- equip/unequip em Curios;
- login/relogin;
- death/respawn;
- dimension change;
- world/server restart;
- XP/level/rank changes;
- item pickup via player e via Sophisticated Backpacks;
- config reload quando suportado;
- coexistência com Reliquified Ars Nouveau 0.8.1.

## 12. Multiplayer
Dois jogadores com o mesmo artifact/relic não podem compartilhar XP, cooldown, target ou modifier. Pickup de um jogador não pode creditar outro. Em grupos/equipes, efeitos de área devem manter owner/source corretos.

## 13. Integrações concretas na modlist
- **Relics 0.12.8:** framework de relic progression.
- **Artifacts 13.2.3:** provider-base explicitamente corrigido pela 1.0.8.
- **Curios 9.5.1:** equip lifecycle.
- **Sophisticated Backpacks 3.26.2:** integração historicamente corrigida para Universal Attractor/pickup XP.
- **Reliquified Ars Nouveau 0.8.1:** contém fix explícito de compatibilidade com este addon.

## 14. Riscos técnicos
1. **Version coupling:** 1.0.8 foi corrigida para Artifacts 13.2.3; update de qualquer lado exige revalidação.
2. **Double-processing:** evento de Artifact e Relics aplicado duas vezes.
3. **Duplicate XP:** pickup/consumption/combat produz progressão duplicada.
4. **Curios stale modifiers:** equip/unequip/relog deixa efeito residual.
5. **Sophisticated pickup dupe:** item/XP processado por duas rotas.
6. **Loot/spawn overlap:** mesma reward adicionada por múltiplos providers.
7. **Cross-addon drift:** interação com Reliquified Ars.
8. **Power stacking:** 48-relic redesign + outros acessórios do pack.
9. **Beta migration:** IDs/state podem mudar entre builds.

## 15. Matriz de testes
- [ ] Dedicated server inicia com 1.0.8 + Artifacts 13.2.3 + Relics 0.12.8.
- [ ] Um artifact/relic representativo equipa e remove modifier uma única vez.
- [ ] XP/progressão avança uma vez e persiste após relog/restart.
- [ ] Death/respawn não duplica ability/modifier.
- [ ] Universal Attractor + Sophisticated Backpacks pickup upgrade não duplica item nem XP.
- [ ] Eternal Steak/Everlasting Beef não apresentam regressão de stat/functionality.
- [ ] Pocket Piston respeita condição de carga conforme comportamento atual.
- [ ] Fire Gauntlet/Kitty Slippers não reproduzem crashes históricos em cenário equivalente.
- [ ] Loot/spawning não gera duplicação não intencional.
- [ ] Reliquified Ars 0.8.1 coexiste sem conflito de compatibilidade.
- [ ] Dois jogadores mantêm progressão/state completamente separados.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 16. Evidências e limites
- Modlist física: versões exatas de Reliquified Artifacts, Artifacts, Relics, Curios, Sophisticated Backpacks e Reliquified Ars.
- Changelog 1.0.8: fix de compatibilidade com Artifacts 13.2.3.
- Changelog 1.0: rework de 48 relics/120+ mecânicas, tratado como lineage.
- Changelogs 1.0.1–1.0.4: superfícies históricas de regressão, sem promover fixes antigos a bugs atuais.
- **Limite:** não foi inventariado individualmente o registry das 48 relics na build 1.0.8; a ficha documenta subsistemas, ownership e riscos confirmados.
