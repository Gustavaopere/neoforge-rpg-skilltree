# Guia de Integração Gratuita de Ferramentas de Autoria

## Objetivo
Documentar como usar/conectar ferramentas auxiliares sem tornar o projeto dependente de serviços pagos e sem criar uma segunda Campaign Bible autoritativa.

## 1. Baseline obrigatório

Quando Grimoire/TTRPG.bot estiver acessível:

`Grimoire (lore estruturada) <-> reconciliação por ID/proveniência <-> historia/ no GitHub -> ChatGPT -> branch/PR -> revisão`

Quando Grimoire estiver temporariamente indisponível:

`historia/ no GitHub -> ChatGPT -> branch/PR -> revisão`

Nesse modo, decisões que dependam de lore central ausente ficam pendentes/fail-closed até reconciliação; não se inventa canon para manter throughput.

O fluxo deve continuar capaz de criar/revisar documentos editoriais, diálogos, evidências e briefs sem qualquer MCP adicional.

## 2. Grimoire/TTRPG.bot

É a integração narrativa prioritária do projeto porque já é a authority estruturada de Campaign Bible/Foundations, NPCs, organizações, locais, relações, conhecimento, segredos e estado da campanha quando esses dados estão registrados ali.

### Regra operacional

- pesquisar antes de criar elemento central;
- preservar IDs estáveis do GitHub como referência cruzada quando a plataforma permitir;
- não executar sincronização bidirecional cega;
- não copiar scaffolding genérico da plataforma como se fosse canon;
- não afirmar atualização no Grimoire quando a conexão/ferramenta não estiver disponível na sessão;
- detalhes de reconciliação estão em `12-grimoire-github-sync.md`.

A conexão concreta pode variar conforme o MCP/app disponível no ChatGPT. Não congelar aqui endpoint ou fluxo de UI que não esteja sob controle deste repositório; quando for necessário reconectar, verificar a integração atual e executar um passo manual verificável por vez.

## 3. Story tools adicionais — somente sob necessidade

AIStoryHub, Spindle, story-architect-mcp ou equivalentes não fazem parte do baseline. Só devem ser testados se entregarem uma capacidade específica que Grimoire + GitHub + tooling local não atendem adequadamente.

Regras:

1. custo incremental obrigatório zero;
2. nenhuma segunda Campaign Bible autoritativa;
3. começar com amostra pequena/não sensível;
4. preferir leitura/análise antes de qualquer escrita;
5. preservar IDs/proveniência;
6. confirmar possibilidade de export/recuperação do trabalho;
7. abandonar o serviço se a função essencial passar a exigir pagamento.

### AIStoryHub

Na auditoria de 2026-09-13 havia caminho gratuito declarado pelo fornecedor. Esse estado é temporal e precisa ser verificado novamente antes de qualquer PoC. Não cadastrar API paga para manter o fluxo.

### Spindle / story-architect-mcp

Alternativas open-source/local-first úteis em clientes MCP compatíveis para revisão/continuidade. Não são requisitos para o ChatGPT nem justificam túnel/infraestrutura apenas para duplicar funções já cobertas.

## 4. Ink/Inky — branching gratuito

Ink/Inky pode ser avaliado quando um diálogo aprovado deixar de ser legível/manutenível em Markdown.

PoC futuro:

1. selecionar diálogo ramificado já aprovado;
2. converter uma cópia para `.ink`;
3. validar escolhas e condições no Inky;
4. exportar/compilar deterministicamente;
5. comparar todas as rotas com o documento editorial;
6. só então avaliar adapter no Narrative Core.

Até lá, `historia/12-dialogos/` permanece o formato editorial.

## 5. Visual / Blockbench

A pipeline visual compartilhada pertence à Minecraft Mod Factory. O fluxo deste projeto fornece o asset brief narrativo e usa os entrypoints canônicos da Factory para Blockbench e QA.

Fluxo:

1. definir identidade/brief do NPC;
2. consultar a Visual Style Bible e assets project-owned relevantes;
3. produzir concept/portrait apenas como look-dev;
4. produzir skin/texture técnica separadamente;
5. validar resolução/texel density, UV/camadas e alvo corporal com evidência;
6. executar QA visual no Minecraft real;
7. manter estado `PENDING` enquanto a evidência final não existir.

## 6. Tooling local sem conta/API

Na branch que contém o stack de autoria:

```bash
python historia/tools/story_inventory.py --format json
python historia/tools/validate_story.py --strict-references
python historia/tools/validate_dialogues.py
python -m unittest discover -s historia/tools/tests -v
```

Esses comandos usam apenas Python padrão e não dependem de rede.

## 7. Critério de interrupção

Se qualquer integração opcional exigir pagamento para uma função essencial, criar lock-in crítico, exigir autoridade sobre o canon/runtime ou impedir recuperar o trabalho, interromper o uso e voltar ao baseline Grimoire + GitHub + ChatGPT/tooling local (ou GitHub + tooling local enquanto Grimoire estiver inacessível).
