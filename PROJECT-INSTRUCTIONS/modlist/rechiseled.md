# Rechiseled

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db812e818df8e068588756
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `rechiseled-1.2.5-neoforge-mc1.21.jar`, mod id `rechiseled`, runtime `1.2.5`, mixin `rechiseled.mixins.json`; Fusion 1.3.15+a, SuperMartijn642's Config Library 1.1.8, Core Lib 1.1.24 e JEI 19.53.0.426 presentes
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Rechiseled 1.2.5 e suas dependências publicadas estão presentes; `Rechiseled: Create` não aparece como top-level. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Rechiseled
- **Arquivo JAR:** `rechiseled-1.2.5-neoforge-mc1.21.jar`
- **Versão 1.21.1:** 1.2.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, QoL
- **Função:** Adiciona grande variedade de variantes decorativas de blocos e sistema de chiseling para construção.
- **Dependências:** Required: Fusion (Connected Textures), SuperMartijn642's Config Lib e SuperMartijn642's Core Lib; todos presentes. JEI é optional e também está presente.
- **Sobreposição:** Compartilha domínio de construção com outros decorative mods, mas possui catálogo/chiseling/CTM próprios. Rechiseled: Create não está presente como top-level.
- **Compatibilidade/Riscos:** Riscos: dependency drift, CTM regression, stack/shift-click dupe/overflow, resource reload stale, recipe bloat e confusão com Rechiseled: Create ausente. Sobreposição decorativa não implica equivalência.
- **Observações:** Release 1.2.5 NeoForge para 1.21–1.21.1 de 22/06/2026. Delta: fix de non-connecting blocks conectando via Fusion e textura do Compacted Coal Block.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge/Modrinth oficiais Rechiseled 1.2.5 + relações oficiais de dependência.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/rechiseled
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Rechiseled 1.2.5 reconstruído: chisel/variants, Fusion CTM, required libs, stack/shift-click lineage, resource lifecycle, addon Create ausente, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `rechiseled-1.2.5-neoforge-mc1.21.jar`, mod id `rechiseled`, versão `1.2.5`, NeoForge compatível com Minecraft 1.21–1.21.1. Rechiseled adiciona grande catálogo de variantes decorativas e uma ferramenta/sistema de chiseling. A build exige Fusion, SuperMartijn642's Config Lib e SuperMartijn642's Core Lib, todos presentes fisicamente; JEI é opcional e também está presente. `Rechiseled: Create` continua ausente como top-level.

## 1. Identidade e papel
- **Mod:** Rechiseled.
- **JAR:** `rechiseled-1.2.5-neoforge-mc1.21.jar`.
- **Mod id:** `rechiseled`.
- **Runtime:** `1.2.5`.
- **Loader/jogo:** NeoForge; release suporta 1.21–1.21.1.
- **Canal:** Release.
- **Ambiente:** Client & Server.
- **Licença:** All Rights Reserved.
- **Papel:** decorative block variants + chiseling workflow.
- **Decisão:** Sem decisão.

## 2. Dependências obrigatórias
A publicação oficial declara como required:
- **Fusion (Connected Textures)**;
- **SuperMartijn642's Config Lib**;
- **SuperMartijn642's Core Lib**.

O pack físico contém `fusion-1.3.15a-neoforge-mc1.21.1.jar`, Config Lib 1.1.8 e Core Lib 1.1.24.

JEI é integração opcional e está presente em 19.53.0.426.

## 3. Chiseling workflow
O mod permite converter blocos compatíveis entre variantes decorativas por meio de seu sistema de chisel. O objetivo é preservar material/família enquanto muda apresentação.

Testar entrada, variante escolhida, stack count, durability/consumption do chisel conforme implementação e shift-click/container interactions.

## 4. Catálogo decorativo
Rechiseled expande fortemente quantidade de blocos/variants. Esse crescimento afeta:
- recipe browser;
- creative tabs;
- resource/model load;
- busca por material;
- custo de armazenamento/curadoria de blocos.

Quantidade de variantes não é incompatibilidade por si só; decisão deve considerar uso estético no pack.

## 5. Connected textures via Fusion
Fusion fornece connected-texture/render infrastructure exigida pelo Rechiseled. Variantes conectáveis precisam resolver vizinhança visual sem alterar block state funcional indevidamente.

Testar paredes grandes, corners, mixed variants, chunk borders e resource reload.

## 6. Release 1.2.5
A build 1.2.5 é Release NeoForge para Minecraft 1.21–1.21.1 publicada em 22/06/2026.

