# Golems Refreshed + Fresh Animations

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81ceae19e02655100811
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `golems-refreshed-fa-v2.1.zip`
- **Versão 1.21.1:** 2.1
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `golems-refreshed-fa-v2.1.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- Logs físicos de 08/09 confirmam Entity Model Features e Entity Texture Features ativos no runtime; a authority de versão exata continua o snapshot físico do pack usado pelo dossiê, que registra EMF `3.3.5` para esta integração.

## Propriedades do banco

- **Mod:** Golems Refreshed + Fresh Animations
- **Arquivo JAR:** `golems-refreshed-fa-v2.1.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 2.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat, Mobs
- **Função:** Compatibility resource pack que torna Golems Refreshed compatível com Fresh Animations, preservando variants/modelos visuais de golems no pipeline de animação.
- **Dependências:** Golems Refreshed + Fresh Animations. O autor recomenda Entity Model Features (EMF); stack físico contém EMF 3.3.5.
- **Sobreposição:** Sobrepõe definitions de golem necessárias à compatibilidade Fresh Animations. Outros golem CEM/model packs podem competir por prioridade; Minecraft continua owner do comportamento.
- **Compatibilidade/Riscos:** Riscos de CEM/model overlap, prioridade incorreta entre Golems Refreshed e Fresh Animations, cache de EMF e variants sem regra aplicável. v2.1 adiciona suporte visual ao Snow Golem.
- **Observações:** Arquivo instalado `golems-refreshed-fa-v2.1.zip`; v2.1 suporta explicitamente Minecraft 1.21.1. Changelog da linha v2.1 registra Snow Golem.
- **Procedência:** CurseForge oficial Golems Refreshed + Fresh Animations v2.1 + captura Resource Packs do perfil em 08/09/2026 + modlist/logs físicos EMF.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/golems-refreshed-fresh-animations
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — v2.1, Golems Refreshed + Fresh Animations, EMF, Snow Golem, load order, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `golems-refreshed-fa-v2.1.zip`, versão `2.1`, com suporte explícito a Minecraft 1.21.1. É o addon de compatibilidade entre Golems Refreshed e Fresh Animations.

## 1. Papel e authority
O pack adapta models/resources do Golems Refreshed para coexistirem com as animações do Fresh Animations. Minecraft continua authority de Iron/Snow Golem AI, target selection, health, damage, spawn e drops.

## 2. Stack visual confirmado
A cadeia relevante é **Golems Refreshed → compat v2.1 → Fresh Animations**, com **EMF** recomendado pelo autor para custom entity models. O stack físico registra EMF no runtime.

## 3. Cobertura e v2.1
Golems Refreshed trabalha com variantes visuais de golems; a linha v2.1 do compat registra **Snow Golem** como adição. Isso é cobertura visual/animação, não criação de nova mecânica de golem.

## 4. Load order e reload
O compat precisa ter prioridade suficiente para que suas definições combinadas prevaleçam sobre as bases conflitantes. Resource reload/relog deve reconstruir CEM/rules sem modificar entity state.

## 5. Sobreposição
Outros packs que alterem Iron Golem, Snow Golem ou suas regras de EMF podem disputar os mesmos models/textures. A resolução é visual por prioridade de resource pack.

## 6. Riscos
1. Compat abaixo das bases perder seus overrides.
2. Model/rule mismatch entre Golems Refreshed e Fresh Animations.
3. Snow Golem ou variant específico cair em fallback.
4. EMF cache stale após reload.
5. Outro CEM de golem produzir model invisível/deformado.

## 7. Matriz de testes
- [ ] Iron Golem vanilla com animações ativas.
- [ ] Variantes de Iron Golem por biome/name tag disponíveis.
- [ ] Snow Golem da linha v2.1.
- [ ] Confirmar EMF ativo no cliente do perfil.
- [ ] Resource reload/relog sem missing model/texture.
- [ ] Confirmar que pack on/off não altera AI, health, damage ou drops.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma v2.1, suporte a 1.21.1, papel de compatibilidade com Fresh Animations e recomendação de EMF. Logs físicos de 08/09 também confirmam EMF/ETF carregados no runtime. O catálogo não atribui gameplay ao resource pack.

> Boundary canônico: **Golems Refreshed/Fresh Animations controlam apresentação; Minecraft controla a entidade e seu comportamento**.
