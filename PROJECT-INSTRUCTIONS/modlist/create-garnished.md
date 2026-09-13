# Create: Garnished — 2.1.9.2

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db813f9230dc22aa269443  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-09

## Propriedades do registro

- **Mod:** Create: Garnished
- **Arquivo JAR:** `garnished-2.1.9.2+1.21.1-neoforged.jar`
- **Versão 1.21.1:** `2.1.9.2`
- **Categoria:** Comida; Tecnologia; Automação
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://github.com/DakotaPride/create-garnished-forge/tree/bd5180a5a659dc6fa7cfdddd928e1a6c16776793
- **Função:** Addon Create de agricultura/nuts, processamento de alimentos e materiais, bebidas, decoração e recipes integradas à maquinaria Create; inclui cadeias de peanut/cashew/hazelnut/almond, nut products, salt e outras interações.
- **Dependências:** Create; source pin 2.1.9.2 usa Create 6.0.9-215, Ponder 1.0.80, Registrate MC1.21-1.3.0+62 e Curios 9.2.2+1.21.1. Pack usa Create 6.0.10, exigindo regression smoke test.
- **Compatibilidade/Riscos:** Create version drift, recipe/tag overlap com food/Create addons, Curios effect duplication, generator/world interaction, stale recipes após reload e documentação wiki desatualizada. Não foram inventadas contagens/IDs do registry 2.1.9.2.
- **Sobreposição:** Sobreposição temática com Farmer's Delight e outros addons Create de comida/processamento não é redundância automática. Compare recipes/inputs/outputs concretos; Create permanece authority de cinética/processors e Garnished do conteúdo/recipes que registra.
- **Observações:** Runtime físico 2.1.9.2. O branch 1.21.1 já avançou além da versão instalada; esta ficha usa commit exato. Wiki público não foi usado como authority de contagens/IDs. Source referencia compats/dev com Farmer's Delight, My Nether's Delight, Create Aquatic Ambitions e Create Dragons Plus.
- **Procedência:** modlist.txt física atual de 09/09/2026 + source oficial Create Garnished commit bd5180a5a659dc6fa7cfdddd928e1a6c16776793 exatamente em 2.1.9.2 + distribuição/documentação oficial, com wiki desatualizado tratado fail-closed.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Create: Garnished 2.1.9.2 source-pinned; nut/food processing, Create contracts, dependencies, data/reload, compats, lifecycle, risks e test matrix catalogados sem extrapolar o wiki desatualizado.
- **Data da última decisão:** 2026-08-26

## Dossiê operacional — padrão Alex's Mobs

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `garnished-2.1.9.2+1.21.1-neoforged.jar`, mod id `garnished`, Minecraft 1.21.1 / NeoForge. O branch oficial já avançou além da versão instalada; esta ficha usa o commit `bd5180a5a659dc6fa7cfdddd928e1a6c16776793`, que declara exatamente `mod_version=2.1.9.2`. O wiki público do projeto está explicitamente desatualizado, portanto não é usado como autoridade para contagens ou registry IDs da 2.1.9.2.

## 1. Identidade e versão
- **Mod:** Create: Garnished.
- **JAR físico:** `garnished-2.1.9.2+1.21.1-neoforged.jar`.
- **Mod id:** `garnished`.
- **Versão instalada:** `2.1.9.2`.
- **Minecraft / loader:** 1.21.1 / NeoForge.
- **Source pin:** commit `bd5180a5a659dc6fa7cfdddd928e1a6c16776793`, exatamente 2.1.9.2.

## 2. Dependências e baselines do source
O commit pinado declara:
- Create para Minecraft 1.21.1, baseline `6.0.9-215`;
- Ponder `1.0.80`;
- Registrate `MC1.21-1.3.0+62`;
- Curios `9.2.2+1.21.1`;
- NeoForge baseline `21.1.93`.
O pack usa **Create 6.0.10**, portanto existe um pequeno version drift acima do baseline de desenvolvimento do commit; isso é regression gate, não incompatibilidade presumida.

## 3. Papel no modpack
Garnished expande o Create com agricultura/ingredientes de nuts, processamento industrial de alimentos e materiais, bebidas, decoração e receitas/mecânicas que aproveitam máquinas Create. O addon deve manter Create como authority de cinética, recipes/processors e contraptions; Garnished é authority do conteúdo e das receitas que registra.

## 4. Sistema de nuts e alimentos
A documentação oficial sustenta uma cadeia de **peanuts, cashews, hazelnuts e almonds**, com processamento em ingredientes e produtos derivados. Conceitos publicados incluem crush/separate/process-extract/garnish/plate, além de produtos como nut butter, salt e nut milk.
**Nut Milk** é documentado com comportamento semelhante a milk na remoção de efeitos. Quantidades exatas de itens/recipes e IDs não são afirmadas sem registry/source enumeration confiável da 2.1.9.2.

## 5. Processamento Create
O addon foi desenhado para usar os fluxos do Create em suas cadeias produtivas. Crushing, separation e demais etapas devem ser tratadas como recipes/data do addon executadas pelos mecanismos do Create. Integrações externas não devem conceder outputs por observar animação; a conclusão authoritative é o recipe settlement do processor/provider.

