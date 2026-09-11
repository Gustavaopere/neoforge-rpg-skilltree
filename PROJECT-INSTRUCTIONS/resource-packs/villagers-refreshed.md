# Villagers Refreshed

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db8136a65ffa1825333938
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack
- **Arquivo:** `villagers-refreshed-v2.zip`
- **Versão 1.21.1:** 2
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `villagers-refreshed-v2.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma `entity_model_features-3.3.5-1.21-neoforge.jar`, mod id `entity_model_features`, runtime `3.3.5`, infraestrutura recomendada pelo autor para custom entity models.

## Propriedades do banco

- **Mod:** Villagers Refreshed
- **Arquivo JAR:** `villagers-refreshed-v2.zip`
- **Tipo de conteúdo:** Resource Pack
- **Versão 1.21.1:** 2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Mobs
- **Função:** Overhaul visual de Villagers, Illagers e mobs relacionados, usando custom entity models/textures sem alterar AI, professions ou trades.
- **Dependências:** Resource pack v2; autor recomenda Entity Model Features. Stack físico contém EMF 3.3.5. Fresh Animations é integração separada e não está validada para v2 pelo addon oficial.
- **Sobreposição:** Fresh Animations, Fresh Illager Mod Compats e outros entity model packs podem tocar os mesmos villagers/illagers. Prioridade e compatibilidade devem ser verificadas por entidade.
- **Compatibilidade/Riscos:** O addon oficial Villagers Refreshed + Fresh Animations declara não estar atualizado para Villagers Refreshed v2. Riscos de overlap com Fresh Animations/Fresh Illager, CEM rules e models modded.
- **Observações:** Arquivo instalado `villagers-refreshed-v2.zip`. Autor recomenda EMF; não declarar compatibilidade v2 + Fresh Animations como resolvida enquanto o addon oficial permanecer desatualizado.
- **Procedência:** CurseForge oficial Villagers Refreshed v2 + captura do perfil RPG em 08/09/2026 + stack físico EMF/Fresh Animations.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/villagers-refreshed
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; v2, Villagers/Illagers, EMF, boundary Fresh Animations, load order, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `villagers-refreshed-v2.zip`, versão `2`. O projeto é um overhaul visual de Villagers, Illagers e mobs relacionados.

## 1. Papel e authority
Villagers Refreshed altera models/textures/apresentação de villagers e illagers. Minecraft e os mods correspondentes continuam authorities de professions, trades, AI, raids, reputação, gossip, spawn e persistence.

## 2. Cobertura confirmada
O upstream descreve a v2 como um overhaul completo de **Villagers, Illagers e mobs relacionados**. A ficha preserva esse escopo editorial sem inferir que toda entidade modded baseada em villager/illager seja automaticamente suportada.

## 3. Infraestrutura de custom entity models
O autor recomenda **Entity Model Features (EMF)** em vez de OptiFine, observando que alguns models modded não funcionam sem EMF. A modlist física confirma EMF `3.3.5`, portanto a infraestrutura recomendada está presente.

## 4. Boundary com Fresh Animations
Existe um addon oficial separado `Villagers Refreshed + Fresh Animations`, mas sua própria página declara que **ainda não foi atualizado para Villagers Refreshed v2**. Portanto v2 + Fresh Animations **não pode ser classificado como compatibilidade validada** por esse addon.

## 5. Load order e overlap
Fresh Animations, Fresh Illager Mod Compats e outros packs de entities podem tocar models/textures dos mesmos mobs. A prioridade final precisa ser validada por entidade e path; simples coexistência no menu não prova compatibilidade.

## 6. Client e reload
É conteúdo client-side. Resource reload/relog deve reconstruir custom models sem alterar villager data, professions ou trades.

## 7. Riscos
1. Addon Fresh Animations oficial estar desatualizado para v2.
2. Outro illager/villager pack sobrescrever model/texture.
3. Entidade invisível ou model quebrado por CEM/EMF rule.
4. Profissão modded ficar com fallback visual.
5. Resource reload deixar entity model cache stale.

## 8. Matriz de testes
- [ ] Villagers adultos/bebês em várias profissões e biomas.
- [ ] Wandering Trader e mobs relacionados cobertos pelo pack.
- [ ] Pillager, Vindicator, Evoker e outros illagers.
- [ ] Testar coexistência com Fresh Animations ativo.
- [ ] Testar coexistência com Fresh Illager Mod Compats.
- [ ] Resource reload/relog sem modelos invisíveis/quebrados.
- [ ] Confirmar que trades/professions não mudam com pack on/off.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma o overhaul v2 e a recomendação de EMF. A página do addon Fresh Animations declara explicitamente não estar atualizada para v2; portanto essa integração permanece aberta.

> Boundary canônico: **Villagers Refreshed v2 controla apenas apresentação; compatibilidade com Fresh Animations não deve ser declarada validada enquanto o addon oficial permanecer anterior à v2**.
