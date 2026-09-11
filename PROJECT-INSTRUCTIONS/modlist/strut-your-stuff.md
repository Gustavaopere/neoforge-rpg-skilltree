# Strut Your Stuff

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81d581a2f9f60cf6328d
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `struts-1.3.1.jar`, mod id `struts`, runtime `1.3.1`
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Strut Your Stuff 1.3.1 está presente. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Strut Your Stuff
- **Arquivo JAR:** `struts-1.3.1.jar`
- **Versão 1.21.1:** 1.3.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, Compat
- **Função:** Biblioteca para estruturas contínuas entre dois pontos, com collision shapes, interação, surface clipping, cable styles e integração de renderização com Flywheel.
- **Dependências:** Biblioteca NeoForge 1.21.1. A documentação confirma integração com Flywheel, mas não hard dependency universal. Consumidor causal específico no pack não foi resolvido neste lote; não remover até mapear dependências.
- **Sobreposição:** Infraestrutura compartilhada por addons; não confundir com conteúdo decorativo de struts adicionado por mods consumidores.
- **Compatibilidade/Riscos:** 1.3.1 corrige item drops e collision shapes desincronizadas em dedicated server. Riscos: consumidor não mapeado, cross-chunk endpoints, renderer↔collision mismatch, clipping em shapes complexos e duplicate drops. Decisão Sem decisão preservada.
- **Observações:** mod id `struts`; runtime 1.3.1. Metadata antiga que citava 1.3.0 como release atual foi supersedida: 1.3.1 é release oficial atual para NeoForge 1.21.1. Não classificar como Dependência sem consumidor causal confirmado.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Strut Your Stuff 1.3.1. Dossiê de 08/09 preservado; consumidor causal específico continua não resolvido e a decisão permanece Sem decisão.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/strut-your-stuff ; https://www.curseforge.com/minecraft/mc-mods/strut-your-stuff/files/8812965
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Strut Your Stuff 1.3.1 permanece exatamente instalado; spanning/collision, Flywheel integration, clipping/cable styles e fixes de drops/dedicated collision sync continuam válidos.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `struts-1.3.1.jar`, mod id `struts`, versão `1.3.1`. Strut Your Stuff é uma **biblioteca de estruturas contínuas entre dois pontos**, com colisão/interação, clipping de superfície, estilos de cabo e integração de renderização com Flywheel.

