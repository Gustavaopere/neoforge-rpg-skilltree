# Codex — execução editorial e técnica em quatro etapas

Este é o roteiro canônico de autoria das perks. Leia em conjunto com [`MASTER-EXECUTION-PLAN.md`](./MASTER-EXECUTION-PLAN.md), [`PROVIDER-EVIDENCE-AND-COSTS.md`](./PROVIDER-EVIDENCE-AND-COSTS.md) e [`ABILITY-SLOTS-AND-TAGS.md`](./ABILITY-SLOTS-AND-TAGS.md).

## Contrato comum a todas as etapas

- Trabalhar nas **11 árvores** com mesma quantidade final de P, vagas T e S por owner, sem filler. Propor quotas comparando as 11 antes de gerá-las em massa; documentar quotas editoriais provisórias separadas das quotas aprovadas.
- **Ler a modlist atual**, datapacks aplicáveis e documentação de providers instalados antes de atribuir efeitos. Usar o JAR/versão exatos como referência de gameplay; buscar source da mesma release, documentação/API versionada ou inspeção do artefato quando houver.
- Cada perk com integração de mod deve registrar nome do mod, mod id, **versão instalada e JAR**, efeito nativo sobre o qual atua, API/hook/evento ou ponto de extensão, evidência consultada e status da verificação. Nunca usar função de branch ou versão diferente como se estivesse na release instalada.
- Usar os critérios invariantes de `PROJECT-INSTRUCTIONS/orientacoes/ia/CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`: os 9 eixos de qualidade/integração e as 18 regras técnicas. O protocolo antigo de IDs `A####`, lotes exatos de 10, três chats e obrigação editorial de Notion **não determina a estrutura nova**; registrar a transposição desses pontos no gate de revisão e não classificar um conceito como aprovado apenas porque o Markdown existe.
- Escrever nomes, descrições e requisitos destinados ao jogador em PT-BR. Nomes oficiais e IDs técnicos podem ser preservados.
- Para todas as famílias, documentar: **custo em pontos de perk por rank**, ranks máximos, nível de personagem, requisitos mínimos dos seis atributos quando aplicáveis, mastery/gateway/nodes e custos extras somente quando possuírem recurso/provider real.
- **Atributo mínimo é requisito de acesso, não gasto do atributo.** Os pontos gastos em rank do atributo já foram consumidos no Core; não debitar Inteligência/Agilidade novamente na compra de perk. Custos alternativos com consumo real só podem ser criados mediante contrato próprio e aprovação explícita.
- Perks concretas têm que ter identidade, efeito mensurável, dependências coerentes, risco/limites, power band e comportamento para ausência de provider; sem bônus arbitrário ou hook imaginado.
- **Status de design ≠ status técnico ≠ implementada.** `CONCEPT-DRAFT`, `CONCEPT-REVIEWED`, `TECH-AUDITED`, `BLOCKED` e `IMPLEMENTED/VALIDATED` representam gates distintos. Só chamar de final quando verificado no runtime.
- O layout visual fica por último, mas **tier, faixa de poder, dependências e localização conceitual** são planejados desde o dossiê. O gate topológico/UX final valida distâncias após o desenho.

## Ponto 0 — atributos internos (I)

**Arquivos:** `01-internal-attributes/I0001..I0006-*.md` e `01-internal-attributes/README.md`.

1. Revalidar `AttributeId`, `AttributeRanks`, `AttributeRankCostPolicy`, `AttributeRankMutationService` e contratos do RPG Core.
2. Completar os seis dossiês com papel no gameplay, efeitos derivados reais, custo de evolução sob policy, fonte de verdade, sinergias e requisitos usados por P/S/T. Onde ainda não existir fórmula aprovada, marcar como decisão de balanceamento, sem inventar números.
3. Criar **matriz de limiares de atributo**: qual faixa de poder pode exigir STR/CON/AGI/INT/DET/CHA, evitando que magia monopolize Inteligência ou que builds híbridas sejam inviáveis.
4. Gate: exatamente seis atributos globais, nenhum sistema duplicado de stats/pontos; mudanças no runtime só em etapa de implementação separada.

