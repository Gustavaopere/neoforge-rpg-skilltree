# Just Expressions

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81999beee8008debaf4d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack
- **Arquivo:** `JustExpressions_v1.2.1.zip`
- **Versão 1.21.1:** 1.2.1
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `JustExpressions_v1.2.1.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma EMF `3.3.5` e ETF `7.2.1`, acima dos mínimos publicados pelo upstream para Just Expressions.

## Propriedades do banco

- **Mod:** Just Expressions
- **Arquivo JAR:** `JustExpressions_v1.2.1.zip`
- **Tipo de conteúdo:** Resource Pack
- **Versão 1.21.1:** 1.2.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual
- **Função:** Resource pack EMF que leva as animações de olhos inspiradas no Fresh Animations para o modelo do jogador, sem substituir o restante das animações do player.
- **Dependências:** Entity Model Features 3.3.5 e Entity Texture Features 7.2.1 no stack físico. O upstream cita mínimos EMF +v2.0.2 e ETF +v6.0.1. OptiFine não suporta player custom models neste contexto.
- **Sobreposição:** Pode colidir com player model/animation packs que alterem eyes/UVs/model definitions. O upstream orienta carregar packs compatíveis acima de Just Expressions 1.2.
- **Compatibilidade/Riscos:** Requer EMF e ETF; stack físico supera os mínimos publicados. Compatibilidade com outros player animation resource packs só funciona quando o outro pack oferece suporte e deve ser carregado acima de Just Expressions 1.2.
- **Observações:** Arquivo físico `JustExpressions_v1.2.1.zip`, versão 1.2.1. O arquivo declara explicitamente suporte a Minecraft 1.21.1 e corrige, nessa release, o head overlay model em versões anteriores a 1.21.2.
- **Procedência:** CurseForge oficial Just Expressions v1.2.1 + captura Resource Packs do perfil em 08/09/2026 + modlist física EMF 3.3.5/ETF 7.2.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/just-expressions/files/8373455
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Just Expressions 1.2.1, suporte 1.21.1, EMF/ETF, player eye animations, load order, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `JustExpressions_v1.2.1.zip`, versão `1.2.1`. O arquivo declara suporte explícito a Minecraft `1.21.1`.

## 1. Papel e authority
Just Expressions é um resource pack baseado em **EMF** que leva animações de olhos inspiradas no Fresh Animations para modelos de jogador. Minecraft continua authority de player state, skin, movement e gameplay; EMF/ETF e o pack controlam apenas apresentação.

## 2. Dependências confirmadas
O upstream exige versões recentes de **Entity Model Features** e **Entity Texture Features**, citando mínimos EMF `+v2.0.2` e ETF `+v6.0.1`. O stack físico atual contém EMF `3.3.5` e ETF `7.2.1`, acima desses mínimos.

## 3. Cobertura visual
O escopo publicado é deliberadamente estreito: **expressões/animações de olhos do jogador**. Não se deve registrar Just Expressions como um player animation overhaul completo.

## 4. Release 1.2.1
O changelog corrige o head overlay model não acompanhando a animação da cabeça em versões anteriores a 1.21.2, relevante diretamente para Minecraft 1.21.1.

## 5. Load order
Para compatibilidade com outros player resource packs, o upstream informa que o outro pack precisa oferecer suporte e deve ser carregado **acima de Just Expressions 1.2**.

## 6. Riscos
1. Outro player model pack sobrescrever olhos/UVs/model definitions.
2. Player animation pack não preparado para Just Expressions.
3. EMF/ETF cache ficar stale após reload.
4. Head overlay desalinhado em combinação não testada.
5. Skin layout incomum revelar artefatos.

## 7. Matriz de testes
- [ ] Jogador local em primeira/terceira pessoa.
- [ ] Jogadores remotos com skins variadas.
- [ ] Eye animation e head overlay em 1.21.1.
- [ ] Integração com player animation packs ativos.
- [ ] Confirmar ordem de packs compatíveis acima de Just Expressions.
- [ ] Resource reload/relog sem model quebrado.
- [ ] Confirmar que pack on/off não altera movimento ou estado do jogador.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma v1.2.1, suporte 1.21.1, dependências EMF/ETF, origem das eye animations e regra de load order. O catálogo limita o escopo ao visual publicado.

> Boundary canônico: **Just Expressions/EMF/ETF controlam a apresentação dos olhos; Minecraft e os mods controlam o jogador e seu comportamento**.
