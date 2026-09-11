# Ozymandias Sundries

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db8184be6ff1ac17ed4f91
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `ozymandias_sundries-0.0.5.jar`, mod id `ozymandias_sundries`, metadata runtime `0.0.1`, hash `b973622ed90f47108aba481fec3beb19b432d853`; Iron's Spells `3.16.3` confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Divergências documentais detectadas na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**.
- A identidade dupla é física e foi preservada: o arquivo/publicação é **0.0.5**, enquanto a metadata runtime permanece **0.0.1**. Nenhuma normalização foi feita por inferência.

## Propriedades do banco

- **Mod:** Ozymandias Sundries
- **Arquivo JAR:** `ozymandias_sundries-0.0.5.jar`
- **Versão 1.21.1:** 0.0.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, RPG
- **Função:** Addon de Iron's Spells 'n Spellbooks com weapons, spellbooks e spells próprios associados a Holy, Blood, Eldritch, Ice, Fire, Nature, Ender, Lightning e Evocation.
- **Dependências:** Iron's Spells 'n Spellbooks — provider/base requerido pelo conteúdo do addon e presente no pack.
- **Sobreposição:** Conteúdo adicional do ecossistema Iron's; comparar spells/gear concretos com outros addons antes de qualquer corte. Não é sistema mágico independente.
- **Compatibilidade/Riscos:** Addon de Iron's com school-power gear/spells. Riscos: filename/publicação 0.0.5 vs runtime metadata 0.0.1, attribute stacking, power creep, teleport/effect lifecycle e overlap com outros addons Iron's.
- **Observações:** JAR/publicação `ozymandias_sundries-0.0.5.jar` (file ID 6978561), mas metadata runtime físico permanece `0.0.1`. Stormcaller Trident é creative-only; mobs/spells adicionais citados como planejados não foram promovidos a conteúdo atual.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial da release 0.0.5 + descrição oficial de items/spells. Divergência de metadata 0.0.1 preservada fail-closed.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ozymandias-sundries
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Ozymandias Sundries reconstruído com dual identity preservada: JAR/publicação 0.0.5 versus metadata runtime 0.0.1; weapons, spellbooks, spells, creative-only content, Iron's ownership, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** O arquivo físico é `ozymandias_sundries-0.0.5.jar` e a publicação oficial também é **0.0.5**, porém o metadata interno do JAR reportado pela modlist física continua em **runtime `0.0.1`**. Esta divergência é preservada deliberadamente: o catálogo não reescreve o runtime para combinar com o filename. O mod é addon de Iron's Spells 'n Spellbooks com armas, spellbooks/acessórios e spells próprios.

## 1. Identidade, versão e authority
- **Mod:** Ozymandias Sundries.
- **JAR físico/publicação:** `ozymandias_sundries-0.0.5.jar`.
- **Mod id:** `ozymandias_sundries`.
- **Metadata runtime físico:** `0.0.1`.
- **Release pública correspondente ao arquivo:** `0.0.5`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** 0zymandias.
- **CurseForge project ID:** 1209674.
- **Ambiente:** Client & Server.
- **Licença:** MIT.
- **Papel:** addon de conteúdo para Iron's Spells 'n Spellbooks, adicionando equipamento mágico e spells ligados a diferentes schools.
- **Decisão:** Sem decisão.

## 2. Divergência 0.0.5 vs 0.0.1
A authority física apresenta duas identidades simultâneas:
- filename/publicação: **0.0.5**;
- metadata runtime interno: **0.0.1**.

Isso pode resultar de metadata não atualizada pelo autor durante builds posteriores. Sem inspeção/source byte-equivalente que prove a causa, a ficha não “corrige” o manifest por conta própria.

Regra operacional:
- `Arquivo JAR` conserva 0.0.5;
- `Versão 1.21.1` conserva o runtime literal 0.0.1;
- status/observações mantêm ambos para evitar falso alinhamento.

## 3. Dependência e ownership de Iron's Spells
O projeto é explicitamente um addon de **Iron's Spells 'n Spellbooks**, que está presente no pack.

