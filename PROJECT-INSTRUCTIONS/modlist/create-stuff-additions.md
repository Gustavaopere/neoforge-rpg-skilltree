# Create Stuff 'N Additions

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db819699f3fabd74284d45
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Stuff 'N Additions
- **Arquivo JAR:** `create-stuff-additions1.21.1_v2.1.4b.jar`
- **Versão 1.21.1:** 2.1.4.
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, QoL
- **Função:** Addon do Create que adiciona equipamentos, ferramentas e gadgets alimentados por recursos/energia do ecossistema Create, incluindo opções de mobilidade e utilidade pessoal.
- **Dependências:** Create 6.0.10 é a base funcional. Pack físico também contém Curios API 9.5.1+1.21.1, Create: Jetpack Curios 1.2.0, Create SA Curios Jetpacks (filename 1.2.4; runtime 1.2.22), Create Stuff & Netherite Additions 1.2 e Sable: Stuff&Additions Compatibility 1.0.3; são integrações/addons separados, não conteúdo interno do host.
- **Sobreposição:** É provider de seus próprios equipamentos/gadgets. Curios bridges apenas mudam slot/equip behavior; Sable SA Compat adapta o conteúdo a contraptions físicas; Create Stuff & Netherite Additions estende tiers/conteúdo. Nenhuma dessas bridges substitui o host.
- **Compatibilidade/Riscos:** Riscos em fuel/capacity migration, jetpack/exoskeleton movement, Curios equip/render, first-person/player-model hooks, Sable/Aeronautics movement e enchantments desativados por config. A linha 2.1.3 migrou gadgets/equipamentos para o sistema NeoForge Capacity; bridges antigas de Tank Fix podem conflitar. Runtime físico `2.1.4.` diverge do filename/publicação.
- **Observações:** mod id `create_sa`; JAR `create-stuff-additions1.21.1_v2.1.4b.jar`; runtime literal `2.1.4.`; publicação upstream `2.1.4.b`. 2.1.4.b adiciona indicador visual para enchantment desativado por config. 2.1.3 registra capacity em Jetpacks/Exoskeletons/gadgets e compatibilidade de filling com tanks/backpacks modded.
- **Procedência:** modlist.txt física atual de 08/09/2026 (594 JARs top-level) + metadata runtime `create_sa` 2.1.4. + CurseForge/Modrinth oficiais da release 2.1.4.b e changelog 2.1.3 + integrações físicas Curios/Sable/Create SA do pack.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-stuff-additions
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio reconstruído; identity drift v2.1.4b↔2.1.4.b↔runtime `2.1.4.`, equipment/capacity, config/enchantments, Create processing, Curios/Sable bridges, lifecycle e risks catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🧰 Identidade física confirmada: `create-stuff-additions1.21.1_v2.1.4b.jar`, mod id `create_sa`, NeoForge 1.21.1. O filename usa `v2.1.4b`, a publicação oficial usa `2.1.4.b` e a metadata runtime da modlist declara literalmente **`2.1.4.`**. Essas evidências permanecem separadas.

## 1. Papel e authority
Create Stuff 'N Additions adiciona **equipamentos, ferramentas e gadgets pessoais** baseados nos recursos e no processing do Create. O addon controla o state, consumo, efeitos e recipes de seu próprio conteúdo; **Create 6.0.10** continua authority das primitives de kinetic/processing que o addon utiliza.
O escopo público atual inclui Jetpacks, Exoskeletons e gadgets capazes de ampliar mobilidade ou capacidades físicas do jogador.

## 2. Famílias de equipamento
A documentação oficial apresenta equipamentos que podem aumentar força ou velocidade de mineração e também oferecer novas formas de movimento, incluindo flutuação, impulsos/bounce e voo.
Esses efeitos devem existir somente enquanto o equipamento/state exigido estiver válido. Integrações externas de atributos, perks ou movement não devem reaplicar o mesmo bônus apenas por reconhecer o item.

## 3. Jetpacks
Jetpacks são uma das superfícies principais do addon e oferecem mobilidade aérea; a própria página oficial demonstra uso para **swim e fly**.
Input de movimento é client-facing, mas capacidade/combustível, validade do equipamento e state persistente precisam permanecer autoritativos no lado comum/server. Disconnect, death ou troca de slot não podem deixar thrust ativo sem o equipamento correspondente.

