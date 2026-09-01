# A0112 — Auto-Manutenção

**Estado Chat 1:** DESIGN APROVADO EM FAIL-CLOSED TRANSITIVO.  
**Runtime atual:** `UNAVAILABLE_NODE`; cadeia A0111 → A0110/P-0036 indisponível e nenhum repair adapter canônico instalado.  
**Notion:** https://app.notion.com/p/3c569db9f0db81869a6ef7f89a40cb2d

## Identidade e posição

- Domínio/árvore: `ENGINEERING` / Principal — ENGINEERING.
- Ramo: Manutenção Técnica; camada 3; função: Notable.
- 3 ranks; 1 PP/rank; faixa Médio.
- Gate estrutural: A0111 ≥2 + Gateway ENGINEERING.

## Contrato congelado

Após **200 ticks** sem causar nem receber dano hostil elegível, A0112 pode executar no máximo um ciclo de manutenção bem-sucedido por jogador a cada **600 / 480 / 360 ticks** conforme o rank.

Candidatos são apenas equipamentos tecnológicos danificados, reparáveis e atualmente em mão/slot equipado/posição ativa exposta por adapter. Inventário armazenado é inelegível. Entre candidatos com recurso pagável, selecionar deterministicamente o menor `durability_remaining / durability_max`; empate: main hand → offhand → head → chest → legs → feet → id estável da posição adaptada.

## Transação e authority

O provider do item define **recurso, custo e quantidade reparada**. A transação é `quote/validate → debit nativo confirmado → repair`; falha de débito aborta tudo e não inicia cooldown. Não existe “repair resource” universal.

Oritech `1.2.11` e outras famílias tecnológicas entram apenas por adapter versionado que prove posição ativa, reparabilidade e débito/reparo atômicos. Protection Pixel `2.2.1` preserva manutenção própria e não é provider automático.

## Availability, lifecycle e anti-abuso

A0112 herda integralmente A0111→A0110/P-0036. Enquanto a cadeia não puder ser adquirida, A0112 é não comprável e allocation legado vale 0 PP. Família sem adapter continua inativa mesmo no futuro.

Cooldown é server-authoritative por `player_uuid`; logout/dimensão não encurta intervalo. Respec remove elegibilidade sem apagar cooldown já iniciado. Um ciclo bem-sucedido escolhe exatamente um item. Item quebrado, indestrutível, armazenado ou com custo não pagável não é candidato.

## Projetos próprios / provider → árvore

- RPG Skill Tree: pode possuir scheduler/cooldown/selection, mas não custo/reparo do provider.
- Volcanoes: recursos de pressão/manutenção permanecem próprios e não viram repair currency.
- Enshrouded/Black Arcana: não fornecem adapter de manutenção tecnológica.

## Nove eixos / 18 critérios

PASS no design: gates transitivos, um scheduler canônico, identidade mecânica própria, seleção determinística, custo real, no-free-repair, causalidade, dedup, lifecycle e fail-closed. Topologia ENGINEERING é coerente; Especializações N/A; Notion 10/10 auditado; NeoVitae ausente.

## Pendências para Chat 2

- `P-A0112-01` — availability transitiva A0111/A0110 e purchase fail-before-spend.
- `P-A0112-02` — state server-side: last hostile damage + next allowed tick, persistência/reconciliação.
- `P-A0112-03` — API de enumeração estável de posições ativas sem scan do inventário armazenado.
- `P-A0112-04` — interface transacional provider-native quote/debit/repair; sem adapter, família fail-closed.
- `P-A0112-05` — seleção determinística e cooldown somente após sucesso.

## Testes exigidos ao Chat 3

Node indisponível sem predecessor; 200-tick hostile window; ordem/tie determinísticos; item armazenado excluído; insufficient resource = zero repair/zero cooldown; debit-before-repair; unbreakable/broken excluded; relog/dimension/respec/rules reload; provider absent/present; multiplayer isolation; GameTests, build, JAR e dedicated-server smoke.