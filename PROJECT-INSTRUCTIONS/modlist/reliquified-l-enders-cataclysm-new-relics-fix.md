# Reliquified L_Ender's Cataclysm — New Relics Fix

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d569db9f0db81e0b0a6d6a81d2c6ed3
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `reliquified-lenders-cataclysm-new-relics-fix-1.0.2.jar`, mod id `reliquified_lenders_cataclysm_new_relics_fix`, runtime `1.0.2`; Relics 0.12.8, Curios 9.5.1, OctoLib 0.6.2, L_Ender's Cataclysm 3.33 e Reliquified L_Ender's Cataclysm 0.1.1 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, a bridge 1.0.2 e todo o stack citado estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Reliquified L_Ender's Cataclysm — New Relics Fix
- **Arquivo JAR:** `reliquified-lenders-cataclysm-new-relics-fix-1.0.2.jar`
- **Versão 1.21.1:** 1.0.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, RPG
- **Função:** Bridge de compatibilidade que adapta Reliquified L_Ender's Cataclysm 0.1.1 da API Relics 0.10 para Relics 0.12.x por Mixins/bytecode, sem substituir o addon original.
- **Dependências:** Presentes fisicamente: Relics 0.12.8, Curios 9.5.1, OctoLib 0.6.2, L_Ender's Cataclysm 3.33 e Reliquified L_Ender's Cataclysm 0.1.1.
- **Sobreposição:** Não é duplicata do addon-base. Reliquified Cataclysm define o conteúdo temático; este fix traduz/adapta chamadas antigas para o framework Relics 0.12.x.
- **Compatibilidade/Riscos:** Bridge de alto acoplamento à API Relics. Riscos: transform/mixin drift, double adaptation após eventual suporte nativo, Curios modifiers stale/duplicados, XP/rank/cooldown migration e motion packet desync. As cinco relics corrigidas permanecem regression gates.
- **Observações:** Revalidado sem reescrever o corpo já completo. A bridge continua separada do addon-base e não assume ownership de relic gameplay; adapta Reliquified Cataclysm 0.1.1 ao Relics 0.12.x.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + JAR/mixins do fix 1.0.2 + publicação oficial do fix + stack físico atual de Relics/Curios/OctoLib/Cataclysm/Reliquified Cataclysm.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/reliquified-l-ender-s-cataclysm-new-relics-fix
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — bridge 1.0.2 revalidada contra o stack físico atual: Relics 0.12.8, Curios 9.5.1, OctoLib 0.6.2, Cataclysm 3.33 e Reliquified Cataclysm 0.1.1; corpo técnico preservado.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `reliquified-lenders-cataclysm-new-relics-fix-1.0.2.jar`, mod id `reliquified_lenders_cataclysm_new_relics_fix`, versão `1.0.2`. Este mod é uma **bridge de compatibilidade** para manter Reliquified L_Ender's Cataclysm 0.1.1 funcional com Relics 0.12.x; ele não substitui o addon original.

## 1. Identidade, versão e papel
- **Mod:** Reliquified L_Ender's Cataclysm — New Relics Fix.
- **JAR físico:** `reliquified-lenders-cataclysm-new-relics-fix-1.0.2.jar`.
- **Mod id:** `reliquified_lenders_cataclysm_new_relics_fix`.
- **Versão:** `1.0.2`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Papel:** adaptar chamadas antigas do addon `reliquified_lenders_cataclysm` feitas contra a API Relics 0.10 para a API Relics 0.12, usando Mixins e transformação de bytecode sem modificar o JAR original.

## 2. Stack físico confirmado
A modlist canônica contém:
- **Relics:** `relics-1.21.1-0.12.8.jar`.
- **Curios API:** `curios-neoforge-9.5.1+1.21.1.jar`.
- **OctoLib:** `OctoLib-NEOFORGE-0.6.2+1.21.jar`.
- **L_Ender's Cataclysm:** `L_Enders_Cataclysm 1.21.1-3.33.jar`.
- **Reliquified L_Ender's Cataclysm:** `reliquified_lenders_cataclysm-1.21.1-0.1.1.jar`.

Todos os requisitos publicados pelo fix estão fisicamente presentes.

## 3. Authority e ownership
O fix não deve se tornar authority de relic gameplay. Ownership permanece dividido assim:
- **Relics 0.12.8:** framework atual de RelicTemplate, ranks, levels, XP, cooldowns e infraestrutura Relics.
- **Reliquified L_Ender's Cataclysm 0.1.1:** conteúdo/identidade das relíquias do Cataclysm.
- **L_Ender's Cataclysm:** mobs, drops e conteúdo-base de origem.
- **Curios:** slots/equip state.
- **este fix:** somente tradução/compatibilidade entre APIs antigas e atuais.

