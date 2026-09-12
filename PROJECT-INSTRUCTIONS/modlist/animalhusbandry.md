# AnimalHusbandry

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c869db9f0db81a5ba5dc211c53b5633
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** AnimalHusbandry
- **Arquivo JAR:** `AnimalHusbandry-neoforge-0.4.1.jar`
- **Versão 1.21.1:** 0.4.1
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:**
- **Categoria:** Mobs, Comida, QoL
- **Função:** Sistema completo de manejo de livestock: domesticação por alimento preferido, happiness, sickness, hunger, dehydration, grooming, Brush, Animal Medicine, Magnifying Glass, Farm Ledger, Feeding Troughs, Fertility Potion, genética hereditária (traits, color, pattern, yield, fertility, growth, constitution), personalities e comportamentos por espécie como truffle hunting de pigs. Não há evidência suficiente nas fontes auditadas para afirmar um sistema separado de gestação por espécie; a ficha anterior foi corrigida para não inventar isso.
- **Dependências:** Architectury API é requisito declarado; o pack instala `architectury-13.0.11-neoforge.jar`. Minecraft 1.21.1 e Java 21 são requisitos upstream.
- **Sobreposição:** Aprofunda os animais de fazenda existentes em vez de adicionar apenas nova fauna. Sobrepõe qualquer mod que altere breeding/genética/necessidades de livestock, mas não é equivalente a grandes mob packs como Alex's Mobs.
- **Compatibilidade/Riscos:** Early Access. A 0.4.1 adiciona config para desabilitar custom animal textures quando o usuário prefere outros texture mods. O pack usa Fresh Animations; o projeto oferece compatibilidade/resource-pack específico para custom coats/eyes/face animations e a prioridade deve ser validada. Se o compat pack não estiver ativo, a alternativa é desabilitar custom textures via config. Sistemas externos que alteram breeding, animal AI, food tags ou textures podem sobrepor funcionalmente.
- **Observações:** Fonte upstream não sustenta a afirmação antiga de 'gestação específica por espécie'; removida do contrato até evidência direta. O mod está em Early Access e deve ser validado com Fresh Animations e resource packs de animais.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Animal Husbandry 0.4.1 + Architectury 13.0.11 e fontes já auditadas no dossiê. Reconciliação final: JAR/runtime permanecem exatamente `AnimalHusbandry-neoforge-0.4.1.jar` / `0.4.1`; sem divergência física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/animal-husbandry
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #28: `AnimalHusbandry-neoforge-0.4.1.jar` / `0.4.1` conferidos contra a modlist atual; versão 0.4.1 preservada como autoridade física, sem regressão para referências antigas 0.4.0; corpo técnico, decisão e estado preservados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

> 🔎 **Escopo canônico.** Runtime físico: `AnimalHusbandry-neoforge-0.4.1.jar`, NeoForge 1.21.1/Java 21. O mod está em **Early Access**. Esta ficha usa apenas mecânicas sustentadas pelo publisher; a antiga afirmação genérica de “gestação específica por espécie” foi removida porque as fontes auditadas não a comprovam.

## 1. Objetivo do sistema
Animal Husbandry transforma animais de fazenda passivos em um sistema de criação de longo prazo. O jogador precisa lidar com domesticação, bem-estar, alimentação, água, grooming, sickness e genética, em vez de apenas alimentar dois mobs e esperar o cooldown de reprodução.

O design é voltado a **livestock management** e pode ser configurado para packs survival mais punitivos ou experiências de farming mais leves.

## 2. Domesticação
Animais são domesticados alimentando-os com seu **preferred food**. A domesticação é pré-condição para algumas ferramentas de inspeção e manejo; changelogs antigos corrigiram inclusive uso indevido de Magnifying Glass/Farm Ledger em animais não domesticados.

Não presumir que “tamed” neste mod significa a mesma interface/classe de Wolf/Cat vanilla. Para integração técnica, verificar attachment/component/capability real usado pelo mod.

## 3. Care needs
O sistema documenta os seguintes estados/necessidades:
- **Happiness** — bem-estar geral.
- **Sickness** — condição de doença que exige manejo/cura.
- **Hunger** — necessidade de comida.
- **Dehydration** — necessidade de água.
- **Grooming** — cuidado físico/manutenção.

Esses valores mudam a criação de “farm automática sem atenção” para rotina de cuidado. Qualquer automação custom deve respeitar os valores reais e não simplesmente resetar timers.

## 4. Brush
A **Brush** é ferramenta de cuidado usada para restaurar/aumentar happiness e manter os animais em boa condição. Deve ser considerada em quests/tutorial do sistema, porque é parte do loop básico de manejo, não item cosmético.

## 5. Animal Medicine
**Animal Medicine** trata animals sick. O sistema de sickness deve ser testado com condições de negligência e cura para descobrir frequência, persistência e consequências na build 0.4.1.

