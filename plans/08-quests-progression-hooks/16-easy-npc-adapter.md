# 08.16 — Easy NPC Adapter

## Goal
Usar Easy NPC como camada de personagem/diálogo/interação sem delegar a ele a autoridade narrativa.

## Baseline de conteúdo
Perfil narrativo: Easy NPC Bundle NeoForge 1.21.1; versão pública verificada em 2026-08-30: `7.9.0`.

A implementação deve auditar a API/código da versão exata instalada antes de compilar contra qualquer classe.

## Entregas
- [ ] Optional integration registration e classloading seguro.
- [ ] Resolver estável `narrativeActorId <-> Easy NPC entity/config identity`.
- [ ] Export read-only de conditions/facts/relationship/knowledge para diálogo.
- [ ] Ações de diálogo chamam mutation services do Narrative Core, nunca escrevem storage diretamente.
- [ ] Suporte a abrir diálogo apropriado por route/priority sem reproduzir toda árvore no Java.
- [ ] Reconciliação quando NPC está missing/dead/replaced.
- [ ] Comandos, tags ou scoreboard somente como bridge de compatibilidade, não source of truth.
- [ ] Proteção anti-double-fire de clique/packet/reopen.
- [ ] Localização PT-BR em arquivos de conteúdo.
- [ ] Dedicated-server/multiplayer tests.

## Fail-soft
Sem Easy NPC:
- o Narrative Core continua carregando;
- actors/quests permanecem no save;
- apenas apresentação/interação que depende do NPC fica indisponível;
- nenhuma flag é concedida automaticamente para “destravar” história.

## Acceptance
O diálogo de Severin muda conforme `status`, relationship e conhecimento do clero; a ação escolhida gera uma mutation canônica única e reabrir o diálogo não repete rewards/consequences.