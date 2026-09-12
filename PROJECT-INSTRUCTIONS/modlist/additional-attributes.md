# Additional Attributes

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81cc9594e40f341c3e59
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Additional Attributes
- **Arquivo JAR:** `additional_attributes-1.21.1-1.2.2.jar`
- **Versão 1.21.1:** 1.2.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, RPG
- **Função:** Provider de atributos mecânicos e bridges data-driven. Em 1.21.1 expõe Looting, Fishing Lure/Luck (documentação pública contém possível typo no ID de Fishing Luck), Harvest, Apothic Crafting, spell-level modifiers para Iron's em escopo geral/escola/spell, innate schools/spells e `keep_scroll`. 1.2.2 move `skip_innate` para server-side e corrige innate spells em dedicated server. Respiration foi removido na linha 1.21 por existir oxygen attribute vanilla.
- **Dependências:** Projeto sem hard dependency obrigatória externa publicada; Iron's Spells aparece como optional dependency na release 1.2.2. Integrações Apothic/Iron's ativam apenas quando providers correspondentes existem. Pack atual possui Iron's 3.16.3 e Apotheosis/Apothic Attributes, portanto ambas as superfícies são relevantes.
- **Sobreposição:** Pufferfish's Attributes, Apothic Attributes, Iron's gear/addons e RPG Skill Tree podem afetar grandezas finais semelhantes. Additional Attributes não os substitui: registra atributos específicos e bridges. Apothic Crafting não é o mesmo que affix generation normal do Apotheosis; é chance aplicada a itens craftados.
- **Compatibilidade/Riscos:** Alto risco de double-dipping com outros attributes/perks que alterem loot, harvest, affix rarity ou spell level. Documentação 1.21 mistura exemplos antigos `spell_school_*`/`spell_type_*` com nota de migração para `school/<namespace>/<path>` e `spell/<namespace>/<path>`; usar sintaxe 1.21 e verificar IDs no registry antes de código. A página pública mostra Fishing Luck com o mesmo ID de Fishing Lure, provável typo documental; fail-closed até confirmar JAR. `spell_general` + school + spell são agregados antes do cálculo; negativos podem reduzir level a 0 e impedir cast.
- **Observações:** Dois pontos de documentação exigem cuidado: Respiration ainda aparece em subseção antiga apesar da nota 1.21 dizer que foi removido; Fishing Luck é listado com o mesmo ID de Fishing Lure. Não 'corrigir' por adivinhação. Antes de perk/datapack, inspecionar registry real do JAR 1.2.2.
- **Procedência:** Modlist física 2026-09-07 + Modrinth oficial Additional Attributes documentação 1.21 + release 1.2.2 + guia gameplay do projeto.
- **Fonte:** https://modrinth.com/mod/additional-attributes
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — dossiê operacional completo de enchantment/harvest/Apothic crafting/spell-level/innate/scroll attributes, configs, tags e dedicated-server fixes confirmado no QC global #5.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

> 🔎 **Escopo canônico.** Runtime físico: `additional_attributes-1.21.1-1.2.2.jar`. Additional Attributes converte várias grandezas de gameplay em **attributes manipuláveis** e integra esses attributes com Apotheosis/Apothic e Iron's Spells. A documentação pública 1.21 contém dois trechos inconsistentes; esta ficha os preserva como alertas em vez de inventar correções.

## 1. Arquitetura geral
Minecraft attributes são valores base + modifiers. Additional Attributes cria novos attributes para pontos que normalmente são controlados por enchantments, harvest rules ou APIs de outros mods.

Isso permite que equipamento, efeitos, perks e outros sistemas modifiquem essas grandezas pelo pipeline de attributes, mas também cria risco de **várias sources diferentes alterarem o mesmo resultado final**.

## 2. Dependências
O projeto não publica hard dependency externa obrigatória para carregar o core na linha auditada.

A release 1.2.2 publica **Iron's Spells 'n Spellbooks como optional dependency**. Integrações com Apotheosis/Apothic também só fazem sentido quando o ecossistema correspondente está presente.

Neste pack:
- Iron's Spells = 3.16.3;
- Apotheosis = 8.8.0;
- Apothic Attributes = 2.10.1.

Portanto as bridges abaixo são operacionalmente relevantes mesmo não sendo hard requirements do JAR.

## 3. Mudanças específicas da linha 1.21
A documentação começa com duas notas importantes:
1. **Respiration attribute foi removido**, porque Minecraft passou a ter atributo vanilla de oxygen.
2. A sintaxe de spell-level foi unificada: antigos padrões `spell_school` / `spell_type` migraram para paths `school/<namespace>/<path>` e `spell/<namespace>/<path>`.

