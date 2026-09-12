# Simply More

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3cc69db9f0db81bfa428f76ab1adea8c
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `simplymore-forge-1.3.0_alpha.jar`, mod id `simplymore`, runtime `1.3.0_alpha`, mixin `simplymore.mixins.json`; Simply Swords 1.70.2, Architectury 13.0.11, Cloth Config 15.0.140, Iron's Spells 3.16.3 e Patchouli 93 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Simply More `1.3.0_alpha` e os providers citados estão presentes. Better Combat, Stick n' Stone e Mythic Metals não aparecem top-level. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Simply More
- **Arquivo JAR:** `simplymore-forge-1.3.0_alpha.jar`
- **Versão 1.21.1:** 1.3.0_alpha
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** RPG, Magia
- **Função:** Addon de Simply Swords que adiciona 10 novos tipos de arma e 33 Unique Weapons, com implicits, abilities, transformação/reforma de uniques e compatibilidades opcionais; a linha 1.3 Alpha está reestruturando várias uniques.
- **Dependências:** Required atuais: Simply Swords (pack 1.70.2-1.21.1) e Architectury (13.0.11). Documentação do projeto também exige Cloth Config >=11.1.106; pack 15.0.140. Iron's Spells 3.16.3 e Patchouli 93 estão presentes como integrações/capacidades; Better Combat, Stick n' Stone e Mythic Metals não foram encontrados top-level.
- **Sobreposição:** Expande Simply Swords; não o substitui. Sobreposição temática com outros mods/addons de armas deve ser avaliada por item IDs, abilities e progressão, não apenas por categoria.
- **Compatibilidade/Riscos:** ALPHA intencional e config-breaking. Riscos: uniques antigas explicitamente incompletas/rework, crashes residuais, ability state órfão, on-hit multi-entity duplication, config reset, Simply Swords API drift e compat Iron's parcial. ALPHA 5 corrige três crashes concretos, não elimina o aviso upstream.
- **Observações:** O arquivo físico chama-se `simplymore-forge-1.3.0_alpha.jar`, mas SHA-1/File ID 8736778 corresponde à publicação oficial `simplymore-neoforge-1.3.0_alpha5+1.21.1.jar`. Tratar como ALPHA 5. A própria release avisa que várias uniques marcadas para rework podem estar sem função e que configs serão quebradas/resetadas.
- **Procedência:** modlist.txt física atual de 11/09/2026 + hash físico reconciliado com CurseForge File ID 8736778 (Simply More 1.3.0 ALPHA 5) + página/changelogs oficiais da linha 1.3.0 Alpha.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/simply-more/files/8736778
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Simply More físico reconciliado com 1.3.0 ALPHA 5; weapon types, 33 uniques, implicit abilities, Reforming Remnant, Iron's Spells compat parcial, lifecycle, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `simplymore-forge-1.3.0_alpha.jar`, mod id `simplymore`, runtime `1.3.0_alpha`, NeoForge 1.21.1. O SHA-1 físico foi reconciliado com o **CurseForge File ID 8736778 — Simply More 1.3.0 ALPHA 5**. A build é Alpha deliberada, não uma Release estável.

## 1. Identidade, versão e maturidade
- **Mod:** Simply More.
- **JAR físico:** `simplymore-forge-1.3.0_alpha.jar`.
- **Mod id:** `simplymore`.
- **Runtime:** `1.3.0_alpha`.
- **Publicação correspondente:** `simplymore-neoforge-1.3.0_alpha5+1.21.1.jar` / File ID 8736778.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Alpha.
- **Ambiente:** client & server.
- **Mixin físico:** `simplymore.mixins.json`.
- **Decisão vigente:** **Manter**, preservada da auditoria de 06/09/2026 com risco de prerelease explícito.

## 2. Papel e authority
Simply More é um **addon de Simply Swords**. Simply Swords continua authority das bases de weapon families, sistemas Runic/Awakening/Gems e contratos gerais de combate compartilhados. Simply More adiciona seus próprios tipos de arma, Unique Weapons, implicits e abilities.

O addon não deve redefinir silenciosamente o state canônico de Simply Swords. Quando usa uma API/semântica do provider, mudanças de Simply Swords são regression gate direto.

## 3. Dependências físicas e opcionais
O projeto atual declara como required **Simply Swords** e **Architectury**; sua documentação também lista Cloth Config como requisito da linha. O pack satisfaz com:
- Simply Swords `1.70.2-1.21.1`;
- Architectury `13.0.11`;
- Cloth Config `15.0.140`.

