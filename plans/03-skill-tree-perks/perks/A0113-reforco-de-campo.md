# A0113 — Reforço de Campo

**Estado Chat 1:** DESIGN APROVADO EM FAIL-CLOSED TRANSITIVO APÓS HARDENING ANTI-CLONE.  
**Runtime atual:** `UNAVAILABLE_NODE`; A0110/P-0036 indisponível e `rpgskilltree:tool_instance_id`/`ToolIdentityLedger` ainda não existem na `main`.  
**Notion:** https://app.notion.com/p/3c569db9f0db81aa930cc619c3a08057

## Identidade e posição

- Domínio/árvore: `SURVIVAL` / Principal — SURVIVAL.
- Ramo: Manutenção em Campo; camada 2; função: Notable.
- 3 ranks; 1 PP/rank; faixa Médio.
- Gate estrutural: A0110 ≥2 + Gateway SURVIVAL.

## Contrato congelado

Na primeira ação válida, uma ferramenta manual elegível, não empilhável e realmente durável recebe no servidor um `DataComponent` de identidade única `rpgskilltree:tool_instance_id`.

Após **12 coletas legítimas** atribuídas ao mesmo `player_uuid + tool_instance_id`, abre `Reforço Pronto` por **600 ticks**. O próximo reparo manual compatível da **mesma instância** durante a janela consome integralmente o material/recurso nativo e aumenta a durabilidade efetivamente restaurada em **+15% / +25% / +35%** conforme o rank. O reparo consome a janela inteira.

## Authority, causalidade e anti-clone

RPG Skill Tree pode ser owner da identidade da instância e do contador/janela; o provider da ferramenta/reparo continua owner do custo e do reparo base.

Coleta precisa ser causal ao jogador e natural/legítima. Bloco colocado, automação, AFK/rebuild e producer duplicado não contam. Registry id, nome, slot ou NBT parcial não identificam “mesma ferramenta”.

A unicidade é policiada por um `ToolIdentityLedger` server-side antes de qualquer ganho de A0113:

1. se o mesmo jogador possuir duas cópias carregadas com o mesmo `tool_instance_id`, a **cópia atuante** recebe um novo UUID antes de contar a coleta;
2. se a identidade estiver registrada para outro owner e a cópia atuante for usada por um jogador diferente, aplicar a mesma reidentificação;
3. a nova identidade começa A0113 com contador 0 e sem `Reforço Pronto`;
4. progresso/janela nunca são copiados, fundidos ou transferidos para o novo id;
5. a cópia original mantém seu próprio ledger intacto; somente a identidade conflitante atuante é reidentificada/resetada.

Conflito de identidade nunca pode compartilhar ou duplicar progresso, janela ou consumo de reparo.

## Availability e fallback

A0113 herda A0110/P-0036: enquanto o predecessor não for adquirível, A0113 é não comprável e allocation legado vale 0 PP. Mesmo futuramente, família sem identidade persistente/única, evento causal de coleta ou reparo nativo interceptável permanece inativa.

O bônus aumenta somente **quantidade realmente restaurada**; nunca reduz o custo material. Falha de débito/reparo não consome janela.

## Projetos próprios / provider → árvore

- RPG Skill Tree: owner permitido de `tool_instance_id`, `ToolIdentityLedger`, ledger de 12 e janela.
- Volcanoes: ferramentas/equipamentos não entram por associação temática; pipelines de recurso permanecem próprios.
- Enshrouded/Black Arcana: nenhuma capability relevante para identidade/reparo desta perk.

## Nove eixos / 18 critérios

PASS no design: gate transitivo, identidade forte, provider-native repair, anti-clone/anti-rebuild, causalidade, consumo real, dedup e fail-closed. Topologia SURVIVAL/maintenance coerente; Especializações N/A; Notion corrigido e re-fetched; NeoVitae ausente.

## Pendências para Chat 2

- `P-A0113-01` — materializar unavailable transitivo de A0110.
- `P-A0113-02` — criar DataComponent/registry `tool_instance_id` + `ToolIdentityLedger`, com geração server-side, persistência e unicidade; duplicação/owner mismatch reidentifica a cópia atuante e zera somente o progresso A0113 dessa nova identidade.
- `P-A0113-03` — producer de coleta legítima deduplicado por ação/bloco e anti-rebuild.
- `P-A0113-04` — ledger `player_uuid + tool_instance_id`, 12 ações e janela 600 ticks, cleanup bounded; nunca copiar janela/progresso ao novo id.
- `P-A0113-05` — interceptar apenas reparo nativo compatível; débito integral antes do bônus e commit somente após reparo confirmado.

## Testes exigidos ao Chat 3

Availability sem A0110; identidade persistente; duas cópias carregadas com mesmo id reidentificam apenas a atuante; owner transfer/reuse reidentifica a atuante; novo id inicia 0/sem janela; original preserva seu ledger; clone nunca duplica progresso/janela; 11/12/13 coletas; placed-block/automation exclusions; expiração da janela; mesma instância obrigatória; material integral; failure rollback; um reparo/uma janela; respec/reload/logout/multiplayer; provider absent/present; GameTests, build, JAR e dedicated-server smoke.
