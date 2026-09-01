# A0111 — Conservação de Equipamento II

**Estado Chat 1:** DESIGN APROVADO EM FAIL-CLOSED TRANSITIVO.  
**Runtime atual:** `UNAVAILABLE_NODE`; herda A0110/P-0036.  
**Notion:** https://app.notion.com/p/3c569db9f0db81db8b71d62cbf683e22

## Identidade e posição

- Domínio: `ENGINEERING`; árvore Principal — ENGINEERING ↔ SURVIVAL.
- Ramo: Manutenção Técnica; camada 2; função: Ponte.
- 5 ranks; 1 PP/rank; faixa Médio.
- Gate: Gateway ENGINEERING + A0110 ≥ 2 ranks efetivamente válidos.

## Contrato congelado

Cada rank concede **1,5%**, até **7,5%**, de chance de um uso legítimo de equipamento tecnológico portátil elegível que chegaria a um decremento final confirmado de exatamente 1 ponto de durabilidade não consumir esse ponto.

A0111 conserva **durabilidade**, não energia. FE, combustível, vapor/pressão, munição, fluidos e demais recursos provider-owned continuam integralmente cobrados.

## Boundary, providers e authority

Owner de durabilidade: Minecraft/NeoForge e o provider concreto do item. Versões de referência do design: NeoForge `21.1.248`, Oritech `1.2.11`, Protection Pixel `2.2.1`.

A ordem obrigatória é a mesma de A0110: regras nativas/Unbreaking/provider → decremento final = 1 → no máximo uma rolagem server-side A0111 → eventual cancelamento pré-write. Oritech/Protection Pixel não são elegíveis por presença do mod: cada família precisa provar item realmente durável e adapter versionado que alcance esse boundary.

## Availability, fallback e dedup

A0110 está indisponível porque P-0036 não possui seam pós-prevenção/pré-decremento. Logo A0111 é não comprável e allocation legado vale 0 PP para gates. Mesmo após A0110 existir, uma família sem adapter seguro continua fail-closed individualmente.

Proibido: converter FE/combustível em “durability equivalent”, repair/refund posterior, polling de damage value, uma rolagem por callback duplicado ou bypass de Unbreaking.

Identidade de dedup: um uso causal que produz um único decremento final elegível. Se o provider já zerar o desgaste, A0111 não rola.

## Projetos próprios / provider → árvore

- RPG Skill Tree: ProgressionService representa availability; não inventa o seam de durabilidade.
- Volcanoes nativo: equipamentos de pressão/respiração mantêm seus próprios recursos; não viram A0111 por estarem no mesmo JAR.
- Enshrouded e Black Arcana: nenhum seam de durabilidade pertinente.

## Nove eixos / 18 critérios

PASS em design para dependências/gates, integração global, identidade, topologia, PT-BR, Notion e cobertura provider. Especialização é N/A. NeoVitae ausente. Provider-native first, custo real, causalidade, dedup, no-free-resource e fail-closed estão explícitos; o efeito existe somente quando o decremento final real puder ser interceptado.

## Pendências para Chat 2

- `P-A0111-01` — materializar availability transitiva A0110→A0111 e purchase fail-before-spend.
- `P-A0111-02` — preservar allocation legado como 0 PP enquanto gate indisponível.
- `P-A0111-03` — não criar adapter Oritech/Protection Pixel sem provar item durável + ordem pós-regras nativas/pré-write.
- `P-A0111-04` — se P-0036 fechar, implementar one-use/one-roll sem afetar FE/fuel/pressure/ammo.

## Testes exigidos ao Chat 3

Estado atual: node indisponível, sem gasto/rank fantasma/PP de gate; predecessor indisponível propaga corretamente. Futuro provider-present: Unbreaking/provider prevention antes da perk, FE-only/unbreakable inelegíveis, uma rolagem por decremento 1, callbacks duplicados deduplicados, lifecycle/rules reload/respec/multiplayer, NeoForge GameTests, build, JAR e dedicated-server smoke.