# Immersive Aeronautics

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81bfa8f3ffce0484719a
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Immersive Aeronautics
- **Arquivo JAR:** `Immersive-Aeronautics1.1.4-1.21.1-NeoForge.jar`
- **Versão 1.21.1:** 6.0.7 (arquivo Immersive Aeronautics 1.1.4)
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, Tecnologia, Visual
- **Função:** Rewrite de Immersive Portals para o stack Sable/Create Aeronautics, fornecendo o próprio `immersive_portals_core` e permitindo render/travessia de portais junto a estruturas físicas/aeronaves.
- **Dependências:** Stack funcional físico: Sable 2.0.5 + Create Aeronautics bundle 1.3.2. Este JAR é o provider do `immersive_portals_core` 6.0.7 no pack; não exige um segundo Immersive Portals top-level.
- **Sobreposição:** É a implementação/rewrite de Immersive Portals usada pelo pack, não um addon instalado ao lado de outro core. Addons que exigem Immersive Portals devem ser testados contra esta implementação 6.0.7.
- **Compatibilidade/Riscos:** Beta rewrite de core portal com muitos mixins. Riscos: portal/ship transform desync, recursive rendering, Sable craft render regression, shader/DH compatibility, physicsmod hooks, entity transfer e update ABI. 1.1.4 corrige >5 Sable contraptions e alguns bugs de Distant Horizons.
- **Observações:** Dual-version intencional: filename/publicação `Immersive Aeronautics 1.1.4`; metadata runtime `immersive_portals_core` / `Immersive Portals` / `6.0.7`. Não existe outro core top-level simultâneo na modlist física. Build 1.1.4 é Beta.
- **Procedência:** modlist.txt física atual + metadata top-level `immersive_portals_core` 6.0.7 + CurseForge oficial Immersive Aeronautics 1.1.4 file 8586854, Beta NeoForge 1.21.1 + changelogs 1.1.1/1.1.4; source exato da build não localizado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/immersive-aeronautics/files/8586854
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Immersive Aeronautics file 1.1.4 / runtime Immersive Portals core 6.0.7; rewrite authority, Sable/Create Aeronautics transport/render, DH fixes, mixin surfaces, Beta maturity, lifecycle/multiplayer, riscos e testes catalogados.
- **Histórico da decisão:** No boot de 22/08/2026 apareceu posteriormente uma exceção durante inicialização de shaders/Immersive Portals, porém o contexto já estava em broken mod state por outra falha causal. Não atribuir causalidade a Immersive Aeronautics a partir daquele boot; decisão Manter preservada até teste isolado válido.
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **DUAL VERSION CANÔNICA.** O arquivo físico é `Immersive-Aeronautics1.1.4-1.21.1-NeoForge.jar`, publicação **Immersive Aeronautics 1.1.4**. Dentro dele, o top-level mod é `immersive_portals_core`, nome runtime `Immersive Portals`, versão **6.0.7**. A diferença é intencional: o projeto é uma reescrita do core Immersive Portals para Sable/Create Aeronautics, não um JAR adulterado nem um addon que exige outro core em paralelo.

## 1. Papel e authority
Immersive Aeronautics fornece a implementação de **Immersive Portals** usada por este pack. Ele é authority de portal geometry/render/transfer e das adaptações desse core ao stack Sable/Create Aeronautics. Sable continua authority dos sublevels/physical structures e Create Aeronautics do sistema de aeronaves/contraptions; o rewrite faz a ponte entre esses domínios.

## 2. Identidade física e runtime
A release oficial 1.1.4 é o CurseForge file 8586854, Beta NeoForge 1.21.1. A metadata física registra `immersive_portals_core` 6.0.7. A varredura dos 595 top-level confirma que não existe outro `immersive_portals_core` separado. Portanto **1.1.4** identifica a distribuição/fork, enquanto **6.0.7** é a versão exposta pelo core runtime que addons podem verificar.

## 3. Dependências e stack físico
O objetivo declarado é funcionar junto de **Sable/Create Aeronautics**. O pack contém Sable 2.0.5 e Create Aeronautics bundle 1.3.2. Addons que dependem semanticamente de Immersive Portals devem resolver contra o core 6.0.7 fornecido por este JAR; não instalar um segundo core só para satisfazer o nome da dependency.

## 4. Portais e estruturas físicas
A função principal do rewrite é permitir que ships/estruturas físicas do stack Aeronautics atravessem ou sejam renderizadas através de portais de maneira coerente. Transform de posição, rotação, escala/frame e referência ao sublevel precisam convergir entre os dois lados. Uma transferência não pode criar cópia órfã da craft nem deixar entity/player em dois spaces.

## 5. Rendering através de portais
O JAR físico possui mixins dedicados a render tweaks, Create compat e Sable compat. Portais podem renderizar outro world/sublevel recursivamente; depth/stencil/framebuffer/camera transforms são boundaries sensíveis. Renderização é apresentação client-side, mas deve refletir o portal/world state autorizado pelo servidor.

## 6. Fix 1.1.4 — múltiplas Sable contraptions
A release 1.1.4 corrige um problema de renderização de Sable crafts quando havia **mais de cinco contraptions Sable simultâneas**. Essa condição deve ser reproduzida em regression test: várias crafts visíveis direta e indiretamente por portais não devem sumir, corromper transform ou quebrar o frame.

