# A0113 — Reforço de Campo

**Estado Chat 1:** DESIGN APROVADO EM FAIL-CLOSED TRANSITIVO APÓS HARDENING ANTI-CLONE V2.  
**Runtime atual:** `UNAVAILABLE_NODE`; A0110/P-0036 indisponível e `rpgskilltree:tool_instance_id`/`rpgskilltree:tool_lease_nonce`/`ToolIdentityLedger` ainda não existem na `main`.  
**Notion:** https://app.notion.com/p/3c569db9f0db81aa930cc619c3a08057

## Identidade e posição

- Domínio/árvore: `SURVIVAL` / Principal — SURVIVAL.
- Ramo: Manutenção em Campo; camada 2; função: Notable.
- 3 ranks; 1 PP/rank; faixa Médio.
- Gate estrutural: A0110 ≥2 + Gateway SURVIVAL.

## Contrato congelado

Na primeira ação válida, uma ferramenta manual elegível, não empilhável e realmente durável recebe no servidor dois DataComponents:

- `rpgskilltree:tool_instance_id` — identidade lógica da linhagem;
- `rpgskilltree:tool_lease_nonce` — lease single-writer rotativa dessa linhagem.

Após **12 coletas legítimas** atribuídas à mesma linhagem lógica `owner_uuid + tool_instance_id` e validadas pela lease corrente, abre `Reforço Pronto` por **600 ticks**. O próximo reparo manual compatível apresentado pela **lease vigente** durante a janela consome integralmente o material/recurso nativo e aumenta a durabilidade efetivamente restaurada em **+15% / +25% / +35%** conforme o rank. O reparo consome a janela inteira.

## Authority, causalidade e anti-clone

RPG Skill Tree pode ser owner da identidade/lease e do contador/janela; o provider da ferramenta/reparo continua owner do custo e do reparo base.

Coleta precisa ser causal ao jogador e natural/legítima. Bloco colocado, automação, AFK/rebuild e producer duplicado não contam. Registry id, nome, slot ou NBT parcial não identificam “mesma ferramenta”.

O `ToolIdentityLedger` mantém `owner_uuid + tool_instance_id + current_nonce`. Antes de qualquer ação A0113 elegível:

1. validar owner e `tool_lease_nonce` contra o ledger;
2. se owner e nonce forem válidos, a ação pode continuar a linhagem;
3. após a ação A0113 ser aceita, rotacionar atomicamente a nonce e escrever a nova nonce **somente no stack atuante**;
4. qualquer cópia que posteriormente apresente a nonce anterior está stale: recebe novo `tool_instance_id` + nova nonce e começa A0113 com contador 0 e sem `Reforço Pronto`;
5. owner mismatch aplica a mesma reidentificação/reset;
6. progresso/janela da linhagem anterior nunca são copiados, fundidos ou transferidos à nova identidade.

O contrato **não tenta identificar qual stack físico era o “original”**. Se um item for clonado, no máximo uma cópia pode continuar a linhagem prévia: a primeira ação A0113 válida que apresentar a lease vigente ganha a próxima lease; as demais cópias ficam stale. Isso elimina dupla continuidade mesmo quando as cópias não estão simultaneamente no mesmo inventário.

## Availability e fallback

A0113 herda A0110/P-0036: enquanto o predecessor não for adquirível, A0113 é não comprável e allocation legado vale 0 PP. Mesmo futuramente, família sem identidade/lease persistente, evento causal de coleta ou reparo nativo interceptável permanece inativa.

O bônus aumenta somente **quantidade realmente restaurada**; nunca reduz o custo material. Falha de débito/reparo não consome janela. Uma lease stale nunca pode consumir a janela da linhagem válida.

## Projetos próprios / provider → árvore

- RPG Skill Tree: owner permitido de `tool_instance_id`, `tool_lease_nonce`, `ToolIdentityLedger`, ledger de 12 e janela.
- Volcanoes: ferramentas/equipamentos não entram por associação temática; pipelines de recurso permanecem próprios.
- Enshrouded/Black Arcana: nenhuma capability relevante para identidade/reparo desta perk.

## Nove eixos / 18 critérios

PASS no design: gate transitivo, identidade lógica forte com lease single-writer, provider-native repair, anti-clone/anti-rebuild, causalidade, consumo real, dedup e fail-closed. Topologia SURVIVAL/maintenance coerente; Especializações N/A; Notion corrigido e re-fetched após review; NeoVitae ausente.

## Pendências para Chat 2

- `P-A0113-01` — materializar unavailable transitivo de A0110.
- `P-A0113-02` — criar DataComponents/registry `tool_instance_id` + `tool_lease_nonce` e `ToolIdentityLedger` server-side persistente; owner/nonce mismatch reidentifica/reset sem copiar progresso.
- `P-A0113-03` — tornar rotação da nonce atômica com a aceitação da ação A0113; só o stack atuante recebe a nova lease.
- `P-A0113-04` — producer de coleta legítima deduplicado por ação/bloco e anti-rebuild.
- `P-A0113-05` — ledger `owner_uuid + tool_instance_id`, 12 ações e janela 600 ticks, cleanup bounded; nunca copiar janela/progresso ao novo id.
- `P-A0113-06` — interceptar apenas reparo nativo compatível; validar lease, debitar integralmente antes do bônus e commitar somente após reparo confirmado.

## Testes exigidos ao Chat 3

Availability sem A0110; identidade/nonce persistentes; clone com mesma id+nonce; primeira cópia a agir rotaciona lease e a segunda fica stale; stale copy é reidentificada com progresso 0/sem janela; owner mismatch reidentifica/reset; clones não simultaneamente carregados não compartilham continuidade após rotação; 11/12/13 coletas; placed-block/automation exclusions; expiração da janela; lease vigente obrigatória no reparo; material integral; failure rollback; um reparo/uma janela; respec/reload/logout/multiplayer; provider absent/present; GameTests, build, JAR e dedicated-server smoke.
