# Excalibur | Ice and Fire: Community Edition | Tabla's Dragon Retextures

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `1.21.1_G's_Dragons_Retextured_I&F-CE.v6.zip`
- **Versão 1.21.1:** v6
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `1.21.1_G's_Dragons_Retextured_I&F-CE.v6.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma Ice and Fire Community Edition `2.1.2`; essa é a authority do provider-alvo.

## Propriedades do banco

- **Mod:** Excalibur | Ice and Fire: Community Edition | Tabla's Dragon Retextures
- **Arquivo JAR:** `1.21.1_G's_Dragons_Retextured_I&F-CE.v6.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** v6
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat, Mobs
- **Função:** Retextura visual dos Fire, Ice e Lightning dragons de Ice and Fire: Community Edition para a estética Excalibur/Tabla, sem alterar entidades ou combate.
- **Dependências:** Ice And Fire Community Edition 2.1.2 + stack visual Excalibur. Conteúdo client-side; AI, stages, attacks, taming, loot e persistence permanecem no mod.
- **Sobreposição:** True Dovah altera os mesmos dragões e conteúdo relacionado; nos paths coincidentes, o pack de maior prioridade vence. Conflito é visual, não funcional.
- **Compatibilidade/Riscos:** ZIP histórico v6; a release externa atual para a linha Community Edition 1.21.1 é v7 (03/10/2026). Não foi comparado o conteúdo do ZIP físico com v6/v7. True Dovah altera assets dos mesmos dragões; a prioridade define a sobrescrita visual.
- **Observações:** Último ZIP registrado pela captura histórica: `1.21.1_G's_Dragons_Retextured_I&F-CE.v6.zip`. O autor publicou v6 CE e posteriormente v7 CE; a instalação atual não foi reobtida e os changelogs granulares dessa linha ainda requerem verificação.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física Ice and Fire CE 2.1.2 + projeto oficial Tabla's Dragon Retextures/Community Edition.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-ice-and-fire-community-edition-tablas
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; I&F CE 2.1.2, v6 física, Fire/Ice/Lightning dragons, overlap True Dovah, load order, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado:** `1.21.1_G's_Dragons_Retextured_I&F-CE.v6.zip`, marcador físico `v6`, para Ice and Fire: Community Edition. O alvo físico atual é Ice And Fire Community Edition `2.1.2`.

## 1. Papel e authority
Este pack retexturiza os dragões de Ice and Fire: Community Edition para a estética Excalibur. **Ice and Fire CE** continua authority de spawn, AI, stages, sex/variants, breath attacks, damage, taming, riding, loot e persistence.

## 2. Cobertura confirmada
O projeto declara cobertura de **todos os Fire, Ice e Lightning dragons** para combinar com a estética do pack. As releases anteriores documentam mudanças por cor/sexo/stage, incluindo eyes, wings, horns e outros detalhes; isso confirma granularidade por variante, não mudança funcional.

## 3. Boundary de versão v6
O último arquivo físico **historicamente registrado** é `v6` (captura de 08/09/2026); o ZIP atual não foi relido. O projeto oficial CE (CurseForge **1553651**) publicou publicamente uma build `v6` para Minecraft 1.21.1 em **01/09/2026**, seguida da **`Gs ReDraco_CE_1.21.1_v7.zip` em 03/10/2026**. A existência dessas versões públicas elimina a hipótese de que o número v6, por si só, não possuía origem upstream. Ainda não foram comparados hashes nem a árvore de assets do ZIP histórico com o distribuído.

## 4. Stack físico
Target: Ice and Fire CE `2.1.2`. O pack visual deve ser testado em Fire/Ice/Lightning dragons, stages e variants reais dessa build para detectar path drift.

## 5. Conflito direto com True Dovah
`True Dovah.zip` também altera **todos os dragões e conteúdo relacionado a dragon scales** de Ice and Fire. Nos assets de dragão sobrepostos, o resource pack de maior prioridade vence. Isso é conflito visual direto/alternativa estética, não conflito de gameplay.

## 6. Load order e reload
Para obter a estética Tabla/Excalibur, este pack precisa ficar acima da base visual e acima de qualquer retexture concorrente que se deseje substituir. Resource reload deve apenas trocar textures/models e nunca entity state.

## 7. Riscos
1. ZIP histórico v6 sem SHA, `pack.mcmeta` ou árvore de assets relidos nesta rodada; nova v7 externa exige regressão visual.
2. True Dovah sobrescrever dragões por prioridade.
3. Paths/variants novos de Ice and Fire CE 2.1.2 sem asset correspondente.
4. Model/texture mismatch por stage/sex/color.
5. Resource reload deixar cache stale.