Iron's continua authority de:
- spell framework;
- mana/cooldowns/casting;
- schools e spellbook infrastructure;
- atributos mágicos base.

Ozymandias é authority apenas do conteúdo adicional que registra sobre essa infraestrutura.

## 4. Sanctified Sword
A descrição oficial lista **Sanctified Sword** como arma associada a Holy spell power e à habilidade/spell **Divine Smite**.

Para balanceamento, separar:
- stats físicos da arma;
- bônus de school power;
- efeito/spell disparado;
- atributos globais do player.

Não assumir valores numéricos sem registry/data da build.

## 5. Sacrificial Kris
**Sacrificial Kris** está ligado a Blood spell power e **Sacrifice**. Como Blood magic pode envolver custos/efeitos especiais no ecossistema Iron's, testar lifecycle de uso, dano/custo e interactions sem inferir números não publicados.

## 6. Sculk Greatsword
**Sculk Greatsword** fornece afinidade com Eldritch spell power e **Sculk Tentacles** segundo a descrição oficial.

É relevante para composição com outros addons Eldritch e mobs/gear que também modificam school power.

## 7. Permafrost Axe
**Permafrost Axe** é associada a Ice spell power e **Ray of Frost**.

Testar efeitos de slow/freeze/damage conforme implementação real e stacking com outros efeitos de gelo do pack; o item não deve ser confundido com provider do spell framework.

## 8. Cinderous Scimitar
**Cinderous Scimitar** associa Fire spell power e **Flaming Strike**. Em pack com múltiplos sistemas de fogo/burning, validar que hit/spell executem uma vez e que atributos não sejam duplicados ao equipar/relogar.

## 9. Druidic Scythe
**Druidic Scythe** está ligada a Nature spell power e **Root**. Como efeitos de root/immobilization podem cruzar movement/AI mods, validar server authority e cleanup do efeito em multiplayer.

## 10. Ender Glaive
**Ender Glaive** fornece Ender spell power e **Dragon's Breath** conforme catálogo oficial.

Efeitos de teleport/dimension ou breath pertencem ao spell correspondente; não atribuir ao item comportamentos adicionais não publicados.

## 11. Levin Sword
**Levin Sword** está associada a Lightning spell power e **Chain Lightning**. Testar targeting/chain hit em grupos para evitar double-hit quando outros combat hooks estiverem ativos.

## 12. Spectral Greatsword
**Spectral Greatsword** oferece Evocation spell power e **Shield**. O shield/spell deve ser tratado segundo o framework Iron's e não como substituto automático de sistemas de block/guard do Epic Fight.

## 13. Mithril Spellblade e Stormcaller Trident
A descrição também publica:
- **Mithril Spellblade** — bônus genérico de spell power;
- **Stormcaller Trident** — lightning spell power + **Thunderstorm**, explicitamente **unobtainable/creative-only** no conteúdo publicado.

O Stormcaller Trident não deve ser colocado em loot/quests como item de progressão normal sem decisão deliberada do pack.

## 14. Spellbooks
Dois spellbooks são descritos:
- **Fulminous Folio** — foco em Lightning;
- **Libram of Flesh** — foco em Blood.

Os slots/tiers/valores exatos não são inventados sem dados da build. Para progressão, comparar seu acesso com outros spellbooks e addons Iron's existentes no pack.

## 15. Spells próprios
### Levitate
Spell Ender que aplica levitation em área ao redor do alvo conforme descrição oficial. Testar duração/área reais a partir da build, não de inferência.

### Lightning Warp
Spell Lightning recastable de teleport que cria um lightning bolt não-damaging no destino segundo documentação pública. Superfícies críticas:
- posição de destino válida;
- recast state;
- cooldown/mana;
- chunk/dimension safety;
- servidor mantendo authority do teleport.

## 16. Conteúdo planejado não é conteúdo instalado
A página do projeto menciona **novos spells e mobs como planejados**. Planejado ≠ registrado na 0.0.5.

