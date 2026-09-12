# Athena

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db813e8874fd85bace5487
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Athena
- **Arquivo JAR:** `athena-neoforge-1.21.1-4.0.6.jar`
- **Versão 1.21.1:** 4.0.6
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** Biblioteca para connected block textures e recursos de modelos/renderização usada por mods dependentes.
- **Dependências:** Biblioteca client-side de model/resource infrastructure; consumidores concretos devem ser confirmados por dependency graph antes de qualquer remoção. Não promove estado visual a authority de gameplay.
- **Sobreposição:** Biblioteca técnica; APIs de renderização/modelos não são automaticamente intercambiáveis.
- **Compatibilidade/Riscos:** Não remover por aparente redundância com outras bibliotecas de renderização; consumidores podem exigir Athena especificamente.
- **Observações:** mod id: athena; runtime name: Athena.
- **Procedência:** modlist.txt física atual de 11/09/2026 + documentação/source Athena 4.0.6 e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `athena-neoforge-1.21.1-4.0.6.jar` / `4.0.6`; sem divergência física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/athena
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #57: `athena-neoforge-1.21.1-4.0.6.jar` / `4.0.6` conferidos contra a modlist atual; escopo client-side/model infrastructure e corpo técnico preservados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria confirmou Athena 4.0.6 como biblioteca client-side de modelos/CTM e registrou que sua necessidade depende dos consumidores reais. Nenhuma decisão de manter/remover foi inferida apenas da presença no pack.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física/source coincidem: `athena-neoforge-1.21.1-4.0.6.jar`, mod id `athena`, versão `4.0.6`, Minecraft 1.21.1. É uma biblioteca **client-side** de custom baked models/connected textures; não adiciona gameplay próprio.

## 1. Papel e autoridade
Athena fornece uma camada cross-platform de **custom block models**, especialmente connected textures (CTM), modelos de pane/pillar e modelos grandes/murais. A biblioteca é autoridade apenas do **baking/seleção visual de quads/texturas** dos consumidores que usam seu loader/factories. Estado de bloco, colisão, drops, inventário e lógica de servidor continuam pertencendo ao Minecraft/mod consumidor.

## 2. Ambiente e side
O projeto 4.0.6 declara plataformas Fabric e NeoForge, com Minecraft 1.21.1. O CurseForge marca o mod como **Client**. Consequência operacional:
- não usar classes Athena em lógica comum/server-only de mods próprios sem dist-safe boundary;
- dedicated server não deve depender de Athena para decidir estado de jogo;
- ausência do cliente deve ser tratada conforme metadata real dos consumidores, nunca contornada com sincronização de gameplay.

## 3. Model factories padrão — 8 IDs registrados
O source 1.21.1 registra em `DefaultModels`:
1. `athena:ctm` → `ConnectedBlockModel.FACTORY`
2. `athena:carpet_ctm` → `ConnectedCarpetBlockModel.FACTORY`
3. `athena:pane_ctm` → `PaneConnectedBlockModel.FACTORY`
4. `athena:giant` → `GiantBlockModel.FACTORY`
5. `athena:mural` → também `GiantBlockModel.FACTORY`
6. `athena:pillar` → `PillarBlockModel.FACTORY`
7. `athena:limited_pillar` → `LimitedPillarBlockModel.FACTORY`
8. `athena:pane_pillar` → `PanePillarBlockModel.FACTORY`

`mural` e `giant` compartilham factory no source auditado; não tratá-los como engines independentes.

## 4. Superfícies públicas/API confirmadas
O tree da branch 1.21.1 expõe como API/client utilities:
- `AthenaBlockModel`
- `AthenaModelAttributes`
- `AthenaModelFactory`
- `AthenaQuad`
- `FactoryManager`
- `NotNullUnbakedModel`
- `TintProvider`
- `AppearanceAndTintGetter`
- `AthenaUnbakedModelLoader`
- `AthenaUtils`
- `CtmState`
- `CtmUtils`
- `NullableEnumMap`

No lado NeoForge há wrappers/implementações de `AthenaBakedModel`, `AthenaUnbakedModel` e `ModelLoaderServiceNeoForgeImpl`.

