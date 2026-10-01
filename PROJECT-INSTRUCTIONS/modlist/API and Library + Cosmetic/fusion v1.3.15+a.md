# Fusion

## Propriedades do registro

- **Mod:** Fusion
- **Arquivo JAR:** `fusion-1.3.15a-neoforge-mc1.21.1.jar`
- **Versão 1.21.1:** `1.3.15+a`
- **Categoria:** Visual, Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://github.com/SuperMartijn642/Fusion/tree/neoforge-1.21
- **Função:** Biblioteca visual client-side para connected textures, scrolling/continuous textures, block overlays, custom entity/player models e modifiers condicionais de recursos/modelos.
- **Dependências:** Client-side NeoForge 1.21/1.21.1, Java 21. Não fornece lógica server-side; consumidores são resource packs/mods que usam seus loaders e formatos.
- **Compatibilidade/Riscos:** Conflitos de model loader/resource priority, stale cache após reload, lighting/render layer, custom entity/player model collision e classloading indevido por consumidor. Correção factual em 01/10/2026: o changelog oficial 1.3.15a corrige frame index de texturas animadas não sequenciais; 1.3.15b adiciona fixes para connected texture sem world context/empty tile e item translúcido invisível em Fabulous.
- **Sobreposição:** Pode cruzar EMF/ETF/Fresh Animations e outros loaders/resource packs apenas na apresentação visual. Não tratar adjacency visual como conexão lógica de gameplay nem criar dependência server-side.
- **Observações:** Divergência física preservada: `fusion-1.3.15a-neoforge-mc1.21.1.jar` e release/source identificam 1.3.15a, enquanto a metadata runtime da modlist declara `1.3.15+a`. Fusion continua client-side de modelos/texturas; não converter uma string na outra sem evidência do JAR.
- **Procedência:** modlist(1).txt física atual de 22/09/2026 — 587 entradas top-level incluindo o modloader — confirma `fusion-1.3.15a-neoforge-mc1.21.1.jar`, mod id `fusion`, metadata runtime `1.3.15+a` e SHA-1 `d29d0d1e0e7a9014fc8e0da69db8f019f458d002`. A divergência textual entre filename/release `1.3.15a` e metadata `1.3.15+a` permanece preservada.
- **Atualização/Status:** CORREÇÃO FACTUAL + UPSTREAM EM 01/10/2026 — runtime físico permanece 1.3.15+a / JAR 1.3.15a. O changelog oficial contradiz uma descrição histórica migrada para 1.3.15a; o texto foi reconciliado com source oficial e a 1.3.15b foi registrada como atualização disponível. Como o Notion histórico permanece com o dado antigo, o `✅-` deve ser removido até reconciliação da origem.
- **Data da última decisão:** 2026-09-07

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #294: JAR `fusion-1.3.15a-neoforge-mc1.21.1.jar`, mod id `fusion`, runtime `1.3.15+a`, SHA-1 `d29d0d1e0e7a9014fc8e0da69db8f019f458d002`.

<callout icon="🔎" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `fusion-1.3.15a-neoforge-mc1.21.1.jar`, mod id `fusion`, metadata runtime `1.3.15+a`. O filename, a release pública e o source usam `1.3.15a`; essa divergência de string é preservada sem normalização. Fusion é uma biblioteca **client-side de modelos/texturas**, não uma authority de gameplay.
</callout>

## 1. Identidade e versão

- **Mod:** Fusion.
- **JAR físico:** `fusion-1.3.15a-neoforge-mc1.21.1.jar`.
- **Mod id:** `fusion`.
- **Metadata runtime instalada:** `1.3.15+a`.
- **Filename/release/source:** `1.3.15a`.
- **Loader:** NeoForge.
- **Source/release:** linha `neoforge-1.21` identificada como 1.3.15a; usar a metadata física `1.3.15+a` para o campo estruturado de versão do runtime.

## 2. Papel no modpack

Fusion fornece loaders e extensões de resource/model para resource packs e mods. Sua função é permitir materiais e modelos visuais mais expressivos sem alterar state lógico de blocos, entidades ou itens. O projeto é útil como infraestrutura para packs que dependem de connected textures, overlays, animações de textura, modelos customizados e condições visuais.

## 3. Connected textures

A documentação oficial confirma **sete layouts de connected textures**. Fusion decide qual variante visual renderizar conforme vizinhança/condições, mas não altera o bloco real nem cria conexão lógica de gameplay. Qualquer integração própria deve tratar adjacency visual separadamente de capability, redstone, pipes ou connectivity funcional.

## 4. Scrolling e continuous textures

Fusion suporta scrolling textures e continuous textures que podem atravessar múltiplos blocos visualmente. Isso introduz state de render derivado de posição/tempo, não state persistente do mundo. Resource reload precisa reconstruir esses materiais sem deixar cache antigo.

## 5. Block overlays

Overlays permitem compor textura/modelo adicional sobre blocos. A prioridade de resource pack e o modelo base continuam relevantes: dois packs podem tentar alterar a mesma superfície. O resultado visual não deve ser usado para inferir ownership, bloco real ou propriedade server-side.

## 6. Custom entity models e modifiers

Fusion suporta modelos customizados de entidades e modificadores condicionais. Condições documentadas incluem altitude, biome, dimension e age. A linha 1.3.15 acrescenta suporte a custom player models com skin textures. Essas condições são client render logic; o servidor continua authority da entidade, idade real, dimensão e demais dados sincronizados.

