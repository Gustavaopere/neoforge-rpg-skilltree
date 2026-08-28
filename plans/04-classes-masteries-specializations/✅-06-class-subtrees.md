# Classes Complete — Dedicated Class Subtrees

**Goal:** fechar árvores próprias de classes que têm progressão interna além do reconhecimento da identidade.

- [x] Technomancer: revisar gateways Create/AE2/Oritech, caminhos e capstone.
- [x] Warlock: validar cinco pactos, exclusividade e reselection/respec.
- [x] Druid: validar formas naturais e permissões associadas.
- [x] Metamorph: validar formas humanoides/monstruosas/aberrantes e blacklist.
- [x] Garantir localização, requisitos e node effects coerentes em cada subtree.
- [x] Testar respec e perda de requisitos de classe.

## Runtime contract

- A validação cliente/dados mantém paridade dos subtrees materializados: Technomancer 17 nós, Warlock 18, Druid 11 e Metamorph 10 no contrato atual.
- Technomancer contém gateways dedicados de Create Kinetics, AE2 Networks e Oritech Power e fecha em `triune_core`.
- Warlock usa cinco escolhas de pacto no mesmo grupo com capacidade padrão 1; limpar a escolha revalida e remove/refunda nós que dependiam dela.
- Druid e Metamorph obtêm permissões de forma a partir da classe e dos nós atualmente investidos; a classificação separa formas naturais, humanoides, monstruosas, aberrantes e entidades técnicas/blacklisted.
- Requisitos de acesso de subtree são reavaliados pelo servidor; quando uma classe/requisito desaparece, nós inválidos são removidos e reembolsados.
- Node effects e localização atuais são validados junto do restante dos datapacks; a tradução `pt_br` contém as identidades e nós player-facing das quatro árvores.

## Verification

- PR #4 / milestone Alpha 2 integrou Technomancer, Warlock, Druid e Metamorph com safe pact respec e Identity 2 morph gate.
- `Alpha2ProgressionTest` cobre pact exclusivity, safe pact respec, permissões Druid/Metamorph, blacklist, requirements e remoção/refund de class subtrees inválidas.
- `validate-client-tree.py` verifica a materialização e gating das quatro árvores; `validate-node-effects.py` valida seus packs de efeito.
- Auditoria de fechamento: `main@7b33aa2af6a96f0f7c72b0dda0492d0b172cd141`.
- CI `33132979048` / run #620: validações, build e dedicated-server smoke GREEN.

**Acceptance:** satisfied. Cada subtree é gated pela identidade/requisitos atuais e o estado derivado não permanece órfão após respec ou perda do requisito.