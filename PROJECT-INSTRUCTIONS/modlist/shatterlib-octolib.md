# ShatterLib | OctoLib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db813dab2fdc03789d4099
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `OctoLib-NEOFORGE-0.6.2+1.21.jar`, mod id `octolib`, runtime `0.6.2`, mixin `octolib-common.mixins.json`; Relics 0.12.8 presente como consumer causal
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, OctoLib 0.6.2 e Relics 0.12.8 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** ShatterLib | OctoLib
- **Arquivo JAR:** `OctoLib-NEOFORGE-0.6.2+1.21.jar`
- **Versão 1.21.1:** 0.6.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca, Visual
- **Função:** Biblioteca compartilhada para configuração YAML, UI/animação/tween, partículas/trails, networking e utilidades usadas por mods consumidores.
- **Dependências:** NeoForge 1.21.1. Consumer confirmado: Relics. SnakeYAML 2.2 está embarcado no JAR e não é top-level.
- **Sobreposição:** Não substitui GeckoLib/Lodestone/outra library genérica; contratos dos consumers são próprios. É dependency operacional enquanto Relics permanecer.
- **Compatibilidade/Riscos:** Dependency Client & Server de Relics. Riscos: consumer/API drift, YAML schema/serialization, networking/config mismatch, UI/animation reload e particle-trail cost. SnakeYAML 2.2 é embedded.
- **Observações:** Filename/publicação `0.6.2+1.21`; metadata runtime `0.6.2`. Release 0.6.2 adiciona particle trails. `snakeyaml-2.2.jar` está embedded.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge/Modrinth oficiais da release 0.6.2 + relação causal com Relics.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/octo-lib
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — OctoLib 0.6.2 reconstruído: consumer Relics, YAML/SnakeYAML, UI/tween/keyframes, networking, particle trails 0.6.2, lifecycle, riscos e testes.
- **Histórico da decisão:** 2026-08-26 — classificado como Dependência após confirmação, no próprio catálogo, de Relics instalado com OctoLib/ShatterLib como dependência.
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `OctoLib-NEOFORGE-0.6.2+1.21.jar`, mod id `octolib`, metadata runtime `0.6.2`, NeoForge 1.21.1. É library compartilhada do ecossistema OctoStudios/Shatterbyte; **Relics é consumer confirmado**, portanto a decisão permanece **Dependência**. O host embute SnakeYAML 2.2, que não é entrada top-level.

## 1. Identidade e papel
- **Página:** ShatterLib | OctoLib.
- **JAR físico:** `OctoLib-NEOFORGE-0.6.2+1.21.jar`.
- **Mod id:** `octolib`.
- **Runtime metadata:** `0.6.2`.
- **Publicação/filename:** `0.6.2+1.21`.
- **Loader:** NeoForge; build publicada para 1.21.1.
- **Ambiente:** Client & Server.
- **Papel:** infraestrutura compartilhada de configuração, UI/animação e utilidades consumida por outros mods.

## 2. Consumer confirmado: Relics
O catálogo do pack já confirma **Relics** como consumer de OctoLib/ShatterLib. Isso transforma a library em componente load-bearing: removê-la isoladamente não é uma otimização válida enquanto o consumer permanecer.

Ownership continua separado: Relics é authority de seus relics/efeitos; OctoLib fornece infraestrutura.

## 3. Configuração YAML
A documentação da linha descreve configuração YAML com validação e serialização profunda. O JAR físico embute `snakeyaml-2.2.jar`, coerente com essa função.

Riscos principais:
- schema/config antigo após update;
- valor inválido rejeitado ou normalizado;
- serialização de estruturas aninhadas;
- diferenças de config client/server quando um consumer exige consistência.

## 4. Tween, keyframes e easings
A linha 1.21 da library inclui primitives para tween/keyframes, easings/transitions e componentes visuais. Essas APIs podem ser usadas por consumers para animações sem que OctoLib seja o owner do comportamento de gameplay exibido.

## 5. UI, particles e child widgets
A documentação/changelogs da linha registram UI particles, child widgets e helpers de cor. São superfícies client-facing e podem interagir com scaling, resource reload e outras camadas de UI.

## 6. Particle trails — delta 0.6.2
O changelog exato da release física 0.6.2 registra **adição de particle trails**. Essa é a mudança específica atribuível a esta versão; features anteriores são tratadas como lineage da library, não como novidades exclusivas de 0.6.2.

## 7. Networking
A linha da library também oferece infraestrutura de networking para consumers. Sem source byte-equivalente pinado nesta auditoria, nomes de channels/packets e protocolo interno não são inventados.

Regra: qualquer mutação de gameplay deve permanecer validada pelo consumer/server, mesmo que OctoLib transporte dados.

## 8. Embedded SnakeYAML
A modlist física mostra `snakeyaml-2.2.jar` dentro do host. Pelo protocolo:
- não criar página top-level;
- não contar como mod adicional;
- não forçar atualização manual isolada;
- stacktraces SnakeYAML devem ser triados como dependency interna de OctoLib/consumer.

## 9. Client/server e lifecycle
CurseForge/Modrinth classificam a build como requerida em cliente e servidor. Validar:
- cold boot;
- dedicated server;
- conexão com versões iguais;
- load/save de configs;
- resource reload de elementos visuais;
- consumer após restart/update.

## 10. Relação com outras libraries
OctoLib não é substituto drop-in de GeckoLib, Lodestone, Moonlight ou outras libs. Mesmo quando duas oferecem animação/UI/config, seus consumers compilam contra contratos diferentes.

## 11. Riscos
1. **Consumer/API drift** entre Relics e OctoLib.
2. **Config schema drift** em YAML.
3. **Client/server mismatch** em consumer networking/config.
4. **UI/animation regression** após resource reload.
5. **Particle trail cost** em cenários de alta densidade.
6. **Embedded SnakeYAML ambiguity** se tratado indevidamente como top-level.
7. **Attribution error:** classe OctoLib em stacktrace não prova bug intrínseco da library.

## 12. Matriz de testes
- [ ] Dedicated server inicia com OctoLib 0.6.2 + Relics atual.
- [ ] Cliente conecta sem API/version mismatch.
- [ ] Relics registra conteúdo sem missing class.
- [ ] Config YAML válida carrega e persiste após restart.
- [ ] Config inválida falha de forma controlada.
- [ ] UI/animações do consumer não quebram após resource reload.
- [ ] Particle trails usados por consumer não causam leak/queda extrema de FPS.
- [ ] Atualização futura da library é regressada com todos os consumers identificados.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 13. Evidências e limites
- Modlist física: JAR, mod id/runtime, `octolib-common.mixins.json` e SnakeYAML 2.2 embarcado.
- CurseForge oficial: project 916747, file ID 8040848, Release NeoForge 1.21.1 de 04/05/2026, Client & Server.
- Modrinth oficial: 0.6.2, library compartilhada; changelog exato `Added particle trails`.
- Catálogo do pack: Relics consumer confirmado.
- **Limite:** internals de networking e cada API usada por Relics não foram inventados sem source pinado.
