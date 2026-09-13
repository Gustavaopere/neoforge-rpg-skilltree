# Pufferfish's Attributes

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db8116810bc94ca1052a29
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `puffish_attributes-0.8.3-1.21-neoforge.jar`, mod id `puffish_attributes`, runtime `0.8.3`
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Pufferfish's Attributes 0.8.3 está presente. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Pufferfish's Attributes
- **Arquivo JAR:** `puffish_attributes-0.8.3-1.21-neoforge.jar`
- **Versão 1.21.1:** 0.8.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** RPG, Biblioteca
- **Função:** Adiciona uma camada ampla de atributos dinâmicos para sistemas RPG, incluindo stamina, tipos de dano/resistência, velocidades, regeneração, life steal, shred e outras estatísticas consumíveis por equipamentos, skills e efeitos.
- **Dependências:** NeoForge 1.21.1. Projeto independente na linha atual; consumers podem incluir skills, equipamentos, datapacks e outros sistemas RPG, mas nenhum consumer causal único é necessário para sua presença.
- **Sobreposição:** Sobreposição parcial de domínio com Additional Attributes, Wayward Attributes e Apothic Attributes; não há equivalência integral nem substituição automática.
- **Compatibilidade/Riscos:** Riscos: double scaling com Apothic/other attributes, misuse do dynamic base/NaN, stamina confundida com Epic Fight, shred ordering, life-steal loops e valores extremos de velocidade. Registries/APIs não são intercambiáveis por nome parecido.
- **Observações:** Release 0.8.3 NeoForge 1.21/1.21.1. Delta exato publicado: adição de traduções ausentes.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge/Modrinth/documentação oficiais Pufferfish's Attributes 0.8.3.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/puffish-attributes
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Pufferfish's Attributes 0.8.3 reconstruído: dynamic-base model, dano/resistência/shred, stamina, velocidades, healing/life steal, Fortune/XP, stack RPG, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `puffish_attributes-0.8.3-1.21-neoforge.jar`, mod id `puffish_attributes`, versão `0.8.3`, NeoForge 1.21/1.21.1. Pufferfish's Attributes fornece uma camada ampla de **atributos dinâmicos** para RPG e integrações. Foi separado historicamente de Pufferfish's Skills e hoje pode funcionar de modo independente; sua presença não implica que todas as estatísticas estejam sendo usadas pelo pack.

## 1. Identidade e papel
- **Mod:** Pufferfish's Attributes.
- **JAR:** `puffish_attributes-0.8.3-1.21-neoforge.jar`.
- **Mod id:** `puffish_attributes`.
- **Runtime:** `0.8.3`.
- **Loader/jogo:** NeoForge 1.21/1.21.1.
- **Canal:** Release.
- **Ambiente:** Client & Server.
- **Licença:** LGPLv3.
- **Papel:** registrar atributos dinâmicos consumíveis por equipment, effects, skills e outros systems RPG.
- **Decisão:** Sem decisão.

## 2. Arquitetura de atributos dinâmicos
O projeto diferencia seus atributos de um atributo vanilla comum com base fixa global. O **valor dinâmico de entrada atua como base efetiva**, e modifiers são aplicados sobre ele; o campo de base pode aparecer como `NaN` intencionalmente.

Essa arquitetura evita que vários providers precisem sobrescrever continuamente uma mesma base global, mas exige integração correta do consumer.

## 3. Famílias ofensivas
A documentação publica atributos para dano geral e especializado, incluindo Magic, Melee, Ranged, Tamed, Sword, Axe, Trident e Mace Damage.

A classificação melee/ranged depende do damage source/tags apropriados. Não inferir que uma arma modded entra numa categoria apenas pela aparência: testar o damage source real.

## 4. Famílias defensivas
Há Resistance, Magic Resistance, Melee Resistance, Ranged Resistance e Tamed Resistance, além de stats de shred como Armor, Toughness, Protection e resistências específicas.

Shred e resistance podem cruzar fortemente com Apothic Attributes e outros systems de defesa. O owner do modifier e a ordem de cálculo devem ser isolados antes de declarar conflito.

## 5. Stamina e mobilidade
O atributo **Stamina** altera o threshold de exhaustion que no vanilla é fixo em 4. Também existem atributos relacionados a Jump, Sprinting Speed, Mount Speed e Fall Reduction.

