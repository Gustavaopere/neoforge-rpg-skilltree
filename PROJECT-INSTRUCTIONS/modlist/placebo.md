# Placebo

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81229df8fedc22615fc7
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `Placebo-1.21.1-9.9.2.jar`, mod id `placebo`, runtime `9.9.2`, mixin `placebo.mixins.json`; consumers Apotheosis/Apothic confirmados no pack
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Placebo 9.9.2 e o stack consumidor Apotheosis/Apothic estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Placebo
- **Arquivo JAR:** `Placebo-1.21.1-9.9.2.jar`
- **Versão 1.21.1:** 9.9.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca
- **Função:** Biblioteca compartilhada do ecossistema Shadows_of_Fire usada por Apotheosis/Apothic e outros mods.
- **Dependências:** NeoForge 1.21.1. Consumers confirmados no pack: ecossistema Apotheosis/Apothic, incluindo Apothic Attributes e Apothic Compats.
- **Sobreposição:** Biblioteca específica do ecossistema Shadows_of_Fire; não é substituível por outra API genérica apenas por categoria semelhante.
- **Compatibilidade/Riscos:** Library Client & Server sem gameplay relevante isolado. Riscos: API/version drift com consumers, classloading, update isolado e atribuição incorreta de stacktraces. Remover Placebo isoladamente quebra consumers confirmados.
- **Observações:** Runtime 9.9.2, Release NeoForge 1.21.1 publicada em 19/07/2026. O projeto define Placebo como shared code e não como provider de gameplay autônomo.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial Placebo 9.9.2 + consumers Apotheosis/Apothic já confirmados no catálogo.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/placebo
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Placebo 9.9.2 reconstruído: shared-code boundary, consumers Apotheosis/Apothic, version coupling, classloading, lifecycle, riscos e testes; Dependência preservada.
- **Histórico da decisão:** 2026-08-26 — classificado como Dependência após confirmação de consumidores atuais do ecossistema Apotheosis/Apothic.
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `Placebo-1.21.1-9.9.2.jar`, mod id `placebo`, versão `9.9.2`, NeoForge 1.21.1. Placebo é uma biblioteca de código compartilhado do ecossistema Shadows_of_Fire e, isoladamente, não fornece gameplay relevante. Nesta modlist ela é **Dependência** porque há consumers físicos confirmados no stack Apotheosis/Apothic.

## 1. Identidade e papel
- **Mod:** Placebo.
- **JAR físico:** `Placebo-1.21.1-9.9.2.jar`.
- **Mod id:** `placebo`.
- **Runtime:** `9.9.2`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** Shadows_of_Fire.
- **Licença:** MIT.
- **Ambiente:** Client & Server.
- **Papel:** concentrar código compartilhado usado pelos mods do autor/ecossistema.
- **Decisão:** Dependência.

## 2. Sem gameplay relevante isolado
A descrição oficial é explícita: Placebo mantém shared code e sozinho essencialmente apenas se registra como mod, sem conteúdo de jogo relevante.

Consequência para auditoria:
- não avaliar Placebo por quantidade de itens/blocos;
- presença é determinada por consumers;
- remover a library para “economizar mod” sem dependency graph pode impedir startup dos consumers.

## 3. Consumers confirmados no pack
A auditoria atual já confirmou consumers do ecossistema **Apotheosis/Apothic**, incluindo Apothic Attributes e Apothic Compats, além do próprio stack Apotheosis presente.

Esses consumers justificam `Decisão = Dependência`.

Ownership:
- Placebo → helpers/shared contracts;
- consumer → atributos, enchanting, loot, gameplay e configs concretas.

Um crash em package Placebo deve ser triado junto do consumer que chamou a API.

## 4. Build 9.9.2
A build física `1.21.1-9.9.2` é **Release** oficial NeoForge 1.21.1 publicada em 19/07/2026.

O projeto aponta o changelog detalhado para o repositório oficial da linha 1.21. Esta ficha não atribui alterações internas específicas à 9.9.2 sem uma entrada textual inequívoca correspondente à build.

## 5. API/library boundary
Bibliotecas compartilhadas costumam concentrar registries, utilitários, serializers, eventos e helpers usados por consumers. O escopo exato de cada API precisa ser obtido do source/changelog quando uma integração específica for implementada.

Não transformar uma API disponível em feature ativa no pack sem provar que um consumer a usa.

## 6. Client/server e classloading
Como Placebo é Client & Server, validar:
- dedicated server sem acesso indevido a classes client-only;
- cliente com mesmas versões dos consumers;
- handshake/load order;
- data/resource reload de consumers;
- updates da library mantendo ABI exigida.

A própria library não deve ser usada como trigger de gameplay ou quest.

## 7. Version coupling com Apotheosis/Apothic
O risco principal é **version coupling**. Um consumer compilado para outra geração de Placebo pode falhar por API/method drift mesmo quando o mod id está presente.

Ao atualizar Placebo:
1. confirmar versão exigida/recomendada pelos consumers principais;
2. iniciar dedicated server;
3. abrir mundo existente;
4. testar registries/atributos/loot/configs do stack Apothic.

Downgrade/upgrade isolado sem esse smoke não é seguro.

## 8. Configuração e persistência
Placebo em si não deve ser tratado como owner de progressão. Se um arquivo/config/evento compartilhado é exposto por Placebo mas pertence logicamente ao consumer, a documentação deve permanecer na página do consumer.

Persistência de atributos/loot/enchants deve ser testada no sistema que os registra, não pela mera presença da library.

## 9. Sobreposição
Placebo não é substituível por Architectury, Platform, Moonlight ou outra library genérica. Bibliotecas não são intercambiáveis por terem categoria semelhante; os consumers dependem de APIs e mod IDs específicos.

## 10. Riscos
1. **Remoção quebra consumers:** stack Apotheosis/Apothic depende da library.
2. **API drift:** consumer e Placebo em versões incompatíveis.
3. **Classloading:** shared code chama classe exclusiva do lado errado.
4. **Attribution error:** stacktrace em Placebo interpretado como bug da library sem investigar consumer.
5. **Update isolado:** mudar apenas Placebo sem validar consumers.
6. **False redundancy:** outra library presente não substitui contratos Placebo.

## 11. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Placebo 9.9.2.
- [ ] Apotheosis/Apothic registram conteúdo sem missing class/method.
- [ ] Atributos Apothic carregam e persistem após relog/restart.
- [ ] Loot/enchants do stack consumidor carregam sem registry error.
- [ ] `/reload` não causa duplicate registration ou crash de consumer.
- [ ] Configs de consumers abrem/carregam normalmente.
- [ ] Mundo existente abre sem data migration error atribuível ao stack.
- [ ] Remoção simulada em instância de teste confirma dependência antes de qualquer decisão futura.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidências e limites
- Modlist física: `Placebo-1.21.1-9.9.2.jar`, mod id/runtime e `placebo.mixins.json`.
- CurseForge oficial: project 283644, Release NeoForge 1.21.1 9.9.2 de 19/07/2026, MIT, Client & Server.
- Descrição oficial: shared code; sozinho não adiciona gameplay relevante.
- Catálogo atual: consumers Apotheosis/Apothic confirmados no pack, sustentando `Dependência`.
- **Limite:** APIs/methods internos e delta textual exato da 9.9.2 não foram inventados sem changelog/source pinado à build.
