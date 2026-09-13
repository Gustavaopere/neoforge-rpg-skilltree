# Guia de Integração Gratuita de Ferramentas de Autoria

## Objetivo
Documentar como conectar ferramentas auxiliares sem tornar o projeto dependente de serviços pagos.

## 1. Fluxo que funciona sem integração externa

Este é o baseline obrigatório:

`historia/ no GitHub -> ChatGPT -> branch/PR -> revisão -> main`

Ele deve permitir criar e revisar NPCs, arcos, quests, diálogos e briefs visuais mesmo que todos os MCPs externos desapareçam.

## 2. AIStoryHub — PoC remoto gratuito

### Motivo da escolha
Na verificação de 2026-09-13, o AIStoryHub declarava plataforma sem assinatura e oferecia um modelo embutido gratuito sem necessidade de chave paga. Esse estado precisa ser verificado novamente antes de uso prolongado.

### Endpoint MCP
`https://aistoryhub.co/mcp/mcp`

### Pré-condições

1. criar uma conta gratuita no AIStoryHub;
2. não cadastrar chave de API paga;
3. usar o modelo gratuito da plataforma para o PoC;
4. conectar apenas uma amostra pequena e não sensível do cânone inicialmente.

### ChatGPT
O suporte depende do plano e das permissões atuais do ChatGPT.

- MCP remoto com leitura/busca pode estar disponível em Developer Mode para contas elegíveis.
- suporte completo de escrita/alteração é mais restrito e pode depender de workspace Business/Enterprise/Edu.
- o projeto não pressupõe que ações de escrita MCP estejam disponíveis.

Quando a opção existir na conta:

1. abrir `Settings`;
2. abrir `Apps`;
3. abrir `Advanced Settings` e habilitar Developer Mode quando disponível;
4. criar/adicionar um app ou conector MCP personalizado;
5. informar o endpoint `https://aistoryhub.co/mcp/mcp`;
6. concluir o fluxo OAuth exibido pelo provedor;
7. revisar permissões antes de aceitar;
8. testar primeiro operações somente de leitura;
9. pedir ao ChatGPT para listar histórias/entradas disponíveis;
10. somente depois testar uma story bible descartável de exemplo.

Não conceder escrita direta no repositório GitHub por meio do AIStoryHub.

## 3. Spindle — fallback local-first/open-source

Spindle armazena sua story bible localmente e oferece ferramentas MCP para personagens, locais, facções, timeline, continuidade, branching e revisão.

### Limitação para este projeto
O ChatGPT não se conecta diretamente a um servidor MCP local por stdio. Portanto Spindle é opção para clientes MCP locais/compatíveis ou para um futuro túnel remoto controlado; não é necessário instalá-lo agora.

### Regra
Não criar uma segunda fonte canônica permanente. Se testado, importar apenas cópia derivada e comparar resultados com `historia/`.

## 4. story-architect-mcp — fallback baseado em arquivos

É uma alternativa MIT que trabalha com Markdown/JSON e snapshots. É útil se precisarmos de auditoria de continuidade em um cliente MCP local.

A mesma limitação do Spindle se aplica: não é conexão direta local com ChatGPT sem infraestrutura intermediária.

## 5. Ink/Inky — branching gratuito

Ink e Inky são gratuitos sob licença MIT.

Adotar somente quando um diálogo deixar de ser legível/manutenível em Markdown. Antes disso, `historia/12-dialogos/` continua sendo o formato editorial principal.

PoC futuro:

1. selecionar um diálogo ramificado já aprovado;
2. converter cópia para `.ink`;
3. validar escolhas e condições no Inky;
4. exportar/compilar de modo determinístico;
5. comparar todas as rotas com o diálogo canônico;
6. só então avaliar adapter no Narrative Core.

## 6. Blockbench — skins

Blockbench é gratuito/open-source e é a ferramenta padrão para validar skins Minecraft.

Fluxo:

1. definir brief visual no dossiê do NPC;
2. gerar/desenhar referência visual sem custo incremental;
3. produzir a textura de skin separadamente;
4. abrir no Blockbench com o modelo de braço correto;
5. revisar frente, costas, laterais, UV, transparência e segunda camada;
6. exportar PNG final.

## 7. Critério de interrupção

Se qualquer ferramenta opcional solicitar pagamento para continuar uma função essencial do fluxo, interromper a integração e voltar imediatamente ao baseline GitHub + ChatGPT + ferramentas gratuitas locais.
