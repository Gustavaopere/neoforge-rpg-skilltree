# Dossier Contract — P / T / S

Este arquivo define o formato mínimo dos dossiês editoriais do novo catálogo. O objetivo é impedir que perks sejam criadas com informações incompletas ou com decisões técnicas misturadas ao conceito.

## 1. Standard Perk — `Pxxxx-<slug>.md`

Cada arquivo deve conter:

- **ID editorial:** `Pxxxx`.
- **Nome:** nome de jogador.
- **Owner tree:** uma das 11 árvores.
- **Família:** `STANDARD`.
- **Status:** estado da state machine do plano mestre.
- **Fantasia/objetivo:** que decisão ou estilo a perk reforça.
- **Efeito conceitual:** comportamento sem fingir API/hook ainda não auditado.
- **Escopo:** self, target, party, summon, world, item, process etc.
- **Providers candidatos:** somente providers presentes/pertinentes.
- **Sinergias:** outras categorias/perks que combinam.
- **Antissinergias/tradeoffs:** quando aplicável.
- **Não deve fazer:** limites explícitos para evitar overlap.
- **Technical Audit:** preenchida somente na Task 9.

Uma Standard Perk não pode recriar uma customização específica de Ability que pertença a um `Txxxx`.

## 2. Transmutation Root — `Txxxx-<ability>.md`

Cada raiz deve conter:

- **ID editorial:** `Txxxx`.
- **Ability alvo:** identidade legível.
- **Provider/authority candidata:** origem da Ability.
- **Owner tree:** exatamente uma.
- **Status:** state machine.
- **Requisito conceitual de acesso:** Ability conhecida/desbloqueada.
- **Descrição da Ability base:** curta, suficiente para entender as decisões.
- **LEFT axis:** qual aspecto menor o grupo modifica.
- **RIGHT axis:** qual outro aspecto menor o grupo modifica.
- **METAMORPHOSIS axis:** que tipos de transformação profunda são válidos.
- **Lista de filhos LEFT:** links `.11-.19`.
- **Lista de filhos RIGHT:** links `.21-.29`.
- **Lista de filhos METAMORPHOSIS:** links `.31-.39`.
- **Exemplo de configuração:** uma combinação 1+1+1 coerente.
- **Boundary de exclusividade:** somente uma opção por grupo.
- **Não duplicação:** declaração de que não existe outra raiz para a mesma Ability.
- **Technical Audit:** preenchida na Task 9.

## 3. Transmutation Option — `Txxxx.xx-<slug>.md`

Cada filho deve conter:

- **ID editorial:** `Txxxx.xx`.
- **Pai:** `Txxxx`.
- **Grupo:** `LEFT`, `RIGHT` ou `METAMORPHOSIS`.
- **Status:** state machine.
- **Nome:** nome de jogador.
- **Intenção:** qual decisão cria.
- **Mudança conceitual:** comportamento esperado sem afirmar hook inexistente.
- **Tradeoff:** quando existir.
- **Compatibilidade intragrupo:** normalmente exclusiva por regra estrutural.
- **Compatibilidade intergrupo:** conflitos excepcionais devem ser documentados.
- **Affinity behavior:** se houver conversão, usar a abstração `Affinity` e listar somente candidatos, nunca afirmar suporte técnico sem auditoria.
- **Technical Audit:** versão, registry/API/boundary, authority, persistência, fallback/fail-closed.

### Regras dos IDs

- `.11-.19` LEFT.
- `.21-.29` RIGHT.
- `.31-.39` METAMORPHOSIS.
- `.10/.20/.30` reservados.
- mínimo 2 LEFT, 2 RIGHT, 3 METAMORPHOSIS.
- máximo 9 por grupo.

## 4. Specialization Container — `04-specialization-perks/<owner-tree>/<slug>/README.md`

Cada especialização deve conter:

- **Nome.**
- **Owner tree:** exatamente uma.
- **Type:** `PURE` ou `HYBRID`.
- **Required trees:** owner e secondary trees quando HYBRID.
- **Fantasia de build.**
- **Providers candidatos.**
- **Mecânica Assinatura:** sistema principal de decisão.
- **Estado persistente da mecânica:** descrito conceitualmente.
- **Como as Sxxxx alimentam a mecânica.**
- **Quantidade de Sxxxx atribuídas a esta especialização.**
- **Relações com outras especializações:** exclusividade, coexistência ou nenhum conflito.
- **Technical Audit:** preenchida depois.

Uma especialização HYBRID pode depender de duas ou mais árvores, mas sua quota conta somente para o owner.

## 5. Specialization Perk — `Sxxxx-<slug>.md`

Cada arquivo deve conter:

- **ID editorial:** `Sxxxx`.
- **Specialization:** slug/container.
- **Owner tree:** herdado e repetido explicitamente.
- **Status:** state machine.
- **Função na Mecânica Assinatura:** unlock, expand, specialize, tradeoff, bridge ou enhancement.
- **Efeito conceitual.**
- **Requisitos conceituais.**
- **Providers candidatos.**
- **Sinergias e limites.**
- **Technical Audit.**

Uma `Sxxxx` que não conversa nem com a fantasia nem com a Mecânica Assinatura precisa ser justificada como exceção ou removida.

## 6. Technical Audit — campos obrigatórios quando o item chega à Task 9

Para P/T/S que dependam de integração externa:

- provider e versão instalados;
- mod id;
- registry/resource id relevante;
- API/event/hook/boundary;
- server/client authority;
- fonte de verdade do estado;
- persistência;
- ordem de aplicação;
- stacking/dedup;
- optional-provider behavior;
- save/reload behavior;
- multiplayer behavior;
- VFX/presentation dependency;
- fallback;
- status final `TECH-AUDITED`, `FAIL-CLOSED` ou `REQUIRES-DESIGN-CHANGE`.

## 7. Regra editorial

O conceito deve ser escrito antes do detalhe técnico. A auditoria técnica pode provar que um conceito é inviável, mas não deve silenciosamente substituí-lo por outro efeito só porque existe um hook mais fácil.
