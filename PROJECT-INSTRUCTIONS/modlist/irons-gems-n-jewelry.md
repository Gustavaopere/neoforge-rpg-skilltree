# Iron's Gems 'n Jewelry

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db811f86dfd4a8b50b2e85
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Propriedades do banco

- **Mod:** Iron's Gems 'n Jewelry
- **Arquivo JAR:** `irons_jewelry-1.21.1-2.0.2.jar`
- **Versão 1.21.1:** 1.21.1-2.0.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** RPG
- **Função:** Sistema modular e data-driven de jewelcrafting: combina patterns e materiais de diferentes qualidades para produzir joias equipáveis com atributos, apoiado por Jewelcrafting Station, gemstones, Jeweler villager e trades/progressão próprios.
- **Dependências:** Obrigatórias oficiais: Iron's Lib, Atlas API e Curios API. No pack físico: Iron's Lib 1.21.1-2.1.0, Atlas API 1.21.1-1.2.0 e Curios 9.5.1+1.21.1. Source 2.0.2 também referencia integrações de desenvolvimento, mas elas não são promovidas a hard dependencies sem declaração oficial.
- **Sobreposição:** Possui sistema próprio de joias/patterns/materials; pode coexistir com Curios, Artifacts e gems/affixes Apothic, mas atributos acumulados devem ser balanceados. Não confundir suas gems com as gems temáticas de Iron's Apothic.
- **Compatibilidade/Riscos:** Sistema data-driven de joalheria/atributos. Riscos: material/tag/pattern registry drift, modifier stacking com Curios/Apothic, trade/economy inflation, invalid jewelry após data update, damage-event recursion/crash, custom price-handler interactions e save/data compatibility.
- **Observações:** Source exato branch 1.21.1 declara `mod_version=1.21.1-2.0.2`. A linha 2.0.2 migra/atualiza para a Iron's Lib 2.0 API e adiciona safeguards na cadeia de damage events; 2.0.0 substituiu pseudo material types por tags reais de materiais.
- **Procedência:** modlist.txt física atual + CurseForge oficial Iron's Gems 'n Jewelry 2.0.2 file 8365016 + source oficial iron431/irons-jewelry branch 1.21.1 com versão exata + changelog/wiki oficial da linha 2.0.x.
- **Fonte:** https://github.com/iron431/irons-jewelry/tree/1.21.1
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Iron's Gems 'n Jewelry 1.21.1-2.0.2 source-pinned; jewelcrafting station, patterns/material quality, 7 gemstones, Jeweler/trades, data registries/tags, Iron's Lib 2.0 API migration, damage-chain safeguard, lifecycle/economia, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `irons_jewelry-1.21.1-2.0.2.jar`, mod id `irons_jewelry`, versão `1.21.1-2.0.2`. O source oficial `iron431/irons-jewelry:1.21.1` declara exatamente essa versão; esta ficha é source-pinned.

## 1. Papel e authority
Iron's Gems 'n Jewelry adiciona um sistema modular de jewelcrafting com patterns, materiais, qualidade, atributos, estação própria e economia de villager. O mod é authority de suas joias, material definitions, pattern definitions, trades e atributos que registra. Curios é infraestrutura de slots/equipamento; Atlas API e Iron's Lib fornecem APIs auxiliares.

## 2. Dependências obrigatórias
O projeto declara Iron's Lib, Atlas API e Curios API como requisitos. No pack físico estão Iron's Lib 1.21.1-2.1.0, Atlas API 1.21.1-1.2.0 e Curios 9.5.1+1.21.1. O source 2.0.2 usa versões de desenvolvimento de outras integrações, mas elas não são tratadas aqui como hard dependencies sem declaração oficial.

## 3. Jewelcrafting Station
A **Jewelcrafting Station** é o ponto central de fabricação. O resultado depende de pattern e materiais compatíveis, em vez de uma lista fixa de recipes tradicionais. Admission, consumo e output devem ser server-authoritative e exactly-once; automação externa não deve retirar output antes do settlement real.

## 4. Patterns
Patterns definem a forma/tipo de joia e a categoria de benefício. Alguns são disponíveis por padrão; patterns avançados podem ser obtidos por loot/trading através de **Artisan Scrolls**. Desbloqueio/progressão deve ser tratado como state real do sistema, não como simples presença do item gráfico.

## 5. Materiais e qualidade
Os materiais determinam o bônus específico e sua qualidade influencia a força. A linha 2.0.0 tornou esse sistema mais explicitamente data-driven: `MaterialDefinition` passou a usar ingredient opcional e o sistema abandonou pseudo material types em favor de tags reais.

## 6. Tags materiais 2.0.x
A documentação da API 2.0 registra tags padrão como `irons_jewelry:metal`, `irons_jewelry:gem` e `irons_jewelry:gem_or_metal`. `PartDefinition` passa a filtrar materiais por holder set/tag. Datapacks/addons devem inserir materiais nas tags corretas, não reintroduzir a classificação antiga removida.

