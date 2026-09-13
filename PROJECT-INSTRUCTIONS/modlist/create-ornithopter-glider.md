# Create: Ornithopter Glider

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81b3a9abcc192c2a174d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Ornithopter Glider
- **Arquivo JAR:** `createornithopterglider-1.2.0-1.21.1.jar`
- **Versão 1.21.1:** 1.2.0-1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Exploração, QoL
- **Função:** Adiciona um planador mecânico/ornithopter inspirado em máquinas de voo de Leonardo da Vinci, com impulso e mobilidade aérea integrados ao Create.
- **Dependências:** Create obrigatório. Curios API é opcional na 1.2.0 e o pack contém Curios 9.5.1. Caelus deixou de ser dependência nesta build. Publicação oficial: 1.2.0+1.21.1; runtime físico: 1.2.0-1.21.1.
- **Sobreposição:** Overlap de mobilidade aérea com Create Jetpack e outras extensões de voo, mas Ornithopter é glider com flap/cooldown, não propulsão sustentada. Comparar movimento/custo e impedir stacking abusivo.
- **Compatibilidade/Riscos:** Riscos: spam de input bypassar cooldown; client/server movement divergence; equip Curios stale; cooldown config mismatch; stacking com Create Jetpack/outros movement mods; fall/glide state residual; regressão de Armor Stand/player animation; optional Curios classloading; version-label normalization errada.
- **Observações:** JAR `createornithopterglider-1.2.0-1.21.1.jar`, mod id `createornithopterglider`, runtime `1.2.0-1.21.1`. O arquivo público correspondente é rotulado `1.2.0+1.21.1`; a distinção foi preservada. Source matching não foi localizado.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level; authority de filename/runtime + release/changelog oficiais Create Ornithopter Glider 1.2.0 para NeoForge 1.21.1.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-ornithopter-glider
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 1.2.0 com Mechanical Crafting, gliding/flap boost, cooldown configurável, Curios opcional, remoção de Caelus e fixes de Armor Stand/player animations catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🪽 **Identidade física confirmada:** `createornithopterglider-1.2.0-1.21.1.jar`, mod id `createornithopterglider`, runtime `1.2.0-1.21.1`. A publicação oficial usa o label `1.2.0+1.21.1`; a diferença `+` no label público versus `-` no runtime físico é preservada, sem normalização indevida.

## 1. Papel e authority
Create Ornithopter Glider adiciona um glider inspirado nas máquinas voadoras de Leonardo da Vinci, integrado visual e progressivamente ao Create. O addon owns o item, lógica de flap/boost, cooldown e integração de slot; Minecraft/Curios continuam authorities de movimento base/equip slots e Create da receita mecânica usada para obtenção.

## 2. Obtenção por Mechanical Crafting
A documentação oficial informa que o Ornithopter Glider é obtido por **Mechanical Crafter**. A recipe carregada no Recipe Manager é authority de ingredientes/shape; JEI/Ponder, quando presentes, apenas apresentam a receita.

## 3. Voo planado
O item funciona como glider: o jogador pode planar e usar batidas de asa para ganhar impulso. Velocity, fall state e movimento efetivo precisam convergir no servidor; animação client-side não pode conceder deslocamento funcional sozinha.

## 4. Flap/boost por Space
O controle padrão publicado usa **Space** para carregar/acionar o flap/boost. Input do cliente deve resultar em uma única solicitação válida por ação; key repeat ou lag não pode multiplicar boosts dentro do mesmo cooldown.

## 5. Cooldown configurável — 1.2.0
A 1.2.0 adiciona arquivo de configuração com **cooldown em ticks**. A documentação geral cita dois segundos como comportamento padrão histórico, mas a configuração efetiva da build é authority. O catálogo não fixa 40 ticks como valor universal se o arquivo do pack puder alterá-lo.

## 6. Curios opcional
A release 1.2.0 torna **Curios API opcional**. O pack contém Curios 9.5.1, portanto o caminho de equip via Curios é concretamente relevante. A ausência futura de Curios deve degradar para o modo suportado pelo mod, sem classloading obrigatório indevido.

## 7. Accessories / Curios Compat Layer
A página oficial também menciona Accessories com Curios Compat Layer como alternativa de slot em ecossistemas compatíveis. Isso é suporte upstream; só deve ser tratado como ativo quando os providers correspondentes estiverem fisicamente presentes e na versão suportada.

## 8. Remoção da dependência Caelus
A 1.2.0 remove a dependência de **Caelus API**. Logo Caelus não deve ser adicionado ao pack apenas para satisfazer esta build e sua ausência não é uma pendência. Dependências antigas não podem ser herdadas de páginas/reviews de versões anteriores.

## 9. Armor Stand — fix 1.2.0
A release corrige a exibição do glider em **Armor Stands**. O item deve renderizar na orientação/slot correto sem duplicar modelo ou produzir transform inválido. Esse fix é client-facing, mas inventory/equipment state continua server-side.

