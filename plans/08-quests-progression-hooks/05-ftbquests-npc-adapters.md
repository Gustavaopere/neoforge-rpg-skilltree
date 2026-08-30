# Quest Hooks Plan — External Quest/NPC Adapters

**Goal:** ligar mods de quests/NPCs à API pública sem acoplamento no core e sem transformar UI/renderer em authority narrativa.

## Decisão arquitetural — 2026-08-30

O perfil completo de campanha usará:

- **Easy NPC Bundle** — NPCs especiais, diálogo, escolhas e ações;
- **FTB Quests** — diário, capítulos e acompanhamento;
- **MineColonies** — assentamento/cidadãos físicos;
- **KubeJS** — authoring, prototipagem e glue controlado.

Ver `DEPENDENCIAS-NARRATIVA.md` para versões públicas verificadas e política de instalação.

Esses mods são requeridos pelo **perfil narrativo completo do modpack**, mas permanecem **optional adapters do jar base**. O RPG Skill Tree/Narrative & Society Core conserva autoridade de estado, persistence, cronologia, relações, facções, leis e consequências.

## Requisitos comuns

- [ ] Registrar adapters pelo mecanismo de integrações opcionais; nenhum classloading inseguro quando provider estiver ausente.
- [ ] Mapear condições/rewards para APIs canônicas.
- [ ] Toda mutation externa passa por service público idempotente; nunca editar attachments/SavedData internos.
- [ ] Proteger ausência/incompatibilidade dos mods.
- [ ] Provider API/version deve ser auditada na versão exata instalada antes da implementação.
- [ ] Testar server-only, dedicated server e multiplayer.
- [ ] Não usar scoreboard/tags/comandos como source of truth; somente bridge quando necessário.

## Adapters detalhados

- `16-easy-npc-adapter.md`
- `17-ftb-quests-journal-adapter.md`
- `18-minecolonies-society-adapter.md`
- `19-kubejs-authoring-adapter.md`

**Acceptance:** remover qualquer mod de quests/NPC/authoring do ambiente de teste não impede o RPG base de carregar. No perfil completo, os adapters projetam o mesmo estado canônico sem pipelines concorrentes.