## 6. Recursos, decoração e world interactions
A documentação pública confirma:
- processamento/uso de **Salt**;
- famílias decorativas de bricks coloridos;
- geração de Cobblestone em condição envolvendo Blackstone e water;
- mecanismos/conteúdo de **Pneumatic Air**.
Essas superfícies precisam ser validadas em runtime/datapacks da versão física antes de usar IDs ou recipes específicos em quests/perks.

## 7. Curios e itens equipáveis
Curios aparece como dependência de desenvolvimento no source pinado. Qualquer item equipável registrado pelo addon deve respeitar o slot/API do Curios e ter efeitos liquidados server-side quando alteram gameplay. Não presumir lista/slots concretos sem registry pinado.

## 8. Integrações de desenvolvimento presentes no source
O commit 2.1.9.2 referencia, para ambiente de desenvolvimento/compatibilidade:
- Farmer's Delight `1.2.8`;
- My Nether's Delight `1.7.8`;
- Create Aquatic Ambitions `1.0.0`;
- Create Dragons Plus `1.6.4`;
- além de referências Sodium/Iris para o stack de render.
Essas referências mostram superfícies consideradas pelo projeto, mas **não provam hard dependency nem garantem que toda integração esteja ativa no pack**. Cada compat deve ser validada separadamente.

## 9. Dados, recipes e Ponder
Ponder/Registrate no build sustentam integração de descoberta/registro com o ecossistema Create. Recipes/tags/data packs são parte crítica do addon e devem sobreviver a `/reload` sem duplicação ou cache stale. O wiki desatualizado não deve substituir os recipes efetivos do JAR/datapack da instância.

## 10. Client / server
- **Servidor/common:** recipes, item/block state, consumo/produção, efeitos de alimentos, geração/interações de mundo e qualquer Curio com gameplay.
- **Cliente:** models, textures, particles, Ponder e apresentação.
O client não deve decidir conclusão de recipe, efeito removido por alimento nem geração de recurso.

## 11. Lifecycle e multiplayer
Validar world creation/chunk generation quando houver world interactions, save/reload de inventories/processors, datapack reload, contraption assemble/disassemble quando blocos compatíveis forem móveis, death/reconnect com Curios e multiplayer usando a mesma máquina/recipe simultaneamente.

## 12. Sobreposição no pack
Há sobreposição temática com Farmer's Delight e outros addons Create de alimentos/processamento, mas isso não equivale a redundância. A decisão deve comparar receitas, ingredientes, máquinas e outputs concretos. Garnished não deve duplicar ownership de recipes de outro mod apenas porque ambos produzem alimento ou sal.

## 13. Riscos técnicos
- drift Create 6.0.9 baseline → 6.0.10 do pack;
- recipes/tags conflitantes com outros food/Create addons;
- public wiki desatualizado induzir IDs/contagens errados;
- duplicação de outputs se duas compat recipes cobrirem o mesmo input;
- Curios integration com slot/effect duplicado;
- resource/model conflict com Sodium/Iris/resource packs;
- generator/world interaction produzir resultado inesperado em worldgen modificado;
- recipe cache stale após `/reload`;
- automação contar output duas vezes em quests/perks por observar etapa visual e settlement real.

## 14. Matriz de testes obrigatória
- [ ] Dedicated server boot com Garnished 2.1.9.2 + Create 6.0.10.
- [ ] Cadeias de peanut/cashew/hazelnut/almond com os processors reais do pack.
- [ ] Crush/separate/process/garnish/plate sem duplicação de outputs.
- [ ] Nut Milk remove efeitos exatamente conforme runtime da versão física.
- [ ] Salt e decorative bricks possuem recipes/tags coerentes após `/reload`.
- [ ] Cobblestone generation Blackstone/water não conflita com outros generators/fluid mods.
- [ ] Ponder/recipe display corresponde ao recipe efetivo.
- [ ] Compat com Farmer's Delight/My Nether's Delight quando presentes.
- [ ] Compat Create Aquatic Ambitions/Create Dragons Plus apenas se esses consumers estiverem ativos.
- [ ] Multiplayer em máquina compartilhada sem dupe/loss.
- [ ] Save/restart preserva inventories/state relacionados.
- [ ] Resource stack real do pack sem model/render regressions.

## 15. Evidências e limites
**Source primário pinado:** `DakotaPride/create-garnished-forge`, commit `bd5180a5...`, exatamente 2.1.9.2.
**Distribuição oficial:** release 2.1.9.2 para NeoForge 1.21.1.
**Documentação pública:** usada apenas para superfícies funcionais sustentadas; o wiki é declarado desatualizado e não foi usado para contagens/IDs.
**Limite:** não foi obtida enumeração confiável integral dos registries do commit 2.1.9.2 nesta etapa; portanto números e IDs não comprovados foram deliberadamente omitidos.
**Nenhum teste de runtime foi executado nesta catalogação.**
