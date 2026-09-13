# Nutritional Balance

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81849ef0f2ae87e60aa4
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `nutritionalbalance-1.21.1-7.0.3.jar`, mod id `nutritionalbalance`, runtime `1.21.1-7.0.3` confirmado fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma `modlist.txt física canônica atual de 10/09/2026`. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Nutritional Balance 7.0.3 está confirmado.

## Propriedades do banco

- **Mod:** Nutritional Balance
- **Arquivo JAR:** `nutritionalbalance-1.21.1-7.0.3.jar`
- **Versão 1.21.1:** 1.21.1-7.0.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Comida, RPG
- **Função:** Sistema persistente de dieta/nutrientes baseado em grupos e item tags, com inferência por ingredients/recipes e buffs/debuffs por equilíbrio alimentar.
- **Dependências:** NeoForge 1.21.1. Não foi confirmada hard dependency externa adicional na release auditada.
- **Sobreposição:** Nenhum segundo provider nutricional top-level equivalente foi comprovado na modlist atual. Interage com o stack culinário/RPG, mas não substitui hunger/saturation vanilla.
- **Compatibilidade/Riscos:** Provider nutricional Client & Server. Riscos: recipe-graph cost em modpack grande, cobertura de tags, recipes alternativos, reload/cache, persistência e stacking com atributos/buffs. 7.0.3 reestrutura recipe traversal para mitigar hangs de primeiro login.
- **Observações:** Runtime 1.21.1-7.0.3, file ID 7980604, Release 25/04/2026. 7.0.3 rearchitecta nutrient building/recipe traversal para grandes modpacks.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial 7.0.3 + documentação pública de tags/datapacks/recipe traversal.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/nutritional-balance
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Nutritional Balance 7.0.3 reconstruído: item tags, recipe traversal/inference, datapacks, estado nutricional, performance de primeiro login, integração culinária/RPG, lifecycle, riscos e testes.
- **Histórico da decisão:** 2026-08-22 — marcado Tirar/Removido para evitar segunda camada nutricional paralela ao TFC; essa decisão pertencia à arquitetura TFC então vigente. 2026-08-26 — decisão anterior invalidada pela remoção do TFC e pela presença confirmada de Nutritional Balance 7.0.3 na modlist atual; restaurado para Instalado / Sem decisão, preservando o histórico em vez de reescrevê-lo.
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `nutritionalbalance-1.21.1-7.0.3.jar`, mod id `nutritionalbalance`, versão literal `1.21.1-7.0.3`, NeoForge 1.21.1. Nutritional Balance é o provider nutricional top-level atualmente instalado. A release 7.0.3 reestrutura o cálculo de nutrientes e a travessia de recipes para eliminar travamentos longos no primeiro login de modpacks grandes — exatamente uma superfície crítica nesta instância.

## 1. Identidade e papel
- **Mod:** Nutritional Balance.
- **JAR físico:** `nutritionalbalance-1.21.1-7.0.3.jar`.
- **Mod id:** `nutritionalbalance`.
- **Runtime:** `1.21.1-7.0.3`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Projeto:** dannydjdk / Nutritional Balance.
- **Ambiente:** Client & Server.
- **Licença:** GPLv3.
- **Papel:** sistema de dieta/nutrientes que recompensa alimentação equilibrada e pode aplicar benefícios ou penalidades conforme o perfil nutricional do jogador.
- **Decisão:** Sem decisão.

## 2. Modelo nutricional
O projeto associa alimentos a nutrientes/grupos nutricionais e calcula o perfil do jogador a partir do consumo. A documentação pública descreve o objetivo como fazer uma dieta equilibrada resultar em um personagem mais forte/rápido, enquanto uma dieta ruim pode enfraquecer ou reduzir desempenho.

O sistema não deve ser confundido com hunger/saturation vanilla. Hunger continua sendo recurso vanilla; Nutritional Balance acrescenta uma camada persistente de qualidade/variedade da dieta.

## 3. Definição por item tags
A documentação permite definir nutrientes por **item tags**. Isso é particularmente importante em modpacks grandes porque novos alimentos podem ser integrados por datapack sem alterar o código do mod.

Consequências:
- tags são contrato de integração;
- um alimento sem tag adequada pode ficar nutricionalmente neutro/incorreto;
- tags excessivamente amplas podem classificar itens indevidos;
- `/reload` precisa reconstruir as definições sem duplicação ou cache stale.

## 4. Travessia de recipes
O mod não exige que todo prato final seja marcado manualmente. A documentação descreve travessia dos ingredientes/recipes para inferir nutrientes de alimentos compostos a partir dos ingredientes-base.

Em um pack com Farmer's Delight e muitos addons, isso permite que pratos derivados herdem composição nutricional, mas também cria uma superfície de custo computacional e de ambiguidade quando recipes são complexos, condicionais, alternativos ou adicionados por scripts.

## 5. Release 7.0.3 — mudança crítica de performance
O changelog exato da 7.0.3 informa que a construção de nutrientes e a travessia de recipes foram **rearquitetadas para melhorar significativamente a eficiência e eliminar longos travamentos no primeiro login em modpacks muito grandes**, tratando a issue #48.