## 7. Gemstones
O projeto publica **7 gemstone items obtíveis**. Essa contagem é escopo documental da release, não licença para inventar nomes/atributos ausentes das fontes pinadas. Gemstone item, material definition e efeito final devem permanecer distinguíveis.

## 8. Jeweler villager e economia
Há uma nova profissão **Jeweler**. O villager compra/vende materiais, joias acabadas e pode fornecer acesso a patterns avançados/segredos via trading. Isso afeta a economia do pack: disponibilidade de esmeraldas, rerolls e outros mods de villagers podem acelerar progressão de jewelcrafting.

## 9. Custom price handling
A linha 2.0.1 substituiu o uso de `minecraft:additional_trade_cost` por um price handler próprio. Portanto mods que alteram preços/trades devem ser testados contra o mecanismo atual e não assumir que o campo vanilla representa o preço final da joalheria.

## 10. Data-driven registries e addons
O projeto enfatiza registries/data para materials, parts/patterns e conteúdo extensível por datapacks/addons. `/reload` pode alterar definitions válidas para novos crafts, mas joias já persistidas precisam permanecer interpretáveis. Remover material/pattern usado por um item existente é risco de save/data compatibility.

## 11. Migração para Iron's Lib 2.0 — 2.0.2
O changelog da 2.0.2 registra atualização para a **Iron's Lib 2.0 API**. O pack usa Iron's Lib 2.1.0, pertencente à linha posterior compatível por intenção, mas isso ainda é um version gate operacional: futuras mudanças de library devem ser smoke-tested com criação/equipamento de joias e data load.

## 12. Damage-event safeguard — 2.0.2
A mesma release adiciona safeguards na cadeia de damage events para prevenir crash potencial. Qualquer joia/atributo que reage a dano precisa evitar recursão/reentrada. Outros mods RPG que também interceptam damage podem revelar novamente esse edge case.

## 13. Relação com Apothic/Curios
O pack possui Iron's Apothic e outros sistemas de acessórios/atributos. Iron's Jewelry mantém seu próprio pipeline de patterns/materials; Curios apenas hospeda slots quando aplicável. Gems do Jewelry não são as mesmas gems/affixes do Apothic. O risco real é **stacking de atributos**, não duplicate mod identity.

## 14. Client / server
Craft result, material validation, attributes, equip state e trades são server-authoritative. Cliente apresenta JEI/GUI/models/tooltips. Um tooltip calculado localmente não deve ser usado por integração externa como proof do modifier server-side.

## 15. Lifecycle e persistência
Validar datapack load/reload, Jewelcrafting Station craft, pattern unlock, material/tag changes, equip/unequip, Curios slot changes, villager profession/trades, death/respawn, reconnect e server restart. Items existentes precisam manter componentes/state sem modifier duplication.

## 16. Riscos técnicos
- material/pattern/tag removido após update;
- item persistido se tornar inválido após datapack change;
- attribute modifier acumular em equip/relog;
- stacking excessivo com Apothic/Artifacts/outros Curios;
- trade economy trivializar Artisan Scroll progression;
- custom price handler conflitar com outro trade modifier;
- damage-event recursion/crash;
- Iron's Lib/Atlas API drift;
- automação da station duplicar consume/output;
- client/server data registry mismatch.

## 17. Matriz de testes obrigatória
- [ ] Dedicated server + cliente iniciam com Jewelry 2.0.2, Iron's Lib 2.1.0, Atlas API 1.2.0 e Curios 9.5.1.
- [ ] Material/pattern registries carregam sem codec/tag errors.
- [ ] Jewelcrafting Station consome inputs e cria um único output válido.
- [ ] Patterns padrão e avançados seguem progressão real.
- [ ] Artisan Scroll/trades não duplicam unlock/reward.
- [ ] Jeweler mantém profissão e trades após restart.
- [ ] Equip/unequip/relog não acumula modifiers.
- [ ] Damage-event jewelry não entra em recursão com stack RPG atual.
- [ ] `/reload` com data válida não corrompe joias existentes.
- [ ] Material/tag inválido falha de forma diagnosticável e sem dupe.
- [ ] Custom trade price compõe corretamente com outros modificadores de villager.

## 18. Evidências e limites
- **Modlist física:** JAR/mod id/version e hard dependencies atuais.
- **Source oficial:** branch 1.21.1 com `mod_version=1.21.1-2.0.2` e dependency versions de desenvolvimento.
- **CurseForge oficial:** release 2.0.2 e escopo do sistema de jewelcrafting.
- **Wiki/changelog:** migração de material tags/API 2.0.x, custom price handler e safeguards de damage chain.
- **Limite:** combinações publicitadas como 10.000+ são espaço combinatório dinâmico, não registry count fixo; valores de atributos individuais não foram inventados.
- **Runtime:** nenhum teste acima foi executado nesta catalogação.
