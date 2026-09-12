# Fancy World Animations — 1.2.31

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81328770f5f43a2446f3  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** Fancy World Animations
- **Arquivo JAR:** `fwa+1.21.1-neoforge-1.2.31.jar`
- **Versão 1.21.1:** `1.2.31`
- **Categoria:** Visual; QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://github.com/maDU59/FancyWorldAnimations/tree/neoforge-1.21.1
- **Função:** Animações client-side para blocos interagíveis do mundo, como portas, trapdoors, levers, buttons, jukeboxes e lanternas; interpola transições sem alterar o block state lógico.
- **Dependências:** Client-side NeoForge 1.21.1. Source 1.2.31 usa NeoForge baseline 21.1.191. Não é dependency server-side de gameplay.
- **Compatibilidade/Riscos:** Ghost/stale visual após unload, culling/bounding-box cache, divergência visual sob updates rápidos e conflitos com resource/model loaders. 1.2.31 melhora frustum culling e adiciona compatibilidade de occlusion com EntityCulling.
- **Sobreposição:** Pode cruzar visualmente com resource packs, Fusion e outros model/animation layers, mas não substitui lógica de blocos. Evitar duas camadas transformando a mesma geometry sem compatibilidade explícita.
- **Observações:** Client-only: o servidor/provider original permanece authority de block state, redstone e colisão. A animação deve sempre convergir para o state sincronizado, inclusive após culling, reload e reconnect.
- **Procedência:** modlist.txt física atual de 09/09/2026 + source oficial maDU59/FancyWorldAnimations branch neoforge-1.21.1 exatamente em 1.2.31 + release oficial CurseForge file 8221257.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Fancy World Animations 1.2.31 source-pinned; authority visual, targets documentados, culling/EntityCulling, lifecycle, multiplayer, riscos e testes catalogados.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `fwa+1.21.1-neoforge-1.2.31.jar`, mod id `fwa`, NeoForge 1.21.1. O branch oficial `maDU59/FancyWorldAnimations:neoforge-1.21.1` declara exatamente `1.2.31`. Fancy World Animations é **client-side**: anima a apresentação de blocos interagíveis sem substituir o block state autoritativo do mundo.

## 1. Identidade e versão
- **Mod:** Fancy World Animations.
- **JAR físico:** `fwa+1.21.1-neoforge-1.2.31.jar`.
- **Mod id:** `fwa`.
- **Versão:** `1.2.31`.
- **Minecraft / loader:** 1.21.1 / NeoForge.
- **Source pin:** branch `neoforge-1.21.1`, exatamente 1.2.31.

## 2. Papel no modpack
O mod adiciona transições/animações visuais a objetos do mundo que normalmente mudariam instantaneamente entre estados. A documentação oficial cita portas, trapdoors, levers, buttons, jukeboxes e lanternas oscilantes, entre outros alvos suportados. O objetivo é fluidez visual e imersão; a lógica de abertura, redstone, colisão e interação continua pertencendo ao bloco/provider original.

## 3. Modelo de autoridade
- **Servidor/Minecraft/mod do bloco:** block state, redstone, colisão, inventory e efeito da interação.
- **FWA:** interpolação/animação visual client-side derivada desse state.
A animação não pode atrasar ou adiantar logicamente uma porta, botão ou lever; o renderer deve convergir para o state recebido do servidor.

## 4. Culling e versão 1.2.31
A release 1.2.31 introduziu/corrigiu pontos diretamente relevantes:
- compatibilidade de occlusion com **EntityCulling**;
- frustum culling melhorado usando cached bounding boxes.
Como EntityCulling está no tipo de stack visual/performance deste pack, validar objetos animados entrando/saindo do frustum e sendo ocultados/reexibidos sem ghost state.

## 5. Render lifecycle
Blocos animados podem precisar de state temporário no cliente entre dois estados discretos. Esse state deve ser descartável e reconstruível. Resource reload, chunk unload, dimension change e reconnect não podem deixar transforms persistentes depois que o world state já mudou.

## 6. Client-only boundary
A distribuição é client-side. O servidor não precisa usar FWA para determinar qualquer ação. Em multiplayer, clientes com e sem o mod podem ver transições diferentes, mas precisam concordar sobre o estado lógico do bloco.

## 7. Compatibilidade com resource/model stack
Resource packs, model loaders e mods que alteram block models podem afetar pivot, geometry e bounding boxes usados pelas animações. Fusion e outras bibliotecas visuais do pack podem coexistir, mas não há integração automática presumida. Cada combinação que altere o mesmo modelo precisa de teste visual.

## 8. Multiplayer
O caso crítico é mudança rápida de state por redstone ou dois jogadores interagindo enquanto a animação anterior ainda está em andamento. O client deve cancelar/reorientar a interpolação para o novo state autoritativo, não acumular uma segunda transição independente.

## 9. Riscos técnicos
- ghost visual após chunk unload/reload;
- culling esconder objeto animado incorretamente;
- cached bounding box ficar stale;
- animação divergir do block state real em updates rápidos;
- model/resource pack alterar pivot ou geometry esperada;
- duas camadas de animação transformarem o mesmo bloco;
- regressão de FPS em áreas com muitos blocos animados;
- classloading client indevido por integração comum/server.

## 10. Matriz de testes obrigatória
- [ ] Client boot com FWA 1.2.31.
- [ ] Dedicated server sem depender do mod para gameplay.
- [ ] Porta/trapdoor/button/lever em interação manual e redstone rápida.
- [ ] Jukebox e lanternas/alvos suportados no resource stack real.
- [ ] EntityCulling ativo: entrar/sair de occlusion sem ghost.
- [ ] Frustum edge: bloco animando enquanto entra/sai da câmera.
- [ ] Chunk unload/reload e dimension change durante animação.
- [ ] Reconnect com bloco no estado final correto.
- [ ] Dois jogadores alterando o mesmo bloco em sequência rápida.
- [ ] Resource reload e coexistência com model loaders/resource packs reais.

## 11. Evidências e limites
**Source primário pinado:** `maDU59/FancyWorldAnimations`, branch `neoforge-1.21.1`, exatamente 1.2.31.
**Release oficial:** CurseForge file 8221257, NeoForge 1.21.1, com fixes de EntityCulling e frustum culling.
**Limite:** não foi feita uma enumeração exaustiva de todos os blocos suportados pelo registry interno; apenas categorias/alvos sustentados pela documentação oficial são afirmados.
**Nenhum teste de runtime foi executado nesta catalogação.**