Integrações relevantes presentes:
- Iron's Spells 'n Spellbooks `3.16.3` — compat começou a ser implementada na linha 1.3 para uniques reworked;
- Patchouli `1.21.1-93-NEOFORGE` — capacidade de livro documentada pelo projeto.

Não foram encontrados top-level no snapshot atual: Better Combat, Stick n' Stone e Mythic Metals. Portanto essas capacidades upstream não são marcadas como paths runtime ativos.

## 4. Dez novos tipos de arma
A documentação oficial lista dez weapon types próprios:
- Great Katanas;
- Grandswords — podem desabilitar shields quando o alvo tenta bloquear;
- Backhand Blades;
- Lances — recebem grande boost de força quando usadas montado;
- Khopeshs;
- Daggers;
- Pernachs;
- Quarterstaffs;
- Great Spears;
- Deer Horns.

A linha 1.3 passa a fornecer **implicit abilities** aos weapon types, alinhando-se ao modelo introduzido no Simply Swords 1.70.

## 5. Unique Weapons
O projeto anuncia **33 Unique Weapons**. A linha 1.3 está em processo de rework dessas uniques e o próprio autor avisa que várias marcadas para rework tiveram funcionalidade removida temporariamente e podem **não fazer nada**.

Esse aviso é parte do estado canônico da Alpha: presença do item no registry não equivale a feature completa.

## 6. Reforming Remnant
A 1.3 adiciona **Reforming Remnant**, usado para escolher entre múltiplas uniques que ocupam o mesmo transformation block para contained remnants.

A seleção precisa ser server-authoritative e atômica: uma transformação não pode consumir o mesmo remnant duas vezes, gerar duas uniques ou manter state intermediário após cancelamento/relog.

## 7. Implicit abilities
Weapon types agora possuem implicits como no Simply Swords >=1.70. O lance bonus, por exemplo, migrou de status effect para implicit ability.

Implicits precisam sobreviver corretamente a save/reload e não serem reaplicados duas vezes quando o item é recriado, transformado ou sincronizado.

## 8. On-hit e swing semantics
Na Alpha 1.3, vários efeitos que antes disparavam por entidade atingida passaram a disparar **uma vez por swing**. Isso é especialmente importante em ataques de área/multi-target.

Regression gate: um único swing que toca N entidades não pode multiplicar proc/cooldown/resource gain N vezes quando a ability foi migrada para swing-scoped semantics.

## 9. Effects e Wounded
Parte dos efeitos deixou de usar vanilla status effects e passa a ser tratada separadamente, inclusive ficando imune à limpeza por leite onde documentado.

`Bleed` foi renomeado para **Wounded** e deixou de causar dano ao longo do tempo. Não interpretar Wounded como o Bleed histórico ao depurar dano residual.

## 10. Friendly/pet safety
A Alpha registra que Unique effects não devem mais atingir mobs montados ou pets. Isso cria boundary importante em um pack com mounts/tameables variados.

Validar owner, tamed relation, rider/mount e teams no servidor; partículas visuais não podem ser usadas como evidência de dano funcional.

## 11. Iron's Spells compatibility
A 1.3 iniciou compatibilidade com **Iron's Spells 'n Spellbooks**, limitada às uniques já reworked. O pack contém Iron's 3.16.3, então esse é um path real.

Não presumir que todas as 33 uniques tenham spell scaling. A própria release limita a integração às reworked uniques. Atualizações de spell attributes/mana APIs precisam de smoke test.

## 12. Uniques reworked — exemplos documentados
A linha Alpha registra alterações relevantes em várias armas, entre elas:
- Ruyi Jingu Bang — FX e active ability ajustados; passive adicionada;
- Blade of the Grotesque — active concede imunidade total, preserva momentum e recebeu FX/sprite changes;
- Matterbane — textura/dye saturation ajustados;
- Tidebreaker — reshading;
- Jester Penetrate — reparo com qualquer wool block, incluindo modded.

Outras armas passaram por reworks nas Alpha anteriores. Esses deltas são lineage da 1.3.0 Alpha, não justificativa para afirmar que toda unique antiga está concluída.

## 13. Lifecycle de abilities
O upstream registra que a maioria das abilities agora deve desaparecer quando:
- a arma é desselecionada;
- o owner morre;
- o owner sai do mundo/servidor.

Isso é uma correção de state leakage. Testar especialmente buffs persistentes, summoned/fragment entities, cooldowns e efeitos presos ao player após relog.

## 14. Config migration
A Alpha inicial avisa explicitamente: **a atualização quebra arquivos de config e vários valores serão resetados para defaults**.