## 5. Contrato de model loading
O consumidor fornece um modelo/JSON compatível com os model loaders Athena. Durante resource/model reload, a factory correspondente constrói o modelo Athena, resolve materiais/texturas e no bake gera quads conforme contexto visual.

A seleção de connected texture depende de aparência/vizinhança observável no cliente. Isso **não altera** `BlockState` do servidor. Se um resource pack muda texturas/model JSON, a fonte correta é o resource reload, não NBT ou pacote custom de gameplay.

## 6. CTM e vizinhança
`ConnectedBlockModel`, `ConnectedCarpetBlockModel` e `PaneConnectedBlockModel` calculam aparência de faces/segmentos com apoio de `CtmState`/`CtmUtils`. Riscos relevantes:
- vizinho descarregado/ausente;
- blockstate visualmente equivalente mas provider diferente;
- tint/appearance override;
- model cache contendo resultado de estado anterior após resource reload;
- mudanças rápidas de bloco não invalidarem visual imediatamente.

A integração correta deve permitir ao renderer recomputar; não sincronizar manualmente uma “máscara CTM” como estado autoritativo do servidor sem necessidade comprovada.

## 7. Giant/Mural
`athena:giant` e `athena:mural` usam `GiantBlockModel`. O objetivo é dividir/compor uma textura/modelagem maior entre blocos/posições. Esse tipo de modelo depende fortemente de coordenada/contexto visual e deve ser testado em chunk borders e após reload.

## 8. Pillar family
- `pillar`
- `limited_pillar`
- `pane_pillar`

Essas factories adaptam orientação/conexão de pilares/panes. Propriedades físicas continuam no blockstate do mod consumidor; Athena só escolhe geometria/quads de render.

## 9. Tint e appearance
`TintProvider` e `AppearanceAndTintGetter` são superfícies explícitas para variação de cor/aparência. Um consumidor que oferece tint dinâmico deve manter determinismo por estado/contexto; valores de cor não podem ser usados como autoridade de lógica do bloco.

## 10. Lifecycle de recursos
Testes obrigatórios para uma biblioteca visual deste tipo:
1. boot do cliente com todos consumidores presentes;
2. F3+T/resource reload sem model missing, crash ou stale cache;
3. trocar resource pack que substitui modelos Athena;
4. entrar/sair de mundo após reload;
5. chunk load/unload com CTM/giant na borda;
6. atualizar bloco vizinho e confirmar rebake/re-render;
7. render em inventário/item quando o consumer usa loader compatível;
8. verificar logs de model loader para IDs desconhecidos.

## 11. Compatibilidade no modpack
Athena é uma **biblioteca**, não substituto genérico para outros CTM frameworks. Dois mods podem usar frameworks diferentes sem conflito se cada modelo declara seu loader correto. Risco real aparece quando resource packs sobrescrevem JSON/model loader de um consumidor ou quando outro mod intercepta model baking.

Não remover Athena só porque “não há blocos Athena”: a necessidade é determinada pelos mods/resource packs consumidores.

## 12. Client-only safety
- Não referenciar `earth.terrarium.athena.api.client.*` de common/server initialization em projeto próprio.
- Não registrar packet de gameplay para “corrigir” connected texture.
- Dedicated server smoke deve verificar que consumidores também respeitam sides; a biblioteca em si é declarada client-side.

## 13. Evidência
- Modlist física atual: Athena 4.0.6.
- CurseForge oficial: solução cross-platform de connected block textures; ambiente Client; NeoForge 4.0.6 para 1.21.1.
- Source oficial `terrarium-earth/Athena`, branch `1.21.1`, SHA atual auditado `deb1209837a201f7ac9f0f3a616521dc1831b78e`.
- `gradle.properties`, tree de API/model classes e `DefaultModels` auditados.

> 🎨 Exaustividade apropriada ao escopo: 8 model IDs/factories padrão e as principais superfícies públicas de model/tint/CTM foram catalogadas. Nenhum bloco/item fictício foi criado para “encher” a ficha de uma biblioteca client-side.