## 7. Fix 1.1.4 — Distant Horizons
O changelog 1.1.4 também corrige alguns bugs visuais com **Distant Horizons**. O JAR físico contém `imm_ptl_dh_compat.mixins.json`, confirmando uma superfície dedicada de compatibilidade. Não generalizar isso para “compatibilidade perfeita”: testar portal view, LOD transitions e dimension/sublevel changes com a configuração real do pack.

## 8. Histórico 1.1.1 relevante
A linha 1.1.1 documentou correções de skybox visto através de portal, ship rendering through portals e integração com Sable/Iris/Sodium/Veil. Também menciona lógica especial envolvendo Distant Horizons/CCME em determinada combinação. Esses pontos são histórico arquitetural da linha, não prova de que toda versão atual desses terceiros foi validada na 1.1.4.

## 9. Mixins físicos e áreas de compatibilidade
A build física declara mixins para core, Create compat, render tweaks, peripherals, Distant Horizons, Physics Mod, Fabric-adaptation/common paths, Sable compat e compat geral. Essa amplitude indica alto acoplamento a render/physics/world-transfer APIs. Update unilateral de qualquer provider atingido exige smoke test direcionado.

## 10. Beta maturity
As builds 1.1.1–1.1.4 da linha 1.21.1 são publicadas como **Beta**; 1.1.0 aparece como Alpha. Portanto a decisão `Manter` não equivale a considerar o rewrite estabilizado. Bugs de edge case em portal recursion, transfer e render continuam risco operacional maior que em uma compat visual simples.

## 11. Não promover mudanças futuras ao runtime atual
O changelog 1.1.4 informa que melhorias de transporte/fluidez de portal ainda estavam em testes para downloads futuros. Isso é roadmap, não feature comprovada da 1.1.4. O dossier não atribui essas mudanças à build instalada.

## 12. Client / server
A distribuição é Client & Server.
- **Servidor:** portal existence/linkage, entity/player transfer, destination/world state e ownership de state persistente.
- **Cliente:** recursive portal rendering, skybox, camera transforms, ship visualization e compat gráfica.

Prediction/render não pode transferir entity por conta própria; o servidor precisa autorizar a mudança de world/sublevel.

## 13. Lifecycle
Validar criação/destruição de portal, login/reconnect, entity/player crossing, ship crossing, chunk/sublevel unload, dimension transfer, server restart, resource/shader reload e atualização do rewrite. Portais com destino indisponível não devem deixar entity presa ou referência stale.

## 14. Multiplayer e exactly-once transfer
Dois jogadores e múltiplas entities podem atravessar o mesmo portal enquanto uma craft se move. A transferência deve ocorrer exatamente uma vez por entity e preservar velocity/orientation/ownership conforme o contrato real. Client render pode mostrar prévia do outro lado, mas isso não significa que a transferência server-side já ocorreu.

## 15. Addons do pack
O pack contém `Immersive Portal - Iron's Spells 'n Spellbooks Addon 1.0.1` e `Immersive Portals: True Immersion 2.0.4`. Ambos devem ser testados contra **esta implementação** do core 6.0.7, não contra a suposição de um Immersive Portals upstream diferente. Compatibilidade por mod id não garante compatibilidade de todos os mixin/internals do rewrite.

## 16. Riscos técnicos
- tratar 1.1.4 e 6.0.7 como conflito em vez de duas camadas de versionamento;
- instalar segundo `immersive_portals_core` e criar duplicate modid;
- portal/ship transfer duplicar ou perder entity/craft;
- recursive rendering ou camera transform incorreto;
- regression com mais de cinco Sable contraptions;
- DH/LOD artifact através de portal;
- shader/render mixin incompatível;
- PhysicsMod/Create/Sable target drift;
- portal destination stale após unload/restart;
- addon compilado para internals do Immersive Portals original não tolerar o rewrite.

## 17. Matriz de testes obrigatória
- [ ] Client + dedicated server boot com file 1.1.4 / core 6.0.7.
- [ ] Não há segundo `immersive_portals_core` nem duplicate-mod-id.
- [ ] Portal vanilla-scale: criação, travessia, retorno e entity transfer.
- [ ] Player cruza portal sem double teleport ou velocity corruption.
- [ ] Sable/Create Aeronautics craft atravessa/renderiza conforme support real da build.
- [ ] Cena com >5 Sable contraptions verifica regressão corrigida na 1.1.4.
- [ ] Portal view mantém skybox/camera/depth corretos.
- [ ] Compat Distant Horizons não apresenta artifact crítico na configuração real.
- [ ] Chunk/sublevel unload e server restart não deixam portal links stale.
- [ ] Dois jogadores/entities atravessando simultaneamente não duplicam transfer.
- [ ] Addon Iron's Portal Spell 1.0.1 funciona contra este core.
- [ ] True Immersion 2.0.4 é validado especificamente contra o rewrite.

## 18. Evidências e limites
- **Modlist/JAR físico:** filename 1.1.4, top-level mod id `immersive_portals_core`, runtime 6.0.7 e mixin configs citados.
- **CurseForge oficial:** file 8586854, Beta NeoForge 1.21.1, descrição do rewrite e changelog 1.1.4.
- **Histórico oficial 1.1.1:** skybox/ship rendering e integrações gráficas documentadas.
- **Limite:** source code exato da build 1.1.4 não foi localizado nesta auditoria; classes/method targets não são inventados.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
