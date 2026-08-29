# 12.10 — Morte, respawn e perda de corpo

## Objetivo

Integrar morte/respawn ao conceito de corpos sem criar duplicação de inventário, ressurreição gratuita involuntária ou corrupção de progressão.

## Princípio

Morrer continua sendo um evento real do corpo ativo. Ter corpos armazenados pode oferecer uma rota alternativa de continuidade, mas a política deve ser explícita e configurável.

## Políticas previstas

### `VANILLA_RESPAWN`

O corpo ativo segue a morte normal do Minecraft/modpack. Outros corpos não participam automaticamente.

### `SELECT_STORED_BODY`

Quando o corpo ativo morre e existe outro corpo válido armazenado, a tela de morte pode oferecer:

- ressurgir/reconstituir o corpo atual conforme regra normal;
- abandonar/perder o corpo atual quando a configuração exigir;
- ativar outro corpo armazenado.

### `BODY_PERMADEATH`

Modo opcional mais severo:

- corpo morto vai para `DESTROYED`;
- sua progressão permanece como tombstone/audit trail;
- o jogador deve ativar outro corpo existente ou seguir fallback configurado.

Não habilitar permadeath implicitamente.

## Ordem na morte

Antes de qualquer seletor:

1. detectar morte real do corpo ativo;
2. resolver regras de drop/keepInventory/soulbound do modpack;
3. persistir estado pós-morte coerente;
4. impedir o mesmo inventário de existir simultaneamente no cadáver/drop e no snapshot recuperável;
5. só então expor opções de corpo/respawn.

## Inventário e drops

Se itens foram dropados no mundo, o snapshot do corpo morto deve refletir que eles não estão mais em seu inventário.

Se `keepInventory`/mecânica equivalente preserva itens, eles continuam pertencendo ao corpo conforme a regra aplicada.

A integração deve respeitar o resultado autoritativo de outros mods, não reproduzir manualmente drops antes que seus eventos terminem.

## Ativação de outro corpo após morte

Usar uma variante controlada da transação 12.05:

- source está em estado `DEAD`/post-death;
- target deve estar `STORED`/`READY`;
- destino físico vem da âncora do target ou fallback permitido;
- nenhum snapshot pré-morte pode ser restaurado para duplicar loot.

## Localização

Corpo armazenado pode possuir sua própria dimensão/posição de anchor. Ao ativá-lo após morte, spawn no local seguro definido pelo anchor/body profile.

Validar chunk/dimensão e fallback para ponto seguro caso a posição deixe de existir.

## Sem outros corpos

Se nenhum corpo alternativo existir, o jogo deve continuar com respawn normal configurado. Stage 12 nunca pode softlockar o save porque o único corpo morreu.

## Exploits

- trocar na tela de morte não pode evitar custo/drop já confirmado;
- não rerrolar loot/encounter;
- não resetar cooldowns de forma explorável sem política;
- corpo destruído não pode ser reativado por pacote cliente;
- logout na tela de seleção não pode restaurar estado pré-morte.

## Testes obrigatórios

- morte sem corpo alternativo;
- morte com corpo armazenado;
- keepInventory true/false;
- soulbound;
- Curios;
- Stage 11 items dropados mantêm identidade;
- crash durante seletor;
- corpo target em dimensão removida/inválida;
- permadeath habilitado/desabilitado.

## Critérios de aceite

- nenhum item duplica por morte + switch;
- progressão do corpo morto segue política configurada;
- outro corpo pode ser ativado com segurança quando permitido;
- fallback impede softlock;
- todas as opções e erros são localizados em PT-BR.