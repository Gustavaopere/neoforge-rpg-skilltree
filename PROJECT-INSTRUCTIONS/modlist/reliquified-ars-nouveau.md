# Reliquified Ars Nouveau

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81ef9796ce519c416152
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `reliquified_ars_nouveau-1.21.1-0.8.1.jar`, mod id `reliquified_ars_nouveau`, runtime `0.8.1`, mixin `reliquified_ars_nouveau.mixins.json`; Relics 0.12.8, Ars Nouveau 5.13.1 e Reliquified Artifacts 1.0.8 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Reliquified Ars Nouveau 0.8.1, Relics 0.12.8, Ars Nouveau 5.13.1 e Reliquified Artifacts 1.0.8 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Reliquified Ars Nouveau
- **Arquivo JAR:** `reliquified_ars_nouveau-1.21.1-0.8.1.jar`
- **Versão 1.21.1:** 0.8.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, Magia, RPG
- **Função:** Addon de conteúdo cruzado Relics × Ars Nouveau que adiciona relics temáticas e integra sua progressão/loot a mecânicas e conteúdo do Ars sem substituir nenhum provider-base.
- **Dependências:** Required: Relics 0.12.8 e Ars Nouveau 5.13.1, ambos presentes. Reliquified Artifacts 1.0.8 também está presente e é alvo de fix explícito da 0.8.1.
- **Sobreposição:** Não substitui Ars Nouveau nem Relics. Relics continua authority de progression/abilities; Ars de mana/Source/spells/world-content; o addon conecta os dois domínios.
- **Compatibilidade/Riscos:** Beta com justificativa concreta no stack atual. Riscos: Relics/Ars API drift, double charge/double cast, loot duplication, stale Curios state e regressão cruzada com Reliquified Artifacts. 0.8.1 corrige Whirlisprig petals e compat com Reliquified Artifacts.
- **Observações:** Mantida a decisão Manter de 06/09/2026. A 0.8.1 é prerelease, mas contém fixes diretamente relevantes ao stack instalado; não há fundamento para downgrade automático apenas pelo canal.
- **Procedência:** modlist.txt física canônica de 10/09/2026 + publicação/dependências oficiais Reliquified Ars Nouveau + changelog exato 0.8.1 + changelogs históricos usados apenas para lineage de conteúdo.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/reliquified-ars-nouveau
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Reliquified Ars Nouveau 0.8.1 reconstruído: ownership Relics/Ars, loot, resource causalidade, fix Whirlisprig/Reliquified Artifacts, lifecycle, riscos e testes.
- **Histórico da decisão:** 2026-09-06 — pesquisa fechada em Manter. A versão 0.8.1 é prerelease, mas contém fix direto para outro addon instalado do mesmo ecossistema, justificando sua permanência.
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `reliquified_ars_nouveau-1.21.1-0.8.1.jar`, mod id `reliquified_ars_nouveau`, versão `0.8.1`, NeoForge 1.21.1. É um addon de conteúdo cruzado entre **Relics** e **Ars Nouveau**. A build 0.8.1 é Beta e foi mantida porque contém correções posteriores relevantes ao stack atual, incluindo compatibilidade com Reliquified Artifacts.

