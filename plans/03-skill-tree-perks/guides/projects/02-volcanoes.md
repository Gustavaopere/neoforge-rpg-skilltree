# Volcanoes — Dossiê Canônico para Perks

**Fonte editorial no Notion:** https://app.notion.com/p/3cc69db9f0db8178b64fe5400b66abc9

**Proveniência canônica do código consolidado:** `Gustavaopere/Volcanoes@eaddc3232dfc600780769f4a5e7e45ff1e50181c`.

**Consolidação no runtime atual:** PR `Gustavaopere/neoforge-rpg-skilltree#308`, merge `f613dac5a15b26c7a92e07a9d9cb537c2412ddf2`.

**Local operacional atual:** o Volcanoes não é mais um segundo mod distribuído. Ele é um subsistema nativo do único mod/JAR `rpgskilltree`; seus planos históricos fechados estão arquivados em `docs/archive/volcanoes/`, o runtime permanece sob o namespace Java `dev.gustavopere.volcanoes` dentro deste repositório e o boundary RPG-facing é exposto por serviços nativos, incluindo `NativeVolcanoesServices`.

Stages 00–07 estão fechados no snapshot fonte consolidado. A integração RNS está fechada como coexistência segura, sem transferência indevida da autoridade do worldgen nativo do RNS.

## 1. Identidade e autoridade

Volcanoes é o provider ambiental/geológico do pack. Seu domínio não se limita à geração de vulcões: inclui geologia, depósitos, tectônica, terremotos, ciclo vulcânico, erupções, cinzas/piroclastos, geotermia, Atmosphere, respiração/gases/poluição e pressão.

A consolidação de repositório não altera essa authority. `rpgskilltree` é o container/distribuição; Volcanoes continua autoridade dos estados físicos e serviços ambientais/geológicos que implementa. Perks não devem recriar estados físicos paralelos nem assumir ownership de sistemas que Volcanoes deliberadamente deixa em providers externos.

Identificadores persistentes e de recursos `volcanoes:*` foram preservados na consolidação quando necessários para compatibilidade de mundos, datapacks, SavedData, registries, rede e integrações.

## 2. Foundation — IMPLEMENTADO E CANÔNICO

O Stage 00 estabelece contratos base, configuração, segurança de mundo e boundaries usados pelos demais estágios. Consumers devem preferir esses contracts em vez de classes internas.

A distribuição consolidada possui apenas o entrypoint de `rpgskilltree`; Volcanoes é inicializado internamente por `RpgSkillTreeMod`, não por um segundo `@Mod("volcanoes")`.

## 3. Geologia — IMPLEMENTADO E CANÔNICO

O Stage 01 fecha rock profiles, strata e resource discovery.

`GeologicalDeposit` representa depósitos com:

- resource tag;
- posição central;
- raio;
- riqueza;
- origem geológica;
- UUID persistente.

Origens canônicas incluem `MAGMATIC`, `HYDROTHERMAL`, `SEDIMENTARY` e `GENERIC`.

`DepositRegistry` é SavedData por level. Repetir o mesmo UUID com o mesmo conteúdo é idempotente; conflito de conteúdo para a mesma identidade falha fechado.

`GeologicalDepositSource` é a SPI read-only para consumers, com consulta integral e `nearby(BlockPos, radius)` bounded.

O core Volcanoes não instala um segundo scanner de jogador. Prospecção visual permanece no provider adequado, especialmente RNS quando a integração é segura.

### Perks legítimas

- leitura/interpretação geológica;
- prospecção baseada em fonte real;
- marcos discretos de descoberta;
- eficiência contextual que não altera o ownership do depósito.

Nunca gerar Mastery por permanecer sobre um depósito ou consultar repetidamente o mesmo UUID.

## 4. Tectônica — IMPLEMENTADO E CANÔNICO

O Stage 02 fecha:

- plate field;
- plate boundaries/stress;
- safe earthquakes.

Volcanoes conserva autoridade de suas semânticas de placa/estresse. `Tectonic` é terrain shaping; sua presença não significa que fornece plate data ao Volcanoes.

### Perks legítimas

- interpretação/detecção tectônica por hook real;
- resposta/mitigação de evento sísmico;
- milestone causal único de descoberta/sobrevivência quando deduplicável.

Não conceder XP/Mastery a cada tick de tremor.

## 5. Vulcanismo — IMPLEMENTADO E CANÔNICO

O Stage 03 fecha:

1. volcano sites;
2. magma lifecycle;
3. lava;
4. eruptions;
5. ash/pyroclastics;
6. geothermal/hot springs.

O ciclo de erupção é server-owned e bounded. Cinzas/piroclastos e calor se integram aos sistemas ambientais em vez de criarem pipelines concorrentes. Geothermal lifecycle alimenta o domínio ambiental por bridges canônicas.

O estado final inclui worldgen físico bounded e determinístico para corpos hidrotermais de Cu/Fe/Au quando a causalidade vulcânica é comprovada, com prova explícita de realização física, rollback/prevalidation e recovery testados. Isso não autoriza perks a produzir minério nem a assumir o pipeline de worldgen.