## Trabalho 1 — perks normais (P)

**Pasta:** `02-standard-perks/<tree>/Pxxxx-<slug>.md`.

1. Ler modlist corrente, datapacks, catálogos de capacidades, critérios obrigatórios e código-fonte/API da **versão instalada** dos providers relevantes.
2. Determinar função, tier, custo de pontos, custo por rank, requisito de atributo, gates, sinergias, conflito com progressões nativas, efeito exato e explicação ao jogador. É permitido benefício percentual **quando houver função real e orçamento justificado**, mas não copiar dezenas de +5% genéricos.
3. Declarar por perk os mods envolvidos, versões e o que está sendo modificado (cooldown nativo, duração nativa, dano nativo, alcance, economia, produção etc.). Custo de mana/stamina/energia nunca é fictício.
4. Revisar completude de cada dossiê em lote verificável. Perks com hook não confirmado ficam `BLOCKED`, não recebem efeito alternativo silencioso.
5. Gate: todas as 11 árvores com quota P igual e dossiers com evidência, antirrepetição, custos/requisitos e identidade. Conteúdo pode ser desenhado enquanto a modlist restante é catalogada, mas **não aprovado tecnicamente** sem verificar o provider próprio.

## Trabalho 2 — especializações (S + Mecânica Assinatura)

**Pasta:** `04-specialization-perks/<owner-tree>/<specialization>/`.

1. Definir especializações PURE/HYBRID com owner único; construir fantasias distintas e Mecânicas Assinatura como afinidades/espíritos, funções ou sacrifício de summons, selamento, juramentos, posturas e equivalentes úteis no pack.
2. **As S são generalistas por classificação de ação**, não versões isoladas de um spell. Exemplo conceitual: projétil de fogo direto contra um alvo pode propagar parte do efeito a até 5 blocos quando os critérios comprovados são satisfeitos. A classificação é `fire + projectile + single_target + direct_hit`, **não** um nome de spell.
3. Consultar metadados reais do provider: school, damage type, projectile, area, hit attribution, owner, resource e capabilities. Nenhuma tag inventada é tratada como tag nativa: construir mapping de tags **normalizadas pelo RPG Skill Tree** sobre evidência versionada.
4. Definir os limites de propagação/recursão: eventos desencadeados por especialização não acionam a mesma perk novamente; não reprocessar dano/cura, não atuar em projeto próprio/mob não elegível, respeitar PvP, friendly fire, summon owner e efeitos aplicados por ticking.
5. Criar dossiês S completos com custos, requisitos de atributo, quantidade de ranks, tier, efeito, provider/versão, assinatura, sinergias. Comparar power budgets de especializações puras e híbridas entre todas as árvores.
6. Gate: quota S igual por owner tree, assinatura coerente, mapeamento provider/tag auditável e nenhum `Sxxxx` dedicado apenas a mutar uma Ability nomeada (isso pertence à etapa 3).

## Trabalho 3 — vagas e perfis de Transmutation (T) — por último

**Pastas:** `03-transmutation-perks/<tree>/Txxxx-reservado/` e `03-transmutation-perks/ability-profiles/<provider>/<ability>/` (segunda pasta é **proposta**, criada nesta etapa).