## 4. Exoskeletons
Exoskeletons são apresentados como equipamento para **hit harder e dig faster**. O addon é owner dos modifiers próprios da peça.
Equip/unequip, respawn e reconnect devem aplicar/remover modifiers exatamente uma vez. Outros sistemas RPG não devem duplicar Strength/Haste-equivalent effects sem integração explícita.

## 5. Gadgets
A linha inclui gadgets de utilidade; a documentação pública mostra, entre outros comportamentos, interação com mineração em área, movimentação de mobs e charging por enchantments próprios.
Não congelar aqui nomes de classes, valores, alcance ou custo que não estejam pinados à build 2.1.4b. O recipe/config runtime permanece a authority concreta.

## 6. Combustíveis e recursos
O projeto documenta diferentes recursos conforme a família/material do equipamento: **steam**, **heat energy** e **hydraulic energy**, usando combinações de água e/ou fuel conforme o caso.
Essa semântica pertence ao próprio addon. Não unificar automaticamente esses saldos com Cold Sweat heat, New Age industrial heat, FE ou outro sistema energético apenas por similaridade nominal.

## 7. NeoForge Capacity — mudança estrutural 2.1.3
A release **2.1.3** migrou Jetpacks, Exoskeletons e gadgets para um sistema de **capacity**, permitindo filling via Spout e compatibilidade com tanks/backpacks modded.
O changelog alerta que bridges antigas de Tank Fix podiam causar dificuldades porque faziam a ponte entre o antigo NBT hardcoded e o sistema NeoForge Capacity. No pack atual, qualquer integração deve consultar a capability/state vigente em vez de manter um segundo saldo NBT paralelo.

## 8. Filling via Create
Equipamentos compatíveis podem ser preenchidos por mecanismos como **Spout**, conforme a linha 2.1.3. O Spout/Create controla a operação de processing/transfer; Stuff 'N Additions controla a capacidade e o recurso aceito pelo item.
Uma operação válida deve debitar o fluido/provider e creditar a capacity exatamente uma vez, inclusive sob lag/retry.

## 9. Tanks e backpacks externos
A mesma linha anuncia filling por tanks ou backpacks modded. Compatibilidade deve ser capability-driven e validada contra os providers efetivamente presentes no pack.
Não presumir que qualquer container modded funciona por nome ou por possuir um tooltip de fluido; o handler real precisa expor o contract esperado pela build.

## 10. Enchantments e config — 2.1.4b
A build física corresponde à publicação **2.1.4b**, cujo changelog adiciona um **indicador visual para mostrar quando um enchantment está desabilitado na config**, além de atualizar o logo e aplicar pequenos fixes.
Esse indicador é apenas apresentação. A config continua authority de habilitação; um enchantment desabilitado não deve produzir efeito só porque ainda aparece renderizado em item/tooltip.

## 11. Create processing e recipes adicionais
O projeto também adiciona recipes para tornar certos itens vanilla craftáveis por novas rotas Create, citando exemplos como Chainmail Armor, Coral, Netherrack, Crying Obsidian e Magma Cream através de processos como **haunting** e **filling**.
Recipe Manager/datapack é authority do custo/output. Se KubeJS ou outro datapack substituir uma rota, o recipe original precisa ser removido ou a coexistência deve ser intencional.

## 12. Curios e bridges do pack
A modlist física atual contém **Curios API 9.5.1+1.21.1** e bridges top-level relacionadas ao ecossistema de jetpacks, incluindo `Create: Jetpack Curios` 1.2.0 e `Create SA Curios Jetpacks` com filename 1.2.4 e runtime declarado 1.2.22.
Essas bridges podem mudar slot/equip/render behavior, mas **não transferem a authority de combustível, capacity ou efeito do equipamento** para Curios. Um item não pode existir funcionalmente em slot vanilla e Curios ao mesmo tempo após uma transição.

