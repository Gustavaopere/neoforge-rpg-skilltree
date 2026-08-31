# A0110 — Conservação de Equipamento I

## Estado de design

**DESIGN APROVADO — FAIL-CLOSED E NÃO COMPRÁVEL NO RUNTIME ATUAL.**

## Contrato canônico

- Domínio: SURVIVAL.
- Ramo: Manutenção e Durabilidade.
- Função: Ramo.
- Ranks: 5.
- Custo: 1 PP por rank.
- Gate: Gateway SURVIVAL desbloqueado.
- Efeito congelado: 1% por rank, máximo 5%, de chance de um uso legítimo de ferramenta manual elegível preservar exatamente 1 ponto de durabilidade que seria realmente consumido.

A perk conserva desgaste; nunca repara dano já sofrido, nunca cria durabilidade e nunca altera custos energéticos, munição ou manutenção própria de máquinas/equipamentos especiais.

## Boundary técnico exigido

O ponto correto precisa ocorrer **depois** das regras nativas que decidem se o uso consumirá durabilidade — incluindo a prevenção de Unbreaking — e **antes** do decremento final do `ItemStack`.

No NeoForge 1.21.1, `ItemStack#hurtAndBreak` chama `Item#damageItem` antes de `EnchantmentHelper.processDurabilityChange`. Portanto `damageItem` não é um hook semanticamente equivalente: cancelar ou reduzir ali faria A0110 ocorrer antes de Unbreaking e alteraria a matemática nativa.

Não existe no runtime canônico atual um adapter global aprovado que exponha o decremento residual pós-Unbreaking como transação cancelável para todas as ferramentas manuais elegíveis.

## Provider policy

- Minecraft/NeoForge é owner de durabilidade e Unbreaking.
- Providers modded só entram mediante adapter explícito da família de ferramenta e estágio de desgaste comprovado.
- Item indestrutível, armor, equipamento com manutenção própria, ferramenta energética, máquina portátil e qualquer família sem adapter ficam fora.

## Fail-closed / availability

Enquanto P-0036 / adapter equivalente não fornecer um boundary pós-prevenção e pré-decremento seguro:

- compra nova de A0110 deve ser recusada;
- ranks legados não podem conceder PP válido para gates dependentes;
- nenhuma rolagem pode ser executada;
- nenhuma aproximação por `damageItem`, comparação de NBT posterior, reparo compensatório ou restauração de damage value é permitida.

## Deduplicação e causalidade

Cada uso causal pode produzir no máximo uma rolagem A0110 e somente quando restar exatamente um decremento positivo elegível. A identidade do uso deve ser server-authoritative; callbacks duplicados não criam novas chances.

## Integrações e exclusões

A0110 não interfere com:

- Unbreaking/Mending além da ordem nativa;
- energia FE/SU/combustível;
- manutenção própria de Create/Oritech/AE2 ou outros providers;
- durability de armadura;
- ferramentas marcadas unbreakable;
- Mastery.

## Critérios de implementação para Chat 2

1. Não implementar a perk por interceptação genérica anterior a Unbreaking.
2. Somente habilitar aquisição se existir adapter aprovado que prove o decremento residual e permita cancelamento exatamente uma vez.
3. Testar ordem com Unbreaking, dano >1, item quebrando no último ponto, item indestrutível e callbacks repetidos.
4. Sem adapter seguro, implementar explicitamente `availability=false` e manter comportamento mecânico zero.

## Decisão Chat 1

A identidade e a posição da perk são aprovadas, mas o runtime atual não oferece o boundary universal necessário. O design fica congelado em fail-closed até existir P-0036 ou contrato tecnicamente equivalente; não pode ser redesenhado pelo Chat 2 para um bônus genérico.
