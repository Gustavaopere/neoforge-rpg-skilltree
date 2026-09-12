# Mobs Refreshed

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db8124a1d3ff54b985e109
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack
- **Arquivo:** `mobs-refreshed-v2.2.zip`
- **Versão 1.21.1:** 2.2
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `mobs-refreshed-v2.2.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma `entity_model_features-3.3.5-1.21-neoforge.jar`, mod id `entity_model_features`, runtime `3.3.5`, infraestrutura recomendada pelo autor para CEM.

## Propriedades do banco

- **Mod:** Mobs Refreshed
- **Arquivo JAR:** `mobs-refreshed-v2.2.zip`
- **Tipo de conteúdo:** Resource Pack
- **Versão 1.21.1:** 2.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Mobs
- **Função:** Overhaul visual de mobs hostis vanilla, com novos designs, shading e modelos adicionais para 29 mobs, sem alterar AI, spawn, combat ou drops.
- **Dependências:** Resource pack v2.2; Entity Model Features (EMF) é recomendado pelo autor e está presente no stack físico. Fresh Animations requer o pack `Mobs Refreshed + Fresh Animations` para integração visual adequada.
- **Sobreposição:** Compete com Fresh Animations e outros CEM/entity packs; usar o compat dedicado Mobs Refreshed + Fresh Animations. Boss Refreshed e Fresh Illager/Illager packs cobrem famílias separadas.
- **Compatibilidade/Riscos:** O pack cobre majoritariamente mobs hostis e exclui bosses/illagers, que possuem packs separados. Recomenda EMF; coexistência com Fresh Animations exige o compat dedicado. Outros entity model packs podem disputar CEM/textures.
- **Observações:** Arquivo instalado `mobs-refreshed-v2.2.zip`, versão 2.2 com suporte oficial a 1.21.1. Linha v2.2 adiciona Creaking, Ghastling/Happy Ghast e refaz Ghast/Bogged; bosses e illagers ficam fora deste pack.
- **Procedência:** CurseForge oficial Mobs Refreshed v2.2 + captura Resource Packs do perfil em 08/09/2026 + stack físico EMF/Fresh Animations.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/mobs-refreshed
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Mobs Refreshed v2.2, 29 hostile mobs, EMF, exclusões bosses/illagers, Fresh Animations boundary, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `mobs-refreshed-v2.2.zip`, versão `2.2`, com suporte oficial a Minecraft 1.21.1. O autor recomenda Entity Model Features para custom entity models.

## 1. Papel e authority
Mobs Refreshed é um overhaul visual de mobs vanilla. Minecraft continua authority de entity registry, AI, spawn, health, damage, drops e persistence.

## 2. Cobertura confirmada
O projeto declara **29 mobs refreshed**, com novos designs, shading e modelos adicionais. O foco é majoritariamente em mobs hostis; bosses e illagers são tratados por packs separados da mesma família.

## 3. Build 2.2
A v2.2 suporta 1.21.1. O changelog da linha inclui **Creaking**, **Ghastling e Happy Ghast**, além de redesigns de **Ghast** e **Bogged**. Esses itens são cobertura visual, não entidades novas introduzidas pelo resource pack.

## 4. Infraestrutura CEM
O autor recomenda **Entity Model Features (EMF)** em vez de OptiFine para maior suporte de features. A modlist física confirma EMF `3.3.5`; custom models/rules devem ser validados após reload/relog.

## 5. Boundary com Fresh Animations
Há um pack separado `Mobs Refreshed + Fresh Animations`. A coexistência direta das duas bases não deve ser tomada como integração garantida sem esse compat dedicado e load order coerente.

## 6. Sobreposição e riscos
1. Outro entity/CEM pack disputar os mesmos mobs.
2. Compat Fresh Animations ficar ausente ou em prioridade incorreta.
3. Boss/illager ser confundido como coberto pelo pack-base.
4. EMF rule/model cair em fallback ou ficar stale.
5. Update de Minecraft alterar model/texture paths.

## 7. Matriz de testes
- [ ] Amostrar diferentes hostile mobs cobertos.
- [ ] Conferir Creaking, Ghast/Ghastling/Happy Ghast e Bogged quando disponíveis na linha de assets.
- [ ] Confirmar EMF ativo.
- [ ] Testar junto ao compat Mobs Refreshed + Fresh Animations.
- [ ] Verificar que bosses/illagers usam seus packs próprios.
- [ ] Resource reload/relog sem entidade invisível ou model quebrado.
- [ ] Confirmar que pack on/off não altera AI, spawn, damage ou drops.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
CurseForge oficial confirma v2.2, suporte a 1.21.1, 29 mobs, recomendação de EMF e a separação editorial de bosses/illagers. Não se atribui gameplay ao resource pack.

> Boundary canônico: **Mobs Refreshed controla apresentação; Minecraft controla as entidades e seu comportamento**.
