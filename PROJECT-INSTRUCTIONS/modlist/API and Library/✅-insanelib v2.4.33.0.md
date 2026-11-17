# InsaneLib

## Propriedades do registro

- **Mod:** InsaneLib
- **Arquivo JAR:** `insanelib-2.4.33.0.jar`
- **Versão 1.21.1:** `2.4.33.0`
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Histórico da decisão:** 2026-09-18 — runtime físico atualizado para 2.4.33.0; nenhuma decisão curatorial nova. Sound Overrides deixa de ser feature ativa porque foi removido pelo upstream nesta release.
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://github.com/Insane96/InsaneLib/tree/1.21.1
- **Função:** Biblioteca e framework compartilhado dos mods de Insane96, com módulos/config declarativos, utilitários, tags/NBT, atributos e pequenas correções/features comuns de gameplay.
- **Dependências:** NeoForge 1.21.1; pack físico atual usa NeoForge 21.1.250. O JAR físico 2.4.33.0 embarca `EvalEx-3.6.0.jar` via JarJar como dependência interna do host. Consumers da família Insane devem ser validados antes de remover/atualizar a library.
- **Compatibilidade/Riscos:** Biblioteca com hooks comuns e features opcionais que podem alterar tempo, weather, NBT de entidades, XP e atributos. Riscos: ABI drift em consumers, tags/NBT malformadas, duplicate handlers, config divergence, mudanças em time/weather compartilhadas com outros sistemas e consumer ainda depender do Sound Overrides removido em 2.4.33.0.
- **Sobreposição:** Não substitui outras libraries. Algumas features comuns podem tocar tempo/weather, atributos, XP e entity NBT, mas a authority final deve permanecer no sistema que efetivamente aplica cada regra; evitar handlers duplicados.
- **Observações:** 2.4.33.0 remove Sound Overrides, recurso que a 2.4.32.0 havia adicionado para fuse/explosion via NBT. O JAR continua hospedando `EvalEx 3.6.0` internamente; essa biblioteca não recebe entrada top-level. As mudanças 2.4.31.0 em `insanelib:mob_detection_range`, `insanelib:push_resistance`, Grindstone XP e ModNBTData/MCUtils permanecem baseline histórica.
- **Procedência:** modlist(1).txt física atual de 18/09/2026 + CurseForge oficial InsaneLib 2.4.33.0, release NeoForge 1.21.1 de 15/09/2026 + changelog oficial 2.4.33.0/2.4.32.0 + inventário físico confirmando `EvalEx-3.6.0.jar` em `META-INF/jarjar`.
- **Atualização/Status:** AUDITORIA DOCUMENTAL 17/11/2026 — runtime 2.4.33.0 preservado. Cadeia de versões 2.4.33.1–2.5.3.1 (NeoForge 1.21.1) verificada no changelog cumulativo oficial; impactos materiais em configs client/server, item components, stack counts, loot, reload, atributos e receitas documentados na seção final. QA físico não realizado.
- **Data da última decisão:** 2026-08-30

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #331: JAR `insanelib-2.4.33.0.jar`, mod id `insanelib`, runtime `2.4.33.0`, SHA-1 `97b2e0a7fea7b80cd96e250e274810b25b597b66`. `EvalEx-3.6.0.jar` permanece dependência JarJar interna e não ocupa posição top-level.

