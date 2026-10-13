# Contrato de evidência, versionamento, custos e requisitos

## Fonte da versão

Para qualquer perk que mencione mod/provider:

1. Resolver o JAR e a versão presentes em `PROJECT-INSTRUCTIONS/modlist` na revisão corrente, confrontando com a snapshot física mais recente se disponível.
2. Registrar **mod name, mod id, JAR, versão de runtime, localização do dossiê da modlist e data/head da conferência**. Se a modlist disser “presente” mas não permitir confirmar versão, marcar `VERSION-UNVERIFIED`, sem alegar integração verificada.
3. Examinar documentação, source/API ou JAR **da mesma versão**. Source de branch `main` ou release diferente é evidência exploratória, nunca prova do hook instalado.
4. Conferir configs/datapacks/addons que alterem comportamento, limites e modo de execução.
5. Registrar o **fato nativo e a mudança pretendida**: por exemplo `cooldown no provider → política de redução`, `max targets → +1 alvo`, `duração nativa de efeito → extensão`. Se o fato/hook não existe, não prometer o efeito.
6. Registrar método de integração e autoridade: evento, callback, registry, capability, acesso de leitura/escrita, servidor, ownership, resource settlement e compatibilidade multiplayer.
7. Para revisão posterior, preservar URL/path de evidência e SHA/release/linhas ou classe/método (se conhecidos). Verificação de versão e **verificação do comportamento são gates distintos**.
8. Ao atualizar qualquer provider, rever todas as perks que o consomem e suas classes de capability; não presumir continuidade binária/semântica.

### Estado da evidência

- `SOURCE-VERIFIED`: fato/hook confirmado na versão instalada e boundary documentado.
- `DOC-VERIFIED`: documentação compatível com a release, mas hook exato ainda exige prova; não declarar implementação pronta.
- `VERSION-UNVERIFIED`: versão física ou documentação da mesma release não confirmada.
- `TECH-BLOCKED`: provider não permite o efeito seguro conforme evidência.
- `NOT-APPLICABLE`: efeito canônico universal sem dependência de mod específico, com justificativa.

Um dossiê editorial pode estar **completo como proposta de design** sem estar `SOURCE-VERIFIED`; nunca classificar como **perks aprovadas/implementáveis** se faltarem hooks obrigatórios.

## Ficha obrigatória para P, S e perfis T

- ID editorial e owner tree;
- nome PT-BR, descrição gameplay, categoria e tier;
- função estrutural (Small/Ranked/Notable/Gateway/Keystone/Capstone/Bridge quando aplicável);
- ranks máximos e efeitos por rank;
- custo em perk/passive points por rank; custo extra só se houver recurso real;
- nível mínimo de personagem;
- **atributos mínimos requeridos**: `STRENGTH`, `CONSTITUTION`, `AGILITY`, `INTELLIGENCE`, `DETERMINATION`, `CHARISMA`, incluindo valores e interação com respec;
- domínio/mastery/requisitos de classe e especialização quando aplicáveis;
- dependências/gates e regras de acesso sem circularidade;
- providers específicos, versões/JARs e evidência de presença;
- efeito nativo e delta proposto, tags/classificação e hook/capability;
- requisitos de servidor/cliente, fallback/fail-closed, causalidade e antiabuso;
- power budget, sinergias, conflitos e cobertura de outros mods;
- status separando conceito, evidência, auditoria e implementação.

Os campos podem indicar `PENDING-EVIDENCE` durante autoria, mas não durante gate técnico final.

## Custos × requisitos

**Requisito (mínimo não consumível):** `Intelligence >= 20` e `Agility >= 15`, avaliados sobre `CoreProgressionState.attributeRanks()` no servidor. Não reduz rank nem reembolsa pontos automaticamente.

**Custo (consumível):** `costPerRank` em Passive Points/ledger da compra de perk, usando `NodePurchaseDefinition` e autoridade de compra existente, sem gastar Core Points novamente como se fossem Passive Points. Um custo extra em mana/stamina/itens exige transação e policy próprias, não é assumido.

**Rank de atributo:** já adquirido com custo próprio do Core em `AttributeRankMutationService`, política `AttributeRankCostPolicy`. Não duplicar esse gasto ao comprar P/S/T.

### Estado atual auditado em 13/10/2026

- `AttributeId` registra os seis atributos e `AttributeRanks` armazena ranks.
- `NodeAccessRequirement` hoje verifica level/classe/mastery/especialização/escolha/node/discovery, **não** atributos mínimos.
- `NodePurchaseDefinition` tem `maxRank` e `costPerRank`; `NodePurchaseMutationService` usa `passivePoints`.
- Portanto: criar extensão versionada de requisito de atributo, validar acesso no servidor **antes de debitar**, auditar comportamento de respec e sincronizar tooltip/UI. Não declarar o gate de atributo já implementado.

### Casos que exigem teste

- Pontos insuficientes: compra negada, sem débito.
- Atributo abaixo do mínimo: compra negada, sem débito.
- Requisito atendido: compra pode prosseguir, stats não são consumidos.
- Respec reduz atributo abaixo do mínimo: política de efeito/refund precisa ser resolvida antes de implementar; nenhuma persistência inválida silenciosa.
- Provider ausente/versão divergente: perk fica bloqueada ou fail-closed.
- Requisitos múltiplos, classes híbridas, mastery e vizinhança: todos avaliados de forma combinada, sem atalhos.
- Addon que reprocessa o mesmo evento: efeitos e custos aplicados apenas uma vez.

## Critérios antigos e novo catálogo

Fonte de critérios vigente consultada:
`PROJECT-INSTRUCTIONS/orientacoes/ia/CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`.

Preservar 9 eixos (gates, integração, identidade, topologia conceitual/final, especializações, PT-BR, completude documental, não NeoVitae, cobertura da modlist) e 18 invariantes técnicos (provider-native, hooks exatos, fail-closed, mastery, antiduplo, custos, reentrância, read-only, anti-farm etc.).

Partes operacionais atreladas a `A####`/Notion/lotes exatos de 10 e três chats são **legadas** ao novo método `I/P/S/T`. Não reutilizá-las para definir IDs ou impedir progressão por fases. Se houver obrigação ativa de sincronização externa com Notion, confirmar antes de migrar a fonte de verdade; não fingir ter escrito ou reconsultado Notion.
