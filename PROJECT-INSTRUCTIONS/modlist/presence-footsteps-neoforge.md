# Presence Footsteps (NeoForge)

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db814cb190c97481516220
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `PresenceFootsteps-1.21.1-1.12.0-beta.1-1.21NeoForge.jar`, mod id `presencefootsteps`, runtime `1.21.1-1.12.0-beta.1`, mixin `presencefootsteps.mixin.json`; `pfsable-1.0.jar`, Sable 2.0.5 e Create Aeronautics 1.3.2 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Presence Footsteps 1.12.0-beta.1 e a bridge pfsable 1.0 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Presence Footsteps (NeoForge)
- **Arquivo JAR:** `PresenceFootsteps-1.21.1-1.12.0-beta.1-1.21NeoForge.jar`
- **Versão 1.21.1:** 1.21.1-1.12.0-beta.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, QoL
- **Função:** Sistema avançado de sons de passos que varia conforme superfície, ambiente e contexto, tornando movimentação mais imersiva.
- **Dependências:** Cliente NeoForge 1.21.1. pfsable 1.0 está presente como bridge funcional com Sable/Aeronautics; o base continua client-only.
- **Sobreposição:** AmbientSounds trata ambience; Presence Footsteps trata sons de contato/movimento. pfsable é bridge específica, não segundo engine de footsteps.
- **Compatibilidade/Riscos:** Beta do port NeoForge não oficial. Riscos: fork drift, wrong surface sound, Sable/SubLevel stale context, double footsteps e resource-pack mapping. Não confundir release Forge com substituição direta do artefato NeoForge.
- **Observações:** File ID 7252696; Beta NeoForge 1.21.1 publicada em 23/11/2025; Environment Client. Issues da porta devem ser separados do upstream original.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial Presence Footsteps (NeoForge) + página já auditada de Presence Footsteps x Sable.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/presence-footsteps-neoforge
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Presence Footsteps NeoForge 1.12.0-beta.1 reconstruído: unofficial-port boundary, client-only audio, surface resolution, pfsable/Sable bridge, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `PresenceFootsteps-1.21.1-1.12.0-beta.1-1.21NeoForge.jar`, mod id `presencefootsteps`, versão `1.21.1-1.12.0-beta.1`. Esta é a porta **não oficial NeoForge** do Presence Footsteps, publicada como Beta e Client-only. O pack contém `pfsable-1.0.jar`, bridge específica para Sable/Aeronautics.

## 1. Identidade e papel
- **Mod:** Presence Footsteps (NeoForge).
- **JAR:** `PresenceFootsteps-1.21.1-1.12.0-beta.1-1.21NeoForge.jar`.
- **Mod id:** `presencefootsteps`.
- **Runtime:** `1.21.1-1.12.0-beta.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Beta.
- **Ambiente:** Client.
- **Licença:** MIT.
- **Papel:** sistema contextual de sons de passos/superfície e movimento.
- **Decisão:** Sem decisão.

## 2. Port NeoForge não oficial
O projeto ZCRAFT se apresenta como port não oficial do Presence Footsteps (Forge) para NeoForge. Bugs específicos desta porta devem ser atribuídos/reportados ao port, não automaticamente ao upstream original.

Uma Release estável do projeto Forge não é substituição direta para esta instalação NeoForge.

## 3. Authority do áudio de passos
Presence Footsteps escolhe/reproduz sons de contato conforme superfície e contexto. Ele não é physics engine e não altera colisão, velocidade ou stamina.

Som diferente não muda mecanicamente o bloco pisado; é presentation layer.

## 4. Surface/context resolution
O valor do mod está na resolução de material/contexto para passos mais específicos que o vanilla. Testar blocos vanilla e modded, transições rápidas de material, sprint/sneak/jump-land e superfícies com sound type customizado.

## 5. Bridge Presence Footsteps x Sable
O pack possui `pfsable-1.0.jar`, já catalogado separadamente, para adaptar o surface lookup em contraptions Sable/Aeronautics.

Ownership:
- Presence Footsteps → footsteps/audio selection;
- Sable/Aeronautics → contraption/SubLevel;
- pfsable → bridge da interseção.

A bridge não transforma Presence Footsteps em dependência server-side.

## 6. Client-only boundary
A publicação é Client. Dedicated server não deve precisar deste JAR para state de gameplay.

Dois clientes podem ouvir passos diferentes por config/resource pack sem divergir no estado funcional do servidor.

## 7. Relação com AmbientSounds e Polytone
AmbientSounds cria ambience/soundscape e não duplica o domínio de footsteps. Polytone/resource packs podem alterar sons de blocos e mappings, criando uma composição de presentation layer.

Risco concreto é áudio duplicado/inadequado, não duplicidade automática de mods.

## 8. Resource packs e configuração
Qualquer mapping/volume/config efetivos da instância deve ser lido antes de balancear áudio. Não inferir suporte a todo bloco modded apenas pela presença do mod.

Resource reload precisa atualizar sons sem deixar mapping stale.

## 9. Lifecycle
Testar:
- join/reconnect;
- first spawn;
- troca de dimensão;
- resource reload;
- entrar/sair de Sable ship;
- assembly/disassembly;
- sprint/sneak/jump;
- troca rápida de superfícies.

O contexto de uma contraption não deve persistir depois que o player volta ao mundo.

## 10. Performance
Footsteps ocorrem frequentemente. Avaliar overhead em movimento contínuo, sobretudo com muitas superfícies modded e áudio/ambience simultâneo. Um problema de mix/volume não deve ser confundido com tick server-side porque o port é client-only.

## 11. Riscos
1. **Beta port:** maturidade menor que uma release estável.
2. **Fork drift:** diferenças do upstream Forge/Fabric.
3. **Wrong surface:** bloco modded resolve som inadequado.
4. **Sable boundary stale:** som de ship permanece após sair.
5. **Double audio:** outro patch emite o mesmo evento.
6. **Resource-pack drift:** IDs/mappings de som mudam.
7. **Client attribution:** não culpar servidor por falha puramente sonora sem evidência.

## 12. Matriz de testes
- [ ] Cliente inicia com build Beta 1.12.0; dedicated server não requer o JAR.
- [ ] Madeira/pedra/metal produzem sons coerentes.
- [ ] Sprint/sneak/jump-land mantêm timing correto.
- [ ] Bloco modded representativo resolve material esperado.
- [ ] Resource reload não duplica nem perde footsteps.
- [ ] pfsable reconhece superfícies em Sable/Aeronautics.
- [ ] Mundo→ship→mundo troca contexto imediatamente.
- [ ] AmbientSounds/Polytone não causam double sound do mesmo passo.
- [ ] Reconnect/dimension change limpam state de áudio temporário.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 13. Evidências e limites
- Modlist física: JAR, mod id/runtime e `presencefootsteps.mixin.json`.
- CurseForge oficial do port: project 1390302, Beta NeoForge 1.21.1, file ID 7252696, Environment Client, MIT.
- Projeto: port não oficial de Presence Footsteps Forge para NeoForge.
- Pack físico: `pfsable-1.0.jar` presente e já catalogado como bridge Presence Footsteps↔Sable/Aeronautics.
- **Limite:** mappings/config/resource packs locais não foram lidos; compatibilidade de cada bloco modded depende de runtime smoke.
