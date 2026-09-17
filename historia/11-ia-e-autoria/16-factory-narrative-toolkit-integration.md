# Integração com o Narrative Authoring Toolkit da Factory

## Escopo

Este arquivo registra a integração do corpus real deste repositório com o contrato de profile revision 1 do `minecraft-mod-factory`.

Existem dois profiles deliberadamente distintos:

- `historia/narrative-authoring-profile.json` — profile **canônico da campanha**, usado pelo workflow consumidor principal contra a árvore `historia/` e os diálogos versionados;
- `historia/11-ia-e-autoria/factory-narrative-profile.json` — profile **de probe de compatibilidade**, restrito ao slice `historia/03-npcs/principais` para exercitar contratos avançados concretos sem impor novas estruturas ao corpus legado inteiro.

Ambos pertencem a este repositório porque paths, headings e regras editoriais são específicos do projeto. Nenhum deles transfere canon para a Factory.

## Pin validado

- Factory repository: `Gustavaopere/minecraft-mod-factory`;
- Factory commit hardened: `86b83005cde89f26ad2ef03af43cf512bc085080`;
- profile contract revision: `1`.

Os workflows `.github/workflows/narrative-factory-consumer.yml` e `.github/workflows/narrative-factory-compat.yml` usam esse SHA explicitamente. Atualização do pin é uma decisão separada e revisável.

O export neutro de authority é emitido pela Factory em `stdout` e redirecionado pelo workflow consumidor para caminhos literais controlados pelo CI, evitando que argumentos CLI escolham destinos de escrita no filesystem.

## Consumer canônico

O workflow `Narrative Factory Consumer` valida a árvore editorial completa com o profile canônico:

- `validate_story.py` para IDs, estados, referências e contratos estruturais base;
- `validate_advanced.py` para contratos avançados opt-in declarados pelo profile;
- `validate_dialogues.py` para `historia/12-dialogos`;
- `story_inventory.py` para produzir inventário spoiler-bearing apenas dentro do runner;
- `authority_reconcile.py` para exportar esse inventário ao schema neutro e executar um round-trip sem mutação.

O inventário não é publicado como artifact pelo workflow, porque constitui superfície editorial potencialmente carregada de spoilers.

## Probe avançado

O probe usa `historia/03-npcs/principais` como slice real e não fabrica lore para satisfazer tooling.

- `NPC-0001-aren-autoria.md` exercita provenance/source-grounding e limites de expansão;
- `NPC-0007-maura-autoria.md` exercita source-grounding próprio, exigindo as seções de authority/proveniência e limites de knowledge e reconhecendo as superfícies editoriais de voz e aparência já versionadas;
- `NPC-0003-iren-valmor-autoria.md`, `NPC-0004-liora-autoria.md`, `NPC-0005-oren-autoria.md` e `NPC-0006-elian-autoria.md` compartilham o contrato estrutural `authored-npc-grounding`, que exige `Referências`, limites explícitos de knowledge e superfícies editoriais de `Voz` e `Aparência` já presentes nos arquivos;
- os contratos de Maura e do grupo Iren–Elian não impõem `reference_rules` sobre suas seções de referência/proveniência porque elas citam entidades e materiais que vivem fora do `story_root` restrito do probe; isso evita transformar referências externas válidas em targets ausentes do slice ou forçar um único tipo sobre referências deliberadamente heterogêneas;
- `NPC-0001-aren.md` exercita o contrato estrutural de relações sistêmicas e preserva a multidimensionalidade declarada no próprio documento;
- o hook de chronology/causality está configurado para `EVT`, mas o slice atual não contém registros `EVT-####`; a cobertura algorítmica de arestas/ciclos permanece no golden corpus da Factory até existirem eventos reais adequados no consumidor;
- o inventory real é exportado para um snapshot neutro com provenance e comparado sem qualquer escrita de volta no canon.

## Evidência de integração em `main`

A adoção revision 1 foi integrada em ondas revisáveis:

- PR #578 — adicionou o probe de compatibilidade do profile v1 e validou o slice narrativo real contra o Factory hardened;
- PR #579 — promoveu o consumer canônico para revision 1, atualizou o pin do workflow principal para `86b83005cde89f26ad2ef03af43cf512bc085080`, adicionou `validate_advanced.py` e o round-trip neutro do inventário completo;
- PR #580 — atualizou a evidência documental da integração e o índice de autoria sem alterar canon;
- PR #581 — adicionou a ficha de autoria e o asset brief source-grounded de Maura; o merge `70d3a1a372c10c1a470ac565f661ad333de50b0e` concluiu **25 de 25** workflows de `push` com `success`, incluindo SonarQube e a matriz de worldgen;
- PR #585 — adicionou `maura-source-grounding` ao probe avançado sem alterar canon; o merge `81e1a169a77c4b459eb49db41e396b683e60256c` concluiu **25 de 25** workflows de `push` sem falha, cancelamento ou execução pendente.

O merge de #579 produziu o commit `37a31d1da0e794d21654c9468888426e18113257` em `main`.

Para esse SHA de merge:

- `Narrative Factory Consumer` run `35104815102`: **success**, incluindo story graph/editorial states, advanced contracts, dialogue structure, inventory local e authority round-trip;
- `CodeQL Security` run `35104815070`: **success**;
- `RPG Skill Tree CI` run `35104815112`: **success**, incluindo GameTests, build, verificação do JAR e dedicated-server smoke;
- consulta agregada dos workflows disparados pelo merge: **24 de 24 top-level workflows concluídos com `success`**.

As evidências de #581 e #585 confirmam que a expansão source-grounded de Maura e seu gate estrutural integraram sem regressão de CI; elas não substituem a authority narrativa e não transformam resultado de CI em aprovação de conteúdo ficcional.

## Material legado fora do corpus ativo

`historia/11-ia-e-autoria/legado-dialogos/` preserva diálogos aposentados somente para provenance histórica. Em particular, `DLG-0001-severin-calibracao.md` foi movido para esse diretório durante a migração Severin→Aren.

O arquivo não pertence a `historia/12-dialogos`, portanto não é diálogo ativo/player-facing e não calibra a voz de Aren. Sua preservação mantém rastreabilidade sem reintroduzir binding ativo a `NPC-0001`.

## Limites

Profiles e workflows são gates de integração, não substitutos para as authorities do projeto. Eles não promovem hipóteses, não sincronizam Grimoire ↔ GitHub automaticamente, não inventam relações e não criam assets visuais.

O estado editorial em português continua governado pelos documentos/canon existentes. Contratos avançados permanecem opt-in: ausência de uma capability avançada no profile canônico significa ausência daquela validação, não autorização para inferir conteúdo.
