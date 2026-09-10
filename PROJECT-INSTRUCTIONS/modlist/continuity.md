# Continuity

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81969a8df2a92a37efde
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Continuity
- **Arquivo JAR:** `continuity-3.0.0+1.21.neoforge.jar`
- **Versão 1.21.1:** 3.0.0+1.21.neoforge
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual
- **Função:** Mod client-side de rendering/resource-pack que implementa connected textures e superfícies emissive/overlay compatíveis com o ecossistema/formato Continuity.
- **Dependências:** Client-side. Requisitos reais dependem do pipeline/renderizador e dos resource packs usados; não possui authority sobre gameplay server-side. Não tratar outros CTM providers como substitutos automáticos sem comparar formatos.
- **Sobreposição:** Pode sobrepor objetivos de connected textures com outros render/CTM mods, mas formatos e pipeline podem ser diferentes. Conflito deve ser provado por rule/resource/render collision concreta.
- **Compatibilidade/Riscos:** Riscos em resource reload, sprite paths, custom block layers, moving blocks, emissive item textures, overlay corner cases e coexistência com outros CTM/render mods. 3.0.0 corrige paths `assets/minecraft/`, moving blocks, stale `disableSolidCheck`, overlay corner e desativa emissive item textures por performance.
- **Observações:** mod id `continuity`; runtime `3.0.0+1.21.neoforge`. Client-only. Atua em CTM/emissive/overlay presentation e resource-pack semantics; não altera hardness, collision, drops ou lógica funcional dos blocos.
- **Procedência:** modlist.txt física atual de 08/09/2026 + runtime 3.0.0+1.21.neoforge + CurseForge/Modrinth oficiais Continuity 3.0.0 NeoForge e changelog oficial.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/continuity
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Continuity 3.0.0+1.21.neoforge, CTM/emissive/overlay pipeline, moving blocks, reload/cache, performance e regressões 3.0.0 confirmados no QC global #107. Client-only; runtime visual QA não executado.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Continuity 3.0.0+1.21.neoforge foi reconfirmado fisicamente e reconstruído como mod client-side de rendering/resource packs. A presença não foi convertida em decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🧩 Versão física confirmada: `continuity-3.0.0+1.21.neoforge.jar`, mod id `continuity`, runtime `3.0.0+1.21.neoforge`. Continuity é **client-side** e atua em connected textures/emissive/overlay de resource packs.

## 1. Papel e authority
Continuity interpreta regras visuais de connected textures e recursos relacionados. O resource pack define assets/regras; Continuity executa essa apresentação no cliente.
Hardness, collision, drops, redstone, inventories e demais propriedades funcionais do bloco continuam sob authority do bloco/provider.

## 2. Connected textures
CTM escolhe sprites/model appearance com base no contexto de vizinhança e regras do resource pack. Dois blocos visualmente conectados continuam sendo block states independentes salvo mecânica explícita de outro mod.
Não usar resultado visual do CTM como input de lógica server-side.

## 3. Emissive e overlays
A linha suporta superfícies emissive/overlay conforme formato suportado. Emissive altera aparência/iluminação de renderização; não implica light level real no mundo sem outro mecanismo.
Overlay é composição visual e deve respeitar ordering/layers do render pipeline.

## 4. Sprite paths — 3.0.0
A 3.0.0 corrige resolução de sprite paths relativos a `assets/minecraft/`. Resource packs antigos/custom podem depender de paths específicos.
Regression gate: packs do usuário devem resolver os sprites esperados sem missing texture depois de reload.

## 5. Moving blocks
A release corrige **custom block layers em moving blocks**. Isso é relevante em pack com Create/contraptions e outras superfícies móveis.
O bloco móvel deve manter aparência coerente sem transferir propriedades funcionais da textura ou gerar crash no render thread.

## 6. `disableSolidCheck` e reload
A 3.0.0 corrige valor default stale de `disableSolidCheck` após **resource reload**. Isso mostra que caches/config de render precisam ser invalidados corretamente.
Trocar resource pack/reload não pode reutilizar state visual da configuração anterior.

## 7. Overlay corner case
O changelog também corrige edge case de overlay em cantos. Construções com várias superfícies conectadas devem ser testadas em quinas, faces internas e transições de material.

## 8. Emissive item textures e performance
A 3.0.0 desabilita emissive item textures devido a potenciais problemas de performance. Portanto não documentar emissividade de item como feature ativa garantida dessa build.
Qualquer resource pack que espere isso precisa ser validado contra o comportamento atual.

## 9. Compatibilidade com outros render/CTM providers
Outro mod pode oferecer CTM, emissive ou model hooks. Coexistência só é conflito quando ambos processam a mesma rule/resource/layer de forma incompatível.
Não remover um provider apenas por função semelhante; comparar formato, resource packs e pipeline efetivamente usados.

## 10. Client-only
Continuity é client-side. Dedicated server não precisa executar sua lógica de render e não deve carregar classes gráficas do mod.
Servidor pode hospedar mundo normalmente para clientes com seu próprio stack visual conforme requisitos do pack.

## 11. Lifecycle
Validar client boot, world join, resource-pack enable/disable, resource reload, language/UI changes irrelevantes ao pipeline, moving blocks, dimension change e shutdown/restart.
Caches de sprite/model/rule precisam ser reconstruídos após reload.

## 12. Riscos
1. Missing textures por path incorreto.
2. Dois CTM providers processarem a mesma superfície.
3. Moving block usar layer incorreta.
4. Cache stale após resource reload.
5. Overlay corner artifact.
6. Emissive item texture ser presumida ativa apesar do delta 3.0.0.
7. Shader/render mod expor incompatibilidade de layer.
8. Confundir efeito emissive visual com light state real.

## 13. Matriz de testes
1. Client boot com resource packs atuais.
2. CTM em paredes/vidro/blocos selecionados.
3. Quinas e transições de overlay.
4. Resource reload repetido e troca de pack.
5. Moving blocks/contraptions com custom layers.
6. Emissive block surfaces sob pipeline atual.
7. Item emissive esperado pelo pack: confirmar comportamento real da 3.0.0.
8. Shaders/render mods atuais sem crash/artifact.
9. Confirmar ausência de requisito no dedicated server.

## 14. Evidência
- modlist física atual: Continuity 3.0.0+1.21.neoforge;
- projeto oficial: connected textures/resource-pack rendering;
- changelog 3.0.0: sprite paths, moving block layers, `disableSolidCheck` reload, overlay corner e emissive item performance change.

> 🎨 Boundary canônico: Continuity controla **aparência client-side**. A textura conectada não muda a lógica física ou funcional do bloco.
