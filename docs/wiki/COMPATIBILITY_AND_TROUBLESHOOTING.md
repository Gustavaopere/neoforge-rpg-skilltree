# Compatibilidade e troubleshooting

## O jogo inicia sem um mod opcional?

Deve iniciar. Se não iniciar, trate como bug de isolamento/classloading da integração.

## Uma perk não afeta um feitiço específico

Primeiro determine se a perk usa um hook mágico genérico ou uma integração nominal. Não é válido assumir compatibilidade só porque o alvo é um spell. Confira o JSON da perk, node effects e adapter do mod.

## Mastery sobe duas vezes

Isso normalmente indica dois eventos representando a mesma ação ou execução simultânea do adapter e fallback. O contrato desejado é uma concessão por ação confirmada.

## Unlock sumiu após reload

Verifique estabilidade do ID, migração de dados, ordem de datapack reload e validação de requisitos. IDs renomeados sem migração são incompatibilidade de save.

## Perk continua ativa após respec

Procure modificador não removido, UUID/ID instável ou cache derivado não recalculado.

## Dedicated server falha, cliente não

Suspeite de referência client-only em classe comum, inicialização estática de integração opcional ou dependência não protegida por mod detection.