## 13. Create Stuff & Netherite Additions
O pack também instala **Create Stuff & Netherite Additions 1.2**, que é um addon separado. Conteúdo/tier adicional desse mod não deve ser atribuído ao host `create_sa`.
Compatibilidade precisa preservar o contract de capacity/equip do host e não copiar state por NBT paralelo.

## 14. Sable / Aeronautics
A modlist instala **Sable: Stuff&Additions Compatibility 1.0.3**. Essa bridge trata coexistência de S&A com o stack Sable/Aeronautics.
Stuff 'N Additions continua owner dos equipamentos; Sable/Aeronautics continuam owners de bodies/sublevels e movement físico. Em contraptions móveis, não aplicar duas transformações de movimento nem duplicar consumo do equipamento.

## 15. Client/server e multiplayer
Capacity, consumo, modifiers, recipes e item state são common/server-authoritative. Models, HUD, animação, tooltip e o indicador de enchantment desativado são client-facing.
Em multiplayer, troca rápida de slot, packet retry ou prediction de voo não pode clonar combustível/capacity nem manter modifier após o item sair do slot válido.

## 16. Lifecycle
Validar:
- equip/unequip;
- filling por Spout/container;
- consumo até vazio;
- troca vanilla ↔ Curios quando bridge estiver ativa;
- death/respawn;
- dimension change;
- disconnect/reconnect;
- server restart;
- resource reload;
- movement em Sable/Aeronautics quando a compat estiver ativa.

Após cada transição deve existir um único saldo de capacity e uma única aplicação de modifiers.

## 17. Divergência de identidade/versionamento
Preservar três identificadores:
- filename físico: `v2.1.4b`;
- publicação oficial: `2.1.4.b` / título `V2.1.4b`;
- runtime metadata: **`2.1.4.`**.

O campo de versão do catálogo segue a metadata física; não corrigir a string para ficar visualmente igual ao release label.

## 18. Riscos técnicos
1. Capacity duplicada entre capability atual e NBT/bridge legado.
2. Fuel debitado duas vezes por Spout/tank/backpack integration.
3. Jetpack movement continuar após item inválido/unequip.
4. Exoskeleton modifier duplicar após reconnect/respawn.
5. Curios bridge deixar duas cópias funcionais do mesmo equipamento.
6. Enchantment desativado na config continuar executando efeito.
7. Recipe Create duplicado por KubeJS/datapack.
8. Sable/Aeronautics aplicar movement transform concorrente.
9. Runtime `2.1.4.` ser normalizado e perder rastreabilidade.
10. Integração antiga baseada em NBT ignorar NeoForge Capacity.

## 19. Matriz de testes
1. Dedicated server boot com Create 6.0.10 + S&A físico.
2. Jetpack: equip, voo, consumo e esvaziamento da capacity.
3. Exoskeleton: modifiers antes/depois de equip, death e reconnect.
4. Gadget smoke-test com config/enchantments atuais.
5. Spout filling: débito/crédito exactly once.
6. Filling por container externo suportado, sem dupla capacidade.
7. Enchantment desabilitado: indicador aparece e efeito não executa.
8. Recipes de haunting/filling via recipe manager.
9. Curios bridges: mover entre slots sem clone ou modifier stale.
10. Sable/Aeronautics compatibility com jogador/equipamento em contraption móvel.
11. Restart e dimension change preservando state válido.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 20. Evidência
- modlist física atual: `create-stuff-additions1.21.1_v2.1.4b.jar`, mod id `create_sa`, runtime `2.1.4.`;
- CurseForge oficial: build Beta NeoForge 1.21.1 V2.1.4b, Client & Server, equipamentos/tools/gadgets, mobilidade, combustíveis e recipes Create;
- changelog 2.1.3: migration para capacity, Spout/tanks/backpacks e alerta de Tank Fix legado;
- changelog 2.1.4b: indicador visual para enchantment desabilitado, logo e small fixes;
- modlist física: Curios/bridges e Sable: Stuff&Additions Compatibility como integrações separadas.

> 🔒 Boundary canônico: **Stuff 'N Additions controla seus equipamentos, capacity e efeitos; Create controla processing; Curios apenas hospeda slots; Sable/Aeronautics controlam o frame físico**. Cada recurso deve ser liquidado uma única vez.