Logo, upgrade/downgrade entre 1.2.x e 1.3 Alpha não deve ser tratado como troca transparente. Antes de qualquer mudança de build, exportar/diffar config e validar weapon attributes/effect blacklists.

## 15. Delta exato ALPHA 5
O File ID 8736778 registra somente três mudanças em relação à Alpha 4:
1. fix de crash quando player entra em dedicated server;
2. fix de client crash ao gerar fragmento de **Soulfracture**;
3. fix de crash da **Blade of the Grotesque** ao tentar criar statue entity.

Esses três casos são regression gates diretos da build física. Não atribuir outros fixes à Alpha 5 especificamente.

## 16. Cadeia de fixes Alpha 1–4
A linha imediatamente anterior inclui correções de startup/dedicated-server e separação de client registration. Isso explica por que o join em dedicated server continua sendo teste prioritário mesmo após Alpha 5.

O fato de Alpha 5 corrigir três crashes não revoga o aviso upstream de crashes residuais em uniques antigas.

## 17. Multiplayer e server authority
Servidor deve decidir:
- proc/implicit real;
- damage/heal;
- cooldowns;
- transformation/remnant consumption;
- target exclusions;
- spell scaling funcional;
- persistent weapon/owner state.

Cliente pode renderizar FX, model, tooltip e previews, mas não pode criar fragments/statues funcionais ou conceder immunity apenas por packet local.

## 18. Integrações concretas no pack
- **Simply Swords 1.70.2:** provider obrigatório e principal boundary de API.
- **Simply Tooltips 0.1.5:** tooltips/implicits/cooldowns do ecossistema Simply.
- **Iron's Spells 3.16.3:** compat parcial da linha 1.3.
- **Epic Fight 21.17.3.1 + Epic Fight Compat:** o pack usa outro combat engine; não é a integração Better Combat documentada pelo Simply More. Compatibilidade deve ser provada em runtime, não presumida.
- **Patchouli 93:** capability opcional presente.

## 19. Riscos técnicos
1. **Alpha incompleta:** uniques antigas podem estar intencionalmente sem função.
2. **Crash residual:** autor avisa que older uniques ainda podem crashar.
3. **Config break/reset:** valores custom desaparecem entre linhas.
4. **Provider drift:** Simply Swords 1.70.x muda implicits/Awakening/API.
5. **Iron's partial compat:** somente reworked uniques recebem integração.
6. **Multi-hit duplication:** effect volta a disparar por entidade em vez de por swing.
7. **State leakage:** ability persiste após deselect/death/logout.
8. **Transformation duplication:** Reforming Remnant gera múltiplos outputs.
9. **Pet/mount friendly-fire:** exclusion falha em entities modded.
10. **Combat-engine conflict:** Epic Fight altera attack lifecycle esperado.

## 20. Matriz de testes
- [ ] Dedicated server inicia e player entra sem crash — regression Alpha 5.
- [ ] Soulfracture fragment é criado sem client crash — regression Alpha 5.
- [ ] Blade of the Grotesque cria statue sem crash — regression Alpha 5.
- [ ] Cada um dos 10 weapon types registra implicit correto.
- [ ] Lance montada aplica boost uma única vez e remove corretamente ao desmontar.
- [ ] Swing multi-target não multiplica effects migrados para once-per-swing.
- [ ] Wounded não aplica Bleed DOT histórico.
- [ ] Unique effect não atinge pet/mount protegido.
- [ ] Reforming Remnant consome/input/output exatamente uma vez.
- [ ] Reworked unique com Iron's usa scaling/API sem erro em 3.16.3.
- [ ] Unique não-reworked não recebe falsa compat Iron's.
- [ ] Buff/ability limpa ao trocar arma, morrer e relogar.
- [ ] Config migration/reset é conhecida antes de qualquer upgrade/downgrade.
- [ ] Epic Fight não duplica hit/proc nem quebra animação/attack state.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 21. Evidências e limites
- Modlist física de 11/09/2026: JAR/runtime/hash e providers presentes.
- CurseForge File ID 8736778: ALPHA 5 e seus três fixes exatos.
- Changelog Alpha inicial: warning de uniques incompletas/config break, implicits, Iron's compat, Reforming Remnant e lifecycle changes.
- Página oficial Simply More: 10 weapon types, 33 uniques, requisitos e compats opcionais.
- **Limite:** config local e completeness de cada uma das 33 uniques não foram testadas em runtime; o dossiê preserva explicitamente a incerteza do canal Alpha.
