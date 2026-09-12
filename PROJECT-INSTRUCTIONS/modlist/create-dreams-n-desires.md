# Create: Dreams n' Desires — 2.3a-BETA

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db816897e0fee487ee2c0b  
> Estado no momento da exportação: `Integrado ao Github`  
> Autoridade física no momento da exportação: `modlist(4).txt`, 595 mods top-level  
> Exportado/reconciliado em: 2026-09-09

## Propriedades do registro

- **Mod:** Create: Dreams n' Desires
- **Arquivo JAR:** `DnDesires-1.21.1-2.3a-BETA.jar`
- **Versão 1.21.1:** `2.3a-BETA`
- **Categoria:** Tecnologia; QoL; Automação
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://github.com/LopyLuna/Create-Dreams-and-Desires/tree/1.21.1
- **Função:** Addon amplo de Create com máquinas, componentes cinéticos, fluid/inventory utilities, itens, alimentos e bridges específicas, mantendo Create como authority de kinetics/stress/contraptions.
- **Dependências:** Source 2.3a-BETA: Minecraft 1.21.1, NeoForge >=21.1.200, Create 6.0.10-280, Flywheel 1.0.6, Ponder 1.0.82, Registrate MC1.21-1.3.0+67. JEI/Curios e Sable aparecem como superfícies de desenvolvimento/integração; compat Sable é explicitamente mencionada no changelog.
- **Compatibilidade/Riscos:** Stress double-count, Smart Hopper dupe/loss, fluid state stale/duplicado, Gold Mixer crash regression, Bore hitbox, BE state em contraptions/Sable, packet-side errors e version drift com Create/Flywheel/Ponder/Sable.
- **Sobreposição:** Cruza superfícies de automação/decoração com outros addons Create, sem equivalência automática. Create continua dono de stress/kinetics/contraptions; D&D controla apenas seu conteúdo e adapters.
- **Observações:** Filename/runtime usa `2.3a-BETA`; isso é identificador da versão, enquanto a plataforma de distribuição pode classificar o arquivo com outro release type. 2.3a-BETA adiciona Spud Sentry e registra fixes para Smart Hopper, fluids, Bore hitbox e Gold Mixer.
- **Procedência:** Runtime: modlist física canônica de 08/09/2026 (595 top-levels), `DnDesires-1.21.1-2.3a-BETA.jar`. Source pin: repo oficial LopyLuna/Create-Dreams-and-Desires branch 1.21.1, cujo `gradle.properties` declara 2.3a-BETA; registries/items/blocks e changelog oficial da mesma release auditados.
- **Histórico da decisão:** Sem decisão curatorial formal. Em 08/09/2026, a ficha foi reconstruída contra o runtime físico 2.3a-BETA e source oficial da mesma versão.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — source 2.3a-BETA pinado; registries, machines/items, kinetics/stress, fluids, Sable, lifecycle/MP, regressões e testes catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `DnDesires-1.21.1-2.3a-BETA.jar` · mod id `dndesires` · versão `2.3a-BETA` · NeoForge 1.21.1. O branch oficial `1.21.1` declara a mesma `mod_version`.

## 1. Papel no modpack
Create: Dreams n' Desires (D&D) é um addon amplo de **Create** que acrescenta máquinas, componentes cinéticos, utilidades, blocos, itens e conteúdo de processamento/automação. O addon registra state próprio, mas o kinetic network, stress, speed e contratos gerais de contraption permanecem sob Create.

## 2. Source pin e baseline técnico
O source oficial `LopyLuna/Create-Dreams-and-Desires`, branch `1.21.1`, declara:
- Minecraft `1.21.1`;
- NeoForge `21.1.226` com range mínimo `21.1.200`;
- Create `6.0.10-280`;
- Flywheel `1.0.6` com range `[1.0.5,2.0)`;
- Ponder `1.0.82`;
- Registrate `MC1.21-1.3.0+67`;
- JEI `19.21.0.247` e Curios `9.2.2` em desenvolvimento/integração;
- Sable `1.2.2`, com `1.2.1` indicado no build de bundle;
- `mod_id=dndesires` e `mod_version=2.3a-BETA`.

