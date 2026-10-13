# Codex — regra de geração e preenchimento de Transmutation

**Atualização de arquitetura:** consulte primeiro [`CODEX-FOUR-STAGES.md`](./CODEX-FOUR-STAGES.md) e [`ABILITY-SLOTS-AND-TAGS.md`](./ABILITY-SLOTS-AND-TAGS.md). A antiga política de selecionar uma lista fixa de skills e vincular cada `Txxxx` permanentemente a uma delas foi substituída por **vagas T + perfis específicos por Ability**.

## Primeira passagem — antes de catalogar todas as skills

- Codex conclui atributos (0), perks normais completas como design (1) e especializações generalistas (2), consultando modlist, versões instaladas e source dos providers.
- Para T, cria **somente vagas numeradas vazias** por árvore: `Txxxx-reservado/` com raiz e sete arquivos estruturais `.11/.12/.21/.22/.31/.32/.33`.
- Os campos existem, mas nomes de spells, requisitos de conhecimento, efeitos concretos e providers são deixados vazios. `Status: RESERVED-UNBOUND`.
- A mesma quota de vagas T deve ser proposta para as 11 árvores; esses números são editoriais provisórios, sem binding de player ou runtime.
- Não sobrescrever silenciosamente o piloto `T5000-chain-lightning`, que deve ser migrado para modelo de **Ability Profile** na terceira etapa.

## Segunda passagem — depois de auditar Abilities uma a uma

- Criar **um perfil de Transmutation por Ability elegível** em `ability-profiles/<provider>/<ability>/`, com versão instalada, ID real, tier/rank/raridade e no mínimo 2 LEFT, 2 RIGHT e 3 META (máximo 9 por grupo).
- A posição `Txxxx` não recebe cópia individual dos perfis. Na jogabilidade, uma Ability conhecida e compatível com a faixa/tier da vaga pode ser vinculada, sujeita à futura política de binding.
- Uma opção ativa por grupo e coexistência 1+1+1.
- Todas as Abilities relevantes são **avaliadas**; unsupported/blocked devem constar da matriz de cobertura com motivo, não receber metamorfoses falsas.
- Nunca tratar quantidade de arquivos de slot como quantidade de perfis implementados.

## Critérios de validação

Modo editorial: slots podem estar vazios. Modo runtime/release: nenhuma vaga vazia é apresentada como transmutação funcional; nenhum perfil sem provider/versão/hook entra em runtime. O sistema não concede spell pelo fato de uma vaga estar comprada.
