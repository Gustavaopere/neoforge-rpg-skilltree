# Lodestone

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8190a792dca616ce9e80
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Lodestone
- **Arquivo JAR:** `lodestone-1.21.1-1.8.2.jar`
- **Versão 1.21.1:** 1.8.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, Visual
- **Função:** Biblioteca Lodestar com infraestrutura reutilizável para particles/rendering/postprocess, screenshake/sound, networking, configs, block/blockentity, attributes/items, multiblocks, worldevents/worldgen e outros systems usados por mods consumidores.
- **Dependências:** A release Lodestone 1.8.2 não lista dependências obrigatórias no projeto. Consumer concreto no pack: Malum 1.8.2, cuja publicação lista Lodestone e Curios API como required dependencies.
- **Sobreposição:** Infraestrutura compartilhada, não gameplay autônomo. Malum e demais consumers permanecem authority de seus itens, magia, mobs, worldevents e progressão; Lodestone fornece systems utilitários.
- **Compatibilidade/Riscos:** Biblioteca de alto fan-out. Riscos: API/ABI drift, render/particle/postprocess conflicts, screenshake stacking, network/side leakage, consumer state drift e config churn. Há issue upstream com Malum 1.8.2 + Lodestone 1.8.2 sobre malum-client.toml sendo corrigido repetidamente; causa raiz não confirmada.
- **Observações:** A branch 1.21 atual já avançou para 1.8.3; esta ficha usa o commit histórico 04113698… como source exato da 1.8.2. O changelog 1.8.2 cita melhoria de Screen Particle Rendering e mudanças de backend.
- **Procedência:** modlist.txt física atual + release oficial Lodestone 1.8.2 NeoForge 1.21.1 + source oficial LodestarMC/Lodestone pinado no commit histórico 04113698d68224bb5fab82e33b2fd115ff52011e, que declara exatamente 1.8.2 + relação oficial de dependência de Malum 1.8.2.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lodestone
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> ✨ **ESCOPO CANÔNICO.** Runtime físico: `lodestone-1.21.1-1.8.2.jar`, mod id `lodestone`, versão `1.8.2`. Lodestone é uma biblioteca/framework da equipe Lodestar com infraestrutura visual, networking, config e systems reutilizáveis por mods consumidores; não deve receber ownership do gameplay desses consumers.

## 1. Identidade e source pin exato
A release oficial corresponde ao JAR físico 1.8.2 para NeoForge 1.21.1. A branch `1.21` atual já avançou para 1.8.3, portanto não é usada como source corrente desta build. Foi localizado o commit histórico `04113698d68224bb5fab82e33b2fd115ff52011e`, de 25/11/2025, cujo `gradle.properties` declara exatamente `mod_version=1.8.2`, Minecraft 1.21.1 e NeoForge 21.1.x. Esse commit é o source pin desta ficha.

## 2. Papel no modpack
Lodestone centraliza componentes reutilizáveis que consumers Lodestar podem usar para apresentação, state comum, eventos e utilitários. A árvore exata 1.8.2 confirma packages de attachment, command, config, datagen, events, handlers, helpers, mixin, network, recipe, registry e um conjunto amplo de `systems`. A existência desses sistemas não significa que todos estejam ativos por si só no pack; consumers determinam uso efetivo.

## 3. Systems confirmados na 1.8.2
No source pin 1.8.2 existem systems dedicados a **attribute, block, blockentity, config, datagen, easing, enchanting, item, model, multiblock, network, particle, postprocess, recipe, rendering, screenshake, sound, texture, worldevent e worldgen**. Essa enumeração é estrutural e source-pinned; não atribui automaticamente conteúdo de um consumer a Lodestone.

## 4. Particles e rendering
Particles/rendering são superfícies centrais da biblioteca. O changelog 1.8.2 menciona melhoria em **Screen Particle Rendering** e outras mudanças de backend. Render queues, buffers, screen particles e efeitos de consumers precisam ser regressados com o stack visual real, sobretudo em cenas densas. Falha visual não deve ser confundida com falha no state de gameplay servidor.

## 5. Post-processing e textures/models
A presença dos systems `postprocess`, `texture` e `model` confirma infraestrutura específica para efeitos e assets visuais. Shader/resource reload, mudança de dimensão e fechamento de mundo precisam limpar state client-side corretamente. Um post-effect órfão não pode persistir depois que o evento/consumer que o criou terminou.