Esses números descrevem o baseline do source. O runtime do modpack deve ser validado contra a modlist física, não rebaixado ao buildscript.

## 3. Authority / ownership
- **Create:** kinetics, stress network, rotational speed, base processing e contraption contracts.
- **D&D:** seus blocos, block entities, itens, fan processing types, packets, configs e regras específicas.
- **Sable:** física/sublevels quando uma bridge D&D/Sable atua.
- **Curios/JEI:** apenas seus contracts quando integração correspondente está presente.

Nenhum mod próprio deve recalcular stress ou processar novamente uma operação já resolvida por um bloco D&D.

## 4. Registries confirmados
O branch 1.21.1 possui classes de registro dedicadas para:
- blocks;
- block entity types;
- items;
- fluids;
- attributes;
- configs;
- packets;
- fan processing types;
- creative tabs;
- lang/data helpers.

O catálogo de blocos é amplo; esta ficha enumera subsistemas/entradas confirmadas sem inventar IDs fora do source pinado.

## 5. Blocos e máquinas confirmados
`DesiresBlocks` confirma, entre outros:
- **Overburden Casing**;
- **Industrial Casing**;
- **Industrial Fan**;
- **Cog Crank** e **Large Cog Crank**;
- **Multimeter**;
- **Smart Hopper**;
- **Creative Gear Motor**;
- **Fluid Gauge**;
- **Fluid Hatch**;
- **Roll Table**;
- **Bore Block**;
- **Fan Sail**;
- **Inverse Gearshift**;
- **Omni Gearbox**;
- **Omni Speed Controller**;
- **Golden Mixer**;
- **Hydraulic Press**;
- **Spud Sentry**;
- **Stirling Engine** e powered-flywheel related content.

A lista não é declarada exaustiva: o registry real é maior e deve ser consultado diretamente para integração por ID.

## 6. Kinetics e stress
O Industrial Fan registra valores de stress no source: **impact 4** e **capacity 16** na camada de registro correspondente. Esses valores são parte do contrato D&D/Create da build source pinada; scripts externos não devem somar outro stress para o mesmo bloco.

Outros blocos cinéticos devem usar os providers/registros do addon em vez de hardcode derivado por nome.

## 7. Itens não-bloco confirmados
`DesiresItems` confirma:
- **Handheld Saw** — atributos de ferramenta alinhados a axe de tier diamond;
- **Handheld Drill** — atributos alinhados a pickaxe de tier diamond;
- **Burner Stock**;
- **Gatling Breaker**;
- **Golden Whisk**;
- **Lapis Lazuli Shard**;
- **Diamond Shard**;
- **Coal Piece**;
- package/burst-package items derivados dos package styles de Create.

## 8. Milkshakes
O source registra milkshakes com food data e efeitos explícitos:
- **Chocolate Milkshake:** Health Boost II por 4 min;
- **Vanilla Milkshake:** Saturation por 10 s;
- **Strawberry Milkshake:** Regeneration II por 30 s;
- **Glowberry Milkshake:** Night Vision por 10 min;
- **Pumpkin Milkshake:** Speed II por 60 s.

No source pinado eles usam nutrition `12`, saturation modifier `1.2`, são always-edible e stackam até `16`.

Balanceamento externo deve alterar o provider deliberadamente; não reaplicar os mesmos efeitos por food event.

## 9. Fluid handling
A presença de **Fluid Gauge**, **Fluid Hatch** e correções de fluid interaction na build 2.3a-BETA confirma uma superfície funcional de fluid state. Fluido continua pertencendo ao fluid/storage contract de Create/NeoForge e ao BE D&D que o contém.

A release 2.3a-BETA adiciona **boiler stats ao Fluid Gauge** e corrige interações de fluidos. Isso deve entrar como regression gate para tanks, gauges e transferências.

## 10. Smart Hopper
O **Smart Hopper** é uma superfície de inventory/automation e recebeu fixes na 2.3a-BETA. Inserção, extração, filtros e concorrência com outros automation providers devem ser testados via inventory contract real.

Risco principal: dupe/perda quando dois sistemas tentam movimentar o mesmo stack no mesmo tick.