Essas notas prevalecem sobre exemplos históricos mais abaixo na própria página.

## 4. Enchantment Attributes
A documentação 1.21 lista attributes associados a enchantment-like effects.

### Looting
ID publicado:
`additional_attributes:looting`

O **base value** é o valor do enchantment que seria consultado. Consequência:
- `multiply_base` não gera benefício se a base/enchantment for 0;
- `multiply_total` sozinho também não cria valor quando a base e adições relevantes são 0;
- `addition` pode criar/aumentar valor e depois é afetada por `multiply_total`.

### Fishing Lure
ID publicado:
`additional_attributes:fishing_lure`

Segue a mesma regra de base ligada ao valor de enchantment consultado.

### Fishing Luck — inconsistência documental
A página pública mostra **Fishing Luck** mas repete o ID `additional_attributes:fishing_lure`.

> ⚠️ Isso parece um typo na documentação, mas esta auditoria **não corrige o ID por adivinhação**. Antes de qualquer perk/config/script que queira Fishing Luck, inspecionar o registry real do JAR 1.2.2.

### Respiration — removido em 1.21
Uma subseção antiga ainda lista `additional_attributes:respiration`, mas a própria nota superior da documentação 1.21 diz que o attribute foi removido por existir oxygen attribute vanilla.

Para runtime 1.21.1, tratar **Respiration como removido** até prova contrária no registry.

## 5. Semântica de operations nos enchantment attributes
Como a base é o valor do enchantment consultado:
- item sem enchantment + `multiply_base` = nada para multiplicar;
- item sem enchantment + `multiply_total` = igualmente sem efeito se não houver addition;
- `addition` introduz quantidade real;
- `multiply_total` pode escalar essa soma.

Qualquer perk deve ser testado com:
- base 0;
- base 1+;
- addition positiva/negativa;
- multiply_base;
- multiply_total;
- combinações.

## 6. Harvest Attribute
ID publicado:
`additional_attributes:harvest`

Objetivo: aumentar quantidade de itens colhidos.

### Regras publicadas
- `addition = 1` significa **um crop adicional**, não seed adicional;
- base value = quantidade inicial colhida.

Isso é importante para economia: o mod multiplica/expande o **produto da colheita**, não necessariamente qualquer drop da planta.

## 7. Harvest — riscos de automação
O pack possui agricultura/manual/automação de múltiplos mods. Antes de aplicar harvest modifier global:
- testar quebra manual;
- right-click harvest se outro mod oferece;
- machine harvest;
- Fortune;
- crop que gera vários outputs;
- seeds separadas do produto.

Se a machine não usa o mesmo harvest event/pipeline, o attribute pode não se aplicar — e isso não é necessariamente bug.

## 8. Apothic Crafting
Additional Attributes possui integração que dá chance de **item craftado nascer com affix Apotheosis**.

O valor do attribute corresponde conceitualmente ao **ordinal da rarity** e também à chance fracionária de subir para o próximo degrau.

### Baseline publicado
- `0–1`: entre nada e Common.
- `1–2`: Common ↔ Uncommon.
- `2–3`: Uncommon ↔ Rare.
- `3–4`: Rare ↔ Epic.
- `4–5`: Epic ↔ Mythic.
- `5–6`: Mythic ↔ Ancient.
- `>=6`: apenas Ancient no baseline de exemplo.

A documentação deixa claro que isso é exemplo baseado nas rarities padrão e que o valor corresponde ao ordinal de rarity.

## 9. Valor fracionário no Apothic Crafting
Exemplo oficial:
- attribute `1.3` → **30% de chance** de considerar Uncommon em vez do tier inferior correspondente;
- o atributo vanilla/modded **Luck** ainda afeta qual rarity é realmente escolhida.

Se o valor é exatamente `0`, nada acontece.

## 10. Apothic Crafting — server config
Existe configuração server-side para limitar a **raridade máxima craftável**.

A ordem de autoridade importa: um perk que aumente o attribute não deve furar o max rarity configurado pelo servidor.

## 11. Apothic Crafting blacklist tag
Tag publicada:
`additional_attributes:apothic_crafting_blacklist`

Itens nessa blacklist não devem receber o processo de affix crafting pela integração.

Projetos próprios que adicionem crafting especial devem respeitar a tag em vez de duplicar uma lista hardcoded.

## 12. Rarity clamps por datapack
A documentação permite definir min/max rarity por item em datapack.

Diretório publicado:
`additional_attributes/rarity_definitions`

