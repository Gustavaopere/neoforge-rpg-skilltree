# Combat Plan — Magic Pipeline

**Goal:** harmonizar atributos mágicos e eventos de cast entre providers sem somar o mesmo bônus duas vezes.

- [ ] Definir contratos comuns para spell power, mana, cooldown, cast time e crit.
- [ ] Mapear Iron's e Ars para o pipeline sem perder semântica própria.
- [ ] Distinguir início, cast confirmado, impacto e cancelamento.
- [ ] Só conceder mastery em cast válido.
- [ ] Validar coexistência Iron's + Ars.
- [ ] Documentar quais bônus são genéricos e quais são de escola/provider.

**Acceptance:** casts válidos recebem efeitos corretos uma vez; cancelados não dão mastery nem proc final.