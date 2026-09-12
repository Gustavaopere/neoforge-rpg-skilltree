# Butchercraft

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db819b9d5cced20523e0d9
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Butchercraft
- **Arquivo JAR:** `butchercraft-2.6.5.jar`
- **Versão 1.21.1:** 2.6.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Comida, Mobs, Compat
- **Função:** Sistema semi-realista de abate/processamento animal: Butcher Knife, Meat Hook, Butcher Block, Meat Grinder, carcaças/cortes, mince/sausages e integração data-driven por loot tables/datapacks.
- **Dependências:** Sem hard dependency externa listada para o core 2.6.5. Extra Delight é integração opcional oficial.
- **Sobreposição:** Compartilha processamento animal/comida com outros mods, mas a cadeia de carcass/butchering é própria. Integrar por loot/datapack provider-native, não duplicar drops por eventos globais.
- **Compatibilidade/Riscos:** Interage com drops/loot de animais e mods de comida/criação. Riscos: slaughter event duplicado, loot tables conflitantes, carcass state em hooks/blocks, server/client effect duplication e breeding hooks. 2.6.5 corrige efeitos rodando em ambos os lados e crash ocasional de breeding sem child válido.
- **Observações:** Superfícies oficiais: Butcher Knife, Meat Hook, Butcher Block, Meat Grinder e foods. Meathook/Butcherblock e Butcher Knife são extensíveis por JSON datapacks e loot tables. Extra Delight é optional dependency.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Butchercraft 2.6.5 + source/documentação oficial 1.21.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/butchercraft
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — Butchercraft 2.6.5 físico confirmado; slaughter→carcass→Hook/Block→Grinder, datapack/loot authority e fixes de side/breeding 2.6.5 preservados. Runtime QA não executado.
- **Histórico da decisão:** Sem decisão formal. Em 09/09/2026, Butchercraft 2.6.5 foi revalidado contra a modlist física atual; a cadeia de processamento provider-native e Extra Delight opcional foram preservadas sem converter presença em decisão curatorial.
- **Data da última decisão:** não definida

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `butchercraft-2.6.5.jar`, mod id `butchercraft`, runtime `2.6.5`, NeoForge 1.21.1. O core não lista hard dependency externa; **Extra Delight é integração opcional**.

## 1. Papel e autoridade
Butchercraft adiciona uma cadeia de **abate e processamento de carcaças** em vez de limitar a obtenção de carne a drops instantâneos. É authority de seus carcass states, recipes/loot de butchering e máquinas/blocos próprios.

Compat externa deve usar loot tables/datapacks do provider e não acrescentar os mesmos cortes em um segundo `LivingDropsEvent`.

## 2. Butcher Knife
Ferramenta oficial para abater animais e cortar carne. O suporte para modpacks é data-driven: JSON pode associar entidades a loot table acionada pelo uso da faca.

Contrato:
- a ação deve settlement uma vez no servidor;
- loot table é authority do output;
- durability/cooldown/consumo seguem o item/provider;
- integração não deve gerar loot vanilla + loot de butchering duas vezes.

## 3. Meat Hook
Barra de ganchos para pendurar carcaças, especialmente criaturas grandes. O sistema de datapack pode carregar modelo/estado animado e produzir loot tables a partir do item/carcass colocado.

State crítico:
- uma carcaça por posição/slot conforme implementação;
- insert/remove exactly-once;
- chunk unload/restart preserva o objeto pendurado;
- quebra do hook não duplica carcass + outputs.

## 4. Butcher Block
Mesa para processar animais pequenos/cortar itens com faca. Compartilha o sistema data-driven de model/animation + loot outputs.

Não tratar o modelo renderizado como inventário real; state persistente do block entity/provider é authority.

## 5. Meat Grinder
Máquina/bloco que transforma carne em mince e também atua como sausage stuffer. Qualquer automação externa deve respeitar input/output/capability real do bloco, sem extrair produto antes do processamento completar.

Testar inserção manual e por automação quando o provider expõe handler compatível.

## 6. Cadeia operacional
Fluxo conceitual do mod:
1. animal/entidade elegível;
2. abate via Butcher Knife/regra suportada;
3. carcass/item intermediário;
4. processamento em Meat Hook ou Butcher Block;
5. cortes/subprodutos definidos por loot table;
6. processamento adicional no Meat Grinder para mince/sausages.

