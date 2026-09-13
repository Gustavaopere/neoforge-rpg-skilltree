# Somake

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db8167b32cc545b4cc90dc
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `somakespells-1.0.8-1.21.1-fix.jar`, mod id `somakespells`, runtime `1.0.8`, mixin `somakespells.mixins.json`; Iron's Spells 3.16.3, L_Ender's Cataclysm 3.33 e Born in Chaos 1.7.6 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Somake 1.0.8 fix e os providers Iron's/Cataclysm/Born in Chaos citados estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Somake
- **Arquivo JAR:** `somakespells-1.0.8-1.21.1-fix.jar`
- **Versão 1.21.1:** 1.0.8
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, RPG
- **Função:** Addon de Iron's Spells com mais de 50 feitiços, foco em Lightning/Fire/Aqua/Symmetry, Aqua School própria, sistema de elemental charges e equipamento mágico/armas associados.
- **Dependências:** Iron's Spells 'n Spellbooks é provider central e está presente em 3.16.3. Integrações publicadas relevantes incluem L_Ender's Cataclysm e Born in Chaos, ambos fisicamente presentes; outras addon schools só são paths ativos quando seus providers existem.
- **Sobreposição:** Amplia Iron's Spells; não substitui seu mana/casting/attribute framework. Sobreposição com outros addons de spells deve ser avaliada por school/spell IDs, effects e balanceamento, não apenas tema.
- **Compatibilidade/Riscos:** Addon grande de Iron's Spells. Riscos: spell/attribute API drift, charge duplication, school registration conflict, projectile/AoE double-hit, equipment modifier stacking, external-school IDs ausentes e combat-engine interaction. Build física é 1.0.8 fix para NeoForge 1.21.1.
- **Observações:** JAR físico `somakespells-1.0.8-1.21.1-fix.jar`, runtime 1.0.8. A ficha pública confirma integração com Cataclysm e Born in Chaos e reconhecimento de escolas externas em parte do sistema de charges; não se presume presença de toda escola opcional.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Somake Spells 1.0.8/fix para NeoForge 1.21.1 + stack Iron's/Cataclysm/Born in Chaos confirmado fisicamente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/somake-spells-irons-spells-addon
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Somake 1.0.8 reconstruído: 50+ spells, Aqua School, elemental charges, equipment/armor, Iron's Spells ownership, Cataclysm/Born in Chaos integrations, casting lifecycle, multiplayer, riscos e testes.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `somakespells-1.0.8-1.21.1-fix.jar`, mod id `somakespells`, versão `1.0.8`, NeoForge 1.21.1. Somake é um **addon de Iron's Spells 'n Spellbooks** com catálogo próprio de spells, Aqua School, elemental charges e equipamentos.

## 1. Identidade e papel
- **Mod:** Somake / Somake Spells.
- **JAR:** `somakespells-1.0.8-1.21.1-fix.jar`.
- **Mod id:** `somakespells`.
- **Versão:** `1.0.8`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Papel:** expandir o framework de Iron's Spells com escolas, spells, charges, armas e armaduras próprias.

## 2. Provider central — Iron's Spells
Iron's Spells `3.16.3` continua authority de casting, mana, spell attributes, spell registration base e infraestrutura de spellbooks/equipment compartilhada.

Somake registra conteúdo em cima desse framework. Um spell Somake não deve manter uma segunda fonte de mana ou ignorar regras server-side do provider sem comportamento explícito próprio.

## 3. Catálogo de spells
A página oficial anuncia **mais de 50 spells**, concentrados principalmente em:
- Lightning;
- Fire;
- Aqua;
- Symmetry;
com conteúdo adicional Blood e Ender.

O número total divulgado não implica que cada spell tenha a mesma maturidade ou integration surface; regressão deve ser feita por escola e por mecânica crítica.

## 4. Aqua School
Somake introduz uma **Aqua School** própria, com spells aquáticos e identidade separada das schools base do Iron's.

Registration de school, focus/icon, spell power/resistance e localization precisam resolver uma única vez. Addons que consultam schools por ID não devem criar alias duplicado.

## 5. Elemental charges
O projeto implementa um sistema de **charges elementais**, incluindo uma charge por elemento/escola suportada. A documentação menciona também reconhecimento de escolas fornecidas por outros addons, como Sound, Symmetry, Spirit e Geo quando seus providers estão presentes.

Charge gain/consume precisa ser server-authoritative e idempotente: o mesmo cast/hit não pode gerar duas charges por packet/event duplicado.

## 6. External-school boundary
Compatibilidade declarada com uma escola externa não prova que o provider esteja instalado. O pack pode conter apenas parte dessas schools.

Ao carregar, registry lookup de school ausente deve degradar de modo seguro em vez de provocar `NoSuchElement`, broken tooltip ou spell registration incompleta.

## 7. Lightning, Fire e Aqua gameplay
Essas escolas podem envolver projectiles, AoE, damage, control e effects. O servidor deve decidir hit/damage/effect; cliente apenas apresenta projectile/particles/sounds e prediction quando aplicável.

Multi-target/AoE é regression surface: um único impacto não pode ser liquidado duas vezes por collision + explosion/event hook.

## 8. Symmetry, Blood e Ender
Somake também amplia schools não estritamente elementais. Como outros addons do pack podem registrar spells/attributes nas mesmas escolas, IDs, tags e spell lists precisam coexistir sem sobrescrever entries de terceiros.

Balanceamento semelhante não é duplicação técnica por si só.

