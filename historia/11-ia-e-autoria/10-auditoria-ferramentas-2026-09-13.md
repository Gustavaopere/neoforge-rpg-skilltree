# Auditoria de Ferramentas — 2026-09-13

## Objetivo
Registrar as condições verificadas no momento da adoção. Esta auditoria não substitui nova verificação futura de preço, licença ou disponibilidade.

## AIStoryHub

**Situação verificada:** plataforma declarada gratuita, sem assinatura ou compra de créditos própria; disponibiliza modelo embutido gratuito sem chave/cartão, com aviso de que qualidade/disponibilidade podem variar.

**Uso aprovado:** PoC opcional de story bible/voz, sem cadastrar API paga.

**Fontes:**
- https://aistoryhub.co/pricing
- https://aistoryhub.co/developers

## Ink / Inky

**Situação verificada:** Ink e Inky são disponibilizados sob licença MIT.

**Uso aprovado:** framework candidato para branching complexo.

**Fontes:**
- https://www.inklestudios.com/ink/
- https://github.com/inkle/inky

## Blockbench

**Situação verificada:** gratuito e open-source; o site oficial declara uso gratuito para qualquer tipo de projeto.

**Uso aprovado:** validação e edição de skins/modelos Minecraft.

**Fontes:**
- https://blockbench.net/
- https://blockbench.net/wiki/blockbench/faq/

## Spindle

**Situação verificada:** projeto open-source MIT, local-first, com servidor MCP e armazenamento local.

**Uso aprovado:** fallback experimental para clientes MCP locais compatíveis; não instalar por padrão.

**Fonte:**
- https://github.com/VerifiedOrganic/spindle

## story-architect-mcp

**Situação verificada:** projeto distribuído sob MIT, baseado em arquivos e MCP local.

**Uso aprovado:** fallback experimental para auditoria/organização em clientes MCP compatíveis.

**Fonte:**
- https://github.com/PTCuong-1102/story-architect-mcp

## Yarn Spinner

**Situação verificada:** existe opção gratuita para jogos, mas a licença vigente possui condições próprias além de simplesmente dizer “open source”.

**Uso aprovado:** apenas alternativa; revisar licença específica da versão antes de incorporar código.

**Fontes:**
- https://www.yarnspinner.dev/install/
- https://github.com/YarnSpinnerTool/YarnSpinner-Godot-GDScript/blob/main/LICENSE.md

## ChatGPT e MCP

**Situação verificada:** ChatGPT conecta-se a MCPs remotos em contas elegíveis. O suporte completo de escrita/modificação é mais restrito que leitura/busca e depende do plano/workspace. Servidores MCP locais não são conectados diretamente sem mecanismo intermediário compatível.

**Implicação:** não desenhar o fluxo editorial supondo que o ChatGPT sempre terá escrita MCP completa. GitHub continua sendo o caminho canônico de escrita.

**Fonte:**
- https://help.openai.com/en/articles/12584461

## AINPC Engine

**Decisão:** não adotado no fluxo recomendado. O projeto não precisa de um serviço adicional de NPC para cumprir os objetivos de autoria, e a política de custo zero prefere soluções locais ou recursos já disponíveis.

## Regra de revalidação
Antes de promover qualquer ferramenta opcional para dependência recomendada, repetir esta auditoria e atualizar a data.
