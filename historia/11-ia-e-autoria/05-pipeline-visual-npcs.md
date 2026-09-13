# Pipeline Visual de NPCs

## Objetivo
Criar identidade visual reproduzível para NPCs sem confundir concept art com asset técnico e sem depender de créditos pagos.

## Authority visual

A infraestrutura visual compartilhada pertence à Minecraft Mod Factory. Este repositório registra identidade narrativa, contexto e restrições do NPC; a Factory governa art direction, Blockbench e QA visual reutilizáveis.

Entrypoints da Factory:

- `art/VISUAL-STYLE-BIBLE.md`;
- `art/skills/minecraft-asset-art-direction/SKILL.md`;
- `art/templates/ASSET-BRIEF.md`;
- `art/skills/minecraft-blockbench-geckolib/SKILL.md`;
- `art/skills/minecraft-visual-qa/SKILL.md`;
- `art/standards/VISUAL-QA.md`.

## Ferramentas

Ordem preferencial:

1. geração de imagem disponível no ChatGPT, quando sem custo incremental, somente para concept/look-dev;
2. pipeline visual/Blockbench da Minecraft Mod Factory;
3. editores gratuitos de imagem/pixel art quando necessários;
4. serviços externos gratuitos apenas como opcionais.

Se uma ferramenta passar a exigir pagamento para manter o fluxo, ela deixa de ser dependência recomendada.

## Fluxo

1. Ler o dossiê do NPC e a Visual Style Bible da Factory.
2. Criar um asset brief específico com o template da Factory.
3. Definir silhueta, proporção, paleta/materials, roupa, acessórios, elementos proibidos e contextos de visualização.
4. Escolher resolução/texel density apenas com evidência adequada ao asset alvo; não criar número global por conveniência.
5. Produzir concept/portrait apenas como referência visual.
6. Revisar identidade contra o brief.
7. Produzir skin/texture técnica separadamente no formato realmente exigido pelo alvo.
8. Validar estrutura, UV, camadas e leitura no Blockbench/pipeline da Factory.
9. Executar QA in-game antes de marcar o asset como final.

## Regras

- Concept art e portrait não são skin/UV final.
- Modelo corporal deve ser registrado e validado no alvo real.
- Imagens-fonte grandes não devem entrar no Git sem uma política adequada de armazenamento.
- Assets devem usar o ID estável do NPC no nome.
- Referência visual externa não autoriza copiar pixels, modelo ou textura.
- Export bem-sucedido não equivale a aprovação visual.

## Nomes sugeridos

- `NPC-####-portrait.png`
- `NPC-####-concept.png`
- `NPC-####-skin.png`
- `NPC-####-skin-alt-01.png`

A pasta de assets só deve ser criada quando existir o primeiro arquivo real.

## Conteúdo narrativo mínimo do brief

- NPC ID;
- função narrativa;
- contextos típicos de visualização;
- idade aparente e proporção percebida;
- silhueta em uma frase;
- rosto/cabelo/barba e marcas distintivas;
- paleta/materials;
- roupa e acessórios;
- sinais de profissão/facção permitidos;
- elementos proibidos;
- variações autorizadas;
- referências canônicas;
- pendências ainda não decididas.

## Critérios de aceitação

1. O NPC continua reconhecível entre portrait, concept e skin.
2. O asset técnico preserva identidade sem forçar detalhes incompatíveis com a resolução aprovada.
3. Não há UV deslocado, transparência acidental ou camada quebrada.
4. A leitura funciona em escala real de gameplay.
5. O QA final segue a Factory.
6. O fluxo continua possível sem ferramenta paga.
