# IronSable

## Propriedades do registro

- **Mod:** IronSable
- **Arquivo JAR:** ironsable-1.2.0.jar
- **Versão 1.21.1:** 1.2.0
- **Categoria:** Compat, Magia, Tecnologia
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ironsable/files/8598255
- **Função:** Bridge magia↔física entre Iron's Spells e Sable que registra 7 spells próprios focados em objetos/ships simulados e adiciona resposta física a 10 spells-base de Iron's, sem assumir authority sobre mana, damage ou cooldown desses spells-base.
- **Dependências:** Obrigatórias funcionais: Iron's Spells 'n Spellbooks 3.16.3 + Sable 2.0.5. Wind's Spellbooks 1.0.5 está presente e ativa escola Wind para Maelstrom, Tempest's Grasp e Downburst. Create Aeronautics bundle 1.3.2 é integração opcional documentada para casos físicos específicos.
- **Compatibilidade/Riscos:** Bridge de física de alto acoplamento. Riscos: double force/rotation/tether, ship/sublevel transform desync, block/entity attribution, friendly-fire/ownership, spell update drift, Wind school remap, public physics API ABI e comportamento não verificado de classes/methods sem bytecode exato.
- **Sobreposição:** Não substitui Iron's nem Sable. Iron's é authority de spell stats/mana/cooldown; Sable é authority de simulated physics/ship state; IronSable controla a tradução spell→physics e seus 7 spells próprios. Projetos próprios não devem duplicar impulso/rotação/orbit/stasis/tether.
- **Observações:** 1.2.0 continua a build atual para 1.21.1. O source exato do mod permanece indisponível; a Public Physics API é registrada como superfície existente, mas signatures/classes continuam fail-closed.
- **Procedência:** modlist.txt física anexada e reconferida em 12/09/2026 + CurseForge oficial IronSable file 8598255, release 1.2.0 NeoForge 1.21.1 de 07/08/2026 + descrição/changelog oficial já auditados. Revalidação em 12/09/2026 confirma que 1.2.0 continua a única release mais recente da linha.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 12/09/2026 — IronSable 1.2.0/JAR físico reconfirmado; 1.2.0 permanece a release NeoForge 1.21.1 mais recente localizada. Sete spells próprios, dez overlays físicos sobre spells-base, Wind school conditional, Public Physics API, Sable authority, lifecycle, riscos e testes preservados.
- **Data da última decisão:** 2026-09-06

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #344: JAR `ironsable-1.2.0.jar`, mod id `ironsable`, runtime `1.2.0`, SHA-1 `6ea9ce03144ec741a1e8d322f06985366d9ecc9e`.

