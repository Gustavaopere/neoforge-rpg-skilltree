# 12.13 — Reuso do NeoSync, proveniência e atribuição

## Objetivo

Aproveitar o fork moderno do Sync de forma seletiva, auditável e compatível com sua licença, sem transformar o RPG Skill Tree em uma cópia integral de outro mod.

## Fonte de referência

Projeto de referência:

```text
breakinblocks/NeoSync
```

Branch/revisão exata usada durante implementação deve ser registrada no momento em que o código for copiado/adaptado.

A licença do repositório consultado é MIT. Isso permite uso, cópia, modificação e redistribuição desde que o aviso de copyright e a licença sejam preservados nas cópias/substanciais porções derivadas.

## Partes conceitualmente úteis já identificadas

- `api/shell/ShellState.java`;
- `api/shell/ShellStateComponent.java`;
- `ShellStateComponentFactoryRegistry`;
- `ShellStateManager`;
- eventos de sincronização;
- pipeline Shell Constructor / Shell Storage;
- selector/networking relacionados a shell switching.

O `ShellState` atual já separa shell artificial vazio de cópia completa e persiste owner, inventário, vida, fome, XP vanilla, dimensão e posição. O `ShellStateComponent` fornece um modelo extensível de estado anexável. Esses conceitos são particularmente adequados ao Stage 12.

## O que NÃO copiar automaticamente

Não importar por atacado:

- shaders;
- render pipeline customizado;
- GUIs completas;
- Treadmill;
- recipes/assets não utilizados;
- mixins sem necessidade demonstrada;
- compatibilidade JEI/Jade que não se aplique;
- workaround específico do NeoSync sem reproduzir a condição no RPG.

Cada trecho trazido aumenta superfície de manutenção.

## Preferência de implementação

Ordem:

1. reutilizar apenas ideia/API pattern quando reimplementação limpa for simples;
2. adaptar código MIT quando houver ganho técnico real;
3. preservar headers/notice quando derivação for substancial;
4. evitar fork interno monolítico.

## Registro de proveniência

Criar tabela versionada, por exemplo `docs/provenance/neosync.md` ou equivalente:

| Arquivo RPG | Fonte NeoSync | Commit fonte | Tipo | Alterações principais | Licença |
| --- | --- | --- | --- | --- | --- |

`Tipo` deve distinguir:

- inspiração arquitetural;
- adaptação;
- cópia modificada;
- asset derivado.

## Notices

Se houver código/asset substancialmente derivado:

- incluir texto da licença MIT aplicável em `THIRD_PARTY_NOTICES.md` ou estrutura equivalente;
- manter atribuição do autor/projeto conforme licença;
- não remover avisos existentes dos arquivos copiados sem substituição juridicamente equivalente.

## Namespace e API

Código incorporado deve usar namespace do RPG e contratos do Stage 12. Não manter `neosync:*` como ID público apenas porque o código veio da referência, salvo migração/compatibilidade explicitamente necessária.

## Divergência intencional

Documentar diferenças fundamentais:

- NeoSync shell pode ser cópia completa; nosso corpo novo é fresh progression por padrão;
- nosso `ownerUuid` nunca muda;
- `bodyId` é identidade de jornada;
- world scaling consulta corpo ativo;
- progressões modded usam `BodyStateProvider`;
- itemização Stage 11 permanece no ItemStack;
- dois frontends: tecnológico e místico.

## Atualizações upstream

Registrar versão/commit base permite futuramente monitorar NeoSync por:

- correções de dupe;
- crash recovery;
- networking;
- shell persistence;
- compatibilidade NeoForge.

Não aplicar mudanças upstream automaticamente sem auditoria das nossas invariantes.

## Critérios de aceite

- toda cópia substancial possui proveniência;
- licença MIT está preservada quando exigida;
- nenhum módulo inteiro é importado sem necessidade;
- divergências de design estão documentadas;
- atualização futura do NeoSync pode ser comparada contra um commit fonte conhecido.