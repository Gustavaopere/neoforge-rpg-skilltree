# Excalibur | Iron's Spells 'N Spellbooks support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81aeb62dff7c21e0b6d8
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur_Irons_Spells_N_Spellbooks-1.3.0.zip`
- **Versão 1.21.1:** 1.3.0
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur_Irons_Spells_N_Spellbooks-1.3.0.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A modlist física de 08/09/2026 confirma `irons_spellbooks-1.21.1-3.16.3.jar`, mod id `irons_spellbooks`, runtime `1.21.1-3.16.3`; o dossiê preserva a forma curta `3.16.3` para o target.
- O upstream do support v1.3.0 declara explicitamente estado WIP e muitas texturas faltantes. A presença do pack não é tratada como cobertura integral do namespace Iron's.
- Outras camadas visuais de Iron's no perfil — runas, armas 3D e armor overhauls — podem tocar subconjuntos coincidentes; a prioridade de resource packs decide apenas a apresentação desses paths.

## Propriedades do banco

- **Mod:** Excalibur | Iron's Spells 'N Spellbooks support
- **Arquivo JAR:** `Excalibur_Irons_Spells_N_Spellbooks-1.3.0.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.3.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat, Magia
- **Função:** Support pack visual que redesenha assets de Iron's Spells 'n Spellbooks para o estilo Excalibur, com cobertura ainda parcial/WIP.
- **Dependências:** Excalibur base + Iron's Spells 'n Spellbooks 3.16.3. Resource pack visual; spells, schools, mana, cooldowns, item stats e networking permanecem no mod.
- **Sobreposição:** Compete com Malumified Iron's Runes, Iron's 3D Weapons, armor overhauls e outros support/retexture packs do mesmo namespace. Deve ficar acima do Excalibur base.
- **Compatibilidade/Riscos:** WIP explícito: o upstream informa que muitas texturas ainda faltam. Pode colidir com Malumified Iron's Runes, 3D Weapons, armor overhauls e outros retextures de Iron's; prioridade resolve assets coincidentes.
- **Observações:** Arquivo instalado `Excalibur_Irons_Spells_N_Spellbooks-1.3.0.zip`, release 1.3.0 para 1.21.1. O próprio projeto declara WIP e muitas texturas faltantes; não registrar cobertura total.
- **Procedência:** CurseForge oficial Excalibur | Iron's Spells 'N Spellbooks support v1.3.0 + captura Resource Packs do perfil em 08/09/2026 + modlist física Iron's Spells 3.16.3.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-irons-spells-n-spellbooks-support
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — v1.3.0, Iron's 3.16.3, WIP explícito, load order, overlaps, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur_Irons_Spells_N_Spellbooks-1.3.0.zip`, versão `1.3.0`, Release para Minecraft 1.21.1. O alvo físico atual é Iron's Spells 'n Spellbooks `3.16.3`.

## 1. Papel e authority
Excalibur | Iron's Spells 'N Spellbooks support redesenha assets de Iron's para a estética Excalibur. **Iron's Spells** continua authority de spell registry, schools, mana, cooldowns, casting, items, stats, recipes, entities e networking.

## 2. Estado de cobertura — WIP
O upstream declara explicitamente que o projeto ainda é **WIP** e que **muitas texturas estão faltando**. Portanto a v1.3.0 deve ser catalogada como cobertura parcial; a existência do support pack não autoriza afirmar que todo o mod foi retexturizado.

## 3. Stack físico
O target atual é Iron's Spells `3.16.3`. Assets adicionados/renomeados nessa linha podem permanecer com o visual original quando não houver override correspondente na v1.3.0.

## 4. Overlap com outros packs de Iron's
O perfil possui outras camadas visuais específicas, incluindo Malumified Iron's Runes e o pack de armas 3D; há também outros overhauls de Iron's na lista especial. Cada pack pode tocar subconjuntos distintos ou coincidentes do namespace. Nos paths coincidentes, a prioridade decide.

## 5. Load order e reload
O support pack deve prevalecer sobre o Excalibur base. A posição relativa contra packs específicos de runas, armas e armaduras deve ser escolhida conforme o visual desejado. Resource reload/relog deve afetar apenas assets.

## 6. Riscos
1. Cobertura incompleta por definição WIP.
2. Iron's 3.16.3 possuir asset novo sem override.
3. Outro Iron's retexture sobrescrever textures/models coincidentes.
4. Mistura visual entre subsets do mod por load order.
5. Resource reload manter sprite/model stale.

## 7. Matriz de testes
- [ ] Amostrar spellbooks, scrolls, weapons e armor representativos.
- [ ] Conferir rune assets junto ao Malumified Iron's Runes.
- [ ] Conferir armas cobertas pelo 3D Weapons pack.
- [ ] Identificar assets ainda vanilla/originais por falta de cobertura.
- [ ] Testar prioridade relativa entre os packs de Iron's.
- [ ] Resource reload sem missing textures/models.
- [ ] Confirmar que pack on/off não altera spells, mana ou item stats.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma a v1.3.0 para 1.21.1 e declara explicitamente o estado WIP com muitas texturas faltantes. A modlist física confirma o target Iron's 3.16.3. O catálogo preserva essa limitação em vez de inferir cobertura total.

> Boundary canônico: **Iron's Spells controla magia e gameplay; este support pack controla apenas os assets que já possui, com cobertura oficialmente incompleta**.
