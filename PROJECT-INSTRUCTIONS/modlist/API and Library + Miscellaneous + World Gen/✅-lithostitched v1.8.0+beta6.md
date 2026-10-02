# Lithostitched

## Propriedades do registro

- **Mod:** Lithostitched
- **Arquivo JAR:** lithostitched-1.8.0+beta6-neoforge-21.1.jar
- **Versão 1.21.1:** 1.8.0+beta6
- **Categoria:** Biblioteca, Worldgen
- **Tipo de conteúdo:** Mod
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lithostitched
- **Função:** Biblioteca/framework de worldgen que adiciona modificadores, predicates, density/placement tooling e mecanismos de compatibilidade/configurabilidade para datapacks e mods geradores de mundo.
- **Dependências:** A release não lista projeto externo obrigatório. No JAR físico, Apollib 1.2.0 está embarcado diretamente em Lithostitched e, dentro dele, json5-java 3.0.0 aparece como biblioteca aninhada; nenhum dos dois é mod top-level separado. Runtime também contém Tectonic 3.0.28, WorldWeaver 21.0.25, BetterEnd 21.0.34 e BetterNether 21.0.26.
- **Compatibilidade/Riscos:** Build física beta e infraestrutura de alto fan-out. Riscos: worldgen modifier/order drift, density caching, biome/surface-rule interop, chunk-border divergence, nested-library skew e mudanças de schema. A release estável upstream 1.8.0 corrige um caso crítico em que mundos com surface-rule injections podiam corromper sob certas combinações, além de fixes de modifiers/density bounds; por isso qualquer promoção exige backup e regressão de mundo/chunks.
- **Sobreposição:** Infraestrutura de worldgen; não substitui Tectonic/WorldWeaver/BetterEnd/BetterNether. Esses mods/datapacks continuam authority de seu terrain/biomes/features, enquanto Lithostitched fornece mecanismos de composição/modificação.
- **Observações:** Build física `1.8.0+beta6-neoforge-21.1`. Hierarquia física: Lithostitched → Apollib 1.2.0 → json5-java 3.0.0. Upstream posterior publicou a release estável `1.8.0` para NeoForge 21.1 em 22/09/2026; o delta está documentado abaixo e não altera a versão instalada.
- **Procedência:** modlist(1).txt física anexada + CurseForge oficial Lithostitched 1.8.0+beta6 e release estável 1.8.0 NeoForge 21.1 + changelog oficial da linha 1.8.0 + hierarquia JarJar física do host; consumidor Tectonic físico atual 3.0.28. Upstream revalidado em 01/10/2026.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — runtime físico permanece Lithostitched 1.8.0+beta6. A release estável 1.8.0 para NeoForge 1.21.1 foi comparada com a beta6; deltas de surface-rule safety, modifiers e density caching/bounds foram incorporados abaixo.
- **Histórico da decisão:** Durante o diagnóstico de 22/08/2026, Lithostitched apareceu como possível ponto de interação por tocar em regras de worldgen semelhantes às do WorldWeaver/BCLib, mas nunca foi o primeiro erro fatal. Após remover FirmaTerrain 1.0.0 e reativar WorldWeaver + BetterEnd + BetterNether, o stack funcionou com Lithostitched presente. Conclusão: MANTER; não houve necessidade de downgrade ou remoção.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #375: JAR `lithostitched-1.8.0+beta6-neoforge-21.1.jar`, mod id `lithostitched`, runtime `1.8.0+beta6`, SHA-1 `b8526bb7fcfb844ce91513ab349c408dd1a8279e`.

