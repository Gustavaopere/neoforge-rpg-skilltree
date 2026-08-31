# A0062 — Golpe Preciso

## Estado de design

**APROVADA COM BOUNDARY.** Fundação MARTIAL de crítico; ponto inicial alternativo de `martial_core`.

## Contrato final

- **Ranks:** 4; **custo:** 1/rank.
- **Gate:** `rpgskilltree:martial_000`; sem dependência de perk.
- **Efeito:** +2% de chance crítica por rank, máximo +8%, somado à chance da disciplina de arma dentro do **mesmo resolver crítico canônico**. Nunca cria uma segunda rolagem.
- O node é justificável como fundação porque escolhe o corredor crítico e conduz a A0063; não deve existir outro bônus global de crítico paralelo.

## Authority / hooks

- Resolver canônico RPG + `CriticalHitEvent`/root Epic Fight correlacionado para melee.
- BOW/CROSSBOW usam o mesmo resolver a partir do launch/projectile root canônico.
- Critical provider-native existente entra como input do único resolver; A0062 não rerrola nem converte derived hit em novo crítico.

## Simply Swords

Katana double damage, Warglaive double strike, Implicits, ability damage, gem powers e traits não abrem nova rolagem A0062. Arma Simply só participa quando a ação raiz possui classificação/provenance MARTIAL segura.

## Fail-closed

Herda `P-A0061-01` para classificação melee. Sem root direto e família/provenance segura, A0062 não participa da resolução.

## Testes obrigatórios

1. A0062 compõe uma vez com A0003/A0009/etc. no mesmo crítico.
2. Provider-critical verdadeiro continua um único crítico.
3. Derived Simply/companion/hazard não recebe nova rolagem.
4. Projectile root correlacionado mantém uma única decisão crítica para Multishot/fan-out conforme provenance.

## Nove eixos

1. Dependências/gates: PASS.
2. Integração global: PASS — resolver único.
3. Qualidade/identidade: PASS — entrada deliberada do corredor crítico, concorrente a força/cadência.
4. Topologia: PASS — ponto inicial `martial_core`.
5. Especializações: PASS — universal MARTIAL.
6. PT-BR: PASS.
7. Registro: GitHub canônico deste ciclo.
8. NeoVitae: PASS.
9. Providers/modlist: PASS com boundary Simply e provenance.