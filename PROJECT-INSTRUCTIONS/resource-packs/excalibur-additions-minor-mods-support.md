# Excalibur | Additions: Minor Mods Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db81e49451f64f1ffe4853
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur Additions_Minor Mod Support 1.6.zip`
- **Versão 1.21.1:** 1.6
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur Additions_Minor Mod Support 1.6.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/nome da build é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- Há drift de metadata: a matriz oficial atual associa a release 1.6 à linha 26.1, enquanto a última build explicitamente rotulada 1.21.1 é 1.2. A build física 1.6 permanece fail-closed quanto à compatibilidade integral com 1.21.1 até QA.

## Propriedades do banco

- **Mod:** Excalibur | Additions: Minor Mods Support
- **Arquivo JAR:** `Excalibur Additions_Minor Mod Support 1.6.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.6
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat
- **Função:** Coleção 16x de suportes visuais menores para vários mods, unificando assets compatíveis com a estética Excalibur sem alterar gameplay.
- **Dependências:** Excalibur base + apenas os mods alvo efetivamente presentes. No perfil atual há, entre outros, Advancement Plaques 1.6.8, Stylish Effects 21.1.3 e YUNG's Cave Biomes 3.1.1.
- **Sobreposição:** Pode disputar assets com support packs específicos dos mesmos mods. Deve ficar acima do Excalibur base; packs mais específicos podem intencionalmente ficar acima dele.
- **Compatibilidade/Riscos:** A release instalada chama-se 1.6, mas a matriz oficial atual rotula 1.6 para 26.1 e a última build explicitamente 1.21.1 como 1.2. Compatibilidade da 1.6 com 1.21.1 requer QA; cobertura varia por mod.
- **Observações:** Arquivo instalado `Excalibur Additions_Minor Mod Support 1.6.zip`. Não inferir que todos os mods suportados pelo projeto estão instalados nem que todos possuem 100% de cobertura.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial Excalibur Additions: Minor Mods Support e matriz de arquivos.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-additions-mod-support
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; coleção multi-mod 1.6, presença física dos alvos, drift de versão, cobertura variável, load order, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur Additions_Minor Mod Support 1.6.zip`, versão catalogada `1.6`. O projeto agrega suportes visuais menores para vários mods em uma única camada Excalibur.

## 1. Papel e authority
Excalibur | Additions: Minor Mods Support é um pacote visual comunitário. Cada mod alvo continua authority do próprio gameplay; o pack apenas substitui assets onde possui cobertura.

## 2. Escopo publicado
A página oficial mantém uma lista dinâmica de mods suportados e percentuais de cobertura. Entre os alvos relevantes fisicamente presentes no perfil estão **Advancement Plaques**, **Stylish Effects** e **YUNG's Cave Biomes**; `Overflowing Bars` também está presente no pack físico e deve ser verificado visualmente quando houver asset correspondente. Outros mods exibidos pelo projeto não devem ser tratados como instalados sem confirmação física.

## 3. Boundary de versão
O arquivo instalado chama-se `1.6`, mas a matriz oficial atual associa a release 1.6 à linha mais nova `26.1`, enquanto a última release explicitamente rotulada 1.21.1 na listagem é 1.2. Isso cria um **drift de metadata de distribuição**: o arquivo físico é autoridade de presença/nome, mas a compatibilidade de cada asset 1.6 com Minecraft 1.21.1 deve permanecer fail-closed até QA.

## 4. Cobertura por mod
A cobertura não é uniforme. O próprio upstream publica percentuais/observações por integração; portanto não se deve transformar o nome “Minor Mods Support” em promessa de cobertura total para todos os mods listados.

## 5. Load order
O pack precisa ficar acima do Excalibur base. Packs específicos de um dos mods alvo podem ficar acima dele quando se deseja que suas textures/models prevaleçam.

## 6. Client e resource reload
É uma camada client-side. Ativar/desativar ou reordenar deve apenas provocar resource reload. Advancement state, effects, cave biome generation e demais dados continuam nos mods respectivos.

## 7. Riscos
1. Build 1.6 não estar explicitamente marcada 1.21.1 na matriz atual.
2. Cobertura parcial por mod produzir mistura visual.
3. Mod atualizado alterar asset paths.
4. Support pack específico disputar o mesmo path.
5. Mod listado upstream não estar instalado no perfil.

## 8. Matriz de testes
- [ ] Advancement Plaques: frame/title visual e legibilidade.
- [ ] Stylish Effects: icons/UI onde aplicável.
- [ ] YUNG's Cave Biomes: assets cobertos sem fallback.
- [ ] Verificar apenas integrações de mods fisicamente presentes.
- [ ] Confirmar prioridade acima do Excalibur.
- [ ] Resource reload sem missing texture/model.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma o caráter comunitário, a lista de suportes e a release 1.6. A mesma matriz não rotula 1.6 especificamente como 1.21.1; por isso a ficha registra o drift em vez de normalizá-lo.

> Boundary canônico: **o pack é somente uma coleção de overrides visuais; presença e funcionalidade dos mods alvo devem sempre vir da modlist física**.
