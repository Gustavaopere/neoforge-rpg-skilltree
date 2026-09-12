# Fresh Animations: Player Extension

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81d3b45ce93ce1571ed6
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `FA+Player-v1.1.zip`
- **Versão 1.21.1:** 1.1
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `FA+Player-v1.1.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A modlist física acessível de 08/09/2026 confirma `entity_model_features-3.3.5-1.21-neoforge.jar` (EMF `3.3.5`) e `entity_texture_features-7.2.1-1.21-neoforge.jar` (ETF `7.2.1`), requisitos explícitos do Player Add-on.
- Fresh Animations `1.10.4` e o ZIP do Player Extension são autoridades visuais registradas pela captura de Resource Packs, não JARs top-level da modlist.
- Epic Fight e Just Expressions permanecem authorities separadas sobre suas próprias superfícies; qualquer colisão com o Player Extension é tratada como compatibilidade visual/animação, não transferência de gameplay authority.

## Propriedades do banco

- **Mod:** Fresh Animations: Player Extension
- **Arquivo JAR:** `FA+Player-v1.1.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual
- **Função:** Extensão oficial do Fresh Animations que anima o jogador em primeira e terceira pessoa, incluindo poses/rotações das mãos e ajustes de movimento/apresentação do player.
- **Dependências:** Entity Model Features 3.3.5 e Entity Texture Features 7.2.1 estão presentes e são requisitos explícitos do arquivo Player Add-on 1.1. Fresh Animations 1.10.4 é a base estética do ecossistema.
- **Sobreposição:** Overlap direto possível com Just Expressions em player model/face resources e com sistemas de animação do jogador como Epic Fight. O pack controla presentation; combat state e mecânicas permanecem nos respectivos mods.
- **Compatibilidade/Riscos:** Player Add-on 1.1 suporta explicitamente 1.21.1. Pode sobrepor player models/animations de Just Expressions, Epic Fight e outros packs de player animation; cada camada deve ser validada por first/third person e combat state.
- **Observações:** Arquivo físico `FA+Player-v1.1.zip`, versão 1.1. A release adiciona first-person hand animations, pitch/yaw rotations e map holding poses, além de corrigir flight, landing, FPS-dependent animations e compatibility de head/elytra models.
- **Procedência:** CurseForge oficial Fresh Animations: Player Extension 1.1 + captura Resource Packs do perfil em 08/09/2026 + modlist física EMF 3.3.5/ETF 7.2.1/Fresh Animations 1.10.4.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/fa-player-extension/files/8245230
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Fresh Animations Player Extension 1.1, suporte 1.21.1, EMF/ETF obrigatórios, first-person hands, player animations, overlaps, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `FA+Player-v1.1.zip`, versão `1.1`. O arquivo oficial suporta explicitamente Minecraft `1.21.1` e requer EMF + ETF.

## 1. Papel e authority
Fresh Animations: Player Extension leva a linguagem de animação do Fresh Animations ao **jogador**, incluindo primeira e terceira pessoa. Minecraft e os mods continuam authorities de movimento, combat state, inventory, attributes e networking; a extensão controla apresentação.

## 2. Dependências obrigatórias
A página do arquivo declara **Entity Model Features** e **Entity Texture Features** como requisitos. O perfil físico contém EMF `3.3.5` e ETF `7.2.1`, satisfazendo essa infraestrutura. Fresh Animations `1.10.4` é a base estética do ecossistema.

## 3. Release 1.1
A v1.1 adiciona novas **first-person hand animations**, rotações de pitch/yaw e poses de segurar mapa. Também ajusta head animations e elytra models e corrige landing reset, flying animations de outros jogadores, dependência de FPS, equipment animations após camera rotation e map hand poses.

## 4. Boundary com Just Expressions
Just Expressions também atua no modelo do jogador, porém com foco estreito em olhos/expressões. A coexistência precisa de load order e QA porque ambos podem tocar player model resources; não se presume compatibilidade perfeita apenas por objetivos diferentes.

## 5. Boundary com Epic Fight
Epic Fight controla estados/animações de combate e comportamento de battle mode. Player Extension é uma camada visual; qualquer disputa de transforms/poses deve ser tratada como compatibilidade de animação, não como transferência de authority de combate.

## 6. Client e reload
É conteúdo client-side. Resource reload/relog deve trocar modelos/animações sem alterar movimento real, velocidade, stamina, damage ou outros dados do player.

## 7. Riscos
1. Conflito de model/animation com Just Expressions.
2. Epic Fight sobrescrever ou ser sobrescrito em poses de combate.
3. Elytra/equipment models apresentarem clipping.
4. First-person hand pose conflitar com item-specific animations.
5. EMF/ETF cache manter transform stale.

## 8. Matriz de testes
- [ ] Idle/walk/run/jump/landing em terceira pessoa.
- [ ] First-person hands com itens e mapa.
- [ ] Elytra/flying local e remoto.
- [ ] Equipar/remover armor e itens rapidamente.
- [ ] Testar junto ao Just Expressions.
- [ ] Testar battle mode do Epic Fight.
- [ ] Resource reload/relog sem model quebrado.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma Player Add-on 1.1, suporte 1.21.1, requisitos EMF/ETF e as mudanças específicas da release. O catálogo não atribui mecânica de player ao resource pack.

> Boundary canônico: **Player Extension/EMF/ETF controlam apresentação do jogador; Minecraft e mods como Epic Fight controlam comportamento e gameplay**.