## 4. Problema que a bridge resolve
A descrição oficial informa que o addon original referencia classes e métodos da antiga API **Relics 0.10** removidos ou alterados na **Relics 0.12**. Sem bridge, isso pode produzir startup errors, linkage failures ou abilities quebradas.

A bridge implementa adaptação sem substituir o JAR original, reduzindo o escopo da intervenção a pontos de compatibilidade.

## 5. Correções funcionais publicadas
A release 1.0.2 documenta:
- correção de startup errors ligados ao `IRelicItem` removido;
- conversão de definições antigas para o sistema `RelicTemplate`;
- restauração de Curios integration e attribute modifiers;
- adaptação de stats, levels, ranks, cooldowns e experience;
- restauração de legacy active ability behavior;
- substituição do antigo player-motion network packet;
- preservação da ordem de abilities e progression values;
- compatibilidade de descriptions/tooltips.

## 6. Relíquias explicitamente corrigidas
O upstream lista exatamente cinco relics:
1. **Void Cloak**.
2. **Scouring Eye**.
3. **Void Vortex in Bottle**.
4. **Vacuum Glove**.
5. **Void Bubble**.

Não foi inferido que outras relíquias sejam alteradas pela bridge sem evidência publicada.

## 7. Client / server e networking
O mod é publicado como Client & Server. A migração inclui substituição de packet de player motion, portanto há superfície de networking real.
- servidor deve continuar authority de progressão/state funcional;
- cliente recebe/renderiza tooltip, Curios state e efeitos locais;
- packet replacement precisa ser testado em multiplayer para evitar double execution, rubber-banding ou ability que só funcione em singleplayer.

## 8. Lifecycle
Validar:
- startup com os cinco requisitos presentes;
- criação/loot/equip de cada relic;
- Curios equip/unequip;
- gain de XP/level/rank e persistência após relog/restart;
- cooldown begin/end e dimension change;
- active ability em singleplayer e dedicated server;
- morte/respawn preservando ou limpando state conforme Relics;
- atualização/reload de datapacks sem perder templates.

## 9. Integrações concretas no pack
- **Relics 0.12.8:** alvo principal da bridge; qualquer update 0.12.x→linha maior é regression gate prioritário.
- **Curios 9.5.1:** equip slots e modifiers.
- **OctoLib 0.6.2:** requisito publicado do stack.
- **Cataclysm 3.33:** provider dos conteúdos-base relacionados.
- Outros addons Reliquified instalados compartilham o framework Relics, mas esta bridge não deve ser aplicada a eles por associação nominal.

## 10. Riscos técnicos
1. **API drift:** Relics update pode tornar Mixins/transforms incompatíveis.
2. **Double adaptation:** se o addon original ganhar suporte nativo à Relics 0.12, manter o fix pode duplicar transforms.
3. **Curios modifiers:** stale/duplicated modifiers após equip/unequip/relog.
4. **Progression migration:** XP/rank/cooldown podem resetar ou duplicar se IDs/serialização divergirem.
5. **Networking:** player-motion replacement precisa de causalidade única e server authority.
6. **Tooltip only ≠ state:** descrição correta não prova que ability/progressão funciona.

## 11. Matriz de testes
- [ ] Dedicated server boot sem linkage error de `IRelicItem`.
- [ ] Todas as cinco relics aparecem/instanciam corretamente.
- [ ] Equip/unequip em Curios sem modifier duplicado.
- [ ] Level/rank/XP avançam uma vez e persistem após restart.
- [ ] Cooldowns não resetam/duplicam indevidamente.
- [ ] Active abilities das cinco relics funcionam em singleplayer.
- [ ] Active abilities funcionam em multiplayer remoto.
- [ ] Motion-related behavior sem desync/rubber-band.
- [ ] Tooltips/descriptions corretos após evolução da relic.
- [ ] Remover o fix em ambiente de teste reproduz a incompatibilidade esperada; reinstalar restaura o stack.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 12. Evidências
- Modlist física canônica de 10/09/2026: JARs e versões do stack.
- CurseForge oficial do fix 1.0.2: propósito, API 0.10→0.12, técnicas Mixins/bytecode, oito famílias de correção, cinco relics e requisitos.
