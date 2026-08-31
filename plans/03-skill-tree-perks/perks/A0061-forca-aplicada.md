# A0061 — Força Aplicada

## Estado de design

**APROVADA COM BOUNDARY.** Fundação MARTIAL ranqueada, não especialização de uma arma específica.

## Contrato final

- **Ranks:** 5; **custo:** 1 ponto/rank.
- **Gate:** `rpgskilltree:martial_000` adquirido; ponto inicial do ramo `martial_core`.
- **Efeito:** +2% de dano físico elegível por rank, máximo +10%, exclusivamente no mesmo `rootActionId` de uma ação MARTIAL direta do jogador.
- O bônus é uma fundação deliberada da árvore: compete com os pontos iniciais de crítico (A0062) e cadência (A0064) e abre os ramos de penetração, impacto e dano contextual. Não deve ser replicado em outro node genérico.

## Authority / hooks

- **Melee Epic Fight 21.17.3.1:** `DELIVER_DAMAGE_PRE` + `EpicFightDamageSource`, com `source.getDirectEntity()==player` e categoria provider-native reconhecida.
- **BOW/CROSSBOW:** somente projectile provenance server-side do pipeline canônico de A0043–A0054; owner isolado não basta.
- `ARCANE_BACKLASH`, hazards ambientais, companion-owned damage, summons e derived/ability hits não herdam A0061.

## Simply Swords

Simply Swords/Simply More/Cataclysm podem fornecer a arma do root, mas Implicits, Awakening, Runic Powers, Unique abilities, sockets/gems, execute, bleed, double-strike e demais resultados derivados continuam provider-owned. Um derived hit não recebe A0061 novamente sem prova de que é o mesmo root direto — nunca um novo root MARTIAL.

## Fail-closed e pendência

`P-A0061-01`: remover do bridge A0061–A0080 os fallbacks `rpgskilltree:hammers`, `rpgskilltree:maces` e `rpgskilltree:scythes`. A família melee deve vir de capability/classification provider-native ou mapping explícito versionado; namespace, nome, tooltip e tags paralelas não classificam MARTIAL.

## Testes obrigatórios

1. +2/4/6/8/10% somente uma vez por root direto elegível.
2. Melee e projectile físico canônico; derivados, companions, hazards e magia = neutro.
3. Simply provider-present: ability/Implicit/gem não cria segundo root nem segundo bônus.
4. Categoria desconhecida = fail-closed.

## Nove eixos

1. Dependências/gates: PASS — gateway MARTIAL.
2. Integração global: PASS com autoria direta e exclusões.
3. Qualidade/identidade: PASS — Ranked Passive de fundação com escolha de caminho A0061/A0062/A0064; não Notable/Capstone.
4. Topologia: PASS — ponto inicial `martial_core`.
5. Especializações: PASS — universal MARTIAL, não mod/classe.
6. PT-BR: PASS.
7. Registro: PASS no GitHub; sem nova escrita Notion por instrução do usuário.
8. NeoVitae: PASS — ausente.
9. Providers/modlist: PASS com Simply boundary; runtime ainda depende de `P-A0061-01`.