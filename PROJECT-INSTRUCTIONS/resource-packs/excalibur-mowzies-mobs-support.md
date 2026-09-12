# Excalibur | Mowzie's Mobs Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db81a1a7dcf82c74d4aa77
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur Mowzie's Mobs 1.1.zip`
- **Versão 1.21.1:** 1.1
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur Mowzie's Mobs 1.1.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A modlist física de 08/09/2026 confirma `mowziesmobs-1.21.1-1.8.2.jar`, mod id `mowziesmobs`, runtime `1.8.2`.
- O upstream da v1.1 descreve cobertura de mobs/bosses, items, particles, boss bars e GUI, além de Elokosa e itens relacionados. Essas categorias são preservadas como declaração oficial, não como inventário independente do ZIP.
- `Integrated Mowzie's Mobs` e `Mowzie's Cataclysm` estão presentes como projetos separados e não são automaticamente cobertos por este resource pack do mod base.

## Propriedades do banco

- **Mod:** Excalibur | Mowzie's Mobs Support
- **Arquivo JAR:** `Excalibur Mowzie's Mobs 1.1.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat, Mobs
- **Função:** Support pack 16x que retexturiza Mowzie's Mobs no estilo Excalibur, cobrindo mobs/bosses, itens, partículas, boss bars e GUI; v1.1 acrescenta Elokosa e seus itens.
- **Dependências:** Uso visual pretendido: Excalibur + Mowzie's Mobs. Stack físico atual: Mowzie's Mobs 1.8.2. Conteúdo client-side; AI, combate, loot e progressão permanecem no mod.
- **Sobreposição:** Pode colidir com Mobs Refreshed/Fresh Animations e outros retextures/model packs caso toquem os mesmos assets de Mowzie's Mobs. Sobreposição é visual, não de gameplay.
- **Compatibilidade/Riscos:** Riscos de drift entre v1.1 e Mowzie's Mobs 1.8.2, conflito com outros mob/model packs, boss bars/GUI sobrescritas, particle sprites e load order. Mudança visual não altera hitbox ou combate.
- **Observações:** Arquivo instalado `Excalibur Mowzie's Mobs 1.1.zip`, release 1.1 de 18/03/2026. Changelog v1.1 retexturiza o novo mob Elokosa e itens relacionados.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial Excalibur Mowzie's Mobs 1.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-mowzies-mobs-support
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; Mowzie's Mobs 1.8.2, cobertura v1.1, Elokosa, UI/particles/boss bars, load order, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur Mowzie's Mobs 1.1.zip`, versão `1.1`, para Minecraft 1.21.1. O alvo físico atual é Mowzie's Mobs `1.8.2`.

## 1. Papel e authority
O support pack altera apresentação visual de Mowzie's Mobs. O mod continua authority de entidades, bosses, AI, dano, habilidades, loot, spawn e progressão.

## 2. Cobertura confirmada
O upstream descreve overhaul de **mobs, bosses, itens, particles, boss bars e GUI**. A release 1.1 acrescenta o novo mob **Elokosa** e seus itens relacionados. Essas categorias são confirmadas; contagem exata de assets não é inferida.

## 3. Boss bars, GUI e particles
Boss bars e GUI continuam feedback client-side; não definem HP, fase ou causalidade de combate. Partículas retexturizadas também não alteram hit registration ou efeitos server-side.

## 4. Stack físico e drift
A modlist contém Mowzie's Mobs 1.8.2. O pack visual 1.1 deve ser validado contra essa build para detectar assets posteriores, paths renomeados ou conteúdo sem cobertura.

## 5. Load order e reload
Deve ter prioridade acima do Excalibur base. Resource reload troca apenas models/textures/UI/particles e não deve tocar entity state ou save.

## 6. Sobreposição
Mobs Refreshed, Fresh Animations e outros packs de entidades podem tocar as mesmas superfícies. A precedência precisa ser avaliada por model/texture path concreto; não presumir que animação e retexture sejam mutuamente exclusivos.

## 7. Riscos
1. Asset novo de Mowzie's 1.8.2 não coberto.
2. Outro pack sobrescrever mob/boss model ou texture.
3. Boss bar/GUI misturar estilos.
4. Particle sprite faltar após reload.
5. Elokosa ou itens relacionados caírem em fallback.
6. Mudança de modelo ser confundida com mudança de hitbox.

## 8. Matriz de testes
- [ ] Conferir bosses e mobs principais.
- [ ] Conferir Elokosa e itens relacionados.
- [ ] Conferir boss bars, GUI e particles durante encounter.
- [ ] Testar coexistência com packs de mobs/animação ativos.
- [ ] Resource reload sem missing assets.
- [ ] Confirmar prioridade acima do Excalibur.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma v1.1 e o escopo mobs/bosses/items/particles/boss bars/GUI, incluindo Elokosa. A modlist física confirma Mowzie's Mobs `1.8.2`. O ZIP não foi inventariado asset por asset.

> Boundary canônico: **Mowzie's Mobs controla gameplay; o support pack controla exclusivamente a apresentação visual coberta**.
