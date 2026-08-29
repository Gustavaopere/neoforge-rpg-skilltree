# 11.01 — Domínio, invariantes e autoridade

## Objetivo

Congelar o contrato conceitual antes de escrever runtime: o que é equipamento itemizável, quais dados são permanentes, quem pode mutá-los e como o Stage 11 se relaciona com sistemas existentes.

## Passo a passo

### A — Definir vocabulário

- [ ] `ItemizationIdentity`: identidade persistente da instância.
- [ ] `ItemRank`: Comum, Incomum, Raro, Épico, Lendário, Mítico, Único.
- [ ] `ItemPower`: escala numérica derivada do contexto de geração.
- [ ] `ModifierFamily`: `PREFIX`, `SUFFIX`, `INFIX`.
- [ ] `RolledModifier`: ID da definição + parâmetros/roll persistidos.
- [ ] `GenerationSource`: craft, smithing, loot, mob equipment/drop, reward, trade, machine, migration, admin/fallback.

### B — Congelar regras permanentes

- [ ] cada família contém 1..5 modificadores;
- [ ] rank não define quantidade;
- [ ] primeira geração é definitiva;
- [ ] reload só altera definições para gerações futuras, nunca reescreve rolls existentes;
- [ ] reparo, mudança de dimensão, drop/pickup e containers preservam identidade;
- [ ] smithing/upgrade preserva identidade quando representar evolução do mesmo equipamento;
- [ ] cópias reais de stack precisam de política explícita para evitar semântica falsa de unicidade por `instanceId`.

### C — Separar estado permanente de projeções

```text
Estado persistido
-> resolução de definições
-> snapshot efetivo
-> projeções de atributos/efeitos/UI
```

Persistir a decisão canônica; caches e modifiers aplicados em runtime são projeções reconstruíveis.

### D — Autoridade e boundaries

- [ ] geração/mutação somente servidor;
- [ ] cliente recebe snapshot suficiente para tooltip/UI;
- [ ] pacote Core não importa classes de mods opcionais;
- [ ] adapters externos ficam atrás das fronteiras dos Stages 00/06;
- [ ] APIs públicas distinguem query de mutation.

### E — Política de reroll

- [ ] não fornecer API survival de reroll;
- [ ] integração que tente substituir rank/modificadores de item já itemizado deve ser recusada ou interceptada explicitamente;
- [ ] comandos administrativos de debug, se existirem, exigem permissão e diagnóstico visível e não contam como mecânica survival.

### F — PT-BR como requisito de domínio

- [ ] IDs técnicos permanecem `ResourceLocation` estáveis;
- [ ] texto próprio visível ao jogador depende de chaves de localização;
- [ ] `pt_br` obrigatório para toda chave do Stage 11;
- [ ] nunca persistir strings traduzidas na identidade.

## Testes previstos

- invariantes 1..5;
- rank independente da contagem;
- rejeição de segunda geração;
- snapshots/query sem mutation implícita;
- barreira de imports opcionais.

## Acceptance

O subplano fecha quando há um único contrato documentado e testado para identidade, autoridade, imutabilidade de roll, famílias e localização, utilizado pelos demais subplanos sem representações concorrentes.
