# Compêndio Natural — Fontes de assets

Este manifesto cobre **assets incorporados ao RPG Skill Tree** sob `src/main/resources/assets/rpgskilltree/compendium/` e outros diretórios do Compêndio que vierem a conter imagens, texturas, modelos, ícones ou mídia externa.

No momento da implementação do Stage 10.01 não existem assets do Compêndio incorporados. A lista vazia é intencional e significa que nenhum asset de Biology Dictionary, Field Guide ou Wildex foi copiado.

<!-- compendium-assets:v1 -->
```json
{
  "schema": 1,
  "assets": []
}
```

## Campos obrigatórios para qualquer asset futuro

Cada asset adicionado ao Compêndio deverá receber um registro contendo, no mínimo:

- `path` — caminho exato no repositório;
- `origin` — `PROJECT_ORIGINAL`, `EXTERNAL_REUSE` ou `DERIVED`;
- `author`;
- `license`;
- `source` — URL/identificador quando externo;
- `source_sha` ou versão congelada quando aplicável;
- `attribution` quando exigida;
- `notes` para adaptações/derivações.

Assets `EXTERNAL_REUSE` e `DERIVED` sem licença/permissão explícita são proibidos. Um upstream constar em `UPSTREAM.md` não autoriza copiar seus assets.

## Política específica dos três projetos de referência

- **Biology Dictionary:** nenhum asset reutilizado; a auditoria não confirmou uma licença de asset separada e inequívoca no snapshot 1.21.1.
- **Field Guide:** nenhum asset reutilizado; o README do snapshot auditado declara assets como All Rights Reserved pelos respectivos criadores, salvo indicação em contrário.
- **Wildex:** nenhum asset reutilizado; o snapshot auditado está sob CC BY-NC 4.0 e o projeto evita incorporar material com restrição NonCommercial.

## Validação

O gate de proveniência deve falhar quando um arquivo aparecer no diretório de assets do Compêndio sem registro correspondente neste manifesto. Arquivos gerados automaticamente também precisam de origem declarada ou regra explícita do validador.
