# Pufferfish's Skills

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db810482cbfe6008e1c259
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `puffish_skills-0.19.0-1.21-neoforge.jar`, mod id `puffish_skills`, runtime `0.19.0`; Pufferfish's Unofficial Additions 2.2.8 presente como consumer causal
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Pufferfish's Skills 0.19.0 e Pufferfish's Unofficial Additions 2.2.8 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Pufferfish's Skills
- **Arquivo JAR:** `puffish_skills-0.19.0-1.21-neoforge.jar`
- **Versão 1.21.1:** 0.19.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** RPG
- **Função:** Framework jogável para árvores de habilidades configuráveis, com nós, conexões, requisitos, custos, recompensas, fontes de experiência e API para extensões e gerenciamento de skills.
- **Dependências:** NeoForge 1.21.1. Consumer causal instalado: Pufferfish's Unofficial Additions 2.2.8 declara Pufferfish's Skills como Required Content.
- **Sobreposição:** Framework configurável de skill trees; pode coexistir com outros RPG systems. Sobreposição depende das árvores/rewards efetivamente carregados.
- **Compatibilidade/Riscos:** Framework Beta. Riscos: schema/config drift, XP farming, exclusive-root errors, IDs persistidos stale, abuso de comandos e addon/API drift. Skills não fornece automaticamente stamina/mana/atributos sem árvore/reward configurado.
- **Observações:** Build 0.19.0 Beta NeoForge 1.21/1.21.1 publicada em 02/09/2026. Release family adiciona exchange, level command e novo point event na API.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge/documentação oficial Pufferfish's Skills 0.19.0 + relação oficial do addon Unofficial Additions 2.2.8.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/puffish-skills
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Pufferfish's Skills 0.19.0 reconstruído e reclassificado como Dependência: datapack skill trees, states/roots, rewards, XP sources, exchange/commands, persistência, addon consumer, riscos e testes.
- **Histórico da decisão:** 2026-09-10 — reclassificado de Sem decisão para Dependência após confirmação de Pufferfish's Unofficial Additions 2.2.8 instalado e declarando Pufferfish's Skills como Required Content.
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `puffish_skills-0.19.0-1.21-neoforge.jar`, mod id `puffish_skills`, versão `0.19.0`, NeoForge 1.21/1.21.1. Pufferfish's Skills é um framework de árvores de habilidades **data-driven por datapacks**. A build 0.19.0 é Beta. Como `Pufferfish's Unofficial Additions 2.2.8` está instalado e declara Skills como Required Content, a decisão passa a **Dependência**.

## 1. Identidade e papel
- **Mod:** Pufferfish's Skills.
- **JAR:** `puffish_skills-0.19.0-1.21-neoforge.jar`.
- **Mod id:** `puffish_skills`.
- **Runtime:** `0.19.0`.
- **Loader/jogo:** NeoForge 1.21/1.21.1.
- **Canal:** Beta.
- **Papel:** engine configurável para skill trees, pontos, experiência, requisitos, rewards e extensões.
- **Decisão:** Dependência.

## 2. Causalidade da dependência
O pack contém `pufferfish_unofficial_additions-1.21.1-2.2.8.jar`. A publicação desse addon marca **Pufferfish's Skills como Required Content**.

Isso é suficiente para tornar Skills load-bearing na instalação atual: remover o framework isoladamente quebra/degrada um consumer físico confirmado.

## 3. Framework, não árvore pronta
O projeto é infraestrutura. Para haver progressão jogável, são necessárias árvores configuradas por datapack. A documentação oficial oferece editor online para produzir a configuração.

Logo:
- JAR presente ≠ árvore concreta presente;
- API disponível ≠ reward ativo;
- a progressão real do pack só pode ser declarada após auditar os datapacks/configs carregados.

## 4. Estrutura de categoria
A documentação atual descreve arquivos como:
- `category.json`;
- `definitions.json`;
- `skills.json`;
- `connections.json`;
- `experience.json`;
- `exchange.json`.

Eles vivem sob namespace datapack de `puffish_skills`. Cada camada tem ownership diferente: category/definitions estruturam o sistema; skills/connections formam a árvore; experience define fontes; exchange define troca de experiência/pontos.

## 5. Estados dos skills
A progressão publicada usa estados ordenados como **Excluded → Locked → Available → Affordable → Unlocked**.

Essa distinção é importante para UI e lógica: “visível” não significa “comprável”, e “comprável” não significa “desbloqueado”. Requisitos/custos precisam convergir server-side.

## 6. Roots e exclusividade
Árvores podem definir roots e `exclusive_root`, permitindo caminhos mutuamente exclusivos. O sistema armazena IDs de skills desbloqueados por jogador.

Regression gates: respeitar exclusividade, reset/lock correto e migração segura se um ID for removido/renomeado.

## 7. Rewards
A documentação publica rewards built-in como:
- Attribute;
- Command;
- Scoreboard;
- Tag;
- Points.

A API permite rewards custom. O reward concreto pertence à árvore/addon; Skills é owner da execução/persistência do framework.

## 8. Experience sources
As fontes built-in documentadas cobrem várias ações: crafting, advancements/criteria, deal/take damage, eat, enchant, fish, heal, stats, kill/shared kill, break/mine e smelt.

Há mecanismos anti-farming por chunk/entidade em superfícies documentadas. Configurar XP sem considerar automação/mob farms pode trivializar progressão.

## 9. Exchange e versão 0.19.0
A família 0.19.0 adiciona **exchange**, permitindo comprar pontos com níveis de experiência vanilla; também adiciona comando de level e um novo point event na API.

Como a mesma versão 0.19.0 é publicada para múltiplos targets, esses deltas são tratados como mudanças da release family, não como comportamento exclusivo do arquivo NeoForge 1.21.1.

## 10. Comandos administrativos
A documentação inclui comandos para points, experience, level e manipulação de skills/category, como add/set, unlock/lock/reset e operações correlatas.

Em servidor, permissions devem impedir progressão arbitrária por usuário comum. Toda automação/quest que chama comandos deve ser auditada para execução exatamente uma vez.

## 11. Persistência por jogador
IDs de skills desbloqueados e progressão precisam sobreviver a relog/restart. Mudanças de datapack são perigosas quando removem IDs ainda persistidos em players.

Testar migração, reset seletivo e comportamento de jogador offline após atualizar árvore.

## 12. Configuração inválida
O próprio projeto orienta que configuração inválida gera mensagem de “Invalid configuration” e consulta aos logs. Isso é um gate operacional: não mascarar erro de schema como ausência de árvore.

Se nenhuma árvore estiver instalada, a mensagem correspondente deve ser interpretada como falta de conteúdo/config, não como crash da engine.

## 13. Integração com Unofficial Additions
O addon #470 amplia experience sources/rewards e integra Iron's Spells. Ownership:
- Skills → engine, árvore, points/XP/state;
- Unofficial Additions → fontes/rewards extras;
- Iron's → spell/cast authority.

Spells contínuos e harvest sources devem ser testados contra limites anti-farming e ganho de XP real.

## 14. Relação com Pufferfish's Attributes
Pufferfish's Attributes é projeto separado. Uma árvore pode aplicar seus atributos como reward/consumer, mas isso precisa estar explícito na configuração.

Não tratar Skills 0.19.0 como provider automático de stamina, mana ou combat stats.

## 15. Client/server e lifecycle
Progressão deve ser server-authoritative. Cobrir login, relog, death, dimension change, datapack reload, restart, tree reset, command operations e sync para clientes observando UI.

Alterar datapack em produção exige backup e smoke test de players existentes.

## 16. Riscos
1. **Beta framework:** 0.19.0 ainda é prerelease.
2. **Config/schema drift:** árvore antiga pode ficar inválida.
3. **XP farming:** sources mal calibradas trivializam progressão.
4. **Exclusive-root errors:** paths mutuamente exclusivos liberam juntos.
5. **Persisted IDs stale:** skill removido continua salvo.
6. **Command abuse:** permissões inadequadas concedem points/levels.
7. **Addon/API drift:** Unofficial Additions pode esperar outra contract.
8. **False integration:** atributos/mana/stamina não são automáticos sem árvore/reward real.

## 17. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Skills 0.19.0 + addon 2.2.8.
- [ ] Datapack de árvore válido carrega sem warnings críticos.
- [ ] Estados Locked/Available/Affordable/Unlocked evoluem corretamente.
- [ ] Exclusive root impede combinação proibida.
- [ ] XP source representativa concede uma vez e respeita anti-farming.
- [ ] Reward de Attribute/Command/Points aplica exatamente uma vez.
- [ ] Exchange consome níveis vanilla e concede pontos conforme config.
- [ ] Relog/restart preserva unlocks e points.
- [ ] `/reload` não duplica listeners/rewards.
- [ ] Remover/renomear skill em cópia de teste trata IDs persistidos de forma controlada.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 18. Evidências e limites
- Modlist física: JAR, mod id/runtime e mixin config.
- Publicação oficial: build 0.19.0 Beta NeoForge para 1.21/1.21.1, publicada em 02/09/2026.
- Documentação oficial: arquivos de categoria/árvore, estados, roots, rewards, sources, commands e persistência.
- Pufferfish's Unofficial Additions 2.2.8: Required Content = Pufferfish's Skills, sustentando `Dependência`.
- **Limite:** datapacks/árvores locais efetivamente carregados não foram abertos neste lote; nenhum perk ou integração concreta foi inventado.
