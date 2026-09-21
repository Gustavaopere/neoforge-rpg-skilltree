# EntityCulling

## Propriedades do registro

- **Mod:** EntityCulling
- **Arquivo JAR:** `entityculling-neoforge-1.10.5-mc1.21.1.jar`
- **Versão 1.21.1:** `1.10.5`
- **Categoria:** Performance
- **Função:** Client-side performance mod que evita renderizar entities e block entities ocultas/occluded, reduzindo custo de rendering sem alterar tick, AI ou state gameplay.
- **Dependências:** Cliente NeoForge 1.21.1. O JAR físico embarca `TRansition 1.0.21` e `TRender 1.0.15` sob META-INF; são dependências internas, não mods top-level. Sable/Create Aeronautics estão presentes e formam uma regressão multiplayer conhecida.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Rever
- **Compatibilidade/Riscos:** Issue upstream #299 reproduz jogadores/entidades desaparecendo e reaparecendo em Sable/Create Aeronautics sublevels no multiplayer; remover EntityCulling resolveu o relato. Skip/whitelist é mitigation antes de remover o mod inteiro. Riscos adicionais: bounding boxes incomuns, render outside bounds, false-positive culling, race/cache após reload e interação com render frameworks.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/entityculling
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 entradas top-level incluindo o modloader — confirma `entityculling-neoforge-1.10.5-mc1.21.1.jar`, mod id `entityculling`, runtime 1.10.5 e SHA-1 `420d0006dbc0a20b1c2dd0b3c7c20ddb5cc51fcf`. CurseForge/GitHub oficiais revalidados em 21/09/2026 mostram 1.11.1 como release NeoForge 1.21.1 publicada em 20/09/2026; há divergência física não resolvida.
- **Observações:** Runtime físico atual permanece 1.10.5, com `TRansition 1.0.21` e `TRender 1.0.15` JarJar. Há atualização oficial 1.11.1 para NeoForge 1.21.1 publicada em 20/09/2026. A linha 1.11.0/1.11.1 reestrutura a coleta de dados de (block)entities para reduzir crashes de acesso assíncrono, corrige `getRenderBoundingBox` no NeoForge e inclui hotfixes de 1.11.1; esses deltas ainda NÃO estão ativos no pack físico.
- **Atualização/Status:** REAUDITADO EM 21/09/2026 — lote físico #256 BLOQUEADO: a modlist física atual ainda contém `entityculling-neoforge-1.10.5-mc1.21.1.jar` / runtime `1.10.5`, porém CurseForge/GitHub oficiais publicaram 1.11.0 em 19/09/2026 e 1.11.1 em 20/09/2026 para NeoForge 1.21.1. Sem `✅-` até o JAR físico ser atualizado e revalidado.
- **Decisão:** Sem decisão
- **Sobreposição:** Complementa Sodium/ImmediatelyFast/BetterFpsDist em outra superfície: EntityCulling decide visibility de entities/block entities. Não deve alterar tick, simulation distance, AI ou physics.
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `entityculling-neoforge-1.10.5-mc1.21.1.jar` · mod id `entityculling` · versão `1.10.5` · NeoForge 1.21.1 · **client-side**. O JAR embarca `TRansition 1.0.21` e `TRender 1.0.15`; essas libs são JarJar internas e **não** entradas top-level.
## 1. Papel no modpack
EntityCulling é um mod de performance visual que evita desenhar entities e block entities consideradas ocultas/occluded. O objetivo é reduzir trabalho de rendering sem alterar a simulação lógica do mundo.
## 2. Authority / ownership
- **Minecraft/mod da entidade:** existência, tick, AI, physics, position e state.
- **EntityCulling:** decisão client-side de pular ou executar o render naquele frame.
- **Renderer da entidade:** desenho quando a entidade não foi culled.
Culling nunca deve ser interpretado como despawn ou ausência server-side.
## 3. Visibility culling
O projeto moderno é descrito como visibility/occlusion culling assíncrono para entities/block entities. A implementação busca determinar se o alvo está efetivamente visível antes de gastar render time.
A exata estratégia pode variar por versão/hardware; esta ficha não projeta detalhes de branches antigas sobre 1.10.5 além do contrato de visibility culling.
O changelog oficial da build física **1.10.5** registra um fix específico para crash no startup em versões NeoForge mais antigas causado por keybinds, associado ao issue #307. Isso é distinto do risco Sable/Aeronautics #299 e deve ser preservado como evidência da versão instalada, sem implicar que o pack atual sofreu esse crash.
## 4. Skip lists / whitelists
Entidades que desenham fora de suas bounds normais ou usam transforms incomuns podem precisar ser excluídas do culling por configuração. O upstream recomenda skip/whitelist para casos problemáticos.
Mitigation deve ser específica por entity/block entity quando possível, antes de desabilitar a otimização globalmente.
## 5. JarJar internas
A modlist física mostra dentro do JAR:
- `TRansition-1.0.21-1.21.1-neoforge-SNAPSHOT.jar`;
- `TRender-1.0.15-1.21.1-neoforge-SNAPSHOT.jar`.
Elas fazem parte do artefato hospedeiro e **não** devem gerar páginas top-level no catálogo.
## 6. Sable / Create Aeronautics — issue #299
Existe issue upstream aberto específico do stack **Sable / Create Aeronautics** em multiplayer. O relato descreve outros players desaparecendo/reaparecendo de forma inconsistente enquanto estão em ships/sublevels.
O autor do relato reproduziu o problema com Create Aeronautics + Sable + Create + Sodium + EntityCulling, e remover EntityCulling eliminou o sintoma. Block entities não foram afetadas no teste relatado.
## 7. Natureza provável do risco #299
A discussão aponta que entities em Sable sublevels podem ter posicionamento/render transforms que não correspondem às bounds/world coordinates esperadas pelo culling. O upstream sugeriu exclusion/skip das entities envolvidas como workaround potencial.
Isso é uma hipótese técnica sustentada pela discussão do issue, não uma correção confirmada para 1.10.5.
## 8. Stack local de performance
O pack também usa Sodium 0.8.13, ImmediatelyFast, BetterFpsDist e outros mods de performance. EntityCulling não é equivalente a eles: sua superfície é visibility de entities/block entities.
Medir FPS/frame time com e sem cada mod separadamente antes de atribuir ganho ou regressão.
## 9. EMF / modelos customizados
EMF 3.3.5 e Easy Model Entities podem produzir modelos maiores ou transforms diferentes das bounds vanilla. O próprio histórico de EME menciona correções de culling bounds.
Regression gate: modelos visualmente grandes não podem desaparecer prematuramente ao sair parcialmente da bounding box esperada.
## 10. Client-only boundary
O servidor não deve consultar EntityCulling para decidir tick, target, collision ou packet tracking. Uma entidade invisível por culling continua existindo logicamente.
Se interaction/hitbox também desaparecer, investigar outro sistema; o culling por si só deve ser uma decisão de render.
## 11. Lifecycle
Validar:
- world join/rejoin;
- chunk load/unload;
- entity spawn/despawn;
- camera teleport;
- dimension change;
- resource reload;
- config/skip-list change;
- Sable sublevel capture/release;
- ship movement rápido;
- renderer/model update.
## 12. Multiplayer
O risco mais importante desta instalação é multiplayer em airships/sublevels. Dois jogadores no mesmo ship devem continuar se vendo consistentemente durante movement, rotation e distância variável.
Também testar animais/passengers transportados, pois a discussão upstream cita sheep como outro caso observado.
## 13. Riscos
1. false-positive culling;
2. entity com transform fora do worldspace esperado;
3. player desaparecer em Sable sublevel;
4. animal/passenger desaparecer no ship;
5. model maior que bounds ser cortado;
6. renderer especial desenhar fora de bounds;
7. skip-list excessiva eliminar ganho de performance;
8. cache/visibility stale após teleport/reload;
9. interação com outro culling/render optimization;
10. confundir invisibilidade client-side com despawn server-side.
## 14. Matriz de testes
1. Benchmark de FPS/frame time em área com muitas entities/block entities.
2. Entity atrás de parede e reaparecendo ao ficar visível.
3. Large/custom model EMF/EME em ângulos extremos.
4. Block entity com renderer especial.
5. Dois jogadores em Sable/Aeronautics ship parado.
6. Dois jogadores no ship em movimento/rotação.
7. Sheep/passenger no ship.
8. Adicionar `minecraft:player` ou entity problemática à skip-list em instância de teste e comparar.
9. Chunk reload/reconnect após sublevel movement.
10. Sodium/ImmediatelyFast coexistindo.
11. Resource/config reload quando aplicável.
**Esta catalogação não afirma que esses testes foram executados.**
## 15. Evidências
- modlist física atual: JAR/mod id/version e JarJars internos;
- CurseForge oficial da 1.10.5 / File ID 8287097: fix de startup/keybind em versões NeoForge mais antigas (#307);
- upstream EntityCulling: client-side visibility/occlusion culling e skip-list model;
- GitHub issue `tr7zw/EntityCulling#299`: reprodução Sable/Create Aeronautics multiplayer, players/entities desaparecendo e mitigation discussion.
> **Boundary canônico:** EntityCulling controla somente **se algo é desenhado pelo cliente**. Ele não é authority de entidade, physics, AI ou tracking server-side.
## 16. Atualização disponível 1.11.1 — bloqueio da certificação
A autoridade física desta snapshot continua em **1.10.5**, porém o upstream publicou **1.11.0 em 19/09/2026** e **1.11.1 em 20/09/2026** para NeoForge 1.21.1. Por isso, esta entrada permanece **sem ****`✅-`** até o JAR instalado ser efetivamente atualizado e a nova build ser revalidada.
A linha 1.11.0 desacopla o Cull Thread da lógica de entidade, corrige o uso de `getRenderBoundingBox` no NeoForge e move parte da extração de dados de entities/block entities para a main thread como correção de segurança/compatibilidade, com pequeno custo de CPU documentado. A 1.11.1 é um hotfix que inclui correções para NeoForge antigo e problemas de invisibilidade/interpolação em branches recentes. Esses deltas são **informação de atualização disponível**, não comportamento atribuído ao runtime 1.10.5 atualmente instalado.
