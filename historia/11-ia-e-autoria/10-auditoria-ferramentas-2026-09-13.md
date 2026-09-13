# Auditoria de Ferramentas — 2026-09-13

## Objetivo
Registrar condições verificadas em 2026-09-13. Esta auditoria não substitui nova verificação futura de preço, licença ou disponibilidade e não concede autoridade canônica a nenhuma ferramenta.

## Grimoire/TTRPG.bot

**Papel no projeto:** authority principal de lore estruturada/Campaign Bible/Foundations quando os dados correspondentes estão registrados e a integração está acessível.

**Observação operacional:** a disponibilidade do conector pode variar entre sessões. Indisponibilidade temporária não autoriza substituir Grimoire por outra story bible; material que dependa dessa authority permanece pendente/fail-closed até reconciliação.

## AIStoryHub

**Situação verificada na data:** a plataforma declarava operação gratuita sem assinatura/créditos próprios e oferecia modelo embutido gratuito sem chave/cartão, com disponibilidade/qualidade sujeitas a mudança.

**Uso arquitetural atual:** experimento opcional de análise de voz/continuidade somente se oferecer capacidade não coberta pelo stack principal. Não é segunda Campaign Bible autoritativa e não é requisito do fluxo.

**Fontes:**
- https://aistoryhub.co/pricing
- https://aistoryhub.co/developers

## Ink / Inky

**Situação verificada:** Ink e Inky eram disponibilizados sob licença MIT.

**Uso aprovado:** candidato opcional para branching quando Markdown deixar de ser suficiente.

**Fontes:**
- https://www.inklestudios.com/ink/
- https://github.com/inkle/inky

## Blockbench

**Situação verificada:** gratuito e open-source; o site oficial declarava uso gratuito para qualquer tipo de projeto.

**Uso arquitetural atual:** ferramenta da pipeline visual compartilhada da Minecraft Mod Factory. Art direction, Blockbench e QA devem seguir os contratos da Factory, não regras paralelas neste repositório.

**Fontes:**
- https://blockbench.net/
- https://blockbench.net/wiki/blockbench/faq/

## Spindle

**Situação verificada:** projeto open-source MIT, local-first, com servidor MCP e armazenamento local.

**Uso arquitetural atual:** experimento/fallback para clientes MCP compatíveis somente quando resolver necessidade concreta; não instalar por padrão nem replicar toda a Campaign Bible.

**Fonte:**
- https://github.com/VerifiedOrganic/spindle

## story-architect-mcp

**Situação verificada:** projeto distribuído sob MIT, baseado em arquivos e MCP local.

**Uso arquitetural atual:** alternativa experimental de auditoria/organização; não é authority do projeto.

**Fonte:**
- https://github.com/PTCuong-1102/story-architect-mcp

## Yarn Spinner

**Situação verificada:** existia opção gratuita para jogos, mas a licença vigente possuía condições próprias além de simplesmente dizer “open source”.

**Uso aprovado:** apenas alternativa; revisar a licença da versão escolhida antes de incorporar código.

**Fontes:**
- https://www.yarnspinner.dev/install/
- https://github.com/YarnSpinnerTool/YarnSpinner-Godot-GDScript/blob/main/LICENSE.md

## ChatGPT e MCP

**Situação verificada na data:** ChatGPT podia conectar-se a MCPs remotos em contas elegíveis; leitura/busca e escrita/modificação possuíam disponibilidade diferente por plano/workspace, e servidores MCP locais exigiam mecanismo intermediário compatível.

**Implicação:** não desenhar o fluxo supondo escrita MCP completa. `historia/`/Git continuam disponíveis para autoria versionada; Grimoire deve ser reconciliado quando a alteração tocar sua authority e a conexão estiver disponível.

**Fonte:**
- https://help.openai.com/en/articles/12584461

## AINPC Engine

**Decisão:** não adotado no fluxo recomendado. O projeto não precisa de serviço adicional de NPC para cumprir os objetivos atuais de autoria, e a política de custo zero prefere recursos já disponíveis/open-source/local-first.

## Regra de revalidação
Antes de promover qualquer ferramenta opcional para dependência recomendada, repetir a auditoria, atualizar a data e verificar se a função ainda é gratuita e necessária frente ao stack Grimoire + GitHub + tooling local.
