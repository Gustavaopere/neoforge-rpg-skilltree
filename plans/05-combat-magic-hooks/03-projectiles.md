# Combat Plan — Projectile Pipeline

**Goal:** preservar autoria e regras de perks até o impacto de projéteis.

- [ ] Definir snapshot-at-fire versus resolve-at-impact por categoria de atributo.
- [ ] Persistir/recuperar owner quando necessário.
- [ ] Tratar projétil sem owner, reflected e indirect sources.
- [ ] Evitar dupla resolução entre hit event e damage event.
- [ ] Testar longa distância, unload/reload e mudanças de dimensão plausíveis.

**Acceptance:** bônus de projétil pertencem ao jogador correto e são aplicados uma única vez no momento definido.