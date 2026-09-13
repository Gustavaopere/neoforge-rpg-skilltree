# Excalibur | Supplementaries Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db812395f4f2d0ea14f8a7
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur_Supplementaries_1.0_1.21.1.zip`
- **Versão 1.21.1:** 1.0
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur_Supplementaries_1.0_1.21.1.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A modlist física acessível de 08/09/2026 confirma `supplementaries-1.21.1-3.9.8-neoforge.jar`, mod id `supplementaries`, runtime `1.21.1-3.9.8`.
- O upstream publica build 1.0 para Minecraft 1.21.1; uma 1.1 posterior pertence à linha 1.20.1 e não é tratada como atualização aplicável ao runtime 1.21.1.
- A documentação pública mostra exemplos de cobertura, mas não um manifesto completo. A exportação preserva isso como escopo parcial comprovado, sem inferir 100% do namespace.

## Propriedades do banco

- **Mod:** Excalibur | Supplementaries Support
- **Arquivo JAR:** `Excalibur_Supplementaries_1.0_1.21.1.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat
- **Função:** Compatibility resource pack 16x que retexturiza assets de Supplementaries para combinar com Excalibur, incluindo decoração e assets animados quando presentes.
- **Dependências:** Uso visual pretendido: Excalibur base + Supplementaries. Stack físico atual: Excalibur V26.1_01 e Supplementaries 1.21.1-3.9.8. Não é dependência de gameplay/servidor.
- **Sobreposição:** Sobrepõe assets de Supplementaries; outros retextures do mesmo namespace podem vencer conforme prioridade. Operacionalmente deve ficar acima do Excalibur para seus overrides serem visíveis.
- **Compatibilidade/Riscos:** Cobertura integral não é declarada. Riscos de fallback visual com Supplementaries 3.9.8, conflito de asset paths, animações/metadata e prioridade incorreta de resource packs.
- **Observações:** Arquivo instalado `Excalibur_Supplementaries_1.0_1.21.1.zip`. Gallery oficial mostra signs/signposts, candles, flowerpots, miniature ship, globe, flax bales e chalice; não há manifesto público completo.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial da build 1.0 para Minecraft 1.21.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-supplementaries-support
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; escopo Supplementaries 3.9.8, cobertura confirmada, load order, lifecycle de resource reload, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur_Supplementaries_1.0_1.21.1.zip`, versão `1.0`, Release para Minecraft 1.21.1. É um compatibility pack não oficial para alinhar visualmente Supplementaries ao Excalibur.

## 1. Papel e authority
Excalibur | Supplementaries Support altera somente assets visuais do mod **Supplementaries**. Supplementaries continua authority de blocos, itens, interações e lógica; Excalibur continua o pack visual base.
O support pack não registra conteúdo, não altera recipes e não deve ser tratado como dependência de servidor.

## 2. Cobertura confirmada
A página oficial descreve o projeto como pack de compatibilidade para fazer as texturas de Supplementaries combinarem com Excalibur. O gallery oficial mostra, entre outros, **signs/signposts, candles e skull candles, flowerpots, miniature ship, globe, flax bales e chalice**.
A documentação pública não fornece manifesto completo nem afirma explicitamente cobertura total da build 1.0 para 1.21.1; portanto não extrapolar esses exemplos para 100% do mod.

## 3. Stack físico atual
A modlist mantém `supplementaries-1.21.1-3.9.8-neoforge.jar`, runtime `1.21.1-3.9.8`. O arquivo visual instalado é a build 1.0 para 1.21.1.
O projeto teve atualização posterior 1.1 para a linha 1.20.1, mas a página oficial ainda lista **1.0 como release de 1.21.1**. Não há evidência de que o pack do usuário esteja desatualizado para Minecraft 1.21.1.

## 4. Load order
Como compatibility pack, precisa ter prioridade suficiente para substituir os assets correspondentes de Supplementaries e manter coerência com Excalibur. O upstream não publicou nesta evidência uma ordem mais específica; usar **acima do Excalibur** como regra operacional de override visual.

## 5. Client e resource reload
É visual/client-side. Troca de prioridade ou ativação dispara resource reload; não deve alterar state de blocos, inventários ou interação de Supplementaries.
Assets animados, quando presentes, precisam recarregar sem warnings de metadata/frames.

## 6. Sobreposição
Pode colidir com outros retextures de Supplementaries, incluindo packs que alterem as mesmas texturas, models ou animações. O asset de maior prioridade vence.
Não classificar outro pack como redundante sem comparar caminhos concretos.

## 7. Riscos
1. Cobertura parcial deixar mistura Excalibur/default.
2. Supplementaries 3.9.8 possuir assets posteriores à build visual 1.0.
3. Load order incorreto impedir o tema Excalibur.
4. Collision com outro support pack de Supplementaries.
5. Assets animados falharem por metadata incompatível.
6. Resource reload produzir missing texture/model por rename de asset upstream.

## 8. Matriz de testes
- [ ] Pack ativo acima de Excalibur.
- [ ] Conferir signs/signposts.
- [ ] Conferir candles/skull candles e animações quando aplicáveis.
- [ ] Conferir flowerpots, miniature ship, globe, flax bales e chalice.
- [ ] Abrir amostra ampla do conteúdo Supplementaries 3.9.8 para procurar fallbacks visuais.
- [ ] Resource reload sem missing textures/models.
- [ ] Verificar conflitos visuais com outros packs ativos que toquem Supplementaries.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
- captura CurseForge do perfil em 08/09/2026: build instalada 1.0 para 1.21.1;
- modlist física: Supplementaries `1.21.1-3.9.8`;
- CurseForge oficial: support pack 16x/medieval/animated, build 1.0 para 1.21.1 e gallery com exemplos de assets.

Manifesto integral do ZIP não foi auditado; completude permanece fail-closed.

> Boundary canônico: **Supplementaries controla o comportamento; o support pack apenas substitui recursos visuais resolvidos pelo cliente**.