O changelog exato registra:
- correção de blocos **não conectáveis** que ainda estavam conectando com Fusion 1.3.0;
- correção da textura do **Compacted Coal Block**.

Esses dois pontos são regression gates diretos.

## 7. Lineage de stack/chisel
Releases anteriores recentes corrigiram problemas como:
- stack do chisel nem sempre atualizando com shift-click;
- stacks acima do limite/oversized após operações do chisel;
- crash ao salvar stack do chisel acima de 99.

Esses itens são lineage histórica, não bugs declarados da 1.2.5; devem ser mantidos como testes de regressão.

## 8. Inventário e atomicidade
Operações de chiseling devem conservar quantidade corretamente. Em shift-click ou spam de conversão, não pode haver dupe/perda nem stack inválido.

Testar inventário cheio, stack parcial, container aberto e troca rápida de variantes.

## 9. Recipes e JEI
JEI pode exibir recipes/variants, mas a recipe funcional continua server-side. Recipe visível que não executa indica divergence de data/sync.

Após `/reload`, validar que recipes não duplicam e que variantes removidas não deixam entradas stale.

## 10. Relação com Create
O addon **Rechiseled: Create** não aparece como top-level na modlist física atual. Portanto:
- não declarar variantes específicas de Create vindas desse addon;
- não marcar dependência/compat baseada em logs antigos;
- avaliar apenas Rechiseled base e integrations efetivamente presentes.

## 11. Sobreposição com construção
O pack possui muitos decorative/building mods. Rechiseled compartilha domínio visual, mas seu diferencial é catálogo próprio + conversão/chiseling + connected textures.

Sobreposição deve ser avaliada por famílias de blocos realmente usadas, não por contagem bruta de mods decorativos.

## 12. Client/server e persistência
Block IDs/variants e recipes precisam existir em ambos os lados. Render/CTM é client-facing, enquanto placement/break/crafting são server-authoritative.

Testar dedicated server, reconnect, world reload e cliente com resource state atualizado.

## 13. Resource lifecycle
Fusion/model/texture resources são sensíveis a reload. Verificar:
- connected textures após F3+T/resource reload;
- chunks já carregados;
- troca de resource pack;
- block model cache;
- variants não conectáveis continuando separadas.

## 14. Performance
Grande quantidade de variants pode aumentar asset/model load; connected textures adicionam trabalho de render. Medir build real com paredes extensas e shaders, sem presumir impacto grave apenas pelo catálogo.

## 15. Riscos
1. **Required-dependency drift:** Fusion/Core/Config Lib incompatíveis.
2. **CTM regression:** bloco não conectável conecta indevidamente.
3. **Stack/shift-click regression:** dupe/perda/oversized stack.
4. **Resource reload:** model/texture cache stale.
5. **Recipe bloat:** catálogo dificulta curadoria/JEI.
6. **Addon confusion:** Rechiseled: Create ausente tratado como instalado.
7. **Client/server recipe mismatch.**
8. **Decorative overlap:** redundância estética sem equivalência funcional.

## 16. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Rechiseled 1.2.5 + dependências atuais.
- [ ] Chisel converte um bloco entre variants mantendo contagem correta.
- [ ] Shift-click não duplica/perde stack.
- [ ] Stack não ultrapassa limite inválido após múltiplas operações.
- [ ] Parede conectável usa Fusion corretamente.
- [ ] Bloco não conectável não se conecta indevidamente.
- [ ] Compacted Coal Block usa textura corrigida.
- [ ] Resource reload mantém CTM/modelos sem stale cache.
- [ ] JEI lista recipes coerentes com o servidor.
- [ ] Rechiseled: Create permanece ausente sem missing dependency/ghost recipes.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 17. Evidências e limites
- Modlist física: Rechiseled 1.2.5; Fusion 1.3.15a; SuperMartijn642 Config Lib 1.1.8; Core Lib 1.1.24; JEI presente.
- Publicação oficial: Rechiseled 1.2.5 Release NeoForge, suporta 1.21–1.21.1, Client & Server.
- Relações oficiais: Fusion + Config Lib + Core Lib required; JEI optional.
- Changelog 1.2.5: fix de non-connecting blocks com Fusion e textura Compacted Coal Block.
- **Limite:** catálogo efetivamente usado em builds do mundo não foi quantificado; addon Rechiseled: Create não foi tratado como presente sem JAR top-level.
