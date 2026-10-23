# Assets visuais de NPC

Este diretório contém apenas arquivos visuais reais, project-owned, associados aos NPCs da campanha.

O manifest físico é `historia/03-npcs/npc-visual-assets.json`. Ele é validado pelo workflow `NPC Visual Asset Policy` contra o validator da Minecraft Mod Factory pinado pelo repositório.

## Regra principal

Não criar placeholder, arquivo vazio, upscale falso ou imagem renomeada apenas para preencher o manifest.

Um asset só entra no manifest quando:
- o arquivo real já existe sob `historia/03-npcs/assets/`;
- o NPC possui identidade/lore reconciliada e asset brief source-grounded;
- a provenance/licença do arquivo é conhecida;
- dimensões, formato e SHA-256 foram medidos a partir do arquivo real.

## Convenção de nomes

Preferir:
- portrait master: `NPC-####-slug-portrait-master.png`;
- skin técnica: `NPC-####-slug-skin.png`;
- look-dev/concept adicional: `NPC-####-slug-concept-<descritor>.png`.

A convenção de nome melhora rastreabilidade, mas não substitui o `Entity ID` do brief nem os campos do manifest.

## Portrait master

Policy consumer-owned atual:
- **2048×2048 px**;
- **PNG**;
- sRGB conforme o asset brief;
- resolução-alvo nativa para `APPROVED MASTER` e `FINAL`;
- portrait/concept e skin são artefatos diferentes.

Derivados menores podem ser gerados para UI/documentação, mas não substituem o master.

Não marcar como `APPROVED MASTER` ou `FINAL` uma imagem que só foi ampliada para 2048×2048.

## Skin humanoide

Policy consumer-owned atual:
- **64×64 px**;
- **PNG RGBA**;
- resolução-alvo nativa para `SKIN CANDIDATE` e `FINAL`;
- modelo `classic`/`slim` deve vir do brief/aprovação do NPC;
- preservar alpha/segunda camada quando usada.

Não converter portrait diretamente em UV de skin.

## Estados aceitos pelo validator

- `LOOK-DEV` — exploração visual; não é aprovação de master.
- `CANDIDATE` — candidato visual ainda não aprovado como master.
- `APPROVED MASTER` — portrait aprovado e sujeito à policy de portrait master.
- `SKIN CANDIDATE` — skin candidata e sujeita à policy 64×64.
- `FINAL` — asset aprovado como final; `final_asset=true` só é válido neste estado.

Um estado editorial ou visual não substitui QA in-game.

## Campos mínimos de um asset real

Cada entrada real em `npc-visual-assets.json` precisa apontar para um arquivo existente e registrar:
- `path`;
- `sha256` hexadecimal de 64 caracteres;
- `width`;
- `height`;
- `format`;
- `status`;
- `asset_kind`;
- `native_target_resolution=true` quando exigido pela policy.

`final_asset=true` só pode ser usado com `status="FINAL"`.

## Exemplo de fluxo

1. concluir/revisar o asset brief source-grounded do NPC;
2. produzir o arquivo visual real;
3. salvar o arquivo neste diretório com nome rastreável;
4. medir resolução/formato e calcular SHA-256;
5. adicionar a entrada correspondente ao manifest;
6. executar `NPC Visual Asset Policy`;
7. fazer QA visual/in-game quando aplicável;
8. promover estado somente depois da evidência exigida pelo brief.

## Estado atual

O manifest pode permanecer com `"assets": []` enquanto nenhum arquivo real tiver sido produzido e aprovado para entrada no pipeline.

Isso é estado válido. Ausência de asset é preferível a registrar um placeholder como se fosse evidência de produção.
