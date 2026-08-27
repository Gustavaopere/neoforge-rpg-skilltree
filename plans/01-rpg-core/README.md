# 01 — RPG Core

Estado: **EM ANDAMENTO / fundação já implementada**.

## Objetivo
Centralizar identidade RPG do jogador e serviços canônicos consumidos por perks, classes, mobs e integrações.

## Entregáveis
- nível/XP canônicos;
- atributos e modificadores;
- identidade/classe emergente;
- API interna de consulta e mutação;
- persistência e sync;
- invariantes server-authoritative.

## Critérios de aceite
- [ ] uma única fonte de verdade para level/XP;
- [ ] modificadores determinísticos e removíveis;
- [ ] nenhuma integração grava progressão por caminho paralelo;
- [ ] save/reload preserva o estado;
- [ ] cliente recebe apenas o estado necessário para UI.