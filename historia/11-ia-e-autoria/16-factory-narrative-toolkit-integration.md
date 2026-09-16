# Integração com o Narrative Authoring Toolkit da Factory

## Escopo

Este arquivo registra o probe de compatibilidade do corpus real deste repositório com o contrato de profile revision 1 do `minecraft-mod-factory`.

O profile consumidor está em `factory-narrative-profile.json`. Ele pertence a este repositório porque paths, headings e regras editoriais são específicos do projeto.

## Pin validado

- Factory repository: `Gustavaopere/minecraft-mod-factory`;
- Factory commit: `86b83005cde89f26ad2ef03af43cf512bc085080`;
- profile contract revision: `1`.

A workflow `.github/workflows/narrative-factory-compat.yml` usa esse SHA de forma explícita; atualização do pin é uma decisão separada e revisável. O export neutro de authority é emitido pela Factory em `stdout` e redirecionado pelo próprio workflow para um caminho literal controlado pelo CI, evitando que argumentos CLI escolham destinos de escrita no filesystem.

## Corpus exercitado

O probe usa `historia/03-npcs/principais` como slice real e não fabrica lore para satisfazer tooling.

- `NPC-0001-aren-autoria.md` exercita provenance/source-grounding e limites de expansão;
- `NPC-0001-aren.md` exercita o contrato estrutural de relações sistêmicas e preserva a multidimensionalidade declarada no próprio documento;
- o hook de chronology/causality está configurado para `EVT`, mas o slice atual não contém registros `EVT-####`; a cobertura algorítmica de arestas/ciclos permanece no golden corpus da Factory até existirem eventos reais adequados no consumidor;
- o inventory real é exportado para um snapshot neutro com provenance e comparado sem qualquer escrita de volta no canon.

## Limites

Este profile é um gate de integração, não uma substituição para as authorities do projeto. Ele não promove hipóteses, não sincroniza Grimoire ↔ GitHub automaticamente, não inventa relações e não cria assets visuais.

O estado editorial em português continua governado pelos documentos/canon existentes. O probe não ativa `validate_dialogues.py` nem impõe um novo vocabulário editorial ao corpus legado.
