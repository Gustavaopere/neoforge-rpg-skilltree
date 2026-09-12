# Fire's Ender Expansion — 2.4.1

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81dc948fea6713a72363  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** Fire's Ender Expansion
- **Arquivo JAR:** `firesenderexpansion-2.4.1.jar`
- **Versão 1.21.1:** `2.4.1`
- **Categoria:** Magia; RPG; Worldgen
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/fires-ender-expansion
- **Função:** Addon de Iron's Spells 'n Spellbooks focado no End, com novos spells, estruturas, armaduras, Curios e conteúdo/NPCs ligados à temática Ender.
- **Dependências:** Iron's Spells 'n Spellbooks. Source pin 2.4.1 usa baseline Iron's 1.21.1-3.15.6; pack usa 3.16.3, portanto recast, projectile, damage source, SpellPreCastEvent e domain integration são regression gates.
- **Compatibilidade/Riscos:** Source matching confirma 11 spells ativos apesar de CurseForge ainda descrever 10. Riscos: Iron's 3.15.6→3.16.3 drift, double teleport/domain/recast, Nova Burn amplifier edge case, Scintillating Stride filtering, Infinite Void fallback, projectile/event API drift.
- **Sobreposição:** Contracts próprios Ender/domain/teleport/damage; outros addons Ender não são substitutos por tema. Bridges devem preservar provider-native authority e não duplicar Anchored, recasts, domain transport, projectile hits ou effects.
- **Observações:** Hierarquia fail-closed: source matching registra 11 spells ativos; descrição pública ainda diz 10. Binary Stars tem teleport/slam AOE comentado no pin; Nova Burn/amplifier, Scintillating Stride filtering e Infinite Void fallback 0,100,0 permanecem QA gates.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `firesenderexpansion-2.4.1.jar`, mod id `firesenderexpansion`, versão 2.4.1 e SHA-1 da87a4a0c926dd75236766584cddbae69c6aeff7. Source matching pin `FireOfPower/firesenderexpansion-1.21.1@5e4067e8112316f55c9f249530ba1917a7bf6643` auditado.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Fire's Ender Expansion 2.4.1 source-pinned; 11 active spells, domain/teleport/damage contracts, public-doc drift, Iron's version drift, lifecycle, riscos e testes catalogados.
- **Data da última decisão:** 2026-09-06

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `firesenderexpansion-2.4.1.jar` · mod id `firesenderexpansion` · versão `2.4.1` · NeoForge 1.21.1 · Client & Server. Source matching previamente pinado em `FireOfPower/firesenderexpansion-1.21.1@5e4067e8112316f55c9f249530ba1917a7bf6643`.

## 1. Papel no modpack
Fire's Ender Expansion é um addon de Iron's Spells 'n Spellbooks focado em magia Ender e conteúdo do End. Ele adiciona spells, estruturas, curios, armor e NPC/conteúdo temático, mantendo o Iron's como framework de casting/mana/cooldown.

## 2. Authority / ownership
- **Iron's Spells:** spell framework, school plumbing, mana, cooldown, spell containers e casting lifecycle.
- **Fire's Ender Expansion:** spells, effects, entities, structures, curios/armor e regras específicas que registra.
- **Minecraft/worldgen:** lifecycle de chunks/structures.

Bridges externas não devem duplicar Anchored, recasts, domain transport, projectile hits ou spell damage.

## 3. Contagem de spells — divergência documentada
A descrição pública do CurseForge ainda fala em **10 Ender spells**, porém a auditoria do source pin correspondente à linha instalada identificou **11 spells ativos**, todos Ender. Pela hierarquia canônica, o source matching tem precedência sobre a descrição pública desatualizada.

Essa divergência fica registrada em vez de forçar os dois números a coincidir.

## 4. Contracts de spells source-pinned
A auditoria anterior do mesmo commit confirmou contratos como:
- damage dependente de mana do alvo;
- proc de Shulker em spell hit;
- Hollow Crystal com charge/recast;
- adaptação por dimensão;
- `Anchored` como anti-teleport;
- `Manifest Domain: Void` com transporte/clash/sure-hit;
- melee Ender escalado por weapon;
- swarm de portal weapon;
- prison ligada a teleport boundary;
- Binary Stars com dual debuff;
- dash com retorno.

Esses contracts são mais úteis para integração que uma lista temática de nomes.

## 5. Source architecture
A árvore pinada contém `VoidDimensionManager`, `NovaBurnDamageSource`, `VoidSureHitDamageSource`, matcher de effects por dimensão, config/common + client config, compat JEI e subsistemas de spell/entities. Isso confirma que o addon possui state/transport/damage próprios além de partículas cosméticas.

