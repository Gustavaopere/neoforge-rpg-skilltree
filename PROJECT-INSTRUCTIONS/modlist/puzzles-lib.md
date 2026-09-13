# Puzzles Lib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81059d94cc788894991c
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `PuzzlesLib-v21.1.60-mc1.21.1-NeoForge.jar`, mod id `puzzleslib`, runtime `21.1.60`, mixins `puzzleslib.common.mixins.json` e `puzzleslib.neoforge.mixins.json`; consumers Portable Hole e Overflowing Bars já confirmados
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Puzzles Lib 21.1.60 está presente; Portable Hole e Overflowing Bars permanecem consumers causais confirmados no catálogo. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Puzzles Lib
- **Arquivo JAR:** `PuzzlesLib-v21.1.60-mc1.21.1-NeoForge.jar`
- **Versão 1.21.1:** 21.1.60
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca
- **Função:** Biblioteca compartilhada do ecossistema Fuzs para registro, configuração, networking e utilidades de mods consumidores.
- **Dependências:** NeoForge 1.21.1. Consumers causais confirmados: Portable Hole 21.1.0 e Overflowing Bars 21.1.1; outros mods Fuzs podem também utilizar a library.
- **Sobreposição:** Biblioteca específica do ecossistema Fuzs; não substituível automaticamente por outras libs genéricas.
- **Compatibilidade/Riscos:** Library compartilhada sem gameplay próprio. Riscos: API/ABI drift, packet mismatch, config migration, classloading client/server e update isolado. Remover Puzzles Lib quebra consumers confirmados.
- **Observações:** Release 21.1.60 NeoForge 1.21.1 de 07/09/2026. Changelog da família backporta ColorCollection, VariantUtils, ProjectileHelper e helpers de gift/shearing loot.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial Puzzles Lib 21.1.60 + consumers Portable Hole/Overflowing Bars já auditados.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/puzzles-lib
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Puzzles Lib 21.1.60 reconstruída; pesquisa Rever→Verificado: shared code/config/networking, consumers Portable Hole/Overflowing Bars, backports 21.1.60, version coupling, riscos e testes.
- **Histórico da decisão:** 2026-08-26 — classificado como Dependência por consumer confirmado. 2026-09-10 — runtime 21.1.60 e relações revalidados; Estado da pesquisa fechado de Rever para Verificado.
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `PuzzlesLib-v21.1.60-mc1.21.1-NeoForge.jar`, mod id `puzzleslib`, versão `21.1.60`, NeoForge 1.21.1. Puzzles Lib é uma biblioteca compartilhada do ecossistema Fuzs. Há consumers físicos confirmados no pack — incluindo **Portable Hole** e **Overflowing Bars** — portanto a decisão permanece **Dependência**. A build exata 21.1.60 está confirmada e o antigo estado `Rever` pode ser fechado como `Verificado`.

## 1. Identidade e papel
- **Mod:** Puzzles Lib.
- **JAR:** `PuzzlesLib-v21.1.60-mc1.21.1-NeoForge.jar`.
- **Mod id:** `puzzleslib`.
- **Runtime:** `21.1.60`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Canal:** Release.
- **Ambiente:** Client & Server.
- **Licença:** Mozilla Public License 2.0.
- **Papel:** infraestrutura comum para registro, configuração, networking, eventos e helpers de mods consumidores.
- **Decisão:** Dependência.

## 2. Causalidade da dependência
A necessidade não vem apenas da categoria “library”. Consumers atuais já estão confirmados:
- **Portable Hole 21.1.0**, cujo alvo NeoForge declara Puzzles Lib;
- **Overflowing Bars 21.1.1**, já identificado no catálogo/runtime como consumer direto;
- outros mods do ecossistema podem também usá-la, mas não são necessários para sustentar esta decisão.

Remover Puzzles Lib isoladamente quebra dependency graph atual.

## 3. Shared-code boundary
Puzzles Lib concentra abstrações/reutilização para vários mods do mesmo ecossistema. Ela não deve ser catalogada como owner do gameplay desses consumers.

Se um stacktrace passa por Puzzles Lib, triagem precisa identificar o consumer que chamou o helper, o evento envolvido e a versão do contrato.

## 4. Registro e bootstrap
Libraries desse tipo participam de bootstrap/registro comum. A regra operacional é simples: update da library deve ser validado contra todos os consumers, não apenas por “o jogo abriu”.

