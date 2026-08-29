# 11.07 — Runtime de atributos e Infixos

## Objetivo

Aplicar os efeitos de equipamento sem stacking acidental, sem listeners por item e sem recalcular todo inventário a cada tick.

## Passo a passo

### A — Snapshot efetivo equipado

Quando o loadout relevante muda:

1. ler identidades itemizadas equipadas;
2. resolver definições existentes;
3. aplicar scaling de Rank/Poder;
4. produzir snapshot efetivo imutável;
5. projetar atributos/efeitos;
6. cachear por revisão de loadout.

### B — Atributos idempotentes

- [ ] IDs determinísticos por entidade/slot/instância/modifier;
- [ ] remover/substituir projeção anterior antes de aplicar nova;
- [ ] nunca somar novamente em login/reload/re-equip;
- [ ] reutilizar serviço canônico de modifiers do Stage 01 quando aplicável.

### C — Infixos event-driven

Centralizar dispatch por eventos suportados:

- dano causado/recebido;
- kill;
- cast de magia;
- projétil;
- block/mining/use;
- movimentação/voo;
- mana/energia/recurso;
- outros hooks explicitamente contratados.

Um evento consulta o snapshot agregado; não registrar até 15 listeners independentes por item.

### D — Cooldowns, chances e recursão

- [ ] cooldown/ledger server-side;
- [ ] caps para chance e redução;
- [ ] proteção anti-recursion para proc que causa novo evento;
- [ ] política explícita para efeitos iguais em múltiplos equipamentos;
- [ ] não reutilizar a seed persistida de geração como RNG previsível de combate.

### E — Mobs

O mesmo resolver deve aceitar entidade viva equipada quando o efeito fizer sentido. Não criar runtime paralelo simplificado para mobs.

### F — Degradação segura

Definição ausente/inválida: preservar o ID persistido, ignorar somente o efeito inseguro e emitir diagnóstico bounded. Nunca substituir por outro modifier.

## Testes previstos

- equip/unequip/re-equip sem stacking;
- save/load idempotente;
- múltiplos slots;
- Infixo sem recursão infinita;
- cooldown server-authoritative;
- mob usando modifier;
- cache invalidado apenas em mudanças relevantes.

## Acceptance

Atributos e Infixos produzem efeitos reais, idempotentes e bounded para jogadores/mobs sem polling completo por tick e sem transformar estado derivado em fonte de verdade.