## 8. Matriz de testes
- [ ] Fire dragons: múltiplas cores, stages e sexos.
- [ ] Ice dragons: múltiplas cores, stages e sexos.
- [ ] Lightning dragons: múltiplas cores, stages e sexos.
- [ ] Eggs/young dragons onde houver assets.
- [ ] Comparar prioridade Tabla vs True Dovah.
- [ ] Resource reload sem missing textures/models.
- [ ] Confirmar que pack on/off não altera AI, damage ou drops.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma o propósito e a cobertura Fire/Ice/Lightning do projeto CE. A captura histórica **registra** `v6` (sem ZIP/hash atuais reobtidos). A página atual comprova publicações CE `v6` e `v7`; sem diff dos arquivos exatos, mudanças individuais entre essas versões não são atribuídas ao ZIP.

> Boundary canônico: **Ice and Fire CE controla os dragões como entidades; Tabla's Dragon Retextures controla apenas sua aparência, sujeita à prioridade contra True Dovah**.

## 10. Retificação da linha CE: v6 pública e v7 disponível — 10/10/2026

**Correção factual da checagem anterior:** a interpretação de que a última release pública de **Excalibur | Ice and Fire: Community Edition | Tabla's Dragon Retextures** seria `v5` estava baseada em uma listagem/indexação desatualizada. A consulta atual da **página oficial do mesmo projeto, CurseForge 1553651**, confirma:

| Data de publicação | Versão oficial CE | Evidência |
|---|---|---|
| 14/07/2026 | `v5` | Release pública anterior da linha Community Edition |
| 01/09/2026 | `1.21.1_G's_Dragons_Retextured_I&F-CE.v6.zip` | Página oficial identifica publicação `v6` para Minecraft 1.21.1 |
| 03/10/2026 | `Gs ReDraco_CE_1.21.1_v7.zip` | **Latest Release** da página oficial para Minecraft **1.21.1**, arquivo CurseForge **9042500** |

A `v7` é **nova opção publicada**, não versão instalada comprovada. A última captura do perfil, datada de 08/09/2026, registra `1.21.1_G's_Dragons_Retextured_I&F-CE.v6.zip`, mas seu **ZIP, hash, `pack.mcmeta`, status de ativação e prioridade atual não foram inspecionados** nesta rodada. Preservam-se o filename deste dossiê `v6` e a versão do inventário histórico. Não renomear para v7 nem afirmar que o ZIP foi substituído.

### Notas técnicas e limites de evidência

O projeto CE do mesmo autor informa cobertura visual de dragões Fire, Ice e Lightning, com variações por cor, estágio e sexo. O autor descreve **emissivos a partir da v5** cujas luzes podem desaparecer quando dragões piscam/dormem. A família de versões `v7` usa o novo prefixo `Gs ReDraco_CE`, mas não se pode afirmar identidade de hashes/assets com o projeto base não-CE: cada release precisa ser tratada conforme seu próprio pacote. A nota granular do arquivo CE `9042500` não foi recuperada diretamente, portanto nenhum delta específico de v6→v7 é atribuído sem fonte individual.

### Matriz de regressão pré-promoção

- [ ] Recuperar o ZIP atual, calcular SHA-256 e conferir `pack.mcmeta`, `pack_format`, paths e ativação.
- [ ] Comparar o ZIP histórico v6 com o release oficial CE v7, verificando renomes de assets, cobertura de dragon entities, eggs, scales, model overrides, emissives e palettes.
- [ ] Verificar precedência com Excalibur principal e **True Dovah**, registrando a ordem efetiva de resource packs.
- [ ] Inspecionar ao menos Fire/Ice/Lightning dragons, múltiplos estágios, variantes sexuais, olhos piscando/sono, eggs e itens cobertos, com **EMF/ETF/shaders** ligados e desligados.
- [ ] Executar troca de packs, F3+T, save/relogin, alteração de dimensão e teste de missing textures no Minecraft 1.21.1.

**Fontes oficiais:** https://www.curseforge.com/minecraft/texture-packs/excalibur-ice-and-fire-community-edition-tablas ; https://www.curseforge.com/minecraft/texture-packs/excalibur-ice-and-fire-community-edition-tablas/files/9042500

**Status:** versão pública **v7**, último ZIP histórico documentado **v6**; nenhuma instalação física, comparação de hashes ou teste visual foi executado. A alegada inexistência pública da v6 **foi corrigida**.
