# Create Enchantment Industry Plus

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: `modlist(4).txt`, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Create Enchantment Industry Plus
- **Arquivo JAR:** `create_enchantment_industry_plus-1.1.1-1.21.1.jar` — último artefato catalogado; ausente da modlist física atual
- **Versão 1.21.1:** `1.1.1` — última versão catalogada; não instalada atualmente
- **Categoria:** Tecnologia; Magia; Compat
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Removido
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-enchantment-industry-plus
- **Função:** Extensão pequena de Create: Enchantment Industry para converter leather/ink/experience em Empty Ink Sacs, Ink Sacs e Glow Ink Sacs usando processamento Create e recipes adicionais.
- **Dependências:** Histórico da versão 1.1.1: Create + Create: Enchantment Industry. No pack físico atual, Create 6.0.10 e Create: Enchantment Industry 2.5.3b continuam presentes, mas o addon Create Enchantment Industry Plus não possui entrada top-level.
- **Compatibilidade/Riscos:** Sobreposição concentrada em recipes de ink/black dye/glow ink. A 1.1.1 muda recipes de ink para black dye, adiciona conversão ink sac→glow ink sac e grinding recipe. Risco principal é recipe duplication/stale datapack com outras bridges do Enchantment Industry.
- **Sobreposição:** Complementa Create: Enchantment Industry; não o substitui. Qualquer outro addon/datapack que converta ink sacs, black dye, experience ou glow ink deve ser comparado por recipe ID/input/output para evitar rotas duplicadas.
- **Observações:** Último artefato confirmado historicamente: `create_enchantment_industry_plus-1.1.1-1.21.1.jar`, runtime 1.1.1. A modlist física atual não contém esse addon; os fluxos press/fill/drain/spout abaixo permanecem documentação histórica da versão catalogada, não funcionalidade afirmada como ativa no pack atual.
- **Procedência:** Última confirmação física histórica em setembro/2026 para `create_enchantment_industry_plus-1.1.1-1.21.1.jar` + CurseForge/Modrinth oficiais da 1.1.1. Reauditoria da modlist física atual em 29/09/2026, com 587 entradas top-level incluindo o modloader, encontrou zero entrada top-level do addon; presença atual = removido.
- **Histórico da decisão:** Sem decisão curatorial formal. Em 08/09/2026, a versão 1.1.1 estava instalada e foi reconstruída ao padrão técnico. Na reauditoria física de 29/09/2026, o addon não aparece mais entre as 587 entradas top-level e passa a ser registrado como Removido.
- **Atualização/Status:** REAUDITADO EM 29/09/2026 — o addon Create Enchantment Industry Plus não está presente na modlist física atual. A versão 1.1.1 e seus recipes ficam preservados como histórico do último artefato catalogado; Create 6.0.10 e Create: Enchantment Industry 2.5.3b permanecem no stack.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> 🖋️ Última versão física historicamente confirmada: `create_enchantment_industry_plus-1.1.1-1.21.1.jar`, runtime `1.1.1`, NeoForge 1.21.1. **O addon não está presente na modlist física atual**; o restante deste dossiê descreve a última versão catalogada.

## Estado físico atual — removido
A modlist física atual, com 587 entradas top-level incluindo o modloader, não contém `create_enchantment_industry_plus-1.1.1-1.21.1.jar` nem outro top-level do Create Enchantment Industry Plus. Create `6.0.10` e Create: Enchantment Industry `2.5.3b` permanecem instalados; somente este addon está ausente.

## 1. Papel e authority
O addon adiciona recipes e itens intermediários para integrar ink sacs ao processing do Create/Enchantment Industry. Create controla as máquinas/process types; Enchantment Industry controla seu sistema de experiência/ink; este addon controla apenas suas conversões adicionais.

## 2. Empty Ink Sac
A documentação oficial confirma produção de **Empty Ink Sac** ao pressionar Leather. Recipe Manager é authority da quantidade/ingredientes finais da build; automação externa não deve duplicar a transformação por listener paralelo.

## 3. Fill com ink
Empty Ink Sac pode ser preenchido com Ink para produzir Ink Sac. Fluid consumption e item output devem ser liquidados uma única vez pelo recipe real.

## 4. Drain de Ink Sac
Ink Sac pode ser drenado para recuperar Empty Ink Sac + Ink. Isso cria uma rota reversível e exige conservation: o ciclo fill↔drain não pode gerar fluido líquido ou items extras.

## 5. Glow Ink Sac
O projeto documenta uso de Spout + experience para transformar Ink Sac em Glow Ink Sac. A 1.1.1 também registra explicitamente a adição desse recipe. Experience/ink settlement continua no server/common.

## 6. Mudança para black dye — 1.1.1
O changelog informa `Changed recipes from ink to black dye`. A frase pública não detalha todos os recipe IDs afetados; portanto a ficha não atribui a mudança a um caminho específico sem recipe JSON. O runtime/datapack 1.1.1 é authority.

## 7. Grinding recipe — 1.1.1
A release acrescenta um grinding recipe, mas o changelog público não identifica input/output. O dossiê registra apenas a existência do delta e exige JEI/JAR para enumerá-lo antes de qualquer override.

## 8. Relação com Enchantment Industry
O addon depende da infraestrutura do Enchantment Industry e não replica o sistema inteiro. Remover o base quebra o propósito desta extensão; remover esta extensão deve apenas retirar seus recipes/conteúdo específico.

## 9. Recipe reload
Recipes são data-driven. Datapack/KubeJS pode substituir ou remover rotas, mas precisa evitar ciclos com ganho líquido entre fill, drain, grinding e outras integrações de ink.

## 10. Client/server
Recipe acceptance, fluid/experience consumption e inventories são server/common. JEI/tooltips/models são client-facing. O viewer não decide se uma conversão existe no servidor.

## 11. Lifecycle
Validar startup, recipe/datapack reload, server restart, fluid tank/container behavior e update isolado de Create ou Enchantment Industry.

## 12. Riscos
1. Fill/drain gerar ink extra em ciclo.
2. Experience ser debitada duas vezes ou não debitada.
3. Black dye recipe antigo coexistir com o novo.
4. Grinding recipe duplicar outra rota.
5. Recipe viewer ficar stale após reload.
6. Base Enchantment Industry atualizar e alterar item/fluid esperado.

## 13. Matriz de testes
1. Dedicated server boot.
2. Leather→Empty Ink Sac.
3. Empty Ink Sac + ink→Ink Sac.
4. Drain Ink Sac e conferir conservation.
5. Spout + experience→Glow Ink Sac.
6. Identificar o grinding recipe real da 1.1.1 via JEI/runtime.
7. Recipe reload sem duplicação.
8. Ciclo fill/drain repetido sem ganho líquido.
9. Multiplayer com dois jogadores usando a cadeia simultaneamente.

## 14. Evidência
- modlist física histórica de 08/09/2026: 1.1.1;
- modlist física atual de 29/09/2026: addon ausente entre 587 entradas top-level incluindo o modloader;
- CurseForge oficial: Empty Ink Sac, fill/drain e Spout + experience;
- changelog 1.1.1: ink→black dye recipe changes, glow ink recipe e grinding recipe;
- Modrinth confirma build 1.1.1 Client & Server.

> 🔒 Boundary canônico: **o addon define recipes de ink-sac; Create/Enchantment Industry executam e contabilizam processing/fluids/experience**. Conversões reversíveis devem conservar recursos.
