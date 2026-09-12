# Create Deep Seas

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81818df8d76d01019c13
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Deep Seas
- **Arquivo JAR:** `create_submarine-2.2.4.jar`
- **Versão 1.21.1:** 2.2.4
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Exploração
- **Função:** Addon submarino/aquático para o stack Create Aeronautics/Sable, com sistemas técnicos de vessel/water-culling e conteúdo de exploração/Abyss na linha pública do projeto.
- **Dependências:** Pack físico: Create 6.0.10 + Create Aeronautics 1.3.2 + Sable 2.0.5; Copycats+ 3.0.9 é diretamente relevante aos fixes de cascade crash 2.2.4.
- **Sobreposição:** Nicho submarino sobre Aeronautics/Sable. O addon separado Create: Deep Seas - Lava Fix é potencial overlap de patch e deve ser auditado separadamente; não foi classificado aqui como redundante.
- **Compatibilidade/Riscos:** 2.2.4 corrige crashes críticos de dedicated server por FlowingFluidMixin/client stripping e referência client-only em packet comum. Riscos remanescentes: vessel/physics desync, water-culling stale, chunk/dimension lifecycle, drift Sable e overlap potencial com Deep Seas - Lava Fix.
- **Observações:** JAR/mod id/runtime 2.2.4 confirmados. Release oficial NeoForge 1.21.1 de 17/06/2026. Source público `main` ainda declara 2.2.3, portanto é usado apenas para arquitetura; o JAR/changelog 2.2.4 prevalecem.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + metadata runtime + release/changelog oficial 2.2.4 + repositório oficial MaxCreateMC/Create-Deep-Seas, source público 2.2.3 usado fail-closed para arquitetura.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-deep-seas
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 2.2.4 com submarine/Abyss/water-culling architecture, dedicated-server fixes, packet/client boundary, physical stack, lifecycle e matriz de regressão catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🌊 **Identidade física confirmada:** `create_submarine-2.2.4.jar`, mod id `create_submarine`, runtime `2.2.4`, NeoForge 1.21.1. A release 2.2.4 é Client & Server. O `main` público do source ainda declara 2.2.3, portanto é usado para arquitetura, não como equivalência binária da build instalada.

## 1. Papel e authority
Create Deep Seas adiciona navegação/submarinos ao stack físico baseado em **Create Aeronautics/Sable**. O addon owns seus blocos/sistemas submarinos, lógica aquática e conteúdo de exploração associado. Create Aeronautics/Sable continuam owners da infraestrutura física/moving contraptions que o addon consome.
Não tratar Create Deep Seas como engine física independente.

## 2. Estrutura pública do projeto
O source público organiza o projeto em superfícies `Create Submarine`, `Create Abyss` e `Create High Seas`, além de conteúdo relacionado a dimensão/abyss. Como o source `main` está em 2.2.3, esses nomes são evidência arquitetural da linha, não prova de cada registry entry do JAR 2.2.4.

## 3. Create Submarine
A documentação pública descreve Create Submarine como a parte técnica voltada a submarinos/boats e integração com Create Aeronautics. O addon fornece blocos e sistemas para construir/operar veículos aquáticos sobre a infraestrutura física do stack.
Assemblies, forças, docking e movimento precisam ser resolvidos pelo runtime real, sem reproduzir state de física em scripts externos.

## 4. Water culling
O source público descreve um **water culling system** próprio. Essa superfície é sensível porque render/occlusion aquática pode divergir da simulação física ou do state de fluido do servidor.
Culling é presentation/client-facing; não deve ser authority para volume de água, colisão, pressão ou estado de vessel.

## 5. Create Abyss e exploração
A arquitetura pública inclui módulo/conteúdo `create_abyss`, com dimensão, blocos e peixes relacionados ao Abyss. World/dimension state é server-authoritative. Teleporte, unload e retorno ao Overworld não podem abandonar vessel/contraption state ou duplicar entidades.
Parâmetros de geração, IDs de bioma e tabelas de spawn não são inferidos nesta ficha sem pin 2.2.4.

## 6. Fix crítico da 2.2.4 — dedicated server
O changelog oficial de 17/06/2026 corrige um crash que impedia dedicated servers de iniciar por causa de `FlowingFluidMixin` combinado com stripping de código client-side.
Esse é um regression gate obrigatório: boot de dedicated server com o pack completo precisa ocorrer sem classloading de código client-only nessa superfície.

## 7. Fix crítico da 2.2.4 — packet/render boundary
A mesma release corrige referência de uma classe client-only (`SubLevelCrackRenderer`) dentro de um packet comum. O erro causava crash do Mixin preprocessor e falha em cascata de mods dependentes, explicitamente incluindo **Copycats+** e **Create Aeronautics** no changelog.
Isso define um boundary claro: packets/common code não podem depender de renderer client-only.

## 8. Stack físico pertinente
O pack atual contém:
- Create Aeronautics 1.3.2;
- Sable 2.0.5;
- Copycats+ 3.0.9;
- Create 6.0.10.

