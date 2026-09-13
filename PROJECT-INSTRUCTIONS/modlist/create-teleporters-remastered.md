# Create Teleporters Remastered

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8115ab14e1ca777d0ab6
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Teleporters Remastered
- **Arquivo JAR:** `createteleporters-remastered-2.0.2b-neoforge-1.21.1.jar`
- **Versão 1.21.1:** 2.0.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Exploração
- **Função:** Addon Create de teleporte de itens/entidades e portais custom multiblock, com TP Links, Quantum Casings, validação de portal e compatibilidade Sable/Aeronautics e Immersive Portals.
- **Dependências:** Create obrigatório. Pack físico também contém Sable 2.0.5 + Create Aeronautics 1.3.2, diretamente cobertos pela compat 2.0.2b. Immersive Portals 6.0.7 está presente e usa o fix de facings/orientation. Waystones 21.1.44 é overlap de transporte, não dependência.
- **Sobreposição:** Waystones 21.1.44/WaystonesSable 1.0.7 compartilham teleporte remoto, mas não substituem Item/Entity Teleporters ou Custom Portal multiblock. Immersive Portals é integração concreta, não duplicata; mantém authority de suas portal entities.
- **Compatibilidade/Riscos:** Riscos: Quantum Portal órfão; base/frame divergirem em dimensão/rotation; entity/item dupe/loss em teleport; TP Link range mismatch; Sable sublevel transform duplicado; Immersive Portals facing/orientation incorreto ou rebuild marker repetido; migration legado; source master não matching.
- **Observações:** Divergência preservada: filename/publicação `2.0.2b`, metadata runtime física `2.0.2`; `Versão 1.21.1` mantém a runtime 2.0.2. 2.0.2b adiciona validação/autolimpeza de Quantum Portal, Sable/Aeronautics compat e corrige Immersive Portals com facings diferentes.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + runtime físico 2.0.2 + release/changelog oficial 2.0.2b NeoForge 1.21.1 + documentação Remastered. Source dcchill/Create-Teleporters `master` consultado apenas como arquitetura, pois não pina 2.0.2b.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-teleporters-remastered
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê runtime 2.0.2/release 2.0.2b com Entity/Item Teleporters, TP Links, Custom Portal multiblock, Quantum Portal validation, Sable/Aeronautics, Immersive Portals e migration catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🌀 **Identidade física confirmada:** `createteleporters-remastered-2.0.2b-neoforge-1.21.1.jar`, mod id `createteleporters`, runtime físico `2.0.2`. A publicação correspondente usa o label **2.0.2b**; filename/release label e runtime são mantidos separados. O projeto é Client & Server e depende de Create.

## 1. Papel e authority
Create Teleporters Remastered adiciona transporte por teleporters/portais integrado ao estilo Create. O addon owns seus teleporters, portal base/frame, linking e transferência de entities/items; Minecraft continua authority de dimension/chunk/entity state e Create fornece a infraestrutura/dependência temática utilizada pelo addon.

## 2. Remastered versus linha antiga
O projeto descreve Remastered como rebuild completo. A documentação alerta que mundos vindos da versão antiga podem encontrar o antigo Custom Portal convertido em placeholder, que deve ser minerado para recuperar o novo **Custom Portal Base**. Migration é boundary explícito e não deve ser automatizado por inferência.

## 3. Entity Teleporter
O addon fornece **Entity Teleporter** em diferentes tamanhos. Teleport de entity precisa validar origem, destino, disponibilidade de chunk/dimension e estado da entity; uma única entrada deve produzir uma única transferência, não clone no source e destination.

## 4. Item Teleporter
**Item Teleporter** move itens entre pontos configurados. Inserção/extração e entrega precisam ser atômicas do ponto de vista lógico: falha de destino, unload ou retry não pode consumir sem entregar nem entregar duas vezes.

## 5. TP Links
A linha Remastered mantém **TP Links** e remove Advanced TP Links. O projeto informa range configurável para TP Links. Config efetiva do servidor é authority; cliente/GUI não pode usar distância diferente da validação server-side.

## 6. Custom Portal multiblock
O Custom Portal agora é multiblock, construído com **Custom Portal Base** e frame de **Quantum Casings**. A base precisa ser a authority da estrutura válida, dimensões e orientação; portal blocks são derivados do multiblock e não devem sobreviver como state independente.

## 7. Validação de Quantum Portal — 2.0.2b
A release 2.0.2b adiciona validação para que Quantum Portal blocks confirmem que pertencem a um frame ativo e corretamente construído. Bloco órfão deve se autoremover quando o frame quebra, fica inválido ou não possui matching active portal base.

## 8. Dimensões e rotação do portal
A 2.0.2b passa a validar usando dimensões e rotation armazenadas no Custom Portal Base, não apenas sinais visuais/particles. Isso reduz falso-positivo de portal ativo; base state precisa permanecer consistente após reload, chunk boundary e edição parcial do frame.

## 9. Centro por interior real
A release também altera cálculo do centro do portal para usar a extensão interior real do frame. Em portais escaláveis, destino/offset devem ser calculados pela geometria efetiva, não por tamanho fixo presumido.

## 10. Sable / Create Aeronautics — integração ativa
A 2.0.2b adiciona compatibilidade com **Aeronautics/Sable**. O pack possui Sable 2.0.5 e Create Aeronautics 1.3.2, portanto esta superfície é concretamente ativa. Portais em/movendo-se com sublevels físicos exigem transformação correta entre coordenadas do body e mundo pai.

