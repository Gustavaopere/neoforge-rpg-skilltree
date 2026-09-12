# UnChipped

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db817781add676caa5545a
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `unchipped-1.21-1.2.jar`, mod id `mr_unchipped`, runtime `1.21-1.2`; Chipped 4.0.2 presente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, UnChipped 1.21-1.2 e Chipped 4.0.2 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** UnChipped
- **Arquivo JAR:** `unchipped-1.21-1.2.jar`
- **Versão 1.21.1:** 1.21-1.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, QoL, Automação
- **Função:** Addon/data layer server-side de Chipped que adiciona recipes de stonecutter para converter variantes decorativas de volta aos blocos de origem, restaurando reversibilidade sem registrar um segundo catálogo de blocos.
- **Dependências:** Chipped é required content; pack instala Chipped 4.0.2. NeoForge 1.21.1. JEI pode visualizar recipes, mas não é dependency funcional afirmada.
- **Sobreposição:** Não substitui Chipped; complementa-o com recipes reversos. Pode colidir apenas com outros datapacks/recipes que alterem a mesma conversão.
- **Compatibilidade/Riscos:** Recipe loops/dupe, drift de cobertura com novas variantes Chipped, missing target ids e colisões com datapacks. 1.21-1.2 corrige Borderless Bricks para minecraft:bricks. Não afirmar integração Create específica sem evidência da build.
- **Observações:** mod id físico `mr_unchipped`; runtime `1.21-1.2`. Server-side. Não adiciona blocos/itens próprios confirmados; superfície principal é recipe/data. Decisão Sem decisão preservada.
- **Procedência:** modlist.txt física atual de 11/09/2026 + Modrinth/CurseForge oficiais UnChipped 1.21-1.2 + Chipped físico 4.0.2. Ambiente server-side, required content Chipped e changelog Borderless Bricks revalidados; nenhum teste de recipe/datapack foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/unchipped/files/7384181 ; https://modrinth.com/mod/unchipped/version/1.21-1.2
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — UnChipped 1.21-1.2 permanece exatamente instalado; reversão Chipped via stonecutter, Borderless Bricks fix, recipe lifecycle, riscos e testes preservados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-30

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `unchipped-1.21-1.2.jar`, mod id `mr_unchipped`, versão `1.21-1.2`. UnChipped é uma **camada de recipes server-side para Chipped**: cria caminhos de reversão que convertem variantes decorativas do Chipped de volta aos blocos de origem usando stonecutter. Não adiciona um segundo catálogo decorativo próprio.

## 1. Identidade, versão e papel
- **Mod:** UnChipped.
- **JAR físico:** `unchipped-1.21-1.2.jar`.
- **Mod id físico:** `mr_unchipped`.
- **Versão:** `1.21-1.2`.
- **Minecraft:** 1.21–1.21.1.
- **Loader físico:** NeoForge.
- **Canal:** Release.
- **Publicação:** 27/12/2025.
- **Ambiente oficial:** server-side / singleplayer.
- **Decisão vigente:** Sem decisão; preservada.

## 2. Authority e ownership
- **Chipped 4.0.2:** authority das variantes decorativas e do conteúdo-base que elas representam.
- **UnChipped:** authority apenas dos recipes de reversão que adiciona.

UnChipped não deve duplicar registries de blocos do Chipped, alterar seus atributos ou assumir ownership de máquinas/mesas do mod-base.

## 3. Função principal
A descrição oficial é direta: **craft Chipped's blocks back to their origin using a stonecutter**.

O problema resolvido é a irreversibilidade de muitas variantes decorativas: o jogador pode converter uma variante de volta ao material/bloco-base correspondente, reduzindo desperdício e lock-in de recipe.

## 4. Conteúdo registrado
A superfície confirmada é de **recipes/data**, não de novo content registry.

Não foram confirmados novos mobs, items, blocks, BlockEntities, effects ou attributes próprios desta build.

O dossiê não enumera todas as variantes Chipped individualmente porque o upstream não publica uma matriz completa estável de recipes da 1.21-1.2; a validação operacional deve ser feita no stonecutter/recipe viewer do runtime.

## 5. Dependência Chipped
A página oficial da versão `1.21-1.2` marca **Chipped** como required content/dependency.

A modlist física instala `chipped-neoforge-1.21.1-4.0.2.jar`, mod id `chipped`, versão `4.0.2`, portanto a dependência funcional está presente.

