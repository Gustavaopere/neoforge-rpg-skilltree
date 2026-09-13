# Pipeline Visual de NPCs

## Objetivo
Criar identidade visual reproduzível para NPCs sem confundir concept art com skin Minecraft e sem depender de créditos pagos.

## Ferramentas

Ordem preferencial:

1. geração de imagem já disponível no ChatGPT, quando incluída sem custo incremental;
2. Blockbench para edição, pintura e validação 2D/3D da skin;
3. editores gratuitos de imagem/pixel art quando necessários;
4. serviços externos com plano gratuito apenas como opcionais, nunca como dependência.

Se um serviço gratuito exigir compra de créditos para continuar o fluxo, ele deixa de ser ferramenta recomendada.

## Fluxo

1. Ler o dossiê canônico do NPC.
2. Extrair apenas características visuais relevantes.
3. Criar um brief com idade aparente, silhueta, rosto, cabelo, paleta, roupa, acessórios e elementos proibidos.
4. Gerar ou desenhar concept art/portrait.
5. Revisar deriva visual contra o brief.
6. Registrar no dossiê somente decisões aprovadas.
7. Produzir a skin Minecraft em etapa separada.
8. Validar a skin no Blockbench em preview 3D antes de considerá-la final.

## Regra de assets

- Concept art e portrait são referências visuais.
- Skin é um asset técnico separado, normalmente 64x64.
- A skin final deve ser validada no modelo correto de braço: classic/Steve ou slim/Alex.
- Não armazenar arquivos-fonte muito grandes no Git normal sem política de LFS.
- Preferir no repositório apenas assets finais otimizados e necessários ao projeto.
- Nomear assets por ID estável do NPC.

## Estrutura sugerida de nomes

- `NPC-####-portrait.png`
- `NPC-####-concept.png`
- `NPC-####-skin.png`
- `NPC-####-skin-alt-01.png`

A pasta física de assets só deve ser criada quando existir o primeiro arquivo final; não criar diretórios vazios apenas por planejamento.

## Brief visual mínimo

- NPC ID
- modelo de braço: classic/Steve ou slim/Alex
- idade aparente
- altura/proporção percebida
- silhueta
- tom de pele
- rosto e marcas distintivas
- cabelo/barba
- paleta principal e secundária
- roupa por camada
- acessórios
- sinais de profissão/facção permitidos
- elementos que não podem aparecer
- variações autorizadas

## Critérios de aceitação

1. Uma nova imagem do mesmo NPC continua reconhecível como a mesma pessoa sem depender do nome escrito na imagem.
2. A skin mantém os elementos identitários essenciais do concept art sem tentar reproduzir detalhes incompatíveis com 64x64.
3. O preview 3D não apresenta UV deslocado, transparência acidental ou camada externa quebrada.
4. O processo pode ser repetido sem ferramenta paga.
