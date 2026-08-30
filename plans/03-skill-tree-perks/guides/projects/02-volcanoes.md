# Volcanoes — Dossiê Canônico para Perks

**Fonte editorial no Notion:** https://app.notion.com/p/3cc69db9f0db8178b64fe5400b66abc9

**Snapshot auditado:** `Gustavaopere/Volcanoes@1d0da7ae7f19e06f60390fdeb0835720e2e40f1b`

Stages 00–05 estão fechados no snapshot. Stage 06 possui integrações canônicas e uma frente RNS parcialmente fail-closed. Stage 07 Hardening permanece aberto.

## 1. Identidade e autoridade

Volcanoes é o provider ambiental/geológico do pack. Seu domínio não se limita à geração de vulcões: inclui geologia, depósitos, tectônica, terremotos, ciclo vulcânico, erupções, cinzas/piroclastos, geotermia, Atmosphere, respiração/gases/poluição e pressão.

Perks não devem recriar estados físicos paralelos nem assumir ownership de sistemas que o Volcanoes deliberadamente deixa em providers externos.

## 2. Foundation — IMPLEMENTADO E CANÔNICO

O Stage 00 estabelece contratos base, configuração, segurança de mundo e boundaries usados pelos demais estágios. Consumers devem preferir esses contracts em vez de classes internas.

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

O core Volcanoes **não** instala um segundo scanner de jogador. Prospecção visual permanece no provider adequado, especialmente RNS quando a integração é segura.

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

### Perks legítimas

- observação/previsão de atividade quando houver boundary real;
- resistência/mitigação contextual;
- exploração de geotermia;
- coleta/prospecção ligada a eventos discretos.

A perk não possui scheduler de erupção nem cria uma segunda fonte de hazard.

## 6. Atmosphere — IMPLEMENTADO E CANÔNICO

`AtmosphereState` é um vetor físico/químico composável, não um único número de “qualidade do ar”. O snapshot auditado inclui grandezas como:

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

## 8. Poluição e chuva ácida — IMPLEMENTADO E CANÔNICO

Volcanoes possui integração com Destroy para o domínio de poluição/acid rain. A bridge preserva a autoridade de cada lado; não se deve inventar feedback agregado ou segundo pollution engine apenas para “conectar” sistemas.

## 9. Pressão — IMPLEMENTADO E CANÔNICO

O Stage 05 fecha:

- atmospheric pressure;
- water pressure;
- sealed/protected volumes;
- equipment.

Pressão atmosférica é uma grandeza física distinta do vetor químico da Atmosphere, embora os sistemas se componham. Pressão hidrostática varia com profundidade. Protected/enclosed environment usa SPI bounded e fail-closed: não presume sala/cabine selada sem prova.

Equipamentos possuem capacidades modulares de proteção e o runtime evita consumo concorrente entre Pressure e Atmosphere.

O contrato de Stage 05 usa Curios `9.5.1+1.21.1` no snapshot auditado.

### Perks legítimas

Tolerância, eficiência ou diagnóstico só devem ser acoplados quando houver extension point real. Não duplicar equipment provider nem conceder proteção grátis se uma bridge falhar.

## 10. Stage 06 — integrações

### Worldgen — IMPLEMENTADO E CANÔNICO

- Terralith volcanic crater/peaks funcionam como hints positivos e opcionais;
- Tectonic continua apenas terrain shaping;
- BWG entra por regras genéricas de suitability;
- nenhum desses mods se torna hard dependency desnecessária.

### Create / Sable / Aeronautics — IMPLEMENTADO E CANÔNICO

Versões verificadas no plano fechado:

- Sable `2.0.5`;
- Aeronautics `1.3.1`.

`SablePressureIntegration` usa API Sable verificada para projetar posições de sublevel no level físico e consultar a pressão canônica.

Aeronautics não expõe, nessa versão, um contrato genérico confiável para “cabine selada/leak/flood”. Por isso Volcanoes deliberadamente **não inventa cabine protegida** e cai para a atmosfera/pressão externa quando não consegue provar proteção.

Create Diving Helmet + Backtank participam da oferta de oxigênio e o ar é consumido pelo adapter uma única vez.

### Cold Sweat — IMPLEMENTADO E CANÔNICO

Cold Sweat `2.4.2` continua autoridade da temperatura corporal. Volcanoes injeta calor ambiental bounded proveniente de fontes como lava, pyroclastics e geothermal. Perks não devem criar segundo body-temperature state.

### Destroy — IMPLEMENTADO E CANÔNICO

Bridge de poluição/acid rain fechada com routing/retry safety. Destroy mantém sua autoridade de domínio.

### MineColonies — IMPLEMENTADO E CANÔNICO

Claims alimentam `ProtectedAreaService` com comportamento fail-closed. Perks, rituais ou world effects não podem contornar protected areas.

### RNS — IMPLEMENTADO PARCIALMENTE / FAIL-CLOSED NO WORLDGEN FÍSICO

A integração resolve identidade hidrotermal de metais apenas quando a causalidade é comprovada:

- shield/fissure → iron;
- stratovolcano → copper;
- caldera → gold;
- tectonic-only → generic.

RNS continua autoridade de prospecção e de worldgen físico de metais enquanto Volcanoes não demonstrar placement determinístico próprio de Cu/Fe/Au. A lifecycle bridge de ownership permanece desabilitada no estado auditado. Tin/nickel/zinc/silver permanecem RNS-owned.

### Regra para perks

Não listar Volcanoes como autoridade de worldgen mineral RNS além do que a integração realmente prova.

## 11. Hardening — PLANEJADO / ABERTO

O Stage 07 cobre compatibility matrix, performance, world-upgrade/persistence e release checklist. Não é provider de perk.

## 12. Anti-abuso e deduplicação

1. Nenhum hazard ambiental contínuo gera Mastery por tick.
2. Descobertas usam UUID/milestone persistente ou identidade equivalente.
3. Reentrada/reload/rebuild não pode conceder a mesma progressão novamente.
4. Equipment/filter consumption acontece uma única vez pelo pipeline canônico.
5. Não inferir cabine selada, proteção, concentração de gás ou mineral ownership por aparência/nome.
6. Bridge opcional ausente desativa apenas a parcela dependente e nunca concede proteção gratuita.

## 13. Fontes principais

- `plans/STATUS.md`
- `plans/00-foundation/`
- `plans/01-geology/`
- `plans/02-tectonics/`
- `plans/03-volcanoes/`
- `plans/04-atmosphere/`
- `plans/05-pressure/`
- `plans/06-integrations/`
- `plans/07-hardening/`