## 11. Golden Mixer
A release registra tentativa de correção de **Gold Mixer crash bug**. Dedicated server + operação do Golden Mixer é regression gate específico da build física. Não afirmar que o bug está eliminado no pack até reproduzir.

## 12. Bore e Spud Sentry
A 2.3a-BETA:
- adiciona **Spud Sentry**;
- reduz a hitbox do **Bore**;
- inclui pequenas correções relacionadas.

Hitbox e interação pertencem ao block/entity implementation do addon; combat/target behavior do Spud Sentry deve ser validado server-side antes de qualquer integração RPG.

## 13. Sable
O changelog registra **“a bit of Sable compat”**, comprovando bridge real, mas sem justificar inferir suporte universal para todas as máquinas/contraptions. O source também contém baseline explícito de Sable.

Objetos stateful capturados/movidos por física precisam preservar BE/inventory/kinetic registration exatamente uma vez.

## 14. Configuração e networking
O source possui registries dedicados a **configs** e **packets**. Valores/keys exatos não são congelados nesta ficha sem inspeção completa do schema gerado.

Packets devem transportar intenção/state sem criar segunda authority client-side. Config server/common que afeta gameplay prevalece sobre apresentação local.

## 15. Client / Server
- machines, inventories, fluids, processing e stress: server-authoritative;
- rendering/models/particles/Ponder presentation: client-facing;
- packets: precisam validar side/context;
- Sable/contraption state: commit no servidor/provider físico.

A ocorrência histórica de crash em Gold Mixer reforça dedicated-server/classloading/lifecycle como superfície real.

## 16. Lifecycle
Validar:
- world/server boot;
- machine placement/removal;
- block entity load/unload;
- chunk unload/reload;
- config reload/restart;
- contraption assemble/disassemble;
- Sable capture/release quando aplicável;
- fluids/inventory persistindo após restart;
- resource/model reload;
- network reconnect.

## 17. Multiplayer / idempotência
Dois jogadores ou duas máquinas não podem liquidar a mesma operação duas vezes. Inventories, fluids, processing e sentry state devem permanecer no servidor. GUI/animation jamais substitui confirmação de mutation server-side.

## 18. Riscos
1. stress double-count;
2. Smart Hopper dupe/loss;
3. fluid transfer duplicado ou stale;
4. Gold Mixer crash regression;
5. Bore hitbox/interação regressar;
6. BE state perdido em contraption/Sable;
7. packet executado no side errado;
8. optional integration classloading sem provider;
9. version drift com Create/Flywheel/Ponder/Sable;
10. múltiplos addons Create registrando funções parecidas sem precedence.

## 19. Matriz de testes
1. Dedicated server boot com Create atual.
2. Colocar/usar cada máquina crítica acima.
3. Industrial Fan: stress impact/capacity sem dupla contabilização.
4. Smart Hopper com item manual, hopper externo e Create transfer.
5. Fluid Gauge/Fluid Hatch com tanks vazios/parciais/cheios.
6. Boiler stats no Fluid Gauge.
7. Golden Mixer sob carga e restart — regression gate.
8. Bore collision/hitbox em multiplayer.
9. Spud Sentry target/state/save-reload.
10. Sable compat em objeto simples e BE stateful.
11. Contraption assemble/disassemble com inventory/fluid state.
12. Dois jogadores interagindo simultaneamente com container/machine.
13. Config/reconnect/resource reload.

**A matriz é documental; testes não foram executados nesta catalogação.**

## 20. Evidências
- modlist física canônica de 08/09/2026: JAR/mod id/runtime;
- source oficial `LopyLuna/Create-Dreams-and-Desires`, branch `1.21.1`, `gradle.properties` 2.3a-BETA;
- `DesiresBlocks` e `DesiresItems` do branch pinado;
- changelog oficial 2.3a-BETA: Spud Sentry, Sable compat, Fluid Gauge boiler stats, fluid/Smart Hopper fixes, Bore hitbox e Gold Mixer crash fix.

> **Boundary canônico:** D&D possui suas máquinas e adapters; **Create continua authority do kinetic/processing framework**. Integrações devem consumir os contracts existentes, nunca duplicar a operação.
