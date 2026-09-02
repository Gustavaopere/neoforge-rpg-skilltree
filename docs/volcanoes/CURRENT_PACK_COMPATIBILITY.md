# Volcanoes — compatibilidade corrente do artefato consolidado

> Autoridade operacional após a consolidação. Os planos históricos do antigo projeto Volcanoes estão arquivados em `docs/archive/volcanoes/` e não são mais uma fila de implementação ativa.

## Estado do artefato

Volcanoes é um subsistema nativo do único artefato/mod `rpgskilltree`. Não existe um segundo `@Mod` Volcanoes nem um segundo ciclo de bootstrap.

A paridade funcional é auditada contra o último snapshot standalone congelado:

- fonte standalone: `eaddc3232dfc600780769f4a5e7e45ff1e50181c`;
- `Volcanoes Functional Parity Audit #6`: **GREEN**;
- 597 arquivos funcionais auditados;
- 575 byte-idênticos;
- 22 adaptações consolidadas explicitamente classificadas;
- 0 arquivos faltando;
- 0 divergências funcionais não classificadas.

As adaptações permitidas são mantidas em `docs/volcanoes/provenance/functional-parity-exceptions.json`. Qualquer novo drift fora dessa lista falha o CI.

## Snapshot do modpack corrente

A reconciliação de 2026-08-30 possui 573 entradas top-level, incluindo NeoForge. Para as integrações Volcanoes relevantes neste fechamento:

| Provider/host | Versão corrente | Estado Volcanoes |
|---|---:|---|
| Create | `6.0.10` | compatibilidade preservada |
| Sable | `2.0.5` | gate exato preservado |
| Create Aeronautics | `1.3.2` | host corrente reconciliado; não é inventada API genérica de cabin seal |
| MineColonies | `1.1.1375-1.21.1-snapshot` | gate exato reconciliado; claim authority continua MineColonies |
| Cold Sweat | `2.4.2` | host térmico exato preservado |
| Create: Rock & Stone | `1.3.1-1.21.1-6` | worldgen nativo continua autoritativo |
| Rhino | `2101.2.8-build.91` | runtime corrente quando a integração scriptada é exercitada |

### KubeJS e RNS

A modlist corrente não contém KubeJS top-level. Por isso, o bridge Volcanoes → RNS é **opcional e fail-closed** no pack instalado: RNS pode permanecer presente e executar seu worldgen nativo sem exigir que Volcanoes materialize a projeção customizada.

O host opcional suportado é testado separadamente com:

- KubeJS `2101.7.2-build.374`;
- Rhino `2101.2.8-build.91`;
- Better Advanced Tooltips `2101.1.0-build.5`;
- Create RNS `1.3.1-1.21.1-6`.

`Volcanoes RNS Hydrothermal Acceptance #372` ficou **GREEN** com 47/47 GameTests. Isso prova o bridge quando o companion exato está instalado sem transformar KubeJS em dependência obrigatória do modpack corrente.

## Evidência de compatibilidade combinada

`Volcanoes Full Pack Compatibility Acceptance #364` ficou **GREEN** no stack combinado de hosts de integração. O workflow concluiu:

- instalação dos hosts exatos;
- GameTests do artefato consolidado;
- preparação de dedicated server;
- save/reload smoke do mundo persistido;
- upload de evidência.

O Full Pack é uma matriz combinada dos hosts que Volcanoes integra, não uma reprodução literal dos 573 mods do modpack. Hosts opcionais podem ser incluídos nessa matriz para provar coexistência sem se tornarem dependências obrigatórias do pack instalado.

`Volcanoes MineColonies Claim Acceptance #374` também ficou **GREEN** contra MineColonies `1.1.1375`.

## Regras de autoridade que permanecem válidas

- Volcanoes conserva autoridade sobre seus depósitos, atmosfera, pressão e runtime geológico.
- RNS conserva autoridade sobre seu worldgen nativo; a projeção Volcanoes é adicional, ownership-aware e fail-closed.
- MineColonies conserva autoridade sobre claim/protected-area data.
- Cold Sweat conserva autoridade sobre o estado térmico corporal quando a integração é usada.
- Sable/Aeronautics conservam autoridade sobre física/sublevels/veículos; Volcanoes não inventa um contrato de cabin sealing inexistente.
- Ausência ou versão não suportada de provider opcional desativa somente a integração dependente; não cria bônus substituto nem segunda mecânica.

## Gates de fechamento

O conjunto consolidado de Release Readiness exige, no mesmo SHA:

1. RPG Skill Tree CI;
2. Volcanoes Consolidation Contract;
3. Volcanoes Functional Parity Audit;
4. Cold Sweat Heat Acceptance;
5. Performance Hardening Acceptance;
6. MineColonies Claim Acceptance;
7. Create Sable Acceptance;
8. RNS Hydrothermal Acceptance;
9. Full Pack Compatibility Acceptance;
10. Third-Party Provenance Audit;
11. Worldgen Compatibility Matrix.

Paridade funcional faz parte do release gate e não pode ser tratada como auditoria opcional.