## 9. Armas próprias
A documentação pública lista equipamentos/armas temáticas, incluindo exemplos como Glacium Greataxe, Core Splitter, Witherite Glaive, Ruined Blade, Clef Sword, Hallow Sword, Rock Sword e Boltcutter.

Stats, spell bonuses e abilities desses itens pertencem ao addon; ataque-base/combat-engine externo pode alterar animation/hit timing, mas não deve duplicar modifiers.

## 10. Armaduras
Conjuntos publicados incluem Dark Metal Battlemage, Ceranium, Aquamancer e Abyssium Armor.

Equip/unequip/death/relog devem adicionar/remover atributos exatamente uma vez. Cosmetic/render layers não podem manter bonus depois de o item real sair do slot.

## 11. Grimoires e progressão
O catálogo existente deste projeto registra **Grimoires evolutivos ligados a escolas mágicas** como parte do escopo de Somake. Essa progressão deve permanecer vinculada ao state real do item/player previsto pelo addon.

Como a página pública curta não detalha toda a máquina de estados, o dossiê não inventa thresholds ou fórmulas não publicadas; eles devem ser validados em runtime/source antes de integração externa.

## 12. Cataclysm integration
O upstream declara integração com **L_Ender's Cataclysm**, que está fisicamente presente no pack.

Essa integration surface pode envolver materiais/entities/effects específicos; somente paths explicitamente registrados pelo addon devem ser atribuídos a Somake. Atualização do Cataclysm exige smoke test de recipes/items/spells relacionados.

## 13. Born in Chaos integration
**Born in Chaos** também está fisicamente presente e é citado como integração do projeto.

Entidades complexas podem ter imunidades, phases ou custom damage handling; spells Somake devem respeitar lifecycle do provider e não pressupor comportamento de mob vanilla.

## 14. Epic Fight / combat-engine boundary
O pack usa Epic Fight. Mesmo sem uma integração nativa específica atribuída aqui, melee weapons e cast animations podem passar por hooks de combate/animação externos.

Testar cast enquanto em combat mode, weapon swap, interruption e hit registration; não assumir que boot sem crash valida animation/state completo.

## 15. Casting lifecycle
Validar:
- spell learn/equip;
- cast start/channel/release;
- mana/cooldown consumption;
- interruption;
- projectile spawn/hit/despawn;
- death/relog durante active effect;
- dimension transfer;
- save/restart;
- spellbook/equipment swap.

Nenhum efeito temporário deve permanecer órfão depois que owner/caster deixa de ser válido.

## 16. Multiplayer e authority
Servidor deve decidir:
- mana/cooldown;
- charge gain/consume;
- spell damage/effects;
- projectile ownership;
- equipment modifiers;
- progression state.

Cliente pode renderizar FX e UI. Dois players não podem compartilhar charge/cooldown/progression por key incorreta.

## 17. Persistência
State persistente potencial inclui item/equipment data, grimoire progression e qualquer charge/progress que o addon defina como persistente.

Save/restart não pode reaplicar modifiers ou resetar state sem regra explícita. Dados temporários de cast não devem ser serializados como efeito permanente por engano.

## 18. Integrações concretas no pack
- **Iron's Spells 3.16.3:** provider obrigatório.
- **L_Ender's Cataclysm:** integração publicada e provider presente.
- **Born in Chaos:** integração publicada e provider presente.
- **Epic Fight:** regression surface de animação/combat lifecycle.
- Outros addons de Iron's coexistem; school/spell IDs e attribute stacking devem ser auditados por registro real.

## 19. Riscos técnicos
1. **Iron's API drift:** spell/attribute registration muda.
2. **Charge duplication:** mesmo evento concede/consome duas vezes.
3. **School collision:** ID/tag de addon externo conflita.
4. **Missing external school:** lookup de provider ausente causa erro.
5. **Projectile/AoE double-hit:** dois hooks liquidam o mesmo impacto.
6. **Equipment modifier stacking:** equip/relog reaplica attribute.
7. **Mob-provider incompat:** boss/entity custom recebe state inválido.
8. **Combat-engine conflict:** cast/melee animation altera timing lógico.
9. **Persistence drift:** grimoire/progress perde ou duplica state.

## 20. Matriz de testes
- [ ] Dedicated server inicia com Somake 1.0.8 + Iron's 3.16.3.
- [ ] Aqua School registra uma única vez e aparece corretamente.
- [ ] Spell de cada escola principal aprende/equipa/casta sem erro.
- [ ] Mana e cooldown são consumidos exatamente uma vez.
- [ ] Elemental charge ganha/consome sem duplicação.
- [ ] Ausência de uma school opcional não impede boot.
- [ ] Projectile/AoE multi-target não duplica dano por evento.
- [ ] Armaduras não duplicam modifiers após relog/equip swap.
- [ ] Armas funcionam com Epic Fight sem double-hit/state preso.
- [ ] Cataclysm integration funciona com provider atual.
- [ ] Born in Chaos entities não quebram spell lifecycle.
- [ ] Death/relog limpa casts/effects temporários.
- [ ] Progressão/Grimoires preservam state após save/restart quando aplicável.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 21. Evidências e limites
- Modlist física atual: Somake 1.0.8 fix, Iron's 3.16.3 e integrations relevantes presentes.
- CurseForge oficial: mais de 50 spells, escolas principais, Aqua School, charges, equipamento e integrações Cataclysm/Born in Chaos.
- Catálogo técnico anterior: Grimoires evolutivos como parte do escopo funcional do addon.
- **Limite:** fórmulas detalhadas, thresholds e state machine de cada spell/grimoire não foram inferidos sem documentação/source específico; runtime tests continuam pendentes.
