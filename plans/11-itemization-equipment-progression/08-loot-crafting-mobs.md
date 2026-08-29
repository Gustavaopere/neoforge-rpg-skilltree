# 11.08 — Craft, loot, drops, mobs, rewards e outputs externos

## Objetivo

Garantir que todos os caminhos relevantes de criação/entrada de equipamentos convergem para `ensureItemized` e que a identidade acompanha o mesmo item entre sistemas.

## Passo a passo

### A — Craft e smithing

- resultado novo de craft elegível recebe identidade ao ser materializado no servidor;
- smithing que representa upgrade do mesmo item preserva identidade;
- receita que realmente cria um novo equipamento usa nova identidade;
- preview de receita/menu nunca consome o roll definitivo.

### B — Loot containers

Itemizar stacks no pipeline autoritativo de loot antes da entrega ao jogador. Não depender de abrir tooltip para gerar.

### C — Mob equipment

- ao receber/spawnar com equipamento elegível, assegurar identidade;
- efeitos aplicáveis funcionam no mob;
- se o stack for dropado, manter os mesmos components/instanceId/rank/modifiers;
- não gerar nova arma no evento de drop para representar a antiga.

### D — Bosses e rewards

Boss/reward pode usar policy de rank/Poder distinta, mas continua no mesmo pipeline. Nenhum `boss itemization` paralelo.

### E — Trades, quests e máquinas

Criar adapters/hooks quando houver evento seguro. Para mods sem hook dedicado, usar reconciliação em fronteiras controladas, nunca scan global por tick.

Create, Productive Metalworks e outros produtores de equipamento entram como origens; categorias/capacidades específicas ficam nos adapters posteriores.

### F — Fronteiras de reconciliação

Exemplos aceitáveis:

- item entra em slot de jogador;
- equipamento é equipado;
- output é retirado de menu/máquina suportada;
- stack é gerado por loot/reward;
- entidade recebe equipamento.

Como `ensureItemized` é idempotente, múltiplas fronteiras não produzem reroll.

## Testes previstos

- crafting uma vez, sem itemizar preview;
- smithing preserva identidade;
- baú entrega já itemizado;
- mob spawn/equip/drop mantém o mesmo snapshot;
- reward/trade suportado;
- output modded desconhecido cai no fallback;
- nenhuma varredura global por tick.

## Acceptance

Os principais caminhos de aquisição convergem para a identidade única e o mesmo equipamento mantém seus dados do produtor/mob/baú até o jogador.