<callout icon="🗺️" color="green_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `lithostitched-1.8.0+beta6-neoforge-21.1.jar`, mod id `lithostitched`, versão `1.8.0+beta6`. É infraestrutura de **worldgen**, não um gerador de mundo autônomo: fornece modificadores, predicates, density/placement tooling e mecanismos de compatibilidade usados por mods/datapacks consumidores.
</callout>
## 1. Identidade e autoridade de versão
A modlist física confirma a build `1.8.0+beta6-neoforge-21.1`; CurseForge publica exatamente a variante NeoForge 21.1 para Minecraft 1.21.1 em 06/09/2026. A build é da linha **beta** 1.8.0. A branch antiga `1.21` do repositório público ainda declara uma versão muito anterior e, por isso, não foi tratada como source exato da beta6.
## 2. Papel e ownership
Lithostitched amplia o sistema data-driven de geração com ferramentas para alterar/compor worldgen de outros providers. Ele não deve receber ownership do terrain de Tectonic, biomes do BetterEnd/BetterNether ou conteúdo do WorldWeaver. Cada consumer mantém a semântica de suas features; Lithostitched fornece a infraestrutura de injeção/configuração.
## 3. Worldgen modifiers e toolkit
A família 1.8.0 documenta expansão de worldgen modifiers e tipos auxiliares. A beta4 para NeoForge 21.1 adicionou `add_spawn_costs`, corrigiu `offset` placement modifier e o feature type `dungeon`, além de otimizar density-function caching. A família beta6 documenta também `set_tree_decorators`, foliage/root placers adicionais e density function `cellular`. Como o changelog beta6 disponível é compartilhado entre alvos modernos, esses itens são tratados como linha da release; diferenças binárias específicas de plataforma só devem ser confirmadas no JAR/source correspondente antes de integração própria.
## 4. Density-function caching
A otimização introduzida na 1.8.0 foi descrita como especialmente relevante para worldgen pesado, citando Tectonic/Lithosphere. O pack contém Tectonic 3.0.28. Cache de density function é performance-sensitive e correctness-sensitive: resultado não pode variar por ordem de chunk, thread ou reload.
## 5. Biome e surface-rule interop
O histórico 1.7/1.8 contém fixes para biome injectors, TerraBlender e modifiers de worldgen no NeoForge. A família beta6 declara correção para modifiers de biome que não aplicavam no NeoForge e para surface rules relacionadas a TerraBlender. Isso torna biome injection/order uma superfície crítica para regressão em um pack com múltiplos providers.
## 6. WorldWeaver — integração concreta
O pack contém `WorldWeaver 21.0.25`. O changelog da família beta6 menciona correção de mensagens de log irritantes, porém inofensivas, quando World Weaver está instalado. Isso confirma interop reconhecida pelo upstream. Ausência de log não deve ser confundida com prova de worldgen correto; chunks gerados precisam ser inspecionados.
## 7. BetterEnd / BetterNether / Tectonic
O runtime físico contém BetterEnd 21.0.34, BetterNether 21.0.26 e Tectonic 3.0.28. São consumers/companheiros de um stack de worldgen amplo, mas a simples coexistência não prova dependência direta de cada um em Lithostitched. Testes precisam verificar dimension/biome/surface/terrain reais em mundos novos e chunks novos de mundos existentes.
## 8. Componentes aninhados no JAR
A hierarquia física da modlist é mais específica: **Apollib 1.2.0** aparece como JarJar direto do Lithostitched e, dentro do Apollib, está aninhado **json5-java 3.0.0**. Portanto a cadeia é `Lithostitched → Apollib 1.2.0 → json5-java 3.0.0`; nenhum desses componentes internos deve ser catalogado como mod top-level independente. Em diagnóstico de classloading, a hierarquia e as versões embarcadas precisam ser preservadas exatamente.
## 9. Histórico de decisão do pack — manter
Em 22/08/2026, Lithostitched foi investigado durante um problema de worldgen, mas não apareceu como primeiro erro fatal. Após remover **FirmaTerrain 1.0.0** e reativar WorldWeaver + BetterEnd + BetterNether, o stack voltou a funcionar mantendo Lithostitched. A conclusão histórica **Manter** permanece preservada. Esse teste anterior é evidência prática do pack, mas não substitui regressão da nova beta6.
## 10. Risco upstream atual com Tectonic beta6
Existe relato upstream aberto, datado de 06/09/2026, de crash do botão **View Presets** do Tectonic com Lithostitched 1.8.0+beta6 em NeoForge 1.21.1 por `NoClassDefFoundError` relacionado à plataforma Lithostitched. Isso é um **relato upstream não confirmado no seu runtime**, mas é diretamente relevante porque Tectonic está instalado. Deve entrar como gate de QA antes de considerar a beta6 plenamente estável.
## 11. Client / server e datapack authority
Worldgen, biome modifiers, density functions, placement e feature application são state do servidor/datapack. Telas/preset viewers são apresentação/tooling cliente. Client e server precisam carregar a mesma interpretação dos datapacks; divergência não deve ser mascarada por uma visualização correta no cliente.
## 12. Lifecycle e migração de mundo
Mudanças de worldgen afetam principalmente **chunks novos**. Atualizar/remover Lithostitched em mundo existente pode produzir fronteiras entre chunks antigos/novos, features ausentes ou diferente ordem de modifiers. Backup e teste em cópia são obrigatórios antes de migração de uma build beta.
## 13. Riscos técnicos
1. **Modifier/order drift** entre vários datapacks/providers.
2. **Density cache correctness** variando por chunk/thread.
3. **Biome/surface-rule incompatibility** com TerraBlender/consumers.
4. **Chunk-border divergence** após update/removal.
5. **Schema drift** de modifiers/predicates entre betas.
6. **Nested-library skew** com Apollib/json5 em outro host.
7. **Tectonic View Presets crash** relatado upstream para beta6 1.21.1.
8. **High fan-out:** um erro na biblioteca pode afetar vários mods de worldgen simultaneamente.
## 14. Matriz de testes
- [ ] Dedicated server inicia com Lithostitched beta6 e o stack atual.
- [ ] Mundo novo gera Overworld sem primeiro erro fatal de worldgen.
- [ ] Tectonic terrain mantém continuidade entre chunks.
- [ ] WorldWeaver carrega sem erro funcional de modifiers.
- [ ] BetterEnd e BetterNether mantêm biomes/features esperados.
- [ ] Biome worldgen modifiers realmente aplicam no NeoForge.
- [ ] Surface rules não apresentam substituição/ausência anormal.
- [ ] `View Presets` do Tectonic não reproduz o crash upstream aberto.
- [ ] Reinício e geração posterior produzem resultados estáveis sem dupe de features.
- [ ] Mundo existente aberto em cópia gera chunks novos sem fronteira/corrupção inesperada.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 15. Evidências e limites
Foram usados o JAR físico, a release oficial beta6 NeoForge 21.1, changelogs oficiais da linha 1.8.0, o repositório upstream e o histórico real de diagnóstico do pack. A branch pública antiga 1.21 não representa a beta6, portanto não foi usada para inventar registry/classes dessa build. O relato de Tectonic permanece explicitamente classificado como issue upstream aberta, não como bug reproduzido neste pack.