## 6. Magnifying Glass
A **Magnifying Glass** mostra genética e care stats de um animal. É ferramenta de inspeção individual, útil para seleção de reprodutores.

A antiga versão 0.1.1 corrigiu acesso em animals não domesticados; portanto testar o gate na versão atual.

## 7. Farm Ledger
O **Farm Ledger** oferece relatório mais detalhado do animal/herd conforme o sistema atual. Serve como interface de gestão, complementando a Magnifying Glass.

Para qualquer overlay externo que mostre genetics/care, evitar duplicar informação se o Ledger já é a UI canônica.

## 8. Feeding Troughs
**Feeding Troughs** armazenam food e water para que animals se alimentem/bebam com menos interação manual. Isso cria uma superfície de semi-automação de curral.

Validar:
- alimentos aceitos;
- água/enchimento;
- range de detecção;
- consumo por animal;
- prioridade quando mais de um trough existe;
- hopper/pipe compatibility se o block expuser inventory/fluid interfaces.

Não presumir compat de pipes antes de testar.

## 9. Fertility Potion
A **Fertility Potion** aumenta readiness de reprodução e melhora genetics **por um breeding cycle** conforme a descrição oficial. Ela é uma ferramenta de seleção/otimização genética, não buff permanente universal.

Quests/economia devem tratar o item como parte de breeding control e não como potion de combate.

## 10. Genetics — propriedades herdáveis
O publisher documenta herança de:
- **traits**;
- **color**;
- **pattern**;
- **yield**;
- **fertility**;
- **growth**;
- **constitution**.

Essas propriedades tornam cada animal uma unidade persistente com valor reprodutivo. Ao cruzar animais, filhotes herdam combinações/qualidades segundo o sistema do mod.

### Consequências práticas
- **Yield:** influencia produtividade.
- **Fertility:** influencia capacidade/readiness reprodutiva.
- **Growth:** influencia velocidade/desenvolvimento.
- **Constitution:** relaciona-se à robustez/saúde conforme o design do sistema.
- **Color/pattern:** altera aparência e é particularmente sensível a resource packs/animation packs.

Valores/fórmulas exatos precisam ser lidos no source/config/runtime antes de perk ou economia usar multiplicadores numéricos.

## 11. Personalities
O sistema inclui personalities:
- **glutton**;
- **cuddly**;
- **grumpy**;
- **energetic**;
- **none**.

As personalities modificam comportamento/necessidades conforme implementação. Não reduzir a um simples flavor text sem verificar efeitos reais quando o sistema for usado em quests/perks.

## 12. Species-flavored behavior
O mod diferencia espécies por comportamento, yield, growth e trait bonuses. A descrição oficial destaca que não é um wrapper genérico idêntico para todo animal.

### Pigs e truffles
Pigs podem **caçar truffles** em blocos adequados. A busca pode alterar terreno:
- grass → dirt;
- dirt → coarse dirt.

Isso cria produção de recurso + modificação ambiental. Farms de pigs podem degradar visualmente o solo se não forem planejadas.

## 13. Breeding
O breeding passa a considerar estado do animal + genetics e pode ser influenciado pela Fertility Potion. O objetivo é seleção de linhagens, não apenas multiplicação de mobs.

> ⚠️ **Limite de evidência:** as fontes oficiais auditadas falam em breeding control, fertility, inherited genetics e animal care. Elas **não documentam um sistema independente de gestação/pregnancy por espécie**. Não usar essa afirmação em outro chat até o source/runtime comprovar.

## 14. Custom animal textures
Animal Husbandry adiciona custom textures/patterns para representar genetics/traits visuais. A build **0.4.1** foi publicada especificamente com uma nova config permitindo **desativar custom animal textures** quando o usuário quer usar outro texture mod/resource pack.

Isso é crucial nesta instância porque o pack usa uma camada visual extensa.

## 15. Fresh Animations
O projeto possui compatibilidade/resource-pack específico para **Fresh Animations**, necessário para que custom coats/patterns e faces/eyes/eyelids animadas se comportem corretamente quando as duas soluções são usadas juntas. A prioridade do pack de compat em relação ao Fresh Animations precisa ser respeitada conforme instruções do projeto.

No catálogo de resource packs já conhecido do projeto, Fresh Animations e extensões estão presentes, mas **não assumir automaticamente** que o pack específico de compat Animal Husbandry ↔ Fresh Animations está ativo. Isso deve ser verificado na etapa de resource packs.

### Estratégias válidas
1. usar custom textures do Animal Husbandry + compat pack Fresh Animations corretamente priorizado; ou
2. usar a opção 0.4.1 para desativar custom textures e deixar outro sistema visual ser authority.

Não misturar os dois caminhos sem validar resultados.

