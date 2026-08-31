# A0114 — Manutenção de Relíquia Vinculada

**Estado Chat 1:** DESIGN APROVADO EM FAIL-CLOSED INTEGRAL.  
**Runtime atual:** `UNAVAILABLE_NODE`; cadeia A0112→A0111→A0110/P-0036 indisponível e Attunement Socket não está integrado à `main`.  
**Notion:** https://app.notion.com/p/3c569db9f0db81208aa6c65056cc7d35

## Identidade e posição

- Domínio: `LOGISTICS`; árvore Principal — LOGISTICS ↔ Attunement Socket.
- Ramo: Relíquias e Equipamento Vinculado; camada 5; função: Keystone.
- 1 rank; 3 PP; faixa Transformativo.
- Gate: A0112 =3 + Gateway LOGISTICS + Attunement Socket estruturalmente disponível.

## Contrato congelado

Após **200 ticks** sem causar/receber dano hostil elegível, A0114 permite no máximo um ciclo global por jogador a cada **400 ticks**. Candidatos são somente itens atualmente **attuned/vinculados e ativos/equipados** que o Attunement Socket/provider exponha como reparáveis e cujo recurso nativo seja pagável.

Selecionar deterministically o menor `durability_remaining / durability_max`; empate por id estável da posição/vínculo. Consumir o recurso nativo integralmente **antes** do reparo. Exatamente um item pode receber manutenção por ciclo.

## Provider, authority e fronteiras

Attunement Socket é infraestrutura transversal planejada, não domínio de PP paralelo. Relics `0.12.8`, Artifacts `13.2.3` e Reliquified Artifacts `1.0.8` são apenas candidatos de conteúdo: presença/Curios slot não prova attunement, reparabilidade ou custo.

RPG Skill Tree pode possuir cooldown/selection; provider de attunement deve publicar vínculo/posição ativa; provider do item mantém repair/resource authority. Curios, se usado, só localiza equipamento e não inventa vínculo.

## Availability, lifecycle e fallback

Qualquer predecessor obrigatório indisponível OU Attunement Socket ausente torna A0114 não comprável e allocation legado 0 PP. Estado dinâmico de posse/vínculo/recurso é gate de ativação, não reconciliação de compra após a infraestrutura existir.

Cooldown é server-authoritative por jogador; relog/dimensão não encurta; respec remove elegibilidade sem apagar cooldown iniciado. Falha de resource debit aborta reparo/cooldown. Nunca reparar gratuitamente ou item armazenado/desvinculado.

## Projetos próprios / provider → árvore

- RPG Skill Tree: Attunement Socket só é hook quando live/canônico; documentação alpha não basta.
- Volcanoes: nenhum vínculo de relíquia pertinente.
- Enshrouded/Black Arcana: itens temáticos não são attuned por associação; nenhum bridge implícito.

## Nove eixos / 18 critérios

PASS no design com availability explícita: dependências/gates, pipeline único de manutenção, identity da keystone, topologia LOGISTICS, provider-native cost/repair, causalidade, dedup, lifecycle, no-free-resource e fail-closed. Especializações N/A; PT-BR/Notion PASS; NeoVitae ausente.

## Pendências para Chat 2

- `P-A0114-01` — unavailable transitivo pela cadeia A0112/A0111/A0110.
- `P-A0114-02` — Attunement Socket ausente = segundo blocker estrutural; não implementar heurística de vínculo.
- `P-A0114-03` — futuro query contract de vínculos/posições ativos com stable id.
- `P-A0114-04` — provider repair transaction debit-before-repair e deterministic selection.
- `P-A0114-05` — cooldown global persistente e lifecycle/respec/rules reload.

## Testes exigidos ao Chat 3

Purchase fail-before-spend/0 PP legado com predecessor ou Attunement ausentes; presença de Relics/Artifacts isoladamente não habilita; item desvinculado/armazenado/custo insuficiente não repara; selection deterministic; debit-before-repair; one cycle/400 ticks; relog/dimensão/respec; provider absent/present; multiplayer isolation; GameTests, build, JAR e dedicated-server smoke.