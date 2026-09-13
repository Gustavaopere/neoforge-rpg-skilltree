# Pipeline Visual de NPCs

## Objetivo
Criar identidade visual reproduzível para NPCs sem confundir concept art com skin Minecraft.

## Ferramenta ativa
OpenArt é usado como ferramenta externa de concept art e portrait. A saída permanece candidata até revisão editorial.

## Fluxo

1. Ler o dossiê canônico do NPC.
2. Extrair apenas características visuais relevantes.
3. Criar um brief com idade aparente, silhueta, rosto, cabelo, paleta, roupa, acessórios e elementos proibidos.
4. Gerar concept art/portrait.
5. Revisar deriva visual contra o brief.
6. Registrar no dossiê somente decisões aprovadas.
7. Produzir a skin Minecraft em etapa separada.
8. Validar a skin em preview 3D antes de considerá-la final.

## Regra de assets

- Concept art e portrait são referências visuais.
- Skin é um asset técnico separado, normalmente 64x64.
- Não armazenar arquivos-fonte muito grandes no Git normal sem política de LFS.
- Preferir no repositório apenas assets finais otimizados e necessários ao projeto.
- Nomear assets por ID estável do NPC.

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

## Critério de aceitação
Uma nova imagem do mesmo NPC deve continuar reconhecível como a mesma pessoa sem depender do nome escrito na imagem.
