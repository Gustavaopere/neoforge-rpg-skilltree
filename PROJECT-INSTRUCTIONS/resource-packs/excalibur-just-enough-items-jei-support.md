# Excalibur | Just Enough Items (JEI) Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81fe9712db9c31641154
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur Jei Support 1.4.zip`
- **Versão 1.21.1:** 1.4
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur Jei Support 1.4.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A modlist física de 08/09/2026 confirma `jei-1.21.1-neoforge-19.53.0.426.jar`, mod id `jei`, runtime `19.53.0.426`.
- Um guia descritivo anterior ainda cita JEI `19.44.0.406`; essa referência é stale perante a modlist física e não é usada como autoridade de versão nesta exportação.
- O projeto também menciona addons como Just Enough Resources e Just Enough Professions, mas essas menções não são convertidas em presença física por inferência.

## Propriedades do banco

- **Mod:** Excalibur | Just Enough Items (JEI) Support
- **Arquivo JAR:** `Excalibur Jei Support 1.4.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.4
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat, QoL
- **Função:** Support pack visual para JEI que redesenha GUIs e elementos do ecossistema JEI na estética Excalibur sem alterar recipes, ingredient indexing ou lookup.
- **Dependências:** Excalibur base + Just Enough Items 19.53.0.426. O projeto também referencia addons JEI, mas somente os fisicamente presentes devem ser tratados como ativos.
- **Sobreposição:** Conflito potencial com Mandala's GUI, dark-mode compats e outros packs que alterem JEI GUI/sprites. Prioridade de resource packs decide a apresentação.
- **Compatibilidade/Riscos:** A v1.4 foi atualizada para a versão beta do JEI. Pode colidir com Mandala's GUI e outros GUI packs nos mesmos sprites/elements; addons JEI citados pelo projeto não são presumidos presentes sem confirmação física.
- **Observações:** Arquivo instalado `Excalibur Jei Support 1.4.zip`, versão 1.4 para 1.21.1. Changelog oficial: atualização para JEI beta. Upstream descreve redesign de todas as GUIs e elementos do pacote suportado.
- **Procedência:** CurseForge oficial Excalibur | JEI Support v1.4 + captura Resource Packs do perfil em 08/09/2026 + modlist física JEI 19.53.0.426.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-just-enough-items-jei-support
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — v1.4, JEI 19.53.0.426, GUI/elements, beta boundary, load order, overlap, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur Jei Support 1.4.zip`, versão `1.4`, com suporte explícito a Minecraft 1.21.1. O alvo físico atual é JEI `19.53.0.426`.

## 1. Papel e authority
Excalibur | Just Enough Items (JEI) Support redesenha a apresentação do JEI e de superfícies compatíveis para o estilo Excalibur. **JEI** continua authority de ingredient indexing, recipe lookup, categories, transfer helpers e demais funções.

## 2. Cobertura publicada
O upstream descreve o suporte como redesign de **GUIs e elementos** do pacote JEI suportado. O projeto também referencia integrações como Just Enough Resources e Just Enough Professions; esses addons não são considerados presentes sem confirmação física própria.

## 3. Build 1.4 e boundary beta
A release `1.4` é específica para 1.21.1. O changelog oficial informa atualização para a **versão beta do JEI**, portanto a compatibilidade deve ser validada contra o JEI físico `19.53.0.426`.

## 4. Load order e GUI stack
O pack deve ficar acima do Excalibur base. Mandala's GUI e seus compats podem tocar sprites, backgrounds ou widgets equivalentes; nos assets coincidentes, a prioridade de resource pack define o resultado.

## 5. Client e reload
É conteúdo client-side. Resource reload deve atualizar apenas GUI/sprites; recipe registry, ingredient data e funcionalidades de busca do JEI não podem mudar.

## 6. Riscos
1. Drift entre a beta alvo da v1.4 e JEI 19.53.0.426.
2. Outro GUI pack sobrescrever widgets/sprites.
3. Addon JEI não instalado ser confundido como coberto em runtime.
4. GUI scale causar clipping/contraste inadequado.
5. Resource reload manter sprite stale.

## 7. Matriz de testes
- [ ] Abrir ingredient list e recipe view do JEI.
- [ ] Testar várias categories e recipe layouts.
- [ ] Conferir search box, navigation e elementos redesenhados.
- [ ] Testar diferentes GUI scales.
- [ ] Comparar prioridade com Mandala's GUI.
- [ ] Resource reload sem missing sprites.
- [ ] Confirmar que pack on/off não altera recipes ou indexing.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma v1.4 para 1.21.1, o redesign das GUIs/elements e a atualização para JEI beta. A modlist física confirma JEI 19.53.0.426. A ficha não presume presença de addons JEI apenas porque são citados pelo projeto.

> Boundary canônico: **JEI controla receitas e indexação; este support pack controla somente sua apresentação visual**.