Nem toda entidade precisa percorrer exatamente todas as etapas; o datapack determina a receita/loot concreta.

## 7. Datapacks — Meat Hook/Butcher Block
A documentação oficial permite editar/adicionar entradas via JSON datapack. Essas entradas:
- associam item/model/animation ao processo;
- aceitam um item de entrada;
- produzem loot table como saída.

Isso é a superfície preferencial para integrar animais de outros mods. Não hardcodar reflection/registry patches se um datapack resolve.

## 8. Datapacks — Butcher Knife
Também é extensível por JSON, apontando entidade especificada para uma loot table ativada por interação com a faca.

Para modded mobs:
- usar ID/tag correto;
- loot table deve ser deterministicamente escolhida pelo provider;
- garantir que death/interaction hooks não disparem dois pipelines de loot.

## 9. Foods e Extra Delight
Butchercraft registra múltiplos tipos de carne/comida. A documentação oficial destaca receitas adicionais quando **Extra Delight** está instalado.

Extra Delight é optional dependency, não requirement do core. Ausência dele não deve quebrar registry/bootstrap do Butchercraft.

## 10. Release 2.6.5
Fixes oficiais relevantes:
- potion effects não rodam mais simultaneamente no servidor e cliente;
- correção de crash ocasional em breeding event quando não existe child apropriado.

Esses dois pontos viram gates explícitos de regressão no pack.

## 11. Breeding hooks
Butchercraft participa de eventos relacionados a animais; a 2.6.5 demonstra que breeding é superfície real. Regras:
- child nulo/inválido deve falhar fechado;
- não duplicar nascimento;
- integração de Animal Wellness/Husbandry não deve reexecutar callback de breeding;
- estado pai/filhote permanece server-authoritative.

## 12. Client/server
- slaughter, loot, carcass inventory/state, grinder e breeding: servidor/common;
- model/animation de carcaça e UI: cliente;
- effects de gameplay devem settlement somente no lado apropriado; 2.6.5 corrige exatamente uma regressão de double-side execution.

## 13. Compatibilidade com outros mods de animais/comida
O pack possui diversos sistemas de animais, TFC/food e addons de culinária. Integração segura:
- preferir tags/loot/datapack;
- não substituir drop table global de uma entidade sem registrar intenção;
- preservar nutrition/food components do mod que registra o item final;
- detectar duplicação de carne crua/carcass/drop.

## 14. Lifecycle
Validar:
1. slaughter manual;
2. carcass pickup/place;
3. hook/block load/unload;
4. server restart com carcass pendurada;
5. grinder em processamento durante restart;
6. datapack reload;
7. breeding de vanilla e modded animals;
8. resource reload de carcass models.

## 15. Riscos
1. Double loot por death + knife event.
2. Carcass duplication em break/unload.
3. Loot table missing após datapack reload.
4. Potion effect executado em ambos os lados por regressão.
5. Breeding child null/crash.
6. Integração com animal mods alterar entidade/drop duas vezes.
7. Automação extrair output antes do settlement real.

## 16. Matriz de testes
1. Cow/pig/sheep/chicken: slaughter→carcass→cuts.
2. Meat Hook: place/remove/break/restart.
3. Butcher Block: entrada, knife action e output.
4. Grinder: mince/sausage, save/reload.
5. Datapack custom para uma entidade de outro mod.
6. Loot exactly-once com Looting/other drop modifiers.
7. Breeding vanilla + principais animal mods do pack.
8. Extra Delight ausente/presente, sem broken recipes.
9. Dedicated server: nenhum efeito duplicado client/server.

## 17. Evidência
- modlist física atual: Butchercraft 2.6.5;
- CurseForge oficial: Knife, Hook, Block, Grinder e foods;
- documentação oficial de JSON datapacks/loot tables para Hook/Block/Knife;
- relations oficiais: Extra Delight opcional;
- changelog 2.6.5: fixes de side em potion effects e breeding child crash.

> 🥩 Authority canônica: **loot tables/datapacks do Butchercraft** definem o processamento. Para integrar mobs novos, estenda essas superfícies em vez de criar um segundo pipeline de drops.