Isso é especialmente relevante num pack com Epic Fight/ParCool: a existência de `Stamina` nesta API não prova que ela esteja conectada automaticamente à stamina do Epic Fight.

## 6. Velocidades de ação
O conjunto inclui Breaking Speed, Mining Speed e velocidades específicas de Pickaxe/Axe/Shovel, além de Consuming Speed.

Testar stacking com haste, tool attributes e perks para evitar multiplicação fora da curva ou valores negativos/impossíveis.

## 7. Cura, regeneração e life steal
A documentação inclui Healing, Natural Regeneration e Life Steal. `Healing` escala restauração de vida; `Life Steal` restaura vida proporcional ao dano causado segundo a semântica do atributo.

Essas superfícies cruzam spells, consumíveis e sistemas de sustain, mas não substituem a lógica do evento de cura/dano original.

## 8. Fortune e Experience
Fortune aceita valores fracionários usando arredondamento probabilístico; Experience afeta ganho de XP orbs. Isso pode alterar progressão e loot de modo sutil.

Benchmarkar valores fracionários ao longo de muitas amostras em vez de concluir por poucos drops.

## 9. Stealth, Knockback e projectiles
Também são publicados Stealth, Knockback e velocidades de projéteis de Bow/Crossbow. Stealth afeta distância de detecção conforme consumer/AI integration; projectile speed precisa ser validada com armas vanilla e modded separadamente.

## 10. Release 0.8.3
A build exata instalada é Release NeoForge 1.21/1.21.1. O changelog específico de 0.8.3 registra **adição de traduções ausentes**.

Não há justificativa para atribuir mudança mecânica à 0.8.3 além desse delta sem source adicional.

## 11. Relação com Pufferfish's Skills
Attributes nasceu dentro do ecossistema Skills, mas hoje é projeto independente. Pufferfish's Skills moderno pode consumi-lo por configs/rewards, porém a presença dos dois JARs não comprova que uma árvore concreta use todos os atributos.

O addon Unofficial Additions também não substitui esta library; seus atributos antigos foram movidos para **Additional Attributes**, outro projeto.

## 12. Sobreposição com o stack RPG
O pack contém Apothic Attributes e outros providers de stats. Sobreposição conceitual não significa equivalência de registry/API.

Auditar por atributo concreto:
- qual ResourceLocation/mod id é lido pelo consumer;
- additive/multiplicative operation;
- base dinâmica;
- cap/min/max;
- sync cliente/servidor.

## 13. Client/server e persistência
Atributos funcionais devem ser server-authoritative. Modifiers vindos de equipment, skills e effects precisam entrar/sair exatamente uma vez em equip/unequip, death/respawn, relog e dimension change.

## 14. Riscos
1. **Double scaling** com outros sistemas de atributos.
2. **Dynamic-base misuse:** consumer trata `NaN`/base como atributo vanilla comum.
3. **Stamina confusion:** API não equivale automaticamente à stamina Epic Fight.
4. **Shred ordering:** redução de defesa pode ser aplicada em ordem inesperada.
5. **Life-steal loops:** dano refletido/secundário gera cura indevida.
6. **Speed extremes:** valores altos/negativos quebram ações.
7. **Registry/API drift** entre consumers.
8. **Client/server divergence** de modifiers.

## 15. Matriz de testes
- [ ] Dedicated server e cliente iniciam com 0.8.3.
- [ ] Modifier simples altera atributo e é removido sem residue.
- [ ] Melee/ranged/magic classificam damage sources representativos corretamente.
- [ ] Resistance/shred não aplica duas vezes com outro provider.
- [ ] Stamina só altera sistema que realmente consome o atributo.
- [ ] Life Steal não dispara em loop com reflection/secondary damage.
- [ ] Fortune fracionário converge estatisticamente ao comportamento esperado.
- [ ] Breaking/consuming/projectile speeds permanecem válidos em extremos configurados.
- [ ] Death/relog/dimension change preservam apenas modifiers persistentes corretos.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 16. Evidências e limites
- Modlist física: JAR, mod id/runtime e mixin config exatos.
- Publicação oficial: Pufferfish's Attributes 0.8.3 Release NeoForge 1.21/1.21.1, Client & Server, LGPLv3.
- Documentação oficial: arquitetura de dynamic attributes e famílias de stats acima.
- Changelog 0.8.3: missing translations.
- **Limite:** árvore/config/equipment consumers da instância não foram enumerados; presença do atributo não foi confundida com uso ativo.