Isto não prova cobertura de 100% de toda variante de Chipped 4.0.2; apenas confirma que o provider exigido existe.

## 6. Stonecutter como superfície operacional
O fluxo publicado usa **stonecutter**. Testes devem confirmar:
- variante Chipped válida aparece como input;
- output corresponde ao bloco-base esperado;
- quantidade de input/output não cria ganho material;
- recipe reversa não forma loop de multiplicação com recipes Chipped;
- tags/variants modded não são convertidas para material errado.

## 7. Release 1.21-1.2 — mudanças exatas
Changelog oficial da build:
- remove arquivos não usados/nomeados incorretamente;
- corrige **Borderless Bricks** para retornar `minecraft:bricks` em vez do id inexistente `minecraft:borderless_bricks`.

Esse segundo item é regression gate direto: o recipe não deve gerar missing item/recipe target.

## 8. Client / server
O upstream marca o projeto como **server-side**. O servidor fornece os recipes; o cliente os recebe pelo fluxo normal de recipe/data synchronization.

Não é necessário atribuir renderer, keybind ou GUI própria ao mod. A UI utilizada é a stonecutter vanilla e qualquer recipe viewer instalado.

## 9. Lifecycle e dados
Validar:
- datapack/recipe load no world boot;
- dedicated server boot;
- recipe sync no login/relog;
- datapack reload quando suportado;
- update de Chipped em cópia de teste;
- world restart sem recipe ids órfãos;
- remoção do addon sem remover os blocos Chipped existentes.

Como recipes são dados, incompatibilidades podem aparecer como missing recipe/invalid output mesmo sem crash imediato.

## 10. Integrações concretas no pack
- **Chipped 4.0.2:** dependência obrigatória e provider do conteúdo convertido.
- **JEI 19.53.0.426:** pode ser usado para inspecionar recipes; é ferramenta de visualização, não dependency funcional afirmada.
- **Create/stone processing:** não há evidência suficiente nesta auditoria para afirmar recipes automáticos específicos de Create na build 1.21-1.2; não documentar essa integração apenas porque CurseForge categoriza o projeto em Processing/Create.

## 11. Riscos técnicos
1. **Recipe loop/dupe:** conversão ida↔volta não pode aumentar material.
2. **Version drift com Chipped:** novo conjunto de variantes pode não estar coberto pela versão atual de UnChipped.
3. **Missing target id:** bug corrigido em Borderless Bricks mostra que ids de output são superfície real.
4. **Recipe collision:** outro datapack pode registrar saída alternativa para mesma variante.
5. **Partial coverage:** ausência de recipe para uma variante não implica necessariamente erro de load; pode ser lacuna de suporte.
6. **Client recipe cache:** após reload/server switch, cliente precisa refletir recipes atuais.

## 12. Matriz de testes
- [ ] Dedicated server inicia com UnChipped 1.21-1.2 + Chipped 4.0.2.
- [ ] Recipes aparecem no stonecutter para amostras de madeira, pedra, tijolos e outras famílias presentes.
- [ ] Borderless Bricks retorna `minecraft:bricks` corretamente.
- [ ] Conversão ida→volta não multiplica material.
- [ ] JEI mostra outputs coerentes com stonecutter runtime.
- [ ] Datapack reload/relog mantém recipe sync.
- [ ] Variant sem recipe falha apenas por ausência, sem invalid registry/crash.
- [ ] Remover UnChipped em cópia de teste não remove os blocos já existentes do Chipped.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 13. Evidências
- Modlist física canônica 08/09/2026: `unchipped-1.21-1.2.jar`, mod id `mr_unchipped`, Chipped 4.0.2 presente.
- CurseForge oficial UnChipped: objetivo de converter blocos Chipped ao original via stonecutter; release 1.21-1.2 para NeoForge/1.21.1.
- Modrinth oficial da versão 1.21-1.2: ambiente server-side, Chipped como required content e changelog da correção Borderless Bricks.

## 14. Revalidação física — 11/09/2026
O runtime físico continua exatamente `unchipped-1.21-1.2.jar`, mod id `mr_unchipped`, versão `1.21-1.2`, com Chipped `4.0.2` presente como provider obrigatório. A release oficial continua sendo a linha pertinente para 1.21/1.21.1 e permanece server-side.

O fix de Borderless Bricks para `minecraft:bricks` e o papel estritamente recipe/data layer continuam válidos. Nenhum teste de stonecutter, loop ida↔volta, JEI, datapack reload ou recipe sync foi executado nesta recatalogação.