## 6. Edge cases já auditados
No source pin:
- o teleport/slam AOE de **Binary Stars** aparece comentado, portanto não deve ser documentado como efeito ativo;
- **Nova Burn** é aplicado com amplifier 0 embora um cálculo de dano use amplifier diretamente, ponto a testar;
- o blast de **Scintillating Stride** não possui filtro provider-side explícito de self/allies no trecho auditado;
- **Infinite Void** possui fallback de retorno ao Overworld em `0,100,0` se a origem falhar.

Esses pontos são riscos QA, não afirmação de bug reproduzido.

## 7. Conteúdo adicional publicado
A página oficial divulga duas estruturas, três curios, um Ender Mage/NPC e um novo armor set além das spells. O registry físico/source deve ser consultado antes de codificar IDs concretos em integrações próprias.

## 8. Version drift com Iron's Spells
O source pin 2.4.1 foi desenvolvido contra Iron's Spells `1.21.1-3.15.6`, enquanto o pack usa **3.16.3**. Isso alcança `AbstractMagicProjectile`, damage sources, `SpellPreCastEvent`, recast/casting e event contracts; é regression gate obrigatório, não incompatibilidade automática.

## 9. Domain / teleport authority
Mechanics de domínio e teleporte precisam de uma única authority. Fire's Ender Expansion deve decidir entrada/saída/retorno de seu domain; sistemas externos podem reagir, mas não executar um segundo teleport ou manter origem paralela.

## 10. Effects e damage
Damage/effects custom são server-authoritative. Nova Burn, sure-hit e efeitos de Ender devem produzir exatamente um settlement por evento. Particles, trails e client animation apenas representam o state.

## 11. Configuração e dados
O source possui `Config` e `ClientConfig`; valores concretos precisam vir dos arquivos reais da instância. Structures/loot/spell resources e tags devem sobreviver a `/reload` sem IDs stale.

## 12. Client / Server
**Servidor:** cast validation, recast state, damage/effects, teleport/domain, entities, loot/worldgen e equipment effects.

**Cliente:** animation, particles, sounds, GUI/JEI e renderers.

Nenhuma apresentação client-side pode decidir hit, teleport ou damage.

## 13. Lifecycle
Validar cast start/cancel/finish, recast, death/respawn, logout durante domain/teleport, dimension change, projectile unload, chunk unload, server restart, structure generation, `/reload` e update do Iron's.

## 14. Multiplayer
Teleport/domain precisa sincronizar target/owner/origin corretamente. Dois clients não podem disparar a mesma recast/damage duas vezes. PvP/team/self filtering deve ser testado explicitamente nas AOE relevantes.

## 15. Integrações no pack
Iron's Spells 3.16.3 é base direta. Epic Fight/animation compats podem tocar apresentação/weapon attacks; Explosive Enhancement toca apenas apresentação de explosões. Outros addons Ender não são equivalentes por tema e não devem sobrescrever spell IDs/effects.

## 16. Riscos
1. version drift 3.15.6→3.16.3;
2. double teleport/domain settlement;
3. recast state stale após logout/death;
4. Nova Burn amplifier edge case;
5. Scintillating Stride atingir self/allies em cenário não esperado;
6. Infinite Void usar fallback 0,100,0;
7. Binary Stars ser documentado com efeito comentado/inativo;
8. projectile API drift;
9. spell event order mudar no Iron's;
10. public docs 10 spells vs source 11 causar catálogo errado;
11. structure/worldgen drift em chunks novos;
12. outro compat aplicar damage/effect novamente.

## 17. Matriz de testes
1. Dedicated server com Iron's 3.16.3.
2. Smoke-test dos 11 spell IDs do source pin.
3. Hollow Crystal charge/recast e cancel.
4. Anchored contra múltiplos teleports.
5. Manifest Domain: Void enter/exit/clash/return.
6. Infinite Void com origem válida e cenário de fallback controlado.
7. Nova Burn amplifier/damage.
8. Scintillating Stride com self/ally/enemy.
9. Binary Stars validando apenas comportamento realmente ativo.
10. Death/logout/dimension change durante state persistente.
11. Multiplayer com dois observers e uma única liquidação por hit.
12. Structures/loot em chunks novos.

**Esta catalogação não afirma que esses testes foram executados.**

## 18. Evidências
- modlist física canônica: JAR/mod id/version/hash;
- source matching pin `5e4067e...`: registry/architecture/contracts e edge cases auditados;
- CurseForge oficial 2.4.1: release NeoForge 1.21.1 e conteúdo divulgado;
- Iron's Spells físico 3.16.3 para registrar version drift real.

> **Boundary canônico:** Fire's Ender Expansion é authority dos **spells/contents Ender que registra**; Iron's Spells continua authority do framework de casting, mana e cooldown.