# 12.08 — Transmigração mística e integração com Vampirism

## Objetivo

Oferecer uma rota mística de criação/vínculo/troca de corpos que use o mesmo `BodyService` do caminho tecnológico. O frontend ritual não pode criar uma segunda implementação de progressão.

## Fantasia proposta

### Elixir de Transmigração

Item/consumível ritual preparado por recipe/data-driven e, quando uma integração compatível existir, por caldeirão/alquimia.

Funções possíveis:

- iniciar o vínculo da alma com um corpo novo;
- marcar uma âncora ritual;
- ser consumido na criação de um `bodyId`;
- funcionar como custo de troca em configurações mais restritivas.

### Âncora de Alma

Componente/item ritual que transforma um ponto físico em **Âncora de Transmigração**.

A âncora referencia `ownerUuid` + `bodyId`; não contém cópia autoritativa do perfil.

## Vampirism

Quando Vampirism estiver instalado, preferir integração não destrutiva:

1. detectar um caixão válido por API/tag/adapter confirmado;
2. jogador aplica uma Âncora de Alma ou executa ritual específico;
3. somente aquele caixão recebe vínculo de transmigração;
4. caixões comuns continuam funcionando exatamente como o Vampirism espera;
5. interação especial abre seleção/troca apenas quando o vínculo existe.

Não substituir globalmente recipe, classe ou comportamento de todos os caixões.

Se a API do Vampirism não permitir decorar uma instância de caixão com segurança, implementar um bloco próprio **Caixão de Transmigração** cuja recipe use materiais/tema do Vampirism quando presente. Fail-closed em relação à integração; nunca mixin invasivo só para preservar estética.

## Sem Vampirism

O sistema místico continua disponível por bloco/estrutura ritual própria, por exemplo:

- Leito de Transmigração;
- Sarcófago da Alma;
- Altar de Transmigração.

Assim Vampirism é integração opcional, não dependência funcional do Stage 12.

## Corpo criado pela rota mística

Por padrão também é um `BodyProfile` fresco. A fantasia pode dizer “receptáculo”, “casca”, “novo corpo” ou “vaso”, mas a regra técnica é a mesma do corpo tecnológico.

## Regras de vínculo

- âncora possui owner;
- corpo alvo deve pertencer ao mesmo owner;
- vínculo deve ser revogável somente por fluxo explícito;
- quebrar a âncora não apaga automaticamente `BodyProfile`;
- remover Vampirism do modpack não pode apagar corpos vinculados;
- um corpo sem âncora válida fica armazenado/recoverable conforme política, não perdido silenciosamente.

## Restrições de troca

Configurar/validar:

- bloqueio em combate;
- bloqueio durante boss/encounter travado;
- distância do anchor;
- cooldown;
- custo do ritual;
- dimensão permitida/proibida;
- estado do corpo destino;
- estado de transformação externa quando incompatível.

Toda rejeição deve ter mensagem PT-BR precisa.

## Integração biológica com Vampirism

Separar duas coisas:

1. **caixão como frontend/anchor**;
2. **estado vampírico do personagem**.

O primeiro pode ser integrado aqui. O segundo pertence ao provider do 12.11 e deverá ser classificado como `BODY_LOCAL` somente após confirmar APIs e invariantes reais do Vampirism 1.21.x.

Não assumir que trocar de corpo deve curar/remover vampirismo até o provider estar implementado e testado.

## Critérios de aceite

- ritual usa o mesmo `BodyService` da máquina;
- somente caixões explicitamente vinculados viram anchors;
- caixão normal do Vampirism permanece intacto;
- ausência/remoção do Vampirism não perde `BodyProfile`;
- integração falha de forma segura se API mudar;
- todo conteúdo próprio tem `pt_br` completo.