## 10. Player animation compatibility — fix 1.2.0
A 1.2.0 também corrige animações quebradas quando usados **player animation textures/mods**. O pack possui uma pilha ampla de animação. Isso torna render/pose coexistence um gate concreto, especialmente em first-person/third-person e durante flap.

## 11. Equip state
Se o glider puder operar equipado por Curios ou pelo mecanismo próprio da build, deve existir uma única instância lógica ativa. Mover o item entre inventory/slot durante voo não pode manter flag de gliding ou cooldown de uma cópia inexistente.

## 12. Cooldown lifecycle
Cooldown precisa persistir conforme implementação durante ticks, troca de slot e lag. Relog, dimension travel ou death não devem permitir bypass indevido se o state for persistente, nem preservar cooldown fantasma se a implementação o reseta deliberadamente. A política exata deve ser observada no runtime.

## 13. Movimento e anti-stacking
O pack contém outras fontes de mobilidade/voo, como Create Jetpack e extensões de backtank, além de mods de movimento. Boost do Ornithopter deve combinar com eles de forma previsível sem somar aceleração múltiplas vezes por um único input ou permitir velocidade infinita por alternância de equipamentos.

## 14. Create Jetpack overlap
Create Jetpack oferece propulsão sustentada, enquanto Ornithopter é um glider com flap/boost. Há overlap de mobilidade aérea, não equivalência funcional automática. Balanceamento deve comparar custo, controle, velocidade, altitude e disponibilidade real no pack.

## 15. Fall damage e landing
Qualquer modificação de queda/planagem precisa terminar corretamente ao pousar, entrar em água, morrer, teleportar ou desequipar. O addon não deve deixar fall-distance, gliding flag ou velocity state stale depois que o voo termina.

## 16. Multiplayer
O servidor deve validar que o jogador possui/equipa o glider válido antes de aplicar boost. Pacotes repetidos, jogador espectador, death transition ou item removido no mesmo tick não podem conceder impulso posterior.

## 17. Client/server
Equip/ownership, cooldown funcional e movimento authoritative pertencem ao servidor. Key input, animação das asas, modelo do item e Armor Stand render são client-facing. Config que afete gameplay deve ser coerente com a authority da build/servidor.

## 18. Resource/animation lifecycle
Resource reload, mudança de texture pack e player animation mods podem reconstruir modelos/poses. O glider não deve perder binding visual, travar pose ou quebrar outras animações depois de reload. O fix 1.2.0 torna essa superfície especialmente relevante.

## 19. Versionamento público versus runtime
A publicação oficial nomeia a build como `1.2.0+1.21.1`, enquanto a metadata física registra `1.2.0-1.21.1`. Para presença/versionamento operacional, a modlist física prevalece; o label público é guardado apenas como referência de release correspondente.

## 20. Riscos
1. Key repeat dispara múltiplos boosts no mesmo cooldown.
2. Cliente aplica movimento sem validação server-side.
3. Troca Curios/inventory deixa glider ativo sem item equipado.
4. Cooldown é bypassado por relog/dimension/slot swap.
5. Cooldown config client/server diverge.
6. Boost acumula indevidamente com Jetpack/outros movement mods.
7. Landing/desequip mantém gliding/fall state stale.
8. Armor Stand reproduz render quebrado corrigido na 1.2.0.
9. Player animation stack quebra flap/pose novamente.
10. Optional Curios tenta classloadar API ausente em outra instalação.
11. Caelus é tratado erroneamente como dependência obrigatória.
12. Label público `+` é confundido com runtime físico `-`.

## 21. Matriz de testes
- [ ] Dedicated server inicia com Ornithopter 1.2.0-1.21.1 + Create 6.0.10.
- [ ] Mechanical Crafting produz o item conforme recipe ativa.
- [ ] Gliding funciona e termina corretamente ao pousar/desequipar.
- [ ] Um pressionamento válido gera um único flap/boost.
- [ ] Cooldown efetivo segue a configuração do pack.
- [ ] Spam de Space não bypassa cooldown.
- [ ] Curios 9.5.1 equipa/remove o glider sem state fantasma.
- [ ] Relog/dimension/death mantêm/resetam cooldown conforme implementação sem exploit.
- [ ] Create Jetpack e outros movement mods não multiplicam impulso indevidamente.
- [ ] Armor Stand renderiza corretamente conforme fix 1.2.0.
- [ ] Player animation/resource packs coexistem sem quebrar pose/asa.
- [ ] Ausência de Caelus não causa erro de dependência.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 22. Evidências e limites
A modlist física confirma JAR, mod id e runtime `1.2.0-1.21.1`. A release oficial correspondente confirma NeoForge 1.21.1, Client & Server, Mechanical Crafting, flap/boost, Curios opcional, config de cooldown, remoção de Caelus e fixes de Armor Stand/player animations. Source matching não foi localizado; packet names, classes, valores efetivos de impulso e serialização permanecem fail-closed.

> 🔒 **Boundary canônico:** Ornithopter controla glider/boost/cooldown; o servidor valida equipamento e movimento. Config efetiva prevalece sobre defaults históricos, e runtime físico `1.2.0-1.21.1` prevalece sobre o label público `1.2.0+1.21.1`.
