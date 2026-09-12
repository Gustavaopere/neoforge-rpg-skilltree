# Grappling Hook Mod: Skybound — 1.1+1.21.1.neoforge

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c869db9f0db8180977ad8abc0b87e6a  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** Grappling Hook Mod: Skybound
- **Arquivo JAR:** `grapplemod-1.1+1.21.1.neoforge.jar`
- **Versão 1.21.1:** `1.1+1.21.1.neoforge`
- **Categoria:** QoL; Exploração
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/grapplemod-skybound/files/8176552
- **Função:** Sistema de grappling hook com física de corda e upgrades, com state multiplayer server-authoritative e módulos internos de compatibilidade para Create e Sable.
- **Dependências:** NeoForge 1.21.1. O JAR físico incorpora `grapplemodcompat_create` 1.0+1.21.1.neoforge e `grapplemodcompat_sable` 1.0+1.21.1.neoforge como JarJar internos. Create 6.0.10 e Sable 2.0.5 estão presentes fisicamente.
- **Compatibilidade/Riscos:** Riscos: rope/hook state divergente sob lag/relog, attachment stale em contraptions/sublevels, dupla aplicação de movimento com ParCool/outros movement mods, teleporte/rocket/motor processado mais de uma vez e version drift dos módulos Create/Sable. Branch público de release ainda declara core 1.0 e README Fabric-first; release NeoForge 1.1 é pinada pela distribuição física/oficial.
- **Sobreposição:** Pode sobrepor mobilidade com ParCool, jetpacks e outros movement systems, mas sua authority é hook/rope/tension e upgrades próprios. Create/Sable continuam authorities de contraption/sublevel transforms; compat modules apenas adaptam coordenadas/attachment.
- **Observações:** JAR físico confirma dois módulos internos: Create Compatibility Module e Sable Compatibility Module. Eles não são top-level. O source público da branch 1.21.1-release é útil para arquitetura/compatibilidade, mas `Core/gradle.properties` ainda declara mod_version=1.0; portanto não é pin byte-exato da release NeoForge 1.1.
- **Procedência:** modlist.txt física atual de 09/09/2026 + release oficial NeoForge 1.1 file 8176552 + source público weaversworkshop/grapplemod-skybound branch 1.21.1-release usado estruturalmente, com source drift explícito.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Grappling Hook Mod: Skybound 1.1 NeoForge; física/rope state, upgrades, server authority, Create/Sable JarJar, lifecycle, multiplayer, riscos e testes catalogados; source drift registrado.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `grapplemod-1.1+1.21.1.neoforge.jar`, mod id `grapplemod`, versão `1.1+1.21.1.neoforge`, NeoForge 1.21.1. A release oficial NeoForge de 31/05/2026 é a authority da versão. O source público `weaversworkshop/grapplemod-skybound:1.21.1-release` confirma arquitetura e compatibilidades, mas ainda contém metadata/documentação Fabric-first e `Core/gradle.properties` em `mod_version=1.0`; por isso não é tratado como pin byte-exato da release 1.1 NeoForge.

## 1. Papel no modpack
Skybound implementa grappling hooks com física de corda para travessia e mobilidade, mantendo o conceito clássico do Grappling Hook Mod e adicionando foco explícito em **estruturas móveis** e **multiplayer**. O hook/rope é um sistema próprio de movimento; não é apenas item cosmético nem teleporte genérico.

## 2. Authority / ownership
- **Grapplemod:** hook, rope/tension, upgrades do gancho, attachment e state específico da mecânica.
- **Servidor:** authority final de hook/rope state e efeitos de mobilidade que alteram posição/state lógico.
- **Create:** continua authority das transforms/contraptions montadas.
- **Sable:** continua authority dos sublevels/moving block-spaces.
Os módulos de compatibilidade convertem/adaptam essas referências; não assumem ownership dos sistemas externos.

## 3. Conteúdo funcional confirmado
A documentação pública da linha Skybound confirma:
- Grappling Hook;
- upgrades de motor, rocket, ender teleport, magnet, dual hooks e forcefield;
- estilos/configuração de rope;
- Long Fall Boots;
- aplicação de upgrades por Smithing Table;
- resource-pack variants built-in;
- pequeno conjunto de advancements de orientação;
- gamerule `useLimitedHook` para restringir superfícies de attachment.
Sem enumeração exata do JAR 1.1, IDs de items/recipes/tags não são inventados nesta ficha.

## 4. Física de hook e rope
O sistema mantém uma conexão física entre jogador e ponto de attachment. Em moving structures, a referência precisa acompanhar posição e rotação do alvo. O source público da linha destaca suporte a tracking de transforms, sublevel splits e wrapping em partial blocks. A implementação exata NeoForge 1.1 não é descrita por classe/método sem pin correspondente.