## 11. Immersive Portals — integração ativa
O pack contém **Immersive Portals 6.0.7**. A 2.0.2b corrige linking quando os portais têm facings diferentes, por exemplo norte→leste, alinhando orientação do lado de destino antes de concluir o cluster bidirecional/bifacial.

## 12. Rebuild one-time de portal Immersive
O changelog informa que portal entities existentes do Immersive Portals são reconstruídas uma vez com o comportamento corrigido usando marcador de versão de compatibilidade. Esse migration step deve ser idempotente: marcador não pode provocar rebuild a cada load.

## 13. Orientação de saída
O fix de facings diferentes evita que a view/saída fique rotacionada apenas pela diferença entre eixos dos frames. Transform de posição, yaw/pitch e velocity deve ser coerente entre os dois lados e validado pelo servidor.

## 14. Waystones e outros teleportes
Waystones 21.1.44 e WaystonesSable 1.0.7 estão fisicamente presentes. Há overlap de finalidade — transporte remoto — mas não equivalência: Waystones possui sua própria rede/UX/custos; Teleporters possui item/entity teleport e custom portal multiblock Create. Nenhuma duplicata integral é presumida.

## 15. Pocket Dimensions
A documentação Remastered informa que Pocket Dimensions não devem mais ser sobrescritas pela nova versão. Existing dimension data deve ser preservado durante atualização; ausência de overwrite não implica que todo conteúdo legado seja migrado automaticamente.

## 16. Pets e comportamento removido
A documentação informa que pets não teleportam automaticamente com o player na linha Remastered. Esse é comportamento intencional; não catalogar ausência de pet-follow como bug sem evidência específica.

## 17. Recursos removidos
Teleporter Receivers e Gravity Stabilizer foram removidos na reconstrução Remastered. Não devem ser exigidos como dependências/etapas atuais apenas porque existiam em documentação antiga.

## 18. Connected textures e GUI
Quantum Casing usa connected textures e a linha Remastered introduz GUIs no estilo Create, além de modelos simplificados e remoção da dependência GeckoLib. Essas superfícies são client-facing; portal validity e teleport settlement continuam server-side.

## 19. Source público versus build exata
O repositório oficial `dcchill/Create-Teleporters` foi localizado, mas o `master` atual declara genericamente `version = 2.0-1.21.1`, não o pin exato 2.0.2b/2.0.2. Portanto ele é usado apenas como arquitetura atual, nunca como equivalência binária da build instalada.

## 20. Client/server e multiplayer
Linking, portal validation, range, item/entity transfer e dimension transition precisam ser server-authoritative. GUI, connected textures e portal rendering são client-facing. Dois jogadores ativando/atravessando simultaneamente não podem duplicar state ou desmontar o mesmo portal duas vezes.

## 21. Lifecycle
Testar frame build/break/rebuild, chunk unload/reload, restart com portal ativo, teleport durante destino descarregado, item queue, cross-dimension transfer, Aeronautics assembly/disassembly, Immersive Portals migration marker e alteração de config de TP Link.

## 22. Riscos
1. Quantum Portal block permanece órfão após frame inválido.
2. Base e frame divergem em dimensões/rotation.
3. Teleport de entity duplica ou perde entity em unload/retry.
4. Item Teleporter consome/entrega duas vezes.
5. TP Link usa range diferente no cliente e servidor.
6. Aeronautics/Sable aplica transform parent↔sublevel duas vezes.
7. Immersive Portals com facing diferente rotaciona saída incorretamente.
8. Compat marker reconstrói portal entities repetidamente.
9. Upgrade legado perde Custom Portal state sem recovery de placeholder.
10. Waystones/portal systems criam bypass de progression/custo por rotas alternativas.
11. Connected texture/GUI failure é confundido com portal inactive state.
12. Source `master` genérico é tratado erroneamente como pin exato da 2.0.2b.

## 23. Matriz de testes
- [ ] Dedicated server inicia com runtime 2.0.2 + Create 6.0.10.
- [ ] Custom Portal Base + Quantum Casing formam portal válido.
- [ ] Quebrar frame remove Quantum Portal blocks órfãos.
- [ ] Rebuild/restauração preserva dimensões e rotation corretas.
- [ ] Entity Teleporter transfere uma entity exatamente uma vez.
- [ ] Item Teleporter conserva stacks em destino cheio/descarregado.
- [ ] TP Link respeita o range configurado no servidor.
- [ ] Portal escalável calcula centro pela área interior real.
- [ ] Sable/Aeronautics portal mantém transform correto em body físico.
- [ ] Immersive Portals norte→leste preserva orientação esperada.
- [ ] Compat marker do Immersive reconstrói entidades antigas uma única vez.
- [ ] Restart/chunk unload não duplica portal/entity/item state.
- [ ] Upgrade legado recupera Custom Portal Base conforme fluxo documentado.
- [ ] Coexistência com Waystones não cria duplicação de state; balanceamento fica para auditoria curatorial.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 24. Evidências e limites
A modlist física confirma filename, mod id e runtime `2.0.2`. A release oficial exata 2.0.2b confirma validação/autolimpeza de Quantum Portals, uso de dimensões/rotation da base, centro por interior real, compat Sable/Aeronautics e correções Immersive Portals. A documentação Remastered confirma Entity/Item Teleporters, Custom Portal multiblock, TP Links e mudanças de migration/recursos removidos. Internals não pinados permanecem fail-closed.

> 🔒 **Boundary canônico:** Custom Portal Base é a âncora lógica do multiblock; portal blocks e teleports são derivados de state validado no servidor. Cada transferência deve ocorrer exatamente uma vez entre origem e destino.
