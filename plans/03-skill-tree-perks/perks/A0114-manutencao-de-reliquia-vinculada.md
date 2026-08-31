# A0114 — Manutenção de Relíquia Vinculada

## Estado
**DESIGN APROVADO — FAIL-CLOSED integral.**

## Contrato
Keystone LOGISTICS↔Attunement Socket, 1 rank. Após ≥200 ticks fora de dano hostil, no máximo um ciclo global por jogador a cada 400 ticks. Só itens realmente attuned/vinculados, ativos/equipados, reparáveis e com custo nativo pagável podem ser candidatos; seleção pelo menor ratio de durabilidade e id estável da posição/vínculo; débito antes do reparo.

## Gates
A0112=3 + Gateway LOGISTICS + Attunement Socket transversal. A0112 herda A0111/A0110; além disso o Attunement Socket ainda não está integrado à main. Relics/Artifacts/Reliquified Artifacts não são `bound` por presença.

## Fallback
Sem cadeia de predecessores + binding query + repair adapter: compra desabilitada e allocation legado 0 PP. Nunca reparar gratuitamente.

## Chat 2
Não implementar heurística de vínculo. Preservar cooldown em logout/dimensão e respec.