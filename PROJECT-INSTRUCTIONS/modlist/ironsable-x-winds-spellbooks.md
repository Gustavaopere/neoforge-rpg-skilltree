# IronSable X Wind's Spellbooks

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db815199bddf9b632d8c9d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR e quatro requirements confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** IronSable X Wind's Spellbooks
- **Arquivo JAR:** `ironsable-wind-1.0.0.jar`
- **Versão 1.21.1:** 1.0.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, Compat
- **Função:** Bridge específica Wind's Spellbooks ↔ IronSable/Sable que adiciona resposta física de quatro spells documentados — Tornado, Almighty Push, Wind Blade e Aeropic — sobre blocos/objetos simulados.
- **Dependências:** Obrigatórias oficiais e presentes: IronSable 1.2.0, Wind's Spellbooks 1.0.5, Sable 2.0.5 e Iron's Spells 'n Spellbooks 3.16.3.
- **Sobreposição:** Não adiciona nova escola nem substitui Wind's Spellbooks/IronSable. É uma camada de compat sobre spells existentes; a bridge não deve reaplicar mana/damage/cooldown nem manter segundo state físico.
- **Compatibilidade/Riscos:** Bridge recente de física. Riscos: double force ou block break, spell/caster attribution, simulated-block desync, ship/sublevel transform drift, update unilateral de qualquer um dos quatro providers e assumir sem source fórmulas/limites não publicados.
- **Observações:** Escopo público exato: Tornado interage com física; Almighty Push repele simulated blocks; Wind Blade pode quebrar simulated blocks; Aeropic tem interação física de colisão/impacto. Não foram publicados registry IDs, fórmulas de força ou regras de break/ownership.
- **Procedência:** modlist.txt física atual + CurseForge oficial IronSable X Wind's Spellbooks 1.0.0, Release NeoForge 1.21.1 de 07/08/2026 + descrição oficial dos quatro spells e quatro requirements. Source exato não localizado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ironsable-x-winds-spellbooks
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — IronSable X Wind's Spellbooks 1.0.0 release-pinned; Tornado/Almighty Push/Wind Blade/Aeropic physics bridge, four-provider authority, simulated-block interactions, lifecycle/multiplayer, risks and tests cataloged; exact source unavailable.
- **Histórico da decisão:** 2026-09-07 — novo mod incorporado à auditoria; sem decisão curatorial ainda.
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `ironsable-wind-1.0.0.jar`, mod id `ironsable_wind`, versão `1.0.0`. A release oficial é NeoForge 1.21.1 de 07/08/2026. Source exato não foi localizado; esta ficha é release-pinned.

## 1. Papel e authority
O addon liga Wind's Spellbooks ao pipeline físico IronSable/Sable. Wind's Spellbooks/Iron's continuam owners dos spells, mana, cooldown e damage; IronSable/Sable continuam owners da tradução e state físico. Este addon apenas adiciona compatibilidade para quatro spells publicados.

## 2. Dependências obrigatórias
O projeto exige **IronSable, Wind's Spellbooks, Sable e Iron's Spells 'n Spellbooks**. O pack contém IronSable 1.2.0, Wind's Spellbooks 1.0.5, Sable 2.0.5 e Iron's Spells 3.16.3. Atualização de qualquer um dos quatro é version gate para esta bridge.

## 3. Tornado
A documentação confirma integração física de **Tornado**. Sem source exato, não se inventa algoritmo, raio, força ou seleção de targets. O teste operacional deve verificar simulated blocks/ships em movimento e evitar aplicar physics duas vezes.

## 4. Almighty Push
**Almighty Push** pode repelir simulated blocks. A autoridade da força/transform continua em Sable; mana/cooldown/cast continuam no provider mágico. Um mesmo cast deve produzir uma única resposta física por alvo elegível.

## 5. Wind Blade
**Wind Blade** pode quebrar simulated blocks. Block-break precisa respeitar server state, ownership/protection e drop semantics do provider real. Não assumir que qualquer bloco físico é quebrável nem duplicar drops por executar break no mundo e no sublevel.

## 6. Aeropic
A documentação descreve **Aeropic** com interação física de impacto/colisão. O detalhe exato da mecânica não é publicado; por isso damage, velocity e block response permanecem fail-closed até source/bytecode ou runtime QA.

## 7. Exactly-once e causalidade
O addon não deve liquidar novamente mana/cooldown nem aplicar um segundo hit só porque também gera uma reação física. Cast event, damage event e physics event são camadas diferentes do mesmo fluxo e precisam de deduplicação por causa real.

## 8. Simulated blocks e transforms
Blocos em ships/sublevels possuem frame de referência próprio. Repulsão, break e impacto precisam ser resolvidos na authority física correta, inclusive se a craft estiver transladando/rotacionando. Coordenada world stale não deve ser usada após movimento/unload.

## 9. Client / server
A release é Client & Server. Servidor decide cast, target, break e physics state; cliente renderiza spell/VFX e transformação visual. Prediction não pode produzir alteração persistente por conta própria.

## 10. Lifecycle e multiplayer
Validar cast/cancel, chunk/sublevel unload, assembly/disassembly, death/reconnect, server restart e dois jogadores afetando a mesma craft. Eventos pendentes devem ser invalidados se o spell, caster ou simulated object deixa de existir.

## 11. Riscos técnicos
- força aplicada duas vezes;
- block break duplicado ou drops duplicados;
- caster/target attribution incorreta;
- stale transform após movimento/unload;
- protection/ownership bypass;
- Wind/IronSable/Sable API drift;
- assumir fórmulas não publicadas;
- client/server divergence.

## 12. Matriz de testes obrigatória
- [ ] Client + dedicated server iniciam com os quatro providers atuais.
- [ ] Tornado interage com simulated blocks sem duplicate physics.
- [ ] Almighty Push repele um alvo físico uma única vez.
- [ ] Wind Blade quebra somente targets elegíveis e não duplica drops.
- [ ] Aeropic resolve impacto no frame correto.
- [ ] Craft rotacionando/transladando mantém target correto.
- [ ] Chunk/sublevel unload cancela referências stale.
- [ ] Dois jogadores não duplicam force/break settlement.
- [ ] Mana/cooldown permanecem sob authority do spell provider.
- [ ] Update futuro de qualquer requirement é bloqueado até smoke test conjunto.

## 13. Evidências e limites
- **Modlist física:** JAR/mod id/version e quatro requirements presentes.
- **CurseForge oficial:** release 1.0.0 e escopo de Tornado, Almighty Push, Wind Blade e Aeropic.
- **Limite:** source, registry IDs e fórmulas exatas não foram publicados/localizados; permanecem fail-closed.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
