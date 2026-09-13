# CreateBetterFps

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81b487f1d82e7a34f0df  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** CreateBetterFps
- **Arquivo JAR:** `createbetterfps-1.21.1-1.1.4.jar`
- **Versão 1.21.1:** `1.1.4`
- **Categoria:** Performance
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-better-fps
- **Função:** Otimização exclusivamente client-side para melhorar FPS em cenas do Create, especialmente com shaderpacks; o projeto anuncia ganhos de até 50% em cenários favoráveis.
- **Dependências:** Create no cliente. Build 1.1.4 é NeoForge 1.21.1 e ambiente Client. Não exigir no dedicated server nem inferir dependências extras sem metadata/source pin.
- **Compatibilidade/Riscos:** Interage com pipeline de render do Create e, por consequência, deve ser validado junto do stack gráfico atual. Ganho é hardware/cena/shader-dependent. Changelog 1.1.4 diz apenas `fix #7`; causa e implementação ficam fail-closed sem abrir a issue/source correspondente.
- **Sobreposição:** Não duplica Create: Lazy Tick: BetterFps atua no cliente/render/FPS; Lazy Tick atua em tick scheduling/caches/sync. Conflito real exige ambos alterarem a mesma superfície concreta, não apenas a categoria Performance.
- **Observações:** JAR `createbetterfps-1.21.1-1.1.4.jar`; runtime 1.1.4; Environment: Client. Projeto anuncia melhoria de FPS do Create `up to 50%`. A 1.1.4 é Release de 19/04/2026; changelog público: `fix #7, Thanks @mrshine4k`, sem detalhe causal suficiente para inventar o bug.
- **Procedência:** Modlist física canônica de 08/09/2026, 595 top-levels + runtime 1.1.4 + CurseForge/Modrinth oficiais da release 1.1.4.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, CreateBetterFps 1.1.4 foi reconfirmado como `Instalado` e reconstruído ao padrão técnico. Benefício potencial de performance não foi convertido automaticamente em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — client-side Create render optimization authority, shader/render-stack boundaries, client lifecycle, performance validation e fail-closed delta 1.1.4 catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> 🎞️ Versão física confirmada: `createbetterfps-1.21.1-1.1.4.jar`, runtime `1.1.4`, NeoForge 1.21.1. O projeto declara ambiente **Client** e foco em melhorar FPS do Create, inclusive com shaderpacks.

## 1. Papel e authority
CreateBetterFps é uma otimização de apresentação/render. Create continua authority de kinetics, block entities, contraptions, inventories e gameplay state; BetterFps não deve alterar o resultado lógico apenas para reduzir custo gráfico.

## 2. Client-only
A página oficial classifica a build como **Client**. Dedicated server não deve precisar do mod para carregar mundo ou executar máquinas Create. Diferença de presença cliente↔servidor não pode alterar recipe, tick ou network state.

## 3. Ganho de performance
O projeto anuncia melhoria de FPS `up to 50%`. Esse é um teto promocional dependente de cena/hardware/shader; não é garantia do pack. A validação correta compara frame time/FPS no mesmo cenário com e sem o mod.

## 4. Shaders
O foco explícito inclui cenas com shaderpack. Shader pipeline, driver, GPU, Sodium/Embeddium-equivalents e resource packs podem mudar o resultado. Um ganho/bug observado em um shader não deve ser generalizado para todos.

## 5. Render de Create
O mod atua sobre a superfície gráfica do Create. Modelos, instancing/batching ou outras técnicas internas específicas não são atribuídas sem source pin; a ficha permanece fail-closed quanto ao mecanismo exato.

## 6. Invariância de gameplay
Com BetterFps ligado/desligado, RPM, stress, item movement, recipes, contraption transforms e state server-side devem permanecer equivalentes. Qualquer diferença funcional é regressão, não trade-off aceitável de FPS.

## 7. Relação com Lazy Tick
Create: Lazy Tick otimiza tick/cache/sync; CreateBetterFps otimiza client rendering/FPS. Ambos podem coexistir porque atuam em camadas distintas. Profiling deve medir CPU server/tick e GPU/client separadamente.

## 8. Version drift
Atualizações do Create podem mudar render internals. Sintomas possíveis de incompatibilidade incluem missing/invisible geometry, flicker, crash de renderer ou perda do ganho — não devem ser corrigidos alterando state de gameplay.

## 9. Release 1.1.4
A release instalada é 1.1.4, publicada para NeoForge 1.21.1. O changelog informa apenas `fix #7`. Como a publicação não explica o bug, nenhum comportamento específico é atribuído a esse fix sem evidência adicional.

## 10. Resource reload
Resource/shader reload é regression gate: render caches precisam ser reconstruídos sem exigir relog e sem manter geometry stale. O servidor não deve perceber diferença funcional.

## 11. Multiplayer
Cada cliente pode ter capacidade gráfica diferente. O servidor não deve depender do mod. Dois jogadores observando a mesma contraption precisam receber o mesmo state lógico mesmo que um renderize com BetterFps e outro não.

## 12. Riscos
1. Geometry do Create desaparecer ou piscar.
2. Shader incompatível produzir artefatos.
3. Render cache ficar stale após resource reload.
4. Create update quebrar hooks client-side.
5. Métrica de FPS ser comparada em cenários diferentes.
6. Otimização visual ser confundida com redução de MSPT.
7. Fix #7 ser descrito incorretamente sem source.

## 13. Matriz de testes
1. Dedicated server sem BetterFps.
2. Cliente com/sem mod no mesmo mundo e câmera.
3. Fábrica Create densa sem shaders.
4. Mesma cena com shaderpack atual.
5. Contraptions/trains em movimento.
6. Resource/shader reload sem geometry stale.
7. Comparar FPS/frame time e verificar gameplay invariance.
8. Multiplayer com um cliente usando BetterFps e outro sem.
9. Update do Create: smoke-test de renderer antes de produção.

## 14. Evidência
- modlist física 08/09/2026: 1.1.4;
- CurseForge/Modrinth: Client-only, otimização de FPS do Create, anúncio `up to 50%`;
- release 1.1.4: NeoForge 1.21.1, changelog público `fix #7` sem detalhe causal.

> 🔒 Boundary canônico: **BetterFps pode mudar custo/renderização, nunca o state funcional do Create**. Performance deve ser medida por frame time, com invariância de gameplay validada separadamente.
