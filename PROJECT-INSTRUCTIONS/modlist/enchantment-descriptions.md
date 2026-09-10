# Enchantment Descriptions — 21.1.11

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db816a9358eda67114597d  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** Enchantment Descriptions
- **Arquivo JAR:** `enchdesc-neoforge-1.21.1-21.1.11.jar`
- **Versão 1.21.1:** `21.1.11`
- **Categoria:** QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/enchantment-descriptions
- **Função:** QoL de tooltip que adiciona descrições explicativas aos encantamentos sem alterar seus efeitos, níveis, compatibilidade ou mecânica de enchanting.
- **Dependências:** Bookshelf 21.1.81 e PrickleMC 21.1.11 estão fisicamente presentes no pack e compõem o stack Darkhax atual. Runtime Enchantment Descriptions 21.1.11 para NeoForge 1.21.1.
- **Compatibilidade/Riscos:** Baixo risco gameplay; riscos são tooltip/localization. Descrições modded dependem da chave `enchantment.%MOD_ID%.%ENCH_ID%.desc`; ausência/duplicação de keys ou outro mod que injete descrições pode produzir texto ausente/duplicado. Há report upstream não verificado de interação `require_keybind` com Apotheosis/Apothic em 1.21.1; o pack usa Apothic Enchanting 1.6.2 e deve smoke-testar essa superfície.
- **Sobreposição:** Pode sobrepor apenas outras soluções de descrição/tooltip de encantamentos. Apothic Enchanting continua authority da mecânica de enchanting; Enchantment Descriptions só apresenta texto.
- **Observações:** Runtime 21.1.11, release NeoForge 1.21.1 de 20/08/2026. O projeto oficial confirma suporte a enchantments modded desde que a localization key esperada exista. O mod é apresentação: não modifica enchanting math, applicability ou effect execution.
- **Procedência:** modlist(4).txt — fonte física canônica atual, 595 mods top-level; JAR `enchdesc-neoforge-1.21.1-21.1.11.jar`, mod id `enchdesc`, runtime `21.1.11`, hash físico `4d453df785ac21e0e7389cba949f4fa460c3e267`. A mesma autoridade física confirma Bookshelf 21.1.81, PrickleMC 21.1.11 e Apothic Enchanting 1.6.2; CurseForge/source oficial permanecem evidência complementar.
- **Histórico da decisão:**
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Enchantment Descriptions 21.1.11; tooltip/localization contract, modded-enchantment support, Bookshelf/Prickle stack, lifecycle, Apothic risk and tests cataloged.
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `enchdesc-neoforge-1.21.1-21.1.11.jar` · mod id `enchdesc` · versão `21.1.11` · NeoForge 1.21.1.

## 1. Papel no modpack

Enchantment Descriptions é uma camada de **informação/tooltip**. Quando um item encantado é inspecionado, o mod acrescenta uma descrição breve do efeito do encantamento para reduzir dependência de wiki externa.

## 2. Authority / ownership

- **Minecraft/mod do encantamento:** efeito, nível, applicability, conflicts e execução gameplay.
- **Enchantment Descriptions:** texto explicativo exibido no tooltip.
- **Localization/resource packs:** conteúdo textual da descrição.

A descrição nunca deve ser tratada como authority mecânica se divergir do código do enchantment provider.

## 3. Runtime 21.1.11

A modlist física confirma 21.1.11; CurseForge lista `enchdesc-neoforge-1.21.1-21.1.11.jar` como release NeoForge 1.21.1 publicada em 20/08/2026.

## 4. Dependências físicas

O pack contém:
- **Bookshelf 21.1.81**;
- **PrickleMC 21.1.11**.

Ambas pertencem ao stack Darkhax utilizado por esta linha. Remover libraries isoladamente deve ser tratado como alteração de dependency graph, não simples limpeza visual.

## 5. Suporte a enchantments modded

O source oficial é explícito: enchantments modded podem ter descrição. O contrato de localization esperado é:

`enchantment.%MOD_ID%.%ENCH_ID%.desc`