## 16. Dependência
### Architectury API
Requisito declarado. O pack instala `architectury-13.0.11-neoforge.jar`, atendendo a dependência para NeoForge 1.21.1.

### Java 21
O publisher lista Java 21, alinhado ao ambiente do projeto.

## 17. Configuração
O publisher enfatiza que o sistema é configurável para modpacks. A configuração é a autoridade para intensidade/dificuldade de care e progressão.

Ao integrar com economia/perks, primeiro inventariar config real de:
- care rates;
- sickness;
- breeding/fertility;
- genetics;
- texture toggle;
- species-specific tuning.

Não copiar valores de exemplos upstream sem confirmar a config instalada.

## 18. Sobreposição funcional
### Outros animal/farming mods
Qualquer mod que altere breeding cooldown, AI de feeding, genetics, food tags ou drop yield pode tocar a mesma superfície. Sobreposição precisa ser avaliada por espécie/hook.

### Alex's Mobs Continued
Não é duplicata funcional geral. Alex's Mobs adiciona fauna e comportamentos/ecossistemas próprios; Animal Husbandry aprofunda **livestock management**. Apenas mobs explicitamente suportados pelo husbandry entrariam em cruzamento.

### Fresh Animations/resource packs
É sobreposição visual real, já reconhecida pelo próprio projeto e mitigada por compat pack/config 0.4.1.

## 19. Riscos específicos do pack
1. **Early Access:** bugs/regressões ainda são plausíveis.
2. **Double breeding logic:** outro mod pode disparar breeding sem respeitar fertility/care.
3. **AI contention:** trough seeking pode competir com goals externos.
4. **Texture conflict:** custom genetics coats vs Fresh Animations/resource packs.
5. **Performance:** herds grandes mantêm estados persistentes + AI de cuidado/feeding.
6. **Economia:** high-yield genetics podem multiplicar produção quando combinadas com perks/automation.
7. **Terrain:** truffle pigs podem converter solo de currais.
8. **Persistence:** genetics/care precisam sobreviver relog, chunk unload, dimension transfer e server restart.

## 20. Matriz de validação
1. Domesticar cada espécie vanilla suportada usando preferred food.
2. Confirmar gate de Magnifying Glass/Farm Ledger antes/depois de domesticação.
3. Deixar hunger/dehydration/grooming/happiness variar e registrar consequência.
4. Induzir/observar sickness e curar com Animal Medicine.
5. Usar Brush e medir alteração de happiness.
6. Feeding Trough: food, water, range e consumo.
7. Testar hopper/pipe somente se capability estiver exposta.
8. Reproduzir dois animals com genetics diferentes e comparar filhote.
9. Repetir com Fertility Potion e confirmar efeito limitado ao ciclo.
10. Testar traits, color, pattern, yield, fertility, growth e constitution em várias gerações.
11. Personalities: observar glutton/cuddly/grumpy/energetic.
12. Pig truffle hunting em grass/dirt e transformação do terreno.
13. Custom textures ON sem Fresh Animations.
14. Custom textures ON + Fresh Animations + compat pack, verificando prioridade.
15. Custom textures OFF com Fresh Animations como authority.
16. Resource reload e relog: patterns não devem trocar indevidamente.
17. Chunk unload/reload e server restart: genetics/care persistem.
18. Herd grande: medir tick/AI cost.
19. Outros mods de animal/breeding: detectar double-trigger.
20. Dedicated server: sync de genetics/UI e consistência cliente-servidor.

## 21. Regras para outros chats
- Não afirmar pregnancy/gestation sem source/runtime.
- Genetics numéricas só podem alimentar perks/economia depois de confirmar fórmula real.
- Texture compatibility é parte funcional da ficha, não detalhe cosmético irrelevante.
- Preferir config/API do mod a mixins para balanceamento.
- Qualquer perk que aumente yield/fertility deve considerar genetics base para evitar multiplicadores explosivos.

## 22. Versão 0.4.1
Release NeoForge publicada em 03/09/2026. Mudança confirmada sobre 0.4.0: **config para desativar custom textures dos animais**, atendendo usuários que preferem outros texture mods.

A versão física atual é 0.4.1. A antiga observação da página que dizia runtime 0.4.0 estava incorreta e foi corrigida.

## 23. Fontes e confiança
**Authority física:** modlist 07/09/2026.

**Upstream:** [CurseForge — Animal Husbandry](https://www.curseforge.com/minecraft/mc-mods/animal-husbandry) e release [0.4.1 NeoForge](https://www.curseforge.com/minecraft/mc-mods/animal-husbandry/files/8798239).

**Confiança:** alta para care system, genetics categories, tools, trough, Fertility Potion, personalities, pig truffles, requirements e texture toggle. Fórmulas internas e espécie exata suportada precisam de source/runtime antes de se tornarem contratos técnicos.