## 7. Evolução 1.3.13–1.3.15a — correção factual pós-migração

> **Correção factual de 01/10/2026.** A versão migrada do dossiê atribuía à **1.3.15a** um fix de IDs de modelo NeoForge em data generation e descrevia 1.3.15/1.3.14 de forma incompatível com o changelog oficial atual. O arquivo changelog.md da branch oficial neoforge-1.21 e o CurseForge da release 1.3.15b confirmam a sequência abaixo. O registro antigo não é mantido como fato técnico.

Mudanças oficiais relevantes da linha instalada:

- **1.3.15a:** corrige **animated textures** usando frame index incorreto quando os frames não estão em ordem sequencial;
- **1.3.15:** corrige ambientocclusion, shade e emissive que nem sempre eram aplicados a texture types com subtextures como connecting e random; corrige o predicate is_biome sempre avaliando false em chunk geometry;
- **1.3.14a:** corrige render_type de modelos NeoForge tornando modelos invisíveis sem outro custom render type e corrige custom geometry NeoForge sendo bakeada como modelo regular;
- **1.3.14:** adiciona DefaultModelTypes#BLOCK_ENTITY_MARKER, corrige parent minecraft:builtin/entity não marcado como custom item renderer, registra DefaultModelTypes#ITEM_MODEL_GENERATOR e corrige crash com Iris PBR quando empty tiles produzem número diferente de sprites;
- **1.3.13:** adiciona consulta de render types por texture type, integração com Iris para normal/specular maps e vários fixes de missing model, subtextures, is_face_visible em item model, minimaps e world context.

Esses fixes tornam model cache, lighting, render layer, PBR/Iris, predicates e resource reload regression gates diretos.

## 8. Client / server

A FAQ oficial é explícita: Fusion só é necessário no cliente e **não faz nada no servidor**. Portanto:
- não deve ser hard dependency server-side de lógica de gameplay própria;
- dedicated server sem Fusion não deve depender de suas classes;
- dois clientes podem apresentar recursos Fusion diferentes sem mudar world state, desde que o conteúdo base seja compatível.

## 9. Lifecycle

Validar cold client boot, resource-pack enable/disable, mudança de prioridade de packs, F3+T/resource reload, login/reconnect, dimension change, model bake/rebake e alteração de skin/player model. Cache derivado precisa ser descartado quando resources mudam.

## 10. Integrações e sobreposição

Fusion pode coexistir com EMF/ETF, resource packs de connected textures, Fresh Animations e outros frameworks visuais. Coexistência não implica integração automática. O risco real surge quando duas camadas alteram o mesmo model/material/entity renderer ou assumem ownership da mesma textura.

## 11. Riscos técnicos

- conflito de model loader/resource priority;
- stale cache após resource reload;
- connected texture escolhida com vizinhança visual incorreta;
- lighting/render layer regressions;
- custom player/entity model colidindo com outro renderer;
- outline/transparency incorretos;
- client classloading indevido por consumidor server/common;
- data generation usando IDs incompatíveis com NeoForge;
- diferenças entre resource packs de clientes serem confundidas com state server-side.

## 12. Matriz de testes obrigatória

- [ ] Client boot com Fusion 1.3.15a e resource stack real do pack.
- [ ] Dedicated server inicia sem exigir Fusion para gameplay.
- [ ] Connected textures nos sete layouts usados por packs consumidores.
- [ ] Continuous e scrolling textures após chunk reload/dimension change.
- [ ] Overlays com packs em diferentes prioridades.
- [ ] F3+T/resource reload repetido sem textura/modelo stale.
- [ ] Custom entity models com altitude/biome/dimension/age conditions.
- [ ] Custom player model + skin texture e troca de skin/model.
- [ ] Lighting/outline/transparency em model parts emitidas.
- [ ] Coexistência com EMF/ETF/Fresh Animations e outros loaders reais do pack.

## 13. Evidências e limites

**Source primário pinado:** `SuperMartijn642/Fusion`, branch `neoforge-1.21`, exatamente 1.3.15a.
**Documentação oficial:** descrição/FAQ do projeto e changelog 1.3.13–1.3.15a.
**Limite:** Fusion é framework visual; a presença de uma feature não prova que o resource pack atual do usuário a utiliza. Consumers devem ser confirmados individualmente.
**Nenhum teste de runtime foi executado nesta catalogação.**


## 14. Atualização upstream 1.3.15b — não instalada
O pack continua fisicamente em fusion-1.3.15a-neoforge-mc1.21.1.jar / metadata 1.3.15+a. O CurseForge publicou **Fusion 1.3.15b para NeoForge 1.21.1** em 21/09/2026 (file ID 8942891).

A 1.3.15b contém dois fixes:
- corrige erro quando uma **connecting texture é renderizada sem world context** e uma empty tile é escolhida;
- corrige **item models translúcidos invisíveis** quando os gráficos estão em **Fabulous**.

Para promoção, testar connected textures em UI/item contexts sem world, modelos translúcidos em Fabulous, Iris/PBR, resource reload e os loaders visuais concorrentes já listados nesta ficha.

**Fontes upstream:** SuperMartijn642/Fusion branch neoforge-1.21, changelog.md; CurseForge file ID 8942891.