### Perks legítimas

- observação/previsão de atividade quando houver boundary real;
- resistência/mitigação contextual;
- exploração de geotermia;
- coleta/prospecção ligada a eventos discretos.

A perk não possui scheduler de erupção nem cria uma segunda fonte de hazard.

## 6. Atmosphere — IMPLEMENTADO E CANÔNICO

`AtmosphereState` é um vetor físico/químico composável, não um único número de “qualidade do ar”. O estado canônico inclui grandezas como:

- pressão total/baseline;
- fração de O₂;
- CO₂;
- SO₂/gases ácidos;
- gases tóxicos genéricos;
- particulados;
- fumaça/smog;
- umidade;
- modificador térmico.

O estado é imutável/normalizado e deriva pressão parcial de oxigênio. Fontes locais são indexadas; sampling é bounded, sem scan global. Difusão/decay usa cadência e budgets explícitos. O snapshot de cliente é comprimido e player-relevant.

### Perks legítimas

Perks podem consultar/alterar resposta do jogador a canais ambientais apenas pelos serviços reais. Não colapsar gases diferentes em um bônus genérico se a identidade da perk depende de um canal específico.

## 7. Respiração e gases vulcânicos — IMPLEMENTADO E CANÔNICO

Respiração, volcanic gases e seus efeitos pertencem ao Stage 04. Proteção/consumo deve passar pelo pipeline canônico de respiração/equipment/protection. Uma perk não deve consumir filtro, ar ou durabilidade pela segunda vez.

Create Diving Helmet + Backtank participam da oferta de oxigênio através do adapter canônico e o ar é debitado uma única vez.

## 8. Poluição e chuva ácida — IMPLEMENTADO E CANÔNICO

Volcanoes possui integração canônica com Destroy para o domínio de poluição/acid rain, incluindo routing e retry safety. A bridge preserva a autoridade de cada lado; não se deve inventar feedback agregado ou segundo pollution engine apenas para “conectar” sistemas.

## 9. Pressão — IMPLEMENTADO E CANÔNICO

O Stage 05 fecha:

- atmospheric pressure;
- water pressure;
- sealed/protected volumes;
- equipment.

Pressão atmosférica é uma grandeza física distinta do vetor químico da Atmosphere, embora os sistemas se componham. Pressão hidrostática varia com profundidade. Protected/enclosed environment usa SPI bounded e fail-closed: não presume sala/cabine selada sem prova.

Equipamentos possuem capacidades modulares de proteção e o runtime evita consumo concorrente entre Pressure e Atmosphere.

O contrato consolidado preserva a integração Curios `9.5.1+1.21.1` auditada no Stage 05.

### Perks legítimas

Tolerância, eficiência ou diagnóstico só devem ser acoplados quando houver extension point real. Não duplicar equipment provider nem conceder proteção grátis se uma bridge falhar.

## 10. Stage 06 — Integrações — IMPLEMENTADO E CANÔNICO

Todos os seis planos de integração estão fechados no estado fonte consolidado.

### Worldgen — IMPLEMENTADO E CANÔNICO

- Terralith volcanic crater/peaks funcionam como hints positivos e opcionais;
- Tectonic continua apenas terrain shaping;
- BWG entra por regras genéricas de suitability;
- nenhum desses mods se torna hard dependency desnecessária.

A matriz de worldgen WG-00–WG-07 faz parte dos gates canônicos de aceitação.

### Create / Sable / Aeronautics — IMPLEMENTADO E CANÔNICO

Versões verificadas no contrato corrente:

- Sable `2.0.5`;
- Aeronautics `1.3.2`.

`SablePressureIntegration` usa API Sable verificada para projetar posições de sublevel no level físico e consultar a pressão canônica.

Aeronautics não expõe, nessa versão, um contrato genérico confiável para “cabine selada/leak/flood”. Por isso Volcanoes deliberadamente não inventa cabine protegida e cai para a atmosfera/pressão externa quando não consegue provar proteção.

### Cold Sweat — IMPLEMENTADO E CANÔNICO

Cold Sweat `2.4.2` continua autoridade da temperatura corporal. Volcanoes injeta calor ambiental bounded proveniente de fontes como lava, pyroclastics e geothermal. Perks não devem criar segundo body-temperature state.

### Destroy — IMPLEMENTADO E CANÔNICO

Bridge de poluição/acid rain fechada com routing/retry safety. Destroy mantém sua autoridade de domínio.

### MineColonies — IMPLEMENTADO E CANÔNICO

A compatibilidade corrente foi validada contra MineColonies `1.1.1375-1.21.1-snapshot`. Claims alimentam `ProtectedAreaService` com comportamento fail-closed. Perks, rituais ou world effects não podem contornar protected areas.

### RNS — IMPLEMENTADO E CANÔNICO COMO COEXISTÊNCIA SEGURA

O estado final fecha a integração sem transferir de forma ampla a autoridade do worldgen metálico do RNS.

Volcanoes:

- produz fisicamente apenas seus corpos hidrotermais bounded/determinísticos de Cu/Fe/Au quando há causalidade vulcânica provada;
- mantém as identidades Stage 01 determinísticas: shield/fissure → iron, stratovolcano → copper, caldera → gold; tectonic-only → generic;
- projeta para o RNS somente depósitos físicos já autoritativos do Volcanoes, como custom/scannable locations;
- persiste marcador de ownership/proveniência suficiente para não adotar nem remover, após restart, um registro estrangeiro de mesmo valor/posição;
- preserva fail-closed diante de colisão, conteúdo divergente ou ausência de prova física.

RNS:

- continua autoridade de prospecção;
- mantém seu native deposit worldgen habilitado para Cu/Fe/Au/Sn/Ni/Zn/Ag;
- conserva Sn/Ni/Zn/Ag inteiramente RNS-owned.

Não existe autorização para uma perk escrever no lifecycle/ownership de worldgen de nenhum dos dois providers.

### Regra para perks

Não tratar “integração RNS fechada” como transferência global de authority. Perks podem consumir apenas boundaries read-only/diagnósticos/discovery aprovados; não podem invocar o produtor físico, criar depósitos, mutar `LevelDepositData`, reescrever ownership ou duplicar prospecção.

## 11. Stage 07 — Hardening — IMPLEMENTADO E CANÔNICO

Stage 07 está fechado no snapshot fonte `eaddc3232dfc600780769f4a5e7e45ff1e50181c` e seus artefatos foram consolidados no RPG Skill Tree.

O fechamento cobre:

- matriz completa de compatibilidade/optional hosts;
- hardening e profiling de performance com budgets bounded;
- world-upgrade/persistence, incluindo políticas fail-closed para schemas futuros/corruptos e comando administrativo explícito de upgrade;
- release readiness agregada e executável;
- auditoria de licenças/proveniência de terceiros.

Esses gates são evidência de confiabilidade do provider; não são capacidades de perk nem justificam bônus próprios.

## 12. Consolidação — IMPLEMENTADO E CANÔNICO

A PR #308 consolidou o snapshot fonte final do Volcanoes no repositório do RPG Skill Tree e preservou o contrato de distribuição de um único mod/JAR.

Regras pós-consolidação:

1. Para runtime, código, testes, documentação e CI do Volcanoes, a fonte operacional é `Gustavaopere/neoforge-rpg-skilltree`.
2. `docs/archive/volcanoes/STATUS.md` preserva o status histórico fechado do subsistema; a documentação operacional corrente vive em `docs/volcanoes/`.
3. O repositório standalone `Gustavaopere/Volcanoes@eaddc323...` é proveniência histórica do import enquanto permanecer existente; ele não deve ser usado como autoridade mais nova que a `main` consolidada.
4. Mudanças futuras de capability do Volcanoes devem ser detectadas no repositório unificado, com atenção aos paths `docs/archive/volcanoes/**`, `src/main/java/dev/gustavopere/volcanoes/**`, `src/main/java/dev/gustavopere/rpgskilltree/runtime/volcanoes/**`, recursos `volcanoes:*`, workflows/scripts/docs do Volcanoes e integrações que consumam esses boundaries.
5. Avanço de `main` causado apenas por outro subsistema do RPG Skill Tree não deve ser classificado automaticamente como delta de capability do Volcanoes; o diff pertinente precisa tocar a superfície acima ou alterar um contrato compartilhado consumido pelo Volcanoes.

## 13. Anti-abuso e deduplicação

1. Nenhum hazard ambiental contínuo gera Mastery por tick.
2. Descobertas usam UUID/milestone persistente ou identidade equivalente.
3. Reentrada/reload/rebuild não pode conceder a mesma progressão novamente.
4. Equipment/filter consumption acontece uma única vez pelo pipeline canônico.
5. Não inferir cabine selada, proteção, concentração de gás ou mineral ownership por aparência/nome.
6. Bridge opcional ausente desativa apenas a parcela dependente e nunca concede proteção gratuita.
7. Consolidação de módulo não cria um segundo pipeline: RPG-facing consumers devem delegar aos serviços nativos do Volcanoes, não duplicar a simulação.

## 14. Fontes principais atuais

- `docs/archive/volcanoes/STATUS.md`
- `docs/archive/volcanoes/00-foundation/`
- `docs/archive/volcanoes/01-geology/`
- `docs/archive/volcanoes/02-tectonics/`
- `docs/archive/volcanoes/03-volcanoes/`
- `docs/archive/volcanoes/04-atmosphere/`
- `docs/archive/volcanoes/05-pressure/`
- `docs/archive/volcanoes/06-integrations/`
- `docs/archive/volcanoes/07-hardening/`
- `src/main/java/dev/gustavopere/volcanoes/`
- `src/main/java/dev/gustavopere/rpgskilltree/runtime/volcanoes/`
- `docs/volcanoes/`
- `.github/workflows/volcanoes-*.yml`
- `scripts/verify-volcanoes-consolidation.py`
- `scripts/verify-volcanoes-release-readiness.py`
