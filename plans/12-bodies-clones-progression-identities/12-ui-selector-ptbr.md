# 12.12 — Seletor de corpos — contrato funcional e pt-BR

## Fronteira

Cards, layout, tema tecnológico/ritual, badges, gauges e acessibilidade visual foram separados para `../textura/05-corpos-seletor-construcao-ritual.md`.

Este arquivo mantém read model, actions, segurança de rede, estados e localização do Stage 12.

## Objetivo

Permitir consultar/criar/trocar corpos sem expor dados de outro owner, sem aceitar estado autoritativo vindo do cliente e sem exigir IDs/NBT internos no fluxo normal.

## Read model do seletor

A interface recebe somente corpos pertencentes ao player autenticado e campos presentation-safe aprovados, que podem incluir:

- nome/apelido;
- tipo de corpo;
- estado;
- nível RPG;
- classe/especialização quando aplicável;
- localização/âncora autorizada;
- vida resumida quando útil;
- última ativação;
- avisos de incompatibilidade/provider.

A aparência desses campos pertence a Textura.

## Segurança de rede

Cliente envia apenas intenção conceitualmente equivalente a:

```text
SwitchBodyRequest(targetBodyId, anchorId/revision)
```

Servidor revalida ownership, estado, distância, anchor, cooldown e revision. Cliente nunca envia progressão, inventário ou snapshot para ser aplicado.

## Estados e disponibilidade de ações

- corpo ativo não oferece ação de ativar;
- `CONSTRUCTING` expõe progresso funcional quando disponível;
- `READY/STORED` pode expor intenção de transferência;
- `RECOVERY_REQUIRED` bloqueia troca normal e fornece motivo presentation-safe;
- `DESTROYED` só é projetado quando configuração/histórico permitirem.

Textura representa esses estados, mas não os calcula.

## Vocabulário pt-BR

Termos canônicos iniciais permanecem:

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

A escolha final de copy deve usar localization keys, não strings hardcoded. IDs técnicos internos permanecem inalterados.

Toda key própria deve possuir `pt_br` no mesmo PR que a introduz; gate de CI deve detectar ausência conforme o padrão do Stage 11.

## Dados que não aparecem no fluxo normal

Não projetar como copy comum:

- `Shell`;
- `BodyProfile`;
- `bodyId`;
- `RECONCILED`;
- nomes de classes Java;
- erros crus de provider.

Diagnóstico autorizado pode fornecer IDs em camada debug separada.

## Construção tecnológica

O read model pode fornecer:

- progresso;
- recursos faltantes;
- energia/custo quando aplicável;
- corpo alvo;
- estado persistente como pausado/pronto.

Cálculo e authority permanecem no Stage 12/provider. Apresentação tecnológica pertence a Textura.

## Transmigração ritual

A superfície ritual usa os mesmos estados/protocolos internos quando aplicável. Tema visual diferente não cria segundo modelo de rede ou segunda authority.

## Handoff de acessibilidade

A engenharia fornece estados explícitos, copy localizada, confirmação requerida para ações classificadas como destrutivas e disponibilidade funcional de cada botão. Distinção visual sem somente cor, escala de GUI e foco pertencem a Textura.

## Critérios de aceite funcionais

- seletor não projeta corpos de outro owner;
- client spoof de `bodyId` falha no servidor;
- ações possuem feedback presentation-safe em pt-BR;
- nenhum termo técnico cru é necessário no fluxo normal;
- estados de construção/recovery/destruição são dados semânticos explícitos;
- dedicated server + client funcionam sem lógica autoritativa no client.

A qualidade estética e a distinção visual desses estados são aceites separados em `../textura/05-corpos-seletor-construcao-ritual.md`.