Esta ficha não cataloga mobs próprios como ativos sem evidência concreta no JAR/publicação. Isso evita transformar roadmap em conteúdo de gameplay.

## 17. Equipamento, attributes e school power
Como vários itens concedem school power, o risco principal no pack é stacking com:
- outros addons de Iron's;
- spell power de armor/accessories;
- AttributeFix/caps;
- perks/skills globais.

Equip/unequip/relog devem remover e reaplicar modifiers exatamente uma vez.

## 18. Client/server e lifecycle
Server-authoritative:
- dano e spell cast válido;
- mana/cooldown;
- teleport;
- status effects;
- modifiers funcionais de equipamento.

Client-facing:
- models/textures;
- spell particles/animation;
- tooltip/presentation.

Lifecycle crítico: equip/unequip, spell cast/recast, logout/reconnect, death, dimension change e restart.

## 19. Sobreposição no ecossistema Iron's
O pack contém vários addons de Iron's. Ozymandias deve ser avaliado por conteúdo concreto:
- school-power gear;
- spell duplication temática;
- acesso a teleport/root/lightning;
- power curve de weapons/spellbooks.

“Outro addon de magia” não é razão suficiente para remover, mas redundância de um spell/gear específico pode justificar curadoria futura.

## 20. Riscos
1. **Version metadata mismatch:** filename 0.0.5 versus runtime 0.0.1 pode confundir dependency/version checks e diagnóstico.
2. **Attribute stacking:** school-power modifiers podem duplicar após lifecycle bugs.
3. **Power creep:** várias armas combinam melee + spell power + spell ability.
4. **Teleport safety:** Lightning Warp exige destino/authority coerentes.
5. **Effect cleanup:** Levitate/Root e outros efeitos devem encerrar corretamente.
6. **Creative-only item:** Stormcaller Trident não deve entrar acidentalmente na progressão normal.
7. **Addon overlap:** outros addons Iron's podem oferecer efeitos/equipamentos equivalentes.
8. **Roadmap confusion:** mobs/spells planejados não devem ser tratados como existentes.
9. **No exact source pin:** internals de registry/network/data ficam deliberadamente fora da ficha.

## 21. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Ozymandias + Iron's atual sem version-check inesperado causado pelo runtime 0.0.1.
- [ ] Cada weapon publicada registra e aparece com atributos corretos.
- [ ] Equip/unequip/relog não duplica school-power modifiers.
- [ ] Divine Smite, Sacrifice, Sculk Tentacles, Ray of Frost, Flaming Strike, Root, Dragon's Breath, Chain Lightning e Shield executam uma vez por ativação válida quando vinculados aos itens.
- [ ] Fulminous Folio e Libram of Flesh mantêm spells/dados após restart.
- [ ] Levitate aplica/remove efeito corretamente em multiplayer.
- [ ] Lightning Warp seleciona destino seguro, consome recurso correto e recast funciona sem duplo teleport.
- [ ] Stormcaller Trident permanece fora de survival progression salvo customização explícita.
- [ ] Death/reconnect/dimension change não deixam modifiers/effects persistentes indevidos.
- [ ] Comparar power curve com outros addons Iron's antes de liberar loot/quests.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 22. Evidências e limites
- Modlist física: `ozymandias_sundries-0.0.5.jar`, mod id `ozymandias_sundries`, metadata runtime `0.0.1`, hash `b973622ed90f47108aba481fec3beb19b432d853`.
- CurseForge oficial: project 1209674, file ID 6978561, Release `ozymandias_sundries-0.0.5.jar` de 09/09/2025, Client & Server, MIT.
- Página oficial: weapons, spellbooks e spells descritos acima; Stormcaller Trident creative-only; novos spells/mobs indicados como planejados.
- Iron's Spells 'n Spellbooks: provider/base do addon e presente fisicamente no pack.
- **Limite:** não foi estabelecido source byte-equivalente que explique o manifest 0.0.1 dentro da release 0.0.5; a divergência permanece registrada em vez de ser corrigida por inferência.
