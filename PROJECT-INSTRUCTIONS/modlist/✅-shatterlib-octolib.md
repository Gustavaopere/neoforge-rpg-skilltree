# ShatterLib | OctoLib

## Propriedades do registro

- **Mod:** ShatterLib \| OctoLib
- **Arquivo JAR:** OctoLib-NEOFORGE-0.6.2+1.21.jar
- **Versão 1.21.1:** 0.6.2
- **Categoria:** Biblioteca, Visual
- **Decisão:** Dependência
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/octo-lib
- **Função:** Biblioteca compartilhada para configuração YAML, UI/animação/tween, partículas/trails, networking e utilidades usadas por mods consumidores.
- **Dependências:** NeoForge 1.21.1. Consumer confirmado: Relics. SnakeYAML 2.2 está embarcado no JAR e não é top-level.
- **Compatibilidade/Riscos:** Dependency Client & Server de Relics. Riscos: consumer/API drift, YAML schema/serialization, networking/config mismatch, UI/animation reload e particle-trail cost. SnakeYAML 2.2 é embedded.
- **Sobreposição:** Não substitui GeckoLib/Lodestone/outra library genérica; contratos dos consumers são próprios. É dependency operacional enquanto Relics permanecer.
- **Observações:** Filename/publicação `0.6.2+1.21`; metadata runtime `0.6.2`. Release 0.6.2 adiciona particle trails. `snakeyaml-2.2.jar` está embedded.
- **Procedência:** modlist(1).txt física reconferida em 25/09/2026 + CurseForge/Modrinth oficiais da release 0.6.2 + relação causal com Relics.
- **Atualização/Status:** REVALIDADO EM 25/09/2026 — OctoLib 0.6.2/JAR físico reconfirmado; Relics permanece consumer confirmado e SnakeYAML 2.2 permanece embedded, não top-level.
- **Histórico da decisão:** 2026-08-26 — classificado como Dependência após confirmação, no próprio catálogo, de Relics instalado com OctoLib/ShatterLib como dependência.
- **Data da última decisão:** 2026-08-27

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #425: JAR `OctoLib-NEOFORGE-0.6.2+1.21.jar`, mod id `octolib`, runtime `0.6.2`, SHA-1 `f4f66d677b104c6af7f5c0f7e3c08d8e4370ecad`.

<callout icon="🔎" color="yellow_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `OctoLib-NEOFORGE-0.6.2+1.21.jar`, mod id `octolib`, metadata runtime `0.6.2`, NeoForge 1.21.1. É library compartilhada do ecossistema OctoStudios/Shatterbyte; **Relics é consumer confirmado**, portanto a decisão permanece **Dependência**. O host embute SnakeYAML 2.2, que não é entrada top-level.
</callout>
## 1. Identidade e papel
- **Página:** ShatterLib \| OctoLib.
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