Se o provider do encantamento ou um resource pack fornecer essa chave, o mod pode mostrar a descrição sem hardcode específico para cada addon.

## 6. Localization / PT-BR

Como as descrições vêm do mapa de localization, a qualidade em PT-BR depende das entradas fornecidas pelo mod/resource pack. Missing translation pode resultar em ausência de descrição ou fallback, sem significar que o enchantment esteja quebrado.

Correções de texto devem ocorrer na localization correspondente, não alterando a lógica do enchantment.

## 7. Tooltips

A modificação é aplicada durante a construção do tooltip de itens encantados. Outro mod de tooltip pode reordenar, esconder ou duplicar linhas; o resultado deve ser tratado como composição de UI.

Não utilizar tooltip render event como gatilho para executar efeito de encantamento.

## 8. Enchantments sem descrição

A ausência de texto não comprova ausência do encantamento nem incompatibilidade. Pode significar apenas que a chave `.desc` esperada não foi fornecida para aquele namespace/ID.

Para um enchantment modded crítico, validar o ResourceLocation real e a localization correspondente.

## 9. Apothic Enchanting — risco local

O pack instala **Apothic Enchanting 1.6.2**. Existe report upstream 1.21.1 não verificado em versão anterior do Enchantment Descriptions envolvendo a opção `require_keybind` junto a Apotheosis/Apothic.

Isso é **risco de regressão**, não bug confirmado na combinação física atual 21.1.11 + Apothic 1.6.2. Deve ser smoke-testado antes de qualquer workaround.

## 10. Client / Server

A funcionalidade visível é client-side tooltip. A publicação distribui o mod para ambiente Client & Server, mas o servidor não deve usar a descrição como dado gameplay. A verdade do enchantment permanece no registry/provider.

## 11. Lifecycle

Validar:
- login/reconnect;
- abrir inventory/container;
- enchanted book e item encantado;
- resource reload;
- troca de idioma;
- datapack/resource-pack update;
- alteração de enchantments via anvil/table;
- update de enchantment provider.

## 12. Multiplayer

Cada cliente pode renderizar traduções/tooltip conforme seus resources, mas o enchantment real é sincronizado pelo item/server state. Divergência textual não pode mudar level/effect no servidor.

## 13. Riscos

1. localization `.desc` ausente;
2. descrição desatualizada em relação ao enchantment provider;
3. dupla descrição por outro tooltip mod;
4. ordem/formatting conflitante;
5. resource pack sobrescrever texto incorretamente;
6. keybind de descrição não funcionar com outro tooltip stack;
7. idioma sem tradução;
8. Bookshelf/Prickle version mismatch;
9. assumir tooltip como especificação mecânica;
10. incompatibilidade de UI ser confundida com enchanting quebrado.

## 14. Matriz de testes

1. Boot com Bookshelf 21.1.81 + Prickle 21.1.11.
2. Tooltip de enchantments vanilla em item e enchanted book.
3. Enchantment modded com chave `.desc` conhecida.
4. Enchantment modded sem descrição e verificar fallback seguro.
5. PT-BR e inglês.
6. Resource reload.
7. Apothic Enchanting 1.6.2 com tool/weapon/armor encantados.
8. Se `require_keybind` estiver habilitado, testar mostrar/ocultar descrição.
9. Anvil/enchanting table e item atualizado em runtime.
10. Multiplayer com cliente reconectando e tooltips consistentes.

**Esta catalogação não afirma que esses testes foram executados.**

## 15. Evidências

- modlist física canônica: JAR/mod id/version + Bookshelf/Prickle/Apothic presentes;
- CurseForge oficial: release NeoForge 1.21.1 21.1.11;
- source oficial Darkhax: tooltip descriptions, suporte modded e formato `enchantment.%MOD_ID%.%ENCH_ID%.desc`;
- issue upstream 1.21.1 de Apothic/`require_keybind` tratada apenas como risco não verificado.

> **Boundary canônico:** Enchantment Descriptions controla **texto explicativo**. O enchantment provider continua sendo a única authority da mecânica.
