# 12.12 — Seletor de corpos, UI e PT-BR

## Objetivo

Criar uma interface clara para consultar, criar e trocar corpos sem expor IDs/NBT internos e sem depender de textos em inglês.

## Seletor

A interface deve apresentar apenas corpos pertencentes ao player autenticado e um read model server-authoritative.

Informações por corpo:

- nome/apelido;
- tipo: Original, Artificial, Ritual etc.;
- estado: Ativo, Armazenado, Em construção, Pronto, Destruído, Recuperação necessária;
- nível RPG;
- classe/especialização principal quando aplicável;
- localização/âncora;
- vida resumida quando útil;
- última ativação;
- avisos de incompatibilidade/provider.

## Segurança de rede

Cliente recebe uma lista de visualização e envia apenas intenção:

```text
SwitchBodyRequest(targetBodyId, anchorId/revision)
```

Servidor revalida ownership, estado, distância, anchor, cooldown e revision. Cliente nunca envia progressão, inventário ou snapshot para ser aplicado.

## Estados de UI

- Corpo ativo não mostra ação de ativar;
- `CONSTRUCTING` mostra progresso;
- `READY/STORED` pode mostrar “Transferir consciência”;
- `RECOVERY_REQUIRED` bloqueia troca normal e explica que é necessária recuperação;
- `DESTROYED` aparece somente se configuração/histórico permitir.

## Vocabulário PT-BR

Termos canônicos iniciais:

```text
Body = Corpo
Original Body = Corpo Original
Artificial Body = Corpo Artificial
Body Constructor = Construtor de Corpos
Body Chamber = Câmara de Corpo
Soul Anchor = Âncora de Alma
Transmigration Elixir = Elixir de Transmigração
Transmigration Coffin = Caixão de Transmigração
Switch Body = Transferir Consciência / Trocar de Corpo
Stored = Armazenado
Ready = Pronto
Constructing = Em construção
Recovery Required = Recuperação necessária
```

A escolha final entre “Transferir Consciência” e “Trocar de Corpo” deve considerar clareza no botão e lore no texto descritivo; ambos devem ser chaves localizadas, não strings hardcoded.

## Tradução

Toda chave própria deve possuir `pt_br` no mesmo PR que a introduz. Gate de CI deve detectar chave ausente como no Stage 11.

Não mostrar ao usuário:

- `Shell`;
- `BodyProfile`;
- `bodyId`;
- `RECONCILED`;
- nomes de classes Java;
- erros crus de provider.

Diagnóstico avançado pode mostrar IDs apenas em tooltip/admin/debug.

## Tela de construção

Mostrar:

- progresso;
- recursos faltantes;
- energia/custo quando aplicável;
- corpo que está sendo criado;
- estado persistente (“Construção pausada”, “Pronto para vincular” etc.).

## Tela ritual

Mostrar termos místicos equivalentes, mas usar os mesmos estados internos. Não duplicar modelo de rede/UI apenas por tema; compartilhar componentes/read models onde fizer sentido.

## Acessibilidade

- não depender apenas de cor para estado;
- textos curtos e descritivos;
- confirmação explícita antes de operações destrutivas;
- suporte a escala de GUI;
- teclado/controle quando a stack do jogo oferecer suporte.

## Critérios de aceite

- seletor não vaza corpos de outro owner;
- client spoof de bodyId falha no servidor;
- todas as ações possuem feedback PT-BR;
- nenhum termo técnico cru aparece em fluxo normal;
- estados de construção/recovery/destruição são distinguíveis sem depender apenas de cor;
- UI funciona em dedicated server + cliente sem lógica autoritativa no client.