<callout icon="🧩" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `insanelib-2.4.33.0.jar`, mod id `insanelib`, versão `2.4.33.0`, NeoForge 1.21.1. A release oficial 2.4.33.0 foi publicada em 15/09/2026; o pack físico atual usa NeoForge 21.1.250.
</callout>
## 1. Papel e authority
InsaneLib é a biblioteca compartilhada dos mods de Insane96 e também contém pequenas features/fixes comuns. Ela é authority apenas das APIs, módulos e comportamentos que registra diretamente; mods consumidores continuam owners de seu gameplay específico.
## 2. Framework de módulos e configuração
A documentação oficial descreve features modulares, cada uma com subconfig própria e opções declaradas via `@Config`. Também existe `JsonFeature`, capaz de criar/consumir JSONs na pasta de configuração. Isso torna config/schema e reload/restart boundaries relevantes para consumers.
O JAR físico `insanelib-2.4.33.0.jar` embarca `META-INF/jarjar/EvalEx-3.6.0.jar`. Pelo protocolo do catálogo, **EvalEx 3.6.0 é dependência interna JarJar do InsaneLib**, não um mod top-level da modlist. Atualizações do host podem trocar essa biblioteca sem aparecer um novo arquivo em `mods`; erros de expression parsing/evaluation devem considerar esse runtime embarcado antes de atribuir causalidade a outro provider.
## 3. Features de jogador documentadas
Entre as features públicas está a opção de interromper day/game time quando não há jogadores online. O comportamento também pode pausar weather e, quando presente, o ciclo de Serene Seasons; a documentação também cita compatibilidade com Time Control. Não inferir que essa feature esteja habilitada sem ler a config efetiva.
## 4. Tags/NBT comuns
O upstream documenta dados como spawn type, flag de explosão causar fogo e multiplicador de XP em entidades. Integrações externas não devem escrever essas tags sem conhecer seu contrato, pois valores duplicados ou tipos errados podem alterar morte/explosão de forma global.
## 5. Sound Overrides — introduzido em 2.4.32.0 e removido em 2.4.33.0
A 2.4.32.0 introduziu override de sons de fuse e explosão via NBT nos paths `NeoForgeData.insanelib.sound_overrides.fuse_sound` e `NeoForgeData.insanelib.sound_overrides.explosion_sound`. A release física **2.4.33.0 removeu integralmente esse recurso**, porque o upstream considerou que suportá-lo corretamente exigiria trabalho excessivo. Portanto esses paths ficam preservados apenas como histórico de versão e **não devem ser tratados como contrato ativo no runtime 2.4.33.0**.
## 6. Atributos e fixes — 2.4.31.0
A linha imediatamente anterior adicionou traduções para `insanelib:mob_detection_range` e `insanelib:push_resistance`; também corrigiu a posição de saída de XP do Grindstone e registrou ajustes técnicos em `ModNBTData` e `MCUtils#getGrindstoneOutputPos`. Esses pontos permanecem regression gates válidos da linha 2.4.33.0.
## 7. Client / server
A distribuição oficial é Client & Server. Config e state que alteram tempo, weather, XP, atributos, explosões ou entity data devem ser authoritative no servidor. Feedback audiovisual permanece client-facing quando aplicável, mas a antiga superfície Sound Overrides não existe mais em 2.4.33.0; eventos de fuse/explosion continuam derivados do state real da entidade e dos providers correspondentes.
## 8. Consumers e ABI
Atualizar InsaneLib é uma mudança potencial de ABI para qualquer mod consumidor. A presença física de uma library não autoriza removê-la por falta de conteúdo visível; remover ou trocar versão exige mapear dependencies reais na metadata dos consumers.
## 9. Lifecycle
Validar construction/config load, datapack/config reload quando suportado, login/logout do último jogador, save/restart, entity spawn/death, explosões, Grindstone interaction e reconnect. State transitório não deve sobreviver indevidamente a unload ou troca de mundo.
## 10. Riscos técnicos
- consumer compilado contra assinatura/API diferente;
- configuração divergente entre ambientes;
- `JsonFeature` receber schema inválido;
- tags/NBT custom com tipo ou resource location inválido;
- consumer/config antigo ainda depender de Sound Overrides removido em 2.4.33.0;
- dois mods aplicarem o mesmo ajuste de XP/explosão;
- feature de tempo/weather competir com outro provider;
- atributo não ser removido ou sincronizado corretamente;
- tratar um NeoForge de desenvolvimento histórico como versão exigida exata apesar do runtime físico atual usar 21.1.250.
## 11. Matriz de testes obrigatória
- [ ] Dedicated server e cliente iniciam com InsaneLib 2.4.33.0 e consumers físicos.
- [ ] Consumers carregam sem linkage/mixin errors.
- [ ] Config default não altera features que deveriam estar desativadas.
- [ ] Nenhum consumer/config do pack depende da antiga superfície Sound Overrides da 2.4.32.0.
- [ ] Fuse/explosion continuam funcionais sem os paths removidos de Sound Overrides.
- [ ] Entity XP multiplier não duplica recompensa.
- [ ] Grindstone XP aparece na posição correta.
- [ ] Login do primeiro/logout do último jogador respeita config de time stop.
- [ ] Restart/reconnect não deixa state transitório residual.
## 12. Evidências e limites
- **Modlist física:** filename/mod id/version exatos e `EvalEx-3.6.0.jar` embarcado em `META-INF/jarjar`.
- **CurseForge oficial:** release 2.4.33.0 de 15/09/2026 e descrição da biblioteca.
- **Changelog:** 2.4.33.0 remove Sound Overrides; 2.4.32.0 registra sua introdução; mudanças 2.4.31.0 citadas acima permanecem baseline histórica.
- **Limite:** consumers físicos não foram inferidos somente pela autoria; devem ser confirmados por dependency metadata quando necessário.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.