Entradas podem definir:
- `items`: item, tag ou lista;
- `min_rarity` opcional;
- `max_rarity` opcional.

A clamp é aplicada **depois** da server config segundo a documentação.

## 13. Ordem de decisão do Apothic Crafting
Para auditoria, a lógica relevante é:
1. item é elegível/não blacklisted;
2. valor do attribute define faixa/ordinal e chance fracionária;
3. server max rarity limita;
4. rarity definition datapack aplica clamps;
5. Luck participa da seleção conforme lógica Apothic.

Antes de mexer na economia, testar essa ordem em runtime.

## 14. Iron's Spells — spell level geral
Attribute publicado:
`additional_attributes:spell_general`

Aplica-se a **todos os spells** da entidade.

## 15. Iron's Spells — por school
Para 1.21, a nota de migração diz usar:
`additional_attributes:school/<namespace>/<path>`

Exemplo conceitual:
`additional_attributes:school/irons_spellbooks/fire`

A documentação mais abaixo ainda mostra exemplos antigos `spell_school_*`; tratar esses exemplos como legado/mistura documental.

## 16. Iron's Spells — por spell
Sintaxe 1.21:
`additional_attributes:spell/<namespace>/<path>`

Exemplo oficial de third-party path em documentação:
`additional_attributes:spell/traveloptics/orbital_void`

Isso é especialmente útil para addons de Iron's no pack porque permite target namespaced sem o Additional Attributes conhecer o spell em compile-time.

## 17. Agregação dos três níveis
A documentação diz que:
- general;
- school;
- spell-specific

**funcionam como um único cálculo**: modifiers são coletados antes da determinação final do level.

Isso significa que não se deve aplicar três cálculos independentes manualmente em um perk externo.

## 18. Base value de spell-level attributes
A base desses attributes é o **level do spell sendo usado**.

A documentação alerta que modificar o **base** do attribute não possui efeito útil no sentido esperado, porque o cálculo parte do spell level dinâmico consultado. A integração deve usar modifiers da forma prevista.

## 19. Max-level 1
Por padrão, spells com **maximum level = 1** não recebem aumento de spell level, porque normalmente não faria sentido ultrapassar o único nível definido. Isso é configurável.

Antes de perk “+1 spell level”, testar spells max 1 e confirmar server config.

## 20. Sem rounding para cima
A documentação diz que **não há rounding** no sentido de promover fração ao próximo inteiro.

Exemplo publicado:
`1.75` resulta em **level 1**.

Logo valores fracionários podem ser desperdiçados até atingir o próximo inteiro efetivo.

## 21. Spell level chegando a zero
Se modifiers negativos reduzem o spell level a **0**, o spell **não pode ser castado**.

Isso cria possibilidade de debuffs/silence-like behavior por attribute, e precisa ser tratado com cuidado em PvP/PvE.

## 22. Attributes em todas as entidades
A documentação afirma que os spell-level attributes existem para **todas as entidades**, não somente player.

Consequência: mobs spellcasters de Iron's/addons podem ser buffados/debuffados pelo mesmo sistema se o modifier for aplicado neles.

Isso é altamente relevante para Acolyte, familiars, bosses e outros spellcasting mobs.

## 23. Innate School
ID pattern publicado:
`additional_attributes:innate_school/<namespace>/<path>`

Exemplo:
`additional_attributes:innate_school/irons_spellbooks/fire`

Spells daquela school ficam disponíveis **independentemente do spellbook/weapon atual**, conforme a integração.

## 24. Innate Spell
ID pattern:
`additional_attributes:innate_spell/<namespace>/<path>`

Exemplo:
`additional_attributes:innate_spell/irons_spellbooks/cloud_of_regeneration`

Permite disponibilizar um spell específico fora da seleção normal de spellbook/arma.

## 25. `skip_innate` — mudança 1.2.2
Release **1.2.2** moveu a config `skip_innate` para **server-side** porque a seleção de spells pode ser iniciada pelo servidor em multiplayer.

A mudança corrigiu problema em que **innate spells não funcionavam em dedicated server**.

Essa é uma correção crítica para este projeto: qualquer teste antigo em 1.2.1/singleplayer não é baseline da 1.2.2.

## 26. Keep Scroll
ID publicado:
`additional_attributes:keep_scroll`

Semântica:
- `0` = 0% de chance de preservar scroll;
- `1` = 100%;
- valores intermediários = probabilidade correspondente.

Base value = `0`.

### Operations
- `multiply_base` sozinho não funciona porque base é 0;
- `multiply_total` sem `addition` também permanece 0;
- precisa existir valor adicionado antes de multiplicação produzir efeito.

