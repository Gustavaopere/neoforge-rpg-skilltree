# FirstPerson — 2.7.2

> **Runtime físico confirmado:** `firstperson-neoforge-2.7.2-mc1.21.1.jar` · mod id `firstperson` · versão `2.7.2` · NeoForge 1.21.1 · client-side. O JAR embarca `TRansition 1.0.21` e `TRender 1.0.15` sob `META-INF/jars`; são dependências internas, não top-levels.

## 1. Papel no modpack
FirstPerson (First-person Model) renderiza o corpo/modelo de terceira pessoa do jogador enquanto a câmera permanece em primeira pessoa. O objetivo é imersão visual: ver torso/pernas/animações sem converter a lógica de gameplay para terceira pessoa.

## 2. Authority / ownership
- **Minecraft/servidor:** posição, hitbox, equipamento, mão ativa e gameplay state.
- **FirstPerson:** composição/render local do player body em first-person.
- **Not Enough Animations:** pode fornecer animações de terceira pessoa mais adequadas ao modelo visível.
- **Epic Fight:** controla seu battle-mode/player animation framework quando ativo.

FirstPerson nunca deve ser authority de hit, reach, damage ou equip state.

## 3. Modelo de operação
O projeto reaproveita o third-person player model e o apresenta a partir da câmera first-person. Isso torna compatibilidade com mods de player model/render uma questão de transform/culling, não de state gameplay.

## 4. F6 e Vanilla Hands
A documentação pública registra toggle por **F6** e opção **Vanilla Hands**, que pode alternar a apresentação de mãos vanilla conforme ângulo/estado do item. Keybind/config são client-local; não devem gerar network state obrigatório.

## 5. Not Enough Animations
O projeto recomenda Not Enough Animations para preencher movimentos de terceira pessoa que normalmente não seriam visíveis em first-person. Ele está instalado no pack e deve ser testado em conjunto, especialmente walking/sprinting/swimming/equip/use.

## 6. Epic Fight — limitação funcional conhecida
A auditoria anterior confirmou que, em battle mode, o Epic Fight suprime o body render do FirstPerson através do seu activation/render path. Isso não impede startup nem uso geral: é uma limitação de apresentação durante o modo de combate.

A bridge opcional **Epic Fight x First Person Model 1.3** pode reativar/ajustar esse comportamento e corrigir clipping de head/hat, mas não está presente na modlist atual. Não tratá-la como dependência obrigatória.

## 7. Player model stack
Mods que substituem o player model de forma não-vanilla podem exigir compatibilidade específica, porque FirstPerson precisa ocultar cabeça/partes que atravessariam a câmera. Custom Player Models, Fresh/player animations e armor 3D tornam head/body visibility e transforms regression gates centrais.

## 8. Armor e cosmetics
Fantasy Armor e Cosmetic Armor Reworked podem adicionar geometry ou separar item funcional/visual. FirstPerson deve apenas renderizar o state client-visible; não pode escolher qual armadura fornece stats nem duplicar cosmetic slots.

## 9. Dependências embarcadas
`TRansition 1.0.21` e `TRender 1.0.15` aparecem dentro do JAR sob `META-INF/jars`. Pelo protocolo do projeto, permanecem documentadas nesta ficha e **não recebem páginas top-level** apenas por estarem embarcadas.

## 10. Client-only boundary
O projeto é visual/client-side e funciona em servidores sem instalação server-side. O servidor não deve depender de F6/config/render do jogador. Dois clientes podem usar configurações de first-person diferentes sem alterar world state.

## 11. Lifecycle
Validar camera enter/exit, F6 toggle, perspective switching, login/reconnect, death/respawn, swimming/crawling, sleeping, riding, elytra/flight, dimension change, battle mode toggle, item use, resource reload e player-model reload.

## 12. Multiplayer
A renderização é local. O client não envia autoridade de pose/hitbox extra. Outro jogador deve continuar vendo o player pelo renderer normal/animation stack; bugs locais não podem mudar reach ou combat settlement.

## 13. Riscos
1. head/hat clipping dentro da câmera;
2. arms/hands duplicados;
3. Epic Fight suprimir body no battle mode;
4. bridge futura aplicar fix em dobro se Epic Fight mudar comportamento;
5. Custom Player Models alterar skeleton/visibility;
6. armor 3D atravessar câmera;
7. swimming/crawling/riding transforms incorretos;
8. shader/culling esconder partes erradas;
9. config/keybind reset após update;
10. confundir posição visual da arma com hitbox/reach lógico.

## 14. Matriz de testes
1. First-person baseline parado/caminhando/correndo.
2. F6 on/off e Vanilla Hands.
3. Not Enough Animations ativo.
4. Epic Fight battle mode on/off.
5. Attack/dodge/guard/cast com Epic Fight sem bridge FPM.
6. Fantasy Armor + Cosmetic Armor.
7. Custom Player Models/player animation stack.
8. Swim/crawl/ride/fly/sleep.
9. Death/respawn e dimension change.
10. Multiplayer: client com e sem FirstPerson.
11. Resource pack/shader real do pack.

**Esta catalogação não afirma que esses testes foram executados.**

## 15. Evidências
- modlist física canônica: JAR/mod id/version/hash + JarJar internas TRender/TRansition;
- projeto oficial First-person Model: client-only, body em first-person, F6, Vanilla Hands e recomendações de animation compat;
- auditoria existente da interação Epic Fight 21.17.3.1 ↔ FirstPerson 2.7.2;
- Not Enough Animations presente no pack.

> **Boundary canônico:** FirstPerson controla somente a **apresentação local da câmera/player model**. O servidor e os mods de gameplay continuam authority de pose lógica, hitbox, equipamento e combate.