1. **Na primeira passagem do Codex**, criar apenas todas as **vagas de árvore** `Txxxx-reservado`, com os sete filhos estruturais vazios `.11/.12/.21/.22/.31/.32/.33`, status `RESERVED-UNBOUND`, sem magia, skill, provider ou efeito. A quota é de **vagas por árvore**, não quantidade de spells do modpack.
2. Colocar vagas em faixas iniciais, médias e avançadas **por progressão normalizada**. Um spell `nível 1–10` não é necessariamente equivalente a um poder lendário `nível máximo 1`. A classificação deverá levar em conta unlock real, potência, raridade, rank/level nativo, custo e classe, nunca apenas `maxLevel`.
3. **Depois de catalogar cada Ability**, criar **um perfil próprio por Ability real**, com namespace/registry id/provider/versão, requisitos de conhecimento, e no mínimo 2 LEFT, 2 RIGHT e 3 METAMORPHOSIS, máximo 9 por grupo. LEFT `.11-.19`, RIGHT `.21-.29`, META `.31-.39`. **Só uma escolha ativa por grupo; coexistência entre os três**.
4. Perfis são específicos: Fireball → projéteis extras, geometria, afinidade de fogo das almas, etc.; Shield → resistência/cobertura/efeitos defensivos **apenas onde existir boundary seguro**. Não inventar movimento/ricochete/duração sem source audit. A transformação de afinidade precisa alterar os aspectos semânticos suportados, não só VFX.
5. Avaliar todos os feitiços, skills, poderes e ações relevantes do pack, incluindo **não mágicos**. O objetivo é disponibilizar perfil a cada Ability tecnicamente elegível; Abilities não elegíveis recebem motivo e estado `UNSUPPORTED`/ `BLOCKED`, não profile genérico falso.
6. Cada vaga T pode receber uma Ability conhecida e elegível **conforme a regra de binding que será fechada no desenho do runtime**. A skill não nasce ao comprar a vaga. Uma Ability deve possuir um único perfil global; não duplicar o perfil ao aparecer em outra vaga/tier. Não assumir que todos os perfis cabem simultaneamente na árvore: a quota igual conta vagas, não todos os perfis.
7. Para cada Ability verificar level/tier, runtime authority, cooldown, mana/stamina, target, duração, entity e hooks reais da versão instalada; somente depois aprovar e implementar.
8. Gate: vagas T iguais entre 11 árvores; nenhuma vaga vazia publicada como perk pronta; perfis versionados específicos, escolhas 1+1+1; classificação e compatibilidade testáveis; documentação das Abilities não suportadas.

## Validações obrigatórias

- Validator editorial deve distinguir **vagas**, **perfis Ability**, **P/S conceitos**, **P/S tecnicamente auditados**, **implementados**; não colapsar essas contagens.
- Cenários de teste: provider removido/alterado, versão sem hook, metadata de tag ausente, dano indireto, recursão de propagação, estado de spellbook vs ability conhecida, maxLevel 1 em tier alto, custo de pontos insuficiente, atributo abaixo do mínimo, attribute respec reduzindo rank sob o threshold, sem consumo indevido de stats, binding duplicado, exclusividade LEFT/RIGHT/META, híbrida contando no owner único, save/reload e servidor dedicado.
- Para runtime, conferir os comandos já presentes no repo: `./gradlew test`, `bash scripts/test-core.sh`, `python3 scripts/validate-data.py`. Não afirmar sucesso sem executá-los.

## Ordem de fechamento

`0 I → 1 P → 2 S + Signature Mechanics → 3 T slots + Ability profiles → auditoria/validação runtime → topologia/UX/wiki`.

A leitura da modlist e a análise de versões/providers ocorrem **durante** as etapas 1 e 2, não só depois delas. A auditoria **por Ability** fica na etapa 3.

## Decisões de produto deixadas para a execução

- Números finais de quotas I/P/S/T; budget provisório não é aprovado.
- Exatamente como jogadores vinculam, trocam ou liberam Abilities nas vagas T e se uma Ability pode ocupar múltiplas posições simultâneas. **Recomendação:** uma Ability ativa em no máximo uma vaga por personagem, com reassociação explícita e persistente.
- Se a perda de um atributo mínimo após respec desativa a perk, força refund ou mantém compra sem efeito. **Recomendação:** impedir efeito enquanto o requisito não estiver satisfeito e garantir refund seguro conforme política de respec.
- Fonte de verdade de “conhece Ability” por provider, requisitos de obtenção e equivalência de nível/tier.
- Custo de pontos de perks, curvas/ranks, peso dos pré-requisitos de atributos e custos alternativos que consumam recursos.
- Como a lista de todos os perfis elegíveis é apresentada e filtrada no cliente.
