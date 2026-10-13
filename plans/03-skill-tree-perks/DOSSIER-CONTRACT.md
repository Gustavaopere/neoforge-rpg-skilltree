# Contrato dos dossiês — I/P/S/T

## Campos comuns para P, S e perfis de Ability T

Cada dossiê de conteúdo precisa registrar:

- ID editorial, status de conceito e status técnico separadamente;
- nome PT-BR, owner tree, função de árvore, tier/faixa de poder;
- fantasia, efeito completo por rank, `maxRank`, `costPerRank` em Passive Points e custos extras reais quando aplicáveis;
- nível mínimo de personagem, atributos mínimos (dos seis do Core), classe/mastery/gateway/nodes requeridos, dependências e respec;
- provider/mod id, **JAR/versão instalada**, caminho da modlist, source/API/evidência da mesma release;
- efeito nativo observado, delta pretendido, pipeline/callback/authority server-side, limites, fallback/fail-closed;
- sinergias, conflicts, mecanismo anti-recursão/antiabuso, deduplicação, alcance, causalidade e compatibilidade com outros mods;
- estado/escopo de implementação e testes necessários.

Campos cujo valor não se encontra verificado podem marcar `PENDING-EVIDENCE` durante conceito, nunca fingir dado real para passar auditoria.

## I — atributo interno

`01-internal-attributes/I0001..I0006-*.md`: enum/rank canônico, efeito real, custo do rank pela Core policy, papel de build, riscos de respec, limites. A árvore interna é comum a todas.

## P — normal

`02-standard-perks/<tree>/Pxxxx-<slug>.md`: efeito geral em sistema/ação/categoria, sem direcionar customização de um único spell. Fonte real e versão obrigatórias quando depender de mod. Ranks/custo/atributos mínimos não podem ser esquecidos.

## S — especialização

`04-specialization-perks/<owner>/<specialization>/README.md`: PURE/HYBRID, required trees, owner único, fantasia, Mecânica Assinatura, providers e seus versions, power budget e relação com outras especializações.

`Sxxxx-<slug>.md`: efeito generalista sobre **predicado explícito de tags/facts** (ex.: fire + projectile + single_target), requisito, custo, rank e limite de disparos; reentrância, attribution/PvP/friendly-fire e ausência de metadata. Não criar S que seja apenas segunda versão nomeada de Fireball ou outra Ability.

## T — vaga editorial vazia

`03-transmutation-perks/<tree>/Txxxx-reservado/` contém raiz `Txxxx-reservado.md` e os sete arquivos `Txxxx.11/.12/.21/.22/.31/.32/.33`. **Antes da catalogação, nenhuma vaga possui Ability, provider ou efeito individual preenchido.** Status de todos: `RESERVED-UNBOUND`. A vaga documenta apenas owner, tier/progressão e IDs dos grupos estruturais. Não conta como perfil de Ability completo nem é exportada para runtime.

## Perfil de Ability — transformação individual (etapa 3)

**Pasta proposta:** `03-transmutation-perks/ability-profiles/<provider>/<ability>/`.

Um perfil por Ability conhecida e tecnicamente elegível, com registry id namespaced, provider/versão, rank/raridade/unlock/cooldown/resource, autoridade de conhecimento, tiers elegíveis, efeitos nativos, eixos LEFT/RIGHT/META, no mínimo 2/2/3 opções até 9/grupo, descrição de mudanças, limites e source audit específico.

Cada opção do perfil é exclusiva dentro do grupo e pode coexistir com uma escolha dos outros dois grupos. O perfil é referenciado dinamicamente pela vaga T; **não recebe uma segunda cópia para cada Txxxx**. Uma Ability sem hook seguro fica `BLOCKED`/ `UNSUPPORTED`, com motivo e testes.

### Cuidado com exemplos anteriores

A pasta existente `arcane/T5000-chain-lightning/` serve como piloto **de um perfil**. Deve ser migrada para a nova pasta de perfis na etapa 3, sem convertê-la em regra de binding fixo nem apagar ideias válidas. `T5000` poderá ser um slot reservado normalmente depois da migração.

## Auditoria técnica

Aplicar as invariantes de `PROJECT-INSTRUCTIONS/orientacoes/ia/CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`. A formulação antiga do protocolo de 10 perks A/Notion não controla o novo naming/routine, mas os princípios de hooks exatos, provider-native-first, ausência de duplicação, anti-farm e PT-BR permanecem obrigatórios.