## 16. Atualização upstream 1.8.0 estável — não instalada

A autoridade física continua em **Lithostitched 1.8.0+beta6**. O CurseForge oficial publicou **1.8.0** estável para NeoForge 21.1/Minecraft 1.21.1 em 22/09/2026, depois da beta6 instalada.

### Deltas oficiais relevantes
A 1.8.0 é descrita como contendo principalmente mudanças de backend e separação do codebase por versão, mas o changelog também consolida alterações funcionais importantes da família:
- adiciona worldgen modifier `add_spawn_costs`;
- adiciona `set_tree_decorators`;
- adiciona foliage placers `branched_mega_jungle` e `branched_mega_pine`;
- adiciona root placer `large_mangrove`;
- adiciona density function `cellular`;
- otimiza o caching de density functions;
- corrige `offset` placement modifier com fields que não deviam ser opcionais;
- corrige o feature type `dungeon`;
- corrige mensagens de log inofensivas quando World Weaver está instalado;
- corrige cálculo/assunção de minimum value em density functions, incluindo casos como `sample_density` que usavam 0 em vez de um mínimo numérico efetivo;
- **corrige um problema crítico em que mundos com surface rule injections podiam corromper sob determinadas condições**, com exemplo upstream envolvendo Regions Unexplored + TerraBlender.

### Impacto para o pack
O último fix é material porque o pack usa um stack de worldgen amplo e Lithostitched tem fan-out alto. A ausência de Regions Unexplored no exemplo não elimina o risco: a causa publicada envolve surface-rule injections, portanto o gate deve focar integridade de worldgen e não um mod específico.

### Gate de promoção beta6 → 1.8.0
- [ ] Backup/cópia de mundo antes da promoção.
- [ ] Dedicated server inicia com Tectonic, WorldWeaver, BetterEnd e BetterNether atuais.
- [ ] Mundo novo gera Overworld/End/Nether sem registry/surface-rule errors.
- [ ] Mundo existente abre e gera chunks novos sem corrupção ou fronteiras anormais.
- [ ] Biome/surface-rule injections aplicam no NeoForge sem perda de terrain/surface.
- [ ] Density caching produz resultado determinístico entre restart e ordem de geração.
- [ ] Modifiers `add_spawn_costs`, `set_tree_decorators` e placement `offset` não quebram datapacks consumidores.
- [ ] Tectonic View Presets é revalidado; o relato histórico da beta6 não é presumido corrigido sem reprodução.
- [ ] WorldWeaver não emite regressões funcionais apesar do fix de logging.

Fonte upstream: CurseForge Lithostitched 1.8.0 para NeoForge 21.1, file ID 8944659, e changelog oficial da release. Nenhum teste acima foi executado nesta atualização documental.
