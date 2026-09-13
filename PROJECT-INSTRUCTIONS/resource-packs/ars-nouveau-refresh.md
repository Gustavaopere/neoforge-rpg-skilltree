# Ars Nouveau Refresh

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81d4932bdbe523022c51
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack
- **Arquivo:** `Ars Nouveau Refresh 1.2.0.zip`
- **Versão 1.21.1:** 1.2.0
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra uma **captura física da pasta Resource Packs do perfil em 08/09/2026** como evidência de instalação de `Ars Nouveau Refresh 1.2.0.zip`.
- A Biblioteca acessível nesta execução não contém essa captura física nem o `.zip`; portanto, não foi possível revalidar diretamente o arquivo do resource pack nesta etapa.
- A modlist física acessível de 08/09/2026 é JAR-centric e **não constitui prova de presença do resource pack**, mas confirma o provider-alvo `ars_nouveau-1.21.1-5.13.1.jar`, Ars Nouveau `5.13.1`.
- Assim, a presença/versão do resource pack é preservada conforme o dossiê e sua procedência declarada, sem convertê-la em evidência física independente inexistente nesta execução.

## Propriedades do banco

- **Mod:** Ars Nouveau Refresh
- **Arquivo JAR:** `Ars Nouveau Refresh 1.2.0.zip`
- **Tipo de conteúdo:** Resource Pack
- **Versão 1.21.1:** 1.2.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Magia
- **Função:** Refresh visual para Ars Nouveau que moderniza glyphs, plaques, essences, charms/familiars, tablets e outros itens, sem alterar o sistema mágico.
- **Dependências:** Ars Nouveau 5.13.1 como alvo visual. Não altera Source, mana, spell registry, glyph behavior, recipes ou networking.
- **Sobreposição:** Compete com outros resource packs que alterem glyphs/items de Ars Nouveau. Addons Ars usam namespaces/assets próprios e não ficam cobertos automaticamente.
- **Compatibilidade/Riscos:** Riscos de icons novos/renomeados em Ars 5.13.1, overlap com outros Ars retextures e addons sem cobertura. Roadmap de mobs/weapons/armor/addons não deve ser tratado como implementado.
- **Observações:** Arquivo instalado `Ars Nouveau Refresh 1.2.0.zip`. Upstream confirma 90+ glyphs alterados, essence icons, charm/familiar icons e tablet icons; mobs/weapons/armor/addons permanecem roadmap.
- **Procedência:** CurseForge oficial Ars Nouveau Refresh 1.2.0 para 1.21.1 + captura Resource Packs do perfil em 08/09/2026 + modlist física Ars Nouveau 5.13.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/ars-nouveau-refresh
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — v1.2.0, Ars 5.13.1, 90+ glyphs, essences/charms/tablets, roadmap separado, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Ars Nouveau Refresh 1.2.0.zip`, versão `1.2.0`, Release oficial para Minecraft 1.21.1. O alvo físico atual é Ars Nouveau `5.13.1`.

## 1. Papel e authority
Ars Nouveau Refresh moderniza a apresentação de glyphs e itens do Ars Nouveau. Ars Nouveau continua authority de Source, mana, spell registry, glyph semantics, casting, recipes, entities e networking.

## 2. Cobertura confirmada
O upstream documenta **90+ glyphs alterados**, além de novos visuais para **essences**, **charms/familiars**, **tablets**, plaques e outros itens. O objetivo é aproximar ícones antigos da linguagem visual moderna do Minecraft.

## 3. Roadmap não implementado
A página lista como roadmap atualizar mobs, weapons, armor e itens de addons. Esses itens são planos futuros e **não devem ser registrados como cobertura atual** da v1.2.0.

## 4. Stack físico
O alvo é Ars Nouveau `5.13.1`. Assets adicionados ou renomeados após a criação da v1.2.0 podem cair no visual original; addons Ars não são automaticamente cobertos.

## 5. Load order e reload
Outros Ars retextures podem disputar os mesmos icons/textures por prioridade. Resource reload deve trocar somente assets, sem alterar spellbook, mana, Source ou recipes.

## 6. Riscos
1. Glyph/item novo do Ars 5.13.1 sem refresh.
2. Outro Ars resource pack sobrescrever icons.
3. Addon usar namespace próprio sem cobertura.
4. UI/item atlas apontar para asset renomeado.
5. Resource reload manter sprite stale.

## 7. Matriz de testes
- [ ] Amostrar glyphs das principais escolas.
- [ ] Conferir essence icons.
- [ ] Conferir charm e familiar icons.
- [ ] Conferir tablet icons e plaques.
- [ ] Identificar itens recentes sem refresh.
- [ ] Resource reload sem missing texture.
- [ ] Confirmar que pack on/off não altera spells ou mana.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma a v1.2.0 para 1.21.1 e as superfícies publicadas. Itens de roadmap permanecem explicitamente fora da cobertura confirmada.

> Boundary canônico: **Ars Nouveau controla magia e gameplay; Ars Nouveau Refresh controla somente os assets visuais efetivamente fornecidos**.