## 6. Screenshake e sound
Lodestone contém systems de screenshake e sound. Em um pack com vários mods de câmera/combate, shakes podem somar-se de forma indesejada mesmo quando cada provider funciona isoladamente. Intensidade visual/sonora é apresentação; dano, knockback e ocorrência do evento continuam pertencendo ao consumer servidor.

## 7. Networking e side boundary
Há infraestrutura de network tanto no package principal quanto nos systems. Payload handlers devem respeitar direção e lado; render/screen classes não devem vazar para dedicated server. Consumers precisam validar inputs no servidor em vez de usar packets como authority implícita.

## 8. BlockEntity, multiblock e worldevent
Os systems incluem block/blockentity, multiblock e worldevent. Isso cria superfícies potencialmente persistentes para consumers: IDs, serialized state, chunk lifecycle e event cleanup precisam ser preservados entre relog/restart. O ownership semântico continua no mod que instancia o sistema.

## 9. Worldgen
Existe infraestrutura `worldgen` no source 1.8.2. Isso não significa que Lodestone gere biomas/estruturas autônomas no pack. Qualquer feature concreta deve ser atribuída ao consumer que registra/configura o conteúdo; Lodestone fornece apenas a camada utilitária confirmada.

## 10. Consumer concreto: Malum 1.8.2
O pack contém `malum-1.21.1-1.8.2.jar`, e a publicação oficial de Malum 1.8.2 lista **Lodestone** e **Curios API** como required dependencies. Portanto Malum é um consumer concreto deste runtime. Remover ou atualizar Lodestone isoladamente pode impedir Malum de iniciar ou alterar suas superfícies visuais/state compartilhadas.

## 11. Issue upstream Malum/Lodestone
Há issue upstream aberta relatando que, com **Malum 1.8.2 + Lodestone 1.8.2 + Curios 9.5.1**, `malum-client.toml` pode ser detectado/corrigido repetidamente durante o startup, produzindo spam e reescritas. O relato suspeita da construção do config em Malum/Lodestone, mas a causa raiz não está confirmada. Nesta ficha isso é **risco upstream aplicável ao mesmo stack de versões**, não bug reproduzido no seu pack.

## 12. Config lifecycle
Como há systems/config e um relato de churn de config em consumer real, cold boot, restart e alteração de opções devem ser testados. Config válida não deve ser reescrita indefinidamente; valores client-only não devem afetar authority server-side. Logs repetitivos devem ser investigados, não ignorados como mero ruído.

## 13. Atualização e ABI
Lodestone é biblioteca de alto fan-out. Atualizar 1.8.2→1.8.3 ou posterior pode alterar API/ABI, behavior de rendering/network ou schema usado por consumers. Uma atualização só deve ser promovida após verificar todos os consumers físicos relevantes, começando por Malum.

## 14. Riscos técnicos
1. **API/ABI drift** quebrando consumers em bootstrap/runtime.
2. **Particle/render regression** após resource reload ou cenas densas.
3. **Postprocess state leak** após dimension/world/event cleanup.
4. **Screenshake stacking** com outros camera/combat systems.
5. **Network side leakage** ou validation inadequada em consumers.
6. **Config churn** no stack Malum/Lodestone, conforme issue upstream.
7. **Persistent-state drift** em multiblock/worldevent/blockentity consumers.
8. **Removal fan-out:** Malum 1.8.2 exige Lodestone.

## 15. Matriz de testes
- [ ] Dedicated server inicia com Lodestone 1.8.2 + Malum 1.8.2.
- [ ] Client conecta sem missing class/method ou side-only class crash.
- [ ] Screen particles renderizam corretamente em quantidade alta e após resource reload.
- [ ] Post-effects/particles limpam ao trocar dimensão, morrer e sair do mundo.
- [ ] Screenshake inicia/termina sem ficar preso ou acumular multiplicadores indevidos.
- [ ] Payloads de consumers não geram double-event em multiplayer.
- [ ] State persistente de consumer sobrevive a chunk unload/restart sem dupe/loss.
- [ ] `malum-client.toml` permanece estável entre startups e não reproduz correção/spam contínuo.
- [ ] Atualização futura de Lodestone é regressada contra Malum antes de adoção.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 16. Evidências e limites
Foram usados o JAR físico, a release oficial 1.8.2, o source histórico pinado no commit `04113698…`, a árvore exata de systems dessa revisão e a relação oficial de dependência de Malum 1.8.2. A issue de config permanece classificada como relato upstream sem causa raiz confirmada. Não foi atribuído a Lodestone nenhum conteúdo específico de Malum ou de outro consumer apenas pela presença da biblioteca.