Logo os dois cenários de cascata citados upstream são relevantes diretamente ao pack atual, embora o changelog indique que foram corrigidos na 2.2.4.

## 9. Divergência source ↔ JAR
O repositório oficial `MaxCreateMC/Create-Deep-Seas`, branch `main`, declara atualmente `mod_version=2.2.3`, MC 1.21.1 e uma linha Sable anterior. O JAR físico/publicação é 2.2.4.
Por isso classes/IDs/valores não confirmados no changelog ou na metadata física não são transportados automaticamente do source para a ficha 2.2.4.

## 10. Create: Deep Seas - Lava Fix
O pack possui também um addon separado **Create: Deep Seas - Lava Fix**. Sua necessidade deve ser auditada na própria posição física, porque a 2.2.4 já contém fixes importantes de fluid/mixin/server.
Nesta página não se presume que o Lava Fix seja redundante nem necessário; apenas se registra a superfície potencial de overlap.

## 11. Client/server
Physics authority, vessel state, dimension state e gameplay devem convergir no servidor. Water culling/render e efeitos visuais são client-facing.
A 2.2.4 existe justamente para reforçar esse separation boundary; qualquer nova referência client-only em common packet é regressão grave.

## 12. Multiplayer
Dois clientes precisam observar posição/orientação do mesmo submarine de forma convergente. Entrada concorrente, piloto desconectando, passenger state e reconexão não podem gerar vessel duplicado, teleporte fantasma ou controle persistente de usuário ausente.

## 13. Chunk lifecycle
Submarinos podem atravessar chunk boundaries e coexistir com sublevels/contraptions móveis. Testar unload/reload, restart com vessel parado/em movimento e chunks parcialmente carregados.
Caches de fluid/culling e physics attachments não podem sobreviver apontando para world/contraption já descarregado.

## 14. Dimension lifecycle
Entrar/sair de dimensões, morrer/relogar durante exploração Abyss e reiniciar o servidor devem manter ownership/position coerentes. Se vessels não forem suportados em determinada transição, o runtime deve falhar de forma segura, não duplicar state.

## 15. Sobreposição com outros veículos
O pack contém diversas soluções Create/Aeronautics/veiculares. Create Deep Seas tem niche submarino/aquático; overlap funcional deve ser avaliado por veículo e control surface, não pelo simples fato de múltiplos mods moverem contraptions.

## 16. Riscos
1. Dedicated server crash por regressão do `FlowingFluidMixin`.
2. Common packet volta a referenciar classe client-only.
3. Crash em cascata envolvendo Copycats+/Aeronautics.
4. Water culling fica stale após assemble/disassemble/unload.
5. Vessel position diverge entre server e clientes.
6. Contraption/sublevel perde state ao cruzar chunks.
7. Dimension change duplica ou perde vessel/passenger.
8. Sable 2.0.5 diverge da linha Sable declarada no source 2.2.3 público.
9. Lava Fix externo duplica patch já resolvido ou conflita com 2.2.4.
10. Dois sistemas de input/physics aplicam movimento concorrente.

## 17. Matriz de testes
- [ ] Dedicated server inicia com Deep Seas 2.2.4 + Create Aeronautics 1.3.2 + Sable 2.0.5 + Copycats+ 3.0.9.
- [ ] Não ocorre crash de `FlowingFluidMixin` no boot.
- [ ] Não há referência client-only em common networking durante conexão/join.
- [ ] Submarine pode ser construído/assemblado conforme runtime real.
- [ ] Pilotagem aquática permanece server-authoritative com dois clientes.
- [ ] Water culling acompanha assemble/disassemble sem água/render fantasma.
- [ ] Chunk unload/reload preserva ou reconstrói vessel state corretamente.
- [ ] Restart com submarine existente não duplica/perde contraption.
- [ ] Passenger/pilot disconnect libera controle corretamente.
- [ ] Transições envolvendo conteúdo Abyss não deixam state órfão.
- [ ] Copycats+ e Aeronautics carregam sem cascade crash.
- [ ] Deep Seas - Lava Fix é testado separadamente contra 2.2.4 antes de qualquer decisão de remoção.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 18. Evidências e limites
A modlist física confirma JAR/mod id/runtime 2.2.4 e mixins próprios. A publicação oficial confirma release NeoForge 1.21.1, Client & Server e os dois fixes críticos de dedicated server/network-client boundary. O source oficial confirma a arquitetura submarino/Abyss/water-culling, mas o `main` ainda declara 2.2.3; internals não explicitamente confirmados para 2.2.4 permanecem fail-closed.

> 🔒 **Boundary canônico:** Deep Seas owns a camada submarina/aquática; Aeronautics/Sable own a infraestrutura física subjacente. Common/server code não pode depender de render client-only, e o JAR 2.2.4 prevalece sobre o source 2.2.3 para identidade/versionamento.
