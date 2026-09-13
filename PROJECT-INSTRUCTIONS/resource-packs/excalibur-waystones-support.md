# Excalibur | Waystones Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db81e8826dc95a79818344
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur Waystones 1.1.zip`
- **Versão 1.21.1:** 1.1
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur Waystones 1.1.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física acessível de 08/09/2026 confirma o provider-alvo `waystones-neoforge-1.21.1-21.1.44.jar`, mod id `waystones`, runtime `21.1.44`.
- Evidências históricas da Biblioteca mostram versões anteriores de Waystones, mas não substituem a authority física atual de 08/09.

## Propriedades do banco

- **Mod:** Excalibur | Waystones Support
- **Arquivo JAR:** `Excalibur Waystones 1.1.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat
- **Função:** Support pack visual medieval para Waystones, cobrindo waystones/warp plates e itens relacionados como shards e scrolls sem alterar teleport.
- **Dependências:** Excalibur base + Waystones 21.1.44. WaystonesSable 1.0.7 é integração separada e não deve ser considerada coberta automaticamente.
- **Sobreposição:** Deve ficar acima do Excalibur base. Outros Waystones retextures podem disputar os mesmos assets; WaystonesSable pode ter assets próprios fora do namespace coberto.
- **Compatibilidade/Riscos:** Arquivo físico é 1.1, enquanto a página oficial indexada ainda expõe 1.0; changelog exato 1.1 permanece fail-closed. Riscos adicionais: assets novos de Waystones, addon Sable e load order.
- **Observações:** Arquivo instalado `Excalibur Waystones 1.1.zip`. Preservar versão 1.1 física; não inventar delta 1.0→1.1 sem changelog oficial suficiente.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial do Excalibur Waystones e evidência de distribuição do arquivo 1.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-waystones
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; Waystones 21.1.44, versão física 1.1, cobertura, drift documental, load order, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur Waystones 1.1.zip`, versão `1.1`. O alvo físico atual é Waystones `21.1.44`; `WaystonesSable 1.0.7` também está presente como integração separada.

## 1. Papel e authority
Excalibur | Waystones Support é uma camada visual medieval para Waystones. **Waystones** continua authority de teleport, waystone activation, warp plates, scroll behavior, costs, permissions e networking.

## 2. Cobertura confirmada
A documentação oficial do projeto descreve redesign de waystones/warp plates e itens relacionados, incluindo shards e scrolls. A publicação indexada mais antiga expõe `1.0`; o arquivo físico instalado é `1.1`, que deve permanecer como autoridade de versão instalada.

## 3. Boundary de versão
Há evidência externa de distribuição para `Excalibur Waystones 1.1.zip`, enquanto a página principal indexada ainda pode exibir 1.0. O catálogo preserva `1.1` sem inventar changelog específico da 1.1. A diferença entre 1.0 e 1.1 permanece fail-closed.

## 4. Stack físico
O target é Waystones `21.1.44`. `WaystonesSable 1.0.7` é outro mod e não é automaticamente coberto pelo resource pack; qualquer asset próprio desse addon exige evidência específica.

## 5. Load order e reload
O support pack deve ficar acima do Excalibur base. Resource reload altera apenas models/textures/icons; teleport network, nomes/links e estado de waystones não podem mudar.

## 6. Riscos
1. Asset novo de Waystones 21.1.44 não coberto.
2. Diferença não documentada entre support pack 1.0 e 1.1.
3. WaystonesSable usar namespace próprio sem retexture.
4. Outro Waystones resource pack vencer os mesmos paths.
5. GUI/item icon ficar inconsistente após update/reload.

## 7. Matriz de testes
- [ ] Conferir waystone normal e variantes disponíveis.
- [ ] Conferir warp plates.
- [ ] Conferir shards/scrolls e icons.
- [ ] Testar ativação/teleport com pack on/off sem mudança funcional.
- [ ] Identificar assets próprios de WaystonesSable, se houver.
- [ ] Resource reload sem missing models/textures.

Nenhum teste foi marcado como aprovado.

## 8. Evidências e limite
O projeto oficial confirma o propósito visual medieval e o escopo geral; o arquivo físico confirma a instalação da 1.1. Como o changelog exato da 1.1 não está publicado de forma suficiente na página indexada, ele não é inventado.

> Boundary canônico: **Waystones controla teleport e estado; este pack controla apenas apresentação visual de assets cobertos**.
