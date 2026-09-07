# Minecraft AI Skill Infrastructure — Design

## Context

O projeto precisa de instruções Minecraft duráveis e legíveis pelo GitHub porque a disponibilidade de skills nativas varia entre chats. O usuário forneceu 20 ZIPs; a auditoria encontrou material útil, exemplos multi-versão e um pacote incompleto.

## Arquitetura

`PROJECT-INSTRUCTIONS/skills/` é o entrypoint. `ROUTER.md` seleciona workflow; `VERSION-AUTHORITY.md` impede vazamento de versões/loaders; `USER-GUIDED-WORKFLOW.md` controla etapas manuais. `library/` contém adaptações auditadas dos 20 entrypoints recebidos. SHA-256 dos ZIPs e riscos ficam registrados em `SOURCE-MANIFEST.md` e `SOURCE-AUDIT.md`.

Skills específicas de art direction, Blockbench/GeckoLib, VFX/spells, áudio e visual QA serão irmãs de `library/`, para que regras específicas do projeto possam ser mais restritivas sem reescrever a proveniência das skills recebidas.

## Autoridade

Código/build/JAR real > evidência oficial/provider da versão exata > decisões do projeto > skill de projeto > library skill > exemplo de outra versão. Informação version-sensitive não comprovada falha fechada.

## Interação manual

Quando o usuário precisar agir, uma ação atômica verificável por vez. Trabalho que o agente consegue executar sozinho continua normalmente.

## Validação

Um validador verifica exatamente 20 entrypoints, metadata `name` coerente e presença dos arquivos de governança.

## Fases seguintes

1. Art direction + Blockbench/GeckoLib.
2. VFX + spell production + áudio + visual QA.
3. Plugin auxiliar do Blockbench somente após verificação da API.
4. Golden samples depois do pipeline validado.