Para esta modlist grande, isso é um regression gate direto:
- cold login de jogador novo;
- primeiro login após alteração de datapack/recipes;
- `/reload` seguido de login;
- server start com grande recipe graph.

A ficha não presume que a correção elimina todo custo; mede-se o runtime real.

## 6. Datapacks e configuração server-side
A documentação permite customização por datapacks e configuração de servidor. Também indica que customizações podem ser recarregadas em jogo.

Ownership:
- Nutritional Balance é authority do estado nutricional;
- mods de comida são authority dos seus itens/recipes;
- datapacks/KubeJS do pack apenas adaptam a classificação sem criar uma segunda fonte paralela de nutrição.

## 7. Integração com o stack culinário
O pack contém Farmer's Delight e numerosos addons, portanto o sistema deve reconhecer corretamente receitas compostas, refeições servidas e ingredientes modded.

Pontos de auditoria:
- pratos com múltiplos ingredientes;
- recipes alternativos por tag;
- cooking/smelting/cutting;
- alimentos adicionados por datapack/KubeJS;
- containers/feasts que geram porções.

Não se deve afirmar cobertura total sem testar itens reais da instância.

## 8. Buffs, debuffs e balanceamento RPG
Benefícios e penalidades nutricionais entram no mesmo domínio de atributos/efeitos em que o pack possui sistemas RPG. Isso cria risco de stacking com:
- buffs de alimentos;
- AttributeFix e caps de atributos;
- perks/skills;
- efeitos de combate ou magia.

Qualquer bônus deve ser considerado parte da curva de progressão global e não um detalhe puramente culinário.

## 9. Client/server e sincronização
O estado nutricional que influencia gameplay precisa ser **server-authoritative**. O cliente pode apresentar barras/tooltips/feedback, mas não deve ser a fonte de verdade do valor.

Testes de multiplayer precisam observar:
- consumo processado uma vez;
- reconnect mostrando o mesmo estado;
- morte/respawn conforme regra real da build;
- dois clientes não divergindo sobre buffs ativos.

## 10. Persistência
O valor de nutrição é estado de longo prazo do jogador. Eventos críticos:
- save/restart;
- logout/reconnect;
- morte/respawn;
- mudança de dimensão;
- atualização de tags/recipes;
- migração entre versões.

Esta auditoria não inventa o formato de capability/data attachment sem pin de source/JAR específico.

## 11. Histórico arquitetural do pack
Em 22/08/2026 o mod foi marcado para remoção porque existia uma segunda camada nutricional associada à arquitetura TFC então vigente. Em 26/08/2026 essa premissa deixou de valer após a remoção do TFC e a presença física de Nutritional Balance foi reconfirmada.

Esse histórico deve permanecer: a decisão antiga não era erro factual, mas dependia de uma arquitetura que mudou. No estado atual não foi comprovado outro provider nutricional top-level equivalente.

## 12. Riscos
1. **Recipe-graph cost:** modpack muito grande pode expor caminhos caros mesmo após a otimização 7.0.3.
2. **Tag coverage:** alimentos sem tags/ingredientes reconhecidos podem receber perfil incorreto.
3. **Recipe ambiguity:** recipes por tags ou múltiplos caminhos podem produzir composição inesperada.
4. **Reload consistency:** mudanças de datapack precisam invalidar caches corretamente.
5. **Attribute stacking:** buffs/debuffs podem somar com sistemas RPG.
6. **Food-addon churn:** atualização de um addon pode adicionar alimentos sem classificação adequada.
7. **Migration/persistence:** estado do player deve sobreviver a restart/update sem reset silencioso.
8. **UI versus authority:** qualquer HUD é apenas apresentação do estado server-side.

## 13. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Nutritional Balance 7.0.3.
- [ ] Primeiro login de jogador novo não produz hang prolongado no recipe graph atual.
- [ ] Primeiro login após `/reload` permanece estável.
- [ ] Alimentos vanilla representativos recebem nutrientes esperados.
- [ ] Amostra de Farmer's Delight e addons herda nutrientes de ingredientes corretamente.
- [ ] Recipe com alternativas/tags não gera composição impossível ou duplicada.
- [ ] Alimento adicionado por KubeJS/datapack entra no sistema após reload.
- [ ] Buff/debuff nutricional é aplicado exatamente uma vez e respeita caps/atributos globais.
- [ ] Logout/reconnect e restart preservam estado nutricional.
- [ ] Morte/respawn segue a regra real da build sem reset/dupe indevido.
- [ ] Dois clientes observam o mesmo estado funcional do jogador.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- Modlist física: `nutritionalbalance-1.21.1-7.0.3.jar`, mod id/runtime exatos.
- CurseForge oficial: project 448222, file ID 7980604, Release NeoForge 1.21.1 de 25/04/2026, Client & Server, GPLv3.
- Changelog exato 7.0.3: rearchitecture de nutrient building/recipe traversal para desempenho e correção dos hangs de primeiro login em packs grandes.
- Documentação pública: nutrientes por item tags, customização por datapack/server config e inferência por ingredientes/recipes.
- **Limite:** fórmulas exatas, valores default, formato de persistência e cada integração de alimento não foram inventados sem config/JAR runtime auditados.
