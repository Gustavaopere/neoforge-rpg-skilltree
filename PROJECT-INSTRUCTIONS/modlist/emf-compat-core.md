# EMF Compat: Core — 2.0.0

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3d369db9f0db81739387fe686a5f4837  
> Estado no momento da reconciliação: `Integrado ao Github`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Reconciliado em: 2026-09-09

## Propriedades do registro

- **Mod:** EMF Compat: Core
- **Arquivo JAR:** `emf_compat_core_1.21.1_2.0.0.jar`
- **Versão 1.21.1:** `2.0.0`
- **Categoria:** Biblioteca; Visual; Compat
- **Decisão:** Dependência
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/emf-compat-core
- **Função:** Biblioteca client-side compartilhada da família EMF Compat que captura/restaura poses de player para impedir que Entity Model Features sobrescreva animações de outros mods.
- **Dependências:** Entity Model Features 3.3.5 e Entity Texture Features 7.2.1 estão presentes. Consumers físicos neste lote: EMF Compat: Create 2.0.0 e EMF Compat: Iron's Spells 2.0.0.
- **Compatibilidade/Riscos:** Client-only e altamente sensível à ordem de render/pose. Riscos: capture/restore em fase errada, braços/corpo/cabeça aplicados duas vezes, primeira e terceira pessoa divergirem, consumer em versão diferente, EMF update alterar pose pipeline e outro player-animation mod competir pela mesma parte do corpo.
- **Sobreposição:** Não substitui EMF/ETF nem os consumers específicos. É infraestrutura de pose precedence; qualquer outro core de player-animation que toque os mesmos transforms precisa coexistência deliberada.
- **Observações:** A documentação oficial 2.0.0 define o Core como required shared library para todos os EMF Compat addons. Ele não adiciona gameplay visível; captura a pose produzida por outro mod antes da sobreposição EMF e a restaura depois, preservando a animação externa.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `emf_compat_core_1.21.1_2.0.0.jar`, mod id `emf_compat_core`, versão 2.0.0 e SHA-1 e22256acaaabc43d4043b119a748e8f52e27a451. CurseForge oficial confirma release 2.0.0 NeoForge 1.21.1 Client de 02/09/2026.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — EMF Compat Core 2.0.0; pose capture/restore, client-only boundary, consumer modules, lifecycle, render precedence, risks and tests cataloged.
- **Data da última decisão:** 2026-09-06

> **Runtime físico confirmado:** `emf_compat_core_1.21.1_2.0.0.jar` · mod id `emf_compat_core` · versão `2.0.0` · NeoForge 1.21.1 · **Client-only**.

## 1. Papel no modpack
EMF Compat: Core é a biblioteca compartilhada da família EMF Compat. Sua função é impedir que animações de player produzidas por outros mods sejam apagadas pelo Entity Model Features quando um resource pack animado assume a pose do modelo.

## 2. Authority / ownership
- **EMF:** animation/model layer do resource pack.
- **Mod externo:** produz sua própria pose ou animation state.
- **EMF Compat Core:** infraestrutura de captura/restauração da pose.
- **Consumer EMF Compat:** decide quais partes/situações precisam de compatibilidade para um mod específico.

O Core não deve inventar uma animation de gameplay por conta própria.

## 3. Pose capture/restore
A documentação oficial descreve o fluxo central:
1. outro mod produz a pose de body/arms/head;
2. a compat captura essa pose antes de EMF sobrescrevê-la;
3. após a aplicação EMF, a pose necessária é restaurada;
4. as partes que não precisam ser preservadas continuam sob controle do resource-pack animation.

Esse fluxo é sensível à ordem do render pipeline.

## 4. Primeira e terceira pessoa
O Core declara manter poses de primeira e terceira pessoa coerentes. Isso é importante em um pack com várias camadas de player model/animation: uma pose correta em third person não prova que first person também esteja correta.

## 5. Consumers físicos atuais
Neste lote estão presentes:
- **EMF Compat: Create 2.0.0**;
- **EMF Compat: Iron's Spells 2.0.0**.

O pack também possui EMF 3.3.5 e ETF 7.2.1. Cada consumer deve permanecer alinhado ao Core para evitar API/pipeline drift.

## 6. Sem gameplay próprio
A página oficial é explícita: o Core não adiciona visible gameplay features. Ele é um framework client-side. Portanto não deve ser documentado como provider de combat, spells, physics, items ou movement.

## 7. Render precedence
Quando duas compatibilidades querem preservar partes diferentes do mesmo modelo, a precedence de capture/restore precisa continuar determinística. O risco não é só visual: uma pose restaurada no momento errado pode apagar a animation que outro consumer pretendia preservar.

## 8. Resource packs
Fresh Animations e outros packs baseados em EMF continuam donos das animações que não são temporariamente substituídas pelo mod externo. O Core existe justamente para permitir coexistência em vez de desligar EMF globalmente.

## 9. Client-only boundary
O projeto oficial classifica o Core como **Client**. Não há motivo para usar o Core como fonte de truth no servidor. Dedicated server não deve depender dessa biblioteca para validar actions, spells, movement ou physics.

## 10. Lifecycle
Validar:
- resource reload;
- troca de resource pack;
- world join/rejoin;
- first/third person toggle;
- player respawn;
- dimension change;
- consumer animation start/stop;
- update isolado de EMF/Core/consumer.

## 11. Multiplayer
Cada cliente renderiza a pose a partir do state sincronizado do jogo/mod externo. O Core não cria um state gameplay compartilhado. Outros jogadores precisam apenas receber o state normal do provider e renderizá-lo corretamente localmente.

## 12. Riscos
1. pose capture em fase errada;
2. restore apagar animation EMF desejada;
3. mesma parte do corpo restaurada duas vezes;
4. first/third person divergirem;
5. consumer compilado para Core diferente;
6. update EMF mudar hooks internos;
7. resource reload manter pose/cache stale;
8. animation permanecer presa após action terminar;
9. outro player-animation mod competir na mesma fase;
10. Core ser instalado no servidor como se fosse gameplay dependency.

## 13. Matriz de testes
1. Cliente com EMF 3.3.5 + ETF 7.2.1 + Core 2.0.0.
2. Resource pack com player animation EMF ativo.
3. Testar consumer Create separadamente.
4. Testar consumer Iron's Spells separadamente.
5. Testar ambos carregados na mesma sessão.
6. Alternar first/third person durante animation.
7. Resource reload durante sessão.
8. Relog/respawn/dimension change.
9. Observar outro jogador executando animação suportada.
10. Update smoke-test de EMF/Core/consumer em instância de teste.

**Esta catalogação não afirma que esses testes foram executados.**

## 14. Evidências
- modlist física canônica: JAR/mod id/version/SHA-1 e consumers atuais;
- CurseForge oficial: release 2.0.0 NeoForge 1.21.1, Environment Client;
- descrição oficial: shared library, pose capture/restore e consistência first/third person.

> **Boundary canônico:** EMF Compat Core é authority apenas do **mecanismo de compatibilidade de poses**, nunca do gameplay que originou a animação.