## 1. Identidade, versão e decisão
- **Mod:** Strut Your Stuff / Struts.
- **JAR físico:** `struts-1.3.1.jar`.
- **Mod id:** `struts`.
- **Versão:** `1.3.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Canal:** release estável oficial de 05/09/2026.
- **Decisão:** Sem decisão; preservada.

## 2. Papel arquitetural
O projeto se define explicitamente como **library** para mods que precisam criar blocos/estruturas que se estendem de um ponto a outro como uma estrutura contínua e interagível.

Struts não deve ser avaliado como content mod autônomo: o conteúdo visível normalmente vem de mods consumidores que registram seus próprios tipos/estilos usando a API.

## 3. Authority e ownership
- **Struts:** authority da geometria/estrutura spanning, collision shapes, clipping e suporte de rendering/cable behavior oferecido pela biblioteca.
- **Mod consumidor:** authority do bloco/item/receita/estética e das regras de gameplay específicas que usam a biblioteca.
- **Flywheel:** authority de seu renderer; Struts oferece integração para renderizar dentro desse sistema quando aplicável.

A presença de uma estrutura visual com aparência de cabo/strut não permite atribuir automaticamente seu conteúdo a esta biblioteca.

## 4. Collision structures
Feature publicada: struts podem criar **estruturas físicas de colisão** ao longo do span entre pontos.

Isso exige que shape lógico/servidor e shape renderizado/cliente permaneçam sincronizados para:
- movimento do jogador;
- raycast/interação;
- pathing quando relevante;
- break/place e seleção;
- dedicated server.

A release 1.3.1 corrige especificamente collision shapes desincronizadas em dedicated server.

## 5. Flywheel integration
A biblioteca oferece integração para que estruturas sejam renderizadas no sistema Flywheel, visando desempenho e coerência com consumidores Create/Flywheel.

A documentação auditada confirma **integração**, mas não estabelece Flywheel como hard dependency universal desta release; portanto não registrar dependência obrigatória apenas por existir essa feature.

Quando Flywheel participa do renderer, colisão/state continuam sendo gameplay/server state, não derivados do mesh renderizado.

## 6. Surface clipping
Strut models podem ser **clipados contra superfícies** para que o span encontre seus endpoints sem atravessar/mostrar geometria visual inadequada.

Esse clipping é superfície de rendering/model geometry e deve ser testado com:
- endpoints em faces distintas;
- blocos não cúbicos;
- transforms/contraptions quando um consumidor permitir;
- resource/model reload.

## 7. Cable styles
A API suporta estilos/comportamentos de cabo, incluindo capacidades publicadas para:
- desabilitar colisão;
- manter shapes específicos;
- aplicar **natural droop**.

Esses estilos são infraestrutura reutilizável. O fato de a biblioteca suportar um comportamento não significa que todo consumidor o habilite.

## 8. Interação e lifecycle
Como a estrutura liga endpoints, validar:
- placement/creation dos dois pontos;
- endpoint removido;
- break/drop do strut;
- chunk unload com endpoints em chunks diferentes;
- server restart;
- mudança de dimensão impossível/inválida;
- atualização de shape após movimento ou bloco adjacente quando consumidor suportar;
- interação/raycast em diferentes pontos do span.

## 9. Release 1.3.1
Changelog oficial 1.3.1:
- corrige novamente problema de **item drops**;
- corrige **collision shapes desincronizadas em dedicated server**.

Essas duas classes são regression gates obrigatórios deste runtime.

## 10. Client / server e multiplayer
- Servidor deve ser authority da existência da estrutura, collision e drops.
- Cliente renderiza span/model e deve receber shape/state coerente.
- Em multiplayer, dois clientes não podem discordar sobre onde há colisão/interação.
- Break simultâneo ou endpoint removido não pode gerar drops duplicados.

## 11. Consumidores e integração no pack
A modlist contém uma biblioteca Struts e diversos addons Create/estruturais, mas **este lote não estabeleceu causalmente qual JAR atual declara Struts como dependency obrigatória**. Por isso a decisão permanece Sem decisão.

Operacionalmente:
- não remover como “sem gameplay” até resolver consumidores;
- se um consumidor Create/Flywheel usar a API, testar renderer + collision em conjunto;
- conteúdo denominado “strut” em outros mods não prova dependência por nome.

## 12. Riscos técnicos
1. **Dependency invisível:** remover biblioteca pode impedir load de consumidor ainda não mapeado.
2. **Collision desync:** regression explícita corrigida em 1.3.1.
3. **Duplicate drops:** regression explícita de 1.3.1.
4. **Cross-chunk endpoints:** um endpoint carregado sem o outro pode deixar shape stale.
5. **Renderer mismatch:** Flywheel/model state pode divergir da collision real.
6. **Non-cubic clipping:** endpoints em shapes complexos podem gerar clipping visual ruim.
7. **Consumer misuse:** API correta não impede consumidor de registrar regras inconsistentes.

## 13. Matriz de testes
- [ ] Dedicated server boot com Struts 1.3.1.
- [ ] Consumidor real do pack é identificado antes de qualquer decisão de remoção.
- [ ] Span básico entre dois pontos cria collision/interação correta.
- [ ] Dois jogadores veem a mesma collision shape.
- [ ] Break gera drop exatamente uma vez.
- [ ] Remover endpoint limpa structure/drop sem duplicação.
- [ ] Endpoints em chunks diferentes sobrevivem unload/reload.
- [ ] Server restart preserva/reconstrói shape corretamente.
- [ ] Flywheel renderer, quando usado por consumidor, coincide com collision.
- [ ] Cable style sem collision realmente não bloqueia jogador.
- [ ] Natural droop/clipping não altera hitbox de forma inesperada.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 14. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão.
- CurseForge oficial Strut Your Stuff 1.3.1: release NeoForge 1.21.1 e fixes de item drop/collision sync.
- Página oficial do projeto: library spanning, collision structures, Flywheel integration, surface clipping e cable styles.

## 15. Revalidação física — 11/09/2026
A modlist atual mantém exatamente `struts-1.3.1.jar`, mod id `struts`, versão `1.3.1`. A release 1.3.1 continua sendo a build física e seus fixes de **item drops** e **collision shapes desincronizadas em dedicated server** permanecem regression gates.

Nenhum consumer causal obrigatório foi comprovado nesta recatalogação; por isso a decisão permanece **Sem decisão**, sem autorizar remoção. Nenhum teste runtime foi executado.