## 13. Correção upstream InsaneLib 2.4.33.1 — 10/10/2026

**Físico:** `insanelib-2.4.33.0.jar` / 2.4.33.0. A release **2.4.33.1 (05/10/2026; CurseForge file 9068453)** é a última NeoForge 1.21.1 pública identificada. O changelog de versão registra precisamente: **“Client modules are no longer loaded on server side”**.

**Impacto:** correção de **classloading por lado**. Bibliotecas usadas por vários consumers não podem inicializar módulos que referenciam render/config GUI no dedicated server; a mudança reduz risco de classnotfound/wrong-side startup crash. Não afirmar que um crash concreto já foi observado neste pack, pois nenhum novo log dedicado foi fornecido. A atualização herda a remoção de Sound Overrides feita em 2.4.33.0 (estado físico) e não reintroduz a feature.

**Gate:** iniciar servidor dedicado sem classes/client jars indevidos, client login, carregamento de todos os consumers InsaneLib sem linkage/mixin error, reload de config/registries, spawn/death, grindstone XP, efeitos que usam módulos comuns, e export/report de profiling. Não mudar config defaults sem comparação de source.

**Fonte:** https://www.curseforge.com/minecraft/mc-mods/insanelib/files/9068453

**Estado:** apenas documentação atualizada até 2.4.33.1; último runtime confirmado permanece 2.4.33.0.

## Atualização de catálogo — 22/10/2026

> **Autoridade física preservada.** Esta revisão registra apenas o estado upstream solicitado; nenhum JAR, filename físico, posição da modlist, configuração ou save foi alterado.

- **Projeto solicitado:** InsaneLib
- **Último candidato 1.21.1 / NeoForge localizado:** **2.4.33.1**
- **Estado:** **CANDIDATO PENDENTE**
- **Gate de promoção:** Revalidar consumers de InsaneLib, config/serialization, networking e dedicated server; não alterar versão física sem QA.
- **Fonte primária do dossier reconsultada:** https://github.com/Insane96/InsaneLib/tree/1.21.1

## Revisão cumulativa auditada — 22/10/2026 — lote 01–10
**Fechamento histórico de uma única release:** JAR histórico InsaneLib **2.4.33.0**, última versão 1.21.1 **2.4.33.1** (upstream). A seção 13 anterior do dossier documenta o delta oficial; esta seção o reconcilia à cadeia de versão instalada → publicada.