## 5. Compatibilidade Create e Sable — JarJar físico
O JAR físico incorpora exatamente:
- `grapplemodcompat_create` — Create Compatibility Module `1.0+1.21.1.neoforge`;
- `grapplemodcompat_sable` — Sable Compatibility Module `1.0+1.21.1.neoforge`.
Esses módulos aparecem sob `META-INF/jarjar/` e pertencem ao host. Não criar páginas top-level independentes.
No pack atual também estão presentes **Create 6.0.10**, **Sable 2.0.5** e Create Aeronautics; o projeto documenta Aeronautics como compatibilidade transitiva via Sable.

## 6. Multiplayer
O projeto declara hook/rope state **server-authoritative**. Outros jogadores devem observar o mesmo hook/rope, o state deve sobreviver a relog conforme comportamento previsto, e ropes podem ser cortadas com shears. Isso torna packet order, reconnect e ownership boundaries relevantes.
Um cliente não deve impor posição final, attachment válido ou teleport effect apenas porque localmente calculou a corda.

## 7. Upgrades e exactly-once
Motor, rocket, ender teleport, magnet, dual hook e forcefield modificam o comportamento do hook. Cada ativação que altera gameplay deve ser liquidada uma vez. Input repetido, lag ou correção de prediction não pode produzir impulso, teleport ou criação de hook duplicados.

## 8. Configuração e data
A superfície pública confirma `useLimitedHook` e resource-pack variants built-in. Não foi obtida nesta auditoria uma enumeração versionada de todos os arquivos/chaves de config da build NeoForge 1.1; nomes/defaults adicionais permanecem fail-closed.

## 9. Client / server
- **Servidor:** attachment válido, state de rope/hook, movement settlement, teleporte e interações que mudam world/player state.
- **Cliente:** renderização da rope/hook, input, partículas/sons e prediction/apresentação.
O objetivo é convergência: prediction visual pode suavizar movimento, mas não deve virar segunda authority.

## 10. Lifecycle
Validar criação/destruição do hook, chunk unload/reload, contraption assemble/disassemble, sublevel split, dimension change, player death/respawn, relog, server restart e corte da rope. Attachment references precisam ser invalidadas quando o alvo deixa de existir.

## 11. Sobreposição com outros movement systems
ParCool e outros mods do pack podem alterar velocidade, salto, direção ou pose no mesmo tick. Isso não torna os mods redundantes, mas cria risco de dupla transformação ou clamp conflitante. Grapplemod deve controlar apenas seu próprio movimento derivado de rope/hook e respeitar o state final authoritative.

## 12. Source drift
A branch pública `1.21.1-release` se declara estável para Minecraft 1.21.1 e documenta as features centrais, porém seu README ainda é Fabric-first e afirma NeoForge como planejado, enquanto `Core/gradle.properties` declara core `1.0`. A distribuição oficial, por outro lado, publica exatamente o JAR NeoForge `1.1+1.21.1`. Logo:
- release/JAR físico = authority de versão/loader;
- source branch = evidência estrutural de arquitetura/compatibilidade;
- detalhes específicos do binário NeoForge 1.1 não são inferidos quando houver divergência.

## 13. Riscos técnicos
- attachment stale em contraption/sublevel após transformação;
- rope state client divergente do servidor;
- relog recriar ou perder hook indevidamente;
- motor/rocket/teleport disparado mais de uma vez;
- dois hooks/dual-hook com ownership inconsistente;
- conflito de movimento com ParCool/jetpacks/outros physics mods;
- compat module Create/Sable quebrar após update unilateral;
- chunk unload cortar ou duplicar attachment;
- resource-pack/config divergence entre clientes;
- source Fabric-first ser confundido com internals NeoForge 1.1.

## 14. Matriz de testes obrigatória
- [ ] Dedicated server boot com Grapplemod NeoForge 1.1.
- [ ] Client join/rejoin preserva exatamente o state previsto do hook/rope.
- [ ] Hook vanilla/static block: attach, swing, detach e cut com shears.
- [ ] Motor, rocket, magnet, dual hook, forcefield e ender teleport sem double-processing.
- [ ] `useLimitedHook` on/off respeita superfícies permitidas.
- [ ] Create 6.0.10: attachment acompanha contraption em translação e rotação.
- [ ] Sable 2.0.5: attachment acompanha sublevel e split/rejoin relevante.
- [ ] Create Aeronautics via Sable em cenário de airship, quando aplicável.
- [ ] Chunk unload/reload durante attachment não deixa rope ghost/stale.
- [ ] Death/respawn e dimension change limpam state temporário corretamente.
- [ ] Dois jogadores usando hooks na mesma estrutura sem ownership leak.
- [ ] Coexistência com ParCool e outros movement systems sem dupla aplicação de displacement.

## 15. Evidências e limites
- **Modlist física:** JAR, mod id, versão e dois módulos JarJar internos.
- **Distribuição oficial:** file 8176552, NeoForge 1.21.1, release 1.1, Client & Server.
- **Source público:** branch `1.21.1-release`, documentação de moving structures, multiplayer/server authority, upgrades e bundling dos compat modules.
- **Limite crítico:** source público não está version-pinned de forma limpa à build NeoForge 1.1; metadata interna ainda mostra 1.0/Fabric-first. Classes, packets e configs exatos não são inventados.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