Missing method/class pode aparecer só quando uma feature específica do consumer é usada.

## 5. Configuração
Puzzles Lib oferece infraestrutura compartilhada de config para consumers. A semântica de cada opção pertence ao mod consumidor.

Testar criação de config, leitura de arquivo existente, defaults após atualização e separação de opções client/server quando aplicável.

## 6. Networking
Consumers podem usar helpers de networking da library. Authority funcional continua no servidor para ações de gameplay.

Regression gates:
- join/handshake;
- envio de packet ligado a GUI/ação;
- reconnect;
- cliente com versão incompatível;
- packet não registrado ou ID drift.

## 7. Release 21.1.60
A build física `21.1.60` é Release oficial NeoForge 1.21.1 publicada em 07/09/2026.

O changelog da família 21.1.60 registra backports de utilitários como:
- `ColorCollection`;
- `VariantUtils`;
- `ProjectileHelper`;
- helpers de loot para gift/shearing.

Esses deltas pertencem à library; não significa que todos estejam usados por consumers deste pack.

## 8. Fechamento do antigo `Rever`
A página estava `Rever` após atualização física da library para 21.1.60. Neste passe:
- filename/runtime 21.1.60 = confirmados;
- release NeoForge 1.21.1 = confirmada;
- consumers causais continuam presentes;
- não há divergência documental que exija manter a pesquisa aberta.

Logo `Estado da pesquisa = Verificado`.

## 9. Consumer Portable Hole
Portable Hole foi catalogado no lote anterior e declara Puzzles Lib no alvo NeoForge. Testes relevantes após update da library:
- item/tool registra;
- uso abre passagem;
- config carrega;
- networking/restauração funciona;
- dedicated server não apresenta missing class/method.

## 10. Consumer Overflowing Bars
Overflowing Bars é outro consumer causal já observado no stack. Como envolve HUD/render e dados funcionais associados, testar cliente, resource reload, efeitos extremos e reconnect após mudança da library.

## 11. Version coupling
A library segue versionamento próprio e consumers podem aceitar ranges distintos. Atualizar só Puzzles Lib sem verificar minimum/maximum esperado por cada consumer pode introduzir ABI/API drift.

Procedimento recomendado para update:
1. mapear consumers declarados;
2. conferir ranges oficiais;
3. boot dedicado;
4. executar smoke por consumer;
5. revalidar config/network/render paths.

## 12. Sobreposição
Puzzles Lib não é substituível por Architectury, Balm, Moonlight, Platform ou outra library genérica. Os consumers compilam contra APIs/mod id específicos.

Categoria semelhante não é redundância.

## 13. Client/server e lifecycle
Cobrir cold boot, dedicated server, login, reconnect, config reload quando suportado, resource reload, dimension change e shutdown/restart.

Listeners/registrations compartilhados não podem duplicar em reload ou reconnect.

## 14. Riscos
1. **Remoção quebra consumers confirmados.**
2. **API/ABI drift** entre 21.1.60 e mods compilados para outra revisão.
3. **Networking mismatch** entre cliente/servidor.
4. **Config migration** após update.
5. **Classloading de lado errado** em dedicated server.
6. **Attribution error:** bug do consumer tratado como bug da library sem isolamento.
7. **False redundancy:** outra library não satisfaz o mesmo contrato.

## 15. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Puzzles Lib 21.1.60.
- [ ] Portable Hole registra e executa uso básico sem missing method/class.
- [ ] Overflowing Bars carrega/renderiza com seu comportamento esperado.
- [ ] Configs dos consumers são lidas e persistidas.
- [ ] Join/reconnect não gera packet mismatch.
- [ ] Resource reload não duplica hooks client-side.
- [ ] Mundo existente abre após restart sem erro de consumer.
- [ ] Backported helpers não geram regressão nos consumers que os usam.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 16. Evidências e limites
- Modlist física: JAR, mod id/runtime e mixins common/NeoForge.
- Publicação oficial: Puzzles Lib 21.1.60 Release NeoForge 1.21.1, 07/09/2026, Client & Server, MPL-2.0.
- Consumers confirmados no catálogo/runtime: Portable Hole e Overflowing Bars.
- Changelog 21.1.60: backports de ColorCollection, VariantUtils, ProjectileHelper e loot helpers.
- **Limite:** não foi enumerado cada consumer de Puzzles Lib entre todos os JARs; consumers causais já confirmados bastam para a classificação `Dependência`.
