# Classes Plan — Deterministic Class Resolution

**Goal:** resolver identidades de classe a partir de estado canônico, sem depender da ordem dos eventos.

- [ ] Definir algoritmo para classes puras, híbridas e provider identities.
- [ ] Consumir somente snapshot canônico de perks/masteries.
- [ ] Permitir múltiplas identidades quando o design admitir.
- [ ] Definir precedência apenas quando semanticamente necessária.
- [ ] Recalcular após compra, respec, mastery threshold e reload.

**Acceptance:** a mesma build sempre produz o mesmo conjunto de classes, independentemente da ordem em que foi montada.