<callout icon="🧲" color="purple_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `ironsable-1.2.0.jar`, mod id `ironsable`, versão `1.2.0`. A release oficial é CurseForge file 8598255, Release NeoForge 1.21.1 de 07/08/2026. O source do mod não está publicamente disponível no repositório oficial localizado; internals/API signatures permanecem fail-closed.
</callout>
## 1. Papel e authority
IronSable traduz magia de Iron's Spells para o domínio físico de Sable. **Iron's** continua authority do spell-base, mana, damage e cooldown; **Sable** continua authority do state físico de ships/simulated objects; **IronSable** controla a ponte e os spells próprios que registra. Essa separação evita aplicar a mesma força ou efeito duas vezes.
## 2. Dependências e matriz física
O stack funcional confirmado é Iron's Spells 3.16.3 + Sable 2.0.5 + IronSable 1.2.0. Wind's Spellbooks 1.0.5 está presente e altera classificação escolar de três spells. Create Aeronautics bundle 1.3.2 é integração adicional relevante em casos documentados pelo projeto.
## 3. Sete spells próprios observados
Os logs/runtime já auditados no pack expõem sete IDs próprios do namespace `ironsable`: **Maelstrom, Tempest's Grasp, Gyroscopic Spin, Downburst, Stasis Lock, Kinetic Barrier e Elastic Tether**. Essa lista é evidência do runtime físico; classes e fórmulas internas não são inventadas sem bytecode/source.
## 4. Dez overlays físicos sobre spells-base
A integração também physicaliza dez spells existentes de Iron's: **Telekinesis, Gust, Sonic Boom, Shockwave, Black Hole, Gravity Fissure, Stomp, Blizzard, Earthquake e Raise Hell**. Esses spells continuam pertencendo ao registry/provider Iron's; IronSable só acrescenta reação de simulated objects/ships.
## 5. Wind's Spellbooks — classificação condicional
A release 1.2.0 documenta que, com Wind's Spellbooks instalado, **Tempest's Grasp, Downburst e Maelstrom** usam a escola Wind. O pack contém Wind's Spellbooks 1.0.5. Não inferir escola dos outros quatro spells próprios sem evidência exata.
## 6. Public Physics API — 1.2.0
O changelog 1.2.0 anuncia uma **Public Physics API for companion mods**. Como signatures/classes não foram publicadas no source localizado, esta ficha registra a existência da superfície sem inventar métodos. Qualquer projeto próprio deve pinçar a API do JAR antes de compilar uma integração.
## 7. Força, rotação e transforms
Quando um spell afeta um ship/simulated block, posição, velocidade, torque/rotação e frame de referência precisam ser aplicados na authority física de Sable. O cliente pode antecipar VFX, mas não deve aplicar uma segunda transformação authoritative.
## 8. Exactly-once
Um único cast não pode produzir impulso uma vez pelo spell original e novamente por dois hooks de bridge. Isso é especialmente sensível em knockback, atração, orbit, stasis e tether. A regra operacional é um settlement físico por evento causal autorizado.
## 9. Ownership e friendly-fire
Caster, target e owner de ship/entity precisam permanecer associados corretamente. Uma estrutura física com múltiplos passageiros/owners não deve herdar automaticamente friendly-fire policy do projectile sem o contrato real. Esses detalhes permanecem test-required onde o upstream não documenta a regra.
## 10. Create Aeronautics
O projeto documenta integração com o ecossistema Aeronautics em casos como Raise Hell/hot-air balloons. Como o pack usa Sable/Create Aeronautics, validar esses paths especificamente; a presença da integração não significa compatibilidade automática com todo addon de Aeronautics.
## 11. Client / server
A release é Client & Server. Cast/damage/cooldown pertencem ao servidor via Iron's; ship/simulated-object state pertence ao servidor via Sable. Cliente apresenta spells, particles e movimento previsto/renderizado. Divergência resolve a favor do server state.
## 12. Lifecycle e multiplayer
Validar cast/cancel, ship assembly/disassembly, player mount/dismount, sublevel/chunk unload, dimension transfer, death/reconnect e server restart. Tethers/stasis/temporary forces precisam limpar quando spell, entity ou ship deixa de existir.
## 13. Riscos técnicos
- double force por hooks concorrentes;
- rotação/posição aplicadas no frame errado;
- stale ship reference após unload/disassembly;
- tether/stasis persistir após fim do spell;
- Wind school conditional não resolver após update;
- friendly-fire/owner attribution incorreta;
- API pública mudar sem source pin;
- Create Aeronautics edge cases;
- cliente aplicar physics que deveria ser server-authoritative.
## 14. Matriz de testes obrigatória
- [ ] Dedicated server + cliente iniciam com IronSable 1.2.0, Iron's 3.16.3 e Sable 2.0.5.
- [ ] Sete spells próprios registram sem duplicate IDs.
- [ ] Dez spells-base continuam com mana/damage/cooldown controlados por Iron's.
- [ ] Telekinesis/Gust/Black Hole etc. aplicam resposta física exatamente uma vez.
- [ ] Tempest's Grasp/Downburst/Maelstrom resolvem Wind com Wind's Spellbooks 1.0.5.
- [ ] Ship em movimento recebe força no frame correto.
- [ ] Disassembly/unload limpa tether/stasis/references.
- [ ] Dois jogadores castando sobre a mesma craft não duplicam state.
- [ ] Raise Hell/Aeronautics edge funciona na stack física atual.
- [ ] Companion mod/API futura só entra após pin de signatures reais.
## 15. Evidências e limites
- **Modlist física:** JAR/mod id/version e dependências atuais.
- **CurseForge oficial:** file 8598255; Wind classification + Public Physics API da 1.2.0.
- **Runtime/logs já auditados:** sete IDs próprios e dez overlays de spells-base.
- **Limite:** source/bytecode exatos não foram extraídos nesta ficha; signatures, fórmulas e ownership policies não publicados permanecem fail-closed.
- **Runtime QA:** nenhum item da matriz acima foi marcado como executado nesta catalogação.
