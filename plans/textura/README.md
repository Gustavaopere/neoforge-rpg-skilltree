# Textura e Apresentação — autoridade de planejamento

Esta pasta é a autoridade canônica de **apresentação** do RPG Skill Tree.

Ela existe para separar direção visual e audiovisual dos contratos de engenharia dos estágios numerados em `plans/`.

## Escopo

Pertencem a `plans/textura/`:

- UI e HUD;
- layout, hierarquia visual e navegação de telas;
- sprites, ícones, badges, gauges e ornamentação;
- texturas e materiais de apresentação;
- modelos, previews, rigs e poses quando forem parte da apresentação;
- animações e motion de interface;
- animações cosméticas/representacionais que não decidam gameplay;
- VFX, partículas e composição visual;
- áudio, SFX, cues e identidade sonora;
- tipografia, contraste e acessibilidade visual;
- estados visuais derivados de estados funcionais já definidos pela engenharia;
- critérios de validação visual e handoff para assets.

## O que não pertence aqui

Continuam nos estágios de engenharia:

- autoridade server-side;
- regras de gameplay, damage, custo, cooldown, gating, progressão e efeitos;
- registries, IDs persistidos e schemas;
- rede, snapshots, sincronização e validação de requests;
- persistência, migração e recuperação;
- seleção de provider e contratos de API;
- hooks/eventos e causalidade;
- deduplicação, idempotência e anti-abuso;
- classloading e compatibilidade opcional;
- segurança, fail-closed/fail-soft e performance funcional.

Regra curta: **Engenharia define o que o sistema significa e faz; Textura define como esse estado é apresentado ao jogador.**

## Dependência entre as duas autoridades

Textura consome um handoff de engenharia. Um asset, tela, animação, partícula ou som nunca pode ser usado para criar autoridade de gameplay inexistente.

Exemplo:

```text
Engineering
  estado = LOCKED
  motivo = MISSING_PREREQUISITE
  dados = prerequisiteIds[]

Textura
  aparência do nó LOCKED
  ícone/ornamento do requisito
  contraste, tooltip e motion de feedback
```

O cliente pode apresentar, mas não conceder unlock, dano, custo, recompensa ou qualquer mutação autoritativa.

## Planos migrados/separados

- `01-skill-tree-ui-hud.md` — árvore de habilidades e feedback de progressão;
- `02-localizacao-acessibilidade-visual.md` — legibilidade, contraste e apresentação de texto localizado;
- `03-compendio-ui-modelos-previews.md` — Compêndio Natural, previews e navegação visual;
- `04-itemizacao-tooltips-identidade-visual.md` — tooltips, Rank e identidade visual de equipamentos;
- `05-corpos-seletor-construcao-ritual.md` — seletor de corpos e superfícies de construção/transmigração;
- `06-cartografia-waypoints-bosses.md` — mapas, overlays, waypoints e Boss Checklist;
- `07-animacao-vfx-audio.md` — boundary para animação, VFX, partículas e áudio;
- `08-assets-texturas-modelos-gerais.md` — inventário e regras de handoff para assets gerais;
- `FRONTEIRA-ENGENHARIA-TEXTURA.md` — contrato normativo de ownership;
- `AUDITORIA-SEPARACAO-2026-09-13.md` — origem e destino do conteúdo auditado.

## Estado da auditoria de animação/VFX/áudio

A auditoria de 2026-09-13 não encontrou, entre os planos ativos de apresentação auditados, um plano normativo dedicado que especifique clips, rigs, partículas, paletas de VFX ou cues/arquivos de áudio. Por isso nada desse conteúdo foi inventado durante a migração.

`07-animacao-vfx-audio.md` estabelece a authority e o formato de handoff para especificações futuras; decisões artísticas concretas só podem ser adicionadas quando houver requisito aprovado e estado/evento de engenharia correspondente.

## Autoridade

Para runtime, código/testes continuam superiores a qualquer plano. Para direção de apresentação ainda não materializada em assets, esta pasta é a fonte de planejamento; menções visuais em planos de engenharia descrevem apenas a semântica que precisa ser perceptível e devem apontar para cá para sua materialização estética.
