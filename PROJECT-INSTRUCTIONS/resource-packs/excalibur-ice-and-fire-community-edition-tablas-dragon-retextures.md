# Excalibur | Ice and Fire: Community Edition | Tabla's Dragon Retextures

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db811aa843d0e686b06627
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
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat, Mobs
- **Função:** Retextura visual dos Fire, Ice e Lightning dragons de Ice and Fire: Community Edition para a estética Excalibur/Tabla, sem alterar entidades ou combate.
- **Dependências:** Ice And Fire Community Edition 2.1.2 + stack visual Excalibur. Conteúdo client-side; AI, stages, attacks, taming, loot e persistence permanecem no mod.
- **Sobreposição:** True Dovah altera os mesmos dragões e conteúdo relacionado; nos paths coincidentes, o pack de maior prioridade vence. Conflito é visual, não funcional.
- **Compatibilidade/Riscos:** Arquivo físico é v6, mas a página CE indexada ainda pode expor v5; delta v5→v6 permanece fail-closed. Conflito visual direto com True Dovah nos assets de dragão; prioridade decide o resultado.
- **Observações:** Arquivo instalado `1.21.1_G's_Dragons_Retextured_I&F-CE.v6.zip`. Preservar `v6` como autoridade física; não inventar changelog CE v6 quando não publicado de forma suficiente.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física Ice and Fire CE 2.1.2 + projeto oficial Tabla's Dragon Retextures/Community Edition.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-ice-and-fire-community-edition-tablas
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; I&F CE 2.1.2, v6 física, Fire/Ice/Lightning dragons, overlap True Dovah, load order, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `1.21.1_G's_Dragons_Retextured_I&F-CE.v6.zip`, marcador físico `v6`, para Ice and Fire: Community Edition. O alvo físico atual é Ice And Fire Community Edition `2.1.2`.

## 1. Papel e authority
Este pack retexturiza os dragões de Ice and Fire: Community Edition para a estética Excalibur. **Ice and Fire CE** continua authority de spawn, AI, stages, sex/variants, breath attacks, damage, taming, riding, loot e persistence.

## 2. Cobertura confirmada
O projeto declara cobertura de **todos os Fire, Ice e Lightning dragons** para combinar com a estética do pack. As releases anteriores documentam mudanças por cor/sexo/stage, incluindo eyes, wings, horns e outros detalhes; isso confirma granularidade por variante, não mudança funcional.

## 3. Boundary de versão v6
O arquivo físico instalado é `v6`. A página pública indexada do projeto CE ainda pode expor `v5` como main file, enquanto a família Tabla's Dragon Retextures recebeu atualizações v6 em setembro de 2026. Portanto `v6` é preservado como autoridade física, mas o delta CE `v5→v6` permanece fail-closed onde não houver changelog oficial específico acessível.

## 4. Stack físico
Target: Ice and Fire CE `2.1.2`. O pack visual deve ser testado em Fire/Ice/Lightning dragons, stages e variants reais dessa build para detectar path drift.

## 5. Conflito direto com True Dovah
`True Dovah.zip` também altera **todos os dragões e conteúdo relacionado a dragon scales** de Ice and Fire. Nos assets de dragão sobrepostos, o resource pack de maior prioridade vence. Isso é conflito visual direto/alternativa estética, não conflito de gameplay.

## 6. Load order e reload
Para obter a estética Tabla/Excalibur, este pack precisa ficar acima da base visual e acima de qualquer retexture concorrente que se deseje substituir. Resource reload deve apenas trocar textures/models e nunca entity state.

## 7. Riscos
1. `v6` física sem changelog CE integral acessível.
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
CurseForge oficial confirma o propósito e a cobertura Fire/Ice/Lightning do projeto CE. O arquivo físico confirma `v6`; sem changelog CE v6 suficiente, diferenças exatas da release não são inventadas.

> Boundary canônico: **Ice and Fire CE controla os dragões como entidades; Tabla's Dragon Retextures controla apenas sua aparência, sujeita à prioridade contra True Dovah**.