## 27. Keep Scroll — economia
Scrolls são uma peça central da progressão Iron's. Perks/gear que aumentem `keep_scroll` reduzem consumo efetivo de scrolls.

Auditar em conjunto com:
- Horn Merchant/Acolyte;
- loot de structures;
- crafting/trades;
- outras fontes de scroll preservation.

Não permitir duas rotas de “não consumir” rolarem separadamente se o design prevê uma chance única.

## 28. Sobreposição com Pufferfish's Attributes
Pufferfish possui muitos attributes adicionais. Dois mods podem alterar:
- loot;
- dano;
- mobilidade;
- resistência;
- outros resultados.

Só existe duplicate registry conflict se IDs colidirem. O problema mais provável é **duas estatísticas diferentes afetando o mesmo output**.

## 29. Sobreposição com Apothic Attributes
Apothic Attributes é provider do ecossistema Apotheosis e possui sua própria GUI/stats. Additional Attributes integra **Apothic Crafting** e outras grandezas adicionais.

Não fundir os namespaces ou assumir que um substitui o outro.

## 30. Sobreposição com RPG Skill Tree
A árvore própria deve, quando possível, aplicar modifier diretamente ao **attribute canônico** já existente em vez de criar cálculo paralelo.

Exemplos:
- perk de crop yield → `additional_attributes:harvest` se o contrato desejado corresponde exatamente;
- perk de keep scroll → `additional_attributes:keep_scroll`;
- spell-level school → attribute namespaced específico.

Mas isso só vale depois de validar registry IDs e semantics no runtime.

## 31. Documentação inconsistente — política fail-closed
Dois conflitos foram encontrados:

### Respiration
Nota 1.21: removido.
Subseção antiga: ainda listado.

**Decisão:** tratar como removido para 1.21.1 até registry provar o contrário.

### Fishing Luck ID
Fishing Lure e Fishing Luck aparecem com o mesmo ID na documentação.

**Decisão:** não adivinhar o ID de Fishing Luck; inspecionar JAR/registry antes de implementar.

## 32. Matriz de validação — enchantment/harvest
1. Looting base 0/1/3.
2. Looting addition.
3. Looting multiply_base/total.
4. Fishing Lure base/modifiers.
5. Descobrir registry ID real de Fishing Luck.
6. Confirmar ausência/presença real de Respiration.
7. Harvest crop simples.
8. Crop multi-output.
9. Seeds separadas do crop.
10. Fortune + Harvest.
11. Machine harvest vs manual.

## 33. Matriz de validação — Apothic Crafting
1. Attribute 0.
2. 0.5.
3. 1.0.
4. 1.3 e amostra estatística.
5. 3+.
6. >=6 baseline.
7. Luck baixo/alto.
8. Server max rarity.
9. Blacklist tag.
10. Datapack min clamp.
11. Datapack max clamp.
12. Item com custom rarity ecosystem.
13. `/reload` idempotente.

## 34. Matriz de validação — Iron's
1. `spell_general` +1.
2. School-specific +1.
3. Spell-specific +1.
4. Todos três simultâneos.
5. Modifier fracionário 0.75.
6. Negative até level 0 → cast bloqueado.
7. Spell max level 1.
8. Third-party school.
9. Third-party spell.
10. Mob spellcaster, não player.
11. Innate school.
12. Innate spell.
13. Dedicated server confirmando fix 1.2.2.
14. `skip_innate` server config.
15. `keep_scroll` 0/0.5/1.
16. keep_scroll com multiply sem addition.

## 35. Regras para outros chats
- Usar registry attributes existentes em vez de duplicar cálculo quando a semântica coincide.
- Não reintroduzir Respiration sem prova.
- Não adivinhar Fishing Luck ID.
- Para 1.21 usar sintaxe namespaced nova de school/spell.
- Spell-level modifiers general+school+spell são agregados; não calcular três vezes.
- `skip_innate` é server-side na 1.2.2.
- Negative spell level pode impedir cast; isso é efeito mecânico sério.

## 36. Fontes e confiança
**Authority física:** modlist 07/09/2026.

**Upstream:** [Additional Attributes — Modrinth](https://modrinth.com/mod/additional-attributes), documentação 1.21 e [release 1.2.2](https://modrinth.com/mod/additional-attributes/version/ZQ340JcB).

**Fonte interna:** guia completo gameplay/sistemas.

**Confiança:** alta para Harvest, Apothic Crafting, spell-level semantics, innate/keep-scroll e fix 1.2.2. Fishing Luck ID é explicitamente **não resolvido** devido à inconsistência documental e precisa de inspeção do runtime.
