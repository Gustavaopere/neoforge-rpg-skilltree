# Excalibur | Waystones Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur Waystones 1.1.zip`
- **Versão 1.21.1:** 1.1
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur Waystones 1.1.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física atual de 18/09/2026 confirma o provider-alvo `waystones-neoforge-1.21.1-21.1.45.jar`, mod id `waystones`, runtime `21.1.45`.
- Evidências históricas da Biblioteca mostram versões anteriores de Waystones, mas não substituem a authority física atual de 08/09.

## Propriedades do banco

- **Mod:** Excalibur | Waystones Support
- **Arquivo JAR:** `Excalibur Waystones 1.1.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.1
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat
- **Função:** Support pack visual medieval para Waystones, cobrindo waystones/warp plates e itens relacionados como shards e scrolls sem alterar teleport.
- **Dependências:** Excalibur base + Waystones 21.1.45. WaystonesSable 1.0.7 é integração separada e não deve ser considerada coberta automaticamente.
- **Sobreposição:** Deve ficar acima do Excalibur base. Outros Waystones retextures podem disputar os mesmos assets; WaystonesSable pode ter assets próprios fora do namespace coberto.
- **Compatibilidade/Riscos:** Resource pack físico 1.1 permanece compatível com a linha 1.21.x publicada. O target físico agora é Waystones 21.1.45. Riscos: assets novos do Waystones sem cobertura, addon WaystonesSable 1.0.7 em namespace próprio, e load order com outros retextures.
- **Observações:** Arquivo instalado `Excalibur Waystones 1.1.zip`. A página oficial atual expõe 1.1 como release principal para Minecraft 1.21.x; o antigo drift de indexação 1.0→1.1 deixou de ser pendência. O target físico atual é Waystones 21.1.45.
- **Procedência:** Captura Resource Packs do perfil em 08/09/2026 + modlist(1).txt física atual de 18/09/2026 (Waystones 21.1.45; WaystonesSable 1.0.7) + CurseForge oficial Excalibur | Waystones Support 1.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-waystones
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 18/09/2026 — resource pack físico permanece `Excalibur Waystones 1.1.zip` / 1.1; target físico atualizado para Waystones 21.1.45. CurseForge oficial atual também expõe 1.1 como release principal para 1.21.x. WaystonesSable 1.0.7 permanece integração separada.
- **Histórico da decisão:** 2026-09-18 — target físico Waystones atualizado de 21.1.44 para 21.1.45; resource pack permanece 1.1 e nenhuma decisão curatorial nova foi tomada.

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado:** `Excalibur Waystones 1.1.zip`, versão `1.1`. O alvo físico atual é Waystones `21.1.45`; `WaystonesSable 1.0.7` também está presente como integração separada.

## 1. Papel e authority
Excalibur | Waystones Support é uma camada visual medieval para Waystones. **Waystones** continua authority de teleport, waystone activation, warp plates, scroll behavior, costs, permissions e networking.

## 2. Cobertura confirmada
A documentação oficial do projeto descreve redesign de waystones/warp plates e itens relacionados, incluindo shards e scrolls. Registros indexados históricos ainda podem citar `1.0`, mas a página oficial atual e o arquivo físico instalado convergem em `1.1`.

## 3. Boundary de versão
A página oficial atual expõe `Excalibur Waystones 1.1.zip` como release principal para Minecraft 1.21.x. O antigo drift de indexação 1.0→1.1 não é mais uma pendência. O catálogo preserva `1.1` como versão física e publicada.

## 4. Stack físico
O target é Waystones `21.1.45`. `WaystonesSable 1.0.7` é outro mod e não é automaticamente coberto pelo resource pack; qualquer asset próprio desse addon exige evidência específica.

## 5. Load order e reload
O support pack deve ficar acima do Excalibur base. Resource reload altera apenas models/textures/icons; teleport network, nomes/links e estado de waystones não podem mudar.

## 6. Riscos
1. Asset novo de Waystones 21.1.45 não coberto.
2. Ausência de changelog detalhado da 1.1 limitar diagnóstico de diferenças internas de assets.
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
