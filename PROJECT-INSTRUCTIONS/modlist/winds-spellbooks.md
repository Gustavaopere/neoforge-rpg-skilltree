# Wind's Spellbooks

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81f996d1cfca594dff66
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Wind's Spellbooks
- **Arquivo JAR:** `wind_spellbooks-1.0.5.jar`
- **Versão 1.21.1:** `1.0.5`
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê reconstruído sobre catálogo 7/7 e fingerprints runtime; bridge Ironsable e causalidade catalogadas.
- **Categoria:** Magia; RPG
- **Compatibilidade/Riscos:** Riscos: double cast/damage/impulse com bridge física, recast attribution, movement/fall interactions e dedicated-server regressions. Source público 1.0.5 não pinado; values/API/signatures continuam não verificados até JAR inspection.
- **Decisão:** Sem decisão
- **Dependências:** Iron's Spells 3.16.3 no pack. Bridge `ironsable-wind-1.0.0.jar` presente para interação com Sable/IronSable.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/winds-spellbooks-irons-spells-n-spellbooks-addon
- **Função:** Addon de Iron's com Wind School própria: 7 spells upgradáveis, armor, staff/spellbook, Windmill e Aeromancer; runtime local já confirmou 7/7 registrations.
- **Histórico da decisão:** vazio
- **Observações:** Mod id `wind_spellbooks`, runtime 1.0.5. IDs confirmados localmente: wind_jump, tornado, iron_slash, aeropic, almighty_push, wind_blade, tailwind. Não inferir stats numéricos sem artefato/source.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Wind's Spellbooks 1.0.5 + auditoria Black Arcana/runtime/ASM existente.
- **Sobreposição:** Expande Iron's com escola própria. Ironsable x Wind é bridge física, não segunda escola; evitar double-application de impulse/damage.
- **Data da última decisão:** 2026-09-09

> 🌪️ **ESCOPO CANÔNICO.** Runtime físico: `wind_spellbooks-1.0.5.jar`, mod id `wind_spellbooks`, versão `1.0.5`. O addon adiciona uma **Wind School** completa ao Iron's Spells com 7 spells upgradáveis, armor, staff/spellbook, estrutura Windmill e Aeromancer. Iron's continua authority de mana/casting/spell framework; Wind's Spellbooks é provider do conteúdo Wind registrado sobre ele.

## 1. Versão e conteúdo público confirmado
A release 1.0.5 é a build atual NeoForge 1.21.1, publicada em 22/07/2026.
O projeto oficial lista:
- **7 new upgradable spells**;
- 1 wizard armor set;
- 1 structure, **Windmill**;
- 1 spellcaster, **Aeromancer**;
- 1 staff e 1 spellbook.

## 2. Catálogo runtime já auditado no pack
Auditoria anterior do Black Arcana/runtime confirmou 7/7 registrations na build instalada:
- `wind_jump`;
- `tornado`;
- `iron_slash`;
- `aeropic`;
- `almighty_push`;
- `wind_blade`;
- `tailwind`.
Esse catálogo é evidência do runtime local. Valores numéricos, damage formulas, cooldowns e configs não são inferidos sem source/JAR inspection.

## 3. Fingerprints estruturais já registrados
Logs runtime/ASM do próprio pack registraram superfícies estruturais:
- Wind Jump: entity + `ImpulseCastData` + projectile + effect;
- Tornado: entity + projectile + raycast;
- Iron Slash: potentiation + entity + impulse + teleport + effect;
- Aeropic: recasts + impulse + effect;
- Almighty Push: entity + effect;
- Wind Blade: shoot + entity + projectile;
- Tailwind: effect.
Esses fingerprints ajudam a decidir onde procurar hooks, mas não substituem signatures/API reais.

## 4. Authority e causalidade
- **Iron's Spells:** mana, spellbook, spell level, cooldown, cast lifecycle e spell power infrastructure.
- **Wind's Spellbooks:** Wind School, seus spells/equipment/Aeromancer/Windmill.
Não criar segundo cast pipeline nem aplicar dano/impulse novamente só porque a spell possui projectile/entity/effect derivados.

## 5. Ironsable x Wind's Spellbooks
A modlist atual contém `ironsable-wind-1.0.0.jar`, bridge específica entre Wind's Spellbooks e Sable/IronSable.
Boundary:
- Wind's Spellbooks mantém authority do spell;
- Sable mantém authority da física/sublevel;
- a bridge materializa efeitos no domínio físico.
Qualquer perk que interaja com knockback/impulse/terrain physics precisa evitar double-application entre spell e bridge.

## 6. Histórico de fixes relevantes
O catálogo anterior registra:
- 1.0.1: fix relacionado a fall damage de Tailwind;
- 1.0.3: fix de server crash ligado a Tornado.
A 1.0.5 instalada é posterior a ambos, mas dedicated-server regression test continua obrigatório.

## 7. World content
Windmill e Aeromancer introduzem aquisição/exploração além de scrolls. Para quests/perks:
- discovery deve usar structure/entity identity real;
- reentrar na mesma Windmill não gera progresso repetido;
- matar/encontrar Aeromancer só pode gerar milestone quando houver causalidade/identidade deduplicável.

## 8. Boundaries para RPG Skill Tree
- Cast válido pode gerar progressão apenas uma vez por ação causal.
- Projectiles/entities/recasts derivados não são automaticamente novos casts autorais.
- Impulse/teleport/effect não devem gerar Mastery por tick.
- School/spell power continua provider-native do Iron's.
- Se não houver hook seguro para distinguir cast manual de recast/efeito derivado, integração específica fica **FAIL-CLOSED**.

## 9. Riscos
1. **Double cast/damage/impulse:** spell + bridge física + listener externo.
2. **Recast attribution:** Aeropic ou outros recasts tratados como nova autoria do player.
3. **Dedicated-server regression:** histórico de Tornado server crash.
4. **Movement/fall interaction:** Tailwind/Wind Jump com Epic Fight/ParCool/Sable.
5. **Worldgen density:** Windmill adicionada em stack de structures já denso.
6. **No public source pin:** IDs/classes foram observados localmente, mas API/signatures permanecem não verificadas.

## 10. Matriz de testes
- [ ] Dedicated server inicia com Iron's 3.16.3 + Wind 1.0.5 + Ironsable Wind 1.0.0.
- [ ] 7/7 spells registram e podem ser carregadas sem missing registry.
- [ ] Cada cast paga mana/cooldown exactly-once.
- [ ] Tornado não reproduz server crash e não duplica entity/projectile.
- [ ] Tailwind não causa regressão de fall damage.
- [ ] Wind Jump/Impulse interagem corretamente com Epic Fight/ParCool/Sable.
- [ ] Aeropic recasts não geram double-Mastery/double-cost externo.
- [ ] Ironsable bridge aplica física uma única vez.
- [ ] Windmill/Aeromancer aparecem em worldgen/encounter sem duplicação anormal.
- [ ] Multiplayer mantém spell/entity ownership correto.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 11. Evidências
- **Modlist física 08/09/2026:** `wind_spellbooks-1.0.5.jar`, Iron's Spells 3.16.3 e `ironsable-wind-1.0.0.jar`.
- CurseForge oficial: Wind School, 7 spells, armor set, Windmill, Aeromancer, staff/spellbook; release 1.0.5.
- Auditoria Black Arcana/runtime existente: 7/7 ids e fingerprints ASM estruturais.

## 12. Limitação
Source público pinado da 1.0.5 não foi localizado nesta auditoria. Fórmulas, valores e API/signatures permanecem **não verificados** até inspeção/decompilação do JAR exato.