| Versão | Delta específico |
|---|---|
| 2.4.33.0 — instalada | Sound Overrides **removido** na 2.4.33.0; retirada é parte do baseline, e não novidade da 2.4.33.1. |
| 2.4.33.1 — publicada | **Client modules deixam de ser carregados no server side**, reduzindo risco de classloading/DISTXFORM ou lançamento de classe cliente no dedicado. Não existe nova feature de gameplay anunciada nesta nota. |

**QA condicionado:** inicialização dedicada sem classes do renderer/cliente, consumidores que usam Common vs Client, registries, efeitos de atributos, spawn/death e XP/grindstone, client login, network/reload e crash logging. Sem download de JAR, boot nem inspeção de metadata; publicação candidata não é autorização de instalação.

Fontes: https://www.curseforge.com/minecraft/mc-mods/insanelib/files/9068453 ; https://github.com/Insane96/InsaneLib/tree/1.21.1 .

## Auditoria de releases posteriores 2.4.33.1 → 2.5.3.1 (17/11/2026, não instaladas)

**JAR físico de referência:** `insanelib-2.4.33.0.jar` (#331), InsaneLib 2.4.33.0. Este dossier já documenta a 2.4.33.1; o histórico abaixo a mantém para formar a sequência completa, sem reatribuir ao runtime físico implementações mais novas. O **projeto InsaneLib / Insane96** publicou a versão **2.5.3.1 Release** em **10/11/2026** para **Minecraft 1.21.1 NeoForge**, [CurseForge file 9548012](https://www.curseforge.com/minecraft/mc-mods/insanelib/files/9548012). O changelog cumulativo oficial nessa página enumera separadamente cada incremento, incluindo releases **Alpha/Beta** da linha 2.5.0. A plataforma e file ID da versão final foram verificados; headings intermediários do changelog não provam isoladamente que cada versão tinha JAR NeoForge distribuído.

| Versão upstream | Mudança publicada, impacto e relevância |
|---|---|
| **2.4.33.1** | Client modules deixam de ser carregados no dedicated server — correção de side/classloading já registrada na seção 13. |
| **2.4.33.2** | **Loot pool injections** passam a funcionar no carregamento do mundo/dedicated server sem precisar de `/reload`. Correções na mesclagem/remoção de components e sync em servidores dedicados; loot functions voltam a encantar books. Muito relevante a datapacks, loot e packs com mais de um cliente. |
| **2.4.34.0** | `ILModConfig` suporta configs **SERVER**; sync em mudanças de arquivo com clientes e proteção contra acesso a config descarregada. Novas utils `ConfigUtils#isLoaded` e `#canSet`. Relevante para autoridade de configuração e consumers. |
| **2.4.35.0** | Mods com `ILModConfig` passam a oferecer tela de config na lista de mods, quando não substituída. **Better Falling Blocks** pode ser desativado, recebe fix de passagem por portais e o fix de duplication dos falling blocks migra para módulo independente. Relevante a UI, física e portais. |
| **2.4.36.0** | **Mudança de alto impacto:** `Increase Stack Limit` (padrão true) eleva teto de stacks até **1.073.741.823**, substituindo antiga opção `Stack Limit`. Corrige loss/deletion de itens sem stack, multiplicador de drops, loot purger e filtros, `/reload` de item components, sync de alterações entre servidores e races no singleplayer; altera várias APIs de JsonFeature, NBT, loot, atributo, knockback, TimeStop e falling blocks. Introduz `SnapshotList` para reload thread-safe e compat com UI/creative tabs. **Teto do stack não prova que inventários e mods consumidores aguentem a contagem gigante**: testar container/courier/backpack/recipe e serialização, especialmente compatibilidades. |
| **2.4.36.1** | Melhora diagnósticos de `@LoadFeature` com múltiplas configs e `Feature.isEnabled(Class)` para features inválidas; correção de API com mod consumidores. |
| **2.4.37.0** | Dano do tridente lançado passa a considerar modifiers do item (vanilla continua 8). Corrige `MCUtils.hurtIgnoreInvulnerability` para não deixar hit threshold incorreto entre iframes. Relevante a combate e addons. |
| **2.5.0.0-alpha** | **Refactor de autoridade de configuração**: tipo COMMON/SERVER/CLIENT passa a ser definido **por feature**, com `@ConfigType` e overloads de `ILModConfig`/`Module.Builder.create`. Config separa toggles por side, pula features CLIENT em dedicated server e alerta sobre módulos carregados fora de ordem. ABI e source consumers precisam revisão. |
| **2.5.0.1-alpha** | Corrige JsonFeatures SERVER não lidas no primeiro carregamento do mundo, apenas após reload. |
| **2.5.0.2-alpha** | Edição do config SERVER global a partir do menu principal, explicando override por world/serverconfig; evita mensagens duplicadas de sync. |
| **2.5.0.3-alpha** | Recalcula patches de **item components** após carga de config SERVER e mudanças/reloads, sincronizando updates com jogadores. |
| **2.5.0.4-alpha** | Ajusta nomes gerados de `@LoadFeature` removendo palavras CamelCase completas, podendo **mudar nomes de chaves de config e caminhos de dados**: risco de migração. |
| **2.5.0.5-beta** | Amplia limite de slots `ComponentItemHandler`/`ItemStackHandler` de 99 para limite maior de stack; relevante à compatibilidade com shulker/inventários, mas **não prova** suporte geral a stacks gigantes de outros mods. |
| **2.5.1.0** | Corrige Endermen presos em blocos passáveis; pode ser desligado por config. Corrige spam de erro em conexões status interrompidas. |
| **2.5.2.0** | API de **post processors em ItemComponentsReloadListener** para transformar components depois de providers programáticos e datapacks, respeitando remoções. |
| **2.5.2.1** | Corrige aplicação e sincronização de configs de mods que não usam `ILModConfig` quando existem duas configs SERVER; importante para players que recebiam valores default indevidos (exemplo autor: Rune Enchanting). |
| **2.5.3.0** | Novo atributo `insanelib:off_ground_mining_speed` para velocidade de mineração fora do chão, padrão 0.2 (vanilla) ou 1 sem penalidade; movido do Rune Enchanting. Mudança de provider pode afetar integração de atributo/efeitos. |
| **2.5.3.1** | Corrige receita condicional `insanelib:feature_enabled` removida no **primeiro load de datapacks** quando config SERVER ainda não foi carregada; lê toggles do arquivo que NeoForge usará (`serverconfig`, `config/` ou `defaultconfigs/`) antes de fallback aos defaults. Relevante a **receitas que só reapareciam após `/reload`**. |

**QA obrigatório antes de promoção:** comparar os consumers físicos da família Insane96, o impacto de `@ConfigType` e renomeação de chaves (migração), comportamento de configs client/server e data packs no primeiro load e após `/reload`, recipes ativadas, alterações em item components, huge stacks e contêineres do pack (Backpacks, Warehouse/Couriers de MineColonies, Quark, Storage), opacidade de bundles, loss/duplication, redstone/portais/falling blocks, loot modifiers, atributos e combate com tridentes. Em particular, não adotar o limite alto de stacks como seguro no pack sem testes end-to-end dos componentes consumidores.

**Evidências primárias:** [changelog cumulativo oficial completo na publicação 2.5.3.1 NeoForge 1.21.1](https://www.curseforge.com/minecraft/mc-mods/insanelib/files/9548012), com headings individuais de 2.4.33.1 a 2.5.3.1, e [source autor Insane96/InsaneLib linha 1.21.1](https://github.com/Insane96/InsaneLib/tree/1.21.1). Nenhum JAR binário foi instalado/inspecionado, nenhum teste runtime foi executado. **Auditoria documental fechada até 2.5.3.1; aprovação física/compatibilidade pendente**, nome v2.4.33.0 e JAR preservados.
