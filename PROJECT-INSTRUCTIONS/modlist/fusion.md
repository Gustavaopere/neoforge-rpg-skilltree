# Fusion — 1.3.15a

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db8141ba94d55b54e38a52  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** Fusion
- **Arquivo JAR:** `fusion-1.3.15a-neoforge-mc1.21.1.jar`
- **Versão 1.21.1:** `1.3.15a`
- **Categoria:** Visual; Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://github.com/SuperMartijn642/Fusion/tree/neoforge-1.21
- **Função:** Biblioteca visual client-side para connected textures, scrolling/continuous textures, block overlays, custom entity/player models e modifiers condicionais de recursos/modelos.
- **Dependências:** Client-side NeoForge 1.21/1.21.1, Java 21. Não fornece lógica server-side; consumidores são resource packs/mods que usam seus loaders e formatos.
- **Compatibilidade/Riscos:** Conflitos de model loader/resource priority, stale cache após reload, lighting/render layer, custom entity/player model collision e classloading indevido por consumidor. 1.3.15a corrige IDs de modelo NeoForge em data generation.
- **Sobreposição:** Pode cruzar EMF/ETF/Fresh Animations e outros loaders/resource packs apenas na apresentação visual. Não tratar adjacency visual como conexão lógica de gameplay nem criar dependência server-side.
- **Observações:** Fusion é client-only e não altera gameplay/world state. Features incluem sete layouts de connected textures, continuous/scrolling textures, overlays e custom entity/player models; uso efetivo depende dos resource packs consumidores.
- **Procedência:** modlist.txt física atual de 09/09/2026 + source oficial SuperMartijn642/Fusion branch neoforge-1.21 exatamente em 1.3.15a + changelog/FAQ oficiais.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Fusion 1.3.15a source-pinned; connected/continuous textures, overlays, entity/player models, client boundary, resource lifecycle, riscos e testes catalogados.
- **Data da última decisão:** 2026-09-07

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `fusion-1.3.15a-neoforge-mc1.21.1.jar`, mod id `fusion`. O branch oficial `SuperMartijn642/Fusion:neoforge-1.21` declara exatamente `mod_version=1.3.15a`, Java 21 e suporte a Minecraft 1.21/1.21.1. Fusion é uma biblioteca **client-side de modelos/texturas**, não uma authority de gameplay.

## 1. Identidade e versão
- **Mod:** Fusion.
- **JAR físico:** `fusion-1.3.15a-neoforge-mc1.21.1.jar`.
- **Mod id:** `fusion`.
- **Versão instalada:** `1.3.15a`.
- **Loader:** NeoForge.
- **Source pin:** branch `neoforge-1.21`, exatamente 1.3.15a.

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

## 7. Evolução 1.3.13–1.3.15a
Mudanças relevantes da linha instalada:
- **1.3.15a:** corrige model IDs NeoForge incorretos em data generation;
- **1.3.15:** custom player models com skin textures;
- **1.3.14:** corrige light level incorreto em model parts emitidas e render layer de entity model;
- **1.3.13:** enhanced model parts com baked textures, `insert` e `textureOverride`, além de correções de cache, outline e continuous textures.
Esses fixes tornam model cache, lighting e resource reload regression gates diretos.

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
