# Iron's Lib — versão 1.21.1-2.4.0 (atualização declarada em 22/11/2026)

> **Estado vigente — 22/11/2026:** versão `1.21.1-2.4.0`, JAR oficial `irons_lib-1.21.1-2.4.0.jar`, **instalação declarada pelo usuário**; [CurseForge file 9687938](https://www.curseforge.com/minecraft/mc-mods/irons-lib/files/9687938). Posição histórica preservada. A confirmação deriva do pedido, não de novo dump do diretório local.

> **Mudanças e dependências:** Releases 2.1.1, 2.2.0, 2.3.0 e 2.4.0 documentadas: Kinetic Weapon System/Projectile Damage, transmogs e novas poses de estátuas, receita de Stonecutter, rolagem da interface e correção para servidores de longa duração. Sincronizar consumers Iron's Spells por lote; sem impedir registro documental. Notas anteriores sobre 'não instalado' descrevem o estado **anterior a esta sincronização**; não substituir por alegação de smoke test.


## Propriedades do registro

- **Mod:** Iron's Lib
- **Arquivo JAR instalado (declarado pelo usuário):** `irons_lib-1.21.1-2.4.0.jar` — arquivo oficial CurseForge 9687938.
- **Versão instalada declarada (Minecraft 1.21.1 / NeoForge):** `1.21.1-2.4.0`; declaração expressa do usuário em 22/11/2026.
- **Categoria:** Biblioteca, RPG
- **Decisão:** Sem decisão
- **Estado da pesquisa:** histórico incremental já auditado e versão de distribuição identificada; execução do pack e compatibilidade global não testadas.
- **Estado no pack:** `1.21.1-2.4.0` instalado **conforme declaração do usuário**, sincronizado no inventário documental GitHub; JAR novo não lido diretamente no computador.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/irons-lib
- **Função:** Biblioteca/framework comum do ecossistema Iron's, fornecendo atributos RPG compartilhados, remapeamento data-driven de atributos, infraestrutura de transmogs e estátuas/multiblocos, além de APIs reutilizáveis para mods consumidores.
- **Dependências:** NeoForge 1.21.1. GeckoLib é required dependency oficial; pack usa GeckoLib 4.9.2. Consumers físicos incluem Iron's Spells 3.16.3 e Iron's Gems 'n Jewelry 2.0.2; Iron's Apothic 2.2.1 também referencia Iron's Lib 2.1.0.
- **Compatibilidade/Riscos:** Library/API central. Riscos: ABI drift em consumers, attribute remap duplicado/cíclico, modifiers acumulados em equip/relog, data remap inválido, transmog/render drift, statue/multiblock state e sobreposição de atributos com Apothic Attributes.
- **Sobreposição:** Não substitui Apothic Attributes ou outras libraries. Possui compatibilidade nativa de remapeamento com Apothic Attributes, mas cada provider continua owner de seus atributos; evitar manter duas regras equivalentes de remap/modifier.
- **Observações (estado vigente declarado):** distribuição `irons_lib-1.21.1-2.4.0.jar` / `1.21.1-2.4.0`, CurseForge 9687938. Antigos nomes de JAR, hashes e versões descritos nas demais seções representam snapshots históricos, não o pedido atual.
- **Procedência:** posição física herdada do snapshot de setembro de 2026; versão vigente declarada no pedido de 22/11/2026, filename de [CurseForge, arquivo 9687938](https://www.curseforge.com/minecraft/mc-mods/irons-lib/files/9687938). Sem hash da cópia local nova ou teste runtime.
- **Atualização/Status:** 22/11/2026 — `1.21.1-2.1.0` → `1.21.1-2.4.0` confirmado pelo pedido do usuário para atualização do inventário. Releases 2.1.1, 2.2.0, 2.3.0 e 2.4.0 documentadas: Kinetic Weapon System/Projectile Damage, transmogs e novas poses de estátuas, receita de Stonecutter, rolagem da interface e correção para servidores de longa duração. Sincronizar consumers Iron's Spells por lote; sem impedir registro documental.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #341: JAR `irons_lib-1.21.1-2.1.0.jar`, mod id `irons_lib`, runtime `1.21.1-2.1.0`, SHA-1 `70fba64d12b6ff9553580e52101d989c89297797`.

<callout icon="🧩" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `irons_lib-1.21.1-2.1.0.jar`, mod id `irons_lib`, versão `1.21.1-2.1.0`. A publicação oficial para NeoForge 1.21.1 é Release de 03/07/2026. Source público exato da build não foi localizado, portanto esta ficha é release/documentation-pinned e mantém internals não publicados em fail-closed.
</callout>
## 1. Papel e authority
Iron's Lib é a biblioteca compartilhada dos mods Iron's. Ela fornece frameworks e conteúdo-base reutilizável, mas não é owner do gameplay específico de Iron's Spells ou Iron's Gems 'n Jewelry. Consumers continuam authority de seus spells, joias, recipes e progressão.
## 2. Dependências e consumers físicos
GeckoLib é required dependency oficial; o pack usa GeckoLib 4.9.2. Consumers físicos relevantes incluem Iron's Spells 3.16.3 e Iron's Gems 'n Jewelry 2.0.2; Iron's Apothic 2.2.1 também foi desenvolvido contra Iron's Lib 2.1.0. Remover ou atualizar a library exige regression test conjunto.
## 3. Atributos RPG
A documentação oficial da linha 1.21.1 introduz sete atributos comuns: **Armor Pierce, Mining Speed, Experience Gained, Arrow Damage, Crit Damage, Dodge Chance e Healing Received**. Eles são contracts compartilhados; sistemas externos não devem criar atributos paralelos equivalentes só para um consumer.
## 4. Attribute Remapper
O framework permite remapear atributos de itens para melhorar interoperabilidade. A documentação expõe registro por código via `AttributeRemapRegistry#register` e suporte data-driven em `irons_lib_attribute_remap`, com campos `from` e `to` representando resource IDs de atributos. Remaps devem ser acíclicos e apontar para registries existentes.
## 5. Compatibilidade com Apothic Attributes
Iron's Lib anuncia compatibilidade nativa com Apothic Attributes. Isso não torna as duas libraries equivalentes: Apothic continua owner de seus attributes; Iron's Lib controla a camada de remapeamento/uso que expõe. Evitar mapear A→B e B→A ou aplicar dois modifiers semanticamente iguais.
## 6. Transmogs
O framework de transmogs permite armor models próprios, interfaces/customização de armor e cape physics automáticas. A linha 2.x inclui transmogs temáticos publicados pelo projeto. Transmog é presentation/equipment integration; não deve alterar stat authority sem regra explícita do consumer.
## 7. Statues e multiblocos
A library fornece geração dinâmica de assets para player statues, handling de multiblocos e static model rendering. Placement, estrutura e persistência precisam sobreviver a chunk unload/restart; render client-side não pode ser a única fonte de existência do multiblock.
## 8. Patreon Integration API
O projeto também expõe serviços de integração Patreon para seus frameworks. Identidade/entitlement externo não deve ser usado por projetos do pack para conceder progressão de gameplay sem contrato explícito e server validation.
## 9. Client / server
A distribuição oficial é Client & Server. Attributes, remaps e state persistente de estruturas/equipamento são server-authoritative. Models, cape physics, transmog rendering e assets de statues pertencem ao cliente. Dedicated server não deve carregar classes gráficas por path comum indevido.
## 10. Lifecycle
Validar construction/registry, data load/reload, equip/unequip, modifier recalculation, death/respawn, reconnect, statue placement/removal, chunk unload e server restart. Atualização da library é mudança potencial de ABI/data para todos os consumers.
## 11. Riscos técnicos
- ABI drift entre Iron's Lib e consumers;
- remap para atributo inexistente;
- ciclo/dupla aplicação de remap;
- modifier acumulado após equip/relog;
- stacking excessivo com Apothic Attributes;
- transmog/model/cape render regression;
- statue/multiblock incompleto após unload;
- client-only class em dedicated server;
- usar documentação de outra build como se fosse source exato.
## 12. Matriz de testes obrigatória
- [ ] Dedicated server e cliente iniciam com Iron's Lib 2.1.0 + consumers atuais.
- [ ] Sete atributos registram/sincronizam sem duplicate ID.
- [ ] Attribute Remapper resolve `from`→`to` válido uma única vez.
- [ ] Remap inválido falha de modo diagnosticável.
- [ ] Equip/unequip/relog não acumula modifiers.
- [ ] Apothic Attributes compõe sem double-stat.
- [ ] Transmogs renderizam sem alterar stats indevidamente.
- [ ] Statue/multiblock persiste após chunk unload/restart.
- [ ] Dedicated server não carrega paths gráficos.
- [ ] Update futuro da library é bloqueado até smoke test dos consumers.
## 13. Evidências e limites
- **Modlist física:** JAR/mod id/version exatos.
- **CurseForge/Modrinth oficiais:** Release 1.21.1-2.1.0, Client & Server e GeckoLib required.
- **Documentação oficial:** atributos, Attribute Remapper, transmogs, statues e Patreon Integration.
- **Limite:** source code exato da build 2.1.0 não foi localizado; classes/registries além dos publicamente documentados não foram inventados.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

## 15. Histórico upstream 2.1.0 → 2.2.0 — não instalado
A authority física continua em **Iron's Lib 2.1.0**. Foram revisadas as duas releases posteriores publicadas para NeoForge 1.21.1.

### 2.1.1 — 21/09/2026
O changelog publicado registra:
- **Improved Logging**;
- **Optimized Memory**.

Há também um **bug report público upstream** aberto no mesmo ciclo alegando que a build 2.1.1 teria sido publicada sem reobfuscation e poderia falhar no carregamento com `NoSuchFieldError: STRUCTURE_PROCESSOR`. Isso é tratado como relato upstream, não como reprodução local confirmada. Como a 2.2.0 já é posterior, não há razão documental para promover 2.1.1 isoladamente sem reproduzir/validar esse artefato.

### 2.2.0 — 25/09/2026
Adiciona um **Kinetic Weapon System** compartilhado:
- charge attacks;
- data components `kinetic_weapon`, `use_effects` e `attack_range`;
- animações de spear;
- client animation registry;
- qualquer stack com `kinetic_weapon` pode carregar ao usar, sem exigir override específico do item.

Também adiciona:
- visual de **Soul Fire burning** via `SoulFireHelper`; a próxima queima pode usar aparência azul/soul fire e o estado visual é resetado por outro fire/burnout;
- novo atributo **Projectile Damage**, aplicável genericamente a projéteis, mantendo **Arrow Damage** como atributo separado.

Impacto no pack: Iron's Spells, Iron's Gems 'n Jewelry, Iron's Apothic, Spell Actionbar e vários addons consomem Iron's Lib; além disso, Epic Fight/Punchy/First-person Model/NotEnoughAnimations e o RPG Skill Tree tornam kinetic weapons/animations e o novo atributo surfaces de compatibilidade reais. Não mapear automaticamente `Projectile Damage` para `Arrow Damage` nem somar os dois sem contrato explícito.

Gate de regressão: dedicated-server boot, kinetic charge/cancel/release, spear animations em primeira/terceira pessoa, data-component serialization, Soul Fire visual state, projectile damage de arrows e não-arrows, Apothic attributes/remapper, multiplayer sync e todos os consumers Iron's instalados.

Fontes upstream: CurseForge Iron's Lib 2.1.1 e 2.2.0 para NeoForge 1.21.1; issue pública `iron431/Irons-Spells-n-Spellbooks#1270` usada apenas como evidência do relato sobre a 2.1.1.

> **Atualização/Status — valor histórico preservado do Notion:** PADRÃO ALEX'S MOBS REVALIDADO EM 12/09/2026 — Iron's Lib 1.21.1-2.1.0/JAR físico reconfirmado; 2.1.0 permanece a release NeoForge 1.21.1 mais recente localizada. Atributos RPG, Attribute Remapper, transmogs, statues/multiblocks, Apothic boundary, lifecycle, riscos e testes preservados.

## 16. Auditoria incremental 2.1.0 → 2.3.0 (17/11/2026)

**Baseline físico:** #341, `irons_lib-1.21.1-2.1.0.jar`, versão runtime `1.21.1-2.1.0`. O **2.3.0 é candidato não instalado**. Projeto oficial CurseForge **1492763**, autor Iron431; categorias oficiais `API and Library + Cosmetic`, pasta correta.

| Release NeoForge 1.21.1 | Data | Changelog e relevância | Fonte |
|---|---|---|---|
| **2.1.1** | 21/09/2026 | Logging aprimorado, uso de memória otimizado; pendência histórica de possível falha de reobfuscation já qualificada como relato externo no dossier, não como teste local. | [Lista oficial de releases](https://www.curseforge.com/minecraft/mc-mods/irons-lib/files/all) |
| **2.2.0** | 25/09/2026 | **Kinetic Weapon System** (charge attack, `kinetic_weapon`/`use_effects`/`attack_range`, spear animations), **SoulFireHelper** e atributo **Projectile Damage**, distinto de Arrow Damage. Já detalhado na seção 15. | [CurseForge 8973642](https://www.curseforge.com/minecraft/mc-mods/irons-lib/files/8973642) |
| **2.3.0** | 25/10/2026 | Adiciona **Autumn Knight Transmog** e **Pumpkin Heads Transmog**. O changelog próprio não anuncia outras APIs ou balanceamento; não atribuir mudanças não publicadas. | [CurseForge 9328060](https://www.curseforge.com/minecraft/mc-mods/irons-lib/files/9328060) |

**Cadeia publicada NeoForge 1.21.1 após a instalada:** 2.1.1 → 2.2.0 → 2.3.0. Gates antes de instalar: validar manifest e compatibilidade com Iron's Spells, seus addons e RPG Skill Tree; Kinetic Weapons e charges/iframes, atributos de projectile (sem confundir Arrow Damage), transmogs, serialização, rendering, client e dedicated server. Nenhum teste nem atualização de JAR foi realizado; auditoria documental até **2.3.0 concluída**, nome do dossier v1.21.1-2.1.0 preservado.

## Auditoria incremental upstream — Iron's Lib 2.4.0 (22/11/2026; não instalada)

**Versão física** #341: `irons_lib-1.21.1-2.1.0.jar` / `1.21.1-2.1.0`. O dossier anterior já audita as versões NeoForge 1.21.1 **2.1.1 → 2.2.0 → 2.3.0**.

A [release **2.4.0**, CurseForge 9687938](https://www.curseforge.com/minecraft/mc-mods/irons-lib/files/9687938), de **18/11/2026**, compatível com o alvo, documenta:
- **Novas poses de estátua:** `Salute`, `Scout Salute`, `Dinnerbone`, `Him`, `Dafoe I`, `Dafoe II`, `Mantis` e `Perched`.
- **Receita de Patreon Statue** a partir de `Chiseled Stone Bricks` no **Stonecutter**.
- **Melhoria da rolagem** da tela de seleção de poses das estátuas.
- Correção de um **problema em servidores com uptime prolongado**; o changelog não detalha o mecanismo, e não é seguro atribuir causa específica a memory leak ou networking.

**QA:** recipe/Stonecutter, poses, assets/transmog, rolagem e escala de GUI, save/reload de estátuas e restart/uptime; possíveis consumers Iron's Spells, Iron's Apothic e Gems n' Jewelry, além de atributos e compatibilidade ABI. Inspecionar ranges dos JARs candidatos antes de atualizar. **Nenhuma instalação/QA foi executada; pastas e versão física inalteradas.**


## Sincronização declarada da instalação — 22/11/2026

**Atualização documental:** `1.21.1-2.1.0` → `1.21.1-2.4.0`; JAR oficial `irons_lib-1.21.1-2.4.0.jar`; registro da versão como instalada por **declaração expressa do usuário**. As seções históricas sobre a versão antiga permanecem para rastreabilidade, mas seus termos 'atual'/'não instalado' são anteriores ao pedido.

**Resumo técnico auditado:** Releases 2.1.1, 2.2.0, 2.3.0 e 2.4.0 documentadas: Kinetic Weapon System/Projectile Damage, transmogs e novas poses de estátuas, receita de Stonecutter, rolagem da interface e correção para servidores de longa duração. Sincronizar consumers Iron's Spells por lote; sem impedir registro documental.

**Requisitos entre lotes:** divergências de dependências no inventário escrito devem ser revisadas quando o respectivo provider chegar à sua vez, sem bloquear a atualização documental deste item. Não afirmar que o conjunto inteiro foi executado/testado ou que o JAR novo foi obtido da instalação local.

**Fonte da distribuição:** https://www.curseforge.com/minecraft/mc-mods/irons-lib/files/9687938
