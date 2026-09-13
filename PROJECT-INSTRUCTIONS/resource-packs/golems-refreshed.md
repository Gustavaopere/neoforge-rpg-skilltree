# Golems Refreshed

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81e79fc8e50c746be8e0
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack
- **Arquivo:** `golems-refreshed-v2.1.zip`
- **Versão 1.21.1:** 2.1
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `golems-refreshed-v2.1.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma `entity_model_features-3.3.5-1.21-neoforge.jar`, mod id `entity_model_features`, runtime `3.3.5`, infraestrutura recomendada pelo autor para CEM.

## Propriedades do banco

- **Mod:** Golems Refreshed
- **Arquivo JAR:** `golems-refreshed-v2.1.zip`
- **Tipo de conteúdo:** Resource Pack
- **Versão 1.21.1:** 2.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Mobs
- **Função:** Overhaul visual do Iron Golem com 26 variants por biome/name tag, usando custom entity models e texturas sem alterar comportamento da entidade.
- **Dependências:** Entity Model Features (EMF) é recomendado pelo autor para CEM; stack físico contém EMF 3.3.5. Fresh Animations exige addon separado para compatibilidade.
- **Sobreposição:** Compete diretamente com outros packs que alterem Iron Golem/Snow Golem models e textures. Para Fresh Animations, usar o addon de compatibilidade separado presente no stack.
- **Compatibilidade/Riscos:** Riscos de overlap com Fresh Animations sem o compat dedicado, outros golem CEM packs, rules de biome/name tag e cache EMF. Variants são visuais, não entidades novas.
- **Observações:** Arquivo instalado `golems-refreshed-v2.1.zip`, versão 2.1 com suporte a Minecraft 1.21.1. Upstream confirma 26 variants do Iron Golem e recomenda EMF.
- **Procedência:** CurseForge oficial Golems Refreshed v2.1 + captura Resource Packs do perfil em 08/09/2026 + modlist física EMF.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/golems-refreshed
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — v2.1, 26 Iron Golem variants, EMF, biome/name-tag rules, boundary Fresh Animations, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `golems-refreshed-v2.1.zip`, versão `2.1`, compatível com Minecraft 1.21.1. O autor recomenda Entity Model Features para os custom entity models.

## 1. Papel e authority
Golems Refreshed é um overhaul visual do Iron Golem. Minecraft continua authority de AI, targeting, health, damage, spawn, village behavior, drops e persistence.

## 2. Cobertura confirmada
O upstream declara **26 variants novas de Iron Golem**, acionadas por diferentes biomas ou name tags. Essas variantes mudam model/texture/apresentação; não devem ser catalogadas como novos entity types.

## 3. Infraestrutura CEM
O autor recomenda **Entity Model Features (EMF)** em vez de OptiFine. A modlist física confirma EMF `3.3.5`, que deve ser validado junto às rules/model definitions do pack.

## 4. Boundary com Fresh Animations
Compatibilidade com Fresh Animations é fornecida por um resource pack separado, `Golems Refreshed + Fresh Animations v2.1`, já presente e catalogado. A simples ativação das duas bases sem o compat não deve ser tratada como integração garantida.

## 5. Load order e reload
O pack e seu compat Fresh Animations precisam de prioridade coerente. Resource reload/relog deve reconstruir CEM e variant rules sem alterar estado da entidade.

## 6. Riscos
1. Outro golem CEM sobrescrever models/textures.
2. Compat Fresh Animations ficar abaixo das bases.
3. Variant por biome/name tag falhar e cair em fallback.
4. EMF cache stale após reload.
5. Model incompatível produzir golem invisível/deformado.

## 7. Matriz de testes
- [ ] Amostrar Iron Golems em múltiplos biomas.
- [ ] Testar variants por name tag documentadas.
- [ ] Confirmar EMF `3.3.5` ativo.
- [ ] Testar com o compat Fresh Animations habilitado.
- [ ] Resource reload/relog sem model quebrado.
- [ ] Confirmar que pack on/off não altera AI, stats ou drops.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma v2.1, suporte a 1.21.1, 26 variants e recomendação de EMF. O catálogo mantém a separação entre aparência e comportamento.

> Boundary canônico: **Golems Refreshed controla apresentação e variants visuais; Minecraft controla o golem como entidade**.
