# A0001 — Treino com Espadas I

## Status e proveniência

- **Design:** APROVADO após reauditoria obrigatória.
- **Código relevante:** PRESENTE.
- **Implementação:** CONFIRMÁVEL após CI/merge desta auditoria.
- **Notion:** https://app.notion.com/p/3c569db9f0db8165adfcc38d24e537f1
- **Critérios locais:** `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

## Especificação canônica

- **Domínio/Árvore:** MARTIAL / Epic Fight — Espadas.
- **Ramo/Camada/Função:** Ritmo e Velocidade / 1 / Ramo.
- **Ranks/Custo:** 3 ranks; 1 ponto por rank.
- **Gate:** nível 8 + `epicfight:sword` ≥60 + Gateway `epic_sword`.
- **Efeito:** +3% de dano com espadas por rank, máximo +9%.
- **Hook:** categoria provider-native de espada + dano corpo a corpo direto normalizado.
- **Fallback corrigido:** se o Epic Fight não expuser classificação server-side segura de espada, A0001 fica inativa para o item. Não inferir categoria por nome, material, aparência ou dano-base e não manter tag paralela não versionada.
- **Regra:** `FUNDAMENTO_EXTERIOR: ESPADAS`; provider-native first; ausência de classificação segura é FAIL-CLOSED.

## Auditoria — 9 eixos

1. **Gates:** PASS — nível, mastery e gateway explícitos.
2. **Integração global:** PASS — pipeline marcial único; nenhuma stamina/recurso paralelo.
3. **Identidade:** PASS COMO FUNDAMENTO — node basal, não Notable/Capstone.
4. **Topologia:** PASS — camada 1 após gateway.
5. **Especializações:** PASS — pode ser fundamento mapeado; não desbloqueia especialista sozinho.
6. **PT-BR:** PASS.
7. **Notion:** PASS após correção do fallback fictício.
8. **NeoVitae:** PASS — nenhuma dependência.
9. **Modlist/integrações:** PASS — Epic Fight é owner da classificação; itens não classificados ficam fail-closed.

## Evidência técnica

- `NotionCombatPerkRules.baseDamageMultiplier`: `WeaponFamily.SWORD -> A0001`, +3%/rank.
- `A0001A0020CombatPolicy.beforeHit`: aplica somente a hit direto/hostil elegível.
- `A0001A0020EpicFightHooks.family`: resolve famílias via capability do Epic Fight e ignora categoria desconhecida.
- `onDamagePre`: aplica o modificador no `EpicFightDamageSource` server-side.

## Pendências

**Nenhuma bloqueante.** A antiga pendência da tag `rpgskilltree:swords` foi removida porque a tag não possuía contrato real e contrariava provider-native first. O comportamento canônico agora é fail-closed.

## Testes

- [x] coeficiente/rank;
- [x] policy de dano direto;
- [x] classificação Epic Fight;
- [x] ausência de heurística de fallback;
- [x] CI/build e dedicated-server smoke exigidos antes do merge.

## Fechamento Chat 1 V3 — ciclo exato A0001–A0010

- **Re-fetch canônico:** registro do Notion consultado novamente em 2026-08-30; propriedades, gate, hook, fallback e regra permanecem alinhados a este dossiê.
- **Mutação no Notion neste ciclo:** não necessária; nenhum drift ou campo contraditório foi encontrado.
- **Cobertura de providers:** Epic Fight 21.17.3.1 permanece o owner da família de arma. `Epic Fight Compat` e armas/addons externos só participam quando resultarem em capability/classificação explícita do Epic Fight; itens sem classificação segura ficam inativos para A0001.
- **Mods periféricos:** Protection Pixel e demais equipamentos/bridges que não fornecem classificação de espada não são providers desta perk e não recebem integração nominal artificial.
- **Resultado:** **APROVADA / FECHADA** no lote A0001–A0010. O Chat 2 deve preservar o fail-closed e nunca restaurar tags ou heurísticas paralelas de classificação.

## Chat 2 — implementação, testes e merge — PR #221

**Estado neste commit:** `IMPLEMENTAÇÃO VALIDADA EM CI`; torna-se `IMPLEMENTAÇÃO CONFIRMADA` quando a PR #221 for mergeada na `main`.

- [x] Hook de dano direto de espada implementado no pipeline canônico.
- [x] Gate/ranks/dependências representados pelo contrato runtime e testes.
- [x] Provider-native confirmado para Epic Fight 21.17.3.1.
- [x] FAIL-CLOSED confirmado para categoria de arma não resolvida; sem tag/heurística paralela.
- [x] Pipeline único e ausência de double-processing confirmados.
- [x] Testes de contrato/policy presentes.
- [x] `RPG Skill Tree CI` #1991 verde no SHA `6a826ecd836dccf85e5c683c1f0eb0bd32d88b74`.
- [x] JUnit, NeoForge GameTests, build, verificação do JAR e dedicated-server smoke verdes.

**Pendências técnicas:** nenhuma para A0001 no provider/versionamento atual.

## Auditoria retroativa de integração — projetos próprios + Mobstein 5.4.4 — 2026-08-30

- **RPG Skill Tree:** `COBERTA POR PERK EXISTENTE`; authority de rank/efeito e deduplicação permanece no pipeline canônico A0001–A0020. Não escrever attachments/estado paralelo.
- **Volcanoes:** `NÃO DEVE SER INTEGRADO` a A0001. Geologia, Atmosphere, respiração, pressão e hazards não alteram o multiplicador marcial de espada. Um alvo relacionado ao projeto continua sendo apenas alvo do ataque Epic Fight direto.
- **Enshrouded:** `NÃO DEVE SER INTEGRADO` a A0001. Shroud/Exposure/Flame/Story não são fonte de dano de espada nem gate deste fundamento.
- **Black Arcana:** `COBERTO POR SISTEMA UNIVERSAL` somente para ataques diretos do jogador contra entidades válidas. `ARCANE_BACKLASH` é terminal e nunca herda A0001, não recursa e não gera Mastery/proc.
- **Mobstein 5.4.4:** `COBERTO POR SISTEMA UNIVERSAL` para golpe direto do jogador contra mobs/bosses; dano de ally/bodyguard ressuscitado permanece Mobstein-owned e não herda A0001 por ownership.
- **Fallback/fail-closed:** nenhum bridge adicional. Sem ação direta + família `SWORD` provider-native, o bônus não aplica.
- **Notion:** nenhuma mutação necessária nesta perk; a exigência existente de ataque direto já expressa o boundary.
- **Estado histórico:** PR #221 já está mergeada; a implementação A0001 está confirmada. Esta retroauditoria não altera runtime.