## 1. Identidade e papel
- **Mod:** Reliquified Ars Nouveau.
- **JAR:** `reliquified_ars_nouveau-1.21.1-0.8.1.jar`.
- **Mod id:** `reliquified_ars_nouveau`.
- **Versão instalada:** `0.8.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Beta.
- **Ambiente publicado:** Client & Server.

## 2. Dependências e stack físico
A publicação oficial exige **Relics** e **Ars Nouveau**. No pack atual:
- Relics 0.12.8;
- Ars Nouveau 5.13.1;
- Reliquified Artifacts 1.0.8 também presente e diretamente relevante ao fix da 0.8.1.

O addon não funciona como substituto de nenhum dos dois providers-base.

## 3. Papel no modpack
Adiciona relics temáticas que usam mecânicas/conteúdo do Ars Nouveau e podem ser encontradas em estruturas ou biomas adicionados pelo Ars Nouveau ou seus addons, conforme a documentação oficial do projeto.

A progressão continua pertencendo a Relics; mana, Source, glyphs, spell recipes e conteúdo mágico continuam pertencendo a Ars Nouveau.

## 4. Autoridade / ownership
- **Relics:** progression, XP, levels/ranks, relic ability lifecycle e framework de relics.
- **Ars Nouveau:** mana/Source, spell/glyph systems, mobs/structures/biomes e recursos do próprio mod.
- **Reliquified Ars Nouveau:** conteúdo cruzado e adaptação entre os dois domínios.

Uma relic deste addon não deve criar um segundo ledger de mana ou Source quando utiliza recursos do Ars.

## 5. Conteúdo confirmado e limites
Changelogs históricos do projeto confirmam relics como **Whirlisprig Petals**, **Spiked Cloak**, **Horn of the Wild Hunter** e **Wing of the Wild Stalker**. Esses nomes sustentam lineage real do addon, mas não são tratados aqui como inventário completo da 0.8.1.

A 0.8.1 especificamente corrige o uso de **Whirlisprig petals** e compatibilidade com Reliquified Artifacts.

## 6. Delta exato da 0.8.1
O changelog da build instalada registra:
- correção do uso de **Whirlisprig petals**;
- correção de compatibilidade com **Reliquified Artifacts**.

Esses dois pontos são regression gates obrigatórios da ficha.

## 7. Loot e descoberta
A documentação oficial informa que relics podem aparecer em estruturas ou biomas adicionados pelo Ars Nouveau ou addons relacionados. Isso representa integração de loot/world-content, mas não transfere ownership de worldgen para o addon.

Validar uma única inserção por loot source; dois injectors não podem duplicar a mesma relic no mesmo evento sem intenção explícita.

## 8. Recursos mágicos e causalidade
Quando uma relic aciona efeito baseado em Ars, devem ser preservados:
- owner correto;
- resource provider correto;
- consumo uma única vez;
- cooldown/progressão da relic uma única vez;
- ausência de double-cast causado por listeners paralelos.

Não foi inferido que todas as relics consumam mana/Source; o teste deve ser feito somente nas que efetivamente utilizam esses recursos.

## 9. Client / Server
O addon é Client & Server. Progressão e efeitos funcionais devem ser server-authoritative; partículas, modelos, tooltip e feedback podem ser client-facing.

A presença de Ars e Relics em ambos os lados não autoriza o cliente a conceder XP, item ou efeito funcional sozinho.

## 10. Lifecycle
Validar:
- loot/acquire;
- Curios equip/unequip;
- login/relogin;
- death/respawn;
- dimension change;
- server restart;
- Ars resource regeneration/consumption quando aplicável;
- relic XP/level/rank progression;
- reload de datapack/loot quando suportado;
- interação com Reliquified Artifacts 1.0.8.

## 11. Multiplayer
Duas pessoas usando a mesma relic devem manter XP, resource consumption e target/owner separados. Efeitos de área ou summon relacionados ao Ars precisam preservar causalidade do caster e não creditar progressão ao jogador errado.

## 12. Integrações concretas no pack
- **Relics 0.12.8:** framework obrigatório.
- **Ars Nouveau 5.13.1:** provider mágico obrigatório.
- **Reliquified Artifacts 1.0.8:** compatibilidade explicitamente corrigida pela 0.8.1.
- Addons Ars presentes podem contribuir estruturas/biomas elegíveis para loot, mas cada integração concreta deve ser confirmada por data/runtime antes de ser declarada ativa.

## 13. Riscos técnicos
1. **Relics API drift:** ability/progression quebra após update do framework.
2. **Ars API drift:** recurso/spell/entity hooks mudam.
3. **Double charge/double cast:** resource e ability processados duas vezes.
4. **Loot duplication:** injeção múltipla em estrutura/bioma.
5. **Cross-addon regression:** conflito com Reliquified Artifacts — superfície corrigida na 0.8.1.
6. **Stale Curios state:** modifier/effect permanece após unequip/relog.
7. **Beta drift:** mudanças de schema/IDs entre releases.

## 14. Matriz de testes
- [ ] Dedicated server inicia com 0.8.1 + Relics 0.12.8 + Ars 5.13.1.
- [ ] Relic do addon é obtida pelo fluxo de loot esperado sem duplicação.
- [ ] Equip/unequip aplica e remove state uma única vez.
- [ ] Relic que usa recurso Ars consome exatamente o esperado e não duplica cast.
- [ ] XP/progressão Relics persiste após relog e restart.
- [ ] Whirlisprig Petals não reproduz o problema corrigido na 0.8.1.
- [ ] Reliquified Artifacts 1.0.8 coexiste sem conflito no cenário corrigido.
- [ ] Death/respawn e dimension change não deixam modifiers/cooldowns stale.
- [ ] Dois jogadores não compartilham state/XP indevidamente.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 15. Evidências e limites
- Modlist física: JAR/mod id/runtime e versões dos providers presentes.
- Publicação oficial: required dependencies Relics + Ars Nouveau e ambiente Client & Server.
- Changelog 0.8.1: fixes de Whirlisprig petals e Reliquified Artifacts.
- Changelogs históricos: exemplos de relics usados apenas como lineage confirmada.
- **Limite:** não foi inventariado todo o registry da 0.8.1; qualquer relic não confirmada por fonte foi deliberadamente omitida.
